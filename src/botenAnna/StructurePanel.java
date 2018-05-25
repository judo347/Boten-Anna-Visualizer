package botenAnna;

import javax.swing.*;
import java.awt.*;

public class StructurePanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    private int numberOfHorizontalElements;
    private int numberOfVerticalElements;
    private int canvasSizeHorizontal;
    private int canvasSizeVertical;

    private Node mainNodeStructure;

    /** This is the content of the window that displays the tree structure. */
    public StructurePanel(Node mainNodeStructure) {
        this.mainNodeStructure = mainNodeStructure;

        //Get number of vertical and horizontal elements
        this.numberOfHorizontalElements = mainNodeStructure.getWidthOfTreeAsCount();
        this.numberOfVerticalElements = mainNodeStructure.getHeightOfTreeAsCount();

        //Calculate size of window
        this.canvasSizeHorizontal = mainNodeStructure.getWidthOfTreeGraphical();
        this.canvasSizeVertical = mainNodeStructure.getHeightOfTreeGraphical();

        //Canvas properties
        setBackground(BACKGROUND_COLOR);
        setSize(canvasSizeHorizontal, canvasSizeVertical);
        setVisible(true);
    }

    /** Recalculates the size of the content of the window.
     *  Is currently only used when the mode is set to collapsed. */
    public void recalculateSize() {
        //Get number of vertical and horizontal elements
        this.numberOfHorizontalElements = mainNodeStructure.getWidthOfTreeAsCount();
        this.numberOfVerticalElements = mainNodeStructure.getHeightOfTreeAsCount();

        //Calculate size of window
        this.canvasSizeHorizontal = mainNodeStructure.getWidthOfTreeGraphical();
        this.canvasSizeVertical = mainNodeStructure.getHeightOfTreeGraphical();

        //Canvas properties
        setSize(canvasSizeHorizontal, canvasSizeVertical);
        repaint();
    }

    /** Used to when painting. */
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Assign coordinates for nodes using initial coordinates of the root node
        int initialXCoordinate = canvasSizeHorizontal / 2;
        int initialYCoordinate = Node.VERTICAL_SPACING / 2;
        mainNodeStructure.setCoordinates(initialXCoordinate , initialYCoordinate , canvasSizeHorizontal);

        // Draw tree with recursion
        mainNodeStructure.drawTree(g2d);
    }

    public void toggleCollapseExpand() {
        mainNodeStructure.setTreeCollapsed(!mainNodeStructure.isCollapsed());
        recalculateSize();
    }

    public int getCanvasSizeHorizontal(){
        return canvasSizeHorizontal;
    }

    public int getCanvasSizeVertical(){
        return canvasSizeVertical;
    }
}
