package net.minestom.ui.swing.control;

import net.minestom.ui.swing.control.primitive.*;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MSControl {
    private MSControl() {}

    public static MSVec3Control Vec3() {
        return new MSVec3Control();
    }

    public static <T> MSDynamicControl<T> Dynamic(Class<T> type, Supplier<T> getter, Consumer<T> setter) {
        var control = MSDynamicControl.Registry.get(type, getter, setter);
        return control != null ? control : (MSDynamicControl<T>) new MSDynamicControl.Missing(type.getSimpleName());
    }

    public static <T> MSDynamicControl2<T> Dynamic(Class<T> type) {
        return MSDynamicControl2.getForTypeOrDefault(type);
    }



    static {
        MSDynamicControl2.register(int.class, MSIntControl::new);
        MSDynamicControl2.register(Integer.class, MSIntControl::new);
        MSDynamicControl2.register(float.class, MSFloatControl::new);
        MSDynamicControl2.register(Float.class, MSFloatControl::new);
        MSDynamicControl2.register(double.class, MSDoubleControl::new);
        MSDynamicControl2.register(Double.class, MSDoubleControl::new);
        MSDynamicControl2.register(boolean.class, MSBooleanControl::new);
        MSDynamicControl2.register(Boolean.class, MSBooleanControl::new);
        MSDynamicControl2.register(String.class, MSStringControl::new);
    }
}
