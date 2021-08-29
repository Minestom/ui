package net.minestom.ui.swing.util.icon;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MSIcon implements Icon {
    private static final Logger logger = LoggerFactory.getLogger(MSIcon.class);

    private static final BufferedImage MissingImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
    private static final BufferedImage EmptyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    public static final MSIcon Empty = new MSIcon(EmptyImage);

    private final BufferedImage image;
    private final int width, height;

    public MSIcon(String imagePath) {
        this(imagePath, -1, -1);
    }

    public MSIcon(String imagePath, int width, int height) {
        BufferedImage image;
        try {
            if (imagePath.startsWith(":/")) {
                try (var stream = MSIcon.class.getResourceAsStream(imagePath.substring(1))) {
                    if (stream == null) throw new RuntimeException("Unable to locate or read packaged icon: '" + imagePath + "'");
                    image = ImageIO.read(stream);
                }
            } else throw new RuntimeException("Cannot load non-packaged icons currently.");
        } catch (Exception e) {
            logger.warn("Failed to load {}. Enable debug logging for more information.", imagePath);
            logger.debug("Icon load failure: ", e);
            image = MissingImage;
        }

        this.image = image;
        this.width = width == -1 ? image.getWidth() : width;
        this.height = height == -1 ? image.getHeight() : height;
    }

    private MSIcon(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(image, 0, 0, width, height, null);
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    static {
        for (int i = 0; i < 32 * 32; i++) {
            MissingImage.setRGB(i % 32, i / 32, i % 8 > 3 ? 0xFFFF00D7 : 0xFF000000);
        }
        EmptyImage.setRGB(0, 0, 0x00000000);
    }

    public static class Element extends Container {
        private final JLabel iconLabel;

        public Element(Dimension size, Icon icon) {
            setLayout(new BorderLayout());

            setMinimumSize(size);
            setPreferredSize(size);
            setMaximumSize(size);

            iconLabel = new JLabel(icon);
            add(iconLabel, BorderLayout.CENTER);
        }

        public void setIcon(@NotNull Icon icon) {
            iconLabel.setIcon(icon);
        }
    }
}
