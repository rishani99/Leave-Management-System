package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.LeaveDAO;
import model.Leave;

@SuppressWarnings("serial")
public class AdminDashboard extends JFrame {

    private static AdminDashboard instance;
    private JTable table;
    private DefaultTableModel model;

    public AdminDashboard() {
        super("Admin Dashboard - All Leaves");
        instance = this;

        setSize(1000, 600);
        setLocationRelativeTo(null); // Center window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel title = new JLabel("Admin Dashboard - All Leaves");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title);
        add(header, BorderLayout.NORTH);

        // Table setup
        String[] cols = {"ID", "Employee", "Month", "From", "To", "Reason", "Type", "Status", "Note"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 153, 255));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        add(scroll, BorderLayout.CENTER);

        loadData();
        setVisible(true);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Leave> list = LeaveDAO.getAllLeaves();

        for (Leave l : list) {
            model.addRow(new Object[]{
                l.getId(), l.getEmpName(), l.getMonth(), l.getFromDate(),
                l.getToDate(), l.getReason(), l.getLeaveType(), l.getStatus(), l.getNote()
            });
        }
    }

    public static void refreshTableIfOpen() {
        if (instance != null) instance.loadData();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard());
    }
}
