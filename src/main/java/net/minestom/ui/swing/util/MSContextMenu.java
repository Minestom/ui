package net.minestom.ui.swing.util;

import net.minestom.ui.swing.listener.MSMouseListener;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MSContextMenu extends JPopupMenu implements MouseListener {
    private final Container eventTarget;

    /**
     * todo
     * @param owner
     * @param eventTarget A target to forward unhandled mouse events
     */
    public MSContextMenu(@Nullable Container owner, @Nullable Container eventTarget) {
        this.eventTarget = eventTarget;
        if (owner != null)
            owner.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        forwardEvent(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!tryShowPopup(e)) {
            forwardEvent(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!tryShowPopup(e)) {
            forwardEvent(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        forwardEvent(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        forwardEvent(e);
    }

    private boolean tryShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            show(e.getComponent(), e.getX(), e.getY());
            return true;
        }
        return false;
    }

    private void forwardEvent(MouseEvent e) {
        if (eventTarget != null) {
            // This is to attempt to put the event in the correct coordinate space
            // however this is only valid if `eventTarget` is a "direct" parent.
            // Probably need a better way.
            var location = e.getComponent().getLocation();
            e.translatePoint(location.x, location.y);

            eventTarget.dispatchEvent(e);
        }
    }
}
