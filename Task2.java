import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Task2 extends JFrame implements ActionListener {

    private JComboBox<String> subjectDropdown;
    private JTextField marksField;
    private JButton addButton, calculateButton, newStudentButton;
    private JTextArea resultArea;
    private HashMap<String, Integer> subjectMarks;

    public Task2() {
        setTitle("🎓 Student Marks & Grade Calculator 🎓");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(230, 248, 255));

        // Heading
        JLabel heading = new JLabel("Student Marks & Grade Calculator");
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBounds(50, 20, 550, 30);
        heading.setForeground(new Color(0, 102, 204));
        add(heading);

        // Subject Dropdown
        JLabel subjectLabel = new JLabel("Select Subject:");
        subjectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subjectLabel.setBounds(100, 80, 150, 25);
        add(subjectLabel);

        String[] subjects = {"Tamil", "English", "Maths", "Science", "Social"};
        subjectDropdown = new JComboBox<>(subjects);
        subjectDropdown.setBounds(250, 80, 200, 30);
        add(subjectDropdown);

        // Marks Field
        JLabel marksLabel = new JLabel("Enter Marks (0-100):");
        marksLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        marksLabel.setBounds(100, 130, 200, 25);
        add(marksLabel);

        marksField = new JTextField();
        marksField.setFont(new Font("Arial", Font.PLAIN, 16));
        marksField.setBounds(250, 130, 200, 30);
        add(marksField);

        // Add Button
        addButton = new JButton("Add Marks");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBounds(100, 180, 120, 35);
        addButton.setBackground(new Color(100, 200, 100));
        addButton.setForeground(Color.BLACK);
        addButton.addActionListener(this);
        add(addButton);

        // Calculate Button
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBounds(240, 180, 120, 35);
        calculateButton.setBackground(new Color(255, 165, 0));
        calculateButton.setForeground(Color.BLACK);
        calculateButton.addActionListener(this);
        add(calculateButton);

        // New Student Button
        newStudentButton = new JButton("New Student");
        newStudentButton.setFont(new Font("Arial", Font.BOLD, 14));
        newStudentButton.setBounds(380, 180, 150, 35);
        newStudentButton.setBackground(new Color(70, 130, 180));
        newStudentButton.setForeground(Color.WHITE);
        newStudentButton.addActionListener(this);
        add(newStudentButton);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Arial", Font.PLAIN, 16));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(245, 245, 245));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(100, 240, 430, 200);
        add(scrollPane);

        subjectMarks = new HashMap<>();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String subject = (String) subjectDropdown.getSelectedItem();
            try {
                int marks = Integer.parseInt(marksField.getText());
                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(this,
                            " Marks must be between 0 and 100!",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    subjectMarks.put(subject, marks);
                    JOptionPane.showMessageDialog(this,
                            " Marks added for " + subject,
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            marksField.setText("");
        }

        else if (e.getSource() == calculateButton) {
            if (subjectMarks.size() < 5) {
                JOptionPane.showMessageDialog(this,
                        " Please enter marks for all 5 subjects!",
                        "Incomplete Data", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int total = subjectMarks.values().stream().mapToInt(Integer::intValue).sum();
            double average = total / 5.0;
            String grade;

            if (average >= 90) grade = "A+";
            else if (average >= 75) grade = "A";
            else if (average >= 60) grade = "B";
            else if (average >= 40) grade = "C";
            else grade = "Fail ";

            resultArea.setText("Results:\n\n");
            for (String sub : subjectMarks.keySet()) {
                resultArea.append(sub + " : " + subjectMarks.get(sub) + "\n");
            }
            resultArea.append("\nTotal Marks: " + total + "/500");
            resultArea.append("\nAverage Percentage: " + String.format("%.2f", average) + "%");
            resultArea.append("\nGrade: " + grade);
        }

        else if (e.getSource() == newStudentButton) {
            subjectMarks.clear();
            resultArea.setText("");
            marksField.setText("");
            JOptionPane.showMessageDialog(this,
                    "Ready for new student marks entry!",
                    "New Student", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Task2();
    }
}
