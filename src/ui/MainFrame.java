package ui;

import dataprocessing.MyDictionary;

import javax.swing.*;
import java.awt.*;

/**
 * ui
 * Created by Duy
 * Date 12/7/2021 - 5:31 PM
 * Description: ...
 */
public class MainFrame extends JFrame {
    final private int WIDTH = 900;
    final  private int HEIGHT = 600;
    private Container container;
    public static MyDictionary dictionary;

    private JTabbedPane tabbedPane;
    private DictionaryTab dictionaryTab;
    private QuizTab quizTab;



    /**
     * Default constructor
     */
    public MainFrame(){
        dictionary = new MyDictionary();
        initialMainFrame();
        addTabbedPane();
    }

    /**
     * Initial my frame of application
     */
    private void initialMainFrame(){
        setTitle("Slang Dictionary");
        setBounds(300, 90, WIDTH, HEIGHT);
//        setDefaultLookAndFeelDecorated(true);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setMinimumSize(new Dimension(WIDTH,HEIGHT));
        container = getContentPane();
        container.setLayout(null);

    }
    public void setDisplay(boolean b){
        pack();
        setLocationRelativeTo(null);
        setVisible(b);
        String[][] randomWord = dictionary.getRandomWord();
        String message = String.format("Word: %s \nDefinition: %s ", randomWord[0][0], randomWord[0][1]);
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }


    private void addTabbedPane(){
        tabbedPane = new JTabbedPane();

        dictionaryTab = new DictionaryTab(this);
        tabbedPane.addTab("Dictionary",null, dictionaryTab, "Slang Dictionary: You can do anything!");

        quizTab = new QuizTab(this);
        tabbedPane.addTab("Quiz", null, quizTab,"Quiz with Slang word");


//        configTab = new ConfigUI();
//        tabbedPane.addTab("Config Server",null, configTab,"Config SQL server");

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setBounds(0,0,WIDTH,HEIGHT);
        container.add(tabbedPane);


    }




    public String[][] getData(){
        return dictionary.convertToDataOfTable();
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDisplay(true);
    }


}
