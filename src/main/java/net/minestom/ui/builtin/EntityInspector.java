package net.minestom.ui.builtin;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.swing.control.MSControl;
import net.minestom.ui.swing.control.renderer.MSEnumComboBoxRenderer;
import net.minestom.ui.panel.MSPanel;
import net.minestom.ui.swing.MSToggleView;
import net.minestom.ui.util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.List;

public class EntityInspector extends MSPanel {
    private final JScrollPane scrollPane;
    private final JPanel root;

    private EntityType type = EntityType.ZOMBIE;
    private Entity entity = new Entity(type);

    public EntityInspector() {
        super(NamespaceID.from("minestom", "entity_inspector"));



        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(300, Short.MAX_VALUE));
//        setPreferredSize(new Dimension(300, Short.MAX_VALUE));
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setPreferredSize(new Dimension(450, 450));
//        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root = new JPanel();
//        root.setMinimumSize(new Dimension(300, Short.MAX_VALUE));

//        root.setMaximumSize(new Dimension(300, Short.MAX_VALUE));
//        root.setMaximumSize(new Dimension(300, Short.MAX_VALUE));

        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(root);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setPreferredSize(new Dimension(300, 0));
//        scrollPane.setMaximumSize(new Dimension(300, Short.MAX_VALUE));
        add(scrollPane, BorderLayout.CENTER);
        JLabel uuidLabel = new JLabel(entity.getUuid().toString());
        root.add(uuidLabel);

        {
            MSToggleView parentToggle = new MSToggleView(null, "Test toggle area", false);
            root.add(parentToggle);

            parentToggle.addChild(new JLabel("AOIWHGAIUOGHU"));
            parentToggle.addChild(new JLabel("AOIWHGAIUOGHU"));
            parentToggle.addChild(new JLabel("AOIWHGAIUOGHU"));

            MSToggleView childToggle = new MSToggleView(null, "Inner toggle area", false);
            parentToggle.addChild(childToggle);

            childToggle.addChild(new JLabel("WOIHGIUAOWGHUAWHGIUHAIUWDHUIOAW"));
            childToggle.addChild(new JLabel("WOIHGIUAOWGHUAWHGIUHAIUWDHUIOAW"));
            childToggle.addChild(new JLabel("WOIHGIUAOWGHUAWHGIUHAIUWDHUIOAW"));
            childToggle.addChild(new JLabel("WOIHGIUAOWGHUAWHGIUHAIUWDHUIOAW"));

            parentToggle.addChild(new JLabel("AOIWHGAIUOGHU"));
            parentToggle.addChild(new JLabel("AOIWHGAIUOGHU"));
            parentToggle.addChild(new JLabel("AOIWHGAIUOGHU"));
            parentToggle.addChild(new JLabel("AOIWHGAIUOGHU"));


        }

        {   // Entity type selector box
            JComboBox<EntityType> entityTypeSelector = new JComboBox<>(EntityTypesAlphabetical);
            entityTypeSelector.setSelectedIndex(ZombieIndex);
            entityTypeSelector.setMinimumSize(entityTypeSelector.getPreferredSize());
            entityTypeSelector.setMaximumSize(entityTypeSelector.getPreferredSize());
            entityTypeSelector.setRenderer(new MSEnumComboBoxRenderer());
            entityTypeSelector.addItemListener(e -> {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                System.out.println("Swapped entity to " + e.getItem());
                type = (EntityType) e.getItem();
                entity.switchEntityType(type);
                //todo recreate metadata tree
            });
            root.add(entityTypeSelector);
        }

//        MSToggleView toggleView = new MSToggleView("Hello, Toggle");
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        add(toggleView);
//        toggleView = new MSToggleView("Hello, Toggle");
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        add(toggleView);
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        toggleView.addChild(new JLabel("AWGWAGAWGAWG"));
//        System.out.println(toggleView.getPreferredSize());

        {   // Metadata tree
            drawEntityMetadata(EntityTypeInfoRegistry.get(type));
        }


//        InspectorEntry entry = new InspectorEntry("Position", new MSVec3Control());
//        add(entry);
//        add(new InspectorEntry("Rotation", new MSVec3Control()));
//
//        JButton b1 = new JButton("Button 1");
//        b1.addActionListener(e -> System.out.println(entry.getSize()));
//        add(b1);
//
//


        JButton button = new JButton("Test Button");
        button.addActionListener(e -> {
            System.out.println(uuidLabel.getLocation());
        });

