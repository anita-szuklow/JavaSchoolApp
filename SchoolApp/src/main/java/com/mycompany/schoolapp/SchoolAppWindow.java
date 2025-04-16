package schoolapp;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
/**
 *
 * @author anita
 */
public class SchoolAppWindow extends JFrame implements ActionListener{    
    
    public static void main(String[] args){
    SchoolAppWindow schoolappwindow = new SchoolAppWindow("SchoolApp");
    schoolappwindow.init();
    schoolappwindow.setVisible(true);
    
    }
    
    JLabel loginLabel;
    JTextField loginField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton submitButton;
    JLabel replyField;
    String textReply;
    
    SchoolAppWindow(String title){
    super(title);
    }
    void init() {
    setSize(800,600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);    
    setLayout(new GridBagLayout());
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5,5,5,5);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    
    loginLabel = new JLabel("Login");
    loginField = new JTextField(32);
    passwordLabel = new JLabel("Password");
    passwordField = new JPasswordField(32);
    submitButton = new JButton("Submit");
    replyField = new JLabel();
    submitButton.addActionListener(this);    
    
    add(loginLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    add(loginField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 2;
    add(passwordLabel, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 3;
    add(passwordField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 4;
    add(submitButton, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 5;
    add(replyField, gbc);
    }
    public void actionPerformed(ActionEvent submitted){
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        String url = "jdbc:mysql://127.0.0.1/schoolappdb";
        Connection con = null;
        ResultSet rs = null;
        String loginCheckQueryString = "select * from LOGIN where LOGIN_STRING like ?;";
        PreparedStatement loginCheckQuery = null;
        try{
            
            con = DriverManager.getConnection(url, "root", "");
            loginCheckQuery = con.prepareStatement(loginCheckQueryString);
            loginCheckQuery.setString(1, login);
            rs = loginCheckQuery.executeQuery();
            if(rs.next()){
                String DbPassword = rs.getString("PASSWORD");
                String role = rs.getString("ROLE");
            
            if (password.equals(DbPassword)){
                switch(role) {
                        case "teacher": {
                        TeachersPage teachersPage = new TeachersPage("Teacher Page",login);
                        teachersPage.setVisible(true);
//                        this.dispose();
                        }; break;
                        case "student": {
                        StudentsPage studentsPage = new StudentsPage("Student Page", login);
                        studentsPage.setVisible(true);
//                        this.dispose();
                        }; break;
                        case "admin": {
                        AdminPage adminPage = new AdminPage("Admin Page");
                        adminPage.setVisible(true);
//                        this.dispose();
                        }; break;
                        default: replyField.setText("There seems to be a problem with your role, please contact your administrator.");
                            
                }
            }
            else{
                replyField.setText("wrong password");
                        };
            }
            else{
                replyField.setText("login not found");
            };            
        }
        catch(SQLException ex){
            System.err.println("SQL exception: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (loginCheckQuery != null) loginCheckQuery.close();
                if (con!= null) con.close();
            } catch (SQLException ex) {
            System.err.println("SQL exception: " + ex.getMessage());
            }
        }
    }
}