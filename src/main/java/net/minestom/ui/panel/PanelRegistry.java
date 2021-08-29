package net.minestom.ui.panel;

import net.minestom.server.utils.NamespaceID;
import net.minestom.ui.swing.panel.MSPanel;
import net.minestom.ui.window.MainWindow;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class PanelRegistry {
    private static final Map<String, Function<MainWindow, MSPanel>> registry = new HashMap<>();

    public static Collection<String> getAvailableIds() {
        return registry.keySet();
    }

    public static MSPanel createPanel(@NotNull String id, @Nullable MainWindow window) {
        var constructor = registry.get(id);
        if (constructor == null) return null;
        return constructor.apply(window);
    }

    public static void registerPanel(Class<? extends MSPanel> panelClass) {
        String id = panelClass.getName();
        try {
            var constructor = panelClass.getConstructor(MainWindow.class);
            registry.put(id, win -> {
                try {
                    return constructor.newInstance(win);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e); //todo error handling
                }
            });
        } catch (NoSuchMethodException ignored) {
            try {
                var constructor = panelClass.getConstructor();
                registry.put(id, win -> {
                    try {
                        return constructor.newInstance();
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e); //todo error handling
                    }
                });
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No acceptable constructor: " + panelClass.getName());
            }
        }
    }




    private PanelRegistry() {}
}
