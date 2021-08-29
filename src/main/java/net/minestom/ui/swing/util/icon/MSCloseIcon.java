package net.minestom.ui.swing.util.icon;

import com.formdev.flatlaf.icons.FlatAbstractIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

// Based on FlatLAF FlatClearIcon
public class MSCloseIcon extends FlatAbstractIcon {
    public static final int DEFAULT = 0;
    public static final int HOVERED = 1;
    public static final int PRESSED = 2;

    protected Color clearIconColor = UIManager.getColor("SearchField.clearIconColor");
    protected Color clearIconHoverColor = UIManager.getColor("SearchField.clearIconHoverColor");
    protected Color clearIconPressedColor = UIManager.getColor("SearchField.clearIconPressedColor");

    private final int state;

    public MSCloseIcon(@MagicConstant int state) {
        super(16, 16, null);
        this.state = state;
    }

    @Override
    protected void paintIcon(Component c, Graphics2D g) {
        if (state != DEFAULT) {
				/*
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16">
					  <path fill="#7F8B91" fill-opacity=".5" fill-rule="evenodd" d="M8,1.75 C11.4517797,1.75 14.25,4.54822031 14.25,8 C14.25,11.4517797 11.4517797,14.25 8,14.25 C4.54822031,14.25 1.75,11.4517797 1.75,8 C1.75,4.54822031 4.54822031,1.75 8,1.75 Z M10.5,4.5 L8,7 L5.5,4.5 L4.5,5.5 L7,8 L4.5,10.5 L5.5,11.5 L8,9 L10.5,11.5 L11.5,10.5 L9,8 L11.5,5.5 L10.5,4.5 Z"/>
					</svg>
				*/

            // paint filled circle with cross
            g.setColor(state == PRESSED ? clearIconPressedColor : clearIconHoverColor);
            Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
            path.append(new Ellipse2D.Float(1.75f, 1.75f, 12.5f, 12.5f), false);
            path.append(FlatUIUtils.createPath(4.5, 5.5, 5.5, 4.5, 8, 7, 10.5, 4.5, 11.5, 5.5, 9, 8, 11.5, 10.5, 10.5, 11.5, 8, 9, 5.5, 11.5, 4.5, 10.5, 7, 8), false);
            g.fill(path);
            return;
        }

		/*
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16">
			  <path fill="none" stroke="#7F8B91" stroke-linecap="square" stroke-opacity=".5" d="M5,5 L11,11 M5,11 L11,5"/>
			</svg>
		*/

        // paint cross
        g.setColor(clearIconColor);
        Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
        path.append(new Line2D.Float(5, 5, 11, 11), false);
        path.append(new Line2D.Float(5, 11, 11, 5), false);
        g.draw(path);
    }
}
