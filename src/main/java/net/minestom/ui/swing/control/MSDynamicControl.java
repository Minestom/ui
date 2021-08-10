package net.minestom.ui.swing.control;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

//todo this whole system kinda sucks. Would like to improve it.
//     ended up being a bad port from the imgui version.
public interface MSDynamicControl<T>{

    void update(T value);

    class Missing extends JLabel implements MSDynamicControl<Object> {
        public Missing(String type) {
            setText(type);
        }
        public void update(Object value) { }
    }

    class Num<T> extends JSpinner implements MSDynamicControl<T> {
        private final SpinnerNumberModel model;

        public Num(Supplier<T> getter, Consumer<T> setter, Number min, Number max, Number step) {
            super();

            model = new SpinnerNumberModel(((Number) getter.get()).doubleValue(), min.doubleValue(), max.doubleValue(), step.doubleValue());
            setModel(model);

            addChangeListener(e -> setter.accept((T) model.getNumber()));

            setMinimumSize(new Dimension(50, getPreferredSize().height));
            setPreferredSize(new Dimension(200, getPreferredSize().height));
        }

        @Override
        public void update(T value) {
            //todo could use getter here?
            model.setValue(value);
        }

        public static class NInt extends Num<Integer> {
            public NInt(Supplier<Integer> getter, Consumer<Integer> setter) {
                super(getter, setter, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            }
        }

        public static class NDouble extends Num<Double> {
            public NDouble(Supplier<Double> getter, Consumer<Double> setter) {
                super(getter, setter, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1);
            }
        }
    }

    class DynamicBool extends JCheckBox implements MSDynamicControl<Boolean> {

        public DynamicBool(Supplier<Boolean> getter, Consumer<Boolean> setter) {
            super();

            setSelected(getter.get());
            addChangeListener(e -> setter.accept(isSelected()));
        }

        @Override
        public void update(Boolean value) {
            setSelected(value);
        }
    }


    // Dynamic type registry

    class Registry {
        @SuppressWarnings("rawtypes")
        private static final Map<Class<?>, BiFunction<Supplier, Consumer, ?>> registry = new HashMap<>();

        public static <T, C extends MSDynamicControl<T>> void register(Class<T> type, BiFunction<Supplier, Consumer, C> constructor) {
            registry.put(type, constructor);
        }

        static <T> @Nullable MSDynamicControl<T> get(Class<T> type, Supplier<?> getter, Consumer<?> setter) {
            var constructor = registry.get(type);
            if (constructor == null) return null;
            return (MSDynamicControl<T>) constructor.apply(getter, setter);
        }

        static {
            register(int.class, Num.NInt::new);
            register(Integer.class, Num.NInt::new);
            register(double.class, Num.NDouble::new);
            register(Double.class, Num.NDouble::new);

            register(boolean.class, DynamicBool::new);
            register(Boolean.class, DynamicBool::new);
        }
    }
}
