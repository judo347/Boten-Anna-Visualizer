package botenAnna;

import javax.swing.*;
import java.io.File;

public class MainFrame extends JFrame {

    private StructurePanel panel;

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

        // Get panel of structure
        panel = new StructurePanel(mainNodeStructure);

        //Scroll pane with drag tool
        frame.add(new ScrollDrag(panel, panel.getCanvasSizeHorizontal(), panel.getCanvasSizeVertical()));

        //Add elements to frame and make visible
        frame.pack();
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
