package botenAnna;

import java.util.ArrayList;

public class Node {

    public ArrayList<Node> children;
    private String nodeName;
    private String lineOrigin;

    public Node(String lineOrigin) {
        children = new ArrayList<Node>();
        this.lineOrigin = lineOrigin;
        setName();
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

    private void setName(){
        this.nodeName = getLineOrigin();
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
