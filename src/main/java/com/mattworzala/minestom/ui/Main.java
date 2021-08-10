package com.mattworzala.minestom.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.appearance", "system");

        FlatDarculaLaf.setup();


        MyFirstFrame frame = new MyFirstFrame();
        frame.setTitle("My Frame");

        frame.setVisible(true);
    }

    public static class MyFirstFrame extends JFrame {
        MyTextEditor textArea1 = new MyTextEditor("First Area");
        MyTextEditor textArea2 = new MyTextEditor("Second Area");
        MyTextEditor textArea3 = new MyTextEditor("Third Area");
        MyTextEditor textArea4 = new MyTextEditor("Forth Area");

        DockingDesktop desktop = new DockingDesktop();

        public MyFirstFrame() {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            getContentPane().add(desktop);

            desktop.addDockable(textArea1);
            desktop.split(textArea1, textArea2, DockingConstants.SPLIT_LEFT);
            desktop.split(textArea1, textArea3, DockingConstants.SPLIT_RIGHT);
            desktop.split(textArea3, textArea4, DockingConstants.SPLIT_BOTTOM);
        }
    }

    public static class MyTextEditor extends JPanel implements Dockable {
        DockKey key;

        JTextArea textArea;

        public MyTextEditor(String name) {
            key = new DockKey(name);
            textArea = new JTextArea(name);

            key.setMaximizeEnabled(false);
            key.setFloatEnabled(false);


            setLayout(new BorderLayout());
            JScrollPane jsp = new JScrollPane(textArea);
            jsp.setPreferredSize(new Dimension(200, 400));
            add(jsp, BorderLayout.CENTER);
        }

        @Override
        public DockKey getDockKey() {
            return key;
        }

        @Override
        public Component getComponent() {
            return this;
        }
    }
}
