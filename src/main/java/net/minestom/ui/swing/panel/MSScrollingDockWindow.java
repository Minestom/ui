package net.minestom.ui.swing.panel;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public final class MSScrollingDockWindow extends JScrollPane implements Dockable {
    private final MSPanel panel;

    private final DockKey dockKey;

    public MSScrollingDockWindow(MSPanel panel) {
        super(panel);
        this.panel = panel;

        // ScrollPane config
//        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
//        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        // DockKey config
        dockKey = new DockKey(panel.getId().toString());
        dockKey.setFloatEnabled(false);
        dockKey.setMaximizeEnabled(false);
        dockKey.setAutoHideEnabled(false);
    }

    public @NotNull MSPanel getPanel() {
        return panel;
    }

    @Override
    public final @NotNull DockKey getDockKey() {
        return this.dockKey;
    }

    @Override
    public final @NotNull Component getComponent() {
        return this;
    }
}
