package net.minestom.ui.panelold;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public abstract class MSPanel extends JPanel implements Dockable {
    private final NamespaceID id;
    private final DockKey dockKey;

    protected MSPanel(NamespaceID id) {
        this.id = id;

        dockKey = new DockKey(id.toString());
        dockKey.setFloatEnabled(false);
        dockKey.setMaximizeEnabled(false);
        dockKey.setAutoHideEnabled(false);
        dockKey.setCloseEnabled(false); //todo should be enabled.
    }

    public final @NotNull NamespaceID getId() {
        return this.id;
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
