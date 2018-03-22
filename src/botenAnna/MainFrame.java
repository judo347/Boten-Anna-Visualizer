package botenAnna;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {



    /** This is the main function for the Behaviour tree visualizer.
     *  Call this and you will be asked to open a file.
     *  The files content should be a formatted behaviour tree
     *  and then this will be displayed as an image. */
    public MainFrame() {

        //Create the frame (window)
        JFrame frame = new JFrame("Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FileAnalyser fa = new FileAnalyser();

        //Display filechooser and get file
        File file = null;
        try{
            file = fa.getFile(frame);
        }catch (NoFileSelectedException e){
            System.exit(0);
        }

        //Get node structure from the chosen file
        Node mainNodeStructure = fa.getStructureArrey(file);

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
