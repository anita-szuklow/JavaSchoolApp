package schoolapp;

import java.awt.*;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author anita
 */
public class AdminPage extends JFrame{
    JLabel showLabel;
    
    AdminPage(String title){
    super("Admin page");
    init();
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
    
    showLabel = new JLabel("Wlecome to Admin's page");
//    loginField = new JTextField(32);
//    passwordLabel = new JLabel("Password");
//    passwordField = new JPasswordField(32);
//    submitButton = new JButton("Submit");
//    replyField = new JLabel();
//    submitButton.addActionListener(this);    
    
    add(showLabel, gbc);
    
//    gbc.gridx = 0;
//    gbc.gridy = 1;
//    add(loginField, gbc);
//    
//    gbc.gridx = 0;
//    gbc.gridy = 2;
//    add(passwordLabel, gbc);
//    
//    gbc.gridx = 0;
//    gbc.gridy = 3;
//    add(passwordField, gbc);
//    
//    gbc.gridx = 0;
//    gbc.gridy = 4;
//    add(submitButton, gbc);
//    
//    gbc.gridx = 0;
//    gbc.gridy = 5;
//    add(replyField, gbc);
    }
    
}
