package net.minestom.ui.swing.control;

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
}
