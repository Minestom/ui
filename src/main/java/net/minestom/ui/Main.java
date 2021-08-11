package net.minestom.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import net.minestom.ui.panel.EntityEditor;
import net.minestom.ui.panel.TestPanel;
import net.minestom.ui.swing.panel.MSScrollingDockWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Would be nice to add a demo window to showcase all of the MS- components.
//    (opened with some combination of development menu, keybind, vm arg)

public class Main {
    public static void main(String[] args) {
        // Allow dark mode on macOS.
        System.setProperty("apple.awt.application.appearance", "system");
        FlatDarkLaf.setup();

        //todo some window class
        JFrame window = new JFrame("Minestom UI");
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            }
        });
        window.setSize(1440 / 2, 1024 / 2);
        window.setLocationRelativeTo(null); // Center the window

        DockingDesktop dock = new DockingDesktop();
        window.getContentPane().add(dock);


        final TestPanel testPanel = new TestPanel();
        final MSScrollingDockWindow testPanelWindow = new MSScrollingDockWindow(testPanel);
        dock.addDockable(testPanelWindow);

        final EntityEditor entityEditor = new EntityEditor();
        final MSScrollingDockWindow entityEditorWindow = new MSScrollingDockWindow(entityEditor);
        dock.split(testPanelWindow, entityEditorWindow, DockingConstants.SPLIT_RIGHT);

        window.setVisible(true);
    }
}
