package schoolapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import schoolapp.SchoolClass;

public class TeachersPage extends JFrame implements ActionListener {
    JLabel showLabel;
    JPanel menu;
    JPanel mainPanel;
    JPanel gradesPanel;
    JPanel chatPanel;
    JPanel projectsPanel;
    JButton gradesButton;
    JButton chatButton;
    JButton projectsButton;
    JPanel showGradesPanel;
    JPanel addGradePanel;
    JPanel showProjectsPanel;
    JPanel addProjectPanel;
    String login;
    Teacher teacher;
    ArrayList<SchoolClass> classes;
    ArrayList<Student> students;
    CardLayout cardLayout;

    JButton showGrades; // Deklaracja showGrades
    JButton addGrade; // Deklaracja addGrade
    private JButton showProjects;
    private JButton addProject;

    TeachersPage(String title, String login) {
        super(title);
        this.login = login;
        teacher = new Teacher(0, null, null, "Teacher", login);
        classes = new ArrayList<>();
        students = new ArrayList<>();
        personaliaCheck(login);
        init();
    }

    void personaliaCheck(String loginDb) {
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String loginCheckQueryString = "SELECT * FROM TEACHERS WHERE LOGIN LIKE ?;";
        PreparedStatement loginCheckQuery = null;
        try {
            con = DriverManager.getConnection(url, "root", "");
            loginCheckQuery = con.prepareStatement(loginCheckQueryString);
            loginCheckQuery.setString(1, login);
            rs = loginCheckQuery.executeQuery();
            if (rs.next()) {
                teacher.first_name = rs.getString("FIRST_NAME");
                teacher.last_name = rs.getString("LAST_NAME");
                teacher.teacher_id = rs.getInt("TEACHER_ID");
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (loginCheckQuery != null) loginCheckQuery.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    }

    void init() {
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menu = new JPanel();
        showLabel = new JLabel("You are logged in as: " + teacher.first_name + " " + teacher.last_name);
        gradesButton = new JButton("Grades");
        chatButton = new JButton("Chat");
        projectsButton = new JButton("Projects");
        gradesButton.addActionListener(this);
        chatButton.addActionListener(this);
        projectsButton.addActionListener(this);

        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(showLabel);
        menu.add(gradesButton);
        menu.add(chatButton);
        menu.add(projectsButton);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        gradesPanel = new JPanel(new BorderLayout());
        chatPanel = new JPanel(new BorderLayout());
        projectsPanel = new JPanel(new BorderLayout());

        gradesPanelInit();
        //chatPanelInit();
        projectsPanelInit();

        mainPanel.add(gradesPanel, "Grades");
        mainPanel.add(chatPanel, "Chat");
        mainPanel.add(projectsPanel, "Projects");

        setLayout(new BorderLayout());
        add(menu, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == gradesButton) {
            cardLayout.show(mainPanel, "Grades");
        } else if (source == chatButton) {
            cardLayout.show(mainPanel, "Chat");
            chatPanelInit();
        } else if (source == projectsButton) {
            cardLayout.show(mainPanel, "Projects");
        }
    }

    void gradesPanelInit() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel classesLabel = new JLabel("Choose class");
        JComboBox<String> classesComboBox = new JComboBox<>(); // Inicjalizacja classesComboBox

        JLabel studentsLabel = new JLabel("Choose student");
        JComboBox<String> studentsComboBox = new JComboBox<>(); // Inicjalizacja studentsComboBox

        // Import listy klas z bazy danych
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String classesListQueryString = "SELECT DISTINCT c.class_id, c.name, c.lead_teacher_id FROM classes c " +
                                       "JOIN courses co ON c.class_id = co.class_id " +
                                       "WHERE co.teacher_id = ?;";
        PreparedStatement classesListCheckQuery = null;
        try {
            con = DriverManager.getConnection(url, "root", "");
            classesListCheckQuery = con.prepareStatement(classesListQueryString);
            classesListCheckQuery.setInt(1, teacher.teacher_id);
            rs = classesListCheckQuery.executeQuery();
            while (rs.next()) {
                SchoolClass schoolClass = new SchoolClass(rs.getInt("class_id"), rs.getString("name"), rs.getInt("lead_teacher_id"));
                classes.add(schoolClass);
                classesComboBox.addItem(schoolClass.name);
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (classesListCheckQuery != null) classesListCheckQuery.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }

        classesComboBox.addActionListener(e -> {
            String selectedClassName = (String) classesComboBox.getSelectedItem();
            int classId = classes.stream()
                                .filter(schoolClass -> schoolClass.name.equals(selectedClassName))
                                .findFirst()
                                .map(schoolClass -> schoolClass.class_id)
                                .orElse(-1); // default value if not found

            if (classId != -1) {
                students.clear();
                loadStudents(classId, studentsComboBox);
            }
        });

        showGradesPanel = new JPanel(new BorderLayout());
        showGradesPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        addGradePanel = new JPanel(new BorderLayout());
        addGradePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        showGrades = new JButton("Show Grades");
        addGrade = new JButton("Add Grade");

        showGrades.addActionListener(e -> {
            String selectedStudentName = (String) studentsComboBox.getSelectedItem();
            if (selectedStudentName != null) {
                showStudentGrades(selectedStudentName);
            }
        });

        addGrade.addActionListener(e -> {
            String selectedStudentName = (String) studentsComboBox.getSelectedItem();
            if (selectedStudentName != null) {
                showAddGradeForm(selectedStudentName);
            }
        });

        panel.add(classesLabel);
        panel.add(classesComboBox);
        panel.add(studentsLabel);
        panel.add(studentsComboBox);
        panel.add(showGrades);
        panel.add(addGrade);

        gradesPanel.add(panel, BorderLayout.NORTH);
        gradesPanel.add(showGradesPanel, BorderLayout.CENTER);
        gradesPanel.add(addGradePanel, BorderLayout.CENTER);
    }

    void loadStudents(int classId, JComboBox<String> studentsComboBox) {
        students.clear();
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String studentsListQueryString = "SELECT student_id, first_name, last_name, class_id, login FROM students WHERE class_id = ?;";
        PreparedStatement studentsListCheckQuery = null;
        try {
            con = DriverManager.getConnection(url, "root", "");
            studentsListCheckQuery = con.prepareStatement(studentsListQueryString);
            studentsListCheckQuery.setInt(1, classId);
            rs = studentsListCheckQuery.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("class_id"), rs.getString("login"));
                students.add(student);
                studentsComboBox.addItem(student.first_name + " " + student.last_name);
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (studentsListCheckQuery != null) studentsListCheckQuery.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    }

    void showStudentGrades(String studentName) {
    Student selectedStudent = students.stream()
                                     .filter(student -> (student.first_name + " " + student.last_name).equals(studentName))
                                     .findFirst()
                                     .orElse(null);

    if (selectedStudent != null) {
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        String gradesQueryString = "SELECT grade, description, date FROM grades " +
                                   "WHERE student_id = ? AND teacher_id = ?;";
        try {
            con = DriverManager.getConnection(url, "root", "");
            pst = con.prepareStatement(gradesQueryString);
            pst.setInt(1, selectedStudent.student_id);
            pst.setInt(2, teacher.teacher_id);
            rs = pst.executeQuery();

            // Tworzymy model danych dla JTable
            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);

            // Dodajemy kolumny do modelu
            model.addColumn("Grade");
            model.addColumn("Description");
            model.addColumn("Date");

            // Dodajemy dane do modelu z ResultSet
            while (rs.next()) {
                float grade = rs.getFloat("grade");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                model.addRow(new Object[]{grade, description, date});
            }

            // Tworzymy JScrollPane dla tabeli i dodajemy do nowego panelu
            JPanel gradesTablePanel = new JPanel(new BorderLayout());
            gradesTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

            // Tworzymy i wyświetlamy okno z tabelą ocen
            JFrame gradesFrame = new JFrame("Grades for " + studentName);
            gradesFrame.setSize(600, 400);
            gradesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gradesFrame.add(gradesTablePanel);
            gradesFrame.setVisible(true);

        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selected student not found.");
    }
}

    void showAddGradeForm(String studentName) {
        addGradePanel.removeAll();

        JPanel formPanel = new JPanel(new FlowLayout());
        JLabel gradeLabel = new JLabel("Grade:");
        JComboBox<Float> gradeComboBox = new JComboBox<>(new Float[]{1.0f, 2.0f, 2.5f, 2.75f, 3.0f, 3.5f, 3.75f, 4.0f, 4.5f, 4.75f, 5.0f, 5.5f, 6.0f});
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField(32);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            Float gradeValue = (Float) gradeComboBox.getSelectedItem();
            String description = descriptionField.getText();
            addGradeToDatabase(studentName, gradeValue, description);
        });

        formPanel.add(gradeLabel);
        formPanel.add(gradeComboBox);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(submitButton);

        addGradePanel.add(formPanel, BorderLayout.CENTER);
        gradesPanel.revalidate();
        gradesPanel.repaint();
    }

