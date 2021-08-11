package net.minestom.ui.panel;

import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.swing.panel.MSPanel;
import net.minestom.ui.swing.MSToggleView;

import javax.swing.*;

public class TestPanel extends MSPanel {
    public TestPanel() {
        super(NamespaceID.from("minestom", "test_panel"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        MSToggleView toggleView = new MSToggleView("Toggle View A", false);

        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        add(toggleView);
        toggleView = new MSToggleView("Another Toggle View", false);
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        add(toggleView);
        toggleView = new MSToggleView("Toggle View with inner", false);
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        add(toggleView);
        MSToggleView toggleView2 = new MSToggleView("INNER Toggle View", false);
        toggleView2.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView2.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView2.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView2.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView2.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView2.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(toggleView2);
        toggleView = new MSToggleView("Third Toggle View", false);
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        add(toggleView);
        toggleView = new MSToggleView("Third Toggle View", false);
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        toggleView.addChild(new JLabel("ABIJHCWYIUAHFIU"));
        add(toggleView);
    }
}
