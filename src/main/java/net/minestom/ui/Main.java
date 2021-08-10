package net.minestom.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import net.minestom.ui.builtin.EntityInspector;

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

        final EntityInspector entityInspector = new EntityInspector();
        window.add(entityInspector);

        window.setVisible(true);
    }
}
