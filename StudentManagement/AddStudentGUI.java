package StudentManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class AddStudentGUI extends JFrame implements ActionListener {

    private JTextField rollField, nameField, gradeField, ageField, emailField;
    private JButton addButton;
    private StudentManagementSystem sms;
    private boolean editing = false;        // are we editing existing student?
    private Student editingStudent = null;  // original student (if editing)

    public AddStudentGUI(StudentManagementSystem sms) {
        this(sms, null);
    }

    // If studentToEdit != null => open form in EDIT mode
    public AddStudentGUI(StudentManagementSystem sms, Student studentToEdit) {
        this.sms = sms;
        this.editing = (studentToEdit != null);
        this.editingStudent = studentToEdit;

        setTitle(editing ? "Edit Student" : "Add Student");
        setSize(520, 430);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(204, 153, 255));

        JLabel title = new JLabel(editing ? "Edit Student Details" : "Add New Student");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(110, 20, 300, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setBounds(40, 70, 120, 25);
        add(rollLabel);
        rollField = new JTextField();
        rollField.setBounds(180, 70, 260, 28);
        add(rollField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(40, 110, 120, 25);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(180, 110, 260, 28);
        add(nameField);

        JLabel gradeLabel = new JLabel("Grade:");
        gradeLabel.setBounds(40, 150, 120, 25);
        add(gradeLabel);
        gradeField = new JTextField();
        gradeField.setBounds(180, 150, 260, 28);
        add(gradeField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(40, 190, 120, 25);
        add(ageLabel);
        ageField = new JTextField();
        ageField.setBounds(180, 190, 260, 28);
        add(ageField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40, 230, 120, 25);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(180, 230, 260, 28);
        add(emailField);

        addButton = new JButton(editing ? "Update Student" : "Add Student");
        addButton.setBounds(170, 290, 180, 40);
        addButton.setBackground(new Color(102, 204, 255));
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.addActionListener(this);
        add(addButton);

        // If editing, prefill fields and disable roll editing (roll is primary key)
        if (editing && editingStudent != null) {
            rollField.setText(String.valueOf(editingStudent.getRollNumber()));
            rollField.setEnabled(false);
            nameField.setText(editingStudent.getName());
            gradeField.setText(editingStudent.getGrade());
            ageField.setText(String.valueOf(editingStudent.getAge()));
            emailField.setText(editingStudent.getEmail());
        }

        setVisible(true);
    }

    // Validation helpers
    private boolean isNumeric(String s) {
        return s != null && s.matches("\\d+");
    }

    private boolean isNameValid(String s) {
        return s != null && s.matches("[A-Za-z ]+");
    }

    private boolean isGradeValid(String s) {
        return s != null && s.matches("[A-Za-z]+");
    }

    private boolean isEmailValid(String s) {
        if (s == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Gather inputs as strings first
        String rollText = rollField.getText().trim();
        String name = nameField.getText().trim();
        String grade = gradeField.getText().trim();
        String ageText = ageField.getText().trim();
        String email = emailField.getText().trim();

        // Check missing fields
        StringBuilder missing = new StringBuilder();
        if (rollText.isEmpty()) missing.append("Roll Number, ");
        if (name.isEmpty()) missing.append("Name, ");
        if (grade.isEmpty()) missing.append("Grade, ");
        if (ageText.isEmpty()) missing.append("Age, ");
        if (email.isEmpty()) missing.append("Email, ");

        if (missing.length() > 0) {
            // remove trailing comma+space
            String msg = missing.substring(0, missing.length() - 2) + " MISSING";
            JOptionPane.showMessageDialog(this, msg, "Missing Fields", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Type/format validations
        if (!isNumeric(rollText)) {
            JOptionPane.showMessageDialog(this, "ROLL NO SHOULD BE A NUMBER", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isNumeric(ageText)) {
            JOptionPane.showMessageDialog(this, "AGE SHOULD BE A NO", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isNameValid(name)) {
            JOptionPane.showMessageDialog(this, "Name should contain only letters and spaces", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isGradeValid(grade)) {
            JOptionPane.showMessageDialog(this, "Grade should contain only letters", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isEmailValid(email)) {
            JOptionPane.showMessageDialog(this, "INVALID EMAIL FORMAT", "Invalid", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int roll = Integer.parseInt(rollText);
        int age = Integer.parseInt(ageText);

        Student s = new Student(roll, name, grade, age, email);

        boolean success;
        if (editing) {
            success = sms.updateStudent(s);
            if (success) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating student (maybe roll not found).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            success = sms.addStudent(s);
            if (success) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error adding student. Roll number might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