        System.out.println(root.getWidth());


//        root.addPropertyChangeListener();
        root.addPropertyChangeListener("__dyn_resize", e -> {
            revalidate();
            repaint();
            Dimension targetSize = new Dimension(
                    root.getLayout().minimumLayoutSize(root).width,
                    root.getLayout().preferredLayoutSize(root).height
            );
            System.out.println(root.getLayout().minimumLayoutSize(root));
            System.out.println(root.getLayout().preferredLayoutSize(root));
            root.setPreferredSize(targetSize);
            revalidate();
            repaint();
        });
        root.setPreferredSize(new Dimension(300, root.getPreferredSize().height));
        System.out.println(root.getPreferredSize().height);


    }

    private <T> void drawEntityMetadata(EntityTypeInfo entityTypeInfo) {
        if (entityTypeInfo == null) return;

        drawEntityMetadata(entityTypeInfo.parent());

        // Don't draw empty meta classes.
        if (entityTypeInfo.properties().isEmpty()) return;

        // Draw this meta type
        MSToggleView view = new MSToggleView(scrollPane, entityTypeInfo.name(), false);
        root.add(view);

        var entityMeta = entity.getEntityMeta();
        for (var property : entityTypeInfo.properties()) {
            var control = MSControl.Dynamic(
                    (Class<T>) property.type(),
                    () -> (T) property.get(entityMeta),
                    (newValue) -> property.set(entityMeta, newValue));
            var entry = new InspectorEntry(property.name(), (Container) control);
            view.addChild(entry); //todo this cast should not be necessary. The whole system is scuffed.
        }
    }

    public static class InspectorEntry extends Container {
        public InspectorEntry(String label, Container item) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//            setLayout(new FlowLayout(FlowLayout.LEADING));

            var entryLabel = new JLabel(label);
            entryLabel.setMinimumSize(new Dimension(100, 0));
            entryLabel.setMaximumSize(new Dimension(100, 100));
            add(entryLabel);

            add(Box.createHorizontalGlue());

//            add(Box.createHorizontalStrut(10));

            add(item);

            setMaximumSize(new Dimension(Short.MAX_VALUE, item.getPreferredSize().height));
        }
    }

    /*
     * Entity metadata parsing stuff
     * todo needs a rework (eg not stuffed at the bottom of some other class)
     */



    private static final EntityType[] EntityTypesAlphabetical = Arrays
            .stream(EntityType.values())
            .sorted(Comparator.comparing(Enum::name))
            .toArray(EntityType[]::new);
    private static final int ZombieIndex = List.of(EntityTypesAlphabetical).indexOf(EntityType.ZOMBIE);

    private record EntityTypeInfo(String name, java.util.List<EntityMetaProperty> properties, EntityTypeInfo parent) {
    }

    private record EntityMetaProperty(String name, Class<?> type, Method getter, Method setter) {
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

    private static final EnumMap<EntityType, EntityTypeInfo> EntityTypeInfoRegistry = new EnumMap<>(EntityType.class);

    static {
        Map<Class<?>, EntityTypeInfo> parentCache = new HashMap<>();

        for (EntityType type : EntityType.values()) {
            net.minestom.server.entity.Entity temp = new net.minestom.server.entity.Entity(type, new UUID(0, 0));

            Class<? extends EntityMeta> metaType = temp.getEntityMeta().getClass();
            var entityTypeInfo = recursiveParseEntityType(parentCache, metaType);
            EntityTypeInfoRegistry.put(type, entityTypeInfo);
        }
    }

    private static EntityTypeInfo recursiveParseEntityType(
            Map<Class<?>, EntityTypeInfo> parentCache,
            Class<? extends EntityMeta> metaType) {
        if (metaType.equals(Object.class)) return null;

        // Use cache if we know about this type.
        if (parentCache.containsKey(metaType))
            return parentCache.get(metaType);

        var name = metaType.getSimpleName();
        var superType = recursiveParseEntityType(parentCache, (Class<? extends EntityMeta>) metaType.getSuperclass());

        List<EntityMetaProperty> properties = new ArrayList<>();
        for (var method : metaType.getMethods()) {
            // Only handle public non-deprecated methods declared by this class.
            if (!Modifier.isPublic(method.getModifiers())) continue;
            if (method.getAnnotation(Deprecated.class) != null) continue;
            if (method.getDeclaringClass() != metaType) {
                continue;
            }

            String methodName = method.getName();
            if (!methodName.startsWith("set")) continue;
            if (method.getParameterCount() != 1) {
                System.out.println("Cannot parse entity meta " + metaType.getSimpleName() + "#" + methodName + ": too many parameters"); //logger, warn
                continue;
            }

            try {
                String baseName = methodName.substring(3);
                String propertyName = StringUtil.toHumanReadable(baseName);
                Class<?> propertyType = method.getParameterTypes()[0];

                String prefix = propertyType == boolean.class ? "is" : "get";
                Method getter = metaType.getMethod(prefix + baseName);


                properties.add(new EntityMetaProperty(propertyName, propertyType, getter, method));
            } catch (NoSuchMethodException e) {
                System.out.println("Cannot parse entity meta " + metaType.getSimpleName() + "#" + methodName + ": no such getter"); //logger, warn
                continue;
            }
        }

        var entityTypeInfo = new EntityTypeInfo(name, properties, superType);
        parentCache.put(metaType, entityTypeInfo);
        return entityTypeInfo;
    }
}
