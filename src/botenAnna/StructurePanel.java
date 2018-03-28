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
    final int verticalSpace;
    final int horizontalSpace;
    private int nodeWidth;
    private int nodeHeight;

    private Node mainNodeStructure;

    public StructurePanel(Node mainNodeStructure) {
        this.mainNodeStructure = mainNodeStructure;

        //Get width and height of a node and the vertical and horizontal space
        this.nodeWidth = mainNodeStructure.getGraphicalWidth();
        this.nodeHeight = mainNodeStructure.getGraphicalHeight();
        this.verticalSpace = mainNodeStructure.getVerticalSpace();
        this.horizontalSpace =mainNodeStructure.getHorizontalSpace();

        //Get number of vertical and horizontal elements
        this.numberOfHorizontalElements = mainNodeStructure.getWidthOfTree();
        this.numberOfVerticalElements = mainNodeStructure.getHeightOfTree();

        //Calculate size of window
        this.canvasSizeHorizontal = numberOfHorizontalElements * (nodeWidth + horizontalSpace);
        this.canvasSizeVertical = numberOfVerticalElements * nodeHeight + (numberOfVerticalElements - 1) * verticalSpace;

        //Canvas properties
        setBackground(BACKGROUND_COLOR);
        setSize(canvasSizeHorizontal, canvasSizeVertical);
        setVisible(true);
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Find and add all Nodes which are going to be drawn
        ArrayList<Node> allNodes = new ArrayList<>();
        allNodes.add(mainNodeStructure); // Adding itself to the array first
        allNodes.addAll(mainNodeStructure.collectAllNodes()); // Adding all other nodes to the array

        // Assign coordinates for nodes using initial coordinates of the root node
        int initialXCoordinate = canvasSizeHorizontal / 2;
        int initialYCoordinate = verticalSpace + nodeHeight;
        mainNodeStructure.setCoordinates(initialXCoordinate , initialYCoordinate , canvasSizeHorizontal);

        // Draw all nodes
        for(int i = 0; i < allNodes.size(); i++){
            allNodes.get(i).draw(g2d);
        }

        // Draw lines between nodes
        for(int i = 0; i < allNodes.size(); i++){
            drawLines(g2d, allNodes.get(i));
        }
    }

    /** Draws a lines between a node and its children.
     * @param g2d a graphical element.
     * @param node the node to draw lines from. */
    private void drawLines(Graphics2D g2d, Node node){

        ArrayList<int[]> array = node.getChildrenCoordinates();
        int[] lineStart = new int[]{node.getXCoordinate(), node.getYCoordinate()};

        for(int i = 0; i < array.size(); i++){
            g2d.drawLine(lineStart[0] + nodeWidth/2, lineStart[1] + nodeHeight, array.get(i)[0] + nodeWidth/2, array.get(i)[1]);
        }
    }

    public int getCanvasSizeHorizontal(){
        return canvasSizeHorizontal;
    }

    public int getCanvasSizeVertical(){
        return canvasSizeVertical;
    }
}
