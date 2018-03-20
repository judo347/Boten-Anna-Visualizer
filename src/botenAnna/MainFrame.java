package botenAnna;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    final JFileChooser fc = new JFileChooser();

    final int nodeWidth = 10;
    final int nodeHeight = 5;
    final int verticalSpace = 2;
    final int horizontalSpace = 2;

    public MainFrame() throws HeadlessException {

        //Create the window
        JFrame frame = new JFrame("Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display filechooser
        int fcReturn = fc.showDialog(frame, null);
        FileAnalyser fa = new FileAnalyser();

        //Get node structure from the choosen file
        Node mainNodeStructure = fa.getStructureArrey(fc.getSelectedFile());

        //Get width and height of tree
        int numberOfHorizontalElements = mainNodeStructure.getWidth();
        int numberOfVerticalElements = mainNodeStructure.getHeight();

        System.out.println("Height of tree: " + numberOfVerticalElements);
        System.out.println("Width of tree: " + numberOfHorizontalElements);

        //Calculate size of window //TODO: Currently without any extra space. So sizeFrame = sizeTree
        int windowSizeHorizontal = numberOfHorizontalElements * nodeWidth + (numberOfHorizontalElements - 1) * horizontalSpace;
        int windowSizeVertical = numberOfVerticalElements * nodeHeight + (numberOfVerticalElements - 1) * verticalSpace;

        frame.pack();
        frame.setSize(windowSizeHorizontal, windowSizeVertical);
        frame.setVisible(true);

    }

    public void addComponents(final Container pane) {


    }
}
