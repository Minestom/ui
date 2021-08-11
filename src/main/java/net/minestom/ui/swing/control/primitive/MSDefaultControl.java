package net.minestom.ui.swing.control.primitive;

import net.minestom.ui.swing.control.MSDynamicControl2;

import javax.swing.*;
import java.awt.*;

public class MSDefaultControl extends MSDynamicControl2<Object> {
    private final JLabel label;

    public MSDefaultControl(Class<?> type) {
        label = new JLabel(type.getSimpleName());
        init(label);
    }

    @Override
    public Object get() { return null; }

    @Override
    public void set(Object newValue) { }

    @Override
    public Container getBackingControl() {
        return label;
    }
}
