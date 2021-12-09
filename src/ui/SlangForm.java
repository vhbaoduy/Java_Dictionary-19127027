package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * ui
 * Created by Duy
 * Date 12/7/2021 - 5:43 PM
 * Description: ...
 */
public class SlangForm extends JFrame implements ActionListener {
    final private int WIDTH = 360;
    final private int HEIGHT = 240;

    private String goal;
    private Vector rowData;
    private Container container;

    private JLabel wordText;
    private JLabel definitionText;
    private JTextField wordInput;
    private JTextField definitionInput;
    private JButton submitButton;

    public SlangForm(String goal, Vector row){
        this.goal = goal;
        rowData = row;
        initialForm();
        createForm();
        setByGoal();
        setDisplay(true);
    }

    public void initialForm(){
        if (goal.compareToIgnoreCase("add") == 0) {
            setTitle("Add a new word");
        }
        if (goal.compareToIgnoreCase("edit") == 0) {
            setTitle("Edit a word");
        }
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setResizable(false);

        setBounds(600,300,WIDTH,HEIGHT);

        container = getContentPane();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
    }


    public void setDisplay(boolean flag){
        pack();
        setVisible(flag);
    }

    public void createForm(){
        JLabel title = new JLabel("Slang Dictionary");
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setAlignmentX(CENTER_ALIGNMENT);

        container.add(Box.createRigidArea(new Dimension(0,20)));
        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0,20)));


        JPanel wordPane = new JPanel();
        wordPane.setLayout(new BoxLayout(wordPane, BoxLayout.X_AXIS));

        wordText = new JLabel("Word:");
        wordText.setFont(new Font("Arial", Font.ITALIC,15));

        wordInput = new JTextField();
        wordInput.setFont(new Font("Arial", Font.PLAIN, 15));
        wordInput.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));

        wordPane.add(Box.createRigidArea(new Dimension(20,0)));
        wordPane.add(wordText);
        wordPane.add(Box.createRigidArea(new Dimension(38,0)));
        wordPane.add(wordInput);
        wordPane.add(Box.createRigidArea(new Dimension(20,0)));



        JPanel definitionPane = new JPanel();
        definitionPane.setLayout(new BoxLayout(definitionPane, BoxLayout.X_AXIS));

        definitionText = new JLabel("Definition:");
        definitionText.setFont(new Font("Arial", Font.ITALIC,15));

        definitionInput = new JTextField();
        definitionInput.setFont(new Font("Arial", Font.PLAIN, 15));
        definitionInput.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));

        definitionPane.add(Box.createRigidArea(new Dimension(20,0)));
        definitionPane.add(definitionText);
        definitionPane.add(Box.createRigidArea(new Dimension(10,0)));
        definitionPane.add(definitionInput);
        definitionPane.add(Box.createRigidArea(new Dimension(20,0)));


        submitButton = new JButton("");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);

        container.add(wordPane);
        container.add(Box.createRigidArea(new Dimension(0,20)));
        container.add(definitionPane);
        container.add(Box.createRigidArea(new Dimension(0,20)));
        container.add(submitButton);
    }

    public void setByGoal(){
        if (goal.compareToIgnoreCase("add") == 0){
            submitButton.setText("Add");
        }
        if (goal.compareToIgnoreCase("edit") == 0){
            submitButton.setText("Edit");
            String word = (String) rowData.elementAt(1);
            String definition = (String) rowData.elementAt(2);
            wordInput.setText(word);
            definitionInput.setText(definition);
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
