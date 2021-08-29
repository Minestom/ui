package net.minestom.ui.swing.docking;

import com.vlsolutions.swing.docking.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MSFloatingWindow extends JFrame implements FloatingDockableContainer {

    private static final long serialVersionUID = 1L;

    // constants to listen to border drags
    private static final int DRAG_TOP = 1;

    private static final int DRAG_LEFT = 2;

    private static final int DRAG_RIGHT = 4;

    private static final int DRAG_BOTTOM = 8;

    /** the drag header height */
    protected int titleHeight = 10/* 6 */;

    private Color controlColor = UIManager.getColor("control");

    private Color highlight = UIManager.getColor("VLDocking.highlight");

    private Color shadow = UIManager.getColor("VLDocking.shadow");

    private Color activeCaptionColor = UIManager.getColor("activeCaption");

    private Color inactiveCaptionColor = UIManager.getColor("inactiveCaption");

    private Color activeCaptionBorderColor = UIManager.getColor("activeCaptionBorder");

    private Color inactiveCaptionBorderColor = UIManager.getColor("inactiveCaptionBorder");

    private Border activeBorder = BorderFactory.createLineBorder(activeCaptionBorderColor);

    private Border inactiveBorder = BorderFactory.createLineBorder(inactiveCaptionBorderColor);

    private JComponent title = createTitlePanel();

    private Resizer left = new Resizer();

    private Resizer right = new Resizer();

    private Resizer bottom = new Resizer();

    protected DockingDesktop desktop;

    public MSFloatingWindow(Frame parent) {
        init();
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    }

    /** initialisation of the title bar */
    public void init() {
//        installDecoration();
//        installBorders();
//        installResizers();
//
//        addWindowListener(new WindowAdapter() {
//
//            public void windowActivated(WindowEvent e) {
//                getRootPane().setBorder(getActiveBorder());
//                repaint();
//            }
//
//            public void windowDeactivated(WindowEvent e) {
//                getRootPane().setBorder(getInactiveBorder());
//                repaint();
//            }
//        });
//
//        title.setPreferredSize(new Dimension(10, titleHeight));
//
//        getContentPane().add(title, BorderLayout.NORTH);
    }

    /**
     * Installs or not the window decorations on this dialog.
     * <p>
     * uses setUndecorated(booelan)
     */
    public void installDecoration() {
        setUndecorated(true);
    }

    /** Creates the component used as a header to drag the window */
    public JComponent createTitlePanel() {
        return new TitlePanel();
    }

    /** Installs the borders of this dialog and its title header */
    public void installBorders() {
        Border border = UIManager.getBorder("FloatingDialog.dialogBorder");
        Border titleBorder = UIManager.getBorder("FloatingDialog.titleBorder");
        title.setBorder(titleBorder);
        ((JPanel) getContentPane()).setBorder(border);
    }

    /**
     * installs the components used for resizing (on left/right/bottom borders)
     *
     * @since 2.0.1
     */
    public void installResizers() { // 2005/10/06
        ResizeListener listener = new ResizeListener();
        left.addMouseMotionListener(listener);
        left.addMouseListener(listener);
        right.addMouseMotionListener(listener);
        right.addMouseListener(listener);
        bottom.addMouseMotionListener(listener);
        bottom.addMouseListener(listener);
        title.addMouseMotionListener(listener);
        title.addMouseListener(listener);
        getContentPane().add(left, BorderLayout.WEST);
        getContentPane().add(right, BorderLayout.EAST);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        Color inactive = UIManager.getColor("inactiveCaption");
        left.setBackground(inactive);
        right.setBackground(inactive);
        bottom.setBackground(inactive);

    }

    public void installDocking(DockingDesktop desktop) {
        this.desktop = desktop;
    }

    public void setInitialDockable(Dockable dockable) {
        SingleDockableContainer sdc =
                DockableContainerFactory.getFactory().createDockableContainer(dockable,
                        DockableContainerFactory.ParentType.PARENT_DETACHED_WINDOW);
        sdc.installDocking(desktop);

        Component comp = (Component) sdc;

        getContentPane().add(comp);

    }

    public void setInitialTabbedDockableContainer(TabbedDockableContainer tdc) {
        Component comp = (Component) tdc;

        getContentPane().add(comp);

    }

    // utility resizer class
    private class ResizeListener implements MouseMotionListener, MouseListener {

        Point lastPoint = null;

        int dragType = -1;

        public void mouseDragged(MouseEvent e) {
            Rectangle bounds = getBounds();
            Dimension d = getSize();
            Point p = e.getPoint();
            SwingUtilities.convertPointToScreen(p, e.getComponent());
            int dx = p.x - lastPoint.x;
            int dy = p.y - lastPoint.y;

            switch (dragType) {
                case DRAG_TOP:
                    Point loc = getLocation();
                    setLocation(loc.x + dx, loc.y + dy);
                    break;
                case DRAG_TOP | DRAG_LEFT:
                    invalidate();
                    bounds.x += dx;
                    bounds.y += dy;
                    bounds.width -= dx;
                    bounds.height -= dy;
                    setBounds(bounds);
                    validate();
                    break;
                case DRAG_TOP | DRAG_RIGHT:
                    invalidate();
                    bounds.y += dy;
                    bounds.width += dx;
                    bounds.height -= dy;
                    setBounds(bounds);
                    validate();
                    break;
                case DRAG_RIGHT:
                    invalidate();
                    setSize(d.width + dx, d.height);
                    validate();
                    break;
                case DRAG_RIGHT | DRAG_BOTTOM:
                    invalidate();
                    setSize(d.width + dx, d.height + dy);
                    validate();
                    break;
                case DRAG_BOTTOM:
                    invalidate();
                    setSize(d.width, d.height + dy);
                    validate();
                    break;
                case DRAG_BOTTOM | DRAG_LEFT:
                    invalidate();
                    bounds.x += dx;
                    bounds.width -= dx;
                    bounds.height += dy;
                    setBounds(bounds);
                    validate();
                    break;
                case DRAG_LEFT:
                    invalidate();
                    bounds.x += dx;
                    bounds.width -= dx;
                    setBounds(bounds);
                    validate();
                    break;
            }

            lastPoint = p;

        }

        public void mouseReleased(MouseEvent e) {
            dragType = -1;
        }

        public void mousePressed(MouseEvent e) {
            dragType = 0;
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), getContentPane());
            /*
             * if (p.y < 10){ dragType |= DRAG_TOP; } if (p.x < 5){ dragType |= DRAG_LEFT; } if (p.x > getWidth() - 5){
             * dragType |= DRAG_RIGHT; } if (p.y > getHeight() - 5){ dragType |= DRAG_BOTTOM; }
             */
            if (p.y < title.getY() + title.getHeight()) {
                dragType |= DRAG_TOP;
            }
            if (p.x < left.getX() + left.getWidth()) {
                dragType |= DRAG_LEFT;
            }
            if (p.x >= right.getX()) {// getWidth() - 5){
                dragType |= DRAG_RIGHT;
            }
            if (p.y >= bottom.getY()) {
                dragType |= DRAG_BOTTOM;
            }

            lastPoint = e.getPoint();
            SwingUtilities.convertPointToScreen(lastPoint, e.getComponent());
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), getContentPane());

            int dragType = 0;

            if (p.y < title.getY() + title.getHeight()) {
                dragType |= DRAG_TOP;
            }
            if (p.x < left.getX() + left.getWidth()) {
                dragType |= DRAG_LEFT;
            }
            if (p.x >= right.getX()) {// getWidth() - 5){
                dragType |= DRAG_RIGHT;
            }
            if (p.y >= bottom.getY()) {
                dragType |= DRAG_BOTTOM;
            }
            Cursor c = null;
            switch (dragType) {
                case 0:
                    c = Cursor.getDefaultCursor();
                    break;
                case DRAG_TOP:
                    c = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
                    break;
                case DRAG_TOP | DRAG_LEFT:
                    c = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
                    break;
                case DRAG_TOP | DRAG_RIGHT:
                    c = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                    break;
                case DRAG_RIGHT:
                    c = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                    break;
                case DRAG_RIGHT | DRAG_BOTTOM:
                    c = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                    break;
                case DRAG_BOTTOM:
                    c = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
                    break;
                case DRAG_BOTTOM | DRAG_LEFT:
                    c = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                    break;
                case DRAG_LEFT:
                    c = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
                    break;
            }
            if (!e.getComponent().getCursor().equals(c)) {
                e.getComponent().setCursor(c);
            }
        }
    }

    /** Class used as a title for dragging the window around */
    protected class TitlePanel extends JComponent {

        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics g) {
            // @todo : this will be refactored to plug custom title rendering
            Color darker, brighter;
            if (isActive()) {
                darker = UIManager.getColor("activeCaption");
                brighter = UIManager.getColor("activeCaptionBorder");
            }
            else {
                darker = UIManager.getColor("inactiveCaption");
                brighter = UIManager.getColor("inactiveCaptionBorder");
            }
            /*
             * Graphics2D g2 = (Graphics2D) g; Paint paint = g2.getPaint(); GradientPaint gradient = new
             * GradientPaint(0,0, darker, 0, getHeight(), brighter); g2.setPaint(gradient); g2.fillRect(0,0, getWidth(),
             * getHeight()); g2.setPaint(paint); // restore
             */
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(darker);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int width = 5;
            int height = titleHeight - 1;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics ig = image.createGraphics();
            ig.setColor(darker);
            ig.fillRect(0, 0, width, height);
            ig.setColor(new Color(1, 1, 1, 0.5f));
            ig.fillRect(2, 2, 2, titleHeight - 4);
            ig.setColor(new Color(0, 0, 0, 0.2f));
            ig.fillRect(1, 1, 2, titleHeight - 4);
            ig.dispose();

            // Create a texture paint from the buffered image
            Rectangle r = new Rectangle(0, 0, width, height);
            TexturePaint tp = null;
            if (tp == null) {
                tp = new TexturePaint(image, r);
            }

            Paint old = g2.getPaint();
            g2.setPaint(tp);
            g2.fillRect(0, 0, getWidth(), height);
            g2.setPaint(old);

            // g2.setColor(hightLight);
            // g2.fillRect(0,getHeight()-1, getWidth(), 1);

        }
    }

    protected class Resizer extends JComponent {

        private static final long serialVersionUID = 1L;

        Resizer() {
            setPreferredSize(new Dimension(3, 3));
            setOpaque(true);
        }

        public void paintComponent(Graphics g) {
            if (isActive()) {
                g.setColor(UIManager.getColor("activeCaption"));
            }
            else {
                g.setColor(UIManager.getColor("inactiveCaption"));
            }
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public Border getInactiveBorder() {
        return inactiveBorder;
    }

    public Border getActiveBorder() {
        return activeBorder;
    }

}
