package net.minestom.ui.swing.control.primitive;

public class MSFloatControl extends MSNumberControl<Float> {
    public MSFloatControl() {
        super(-Float.MAX_VALUE, Float.MAX_VALUE, 0.1);
    }

    @Override
    public Float get() {
        return model.getNumber().floatValue();
    }
}
