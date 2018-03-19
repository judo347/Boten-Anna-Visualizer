package botenAnna;

import java.io.*;
import java.util.ArrayList;

public class FileAnalyser {

    /** Main method to call. */
    public ArrayList<ArrayList> getStructureArrey(File file){

        //Get array containing lines from file
        ArrayList<String> fileLines = loadFile(file);

        //System.out.println(fileLines.toString()); //TODO: TMEP
        //for(int i = 0; i < fileLines.size(); i++) //TEMP
        //    System.out.println(getLevel(fileLines.get(i).toString())); //TEMP

        //
        ArrayList<ArrayList> mainArray = new ArrayList<>();
        System.out.println("CALLING RECURSION!"); //TODO: TEMP
        fillArray(fileLines, mainArray);
        System.out.println("RECURSION CALL ENDED!"); //TODO: TEMP

        displayMainArray(mainArray); //TODO: TEMP


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

        return null;
    }

    public ArrayList<ArrayList> fillArray(ArrayList fileLines, ArrayList mainArray){
        return fillArayHELP(fileLines, mainArray, 0);
    }

    public ArrayList<ArrayList> fillArayHELP(ArrayList fileLines, ArrayList mainArray, int currentLine){

        int nextLine = currentLine + 1;
        int currentLineLevel = getLevel(fileLines.get(currentLine).toString());

        //Reached end of line in mainArray
        if(fileLines.size() <= nextLine){
            System.out.println("We ran out of lines!");
            return mainArray;
        }

        int nextLineLevel = getLevel(fileLines.get(nextLine).toString());



        //Next lvl is one lower
        if(nextLineLevel > currentLineLevel){
            do{
                System.out.println("Next line is lower!");
                mainArray.add(fillArayHELP(fileLines, new ArrayList<ArrayList>(), nextLine));
                nextLine++;

                if(fileLines.size() <= nextLine) { //TODO TEST
                    break;
                }

                nextLineLevel = getLevel(fileLines.get(nextLine).toString());
            }while (nextLineLevel > currentLineLevel);

            if(getLevel(fileLines.get(nextLine-1).toString()) == nextLineLevel + 1 ){
                return mainArray;
            }
        }

        //Next line is on same level
        if(nextLineLevel == currentLineLevel){ //TODO: Burde jeg tilføje et check for om denne har nogle underlinjer?
            System.out.println("Next line is on the same level!");
            //if(getLevel(fileLines.get(nextLine+1).toString()) > currentLineLevel){
            //    mainArray.add(fillArayHELP(fileLines, new ArrayList<ArrayList>(), nextLine+1));
            //}

            //mainArray.add(fillArayHELP(fileLines, new ArrayList<ArrayList>(), nextLine));

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
