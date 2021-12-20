package dataprocessing;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * dataprocessing
 * Created by Duy
 * Date 12/6/2021 - 5:40 PM
 * Description: ...
 */
public class MyDictionary {
    final private int LENGTH_HISTORY = 100;
    private HashMap<String, ArrayList<String>> dictionary;
    private String dir = "data";
    private String pathToDataRestore = dir + "/" + "restore/slang.txt";
    private String pathToData = dir + "/" + "slang.txt";
    private String pathToHistory = dir + "/" + "history_search.txt";
    private ArrayList<String> history;

    /**
     * Default constructor
     */
    public MyDictionary() {
        history = new ArrayList<>();
        readDataFromFile(false);
        loadHistoryFromFile();
    }

    /**
     * Return success or not
     * @param isReset option
     * @return true/false
     */
    public boolean readDataFromFile(boolean isReset) {
        try {
            dictionary = new HashMap<String, ArrayList<String>>();
            File file= null;
            if (!isReset) {
                file = new File(pathToData);
                if (!file.exists()){
                    readDataFromFile(true);
                    storeCurrentData();
                    return true;
                }
            }else{
                file = new File(pathToDataRestore);
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
//            line = bufferedReader.readLine(); // ignore first line
            int i = 0;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
//                System.out.println(i++);
                String word = getWordFromLine(line);
                ArrayList<String> meaning = getMeaningFromLine(line,isReset);
                if (dictionary.get(word) != null){
                    System.out.println(word);
                }
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
    public ArrayList<String> getMeaningFromLine(String line,boolean isReset) {
        String word = "";
        try {
            String[] lineList = line.split("`");
            word = lineList[0];
            String meaning = "";
            ArrayList<String> arrayList = new ArrayList<>();
            if (lineList.length == 2) {
                if (isReset) {
                    meaning = lineList[1].replace("|", ",");
                    arrayList.add(meaning);
                }else {
                    String[] list = lineList[1].split("\\|");
                    for(String str:list){
                        arrayList.add(str);
                    }
                }
            }else{
                System.out.println(word);
                arrayList.add("");
            }
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
            storeCurrentData();
        } catch (Exception e) {

        }

    }

    /**
     * Edit word
     * @param word String of word
     * @param oldMeaning String of old meaning
     * @param newMeaning String of new meaning
     */
    public void editWord(String word, String oldMeaning, String newMeaning) {
        try {
            ArrayList<String> meaningList = findMeaning(word);
            int size = meaningList.size();
            if (size == 1) {
                meaningList.clear();
                meaningList.add(newMeaning);
            }else{
                for (int i = 0; i< size ; ++i){
                    if (meaningList.get(i).equals(oldMeaning)){
                        meaningList.set(i,newMeaning);
                    }
                }
            }
            storeCurrentData();

        } catch (Exception e) {

        }
    }

    /**
     * Delete word of dictionary
     *
     * @param word String of word
     */
    public void deleteWord(String word,String definition) {
        try {
            ArrayList<String> meaning = findMeaning(word);
            if (meaning.size() == 1){
                dictionary.remove(word);
            }else{
                meaning.remove(definition);
            }
            storeCurrentData();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * Reset Dictionary
     */
    public void resetDictionary() {
        readDataFromFile(true);
        storeCurrentData();
    }

    public String[][] convertToDataOfTable() {
        String[] keys = dictionary.keySet().toArray(new String[0]);
        return getMeaningByWords(keys);
    }

    public String[][] getMeaningByWords(String[] keys){

        ArrayList<String[]> data = new ArrayList<>();
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

    /**
     * Search by key == word
     * @param word String of word
     * @return String[][]
     */
    public String[][] searchByWord(String word){
        String[] keys = dictionary.keySet().toArray(new String[0]);
        ArrayList<String> resultKeys = new ArrayList<>();
        String wordLowercase = word.toLowerCase(Locale.ROOT);
        for (String key: keys){
            String temp = key.toLowerCase(Locale.ROOT);
            if (wordLowercase.compareToIgnoreCase(temp) == 0 || temp.contains(wordLowercase)){
                resultKeys.add(key);
            }
        }
        addHistory("WORD", word);
        return getMeaningByWords(resultKeys.toArray(new String[0]));
    }

    /**
     *  Search by definition
     * @param definition String of definition
     * @return String[][]
     */
    public String[][] searchByMeaning(String definition){
        String[] keys = dictionary.keySet().toArray(new String[0]);
        ArrayList<String> resultKeys = new ArrayList<>();
        String definitionLowercase = definition.toLowerCase(Locale.ROOT);
        for (String key: keys){
            ArrayList<String> meanings = dictionary.get(key);
            if (meanings != null){
                for (String value: meanings){
                    String temp = value.toLowerCase(Locale.ROOT);
                    if(temp.contains(definitionLowercase)){
                        resultKeys.add(key);
                        break;
                    }
                }
            }
        }
        addHistory("DEFINITION",definition);
        return getMeaningByWords(resultKeys.toArray(new String[0]));
    }

    /**
     * Store data to file
     */
    public void storeCurrentData(){
        try {
            FileWriter file = new FileWriter(pathToData);
            BufferedWriter bufferedWriter = new BufferedWriter(file);
            String[] keys = dictionary.keySet().toArray(new String[0]);
            for (String key : keys){
                String string="";
                ArrayList<String> meaning = dictionary.get(key);
                int size = meaning.size();
                if (meaning.size() > 1) {
                    for (int i = 0; i <size - 1;++i){
                        string+= meaning.get(i) +"|";
                    }
                    string += meaning.get(size-1);
                }else{
                    string += meaning.get(0);
                }
                bufferedWriter.write(key+"`"+string+"\n");
            }
            bufferedWriter.close();

        }catch(Exception e){

        }
    }

    /**
     * Add history to file and list
     * @param type Type of search (word or definition)
     * @param textSearch text in Search input
     */
    public void addHistory(String type, String textSearch){
        String string =  "Search by: " + type + " Text: " + textSearch;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        String historyString = "["+date+"] - "+string;
        history.add(historyString);
        try {
            File file = new File(pathToHistory);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            bufferedWriter.write(historyString+"\n");
            bufferedWriter.close();
        }catch (Exception e){
            System.out.println("History bug: " + e.getMessage());
        }
    }

    public void loadHistoryFromFile(){
        try{
            File file = new File(pathToHistory);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int i = 0;
            while(true){
                line = bufferedReader.readLine();
                if (line == null){
                    break;
                }
                history.add(line);
            }
//            System.out.println(history);
            bufferedReader.close();
        }catch (Exception e){
            System.out.println("History loading bug");
        }
    }

    /**
     * Get history
     * @return List String
     */
    public String[] getHistorySearch(){
        int size = history.size();
        String[] list = new String[size];
        int index = 0;
        for (int i = size - 1 ; i >=0 ; i--){
            list[index++] = history.get(i);
        }
//        System.out.println(Arrays.toString(list));
        return list;
    }

    /**
     * Get random slang word
     * @return String[][]
     */
    public String[][] getRandomWord(){
        String[] keys = dictionary.keySet().toArray(new String[0]);
        int index = (int)Math.floor(Math.random()*(keys.length+1));
        String[][] random = new String[1][2];
        String meaning = dictionary.get(keys[index]).get(0);
        random[0][0] = keys[index];
        random[0][1] = meaning;
        return random;
    }


    /**
     * Quiz with guess definition
     * @return Hashmap of data
     */
    public HashMap<String,String> getRandomQuestionForGuessingDefinition(){
        String[][] word = getRandomWord();
        String[] keys = dictionary.keySet().toArray(new String[0]);
        HashMap<String,String> indexes = new HashMap<>();
        while (indexes.size() < 3){
            int index = (int)Math.floor(Math.random()*(keys.length+1));
            if (!keys[index].equals(word[0][0])){
                indexes.put(String.valueOf(index),dictionary.get(keys[index]).get(0));
            }
        }
        indexes.put(word[0][0],word[0][1]);
        return indexes;
    }

    /**
     * Quiz with guess word
     * @return Hashmap of data
     */
    public HashMap<String,String> getRandomQuestionForGuessingWord(){
        String[][] word = getRandomWord();
        String[] keys = dictionary.keySet().toArray(new String[0]);
        HashMap<String,String> indexes = new HashMap<>();
        while (indexes.size() < 3){
            int index = (int)Math.floor(Math.random()*(keys.length+1));
            if (!keys[index].equals(word[0][0])){
                indexes.put(String.valueOf(index),keys[index]);
            }
        }
        indexes.put(word[0][1],word[0][0]);
        return indexes;
    }

}
