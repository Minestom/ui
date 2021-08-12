package net.minestom.ui.panel;

import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.entity.EntityFinder;
import net.minestom.ui.swing.MSFont;
import net.minestom.ui.swing.control.primitive.MSStringControl;
import net.minestom.ui.swing.listener.MSMouseListener;
import net.minestom.ui.swing.panel.MSPanel;
import net.minestom.ui.swing.select.MSSelectable;
import net.minestom.ui.swing.select.MSSelectionContext;
import net.minestom.ui.swing.util.MSContextMenu;
import net.minestom.ui.util.StringUtil;
import net.minestom.ui.window.MainWindow;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static net.minestom.ui.swing.util.SwingHelper.alignLeft;

public class InstanceHierarchyPanel extends MSPanel implements MSMouseListener {
    private static final Logger logger = LoggerFactory.getLogger(InstanceHierarchyPanel.class);

    private List<Instance> targets = null;
    private List<Entity> entities = null;

    private final ArgumentEntity entitySelector;
    private final MSStringControl entitySelectorControl;

    private final MSSelectionContext<Entity> entityHolder;

    public InstanceHierarchyPanel(MainWindow window) {
        super(NamespaceID.from("minestom", "instance_hierarchy"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addMouseListener(this);

        // Selector Argument
        entitySelector = new ArgumentEntity("_");
        entitySelector.onlyPlayers(false);
        entitySelector.singleEntity(false);

        // Create argument selector input
        entitySelectorControl = new MSStringControl();
        entitySelectorControl.setClientProperty("JTextField.placeholderText", "@e[type=player]");
        entitySelectorControl.addChangeListener(this::updateQuery);
        entitySelectorControl.setMaximumSize(new Dimension(
                entitySelectorControl.getMaximumSize().width,
                entitySelectorControl.getPreferredSize().height
        ));
        var wrappedEntitySelectorControl = alignLeft(entitySelectorControl);
        wrappedEntitySelectorControl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(wrappedEntitySelectorControl);

        var separator = new JSeparator();
        separator.setMaximumSize(new Dimension(separator.getMaximumSize().width, 5));
        separator.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(separator);

        entityHolder = new MSSelectionContext<>();
        entityHolder.setSelectableFactory(entity -> new SelectableEntity(entity, entityHolder));
        entityHolder.setSelectionHandler(newSelection -> {
            var entityEditor = window.getPanel(EntityEditor.class);
            if (entityEditor == null) {
                logger.debug("No available entity editor.");
                return;
            }
            entityEditor.setTarget(newSelection);
        });
        add(entityHolder);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        var component = getComponentAt(e.getPoint());

        // Deselect current entity if we are clicking on empty space in the hierarchy
        if (component == this) {
            entityHolder.setSelection(null);
        }
    }

    private void updateQuery(String newQuery) {
        try {
            var queryString = entitySelectorControl.get();
            if (queryString.isEmpty()) {
                // An empty string is not a valid query according to an EntitySelector,
                // so we manually clear results and accept it.
                entities = new ArrayList<>();
            } else {
                EntityFinder entityFinder = entitySelector.parse(queryString);
                // It would be better if `EntityFinder` supported a set of instances to query
                // todo should pr
                entities = entityFinder.find(null, null).stream()
//                .filter(entity -> targets.contains(entity.getInstance()))
                        .toList();
            }

            createElements();
            // It is an acceptable input if `EntityFinder#find` succeeds.
            entitySelectorControl.removeClientProperty("JComponent.outline");
        } catch (ArgumentSyntaxException ignored) {
            entitySelectorControl.setClientProperty("JComponent.outline", "error");
        }
    }

    private void createElements() {
        if (entities == null) return;

        //todo need to handle replacing entries smoother. I should be able to pass a
        // collection of the selection type and it will only create new elements/remove
        // old ones while preserving the existing ones (and their selection status)
        // It must also call `onSelect` if the previous selection is removed.

        entityHolder.clear();

        for (var entity : entities) {
            entityHolder.addItem(entity);
        }
    }

    /**
     * {@link MSSelectable} for an Entity.
     * <p>
     * Based on {@link net.minestom.ui.swing.select.MSSelectableLabel}, check that implementation
     * for explanation and details.
     *
     * @see MSSelectionContext
     */
    private static class SelectableEntity extends Box implements MSSelectable<Entity>, MSMouseListener {
        private final Entity entity;

        private final JLabel line1;
        private final JLabel line2;
        private final MSContextMenu contextMenu;

        public SelectableEntity(Entity entity, Container eventTarget) {
            super(BoxLayout.Y_AXIS);
            this.entity = entity;

            setBackground(new Color(74, 136, 199)); //todo keep track of this selection color somewhere logical
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            String entityType = StringUtil.toHumanReadable(entity.getEntityType());
            String entityClass = entity.getEntityType() == EntityType.PLAYER ?
                    "" : " - " + entity.getClass().getSimpleName();
            line1 = new JLabel(entityType + entityClass);
            add(alignLeft(line1));

            line2 = new JLabel(entity.getUuid().toString());
            line2.setFont(MSFont.SubtitleMono);
            add(alignLeft(line2));

            contextMenu = new MSContextMenu(this, eventTarget);
            createContextMenu();
        }

        @Override
        public void setSelected(boolean selected) {
            setOpaque(selected);
            repaint();
        }

        @Override
        public @NotNull Entity get() {
            return entity;
        }

        @Override
        public @NotNull Container getBackingControl() {
            // This isnt perfectly accurate since there are actually 2 underlying labels.
            return this;
        }

        private void createContextMenu() {
            JMenuItem removeItem = new JMenuItem("Remove Entity");
            removeItem.addActionListener(e -> {
                System.out.println("TODO");
            });
            contextMenu.add(removeItem);
            JMenuItem copyIdItem = new JMenuItem("Copy UUID");
            copyIdItem.addActionListener(e -> {
                var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(entity.getUuid().toString()), null);
            });
            contextMenu.add(copyIdItem);
        }

        @Override
        public String toString() {
            return "SelectableEntity{" + entity + "}";
        }
    }
}
