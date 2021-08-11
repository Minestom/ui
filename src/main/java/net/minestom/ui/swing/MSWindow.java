package net.minestom.ui.swing;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class MSWindow extends JFrame {
    static {
        // Allow dark mode on macOS.
        System.setProperty("apple.awt.application.appearance", "system");
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        FlatDarkLaf.setup();
    }

    public MSWindow(String name) {
        super(name);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //todo this should just be a default and use a saved version in the future.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocationRelativeTo(null); // Center the window

    }

    public void display() {
        setVisible(true);
    }

    public void setCentralWidget(Container container) {
        getContentPane().add(container);
    }
}
