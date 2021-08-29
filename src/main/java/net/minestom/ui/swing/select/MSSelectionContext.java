package net.minestom.ui.swing.select;

import net.minestom.ui.swing.util.MSMouseListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
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

/**
 * Creates a list of items which supports a custom item renderer and managed selection.
 *
 * @param <T> The selectable type in this selection context
 */
public class MSSelectionContext<T> extends Container implements MSMouseListener {
    private static final Logger logger = LoggerFactory.getLogger(MSSelectionContext.class);

    // Flags
    public static final int NONE = 0;
    public static final int MULTI_SELECT = 0x1;

    private final int opts;
    private final MSSelectable.Factory<T> factory;
    private final List<MSSelectable<T>> items = new ArrayList<>();
    private MSSelectable<T> selection = null;

    private BiFunction<T, T, Boolean> comparator = Object::equals;
    private Consumer<T> selectionHandler = (e) -> {};

    /**
     * Creates a selection context using the default selectable label and no extra options
     *
     * @see MSSelectableLabel
     */
    public MSSelectionContext() {
        this(MSSelectableLabel::new, NONE);
    }

    /**
     * Creates a selection context using the default selectable label.
     *
     * @param opts Extra options for the selection context
     *
     * @see MSSelectableLabel
     */
    public MSSelectionContext(int opts) {
        this(MSSelectableLabel::new, opts);
    }

    /**
     * Creates a selection context using a custom selection factory. Typically used for a custom renderer.
     *
     * @param factory A factory for the desired {@link MSSelectable<T>}
     */
    public MSSelectionContext(@NotNull MSSelectable.Factory<T> factory) {
        this(factory, NONE);
    }

    /**
     * Creates a selection context using a custom selection factory. Typically used for a custom renderer.
     *
     * @param factory A factory for the desired {@link MSSelectable<T>}
     * @param opts Extra options for the selection context
     */
    public MSSelectionContext(@NotNull MSSelectable.Factory<T> factory, int opts) {
        this.factory = factory;
        this.opts = opts;

        if (isMultiSelect())
            throw new IllegalStateException("Multi select not currently supported.");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addMouseListener(this);
    }

    /**
     * @return The currently selected item, or none if nothing is selected.
     */
    @Nullable
    public T getSelection() {
        if (selection == null) return null;
        return selection.get();
    }

    /**
     * Sets the new selection, including deselecting a previous selection and calling relevant handlers.
     * <p>
     * If the item is not in this selection list, false is returned.
     *
     * @param selection The new selection
     * @return True if the item is now selected
     */
    public boolean setSelection(@Nullable T selection) {
        if (selection == null) {
            // Always accept a null selection
            changeSelection(null);
        } else {
            final var newSelectable = items.stream()
                    .filter(item -> comparator.apply(item.get(), selection))
                    .findFirst().orElse(null);

            if (newSelectable == null)
                return false; // Cannot select an item not managed by the context

            changeSelection(newSelectable);
        }
        return true;
    }

    /**
     * Removes all items from this selection context.
     * <p>
     * Selection is not preserved if the selected item is added again later.
     */
    public void clear() {
        setSelection(null);
        items.clear();
        removeAll();

        resize();
    }

    /**
     * Adds a new item to the end of the context.
     *
     * @param item The item to be added
     */
    public void addItem(T item) {
        var selectable = factory.create(item);
        items.add(selectable);
        add((Container) selectable);

        resize();
    }

    /**
     * Replaces the current items with a new set.
     * <p>
     * Selection is preserved if the current selection still exists in the new set of items.
     *
     * @param items The new set of items
     * @see #setItemComparator(BiFunction)
     */
    public void setItems(List<T> items) {
        this.items.clear();
        removeAll();
        items.stream()
                .map(factory::create)
                .forEach(item -> {
                    this.items.add(item);
                    add((Container) item);
                });

        // Attempt to preserve selection (if there was one)
        if (selection == null || !setSelection(selection.get())) {
            // Old selection is no longer present, deselect.
            setSelection(null);
        }

        resize();
    }

    public void setItemComparator(@NotNull BiFunction<T, T, Boolean> comparator) {
        this.comparator = comparator;
    }

    public void setSelectionHandler(@NotNull Consumer<T> onSelect) {
        this.selectionHandler = onSelect;
    }

    public boolean isMultiSelect() {
        return (opts & MULTI_SELECT) == MULTI_SELECT;
    }

    // Implementation details
    // ======================

    private void changeSelection(@Nullable MSSelectable<T> newSelection) {
        // If it was null and new selection is null then do nothing
        if (selection == null && newSelection == null) return;
        // If the old and new are referentially equal, do nothing
        if (selection == newSelection) return;

        // If we are setting a new selection, we need to check if the current selection has the same item
        if (selection != null && newSelection != null &&
                comparator.apply(selection.get(), newSelection.get())) {
            // Replace the selection but do not trigger a selection change
            selection = newSelection;
            newSelection.setSelected(true);
            return;
        }

        // Visually deselect old selection
        if (selection != null)
            selection.setSelected(false);

        if (newSelection != null) {
            // Set new selection
            selection = newSelection;
            newSelection.setSelected(true);

            // Notify handler that selection has changed
            selectionHandler.accept(selection.get());
            logger.debug("Set selection to {}", selection.get());
        } else {
            // Null selection
            selection = null;

            // Notify handler that selection has changed
            selectionHandler.accept(null);
            logger.debug("Set selection to null");
        }
    }

    @ApiStatus.Internal
    @Override
    public void mouseClicked(MouseEvent e) {
        var clicked = getComponentAt(e.getPoint());
        if (!(clicked instanceof MSSelectable<?>))
            return;

        //noinspection unchecked
        changeSelection((MSSelectable<T>) clicked);
        e.consume();
    }

    private void resize() {
        revalidate();
        repaint();

        firePropertyChange("minestom:resize", 0, 1);
    }
}
