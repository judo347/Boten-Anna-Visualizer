package botenAnna;

import java.io.*;
import java.util.ArrayList;

public class FileAnalyser {

    private int nextLine;

    /** Main method to call. */
    public Node getStructureArrey(File file){

        //Get array containing lines from file
        ArrayList<String> fileLines = loadFile(file);

        //Analyse fileLines and fill main array
        Node mainNode = new Node(fileLines.get(0).toString());
        //System.out.println("CALLING RECURSION!"); //TODO: TEMP
        fillArray(fileLines, mainNode);
        //System.out.println("RECURSION CALL ENDED!"); //TODO: TEMP

        //Display the array
        //displayMainArray(mainArray); //TODO: TEMP
        //displayMainNode(mainNode); //TODO: TEMP


        /*
        //TODO: TEMP TEST
        ArrayList<ArrayList> threeOne = new ArrayList<>();
        ArrayList<ArrayList> threeTwo = new ArrayList<>();

        ArrayList<ArrayList> threeThree = new ArrayList<>();
        ArrayList<ArrayList> threeFour = new ArrayList<>();

        ArrayList<ArrayList> twoOne = new ArrayList<>();
        twoOne.add(threeOne);
        twoOne.add(threeTwo);

        ArrayList<ArrayList> twoTwo = new ArrayList<>();
        twoTwo.add(threeThree);
        twoTwo.add(threeFour);

        ArrayList<ArrayList> one = new ArrayList<>();
        one.add(twoOne);
        one.add(twoTwo);

        displayMainArray(one);
        */

        return mainNode;
    }

    public Node fillArray(ArrayList fileLines, Node mainArray){

        nextLine = 0;

        return fillArayHELP(fileLines, mainArray, 0);
    }

    public Node fillArayHELP(ArrayList fileLines, Node mainArray, int currentLine){

        nextLine++;

        int currentLineLevel = getLevel(fileLines.get(currentLine).toString());

        //Reached end of lines in fileLines
        if(fileLines.size() <= nextLine){
            System.out.println("We ran out of lines!");
            return mainArray;
        }

        int nextLineLevel = getLevel(fileLines.get(nextLine).toString());


        //Next lvl is one lower
        if(nextLineLevel > currentLineLevel){
            do{
                System.out.println("Next line is lower!");
                mainArray.addChild(fillArayHELP(fileLines, new Node(fileLines.get(nextLine).toString()), nextLine));

                if(fileLines.size() <= nextLine) {
                    return mainArray;
                }

                nextLineLevel = getLevel(fileLines.get(nextLine).toString());
            }while (nextLineLevel > currentLineLevel);
        }

        //Next line is on same level
        if(nextLineLevel == currentLineLevel){
            System.out.println("Next line is on the same level!");
            return mainArray;
        }

        //Next line is one level above
        if(nextLineLevel < currentLineLevel){
            System.out.println("Next line is above!");
            return mainArray;
        }

        System.out.println("SHOULD NOT GET HERE, END OF fillArray");
        return mainArray;
    }

    /** Loads all lines from file into array */
    public ArrayList<String> loadFile(File file){

        ArrayList<String> array = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                array.add(line);
            }
        }catch (IOException e) {
            System.out.println("Failed to open and read file: " + file.getName());
            e.printStackTrace();
        }

        return array;
    }

    /** Parse and ArrayList of arraylists to get their hierarchy displayed */
    public void displayMainArray(ArrayList mainArray){
        int currentLevel = 0;

        displayMainArrayHELP(mainArray, 0);

    }

    public void displayMainArrayHELP(ArrayList<ArrayList> currentArray, int currentLevel){

        currentLevel++;

        for(int i = 0; i < currentLevel; i++){
            System.out.print("S");
        }
        System.out.println();

        if(currentArray.size() == 0)
            return;
        else{
            for(int i = 0; i < currentArray.size(); i++){
                displayMainArrayHELP(currentArray.get(i), currentLevel);
            }
        }

        /*
        if(currentArray.size() > currentLevel){
            for(int i = 0; i < currentArray.size(); i++){
                displayMainArrayHELP(currentArray.get(i), currentLevel);
            }
        }*/

    }

    public void displayMainNode(Node mainNode){
        int currentLevel = 0;

        displayMainNodeHELP(mainNode, 0);
    }

    public void displayMainNodeHELP(Node currentArray, int currentLevel){

        currentLevel++;

        System.out.println(currentArray.getNodeName());

        if(currentArray.getArrayList().size() == 0){
            return;
        }else{
            for(int i = 0; i < currentArray.getArrayList().size(); i++){
                displayMainNodeHELP(currentArray.getArrayList().get(i), currentLevel);
            }
        }
    }

    //OLD

    public ArrayList<ArrayList> readFile(File file) {

        int currentLevel = 0;
        int currentMaxLevel = 0;
        int numberOfLevels = getNumberOfLevels(file);
        String[] behaviourTree = new String[numberOfLevels];

        ArrayList<Integer> levelElements = new ArrayList<>();
        ArrayList<ArrayList> mainList = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                levelElements.add(getLevel(line));

            }
        }catch (IOException e) {
            System.out.println("Failed to open and read file:");
            e.printStackTrace();
        }

        System.out.println(levelElements);

        return null;
    }

    public int getLevel(String line){

        int i = 0;

        while(line.charAt(i) == ' '){
            i++;
        }

        return i/4;
    }

    public int getNumberOfLevels(File file) {

        int currentMaxLevel = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                if(getLevel(line) > currentMaxLevel)
                    currentMaxLevel = getLevel(line);
            }
        }catch (IOException e) {
            System.out.println("Failed to open and read file:");
            e.printStackTrace();
        }

        return (currentMaxLevel /4) + 1;
    }
}
