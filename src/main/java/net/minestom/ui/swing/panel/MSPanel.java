package net.minestom.ui.swing.panel;

import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.swing.util.SwingHelper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public abstract class MSPanel extends JPanel {
    private final NamespaceID id;

    public MSPanel(NamespaceID id) {
        this.id = id;

        // Respond to children sending resize messages (and window resize)
        // Used to implement MSToggleView (see notes there)
        addPropertyChangeListener("minestom:resize", this::handleResizeHint);
        addHierarchyBoundsListener(SwingHelper.resize(this::handleResizeHint));
    }

    private void handleResizeHint(Object /*event*/ignored) {
        if (getParent() == null) return;

        setPreferredSize(new Dimension(
                getParent().getSize().width,
                getLayout().minimumLayoutSize(this).height
        ));
    }

    public @NotNull NamespaceID getId() {
        return id;
    }
}
