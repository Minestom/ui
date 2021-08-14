package net.minestom.ui.panel;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.needspackage.EntityTypes;
import net.minestom.ui.swing.MSToggleView;
import net.minestom.ui.swing.util.SwingHelper;
import net.minestom.ui.swing.control.MSControl;
import net.minestom.ui.swing.control.MSDynamicControl;
import net.minestom.ui.swing.panel.MSPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityEditor extends MSPanel {
    private record MetadataElement<T>(EntityTypes.MetadataHandle.Property property, MSDynamicControl<T> control) {
        public void update(EntityMeta meta) {
            control.set((T) property.get(meta));
        }
    }

    private final List<MetadataElement<?>> elements = new ArrayList<>();

    private Entity entity = null;

    public EntityEditor() {
        super(NamespaceID.from("minestom", "entity_editor"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initForEntity();
    }

    public void setTarget(Entity entity) {
        System.out.println("EntityEditor >> New Target: " + entity);
        this.entity = entity;
        initForEntity();
    }

    public void updateValues() {
        //todo
    }

    private void initForEntity() {
        removeAll();

        if (entity == null) {
            add(new JLabel("Select an entity to edit properties"));
        } else {
            add(SwingHelper.alignLeft(new JLabel(entity.getUuid().toString())));

            var metadata = EntityTypes.MetadataHandles.get(entity.getEntityType());
            createMetadataElements(metadata);
        }

        msResize();
    }

    //todo clear elements before calling this for the first time.
    private void createMetadataElements(EntityTypes.MetadataHandle metadata) {
        if (metadata == null) return; // Recursive end

        // Draw the parent first so it goes from least specific to most specific in panel
        createMetadataElements(metadata.parent());

        // Dont draw empty metadata classes
        if (metadata.properties().isEmpty()) return;

        // Create item wrapper
        MSToggleView container = new MSToggleView(metadata.name(), false);
        add(container);

        // Add properties
        for (var property : metadata.properties()) {
            var control = MSControl.Dynamic(property.type());
            control.addChangeListener(newValue -> property.set(entity.getEntityMeta(), newValue));
            var labelledControl = createLabelledControl(property.name(), control);
            container.addChild(labelledControl);
            // Create updatable element
            var element = new MetadataElement<>(property, control);
            elements.add(element);
        }
    }

    private Container createLabelledControl(String name, Container control) {
        Box box = Box.createHorizontalBox();
        var label = new JLabel(name);
        label.setPreferredSize(new Dimension(140, label.getMinimumSize().height));
        box.add(label);
        box.add(Box.createHorizontalStrut(10));
        box.add(control);
        return box;
    }
}
