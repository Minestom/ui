package net.minestom.ui;

import com.vlsolutions.swing.docking.DockingDesktop;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {
    public final static Logger logger = LoggerFactory.getLogger(Settings.class);

    private static Path dataDirectory;

    public static Path getDataDirectory() {
        return dataDirectory;
    }

    @ApiStatus.Internal
    public static void init(@NotNull Path workingDirectory) {
        dataDirectory = workingDirectory.resolve(".msui").toAbsolutePath();
        logger.trace("Loading settings from: {}", dataDirectory);

        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }
        } catch (IOException e) {
            logger.warn("Failed to create data directory:", e);
            logger.warn("Some services will not work as expected.");
            dataDirectory = null;
            return;
        }

    }

    @ApiStatus.Internal
    public static boolean loadLayout(@NotNull DockingDesktop dockView) {
        if (dataDirectory == null) {
            logger.warn("Cannot load layout: missing data directory");
            return false;
        }

        Path target = getDataDirectory().resolve(String.format("layout-%s.xml", dockView.getName()));
        logger.trace("Loading layout file: {}", target);

        if (!Files.exists(target)) {
            logger.trace("Layout file not found: {}", target);
            return false;
        }

        try (InputStream is = Files.newInputStream(target)) {
            dockView.readXML(is);
            return true;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            logger.warn("Unable to load layout file: {}", target, e);
            return false;
        }
    }

    @ApiStatus.Internal
    public static void saveLayout(@NotNull DockingDesktop dockView) {
        if (dataDirectory == null) {
            logger.warn("Cannot save layout: missing data directory");
            return;
        }

        Path target = getDataDirectory().resolve(String.format("layout-%s.xml", dockView.getName()));
        logger.trace("Saving layout file: {}", target);

        try (OutputStream os = Files.newOutputStream(target)) {
            dockView.writeXML(os);
        } catch (IOException e) {
            logger.warn("Unable to save layout file: {}", target, e);
        }
    }

    private Settings() {}
}
