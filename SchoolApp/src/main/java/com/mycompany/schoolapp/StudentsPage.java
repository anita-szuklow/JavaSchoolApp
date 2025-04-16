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

public class StudentsPage extends JFrame implements ActionListener {
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
    JPanel showProjectsPanel;
    String login;
    Student student;
    ArrayList<Course> courses; // Initialize ArrayList<Course> to hold courses
    CardLayout cardLayout;

    JButton showGrades; // Deklaracja showGrades
    private JButton showProjects;

    StudentsPage(String title, String login) {
        super(title);
        this.login = login;
        student = new Student(0, null, null, 0, login);
        courses = new ArrayList<>(); // Initialize the courses ArrayList
        personaliaCheck(login);
        init();
    }

    void personaliaCheck(String loginDb) {
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String loginCheckQueryString = "SELECT * FROM STUDENTS WHERE LOGIN LIKE ?;";
        PreparedStatement loginCheckQuery = null;
        try {
            con = DriverManager.getConnection(url, "root", "");
            loginCheckQuery = con.prepareStatement(loginCheckQueryString);
            loginCheckQuery.setString(1, login);
            rs = loginCheckQuery.executeQuery();
            if (rs.next()) {
                student.first_name = rs.getString("FIRST_NAME");
                student.last_name = rs.getString("LAST_NAME");
                student.student_id = rs.getInt("STUDENT_ID");
                student.class_id = rs.getInt("CLASS_ID"); // Assuming CLASS_ID is retrieved
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
        showLabel = new JLabel("You are logged in as: " + student.first_name + " " + student.last_name);
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

        JLabel coursesLabel = new JLabel("Choose course");
        JComboBox<String> coursesComboBox = new JComboBox<>(); // Inicjalizacja coursesComboBox

        // Import listy kursow z bazy danych
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String coursesListQueryString = "SELECT co.course_id, co.name, co.teacher_id FROM courses co " +
                                       "WHERE co.class_id = ?;";
        PreparedStatement coursesListCheckQuery = null;
        try {
            con = DriverManager.getConnection(url, "root", "");
            coursesListCheckQuery = con.prepareStatement(coursesListQueryString);
            coursesListCheckQuery.setInt(1, student.class_id); // Use student.class_id
            rs = coursesListCheckQuery.executeQuery();

            // Clear courses list before populating
            courses.clear();

            while (rs.next()) {
                Course course = new Course(rs.getInt("course_id"), rs.getString("name"), rs.getInt("teacher_id"), student.class_id);
                courses.add(course);
                coursesComboBox.addItem(course.name);
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (coursesListCheckQuery != null) coursesListCheckQuery.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }

        coursesComboBox.addActionListener(e -> {
            String selectedCourseName = (String) coursesComboBox.getSelectedItem();
            int classId = courses.stream()
                                .filter(course -> course.name.equals(selectedCourseName))
                                .findFirst()
                                .map(course -> course.course_id)
                                .orElse(-1); // default value if not found

        });

        showGradesPanel = new JPanel(new BorderLayout());
        showGradesPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        showGrades = new JButton("Show Grades");

        showGrades.addActionListener(e -> {
            String selectedCourseName = (String) coursesComboBox.getSelectedItem();
            if (selectedCourseName != null) {
                showStudentGrades(selectedCourseName);
            }
        });

        panel.add(coursesLabel);
        panel.add(coursesComboBox);
        panel.add(showGrades);

        gradesPanel.add(panel, BorderLayout.NORTH);
        gradesPanel.add(showGradesPanel, BorderLayout.CENTER);
    }

    void showStudentGrades(String courseName) {
        Course selectedCourse = courses.stream()
                                      .filter(course -> course.name.equals(courseName))
                                      .findFirst()
                                      .orElse(null);

        if (selectedCourse != null) {
            String url = "jdbc:mysql://127.0.0.1/schoolappdb";
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
            String gradesQueryString = "SELECT grade, description, date FROM grades " +
                                       "WHERE course = ? AND student_id = ?;";
            try {
                con = DriverManager.getConnection(url, "root", "");
                pst = con.prepareStatement(gradesQueryString);
                pst.setInt(1, selectedCourse.course_id);
                pst.setInt(2, student.student_id);
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
                JFrame gradesFrame = new JFrame("Grades from " + courseName);
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
            JOptionPane.showMessageDialog(this, "Selected course not found.");
        }
    }

    void chatPanelInit() {
        new ChatClient(student.getName()).setVisible(true);
        //chatPanel.add(new JLabel("Chat panel content"), BorderLayout.CENTER);
    }

    void projectsPanelInit() {
        projectsPanel.setLayout(new BorderLayout()); // Ustawienie layoutu dla projectsPanel

        JLabel coursesLabel = new JLabel("Choose course");
        JComboBox<String> coursesComboBox = new JComboBox<>(); // Inicjalizacja classesComboBox

        showProjectsPanel = new JPanel(new BorderLayout());
        showProjectsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton showProjectsButton = new JButton("Show Projects");

        showProjectsButton.addActionListener(e -> {
            String selectedCourseName = (String) coursesComboBox.getSelectedItem();
            if (selectedCourseName != null) {
                showProjects(selectedCourseName);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(coursesLabel);
        buttonPanel.add(coursesComboBox);
        buttonPanel.add(showProjectsButton);

        projectsPanel.add(buttonPanel, BorderLayout.NORTH);
        projectsPanel.add(showProjectsPanel, BorderLayout.CENTER);

        // Import listy kursow z bazy danych
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String coursesListQueryString = "SELECT co.course_id, co.name, co.teacher_id FROM courses co " +
                                       "WHERE co.class_id = ?;";
        PreparedStatement coursesListCheckQuery = null;
        try {
            con = DriverManager.getConnection(url, "root", "");
            coursesListCheckQuery = con.prepareStatement(coursesListQueryString);
            coursesListCheckQuery.setInt(1, student.class_id); // Use student.class_id
            rs = coursesListCheckQuery.executeQuery();

            // Clear courses list before populating
            courses.clear();

            while (rs.next()) {
                Course course = new Course(rs.getInt("course_id"), rs.getString("name"), rs.getInt("teacher_id"), student.class_id);
                courses.add(course);
                coursesComboBox.addItem(course.name);
            }
        } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (coursesListCheckQuery != null) coursesListCheckQuery.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    }

    void showProjects(String selectedCourseName) {
        Course selectedCourse = courses.stream()
                                       .filter(course -> course.name.equals(selectedCourseName))
                                       .findFirst()
                                       .orElse(null);

        if (selectedCourse != null) {
            String url = "jdbc:mysql://127.0.0.1/schoolappdb";
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
            String projectsQueryString = "SELECT name, description, date FROM projects " +
                                         "WHERE class_id = ? AND teacher_id = ?;";
            try {
                con = DriverManager.getConnection(url, "root", "");
                pst = con.prepareStatement(projectsQueryString);
                pst.setInt(1, selectedCourse.class_id);
                pst.setInt(2, selectedCourse.teacher_id);
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

                JFrame projectsFrame = new JFrame("Projects for " + selectedCourseName);
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
            JOptionPane.showMessageDialog(null, "Selected course not found.");
        }
    }


}
