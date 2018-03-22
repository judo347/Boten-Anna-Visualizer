package botenAnna;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StructureCanvas extends Canvas {

    private int numberOfHorizontalElements;
    private int numberOfVerticalElements;
    private int canvasSizeHorizontal;
    private int canvasSizeVertical;
    final int verticalSpace;
    final int horizontalSpace;
    private int nodeWidth;
    private int nodeHeight;

    private Node mainNodeStructure;

    public StructureCanvas(Node mainNodeStructure) {
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
            drawElement(g, allNodes.get(i));
        }
    }

    /** This method draws a node.
     * @param g a graphical element.
     * @param node the node to be drawn. */
    private void drawElement(Graphics g, Node node){
        int x = node.getXCoordinate();
        int y = node.getYCoordinate();
        int width = node.getGraphicalWidth();
        int height = node.getGraphicalHeight();
        Node.NodeTypes type = node.getNodeType();
        String txt = node.getNodeName();
        Image image = getTypeImage(type);

        Graphics2D g2d = (Graphics2D) g;

        int borderThickness = 2;
        int edgeArc = 10;
        int imageSizeX = height, imageSizeY = height;

        //  | IMAGE | text       | //TODO used variable calculating from one to the next
        //Background box
        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, width, height, edgeArc, edgeArc);

        //Image and image background
        int imageWidth = imageSizeX - (2 * borderThickness);
        int imageHeight = imageSizeY - (2 * borderThickness);
        g.setColor(getTypeColor(type));
        g.fillRoundRect(x + borderThickness, y + borderThickness, imageWidth, imageHeight, edgeArc, edgeArc);
        ((Graphics2D) g).drawImage(image, x, y, imageWidth, imageHeight, null); //Image

        //Text background and text
        g.setColor(Color.WHITE);
        g.fillRoundRect(x + imageWidth + (2 * borderThickness), y + borderThickness, width - imageWidth - (3 * borderThickness), height - ( 2 * borderThickness), edgeArc, edgeArc);
        //g.fillRoundRect(x + (imageSizeX + (1 * borderThickness)), y + borderThickness, width - imageSizeX - (2 * borderThickness), height - (3 * borderThickness), edgeArc, edgeArc);
        g.setColor(Color.black);
        g.drawString(txt, x + imageSizeX + 10, y + nodeHeight/2 + 2);
    }

    /** Takes a enum of a node type and returns the image corresponding to the type.
     * @param type enum of a node type
     * @return the image of the parsed type. */
    private Image getTypeImage(Node.NodeTypes type){

        Image image = null;

        //Load the image corresponding to the type of the enum
        try{
            switch (type){
                case SELECTOR   : image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/Selector.png"));
                    break;
                case SEQUENCER  : image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/Sequencer.png"));
                    break;
                case INVERTER   : image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/Inverter.png"));
                    break;
                case GUARD      : image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/Guard.png"));
                    break;
                case TASK       : image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/Task.png"));
                    break;
            }
        } catch (IOException e){
            System.out.println("Could not load image: StructureCanvas -> getTypeImage");
            e.printStackTrace();
        }

        return image;
    }

    /** Takes an enum of a node type and returns the corresponding color.
     * @param type enum of a node type.
     * @return the color of the parsed type. */
    private Color getTypeColor(Node.NodeTypes type){

        switch (type) {
            case SELECTOR   : return Color.YELLOW;
            case SEQUENCER  : return Color.PINK;
            case INVERTER   : return Color.GREEN;
            case GUARD      : return Color.CYAN;
            case TASK       : return Color.RED;
        }

        return null; //Should not get here
    }
}
