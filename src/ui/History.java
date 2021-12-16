package ui;

import javax.swing.*;
import java.awt.*;

/**
 * ui
 * Created by Duy
 * Date 12/6/2021 - 10:16 PM
 * Description: ...
 */
public class History extends JFrame {
    final private int WIDTH = 360;
    final private int HEIGHT = 240;

    private Container container;
    private JTextArea textArea;
    private String[] history = null;

    public History(String[] str){
        history = str;
        initialForm();
        createUI();
        setDisplay(true);

    }

    public void initialForm() {
        setTitle("History");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setBounds(400, 200, WIDTH, HEIGHT);

        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    }
    public void setDisplay(boolean flag) {
        pack();
        setVisible(flag);
    }
    public void createUI(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Detail History"));
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial",Font.ITALIC,12));
        textArea.setEditable(false);
        if (history != null){
            for(String str:history){
                textArea.append(str + "\n");
            }
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
        container.add(panel);


    }
}
