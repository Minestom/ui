package net.minestom.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import net.minestom.ui.panel.EntityEditor;
import net.minestom.ui.panel.TestPanel;
import net.minestom.ui.swing.panel.MSScrollingDockWindow;
import net.minestom.ui.swing.util.JMenuBarBuilder;
import net.minestom.ui.window.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Would be nice to add a demo window to showcase all of the MS- components.
//    (opened with some combination of development menu, keybind, vm arg)
// Should add some debug menu to figure out the Minestom version (if possible) and vm info for debugging

public class Main {
    public static void main(String[] args) {

        MainWindow window = new MainWindow();

        window.display();


//

//
//        window.setVisible(true);
    }
}
