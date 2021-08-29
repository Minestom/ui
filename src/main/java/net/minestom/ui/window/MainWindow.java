package net.minestom.ui.window;

import com.formdev.flatlaf.icons.FlatClearIcon;
import com.vlsolutions.swing.docking.DefaultDockableContainerFactory;
import com.vlsolutions.swing.docking.DockableContainerFactory;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import com.vlsolutions.swing.docking.ui.DockingUISettings;
import net.minestom.server.MinecraftServer;
import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.Settings;
import net.minestom.ui.panel.PanelRegistry;
import net.minestom.ui.panel.entity.EntityEditor;
import net.minestom.ui.panel.InstanceHierarchyPanel;
import net.minestom.ui.panel.nbt.NBTEditorPanel;
import net.minestom.ui.swing.MSFont;
import net.minestom.ui.swing.MSWindow;
import net.minestom.ui.swing.docking.MSDockableContainerFactory;
import net.minestom.ui.swing.panel.MSPanel;
import net.minestom.ui.swing.panel.MSScrollingDockWindow;
import net.minestom.ui.swing.util.JMenuBarBuilder;
import net.minestom.ui.swing.util.icon.MSCloseIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends MSWindow {
    public final static Logger logger = LoggerFactory.getLogger(MainWindow.class);

    private final DockingDesktop dockView;
    private final Map<Class<? extends MSPanel>, MSPanel> panels = new HashMap<>();

    public MainWindow() {
        super("Minestom UI - \"" + MinecraftServer.getBrandName() + "\" " + MinecraftServer.VERSION_NAME);

        // Replace vldocking icons
        DockingUISettings.getInstance().installUI();
        UIManager.put("DockViewTitleBar.titleFont", MSFont.Monospaced);
        UIManager.put("DockViewTitleBar.close", new MSCloseIcon(MSCloseIcon.DEFAULT));
        UIManager.put("DockViewTitleBar.close.rollover", new MSCloseIcon(MSCloseIcon.HOVERED));
        UIManager.put("DockViewTitleBar.close.pressed", new MSCloseIcon(MSCloseIcon.PRESSED));

        // Use custom container factory for floating windows (to make them native os windows)
//        DockableContainerFactory.setFactory(new MSDockableContainerFactory());

        // Create dock view
        dockView = new DockingDesktop();
        dockView.setName("main");
        setCentralWidget(dockView);

        //todo below

        final InstanceHierarchyPanel instanceHierarchyPanel = new InstanceHierarchyPanel(this);
        panels.put(InstanceHierarchyPanel.class, instanceHierarchyPanel);
        final MSScrollingDockWindow instanceHierarchyPanelWindow = new MSScrollingDockWindow(instanceHierarchyPanel);
        dockView.registerDockable(instanceHierarchyPanelWindow);

        final EntityEditor entityEditor = new EntityEditor();
        panels.put(EntityEditor.class, entityEditor);
        final MSScrollingDockWindow entityEditorWindow = new MSScrollingDockWindow(entityEditor);
        dockView.registerDockable(entityEditorWindow);

        final NBTEditorPanel nbtEditor = new NBTEditorPanel();
        panels.put(NBTEditorPanel.class, nbtEditor);
        final MSScrollingDockWindow nbtEditorWindow = new MSScrollingDockWindow(nbtEditor);
        dockView.registerDockable(nbtEditorWindow);

        // Load layout (or default)
        if (loadLayout()) {
            // Resizing here is a pretty ugly hack, otherwise the window is empty until resize (a revalidate/repaint didnt seem to solve it)
            setSize(getWidth() + 1, getHeight() + 1);
        } else {
            logger.info("Failed to find layout, creating default.");
            dockView.addDockable(instanceHierarchyPanelWindow);
            dockView.split(instanceHierarchyPanelWindow, entityEditorWindow, DockingConstants.SPLIT_RIGHT);
            dockView.split(entityEditorWindow, nbtEditorWindow, DockingConstants.SPLIT_RIGHT);
        }

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

    public void saveLayout() {
        Settings.saveLayout(dockView);
    }

    public boolean loadLayout() {
        return Settings.loadLayout(dockView);
    }

    private void createMenuBar() {
        // Can also have radio, checkbox, separator, submenu, icons (i think)
        var builder = new JMenuBarBuilder()
                .menu("File")
                    .add()
                .menu("Edit")
                    .add()
                .menu("View")
                    .add();

        var menuBuilder = builder.menu("Window");
        for (String panelId : PanelRegistry.getAvailableIds()) {
            menuBuilder = menuBuilder
                    .item(panelId)
                    .onClick(() -> {
                        MSPanel panel = PanelRegistry.createPanel(panelId, this);
                        MSScrollingDockWindow panelWindow = new MSScrollingDockWindow(panel);
                        dockView.addDockable(panelWindow);
                    })
                    .add();
        }

        builder = menuBuilder.add();
        builder.menu("Develop")
                .item("Save Layout [tmp]")
                    .onClick(this::saveLayout)
                    .add()
                .add()
                .build();

        setJMenuBar(builder.build());
    }
}
