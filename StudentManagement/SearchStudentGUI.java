package StudentManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchStudentGUI extends JFrame implements ActionListener {

    private JTextField rollField;
    private JButton searchButton;
    private JTextArea resultArea;
    private StudentManagementSystem sms;

    public SearchStudentGUI(StudentManagementSystem sms) {
        this.sms = sms;

        setTitle("Search Student");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(153, 204, 255));

        JLabel title = new JLabel("Search Student");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(120, 20, 300, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setBounds(50, 80, 120, 25);
        add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(180, 80, 200, 25);
        add(rollField);

        searchButton = new JButton("Search");
        searchButton.setBounds(150, 120, 150, 40);
        searchButton.setBackground(new Color(102, 255, 153));
        searchButton.addActionListener(this);
        add(searchButton);

        resultArea = new JTextArea();
        resultArea.setBounds(50, 180, 380, 150);
        resultArea.setEditable(false);
        resultArea.setBackground(Color.WHITE);
        add(resultArea);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int roll = Integer.parseInt(rollField.getText().trim());
            Student s = sms.searchStudent(roll);
            if (s != null) {
                resultArea.setText(
                        "Roll Number: " + s.getRollNumber() +
                        "\nName: " + s.getName() +
                        "\nGrade: " + s.getGrade() +
                        "\nAge: " + s.getAge() +
                        "\nEmail: " + s.getEmail()
                );
            } else {
                resultArea.setText("Student not found!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Roll number must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
