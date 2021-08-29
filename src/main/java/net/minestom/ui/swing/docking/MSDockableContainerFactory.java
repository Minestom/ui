package net.minestom.ui.swing.docking;

import com.vlsolutions.swing.docking.*;

import java.awt.*;

public class MSDockableContainerFactory extends DockableContainerFactory {
    private final DefaultDockableContainerFactory delegate = new DefaultDockableContainerFactory();

    @Override
    public SingleDockableContainer createDockableContainer(Dockable dockable, ParentType parentType) {
        return delegate.createDockableContainer(dockable, parentType);
    }

    @Override
    public TabbedDockableContainer createTabbedDockableContainer() {
        return delegate.createTabbedDockableContainer();
    }

    @Override
    public FloatingDockableContainer createFloatingDockableContainer(Window owner) {
        if (owner instanceof Dialog) {
            return new FloatingDialog((Dialog) owner);
        } else {
            return new MSFloatingWindow((Frame) owner);
        }
    }

    @Override
    public DockViewTitleBar createTitleBar() {
        return delegate.createTitleBar();
    }
}
