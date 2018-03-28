package botenAnna;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StructurePanel extends JPanel {

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
        setBackground(Color.GRAY);
        setSize(canvasSizeHorizontal, canvasSizeVertical);
        setVisible(true);
    }

    public void paint(Graphics g){

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
            drawElement(g2d, allNodes.get(i));
        }

        // Draw lines between nodes
        for(int i = 0; i < allNodes.size(); i++){
            drawLines(g2d, allNodes.get(i));
        }
    }

    /** This method draws a node.
     * @param g2d a graphical element.
     * @param node the node to be drawn. */
    private void drawElement(Graphics2D g2d, Node node){
        int x = node.getXCoordinate();
        int y = node.getYCoordinate();
        int width = node.getGraphicalWidth();
        int height = node.getGraphicalHeight();
        Node.NodeTypes type = node.getNodeType();
        String txt = node.getNodeName();
        Image image = type.getImage();

        int borderThickness = 2;
        int edgeArc = 10;
        int imageSizeX = height, imageSizeY = height;

        //  | IMAGE | text       | //TODO used variable calculating from one to the next
        //Background box
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(x, y, width, height, edgeArc, edgeArc);

        //Image and image background
        int imageWidth = imageSizeX - (2 * borderThickness);
        int imageHeight = imageSizeY - (2 * borderThickness);
        g2d.setColor(type.getColor());
        g2d.fillRoundRect(x + borderThickness, y + borderThickness, imageWidth, imageHeight, edgeArc, edgeArc);
        g2d.drawImage(image, x+2, y+2, imageWidth, imageHeight, null); //Image

        //Text background and text
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x + imageWidth + (2 * borderThickness), y + borderThickness, width - imageWidth - (3 * borderThickness), height - ( 2 * borderThickness), edgeArc, edgeArc);
        //g.fillRoundRect(x + (imageSizeX + (1 * borderThickness)), y + borderThickness, width - imageSizeX - (2 * borderThickness), height - (3 * borderThickness), edgeArc, edgeArc);
        g2d.setColor(Color.black);
        drawText(g2d, node);

    }

    /** Draws the text for a node
     * @param g2d a graphical element.
     * @param node the node with the text. */
    private void drawText(Graphics g2d, Node node){
        String txt = node.getNodeName();
        int imageSize = node.getGraphicalHeight();
        int x = node.getXCoordinate() + imageSize + 10;
        int y = node.getYCoordinate() + nodeHeight/2 + 2;

        //Can the string be on one line
        if(txt.length() < 14)
            g2d.drawString(txt, x, y);
        else{

            //Find the last upperCase / start of a word
            int i = 13;
            while(!Character.isUpperCase(txt.charAt(i))){i--;}

            //Cut the line into two
            String firstLine = "";
            String secondLine = "";
            int j;

            for(j = 0; j < i; j++){
                firstLine = firstLine + txt.charAt(j);
            }

            for(;j < txt.length(); j++){
                secondLine = secondLine + txt.charAt(j);
            }

            g2d.drawString(firstLine, x, y - 5);
            g2d.drawString(secondLine, x, y + 10);
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
