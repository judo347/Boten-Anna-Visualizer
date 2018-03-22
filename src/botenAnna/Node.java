package botenAnna;

import java.util.ArrayList;

public class Node {

    public ArrayList<Node> children;
    private String nodeName;
    private String lineOrigin;
    private NodeTypes nodeType;

    final private int width = 150;
    final private int height = 60;
    final private int verticalSpace = 50;
    final private int horizontalSpace = 50;

    private int x = 0;
    private int y = 0;

    public Node(String lineOrigin) {
        children = new ArrayList<Node>();
        this.lineOrigin = lineOrigin;
        setNameAndType();
    }

    public enum NodeTypes {
        SELECTOR, SEQUENCER, INVERTER, GUARD, TASK
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getLineOrigin() {
        return lineOrigin;
    }

    public ArrayList<Node> getArrayList() {
        return children;
    }

    private void setNameAndType() {
        String nodeName = getLineOrigin().trim().split(" ")[0];
        this.nodeType = getNodeTypeFromString(nodeName);
        this.nodeName = nodeName;
    }

    public NodeTypes getNodeType() {
        return this.nodeType;
    }

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

    public void addChild(Node node) {
        children.add(node);
    }

    public int getSize() {
        return 1 + children.stream().mapToInt(c -> c.getSize()).sum();
    }

    //TODO: Can be optimized to act like the (getSize()) method
    public int getWidth() {
        if (children.size() == 0)
            return 1;
        else {
            int sum = 0;
            for (int i = 0; i < children.size(); i++) {
                sum += children.get(i).getWidth();
            }

            return sum;
        }
    }

    public int getHeight() {
        if (children.size() == 0)
            return 1;
        else {
            int largestBranchHeight = 0;
            int brachHeight;
            for (int i = 0; i < children.size(); i++) {
                brachHeight = children.get(i).getHeight();
                if (brachHeight > largestBranchHeight)
                    largestBranchHeight = brachHeight;
            }

            return largestBranchHeight + 1;
        }
    }

    public boolean isCoordinatesSet() {
        return x != 0 && y != 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGraphicalWidth() {
        return width;
    }

    public int getGraphicalHeight() {
        return height;
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getVerticalSpace() {
        return verticalSpace;
    }

    public int getHorizontalSpace() {
        return horizontalSpace;
    }

    public void setCoordinates(int startX, int startY, int parentWidth) {

        this.setXandY(startX, startY);
        int x;

        if (this.children.size() > 0) {
            int currentX = 0;
            for (int i = 0; i < children.size(); i++) {
                int nextMaxWidth = parentWidth / children.size();
                int childWidth = children.get(i).getWidth() * (width + horizontalSpace);
                if (children.size() == 1){
                    x = startX;
                } else {
/*                    x = ((nextMaxWidth * (i + 1)) / 2);<*/
                    x = startX - parentWidth / 2 + childWidth / 2 + currentX;
                    currentX += childWidth;
                }
                int y = (startY + verticalSpace + height);

                this.children.get(i).setCoordinates(x, y, childWidth);
            }
        }
    }

    public ArrayList<Node> collectAllNodes() {
        ArrayList<Node> returnArray = new ArrayList<>();

/*        // Array of all non-childs
        if (children.size() == 0) {
            returnArray.add(new Node(this.lineOrigin));
            return returnArray;
        }*/

        // Array med alle childs
        for (int i = 0; i < children.size(); i++) {
            ArrayList<Node> child = children.get(i).collectAllNodes();
            returnArray.addAll(child);
        }

        for(int i = 0; i < children.size(); i++) {
            returnArray.add(children.get(i));
        }
        return returnArray;
    }
}
