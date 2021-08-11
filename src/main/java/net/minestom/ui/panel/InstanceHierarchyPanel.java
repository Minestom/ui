package net.minestom.ui.panel;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.entity.EntityFinder;
import net.minestom.ui.swing.control.primitive.MSStringControl;
import net.minestom.ui.swing.panel.MSPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static net.minestom.ui.swing.util.SwingHelper.alignLeft;

public class InstanceHierarchyPanel extends MSPanel {
    private List<Instance> targets = null;
    private List<Entity> entities = null;

    private final ArgumentEntity entitySelector;
    private final MSStringControl entitySelectorControl;

    private final Container entityHolder;

    public InstanceHierarchyPanel() {
        super(NamespaceID.from("minestom", "instance_hierarchy"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Selector Argument
        entitySelector = new ArgumentEntity("_");
        entitySelector.onlyPlayers(false);
        entitySelector.singleEntity(false);

        // Create argument selector input
        entitySelectorControl = new MSStringControl();
        entitySelectorControl.addChangeListener(this::updateQuery);
        entitySelectorControl.setMaximumSize(entitySelectorControl.getPreferredSize());
        add(alignLeft(entitySelectorControl));

        entityHolder = new JPanel();
        entityHolder.setLayout(new BoxLayout(entityHolder, BoxLayout.Y_AXIS));
        add(entityHolder);
    }

    private void updateQuery(String newQuery) {
        try {
            System.out.println("UPDATE QUERY");
            EntityFinder entityFinder = entitySelector.parse(entitySelectorControl.get());

            entities = entityFinder.find(null, null).stream()
//                .filter(entity -> targets.contains(entity.getInstance()))
                    .toList();

            createElements();

            entitySelectorControl.removeClientProperty("JComponent.outline");
        } catch (ArgumentSyntaxException ignored) {
            entitySelectorControl.setClientProperty("JComponent.outline", "error");
        }
    }

    private void createElements() {
        if (entities == null) return;

        entityHolder.removeAll();
        System.out.println("Entities: " + entities);

        for (var entity : entities) {
            JLabel element = new JLabel(entity.getUuid() + " // " + entity.getClass().getSimpleName());
            entityHolder.add(alignLeft(element));
        }

        entityHolder.revalidate();
        entityHolder.repaint();

        msResize();
    }


}
