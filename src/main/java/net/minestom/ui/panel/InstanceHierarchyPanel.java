package net.minestom.ui.panel;

import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.entity.EntityFinder;
import net.minestom.ui.swing.control.primitive.MSStringControl;
import net.minestom.ui.swing.panel.MSPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static net.minestom.ui.swing.util.SwingHelper.alignLeft;

public class InstanceHierarchyPanel extends MSPanel {
    private List<Instance> targets = null;

    private final ArgumentEntity entitySelector = new ArgumentEntity("_");
    private final MSStringControl entitySelectorControl = new MSStringControl();

    public InstanceHierarchyPanel() {
        super(NamespaceID.from("minestom", "instance_hierarchy"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create argument selector input
        entitySelectorControl.setMaximumSize(entitySelectorControl.getPreferredSize());
        add(alignLeft(entitySelectorControl));

        var tempButton = new JButton("Search");
        tempButton.addActionListener(e -> createElements());
        add(alignLeft(tempButton));
    }

    private void createElements() {
        entitySelector.onlyPlayers(false);
        entitySelector.singleEntity(false);
        EntityFinder entityFinder = entitySelector.parse(entitySelectorControl.get());

        System.out.println("Creating Instance Hierarchy");

        // Would be nice if entityFinder could accept a filter to a set of instances
        List<Entity> entities = entityFinder.find(null, null).stream()
//                .filter(entity -> targets.contains(entity.getInstance()))
                .toList();

        System.out.println("Entities: " + entities.toString());

        for (var entity : entities) {
            JLabel element = new JLabel(entity.getUuid() + " // " + entity.getClass().getSimpleName());
            add(alignLeft(element));
        }
    }


}
