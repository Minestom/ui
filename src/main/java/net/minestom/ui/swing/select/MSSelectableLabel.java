package net.minestom.ui.swing.select;

import net.minestom.ui.swing.MSFont;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;
import java.util.function.Function;

public class MSSelectableLabel<T> extends Box implements MSSelectable<T> {
    private final JLabel label;

    private final T item;

    public MSSelectableLabel(T item) {
        this(Objects::toString, item);
    }

    public MSSelectableLabel(Function<T, String> displayFn, T item) {
        super(BoxLayout.X_AXIS);
        this.item = item;

        // Selection color. Will only be visible when selected (see #setSelected)
        setBackground(new Color(74, 136, 199)); //todo keep track of this selection color somewhere logical
        addFocusListener(new FocusListener() {
            //todo background color should change when it loses a local focus. Not sure how flatlaf has implemented this (the focus events arent even being called)
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(new Color(74, 136, 199));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(new Color(11, 34, 52));
            }
        });

        label = new JLabel(displayFn.apply(item));
        label.setFont(MSFont.Default);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(label);

        add(Box.createHorizontalGlue());
    }

    @Override
    public void setSelected(boolean selected) {
        // There is a background color set, so setting to opaque will show it
        setOpaque(selected);

        repaint();
    }

    @Override
    public @NotNull T get() {
        return item;
    }

    @Override
    public @NotNull Container getBackingControl() {
        return label;
    }
}
