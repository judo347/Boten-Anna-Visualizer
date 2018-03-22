package botenAnna;

import java.awt.*;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class StructureCanvas extends Canvas {

    final int nodeWidth = 150;
    final int nodeHeight = 60;
    final int verticalSpace = 50;
    final int horizontalSpace = 50;



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

        for (int i = 0; i < allNodes.size(); i++) {
            System.out.println(allNodes.get(i).getNodeName());
        }

        //Some loop to draw all elements in nodeArray
        for(int i = 0; i < allNodes.size(); i++){
            int x = allNodes.get(i).getX();
            int y = allNodes.get(i).getY();
            int width = allNodes.get(i).getGraphicalWidth();
            int heigth = allNodes.get(i).getGraphicalHeight();
            g.drawRect(x,y,width,heigth);
            g.drawString(allNodes.get(i).getNodeName(), width/2+x, heigth/2+y);
        }
    }
}
