package net.minestom.ui.swing;

import java.awt.*;

public class MSContainer extends Container {

    public MSContainer() {
        addPropertyChangeListener("minestom:resize", this::handleResizeHint);
    }

    protected void resize() {
        handleResizeHint(null);
    }

    private void handleResizeHint(Object /*event*/ignored) {
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
}
