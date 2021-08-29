package net.minestom.ui.swing.tree;

import com.formdev.flatlaf.icons.FlatTreeCollapsedIcon;
import com.formdev.flatlaf.icons.FlatTreeExpandedIcon;
import net.minestom.ui.swing.MSContainer;
import net.minestom.ui.swing.util.MSMouseListener;
import net.minestom.ui.swing.util.icon.MSIcon;
import net.minestom.ui.swing.util.SwingHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MSTreeNode extends MSContainer implements MSMouseListener {


    private static final Icon TreeCollapsed = new FlatTreeCollapsedIcon();
    private static final Icon TreeExpanded = new FlatTreeExpandedIcon();

    public static final int NONE = 0x0;
    public static final int ALWAYS_SHOW_ARROWS = 0x1;


    private final int opts;

    private Container labelContainer;
    private MSIcon.Element labelToggle;
    private Container labelElement;

    private final Map<String, MSTreeNode> children = new HashMap<>();
    private JPanel childrenContainer;

    private boolean expanded = false;

    public MSTreeNode(Container element) {
        this(element, NONE);
    }

    public MSTreeNode(Container element, int opts) {
        this.opts = opts;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        labelContainer = Box.createHorizontalBox();
        labelContainer.addMouseListener(this);
        add(labelContainer);

        labelToggle = new MSIcon.Element(new Dimension(25, 25), TreeCollapsed);
        labelContainer.add(labelToggle);

        labelElement = element;
        labelContainer.add(labelElement);

        labelContainer.add(Box.createHorizontalGlue());

        // Children
        childrenContainer = new JPanel();
        childrenContainer.setLayout(new BoxLayout(childrenContainer, BoxLayout.Y_AXIS));
        childrenContainer.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        add(childrenContainer);
        SwingHelper.forwardLongProperty(childrenContainer, "minestom:resize");

        redraw();
    }

    public boolean isLeaf() {
        return childrenContainer.getComponentCount() == 0;
    }

    public void __addChild(MSTreeNode child) {
        children.put(child.hashCode() + " ", child);
        childrenContainer.add(child);
        redraw();
    }

    @Nullable
    public MSTreeNode getChildNode(@NotNull String id) {
        return children.get(id);
    }

    @NotNull
    public MSTreeNode getChildNodeOrCreate(@NotNull String id, @NotNull Supplier<Container> elementSupplier) {
        MSTreeNode child = getChildNode(id);
        if (child != null) return child;
        return createChildNode(id, elementSupplier.get());
    }

    @NotNull
    public MSTreeNode createChildNode(@NotNull String id, @NotNull String label) {
        return createChildNode(id, SwingHelper.alignLeft(new JLabel(label)));
    }

    @NotNull
    public MSTreeNode createChildNode(@NotNull String id, @NotNull Container element) {
        var child = new MSTreeNode(element);
        children.put(id, child);
        childrenContainer.add(child);
        redraw();
        return child;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        redraw();
    }

    // Implementation
    // ==============

    @Override
    public void mouseClicked(MouseEvent e) {
        setExpanded(!expanded);
    }

    private void redraw() {
        // Icon
        if (isLeaf() && (opts & ALWAYS_SHOW_ARROWS) != ALWAYS_SHOW_ARROWS) {
            labelToggle.setIcon(MSIcon.Empty);
        } else {
            labelToggle.setIcon(expanded ? TreeExpanded : TreeCollapsed);
        }

        if (expanded) {
            add(childrenContainer);
        } else {
            remove(childrenContainer);
        }

        resize();
    }
}
