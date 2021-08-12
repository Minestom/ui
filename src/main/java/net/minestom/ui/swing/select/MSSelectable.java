package net.minestom.ui.swing.select;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public interface MSSelectable<T> {

    void setSelected(boolean selected);

    @NotNull T get();

    @NotNull Container getBackingControl();

    interface Factory<T> {
        MSSelectable<T> create(T item);
    }
}
