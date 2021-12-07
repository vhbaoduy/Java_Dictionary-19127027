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
    private MyDictionary myDictionary;

    private JTabbedPane tabbedPane;
    private DictionaryTab dictionaryTab;



    /**
     * Default constructor
     */
    public MainFrame(){
        myDictionary = new MyDictionary();
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
    public void setDisplay(){
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void addTabbedPane(){
        tabbedPane = new JTabbedPane();

        dictionaryTab = new DictionaryTab(this);
        tabbedPane.addTab("Dictionary",null, dictionaryTab, "Slang Dictionary: You can do anything!");

//        configTab = new ConfigUI();
//        tabbedPane.addTab("Config Server",null, configTab,"Config SQL server");

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setBounds(0,0,WIDTH,HEIGHT);
        container.add(tabbedPane);


    }

    public String[][] getData(){
        return myDictionary.convertToDataOfTable();
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDisplay();
    }


}
