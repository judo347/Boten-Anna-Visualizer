package botenAnna;

import java.awt.*;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class StructureCanvas extends Canvas {

    final int nodeWidth = 150;
    final int nodeHeight = 60;
    final int verticalSpace = 10;
    final int horizontalSpace = 30;



    private int numberOfHorizontalElements;
    private int numberOfVerticalElements;
    private int canvasSizeHorizontal;
    private int canvasSizeVertical;

    private Node mainNodeStructure;

    public StructureCanvas(Node mainNodeStructure) {
        this.mainNodeStructure = mainNodeStructure;

        //Get width and height of tree
        this.numberOfHorizontalElements = mainNodeStructure.getWidth();
        this.numberOfVerticalElements = mainNodeStructure.getHeight();

        //Calculate size of window //TODO: Currently without any extra space. So sizeFrame = sizeTree
        this.canvasSizeHorizontal = numberOfHorizontalElements * nodeWidth + (numberOfHorizontalElements - 1) * horizontalSpace;
        this.canvasSizeVertical = numberOfVerticalElements * nodeHeight + (numberOfVerticalElements - 1) * verticalSpace;

        //Canvas properties
        setBackground(Color.green);
        setSize(canvasSizeHorizontal, canvasSizeVertical);
        setVisible(true);
    }

    public void paint(Graphics g){
        //Assign coordinates
        mainNodeStructure.setCoordinates(canvasSizeHorizontal/2,verticalSpace + nodeHeight, canvasSizeHorizontal);

        //Get draw elements
        ArrayList<Node> allNodes = mainNodeStructure.collectAllNodes();
        allNodes.add(mainNodeStructure);

        //Some loop to draw all elements in nodeArray
        for(int i = 0; i < allNodes.size(); i++){
            int x = allNodes.get(i).getX();
            int y = allNodes.get(i).getY();
            int width = allNodes.get(i).getWidth();
            int heigth = allNodes.get(i).getHeight();
            g.drawRect(x,y,width,heigth);
            g.drawString(allNodes.get(i).getNodeName(), width/2+x, heigth/2+y);
        }
    }
}
