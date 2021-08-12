package net.minestom.ui.swing.select;

import net.minestom.ui.swing.listener.MSMouseListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class MSSelectionContext<T> extends Container implements MSMouseListener {
    private static final Logger logger = LoggerFactory.getLogger(MSSelectionContext.class);

    private MSSelectable.Factory<T> factory;
    private BiFunction<T, T, Boolean> comparator;
    private Consumer<T> onSelect = (e) -> {};

    private final List<MSSelectable<T>> items = new ArrayList<>();
    public MSSelectable<T> selected = null;

    public MSSelectionContext() {
        this(MSSelectableLabel::new, Object::equals);
    }

    public MSSelectionContext(MSSelectable.Factory<T> factory) {
        this(factory, Object::equals);
    }

    public MSSelectionContext(BiFunction<T, T, Boolean> comparator) {
        this(MSSelectableLabel::new, comparator);
    }

    public MSSelectionContext(MSSelectable.Factory<T> factory, BiFunction<T, T, Boolean> comparator) {
        this.factory = factory;
        this.comparator = comparator;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addMouseListener(this);
    }

    @Nullable
    public T getSelection() {
        if (selected == null) return null;
        return selected.get();
    }

    public boolean setSelection(@Nullable T selection) {
        boolean accepted = true;

        if (this.selected != null) {
            this.selected.setSelected(false);
            this.selected = null;
        }

        if (selection != null) {
            var selectable = items.stream()
                    .filter(item -> comparator.apply(item.get(), selection))
                    .findFirst().orElse(null);
            if (selectable != null) {
                this.selected = selectable;
                selectable.setSelected(true);
            } else accepted = false;
        }

        if (accepted) {
            onSelect.accept(selection);
            logger.debug("Set selection to {}", selection);
        }
        return accepted;
    }

    public void setSelectableFactory(MSSelectable.Factory<T> factory) {
        this.factory = factory;
    }

    public void setItemComparator(BiFunction<T, T, Boolean> comparator) {
        this.comparator = comparator;
    }

    public void setSelectionHandler(Consumer<T> onSelect) {
        this.onSelect = onSelect;
    }

    public void clear() {
        items.clear();
        removeAll();

        resize();
    }

    public void addItem(T item) {
        var selectable = factory.create(item);
        items.add(selectable);
        add((Container) selectable);

        resize();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        var clicked = getComponentAt(e.getPoint());
        if (!(clicked instanceof MSSelectable<?>))
            return;
        e.consume();

        //noinspection unchecked
        var selectable = (MSSelectable<T>) clicked;
        if (selectable == selected) return; // No need to reselect

        if (selected != null)
            selected.setSelected(false);

        selected = selectable;
        selected.setSelected(true);

        //todo There is a duplicate logic here to set the selection. Combine them.
        onSelect.accept(selected.get());
        logger.debug("Set selection to {}", selected.get());
    }

    private void resize() {
        revalidate();
        repaint();

        firePropertyChange("minestom:resize", 0, 1);
    }




}
