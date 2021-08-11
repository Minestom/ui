package net.minestom.ui.swing.util;

import javax.swing.*;
import java.util.function.Consumer;

public class JMenuBarBuilder {
    private final JMenuBar menuBar = new JMenuBar();

    public JMenuBarBuilder() { }

    public JMenuBuilder<JMenuBarBuilder> menu(String name) {
        return new JMenuBuilder<>(name, this, (menu) -> menuBar.add((JMenu) menu));
    }

    public JMenuBar build() {
        return menuBar;
    }


    public static class JMenuBuilder<T> {
        private final JMenu menu;

        private final T owner;
        private final Consumer<JMenu> addFn;

        private JMenuBuilder(String name, T owner, Consumer<JMenu> addFn) {
            menu = new JMenu(name);
            this.owner = owner;
            this.addFn = addFn;
        }

        public JMenuItemBuilder<T> item(String name) {
            return new JMenuItemBuilder<T>(name, this);
        }

        public JMenuBuilder<T> separator() {
            menu.addSeparator();
            return this;
        }

        //todo handle menu events

        public T add() {
            addFn.accept(menu);
            return owner;
        }



    }

    public static class JMenuItemBuilder<T> {
        private final JMenuItem menuItem;

        private final JMenuBuilder<T> owner;

        private JMenuItemBuilder(String name, JMenuBuilder<T> owner) {
            menuItem = new JMenuItem(name);
            this.owner = owner;
        }

        public JMenuItemBuilder<T> shortcut(KeyStroke keyStroke) {
            menuItem.setAccelerator(keyStroke);
            return this;
        }

        public JMenuItemBuilder<T> onClick(Runnable action) {
            menuItem.addActionListener(ignored -> action.run());
            return this;
        }

        public JMenuBuilder<T> add() {
            owner.menu.add(menuItem);
            return owner;
        }
    }



}
