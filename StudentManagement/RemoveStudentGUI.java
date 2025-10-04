package StudentManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RemoveStudentGUI extends JFrame implements ActionListener {

    private JTextField rollField;
    private JButton removeButton;
    private StudentManagementSystem sms;

    public RemoveStudentGUI(StudentManagementSystem sms) {
        this.sms = sms;

        setTitle("Remove Student");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 153, 204));

        JLabel title = new JLabel("Remove Student");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(80, 20, 300, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setBounds(50, 80, 120, 25);
        add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(150, 80, 180, 25);
        add(rollField);

        removeButton = new JButton("Remove");
        removeButton.setBounds(120, 130, 150, 40);
        removeButton.setBackground(new Color(255, 102, 102));
        removeButton.addActionListener(this);
        add(removeButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int roll = Integer.parseInt(rollField.getText().trim());
            if (sms.removeStudent(roll)) {
                JOptionPane.showMessageDialog(this, "Student removed successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Roll number must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
