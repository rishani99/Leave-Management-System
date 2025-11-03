package ui;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class HRLoginUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public HRLoginUI() {
        super("HR Login");

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Title
        JLabel title = new JLabel("HR Login");
        title.setBounds(100, 20, 250, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 102, 204));
        add(title);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 70, 100, 25);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(150, 70, 180, 25);
        add(emailField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 110, 100, 25);
        add(passLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 180, 25);
        add(passwordField);

        // Buttons
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 170, 120, 35);
        loginBtn.setBackground(new Color(0,102,204));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(e -> login());
        add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(210, 170, 120, 35);
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

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if(email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM hr WHERE email=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password); // For production, hash & compare
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new HRDashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HRLoginUI());
    }
}
