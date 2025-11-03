package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;

@SuppressWarnings("serial")
public class LoginUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passField;

    public LoginUI() {

        setTitle("HR System Login");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("Login Panel");
        lblTitle.setBounds(140, 10, 200, 30);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitle);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 70, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(130, 70, 220, 25);
        add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 110, 100, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(130, 110, 220, 25);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 160, 120, 30);
        add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        setVisible(true);
    }

    private void login() {
        String email = emailField.getText();
        String pass = new String(passField.getPassword());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role"); // employee or hr

                JOptionPane.showMessageDialog(null, "Login Successful");

                if (role.equalsIgnoreCase("hr")) {
                    new HRDashboard();
                    dispose();
                } else {
                    new EmpDashboard(email); // employee UI file (future)
                    dispose();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Credentials");
            }

            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginUI();
    }

}
