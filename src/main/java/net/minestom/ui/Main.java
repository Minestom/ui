package net.minestom.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import net.minestom.ui.builtin.TestPanel;
import net.minestom.ui.panel2.MSScrollingDockWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
//        window.add(testPanelWindow);
        dock.addDockable(testPanelWindow);

        final com.mattworzala.minestom.ui.Main.MyTextEditor temp = new com.mattworzala.minestom.ui.Main.MyTextEditor("I am a name");
        dock.split(testPanelWindow, temp, DockingConstants.SPLIT_RIGHT);

//        final EntityInspector entityInspector = new EntityInspector();
//        window.add(entityInspector);

        window.setVisible(true);
    }
}
