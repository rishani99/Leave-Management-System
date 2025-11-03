package ui;

import javax.swing.*;
import dao.UserDAO;
import model.User;

@SuppressWarnings("serial")
public class SignUpUI extends JFrame {

    public SignUpUI() {
        super("HR - Employee Signup");

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JLabel roleLabel = new JLabel("Role:");
        JTextField roleField = new JTextField();

        JButton signupBtn = new JButton("Register Employee");

        signupBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword());
            String role = roleField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!");
                return;
            }

            // ✅ Create User and set values
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(pass);
            user.setRole(role);

            boolean success = UserDAO.register(user);

            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Signup Successful! Please login.");
                new LoginUI().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Signup Failed! Try again.");
            }
        });

        setLayout(null);
        nameLabel.setBounds(20, 20, 80, 25);
        nameField.setBounds(120, 20, 180, 25);
        emailLabel.setBounds(20, 60, 80, 25);
        emailField.setBounds(120, 60, 180, 25);
        passLabel.setBounds(20, 100, 80, 25);
        passField.setBounds(120, 100, 180, 25);
        roleLabel.setBounds(20, 140, 80, 25);
        roleField.setBounds(120, 140, 180, 25);
        signupBtn.setBounds(120, 190, 180, 30);

        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passLabel);
        add(passField);
        add(roleLabel);
        add(roleField);
        add(signupBtn);

        setSize(360, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUpUI();
    }
}
