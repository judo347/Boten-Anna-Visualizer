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
}
