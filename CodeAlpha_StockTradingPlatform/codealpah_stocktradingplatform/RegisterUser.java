package codealpah_stocktradingplatform;

import javax.swing.*;
import java.awt.*;

public class RegisterUser extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField, confirmField;
    private DatabaseHandler db;

    public RegisterUser() {
        db = new DatabaseHandler();

        setTitle("Stock Trading Platform - Register");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        getContentPane().setBackground(new Color(40, 53, 147));

        JLabel heading = new JLabel("Stock Trading Platform", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);
        heading.setBounds(0, 30, getWidth(), 50);
        add(heading);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);
        registerPanel.setBackground(Color.BLACK);
        registerPanel.setBounds(450, 150, 500, 450);
        add(registerPanel);

        JLabel panelTitle = new JLabel("Create Account", SwingConstants.CENTER);
        panelTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panelTitle.setForeground(Color.WHITE);
        panelTitle.setBounds(150, 20, 200, 40);
        registerPanel.add(panelTitle);

        usernameField = new JTextField();
        usernameField.setBounds(130, 90, 240, 40);
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        registerPanel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 150, 240, 40);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        registerPanel.add(passwordField);

        confirmField = new JPasswordField();
        confirmField.setBounds(130, 210, 240, 40);
        confirmField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        registerPanel.add(confirmField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(130, 280, 100, 40);
        registerBtn.setBackground(Color.WHITE);
        registerPanel.add(registerBtn);

        JButton loginBtn = new JButton("Back to Login");
        loginBtn.setBounds(270, 280, 120, 40);
        loginBtn.setBackground(Color.WHITE);
        registerPanel.add(loginBtn);

        registerBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required");
            } else if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            } else {
                boolean success = db.registerUser(user, pass);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Registered Successfully");
                    dispose();
                    new LoginUser();
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists");
                }
            }
        });

        loginBtn.addActionListener(e -> {
            dispose();
            new LoginUser();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
