package botenAnna;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScrollDrag extends JPanel {

    private int sizeW, sizeY;

    /**
     * This method adds a scroll panel to the window and the feature to drag using the mouse.
     * @param panel Which JPanel to add this feature to.
     * @param fullSizeWidth The full width size of the whole panel
     * @param fullSizeHeight The full height size of the whole panel
     */
    public ScrollDrag(JPanel panel, int fullSizeWidth, int fullSizeHeight) {

        // Store size variables so that it can be used in other methods
        this.sizeW = fullSizeWidth;
        this.sizeY = fullSizeHeight;

        setLayout(new BorderLayout());

        add(new JScrollPane(panel));

        MouseAdapter ma = new MouseAdapter() {

            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, panel);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        panel.scrollRectToVisible(view);
                    }
                }
            }

        };

        panel.addMouseListener(ma);
        panel.addMouseMotionListener(ma);
        panel.setPreferredSize(getPreferredSize());

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(sizeW, sizeY+200);
    }
}