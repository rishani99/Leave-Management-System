package ui;

import javax.swing.*;
import java.awt.*;

public class HomeUI extends JFrame {
    private static final long serialVersionUID = 5639470214051818444L;

    public HomeUI() {
        super("QLanka Pvt.Ltd");

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        // Title
        JLabel title = new JLabel("QLanka Pvt.Ltd");
        title.setBounds(120, 20, 300, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 204));
        add(title);

        JLabel sub = new JLabel("Human Resource Management System");
        sub.setBounds(90, 60, 350, 20);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(Color.GRAY);
        add(sub);

        // Employee Buttons
        JLabel empLabel = new JLabel("Employee");
        empLabel.setBounds(50, 100, 100, 20);
        empLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(empLabel);

        JButton empSignup = new JButton("Sign Up");
        empSignup.setBounds(50, 130, 120, 40);
        styleButton(empSignup, new Color(0, 153, 76), new Color(0,140,70));
        empSignup.addActionListener(e -> {
            new EmployeeSignUpUI().setVisible(true);
            dispose();
        });
        add(empSignup);

        JButton empLogin = new JButton("Login");
        empLogin.setBounds(200, 130, 120, 40);
        styleButton(empLogin, new Color(0,102,204), new Color(0,92,184));
        empLogin.addActionListener(e -> {
            new EmployeeLoginUI().setVisible(true);
            dispose();
        });
        add(empLogin);

        // HR Buttons
        JLabel hrLabel = new JLabel("HR");
        hrLabel.setBounds(50, 190, 100, 20);
        hrLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(hrLabel);

        JButton hrSignup = new JButton("Sign Up");
        hrSignup.setBounds(50, 220, 120, 40);
        styleButton(hrSignup, new Color(0, 153, 76), new Color(0,140,70));
        hrSignup.addActionListener(e -> {
            new HRSignUpUI().setVisible(true);
            dispose();
        });
        add(hrSignup);

        JButton hrLogin = new JButton("Login");
        hrLogin.setBounds(200, 220, 120, 40);
        styleButton(hrLogin, new Color(0,102,204), new Color(0,92,184));
        hrLogin.addActionListener(e -> {
            new HRLoginUI().setVisible(true);
            dispose();
        });
        add(hrLogin);

        // Admin Buttons
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setBounds(50, 280, 100, 20);
        adminLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(adminLabel);

        JButton adminSignup = new JButton("Sign Up");
        adminSignup.setBounds(50, 310, 120, 40);
        styleButton(adminSignup, new Color(204, 0, 0), new Color(180,0,0));
        adminSignup.addActionListener(e -> {
            new AdminSignUpUI().setVisible(true);
            dispose();
        });
        add(adminSignup);

        JButton adminLogin = new JButton("Login");
        adminLogin.setBounds(200, 310, 120, 40);
        styleButton(adminLogin, new Color(0,102,204), new Color(0,92,184));
        adminLogin.addActionListener(e -> {
            new AdminUI().setVisible(true);
            dispose();
        });
        add(adminLogin);

        setVisible(true);
    }

    // Helper method for styling buttons
    private void styleButton(JButton btn, Color bg, Color border) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(border, 2));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeUI());
    }
}
