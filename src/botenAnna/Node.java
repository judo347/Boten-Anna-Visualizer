package botenAnna;

import java.util.ArrayList;

public class Node {

    public enum NodeTypes {
        SELECTOR, SEQUENCER, INVERTER, GUARD, TASK
    }

    private ArrayList<Node> children;
    private String nodeName;
    private String lineOrigin;
    private NodeTypes nodeType;

    // Assigning variables used for drawing the Behaviour Tree
    final private int nodeWidth = 150;
    final private int nodeHeight = 60;
    final private int verticalSpace = 50;
    final private int horizontalSpace = 50;

    // Assigning default x,y position-values.
    private int x = 0;
    private int y = 0;

    // Constructor
    public Node(String lineOrigin) {
        children = new ArrayList<Node>();
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
        this.setXYCoordinates(startX, startY);
        int x;

        if (this.children.size() > 0) {
            int currentX = 0; // Used to position nodes right of the previous node if on same y-coordinate.
            for (int i = 0; i < children.size(); i++) {
                int childWidth = children.get(i).getWidthOfTree() * (nodeWidth + horizontalSpace);
                if (children.size() == 1){
                    // If there is no other child it will receive same x-coordinate as its parent
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
     * Method to find and collect all nodes using recursion.
     * @return Returns an array of Nodes in which ALL nodes are stored.
     */
    public ArrayList<Node> collectAllNodes() {
        ArrayList<Node> returnArray = new ArrayList<>();

        // This loop adds all child nodes to the array by calling them recursive.
        for (int i = 0; i < children.size(); i++) {
            ArrayList<Node> child = children.get(i).collectAllNodes();
            returnArray.addAll(child);
        }

        // This loop adds all parent nodes to the array.
        for(int i = 0; i < children.size(); i++) {
            returnArray.add(children.get(i));
        }

        return returnArray;
    }

    /**
     * Sets the name of a node by trimming the lineOrigin (full line) and removing
     * unnecessary spacing and then assigning only the first element of the string.
     * Also sets the type by using the getNodeTypeFromString method with the name of a node.
     */
    private void setNameAndType() {
        String nodeName = getLineOrigin().trim().split(" ")[0];
        this.nodeType = getNodeTypeFromString(nodeName);
        this.nodeName = nodeName;
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
            default:
                if (nodeName.length() >= 5 && nodeName.substring(0, 5).equals("Guard")) {
                    return NodeTypes.GUARD;
                } else if (nodeName.length() >= 4 && nodeName.substring(0, 4).equals("Task")) {
                    return NodeTypes.TASK;
                }
        }

        return null;
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

    // TODO: Mikkel kommentar
    private int getSizeOfTree() {
        return 1 + children.stream().mapToInt(c -> c.getSizeOfTree()).sum();
    }

    //TODO: Can be optimized to act like the (getSize()) method
    //TODO: Mikkel - Er ikke sikker på hvordan den fungerer. Mangler kommentar
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

    //TODO: Mikkel - Er ikke sikker på hvordan den fungerer. Mangler kommentar
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

    // TODO: Mikkel - Bliver denne stadig brugt?
    public void addChild(Node node) {
        children.add(node);
    }

    // TODO: Mikkel - bliver denne stadig brugt?
    public ArrayList<Node> getArrayList() {
        return children;
    }
}
