package botenAnna;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StructurePanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    private int numberOfHorizontalElements;
    private int numberOfVerticalElements;
    private int canvasSizeHorizontal;
    private int canvasSizeVertical;

    private Node mainNodeStructure;

    public StructurePanel(Node mainNodeStructure) {
        this.mainNodeStructure = mainNodeStructure;

        //Get number of vertical and horizontal elements
        this.numberOfHorizontalElements = mainNodeStructure.getWidthOfTree();
        this.numberOfVerticalElements = mainNodeStructure.getHeightOfTree();

        //Calculate size of window
        this.canvasSizeHorizontal = numberOfHorizontalElements * (Node.NODE_WIDTH + Node.HORIZONTAL_SPACING);
        this.canvasSizeVertical = numberOfVerticalElements * Node.NODE_HEIGHT + (numberOfVerticalElements - 1) * Node.VERTICAL_SPACING;

        //Canvas properties
        setBackground(BACKGROUND_COLOR);
        setSize(canvasSizeHorizontal, canvasSizeVertical);
        setVisible(true);
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Assign coordinates for nodes using initial coordinates of the root node
        int initialXCoordinate = canvasSizeHorizontal / 2;
        int initialYCoordinate = Node.VERTICAL_SPACING + Node.NODE_HEIGHT;
        mainNodeStructure.setCoordinates(initialXCoordinate , initialYCoordinate , canvasSizeHorizontal);

        // Draw tree with recursion
        mainNodeStructure.draw(g2d);
    }

    public int getCanvasSizeHorizontal(){
        return canvasSizeHorizontal;
    }

    public int getCanvasSizeVertical(){
        return canvasSizeVertical;
    }
}
