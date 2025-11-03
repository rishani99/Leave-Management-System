package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.LeaveDAO;
import model.Leave;

@SuppressWarnings("serial")
public class HRDashboard extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private static HRDashboard instance;
    private JLabel title;

    public HRDashboard() {
        instance = this;
        setTitle("HR Dashboard - Leave Management");
        setSize(1000, 600);
        setLocationRelativeTo(null); // Center window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header panel
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        title = new JLabel("HR Dashboard - Leave Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        // Table setup
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "ID", "Employee", "Email", "Month", "From", "To",
            "Taken", "Remaining", "Reason", "Status", "Type", "Note"
        });

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 153, 255));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        add(scrollPane, BorderLayout.CENTER);

        loadData();

        // Bottom buttons panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(230, 230, 250));

        JButton approveBtn = new JButton("Approve");
        approveBtn.setBackground(new Color(76, 175, 80));
        approveBtn.setForeground(Color.WHITE);
        approveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        approveBtn.setFocusPainted(false);
        approveBtn.addActionListener(e -> updateStatus("APPROVED"));

        JButton rejectBtn = new JButton("Reject");
        rejectBtn.setBackground(new Color(244, 67, 54));
        rejectBtn.setForeground(Color.WHITE);
        rejectBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rejectBtn.setFocusPainted(false);
        rejectBtn.addActionListener(e -> updateStatus("REJECTED"));

        bottomPanel.add(approveBtn);
        bottomPanel.add(rejectBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Leave> list = LeaveDAO.getAllLeaves();

        for (Leave lr : list) {
            model.addRow(new Object[]{
                lr.getId(),
                lr.getEmpName(),
                lr.getEmpEmail(),
                lr.getMonth(),
                lr.getFromDate(),
                lr.getToDate(),
                lr.getTaken(),
                lr.getRemaining(),
                lr.getReason(),
                lr.getStatus(),
                lr.getLeaveType(),
                lr.getNote()
            });
        }
    }

    private void updateStatus(String status) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String hrName = JOptionPane.showInputDialog(this, "Enter HR Name");

        if (hrName == null || hrName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "HR name is required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LeaveDAO dao = new LeaveDAO();
        dao.updateStatus(id, status, hrName);

        JOptionPane.showMessageDialog(this, "Status updated to " + status, "Success", JOptionPane.INFORMATION_MESSAGE);
        loadData();
    }

    public static void refreshTableIfOpen() {
        if (instance != null) {
            instance.loadData();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HRDashboard().setVisible(true));
    }
}