    void addGradeToDatabase(String studentName, Float gradeValue, String description) {
    Student selectedStudent = students.stream()
                                     .filter(student -> (student.first_name + " " + student.last_name).equals(studentName))
                                     .findFirst()
                                     .orElse(null);

    if (selectedStudent != null) {
        int courseId = getCourseId(selectedStudent.class_id);

        if (courseId != -1) {
            String url = "jdbc:mysql://127.0.0.1/schoolappdb";
            Connection con = null;
            PreparedStatement pst = null;
            String insertGradeQuery = "INSERT INTO grades (student_id, teacher_id, course, grade, description, date) VALUES (?, ?, ?, ?, ?, ?);";
            try {
                con = DriverManager.getConnection(url, "root", "");
                pst = con.prepareStatement(insertGradeQuery);
                pst.setInt(1, selectedStudent.student_id);
                pst.setInt(2, teacher.teacher_id);
                pst.setInt(3, courseId);
                pst.setFloat(4, gradeValue);
                pst.setString(5, description);
                pst.setObject(6, LocalDate.now());
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Grade added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add grade.");
                }
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            } finally {
                try {
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (SQLException ex) {
                    System.err.println("SQL exception: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Course ID not found for selected student.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selected student not found.");
    }
}

    int getCourseId(int classId) {
        int courseId = -1;
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String courseIdQueryString = "SELECT course_id FROM courses WHERE teacher_id = ? AND class_id = ?;";
        PreparedStatement courseIdQuery = null;
        try {
            con = DriverManager.getConnection(url, "root", "");
            courseIdQuery = con.prepareStatement(courseIdQueryString);
            courseIdQuery.setInt(1, teacher.teacher_id);
            courseIdQuery.setInt(2, classId);
            rs = courseIdQuery.executeQuery();
            if (rs.next()) {
                courseId = rs.getInt("course_id");
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (courseIdQuery != null) courseIdQuery.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
        return courseId;
    }

    void chatPanelInit() {
        new ChatClient(teacher.getName()).setVisible(true);
        //chatPanel.add(new JLabel("Chat panel content"), BorderLayout.CENTER);
        
    }

    void projectsPanelInit() {
    projectsPanel.setLayout(new BorderLayout()); // Ustawienie layoutu dla projectsPanel

    JLabel classesLabel = new JLabel("Choose class");
    JComboBox<String> classesComboBox = new JComboBox<>(); // Inicjalizacja classesComboBox

    showProjectsPanel = new JPanel(new BorderLayout());
    showProjectsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    addProjectPanel = new JPanel(new BorderLayout());
    addProjectPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

    JButton showProjectsButton = new JButton("Show Projects");
    JButton addProjectButton = new JButton("Add Project");

    showProjectsButton.addActionListener(e -> {
        String selectedClassName = (String) classesComboBox.getSelectedItem();
        if (selectedClassName != null) {
            showProjects(selectedClassName);
        }
    });

    addProjectButton.addActionListener(e -> {
        String selectedClassName = (String) classesComboBox.getSelectedItem();
        if (selectedClassName != null) {
            showAddProjectForm(selectedClassName);
        }
    });

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(classesLabel);
    buttonPanel.add(classesComboBox);
    buttonPanel.add(showProjectsButton);
    buttonPanel.add(addProjectButton);

    projectsPanel.add(buttonPanel, BorderLayout.NORTH);
    projectsPanel.add(showProjectsPanel, BorderLayout.CENTER);
    projectsPanel.add(addProjectPanel, BorderLayout.CENTER);

    // Import listy klas z bazy danych
    String url = "jdbc:mysql://127.0.0.1/schoolappdb";
    Connection con = null;
    ResultSet rs = null;
    String classesListQueryString = "SELECT DISTINCT c.class_id, c.name, c.lead_teacher_id FROM classes c " +
                                   "JOIN courses co ON c.class_id = co.class_id " +
                                   "WHERE co.teacher_id = ?;";
    PreparedStatement classesListCheckQuery = null;
    try {
        con = DriverManager.getConnection(url, "root", "");
        classesListCheckQuery = con.prepareStatement(classesListQueryString);
        classesListCheckQuery.setInt(1, teacher.teacher_id);
        rs = classesListCheckQuery.executeQuery();
        while (rs.next()) {
            SchoolClass schoolClass = new SchoolClass(rs.getInt("class_id"), rs.getString("name"), rs.getInt("lead_teacher_id"));
            classes.add(schoolClass);
            classesComboBox.addItem(schoolClass.name);
        }
    } catch (SQLException ex) {
        System.err.println("SQL exception: " + ex.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (classesListCheckQuery != null) classesListCheckQuery.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        }
    }
}

void showProjects(String selectedClassName) {
    SchoolClass selectedClass = classes.stream()
                                       .filter(schoolClass -> schoolClass.name.equals(selectedClassName))
                                       .findFirst()
                                       .orElse(null);

    if (selectedClass != null) {
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        String projectsQueryString = "SELECT name, description, date FROM projects " +
                                     "WHERE class_id = ? AND teacher_id = ?;";
        try {
            con = DriverManager.getConnection(url, "root", "");
            pst = con.prepareStatement(projectsQueryString);
            pst.setInt(1, selectedClass.class_id);
            pst.setInt(2, teacher.teacher_id);
            rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);

            model.addColumn("Project");
            model.addColumn("Description");
            model.addColumn("Date");

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                model.addRow(new Object[]{name, description, date});
            }

            JPanel projectsTablePanel = new JPanel(new BorderLayout());
            projectsTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

            JFrame projectsFrame = new JFrame("Projects for " + selectedClassName);
            projectsFrame.setSize(600, 400);
            projectsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            projectsFrame.add(projectsTablePanel);
            projectsFrame.setVisible(true);

        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Selected class not found.");
    }
}

void showAddProjectForm(String className) {
    addProjectPanel.removeAll();

    JPanel formPanel = new JPanel(new FlowLayout());
    JLabel projectLabel = new JLabel("Project:");
    JTextField nameField = new JTextField(32);
    JLabel idLabel = new JLabel("Id:");
    JTextField idField = new JTextField(10);
    JLabel descriptionLabel = new JLabel("Description:");
    JTextField descriptionField = new JTextField(64);
    JButton submitButton = new JButton("Submit");

    submitButton.addActionListener(e -> {
        String name = nameField.getText();
        String idText = idField.getText();
            int id = 0;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e1) {
                System.err.println("Cannot be converted to integer: " + e1.getMessage());
            }
        String description = descriptionField.getText();
        addProjectToDatabase(className, id, name, description);
    });

    formPanel.add(projectLabel);
    formPanel.add(nameField);
    formPanel.add(idLabel);
    formPanel.add(idField);
    formPanel.add(descriptionLabel);
    formPanel.add(descriptionField);
    formPanel.add(submitButton);

    addProjectPanel.add(formPanel, BorderLayout.CENTER);
    projectsPanel.revalidate();
    projectsPanel.repaint();
}

void addProjectToDatabase(String className, int id, String name, String description) {
    SchoolClass selectedClass = classes.stream()
                                      .filter(schoolClass -> schoolClass.name.equals(className))
                                      .findFirst()
                                      .orElse(null);

    if (selectedClass != null) {
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        PreparedStatement pst = null;
        String insertProjectQuery = "INSERT INTO projects (project_id, name, description, teacher_id, class_id, date) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            con = DriverManager.getConnection(url, "root", "");
            pst = con.prepareStatement(insertProjectQuery);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, description);
            pst.setInt(4, teacher.teacher_id);
            pst.setInt(5, selectedClass.class_id);
            pst.setObject(6, LocalDate.now());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Project added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add project.");
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Selected class not found.");
    }
}

}
