package StudentManagement;

public class Student {
    private int rollNumber;
    private String name;
    private String grade;
    private int age;
    private String email;

    public Student(int rollNumber, String name, String grade, int age, String email) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.grade = grade;
        this.age = age;
        this.email = email;
    }

    // Getters
    public int getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    public String getGrade() { return grade; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    // Setters (useful for editing)
    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
}
