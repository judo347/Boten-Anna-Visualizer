package botenAnna;

import java.io.*;
import java.util.ArrayList;

public class FileAnalyser {

    public ArrayList<ArrayList> readFile(File file) throws FileNotFoundException {

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

        return i;
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
