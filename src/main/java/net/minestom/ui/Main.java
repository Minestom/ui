package net.minestom.ui;

import net.minestom.ui.panel.InstanceHierarchyPanel;
import net.minestom.ui.panel.PanelRegistry;
import net.minestom.ui.panel.TestPanel;
import net.minestom.ui.panel.entity.EntityEditor;
import net.minestom.ui.panel.nbt.NBTEditorPanel;
import net.minestom.ui.window.MainWindow;

import java.nio.file.Paths;

// Would be nice to add a demo window to showcase all of the MS- components.
//    (opened with some combination of development menu, keybind, vm arg)
// Should add some debug menu to figure out the Minestom version (if possible) and vm info for debugging

public class Main {
    public static void main(String[] args) {

        Settings.init(Paths.get("."));

        PanelRegistry.registerPanel(TestPanel.class);
        PanelRegistry.registerPanel(InstanceHierarchyPanel.class);
        PanelRegistry.registerPanel(NBTEditorPanel.class);
        PanelRegistry.registerPanel(EntityEditor.class);

        MainWindow window = new MainWindow();
        window.display();

    }
}
