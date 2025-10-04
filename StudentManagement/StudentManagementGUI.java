package StudentManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentManagementGUI extends JFrame implements ActionListener {

    private JButton addButton, removeButton, searchButton, displayButton, editButton;
    private StudentManagementSystem sms;

    public StudentManagementGUI() {
        sms = new StudentManagementSystem();

        setTitle("Student Management System");
        setSize(520, 420);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(153, 102, 255));

        JLabel title = new JLabel("🎓 Student Management System 🎓");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(50, 30, 420, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        addButton = new JButton("Add Student");
        addButton.setBounds(60, 90, 180, 40);
        addButton.setBackground(new Color(102, 204, 255));
        addButton.addActionListener(this);
        add(addButton);

        removeButton = new JButton("Remove Student");
        removeButton.setBounds(280, 90, 180, 40);
        removeButton.setBackground(new Color(255, 102, 102));
        removeButton.addActionListener(this);
        add(removeButton);

        searchButton = new JButton("Search Student");
        searchButton.setBounds(60, 150, 180, 40);
        searchButton.setBackground(new Color(102, 255, 153));
        searchButton.addActionListener(this);
        add(searchButton);

        displayButton = new JButton("Display All Students");
        displayButton.setBounds(280, 150, 180, 40);
        displayButton.setBackground(new Color(255, 204, 102));
        displayButton.addActionListener(this);
        add(displayButton);

        // New Edit Students button - opens table where each row has Edit button
        editButton = new JButton("Edit Student");
        editButton.setBounds(170, 210, 180, 40);
        editButton.setBackground(new Color(100, 149, 237));
        editButton.addActionListener(this);
        add(editButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) new AddStudentGUI(sms);
        else if (e.getSource() == removeButton) new RemoveStudentGUI(sms);
        else if (e.getSource() == searchButton) new SearchStudentGUI(sms);
        else if (e.getSource() == displayButton) new DisplayStudentsGUI(sms,false);
        else if (e.getSource() == editButton) new DisplayStudentsGUI(sms,true); // same table allows edit
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementGUI());
    }
}
