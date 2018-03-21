package botenAnna;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    final JFileChooser fc = new JFileChooser();

    public MainFrame() throws HeadlessException {

        //Create the window
        JFrame frame = new JFrame("Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //TODO: EXCEPTIONS
        //Display filechooser
        int fcReturn = fc.showDialog(frame, null);
        FileAnalyser fa = new FileAnalyser();

        //Get node structure from the choosen file
        Node mainNodeStructure = fa.getStructureArrey(fc.getSelectedFile());

        /*
        //Get canvas of structure
        StructureCanvas canvas = new StructureCanvas(mainNodeStructure);

        //Scroll pane
        ScrollPane sp = new ScrollPane();
        sp.add(canvas);
        */

        //addComponents(frame.getContentPane());

        //frame.add(sp);
        frame.pack();
        frame.setSize(600, 600);
        frame.setVisible(true);

    }

    public void addComponents(final Container pane) {




    }
}
