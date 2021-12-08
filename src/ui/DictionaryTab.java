package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * ui
 * Created by Duy
 * Date 12/6/2021 - 10:15 PM
 * Description: ...
 */
public class DictionaryTab extends JPanel implements ActionListener, MouseListener {

    // Set frame
    private MainFrame mainFrame;


    private final String[] columns = {"Index","Word", "Definition"};
    private String[][] data = {{"",""}};
    // Title of pane, top pane
    private JLabel title;
    private JLabel searchText;
    private JRadioButton wordButton;
    private JRadioButton definitionButton;
    private JTextField searchInput;
    private JButton searchButton;
    private JButton historyButton;

    // Middle - pane
    private JTable table;

    // Bottom pane
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton resetButton;

    // Display
    private JLabel selectedRow;
    private JTextField rowField;
    private JFileChooser fileChooser;
    private SlangForm slangForm;


    public DictionaryTab(MainFrame parent) {
        mainFrame = parent;
        data = mainFrame.getData();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMaximumSize(new Dimension(900, 600));
        createUI();
    }

    private void createUI() {
        title = new JLabel("Slang Dictionary");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(CENTER_ALIGNMENT);

        add(title);
        JPanel topPane = createTopPane();
        add(topPane);
        add(Box.createRigidArea(new Dimension(0,10)));
        JPanel middlePane = createMiddlePane();
        JPanel bottomPane = createBottomPane();
        add(middlePane);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(bottomPane);
    }

    private JPanel createTopPane() {
        JPanel topPane = new JPanel();
//        topPane.setLayout(new BorderLayout());
        topPane.setMaximumSize(new Dimension(900, 70));
        topPane.setBorder(BorderFactory.createTitledBorder("Search"));
        searchText = new JLabel("Search by: ");

        topPane.add(searchText);

        wordButton = new JRadioButton("Word");
        definitionButton = new JRadioButton("Definition");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(wordButton);
        buttonGroup.add(definitionButton);
        topPane.add(wordButton);
        topPane.add(definitionButton);
        topPane.add(Box.createRigidArea(new Dimension(20,0)));


        searchInput = new JTextField();
        searchInput.setFont(new Font("Arial", Font.ITALIC, 15));
        searchInput.setMinimumSize(new Dimension(100,20));
        searchInput.setMaximumSize(new Dimension(300,20));
        searchInput.setPreferredSize(new Dimension(250,20));
        topPane.add(searchInput);
        searchButton = new JButton("Search");
        topPane.add(searchButton);

        historyButton = new JButton("History");
        topPane.add(historyButton);

//        topPane.add(Box.createRigidArea(new Dimension(5, 0)));

        return topPane;
    }

    private JPanel createMiddlePane() {

        JPanel tablePane = new JPanel();
        tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.Y_AXIS));
        tablePane.setMaximumSize(new Dimension(900, 300));
        tablePane.setMaximumSize(new Dimension(900,350));
        tablePane.setPreferredSize(new Dimension(900,350));
        tablePane.setBorder(BorderFactory.createTitledBorder("Dictionary"));
        tablePane.setLocation(0, 0);


        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        table = new JTable(data, columns);
        table.setModel(tableModel);
        table.setGridColor(Color.darkGray);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(this);

        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(300);
        table.getColumnModel().getColumn(1).setMinWidth(200);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD, 13));
        table.setRowHeight(20);

//
//        ListSelectionModel model = table.getSelectionModel();
//        model.addListSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tablePane.add(scrollPane);


        JPanel selectPane = new JPanel();
        selectPane.setLayout(new BoxLayout(selectPane,BoxLayout.X_AXIS));

        selectedRow = new JLabel("Selected Row: ");
        rowField = new JTextField();
        rowField.setEditable(false);
        rowField.setText("None");
        rowField.setMaximumSize(new Dimension(50,20));
        rowField.setFont(new Font("Arial", Font.BOLD,15));

        selectPane.add(Box.createRigidArea(new Dimension(700,0)));
        selectPane.add(selectedRow);
        selectPane.add(rowField);

        tablePane.add(selectPane);
        return tablePane;
    }

    private JPanel createBottomPane() {

        JPanel bottomPane = new JPanel();
        bottomPane.setMaximumSize(new Dimension(900, 60));
        bottomPane.setBorder(BorderFactory.createTitledBorder("Menu"));



        resetButton = new JButton("Reset Dictionary");
        resetButton.addActionListener(this);

        addButton = new JButton("Add word");
        addButton.addActionListener(this);

        deleteButton = new JButton("Delete word");
        deleteButton.addActionListener(this);

        editButton = new JButton("Edit word");
        editButton.addActionListener(this);

//        bottomPane.add(selectedRow);

        bottomPane.add(resetButton);
        bottomPane.add(addButton);
        bottomPane.add(editButton);
        bottomPane.add(deleteButton);

        return bottomPane;

    }

    /**
     * Get model not edit, only select
     * @param data_ String[][] Data of table
     * @param columns_ String [] Columns name
     * @return model
     */
    public static DefaultTableModel getModel(String[][] data_, String[] columns_){
        return new DefaultTableModel(data_, columns_) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row =table.getSelectedRow();
        if (e.getClickCount() == 1){
            System.out.println(table.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            System.out.println(model.getDataVector().elementAt(table.getSelectedRow()));
        }
        if (e.getClickCount() == 2){
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            Vector<Vector> vector = model.getDataVector();
            String word = (String) vector.elementAt(row).elementAt(1);
            String definition = (String) vector.elementAt(row).elementAt(2);
            String message = String.format("Word: %s \nDefinition: %s ",word,definition);
            JOptionPane.showMessageDialog(this,message,"Information",JOptionPane.INFORMATION_MESSAGE);
        }

        rowField.setText(String.valueOf(row));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
