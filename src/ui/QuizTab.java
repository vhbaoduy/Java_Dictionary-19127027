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
    private JPanel questionPane= null;
    private JLabel questionText = null;
    private JPanel answerPane = null;
    private HashMap<String,String> data;
    private MyDictionary dictionary = null;
    private int counterCorrection = 0;
    private JLabel counterText;
    private JButton nextButton;
    private String type;


    public QuizTab(MainFrame parent){
        mainFrame = parent;
        dictionary = MainFrame.dictionary;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
        pane.setBorder(BorderFactory.createTitledBorder("Recording"));
        JLabel correct = new JLabel("Correct: ");
        correct.setAlignmentX(CENTER_ALIGNMENT);
        correct.setFont(new Font("Arial", Font.ITALIC,15));
        counterText = new JLabel(String.valueOf(counterCorrection));
        counterText.setFont(new Font("Arial", Font.ITALIC,15));
        pane.add(correct);
        pane.add(counterText);

        questionPane = new JPanel();
        questionPane.setMaximumSize(new Dimension(900, 70));
        questionPane.setBorder(BorderFactory.createTitledBorder("Question"));

        questionText = new JLabel("");
        questionText.setFont(new Font("Arial",Font.ITALIC,15));
        questionText.setAlignmentX(CENTER_ALIGNMENT);
        questionPane.add(questionText);

        answerPane = new JPanel();
//        answerPane.setLayout(new BoxLayout(answerPane,BoxLayout.PAGE_AXIS));
        answerPane.setMaximumSize(new Dimension(900, 100));
        answerPane.setBorder(BorderFactory.createTitledBorder("Answer"));
        answerPane.add(Box.createRigidArea(new Dimension(0,50)));


        if (option.equalsIgnoreCase("GuessDefinition")){
            data = dictionary.getRandomQuestionForGuessingDefinition();
        }
        if (option.equalsIgnoreCase("GuessWord")){
            data = dictionary.getRandomQuestionForGuessingWord();
        }
        addContentToPane(data);
        quizPane.add(pane);
        quizPane.add(questionPane);
        quizPane.add(answerPane);
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        quizPane.add(Box.createRigidArea(new Dimension(0,10)));
        quizPane.add(nextButton);
        System.out.println(quizPane.getComponent(0));
        return quizPane;
    }

    public void addContentToPane(HashMap<String,String>data){
        choicesButton = new JButton[4];
        String[] keys = data.keySet().toArray(new String[0]);
        int i = 0;
        for (String key: keys){
            choicesButton[i] = new JButton();
            choicesButton[i].addActionListener(this);
//            choicesButton[i].setActionCommand(key);
            choicesButton[i].setText(data.get(key));
            answerPane.add(choicesButton[i]);
            try{
                int temp = Integer.parseInt(key);
            }catch (Exception e){
                if (type.equalsIgnoreCase("GuessDefinition")) {
                    questionText.setText("What is the definition of '" + key + "'?");
                }
                if (type.equalsIgnoreCase("GuessWord")){
                    questionText.setText("What is the word that meaning '" + key + "'?");
                }
                correctIndex = i;
            }
            i++;
        }
    }

    public void setANewQuestion(){
        if (type.equalsIgnoreCase("GuessDefinition")){
            data = dictionary.getRandomQuestionForGuessingDefinition();
        }
        if (type.equalsIgnoreCase("GuessWord")){
            data = dictionary.getRandomQuestionForGuessingWord();
        }
        for (int i = 0; i < 4;++i){
            answerPane.remove(choicesButton[i]);
        }
        revalidate();
        repaint();
        addContentToPane(data);
    }

    public void refresh(){
        remove(quizPane);
        quizPane = null;
        choicesButton = null;
        counterCorrection = 0;
        type = null;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessDefinitionButton) {
            if (quizPane != null) {
                refresh();
            }
            type = "GuessDefinition";
            quizPane = createQuestionAndAnswerPanel("GuessDefinition");

            add(quizPane);
            revalidate();
            repaint();
        }
        if (e.getSource() == guessWordButton) {
            if (quizPane != null) {
                refresh();
            }
            type = "GuessWord";
            quizPane = createQuestionAndAnswerPanel("GuessWord");
            add(quizPane);
            revalidate();
            repaint();
        }
        if (e.getSource() == refreshButton) {
            if (quizPane != null) {
                refresh();
            }
            revalidate();
            repaint();
        }

        if (choicesButton != null){
            if (e.getSource() == choicesButton[correctIndex]) {
                JOptionPane.showMessageDialog(this, "Congratulation, it is the correct choice! ^_^");
                counterCorrection++;
                counterText.setText(String.valueOf(counterCorrection));
            }
            for (int i = 0; i <4; i++) {
                if (correctIndex != i && e.getSource() == choicesButton[i]) {
                    JOptionPane.showMessageDialog(this, "This is the incorrect choice! :((");
                }
            }
        }
        if (e.getSource() == nextButton){
            setANewQuestion();
        }






    }
}
