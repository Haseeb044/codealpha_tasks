package codealpah_stocktradingplatform;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginUser extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private DatabaseHandler db;

    public LoginUser() {
        db = new DatabaseHandler();

        setTitle("Stock Trading Platform - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        getContentPane().setBackground(new Color(40, 53, 147));

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        JLabel heading = new JLabel("Stock Trading Platform", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);
        heading.setBounds(0, 30, screenWidth, 50);
        add(heading);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.BLACK);
        loginPanel.setBounds(450, 150, 500, 400);
        add(loginPanel);

        JLabel panelTitle = new JLabel("User Login", SwingConstants.CENTER);
        panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panelTitle.setForeground(Color.WHITE);
        panelTitle.setBounds(150, 20, 200, 40);
        loginPanel.add(panelTitle);

        usernameField = new JTextField();
        usernameField.setBounds(130, 100, 240, 40);
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        loginPanel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 160, 240, 40);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        loginPanel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(130, 230, 100, 40);
        loginBtn.setBackground(Color.WHITE);
        loginPanel.add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(270, 230, 100, 40);
        registerBtn.setBackground(Color.WHITE);
        loginPanel.add(registerBtn);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            try {
                ResultSet rs = db.loginUser(user, pass);
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    dispose();
                    new Dashboard(userId, db);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Login");
                }
            } catch (Exception ex) {
            }
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterUser();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
