package net.minestom.ui.swing.control.renderer;

import net.minestom.ui.util.StringUtil;

import javax.swing.*;
import java.awt.*;

public class MSEnumComboBoxRenderer extends JLabel implements ListCellRenderer<Enum<?>> {
    public MSEnumComboBoxRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Enum<?>> list, Enum<?> value, int index, boolean isSelected, boolean cellHasFocus) {
        // Adopted from BasicComboBoxRenderer

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setFont(list.getFont());
        setText(StringUtil.toHumanReadable(value));

        return this;
    }
}
