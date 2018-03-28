package botenAnna;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class FileAnalyser {

    private int nextLine;

    public File getFile(Frame frame){
        JFileChooser fc = new JFileChooser();
        int fcReturn = fc.showDialog(frame, null);

        if(fcReturn == JFileChooser.CANCEL_OPTION)
            throw new NoFileSelectedException();

        return fc.getSelectedFile();
    }

    /** This method opens reads a file and build the tree.
     * @param file a file with a formatted behaviour tree (usually from a filechooser)
     * @return the root node of the tree. */
    public Node getStructure(File file){

        //Create an arraylist of the lines in the file
        ArrayList<String> fileLines = loadFile(file);

        //Analyse fileLines and fill main array
        Node mainNode = new Node(fileLines.get(0).toString());
        fillArray(fileLines, mainNode);

        return mainNode;
    }

    /** Loads all lines from a file into an arrayList of strings
     * @param file a file
     * @return an arrayList of strings containing all lines */
    public ArrayList<String> loadFile(File file){

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

    /** Calls fillArrayHELP. Read that one for description. */
    public Node fillArray(ArrayList fileLines, Node mainNode){

        this.nextLine = 0;
        return fillArrayHELP(fileLines, mainNode, 0);
    }

    /** Uses recursion to fill a root node with
     *  behaviour tree structure from the given file.
     * @param fileLines an arrayList of strings.
     * @param node the root node of a behaviour tree.
     * @return a root node containing the structure of the behaviour tree. */
    public Node fillArrayHELP(ArrayList fileLines, Node node, int currentLine){

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

    /** Counts spaces from the start in a line and returns how many tabs that equals too.
     *  This is used to determine the level of a line. This is determined from how many tabs
     *  there is at the start of a line.
     * @param line a string of text.
     * @return number of tab spaces at the start of a line. */
    public int getLevel(String line){

        int i = 0;

        while(line.charAt(i) == ' '){
            i++;
        }

        return i/4;
    }
}
