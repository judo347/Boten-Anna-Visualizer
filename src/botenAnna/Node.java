package botenAnna;

import java.awt.*;
import java.util.ArrayList;

public class Node {

    public static final int VERTICAL_SPACING = 30;
    public static final int HORIZONTAL_SPACING = 30;

    private static final int NODE_WIDTH = 120;
    private static final int NODE_HEIGHT = 30;
    private static final int BORDER_THICKNESS = 2;
    private static final int BORDER_ARC = 8;
    private static final int TEXT_INDENT = 4;

    private int x, y;
    private ArrayList<Node> children;
    private String nodeName;
    private String lineOrigin;
    private NodeType nodeType;
    private boolean collapsed = false;

    public Node(String lineOrigin) {
        setXYCoordinates(0, 0);
        children = new ArrayList<>();
        this.lineOrigin = lineOrigin;
        setNameAndType();
    }

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
        this.setXYCoordinates(startX, startY);
        int x;

        if (this.children.size() > 0) {
            int currentX = 0; // Used to position nodes to the right of previous node if on same y-coordinate.
            for (int i = 0; i < children.size(); i++) {
                int childWidth = children.get(i).getWidthOfTreeAsCount() * (NODE_WIDTH + HORIZONTAL_SPACING);
                if (children.size() == 1){
                    // If there is no other children it will receive same x-coordinate as its parent
                    x = startX;
                } else {
                    // If there are multiple children it will position them next to each other.
                    x = startX - parentWidth / 2 + childWidth / 2 + currentX;
                    currentX += childWidth;
                }
                // The y-coordinate is the same for all nodes.
                int y = (startY + VERTICAL_SPACING + NODE_HEIGHT);

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
    private NodeType getNodeTypeFromString(String nodeName) {
        switch (nodeName) {
            case "Sequencer":
                return NodeType.SEQUENCER;
            case "Selector":
                return NodeType.SELECTOR;
            case "Inverter":
                return NodeType.INVERTER;
            case "IfThenElse":
                return NodeType.IF_THEN_ELSE;
            case "AlwaysSuccess":
                return NodeType.ALWAYS_SUCCESS;
            case "AlwaysFailure":
                return NodeType.ALWAYS_FAILURE;
            default:
                if (isStringGuard(nodeName)) {
                    return NodeType.GUARD;
                } else if (isStringTask(nodeName)) {
                    return NodeType.TASK;
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
    public int getWidthOfTreeAsCount() {
        if (children.size() == 0)
            return 1;
        else {
            int sum = 0;
            for (int i = 0; i < children.size(); i++) {
                sum += children.get(i).getWidthOfTreeAsCount();
            }

            return sum;
        }
    }

    /** @return the width of this node's tree in pixels. This includes spacing */
    public int getWidthOfTreeGraphical() {
        if (children.size() == 0) {
            return getWidth() + HORIZONTAL_SPACING;
        } else {
            return children.stream().mapToInt(Node::getWidthOfTreeGraphical).sum();
        }
    }

    /** Used to get the number of levels in tree.
     * @return number of levels in the tree. */
    public int getHeightOfTreeAsCount() {
        if (children.size() == 0)
            return 1;
        else {
            int largestBranchHeight = 0;
            int brachHeight;
            for (int i = 0; i < children.size(); i++) {
                brachHeight = children.get(i).getHeightOfTreeAsCount();
                if (brachHeight > largestBranchHeight)
                    largestBranchHeight = brachHeight;
            }

            return largestBranchHeight + 1;
        }
    }

    /** @return the height of this node's tree in pixels. This includes spacing */
    public int getHeightOfTreeGraphical() {
        if (children.size() == 0) {
            return getHeight() + VERTICAL_SPACING;
        } else {
            return children.stream().mapToInt(Node::getHeightOfTreeGraphical).sum();
        }
    }

    /** This method draws a the node and all its children recursively.
     * @param g2d a graphical element. */
    public void drawTree(Graphics2D g2d) {
        if (collapsed) {
            drawCollapsed(g2d);
        } else {
            drawExpanded(g2d);
        }

        //Lines
        drawLines(g2d);

        //Draw children - recursion
        for (Node child : children) {
            child.drawTree(g2d);
        }
    }

    private void drawCollapsed(Graphics2D g2d) {

    }

    private void drawExpanded(Graphics2D g2d) {
        int cornerX = getTopLeftX();

        //Background box
        g2d.setColor(Color.BLACK);
        g2d.fillRoundRect(cornerX, y, NODE_WIDTH, NODE_HEIGHT, BORDER_ARC, BORDER_ARC);

        //Image and image background
        int imageWidth = NODE_HEIGHT - (2 * BORDER_THICKNESS);
        int imageHeight = NODE_HEIGHT - (2 * BORDER_THICKNESS);
        g2d.setColor(nodeType.getColor());
        g2d.fillRoundRect(cornerX + BORDER_THICKNESS, y + BORDER_THICKNESS, imageWidth, imageHeight, BORDER_ARC, BORDER_ARC);
        g2d.drawImage(nodeType.getImage(), cornerX + BORDER_THICKNESS, y + BORDER_THICKNESS, imageWidth, imageHeight, null); //Image

        //Text background and text
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(cornerX + imageWidth + (2 * BORDER_THICKNESS), y + BORDER_THICKNESS, NODE_WIDTH - imageWidth - (3 * BORDER_THICKNESS), NODE_HEIGHT - ( 2 * BORDER_THICKNESS), BORDER_ARC, BORDER_ARC);
        g2d.setColor(Color.BLACK);
        DrawHelper.drawText(g2d, cornerX + imageWidth + 3 * BORDER_THICKNESS + TEXT_INDENT, y + NODE_HEIGHT /2 + 3, nodeName, 13);
    }

    /** Draws a lines between the node and its children.
     * @param g2d a graphical element. */
    private void drawLines(Graphics2D g2d) {

        for (Node child : children) {
            g2d.drawLine(x, y + NODE_HEIGHT, child.x, child.y);
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

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public int getTopLeftX() {
        return x - getWidth() / 2;
    }

    public int getWidth() {
        return collapsed ? NODE_HEIGHT : NODE_WIDTH;
    }

    public int getHeight() {
        return NODE_HEIGHT;
    }
}
