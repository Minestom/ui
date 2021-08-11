package net.minestom.ui.swing.control.primitive;

import net.minestom.ui.swing.control.MSDynamicControl;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class MSStringControl extends MSDynamicControl<String> {
    private final JTextField textField = new JTextField();

    public MSStringControl() {
        init(textField);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fireChangeEvent(textField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fireChangeEvent(textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fireChangeEvent(textField.getText());
            }
        });
    }

    @Override
    public String get() {
        return textField.getText();
    }

    @Override
    public void set(String newValue) {
        textField.setText(newValue);
    }

    public void setClientProperty(String property, String value) {
        textField.putClientProperty(property, value);
    }

    public void removeClientProperty(String property) {
        textField.putClientProperty(property, null);
    }

    @Override
    public Container getBackingControl() {
        return textField;
    }
}
