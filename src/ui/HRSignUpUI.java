package ui;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import db.DBConnection;

@SuppressWarnings("serial")
public class HRSignUpUI extends JFrame {

    private JTextField nameField, emailField;
    private JPasswordField passwordField;

    public HRSignUpUI() {
        super("HR Sign Up");

        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Title
        JLabel title = new JLabel("HR Sign Up");
        title.setBounds(100, 20, 250, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 102, 204));
        add(title);

        // Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 70, 100, 25);
        add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(150, 70, 180, 25);
        add(nameField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 100, 25);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(150, 110, 180, 25);
        add(emailField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 150, 100, 25);
        add(passLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 180, 25);
        add(passwordField);

        // Buttons
        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(50, 210, 120, 35);
        signupBtn.setBackground(new Color(0, 153, 76));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.addActionListener(e -> signUp());
        add(signupBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(210, 210, 120, 35);
        backBtn.setBackground(new Color(204,0,0));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> {
            new HomeUI().setVisible(true);
            dispose();
        });
        add(backBtn);

        setVisible(true);
    }

    private void signUp() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO hr (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password); // For production, hash password
            ps.executeUpdate();
            ps.close();
            conn.close();

            JOptionPane.showMessageDialog(this, "HR Sign Up successful!");
            new HRLoginUI().setVisible(true);
            dispose();

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HRSignUpUI());
    }
}
