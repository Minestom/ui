package net.minestom.ui.swing.control.primitive;

import net.minestom.ui.swing.control.MSDynamicControl2;

import javax.swing.*;
import java.awt.*;

public class MSBooleanControl extends MSDynamicControl2<Boolean> {
    private final JCheckBox checkBox = new JCheckBox();

    public MSBooleanControl() {
        init(checkBox);
        checkBox.addChangeListener(e -> fireChangeEvent(checkBox.isSelected()));
    }

    @Override
    public Boolean get() {
        return checkBox.isSelected();
    }

    @Override
    public void set(Boolean newValue) {
        checkBox.setSelected(newValue);
    }

    @Override
    public Container getBackingControl() {
        return checkBox;
    }
}
