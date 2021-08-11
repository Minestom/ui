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

    public MSToggleView(String label) {
        this(label, true);
    }

    public MSToggleView(String label, boolean defaultVisible) {
        this.visible = defaultVisible;
        setLayout(new BorderLayout());

        addPropertyChangeListener("minestom:resize", this::handleResizeHint);

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
        // Need to forward resize events through here in case a child toggle view sends one to `panel`.
        SwingHelper.forwardLongProperty(panel, "minestom:resize");
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

    public void resize() {
        revalidate();
        repaint();
        if (getParent() != null) {

            getParent().revalidate();
            getParent().repaint();
        }

        setMaximumSize(new Dimension(Short.MAX_VALUE, getMinimumSize().height));
        setPreferredSize(new Dimension(Short.MAX_VALUE, getMinimumSize().height));

        if (getParent() != null) {
            getParent().firePropertyChange("minestom:resize", 0, 1);
        }
    }

    private void handleResizeHint(Object /*event*/ignored) { resize(); }

}
