package StudentManagement;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class DisplayStudentsGUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private StudentManagementSystem sms;
    private boolean allowEdit; // ✅ decide mode: view-only OR edit-enabled

    public DisplayStudentsGUI(StudentManagementSystem sms, boolean allowEdit) {
        this.sms = sms;
        this.allowEdit = allowEdit;

        setTitle(allowEdit ? "Edit Students" : "All Students");
        setSize(700, 450);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 204, 153));

        JLabel title = new JLabel(allowEdit ? "Edit Students" : "All Students");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // ✅ Columns based on mode
        String[] columns = allowEdit ?
                new String[]{"Roll", "Name", "Grade", "Age", "Email", "Edit"} :
                new String[]{"Roll", "Name", "Grade", "Age", "Email"};

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return allowEdit && column == 5; // only "Edit" button editable
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);

        // ✅ Only add "Edit" button column if allowEdit == true
        if (allowEdit) {
            TableColumn editColumn = table.getColumn("Edit");
            editColumn.setCellRenderer(new ButtonRenderer());
            editColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
        }

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);

        loadTableData();

        setVisible(true);
    }

    // Load data from DB into table
    private void loadTableData() {
        model.setRowCount(0);
        ArrayList<Student> students = sms.getAllStudents();
        for (Student s : students) {
            if (allowEdit) {
                model.addRow(new Object[]{
                        s.getRollNumber(),
                        s.getName(),
                        s.getGrade(),
                        s.getAge(),
                        s.getEmail(),
                        "Edit"
                });
            } else {
                model.addRow(new Object[]{
                        s.getRollNumber(),
                        s.getName(),
                        s.getGrade(),
                        s.getAge(),
                        s.getEmail()
                });
            }
        }
    }

    // Refresh table (call after edit)
    public void refresh() {
        loadTableData();
    }

    // Renderer for button column
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(70, 130, 180));
            setForeground(Color.WHITE);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "Edit" : value.toString());
            return this;
        }
    }

    // Editor for button column — handles click
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(30, 144, 255));
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "Edit" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int roll = (int) model.getValueAt(row, 0);
                Student s = sms.searchStudent(roll);
                if (s != null) {
                    //  open AddStudentGUI in edit mode
                    AddStudentGUI editForm = new AddStudentGUI(sms, s);
                    // Refresh table after edit form closes
                    editForm.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            loadTableData();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(DisplayStudentsGUI.this,
                            "Student not found for editing.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}
