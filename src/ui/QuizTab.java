package ui;

import dataprocessing.MyDictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * ui
 * Created by Duy
 * Date 12/6/2021 - 10:15 PM
 * Description: ...
 */
public class QuizTab extends JPanel implements ActionListener {
    private MainFrame mainFrame;
    private JLabel title;

    private JButton guessWordButton;
    private JButton guessDefinitionButton;
    private JButton playButton;
    private JButton refreshButton;

    private JButton[] choicesButton;
    private int correctIndex;
    private JPanel quizPane;
    private HashMap<String,String> data;
    private MyDictionary dictionary = null;
    private int counterCorrection = 0;
    private JButton nextButton;
    private String type = null;


    public QuizTab(MainFrame parent){
        mainFrame = parent;
        dictionary = MainFrame.dictionary;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMaximumSize(new Dimension(900, 600));
        createUI();
    }

    private void createUI() {
        title = new JLabel("Quiz with me");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);

        add(title);
        JPanel optionPane = createOptionPane();
        add(optionPane);
        add(Box.createRigidArea(new Dimension(0, 10)));


    }

    private JPanel createOptionPane(){
        JPanel topPane = new JPanel();
//        topPane.setLayout(new BorderLayout());
        topPane.setMaximumSize(new Dimension(900, 70));
        topPane.setBorder(BorderFactory.createTitledBorder("Option"));

        guessWordButton = new JButton("Guess the word");
        guessWordButton.addActionListener(this);

        guessDefinitionButton = new JButton("Guess the definition");
        guessDefinitionButton.addActionListener(this);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);

        topPane.add(guessDefinitionButton);
        topPane.add(Box.createRigidArea(new Dimension(50,0)));
        topPane.add(guessWordButton);
        topPane.add(Box.createRigidArea(new Dimension(50,0)));
        topPane.add(refreshButton);
        return topPane;
    }

    public JPanel createQuestionAndAnswerPanel(String option){
        JPanel quizPane = new JPanel();
        quizPane.setLayout(new BoxLayout(quizPane, BoxLayout.Y_AXIS));
        JPanel pane = new JPanel();
        pane.setMaximumSize(new Dimension(900, 70));
        pane.setBorder(BorderFactory.createTitledBorder("Time"));
        JLabel correct = new JLabel("Correct: ");
        correct.setAlignmentX(CENTER_ALIGNMENT);
        correct.setFont(new Font("Arial", Font.ITALIC,15));
        JLabel value = new JLabel(String.valueOf(counterCorrection));
        correct.setFont(new Font("Arial", Font.ITALIC,15));
        pane.add(correct);
        pane.add(value);

        JPanel pane1 = new JPanel();
        pane1.setMaximumSize(new Dimension(900, 70));
        pane1.setBorder(BorderFactory.createTitledBorder("Question"));

        JPanel pane2 = new JPanel();
        pane2.setMaximumSize(new Dimension(900, 100));
        pane2.setBorder(BorderFactory.createTitledBorder("Answer"));
        pane2.add(Box.createRigidArea(new Dimension(0,50)));


        if (option.equalsIgnoreCase("GuessDefinition")){
            data = dictionary.getRandomQuestionForGuessingDefinition();
            addContentToPane(pane1,pane2,data);
        }

        quizPane.add(pane);
        quizPane.add(pane1);
        quizPane.add(pane2);
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        quizPane.add(Box.createRigidArea(new Dimension(0,10)));
        quizPane.add(nextButton);
        System.out.println(quizPane.getComponent(0));
        return quizPane;
    }

    public void addContentToPane(JPanel questionPane,JPanel answerPane, HashMap<String,String>data){
        choicesButton = new JButton[4];
        String[] keys = data.keySet().toArray(new String[0]);
        int i = 0;
        for (String key: keys){
            choicesButton[i] = new JButton();
            choicesButton[i].addActionListener(this);
            choicesButton[i].setActionCommand(key);
            choicesButton[i].setText(data.get(key));
            answerPane.add(choicesButton[i]);
            try{
                int temp = Integer.parseInt(key);
            }catch (Exception e){
                JLabel label = new JLabel("What is the definition of '" +key+"'?" );
                label.setFont(new Font("Arial",Font.ITALIC,15));
                label.setAlignmentX(CENTER_ALIGNMENT);
                questionPane.add(label);
                correctIndex = i;
            }
            i++;
        }
    }

//    public void setANewQuestion(){
//        if (type.equalsIgnoreCase("GuessDefinition")){
//            data = dictionary.getRandomQuestionForGuessingDefinition();
//            addContentToPane(,data);
//        }
//    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessDefinitionButton) {
            if (quizPane == null) {
                quizPane = createQuestionAndAnswerPanel("GuessDefinition");
                type = "GuessDefinition";
                add(quizPane);
            }
            revalidate();
            repaint();
        }
        if (e.getSource() == guessWordButton) {
            if (quizPane == null) {
                quizPane = createQuestionAndAnswerPanel("GuessWord");
                add(quizPane);
            }
            revalidate();
            repaint();
        }
        if (e.getSource() == refreshButton) {
            if (quizPane != null) {
                remove(quizPane);
                quizPane = null;
                choicesButton = null;
            }
            revalidate();
            repaint();
        }

        if (choicesButton != null){
            if (e.getSource() == choicesButton[correctIndex]) {
                JOptionPane.showMessageDialog(this, "Congratulation, it is the correct choice! ^_^");
            }
            for (int i = 0; i <4; i++) {
                if (correctIndex != i && e.getSource() == choicesButton[i]) {
                    JOptionPane.showMessageDialog(this, "This is the incorrect choice! :((");
                }
            }
        }






    }
}
