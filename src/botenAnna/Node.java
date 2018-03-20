package botenAnna;

import java.util.ArrayList;

public class Node {

    public Node(ArrayList<Node> children, String lineOrigin) {
        this.children = children;
        this.lineOrigin = lineOrigin;
        setName();
    }

    public ArrayList<Node> children;
    private String nodeName;
    private String lineOrigin;

    public String getNodeName() {
        return nodeName;
    }

    public String getLineOrigin() {
        return lineOrigin;
    }

    private void setName(){
        this.nodeName = getLineOrigin();
    }

}
