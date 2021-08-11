package net.minestom.ui.swing.control;

import net.minestom.ui.swing.control.primitive.MSDefaultControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;

public abstract class MSDynamicControl2<T> extends Container {
    private final List<ValueChangeListener<T>> changeListeners = new ArrayList<>();

    public abstract T get();
    public abstract void set(T newValue);

    public void addChangeListener(ValueChangeListener<T> listener) {
        changeListeners.add(listener);
    }

    public abstract Container getBackingControl();

    protected void init(Container backing) {
        setLayout(new BorderLayout());
        add(backing, BorderLayout.CENTER);
    }

    protected void fireChangeEvent(T newValue) {
        changeListeners.forEach(listener -> listener.onValueChange(newValue));
    }

    @FunctionalInterface
    public interface ValueChangeListener<T> {
        void onValueChange(T newValue);
    }

    // Dynamic control registry

    private static final Map<Class<?>, Supplier<MSDynamicControl2<?>>> controls = new HashMap<>();

    @Nullable
    public static <T> MSDynamicControl2<T> getForType(Class<T> type) {
        return (MSDynamicControl2<T>) Optional.ofNullable(controls.get(type))
                .map(Supplier::get)
                .orElse(null);
    }

    @NotNull
    public static <T> MSDynamicControl2<T> getForTypeOrDefault(Class<T> type) {
        var control = getForType(type);
        return control != null ? control : (MSDynamicControl2<T>) new MSDefaultControl(type);
    }

    public static <T> void register(Class<T> type, Supplier<MSDynamicControl2<?>> constructor) {
        controls.put(type, constructor);
    }

}
