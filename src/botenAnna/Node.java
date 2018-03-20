package botenAnna;

import java.util.ArrayList;

public class Node {

    public ArrayList<Node> children;
    private String nodeName;
    private String lineOrigin;
    private NodeTypes nodeType;

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

    public ArrayList<Node> getArrayList(){
        return children;
    }

    private void setNameAndType(){
        String nodeName = getLineOrigin().trim().split(" ")[0];
        this.nodeType = getNodeTypeFromString(nodeName);
        this.nodeName = nodeName;
    }

    public NodeTypes getNodeType(){
        return this.nodeType;
    }

    private NodeTypes getNodeTypeFromString(String nodeName){
        switch(nodeName) {
            case "Sequencer":
                return NodeTypes.SEQUENCER;
            case "Selector":
                return NodeTypes.SELECTOR;
            case "Inverter":
                return NodeTypes.INVERTER;
            default:
               if (nodeName.length() >= 5 && nodeName.substring(0, 5).equals("Guard")) {
                   return NodeTypes.GUARD;
               } else if (nodeName.length() >= 4 && nodeName.substring(0, 4).equals("Task")){
                   return NodeTypes.TASK;
               }
        }

        return null;
    }

    public void addChild(Node node){
        children.add(node);
    }

    public int getSize() {
        return 1 + children.stream().mapToInt(c -> c.getSize()).sum();
    }

    //TODO: Can be optimized to act like the (getSize()) method
    public int getWidth() {
        if(children.size() == 0)
            return 1;
        else{
            int sum = 0;
            for(int i = 0; i < children.size(); i++){
                sum += children.get(i).getWidth();
            }

            return sum;
        }
    }

    public int getHeight(){
        if(children.size() == 0)
            return 1;
        else{
            int largestBranchHeight = 0;
            int brachHeight;
            for(int i = 0; i < children.size(); i++){
                brachHeight = children.get(i).getHeight();
                if(brachHeight > largestBranchHeight)
                    largestBranchHeight = brachHeight;
            }

            return largestBranchHeight + 1;
        }
    }
}
