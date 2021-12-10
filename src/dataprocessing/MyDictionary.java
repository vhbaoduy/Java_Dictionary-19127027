package dataprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * dataprocessing
 * Created by Duy
 * Date 12/6/2021 - 5:40 PM
 * Description: ...
 */
public class MyDictionary {
    private HashMap<String, ArrayList<String>> dictionary;
    private HashMap<String, ArrayList<String>> dupplicateWord;
    private String pathToDictionary = "./data/slang.txt";

    /**
     * Default constructor
     */
    public MyDictionary() {
        dictionary = new HashMap<String, ArrayList<String>>();
        dupplicateWord = new HashMap<String, ArrayList<String>>();
        readDataFromFile(pathToDictionary);
    }

    /**
     * Read data from file
     *
     * @param path String of path to dictionary
     * @return True if succeed or false if fail
     */
    public boolean readDataFromFile(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            line = bufferedReader.readLine(); // ignore first line
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                String word = getWordFromLine(line);
                ArrayList<String> meaning = getMeaningFromLine(line);
                dictionary.put(word, meaning);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Get meaning of word in the line
     *
     * @param line String of line in the file
     * @return Array list of Strong for meaning
     */
    public ArrayList<String> getMeaningFromLine(String line) {
        String word = "";
        try {
            String[] lineList = line.split("`");
            word = lineList[0];
            String meaning = lineList[1].replace("|",",");
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(meaning);
            return arrayList;

        } catch (Exception e) {
            System.out.println(word);
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get word in the line
     *
     * @param line String of line
     * @return String of word
     */
    public String getWordFromLine(String line) {
        try {
            String[] lineList = line.split("`");
            return lineList[0];

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Find meaning of word
     *
     * @param word String of word
     * @return Array list of meaning for word
     */
    public ArrayList<String> findMeaning(String word) {
        try {
            return dictionary.get(word);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    /**
     * Add a new word to dictionary
     *
     * @param word        String of word
     * @param meaning     String of meaning
     * @param isOverwrite optional
     */
    public void addANewWord(String word, String meaning, boolean... isOverwrite) {
        try {
            boolean flag = isOverwrite.length > 0 && isOverwrite[0];
            ArrayList<String> meaningList = findMeaning(word);
            if (meaningList == null) {
                meaningList = new ArrayList<String>();
                meaningList.add(meaning);
                dictionary.put(word, meaningList);
            } else {
                if (flag) {
                    meaningList.clear();
                    meaningList.add(meaning);
                } else {
                    meaningList.add(meaning);
                }
            }
        } catch (Exception e) {

        }

    }

    /**
     * Edit the meaning of word
     *
     * @param word    String of word
     * @param meaning String of meaning
     */
    public void editWord(String word, String meaning) {
        try {
            ArrayList<String> meaningList = findMeaning(word);

            meaningList.clear();
            meaningList.add(meaning);

        } catch (Exception e) {

        }
    }

    /**
     * Delete word of dictionary
     *
     * @param word String of word
     */
    public void deleteWord(String word) {
        try {
            dictionary.remove(word);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     *
     */
    public void resetDictionary() {
        dictionary = new HashMap<String, ArrayList<String>>();
        dupplicateWord = new HashMap<String, ArrayList<String>>();
        readDataFromFile(pathToDictionary);
    }

    public String[][] convertToDataOfTable() {
//        String[][] data = new String[dictionary.size() + dupplicateWord.size()][3];
        ArrayList<String[]> data = new ArrayList<>();

        String[] keys = dictionary.keySet().toArray(new String[0]);
//        String[] key2s = dupplicateWord.keySet().toArray(new String[0]);
        int i = 0;

        for (String key : keys) {
            ArrayList<String> meaning = dictionary.get(key);
//            data[i][0] = String.valueOf(i);
//            data[i][1] = key;
//            data[i][2] = String.valueOf(dictionary.get(key)).replace("[","").replace("]","");;

            if (meaning != null) {
                for (String value : meaning) {
                    String[] row = new String[3];
                    row[0] = String.valueOf(i);
                    row[1] = key;
                    row[2] = value;
                    i++;
                    data.add(row);
                }

            }else{
                String[] row = new String[3];
                row[0] = String.valueOf(i);
                row[1] = key;
                row[2] = "";
                i++;
                data.add(row);
            }
        }
        return data.toArray(new String[0][]);
    }

    public static void main(String[] args) {
        MyDictionary myDictionary = new MyDictionary();
//        myDictionary.addANewWord("BBC","Cute",false);
    }
}
