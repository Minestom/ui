package net.minestom.ui.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Creates an area which can be opened and closed by clicking the label button.
 *
 * todo It feels like there should be some better way to accomplish this.
 */
public class MSToggleView extends Container {
    private JButton button;
    private Container panel;

    private boolean visible;
    private JScrollPane scrollPane;

    public MSToggleView(JScrollPane scrollPane, String label) {
        this(scrollPane, label, true);
    }

    public MSToggleView(JScrollPane scrollPane, String label, boolean defaultVisible) {
        this.visible = defaultVisible;
        setLayout(new BorderLayout());

        addPropertyChangeListener("__dyn_resize", e -> resize());

        // Toggle button, always visible
        button = new JButton(label);
        button.setMaximumSize(new Dimension(Short.MAX_VALUE, button.getPreferredSize().height));
        button.putClientProperty("JButton.buttonType", "square");
        button.setHorizontalAlignment(SwingConstants.LEADING);
        button.addActionListener(e -> {
            if (visible = !visible) {
                add(panel);
            } else {
                remove(panel);
            }

            resize();
        });
        add(button, BorderLayout.NORTH);

        // Panel, Visibility toggled, Contains all children in vertical box layout
        panel = new Container();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(Short.MAX_VALUE, panel.getPreferredSize().height));
        if (visible) add(panel, BorderLayout.CENTER);

        // Spacer to simulate indent (15 aligns it with button text)
        add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);

        // Set initial size
        resize();
    }

    public void addChild(Container child) {
        panel.add(child);
        resize();
    }

    private void resize() {
        // Force min size + some padding.
        setMaximumSize(new Dimension(Short.MAX_VALUE, getMinimumSize().height + 15));
        setPreferredSize(new Dimension(Short.MAX_VALUE, getMinimumSize().height + 15));

        if (getParent() != null) {

            getParent().revalidate();
            getParent().repaint();


            getParent().firePropertyChange("__dyn_resize", 0, 1);
        }

        recursiveFireDynamicResize(getParent());
    }

    private void recursiveFireDynamicResize(Container container) {
        if (container == null) return;

        container.firePropertyChange("__dyn_resize", 0, 1);

        recursiveFireDynamicResize(container.getParent());
    }
}
