package net.minestom.ui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public class SwingUtil {
    private SwingUtil() {}

    private Container alignLeft(Container container) {
        // Can left align inside a vertical box layout, though the need for this is really incredible.
        // This will (i think) lose most layout hints on `container`. Not sure how `Box` handles size hints.
        Box b = Box.createHorizontalBox();
        b.add(container);
        b.add(Box.createHorizontalGlue());
        return b;
    }

    /**
     * Unsafe
     *
     * todo doc
     */
    public static void forwardLongProperty(Container container, String property) {
        container.addPropertyChangeListener(e -> {
            if (container.getParent() == null || !property.equals(e.getPropertyName()))
                return;
            try {
                container.getParent().firePropertyChange(e.getPropertyName(), (long) e.getOldValue(), (long) e.getNewValue());
            } catch (ClassCastException ignored) {
                throw new RuntimeException("Only long properties may be forwarded by forwardLongProperty!");
            }
        });
    }

    public static HierarchyBoundsListener resize(Consumer<HierarchyEvent> fn) {
        return new HierarchyBoundsListener() {
            @Override
            public void ancestorMoved(HierarchyEvent e) { }

            @Override
            public void ancestorResized(HierarchyEvent e) {
                fn.accept(e);
            }
        };
    }
}
