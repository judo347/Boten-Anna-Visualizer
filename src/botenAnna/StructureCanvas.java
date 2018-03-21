package botenAnna;

import java.awt.*;
import java.util.PrimitiveIterator;

public class StructureCanvas extends Canvas {

    final int nodeWidth = 10;
    final int nodeHeight = 5;
    final int verticalSpace = 2;
    final int horizontalSpace = 2;

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
        //TODO
    }
}
