package botenAnna;

import java.awt.*;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class StructureCanvas extends Canvas {

    final int nodeWidth = 10;
    final int nodeHeight = 5;
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
        setSize(canvasSizeHorizontal+5000, canvasSizeVertical+4000);
        setVisible(true);
    }

    public void paint(Graphics g){
        //Convert existing array into GraphicalNode
        GraphicalNode gnSructure = new GraphicalNode("Does this matter?");
        gnSructure = this.mainNodeStructure.getGraphicalNodeArray2();

        //Assign coordinates


        //Get draw elements
        //nodeArray = this.mainNodeStructure.getGriphicalNodeArray();

        //Some loop to draw all elements in nodeArray
        /*
        for(int i = 0; i < nodeArray.size(); i++){
            int x = nodeArray.get(i).getX();
            int y = nodeArray.get(i).getY();
            int width = nodeArray.get(i).getWidth();
            int heigth = nodeArray.get(i).getHeight();
            g.drawRect(x,y,width,heigth);
            g.drawString(nodeArray.get(i).getName(), width/2+x, heigth/2+y);
        }*/
    }
}
