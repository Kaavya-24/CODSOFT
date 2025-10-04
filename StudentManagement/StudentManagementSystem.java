package StudentManagement;

import java.sql.*;
import java.util.ArrayList;

public class StudentManagementSystem {

    private Connection conn;

    public StudentManagementSystem() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL (replace password if needed)
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_db?serverTimezone=UTC",
                "root",
                ""
            );

            // Create table if it doesn't exist
            Statement st = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                         "roll INT PRIMARY KEY, " +
                         "name VARCHAR(100) NOT NULL, " +
                         "grade VARCHAR(10), " +
                         "age INT, " +
                         "email VARCHAR(100))";
            st.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            System.out.println(" MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Database connection failed!");
            e.printStackTrace();
        }
    }

    // Add student
    public boolean addStudent(Student s) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO students(roll, name, grade, age, email) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, s.getRollNumber());
            ps.setString(2, s.getName());
            ps.setString(3, s.getGrade());
            ps.setInt(4, s.getAge());
            ps.setString(5, s.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(" Error adding student!");
            e.printStackTrace();
            return false;
        }
    }

    // Update student (roll must already exist)
    public boolean updateStudent(Student s) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE students SET name=?, grade=?, age=?, email=? WHERE roll=?");
            ps.setString(1, s.getName());
            ps.setString(2, s.getGrade());
            ps.setInt(3, s.getAge());
            ps.setString(4, s.getEmail());
            ps.setInt(5, s.getRollNumber());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(" Error updating student!");
            e.printStackTrace();
            return false;
        }
    }

    // Remove student
    public boolean removeStudent(int roll) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE roll=?");
            ps.setInt(1, roll);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(" Error removing student!");
            e.printStackTrace();
            return false;
        }
    }

    // Search student
    public Student searchStudent(int roll) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE roll=?");
            ps.setInt(1, roll);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                    rs.getInt("roll"),
                    rs.getString("name"),
                    rs.getString("grade"),
                    rs.getInt("age"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println(" Error searching student!");
            e.printStackTrace();
        }
        return null;
    }

    // Get all students
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students ORDER BY roll");
            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("roll"),
                    rs.getString("name"),
                    rs.getString("grade"),
                    rs.getInt("age"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.out.println(" Error fetching students!");
            e.printStackTrace();
        }
        return list;
    }
}
