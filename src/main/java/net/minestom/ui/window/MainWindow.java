package net.minestom.ui.window;

import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import net.minestom.server.MinecraftServer;
import net.minestom.ui.panel.EntityEditor;
import net.minestom.ui.panel.InstanceHierarchyPanel;
import net.minestom.ui.swing.MSWindow;
import net.minestom.ui.swing.panel.MSPanel;
import net.minestom.ui.swing.panel.MSScrollingDockWindow;
import net.minestom.ui.swing.util.JMenuBarBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends MSWindow {
    private final DockingDesktop dockView;
    private final Map<Class<? extends MSPanel>, MSPanel> panels = new HashMap<>();

    public MainWindow() {
        super("Minestom UI - \"" + MinecraftServer.getBrandName() + "\" " + MinecraftServer.VERSION_NAME);

        dockView = new DockingDesktop();
        setCentralWidget(dockView);


//        final TestPanel testPanel = new TestPanel();
//        final MSScrollingDockWindow testPanelWindow = new MSScrollingDockWindow(testPanel);
//        dockView.addDockable(testPanelWindow);

        final InstanceHierarchyPanel instanceHierarchyPanel = new InstanceHierarchyPanel(this);
        panels.put(InstanceHierarchyPanel.class, instanceHierarchyPanel);
        final MSScrollingDockWindow instanceHierarchyPanelWindow = new MSScrollingDockWindow(instanceHierarchyPanel);
        dockView.addDockable(instanceHierarchyPanelWindow);

        final EntityEditor entityEditor = new EntityEditor();
        panels.put(EntityEditor.class, entityEditor);
        final MSScrollingDockWindow entityEditorWindow = new MSScrollingDockWindow(entityEditor);
        dockView.split(instanceHierarchyPanelWindow, entityEditorWindow, DockingConstants.SPLIT_RIGHT);

        createMenuBar();
    }

    /**
     * Probably not a final api, though we do need something like this.
     *
     * See notes in {@link net.minestom.ui.annotation.Panel} on behavior.
     *
     * @param type
     * @param <T>
     * @return
     */
    @Nullable
    public <T extends MSPanel> T getPanel(Class<T> type) {
        //noinspection unchecked
        return (T) panels.get(type);
    }

    @NotNull
    public DockingDesktop getDockView() {
        return dockView;
    }

    private void createMenuBar() {
        // Can also have radio, checkbox, separator, submenu, icons (i think)
        JMenuBar menuBar = new JMenuBarBuilder()
                .menu("File")
                    .add()
                .menu("Edit")
                    .add()
                .menu("View")
                    .add()
                .menu("Window")
                    .item("Item 1")
                        .add()
                    .separator()
                    .item("Item 2")
                        .add()
                    .add()
                .menu("Develop")
                    .add()
                .build();

        setJMenuBar(menuBar);
    }
}
