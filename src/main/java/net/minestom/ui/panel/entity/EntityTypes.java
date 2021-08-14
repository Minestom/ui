package net.minestom.ui.panel.entity;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.ui.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class EntityTypes {
    /**
     * All of the entity types in alphabetical order by `Enum#name` field.
     */
    public static final EntityType[] Alphabetical = Arrays
            .stream(EntityType.values())
            .sorted(Comparator.comparing(Enum::name))
            .toArray(EntityType[]::new);

    /**
     * The alphabetical index of the default entity type to use.
     */
    public static final int DefaultIndex = List.of(Alphabetical).indexOf(EntityType.ZOMBIE);

    /**
     * Contains an entry for every known {@link EntityMeta} subclass.
     */
    public static final EnumMap<EntityType, MetadataHandle> MetadataHandles = new EnumMap<>(EntityType.class);

    /**
     * Represents a single {@link EntityMeta} class with its known modifiable properties and its parent.
     */
    public static record MetadataHandle(String name, List<Property> properties, MetadataHandle parent) {

        /**
         * Represents a single property of an {@link EntityMeta}.
         * Can be modified given an instance of the {@link EntityMeta}.
         */
        public static record Property(String name, Class<?> type, Method getter, Method setter) {
            public <Meta extends EntityMeta> Object get(Meta meta) {
                try {
                    return getter.invoke(meta);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace(); //todo better exception handling
                    throw new RuntimeException("See above error");
                }
            }

            public <Meta extends EntityMeta> void set(Meta meta, Object value) {
                try {
                    setter.invoke(meta, value);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace(); //todo better exception handling
                }
            }
        }
    }

    // Metadata Crawling
    // =================

    static {
        final Map<Class<?>, MetadataHandle> metadataCache = new HashMap<>();
        final UUID knownUuid = new UUID(0, 0);

        for (EntityType type : EntityType.values()) {
            // Create useless entity of the given type (so it will instantiate the metadata.
            final Entity temp = new Entity(type, knownUuid);

            final Class<? extends EntityMeta> metaType = temp.getEntityMeta().getClass();
            final var handle = parseMetadataType(metadataCache, metaType);
            MetadataHandles.put(type, handle);
        }
    }

    private static MetadataHandle parseMetadataType(Map<Class<?>, MetadataHandle> cache, Class<? extends EntityMeta> metaType) {
        if (metaType.equals(Object.class)) return null; // Recursive end

        // Use cache if we have parsed this type already.
        if (cache.containsKey(metaType))
            return cache.get(metaType);

        // Parse parent type for reference in this type (will use cache if available)
        //noinspection unchecked
        final var parentType = parseMetadataType(cache,
                (Class<? extends EntityMeta>) metaType.getSuperclass());

        // Parse this metadata
        var name = metaType.getSimpleName();
        List<MetadataHandle.Property> properties = new ArrayList<>();

        // Parse all of the methods in the EntityMeta class and select methods which appear to be properties.
        for (var method : metaType.getMethods()) {
            // Properties are always
            //  public,
            //  non-deprecated (we ignore deprecated ones),
            //  declared in this class (properties in parent class will be handled by that class's MetadataHandle)
            if (!Modifier.isPublic(method.getModifiers())) continue;
            if (method.getAnnotation(Deprecated.class) != null) continue;
            if (method.getDeclaringClass() != metaType) {
                continue;
            }

            String methodName = method.getName();
            // Only look for setters, we find the appropriate getter later.
            if (!methodName.startsWith("set")) continue;
            // The properties should only have one argument in the setter.
            // todo: Currently printing to see if there are any edge cases here
            if (method.getParameterCount() != 1) {
                System.out.println("Cannot parse entity meta " + metaType.getSimpleName() + "#" + methodName + ": too many parameters"); //logger, warn
                continue;
            }

            try {
                String baseName = methodName.substring(3);
                String propertyName = StringUtil.toHumanReadable(baseName);
                Class<?> propertyType = method.getParameterTypes()[0];

                // We make an assumption about the prefix of the getter method,
                // if it does not exist we will not use the property.
                String prefix = propertyType == boolean.class ? "is" : "get";
                Method getter = metaType.getMethod(prefix + baseName);

                properties.add(new MetadataHandle.Property(propertyName, propertyType, getter, method));
            } catch (NoSuchMethodException e) {
                System.out.println("Cannot parse entity meta " + metaType.getSimpleName() + "#" + methodName + ": no such getter"); //logger, warn
            }
        }

        var metadataHandle = new MetadataHandle(name, properties, parentType);
        cache.put(metaType, metadataHandle);
        return metadataHandle;
    }







}
