package net.minestom.ui.extension.panel;

import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.swing.panel.MSPanel;

public class ExtensionTestPanel extends MSPanel {

    public ExtensionTestPanel(NamespaceID id) {
        super(NamespaceID.from("extension", "test_panel"));
    }
}
