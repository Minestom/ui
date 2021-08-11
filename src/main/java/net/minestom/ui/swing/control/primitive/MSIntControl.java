package net.minestom.ui.swing.control.primitive;

public class MSIntControl extends MSNumberControl<Integer> {
    public MSIntControl() {
        super(Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
    }

    @Override
    public Integer get() {
        return model.getNumber().intValue();
    }
}
