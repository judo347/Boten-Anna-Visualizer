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
        this.nodeWidth = mainNodeStructure.getNodeWidth();
        this.nodeHeight = mainNodeStructure.getNodeHeight();
        this.verticalSpace = mainNodeStructure.getVerticalSpace();
        this.horizontalSpace =mainNodeStructure.getHorizontalSpace();

        //Get number of vertical and horizontal elements
        this.numberOfHorizontalElements = mainNodeStructure.getWidth();
        this.numberOfVerticalElements = mainNodeStructure.getHeight();

        //Calculate size of window //TODO: Currently without any extra space. So sizeFrame = sizeTree
        this.canvasSizeHorizontal = numberOfHorizontalElements * (nodeWidth + horizontalSpace);
        this.canvasSizeVertical = numberOfVerticalElements * nodeHeight + (numberOfVerticalElements - 1) * verticalSpace;

        //Canvas properties
        setBackground(Color.green);
        setSize(canvasSizeHorizontal, canvasSizeVertical);
        setVisible(true);
    }

    public void paint(Graphics g){


        //Get draw elements
        ArrayList<Node> allNodes = new ArrayList<>();
        allNodes.add(mainNodeStructure);
        allNodes.addAll(mainNodeStructure.collectAllNodes());

        //Assign coordinates
        mainNodeStructure.setCoordinates((canvasSizeHorizontal/2),verticalSpace + nodeHeight, canvasSizeHorizontal);

        //Some loop to draw all elements in nodeArray
        for(int i = 0; i < allNodes.size(); i++){
            drawElement(g, allNodes.get(i));
        }
    }

    private void drawElement(Graphics g, Node node){
        int x = node.getX();
        int y = node.getY();
        int width = node.getGraphicalWidth();
        int height = node.getGraphicalHeight();
        Node.NodeTypes type = node.getNodeType();
        String txt = node.getNodeName();
        Image image = getTypeImage(type);

        Graphics2D g2d = (Graphics2D) g;

        int borderThickness = 2;

        //  | IMAGE | text       |
        //g.fillRoundRect(300, 300, width, height, 10, 10);
        //g.fillRoundRect(500, 300, width, height, 10, 10);

        g.setColor(Color.BLACK);
        g.fillRoundRect(x, y, width, height, 10, 10); //Overall node
        g.setColor(Color.WHITE);
        g.fillRoundRect(x + borderThickness,y + borderThickness,40 - (2 * borderThickness),40 - (2 * borderThickness),10,10); //Left small box
        ((Graphics2D) g).drawImage(image, x, y, null); //Image
        g.drawImage(image,x + borderThickness, y + borderThickness, 40 - (2 * borderThickness), 40 - (2 * borderThickness), null);
        g.fillRoundRect(x + 40 + borderThickness, y + borderThickness, width - 40 - (borderThickness * 3), height - (2 * borderThickness), 10, 10); //Seconds box
        g.setColor(Color.BLACK);
        g.drawString(txt, x + 40 + 5, y + nodeHeight/2 + 2);
    }

    private Image getTypeImage(Node.NodeTypes type){

        Image image = null;
        try {
            image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/Guard.png"));
        } catch (IOException e) {

        }

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
}
