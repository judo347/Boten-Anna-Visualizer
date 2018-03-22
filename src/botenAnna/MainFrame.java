package botenAnna;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    final JFileChooser fc = new JFileChooser();

    /** This is the main function for the Behaviour tree visualizer.
     *  Call this and you will be asked to open a file.
     *  The files content should be a formatted behaviour tree
     *  and then this will be displayed as an image. */
    public MainFrame() {

        //Create the frame (window)
        JFrame frame = new JFrame("Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //TODO: EXCEPTIONS
        //Display filechooser
        int fcReturn = fc.showDialog(frame, null);
        FileAnalyser fa = new FileAnalyser();

        //Get node structure from the chosen file
        Node mainNodeStructure = fa.getStructureArrey(fc.getSelectedFile());

        //Get canvas of structure
        StructureCanvas canvas = new StructureCanvas(mainNodeStructure);

        //Scroll pane
        ScrollPane sp = new ScrollPane();
        sp.add(canvas);

        //Add elements to frame and make visible
        frame.add(sp);
        frame.pack();
        frame.setSize(1200, 800);
        frame.setVisible(true);

    }
}
