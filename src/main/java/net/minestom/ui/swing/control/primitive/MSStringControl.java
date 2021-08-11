package net.minestom.ui.swing.control.primitive;

import net.minestom.ui.swing.control.MSDynamicControl;

import javax.swing.*;
import java.awt.*;

public class MSStringControl extends MSDynamicControl<String> {
    private final JTextField textField = new JTextField();

    public MSStringControl() {
        init(textField);
        textField.addActionListener(e -> fireChangeEvent(textField.getText()));
    }

    @Override
    public String get() {
        return textField.getText();
    }

    @Override
    public void set(String newValue) {
        textField.setText(newValue);
    }

    @Override
    public Container getBackingControl() {
        return textField;
    }
}
