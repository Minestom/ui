package net.minestom.ui.swing;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class MSFont {
    private MSFont() {
    }

    public static Font Default = null;
    public static Font Monospaced = null;

    public static Font Subtitle = null;
    public static Font SubtitleMono = null;

    public static void init() {
        if (Default != null) return;
        boolean isMacOS = System.getProperty("os.name").equals("Mac OS X");
        if (isMacOS) {
            Default = new Font("SF Pro", Font.PLAIN, 15);
            Monospaced = new Font("SF Mono", Font.PLAIN, 15);
            //todo should load the default fonts from resources
        } else {
            //todo (ubuntu not available on most, could include or just use a common font)
            Default = new Font("Ubuntu", Font.PLAIN, 15);
            Monospaced = new Font("Ubuntu Mono", Font.PLAIN, 15);
        }

        Subtitle = Default.deriveFont(12.0f);
        SubtitleMono = Monospaced.deriveFont(12.0f);

        // Globally set the font
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, Default);
        }
    }
}
