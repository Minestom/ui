package net.minestom.ui.swing.control;

import net.minestom.ui.swing.control.primitive.*;

public class MSControl {
    private MSControl() {}

    public static MSVec3Control Vec3() {
        return new MSVec3Control();
    }

    public static <T> MSDynamicControl<T> Dynamic(Class<T> type) {
        return MSDynamicControl.getForTypeOrDefault(type);
    }



    static {
        MSDynamicControl.register(int.class, MSIntControl::new);
        MSDynamicControl.register(Integer.class, MSIntControl::new);
        MSDynamicControl.register(float.class, MSFloatControl::new);
        MSDynamicControl.register(Float.class, MSFloatControl::new);
        MSDynamicControl.register(double.class, MSDoubleControl::new);
        MSDynamicControl.register(Double.class, MSDoubleControl::new);
        MSDynamicControl.register(boolean.class, MSBooleanControl::new);
        MSDynamicControl.register(Boolean.class, MSBooleanControl::new);
        MSDynamicControl.register(String.class, MSStringControl::new);
    }
}
