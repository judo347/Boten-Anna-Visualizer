package botenAnna;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class MainFrame extends JFrame {

    final JFileChooser fc = new JFileChooser();

    public  MainFrame() throws HeadlessException {

        JFrame frame = new JFrame("Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int fcReturn = fc.showDialog(frame, null);
        FileAnalyser fa = new FileAnalyser();

        fa.getStructureArrey(fc.getSelectedFile());


        frame.pack();
        frame.setSize(600,600);
        frame.setVisible(true);

    }

    public void addComponents(final Container pane){

    }
}
