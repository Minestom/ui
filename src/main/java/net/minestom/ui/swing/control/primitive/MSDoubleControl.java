package net.minestom.ui.swing.control.primitive;

public class MSDoubleControl extends MSNumberControl<Double> {
    public MSDoubleControl() {
        super(-Double.MAX_VALUE, Double.MAX_VALUE, 0.1);
    }

    @Override
    public Double get() {
        return model.getNumber().doubleValue();
    }
}