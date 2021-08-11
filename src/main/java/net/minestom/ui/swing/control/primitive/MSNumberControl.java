package net.minestom.ui.swing.control.primitive;

import net.minestom.ui.swing.control.MSDynamicControl2;

import javax.swing.*;
import java.awt.*;

public abstract class MSNumberControl<T extends Number> extends MSDynamicControl2<T> {
    protected final SpinnerNumberModel model;
    private final JSpinner spinner;

    protected MSNumberControl(Number min, Number max, Number step) {
        model = new SpinnerNumberModel(0.0, min.doubleValue(), max.doubleValue(), step.doubleValue());

        spinner = new JSpinner(model);
        init(spinner);
    }

    @Override
    public void set(T newValue) {
        model.setValue(newValue);
    }

    @Override
    public Container getBackingControl() {
        return spinner;
    }
}
