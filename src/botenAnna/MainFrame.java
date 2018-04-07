package botenAnna;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {

    private JFrame frame;

    private File file = null;
    private StructurePanel structurePanel = null;

    public static final int WINDOW_INITIAL_WIDTH = 1200;
    public static final int WINDOW_INITIAL_HEIGHT = 800;

    public static void main(String[] args) {
        MainFrame window = new MainFrame();
    }

    /** This is the main function for the Behaviour tree visualizer.
     *  Call this and you will be asked to open a file.
     *  The files content should be a formatted behaviour tree
     *  and then this will be displayed as an image. */
    public MainFrame() {

        //Creating the frame (window)
        frame = new JFrame("Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Load file and add components
        frame.setSize(WINDOW_INITIAL_WIDTH, WINDOW_INITIAL_HEIGHT);
        doLoadFile();

        //Display the window
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /** Adds components/content to to the frame. */
    private void addComponentsToPane(final Container pane){

        pane.removeAll();

        //Creating the topbar
        JPanel topbar = new JPanel();
        topbar.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton buttonLoad = new JButton("Load");
        JButton buttonRefresh = new JButton("Refresh");
        buttonLoad.addActionListener(e -> doLoadFile());
        buttonRefresh.addActionListener(e -> doRefreshFile());
        topbar.add(buttonLoad);
        topbar.add(buttonRefresh);

        //Creating the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(topbar);
        mainPanel.add(new ScrollDrag(structurePanel, structurePanel.getCanvasSizeHorizontal(), structurePanel.getCanvasSizeVertical()));

        frame.add(mainPanel);

        //Save the current window size and pack frame
        Dimension windowSize = frame.getSize();
        frame.pack();
        frame.setSize(WINDOW_INITIAL_WIDTH, WINDOW_INITIAL_HEIGHT);
    }

    /** Display the filechooser and generate content for frame. */
    private void doLoadFile(){

        FileAnalyser fa = new FileAnalyser();

        //Display filechooser and get file
        try{
            file = fa.getFile(frame);
            Node mainNodeStructure = fa.getStructure(file);
            structurePanel = new StructurePanel(mainNodeStructure);

            //Refresh the content
            addComponentsToPane(frame.getContentPane());

        }catch (NoFileSelectedException e){
            if(file == null)
                System.exit(0);
        }
    }

    /** Reload the file and structurePanel. */
    private void doRefreshFile(){

        FileAnalyser fa = new FileAnalyser();

        //Load file and create structurePanel
        Node mainNodeStructure = fa.getStructure(file);
        structurePanel = new StructurePanel(mainNodeStructure);

        //Refresh components
        addComponentsToPane(frame.getContentPane());
    }
}
