package botenAnna;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Node {

    public static final Color COMPOSITE_COLOR = Color.decode("#B38867");
    public static final Color DECORATOR_COLOR = Color.decode("#FD974F");
    public static final Color ABSOLUTE_COLOR = Color.decode("#128277");
    public static final Color GUARD_COLOR = Color.decode("#c6d166");
    public static final Color TASK_COLOR = Color.decode("#B2473E");


    public enum NodeTypes {
        SELECTOR("Selector.png", COMPOSITE_COLOR),
        SEQUENCER("Sequencer.png", COMPOSITE_COLOR),
        INVERTER("Inverter.png", DECORATOR_COLOR),
        GUARD("Guard.png", GUARD_COLOR),
        TASK("Task.png", TASK_COLOR),
        ALWAYSFAILURE("AlwaysFailure.png", DECORATOR_COLOR),
        ALWAYSSUCCESS("AlwaysSuccess.png", DECORATOR_COLOR),
        IFTHENELSE("IfThenElse.png", ABSOLUTE_COLOR);

        private Image image;
        private Color color;

        NodeTypes(String fileName, Color color){
            try {
                this.image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/" + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.color = color;
        }

        public Image getImage() {
            return image;
        }

        public Color getColor() {
            return color;
        }
    }

    private ArrayList<Node> children;
    private String nodeName;
    private String lineOrigin;
    private NodeTypes nodeType;

    // Assigning variables used for drawing the Behaviour Tree
    final private int nodeWidth = 150;
    final private int nodeHeight = 40;
    final private int verticalSpace = 50;
    final private int horizontalSpace = 50;

    // Assigning default x,y position-values.
    private int x = 0;
    private int y = 0;

    // Constructor
    public Node(String lineOrigin) {
        children = new ArrayList<>();
        this.lineOrigin = lineOrigin;
        setNameAndType();
    }

    // Methods

    private void setXYCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method will set the coordinates for a node which will then be used to draw the behaviour tree.
     * It uses recursion to find the respective positions relative to its parent nodes.
     * @param startX X-Coordinate of the current node
     * @param startY Y-Coordinate of the current node
     * @param parentWidth The width of a parent node for which the child node will use to determine its position.
     */
    public void setCoordinates(int startX, int startY, int parentWidth) {

        // Sets the coordinates of the current node
        this.setXYCoordinates(startX-75, startY); // TODO: Temporary fix to fit all nodes. Moving whole tree by 75 pixels.
        int x;

        if (this.children.size() > 0) {
            int currentX = 0; // Used to position nodes to the right of previous node if on same y-coordinate.
            for (int i = 0; i < children.size(); i++) {
                int childWidth = children.get(i).getWidthOfTree() * (nodeWidth + horizontalSpace);
                if (children.size() == 1){
                    // If there is no other children it will receive same x-coordinate as its parent
                    x = startX;
                } else {
                    // If there are multiple children it will position them next to each other.
                    x = startX - parentWidth / 2 + childWidth / 2 + currentX;
                    currentX += childWidth;
                }
                // The y-coordinate is the same for all nodes.
                int y = (startY + verticalSpace + nodeHeight);

                // By using recursion we can do this for all nodes.
                this.children.get(i).setCoordinates(x, y, childWidth);
            }
        }
    }

    /**
     * Sets the name of a node by trimming the lineOrigin (full line) and removing
     * unnecessary spacing and then assigning only the first element of the string.
     * Also sets the type by using the getNodeTypeFromString method with the name of a node.
     */
    private void setNameAndType() {
        String nodeName = getLineOrigin().trim().split(" ")[0];
        this.nodeType = getNodeTypeFromString(nodeName);
        if (isStringTask(nodeName)){
            this.nodeName = nodeName.replace("Task", "");
        } else if (isStringGuard(nodeName)){
            this.nodeName = nodeName.replace("Guard", "");
        } else {
            this.nodeName = nodeName;
        }
    }

    private boolean isStringTask (String nodeName){
        if (nodeName.length() >= 4 && nodeName.substring(0, 4).equals("Task")){return true;}
        else {return false;}
    }
    private boolean isStringGuard (String nodeName){
        if (nodeName.length() >= 5 && nodeName.substring(0, 5).equals("Guard")){return true;}
        else {return false;}
    }

    /**
     * Checks a given string for which type of Node it is by comparing the text.
     * If no recognized NodeType is found for the string it returns null.
     * @param nodeName String which contains the nodeName.
     * @return Returns NodeType of a given string.
     */
    private NodeTypes getNodeTypeFromString(String nodeName) {
        switch (nodeName) {
            case "Sequencer":
                return NodeTypes.SEQUENCER;
            case "Selector":
                return NodeTypes.SELECTOR;
            case "Inverter":
                return NodeTypes.INVERTER;
            case "IfThenElse":
                return NodeTypes.IFTHENELSE;
            case "AlwaysSuccess":
                return NodeTypes.ALWAYSSUCCESS;
            case "AlwaysFailure":
                return NodeTypes.ALWAYSFAILURE;
            default:
                if (isStringGuard(nodeName)) {
                    return NodeTypes.GUARD;
                } else if (isStringTask(nodeName)) {
                    return NodeTypes.TASK;
                }
        }

        return null;
    }

    /** Used to get the number of elements in this nodes tree.
     * @return number of nodes in this nodes tree. (counting this one)*/
    private int getSizeOfTree() {
        return 1 + children.stream().mapToInt(c -> c.getSizeOfTree()).sum();
    }

    /** Used to get the number of elements on the widest level.
     * @return number of elements on the widest level. */
    public int getWidthOfTree() {
        if (children.size() == 0)
            return 1;
        else {
            int sum = 0;
            for (int i = 0; i < children.size(); i++) {
                sum += children.get(i).getWidthOfTree();
            }

            return sum;
        }
    }

    /** Used to get the number of level in tree.
     * @return number of levels in the tree. */
    public int getHeightOfTree() {
        if (children.size() == 0)
            return 1;
        else {
            int largestBranchHeight = 0;
            int brachHeight;
            for (int i = 0; i < children.size(); i++) {
                brachHeight = children.get(i).getHeightOfTree();
                if (brachHeight > largestBranchHeight)
                    largestBranchHeight = brachHeight;
            }

            return largestBranchHeight + 1;
        }
    }

    /** This method draws a the node and all its children recursively.
     * @param g2d a graphical element. */
    public void draw(Graphics2D g2d){

        int borderThickness = 2;
        int edgeArc = 10;
        int imageSize = nodeHeight;

        //  | IMAGE | text       | //TODO used variable calculating from one to the next
        //Background box
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(x, y, nodeWidth, nodeHeight, edgeArc, edgeArc);

        //Image and image background
        int imageWidth = imageSize - (2 * borderThickness);
        int imageHeight = imageSize - (2 * borderThickness);
        g2d.setColor(nodeType.getColor());
        g2d.fillRoundRect(x + borderThickness, y + borderThickness, imageWidth, imageHeight, edgeArc, edgeArc);
        g2d.drawImage(nodeType.getImage(), x + borderThickness, y + borderThickness, imageWidth, imageHeight, null); //Image

        //Text background and text
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x + imageWidth + (2 * borderThickness), y + borderThickness, nodeWidth - imageWidth - (3 * borderThickness), nodeHeight - ( 2 * borderThickness), edgeArc, edgeArc);
        g2d.setColor(Color.BLACK);
        DrawHelper.drawText(g2d, x + imageSize + 10, y + nodeHeight/2 + 2, nodeName, 13);

        //Lines
        drawLines(g2d);

        //Draw children - recursion
        for (Node child : children) {
            child.draw(g2d);
        }
    }

    /** Draws a lines between the node and its children.
     * @param g2d a graphical element. */
    private void drawLines(Graphics2D g2d) {

        for (Node child : children) {
            g2d.drawLine(x + nodeWidth / 2, y + nodeHeight, child.x + child.nodeWidth / 2, child.y);
        }
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public String getNodeName() {
        return nodeName;
    }

    private String getLineOrigin() {
        return lineOrigin;
    }

    public NodeTypes getNodeType() {
        return this.nodeType;
    }

    public int getGraphicalWidth() {
        return nodeWidth;
    }

    public int getGraphicalHeight() {
        return nodeHeight;
    }

    public int getXCoordinate() {
        return x;
    }

    public int getYCoordinate() {
        return y;
    }

    public int getVerticalSpace() {
        return verticalSpace;
    }

    public int getHorizontalSpace() {
        return horizontalSpace;
    }
}
