package botenAnna;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class FileAnalyser {

    private int nextLine;

    /** Displays a filechooser and lets the user choose a file.
     *  @param frame the frame parent (will be locked while filechooser is displayed).
     *  @return the file chosen by the user. */
    public File getFile(Frame frame){
        JFileChooser fc = new JFileChooser();
        int fcReturn = fc.showDialog(frame, null);

        if(fcReturn == JFileChooser.CANCEL_OPTION)
            throw new NoFileSelectedException();

        return fc.getSelectedFile();
    }

    /** This method opens reads a file and builds the tree.
     * @param file a file with a formatted behaviour tree (usually from a fileChooser).
     * @return the root node of the tree. */
    public Node getStructure(File file){

        //Create an array list of the lines in the file
        ArrayList<String> fileLines = loadFile(file);

        //Analyse fileLines and fill main array
        Node mainNode = new Node(fileLines.get(0));
        fillArray(fileLines, mainNode);

        return mainNode;
    }

    /** Loads all lines from a file into an arrayList of strings.
     *  @param file the file to be analysed.
     *  @return an arrayList of strings containing all lines from the file. */
    private ArrayList<String> loadFile(File file){

        ArrayList<String> array = new ArrayList<>();

        //Try to open file
        try (BufferedReader br = new BufferedReader(new FileReader(file))){

            String line;

            //Read line for line and add it to the array
            while((line = br.readLine()) != null){
                array.add(line);
            }
        }catch (IOException e) {
            System.out.println("Failed to open and read file: " + file.getName());
            e.printStackTrace();
        }

        return array;
    }

    /** Used to create a behavior tree of nodes from an array of lines (formatted).
     *  See fillArrayHelp-method for further explanation.
     *  @param fileLines an arrayList of strings.
     *  @param mainNode the root node of a behaviour tree.
     *  @return a root node containing the structure of the behavior tree. */
    private Node fillArray(ArrayList fileLines, Node mainNode){

        this.nextLine = 0;
        return fillArrayHELP(fileLines, mainNode, 0);
    }

    /** Uses recursion to fill a root node with
     *  behaviour tree structure from the given file.
     * @param fileLines an arrayList of strings.
     * @param node the root node of a behaviour tree.
     * @return a root node containing the structure of the behavior tree. */
    private Node fillArrayHELP(ArrayList fileLines, Node node, int currentLine){

        nextLine++;

        int currentLineLevel = getLevel(fileLines.get(currentLine).toString());

        //Reached end of lines in fileLines CHECK
        if(fileLines.size() <= nextLine){
            return node;
        }

        int nextLineLevel = getLevel(fileLines.get(nextLine).toString());


        //Next lvl is one lower
        if(nextLineLevel > currentLineLevel){
            do{
                node.addChild(fillArrayHELP(fileLines, new Node(fileLines.get(nextLine).toString()), nextLine));

                if(fileLines.size() <= nextLine) {
                    return node;
                }

                nextLineLevel = getLevel(fileLines.get(nextLine).toString());
            }while (nextLineLevel > currentLineLevel);
        }

        //Next line is on same level
        if(nextLineLevel == currentLineLevel){
            return node;
        }

        //Next line is one level above
        if(nextLineLevel < currentLineLevel){
            return node;
        }

        return node;
    }

    /** Counts spaces or tabs from the start in a line and returns how many tabs that equals too.
     *  This is used to determine the level of a line. This is determined from how many tabs
     *  there is at the start of a line.
     *  @param line a string of text.
     *  @return number of tabs at the start of a line. */
    private int getLevel(String line){

        int i = 0;

        //SPACE check. If this line starts with a space: we count spaces.
        if(line.charAt(0) == ' '){
            while(line.charAt(i) == ' '){
                i++;
            }

            return i/4;

        //TAB check. If this line starts with a tab: we count tabs
        }else if(line.charAt(0) == '\t'){
            while(line.charAt(i) == '\t'){
                i++;
            }

            return i;
        }else
            return i;
    }
}
