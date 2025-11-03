package ui;

import java.time.YearMonth;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import dao.LeaveDAO;
import model.Leave;

@SuppressWarnings("serial")
public class EmpDashboard extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private String empName;
    private final int MONTHLY_ALLOWANCE = 3;

    public EmpDashboard(String empName) {
        super(empName + " - Employee Dashboard");
        this.empName = empName;

        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Header Panel
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 102, 204));
        header.setBounds(0, 0, 900, 60);
        header.setLayout(null);

        JLabel title = new JLabel("Employee Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 10, 300, 40);
        header.add(title);

        JLabel userLabel = new JLabel("Hi, " + empName);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(750, 18, 150, 30);
        header.add(userLabel);

        add(header);

        // Summary Label
        JLabel summaryLabel = new JLabel();
        summaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        summaryLabel.setForeground(new Color(50, 50, 50));
        summaryLabel.setBounds(20, 70, 600, 25);
        add(summaryLabel);

        // Table
        String[] columns = {"ID", "Month", "From", "To", "Reason", "Type", "Status", "Note"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeader.setBackground(new Color(230, 240, 255));

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 110, 850, 280);
        add(scroll);

        // Add Leave Button
        JButton addButton = new JButton("Request Leave");
        addButton.setBounds(350, 410, 180, 40);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(addButton);

        loadData();
        updateSummary(summaryLabel);

        addButton.addActionListener(e -> openAddDialog(summaryLabel));

        setVisible(true);
    }

    private String currentMonthLabel() {
        YearMonth ym = YearMonth.now();
        return String.format("%d-%02d", ym.getYear(), ym.getMonthValue());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Leave> leaves = LeaveDAO.getLeavesByEmployee(empName);

        for (Leave l : leaves) {
            model.addRow(new Object[]{
                l.getId(), l.getMonth(), l.getFromDate(), l.getToDate(),
                l.getReason(), l.getLeaveType(), l.getStatus(), l.getNote()
            });
        }
    }

    private void updateSummary(JLabel summaryLabel) {
        String month = currentMonthLabel();
        int used = LeaveDAO.getLeaveCountForEmployeeMonth(empName, month);
        int remaining = MONTHLY_ALLOWANCE - used;
        if (remaining < 0) remaining = 0;

        summaryLabel.setText(
            String.format("This month (%s): used %d / %d | Remaining: %d",
            month, used, MONTHLY_ALLOWANCE, remaining)
        );
    }

    private void openAddDialog(JLabel summaryLabel) {
        JDialog dialog = new JDialog(this, "Request Leave", true);
        dialog.setLayout(null);
        dialog.setSize(420, 360);
        dialog.setLocationRelativeTo(this);

        JLabel lblMonth = new JLabel("Month:");
        JLabel lblFrom = new JLabel("From:");
        JLabel lblTo = new JLabel("To:");
        JLabel lblReason = new JLabel("Reason:");
        JLabel lblType = new JLabel("Type:");

        JTextField monthField = new JTextField(currentMonthLabel());
        JTextField fromField = new JTextField("YYYY-MM-DD");
        JTextField toField = new JTextField("YYYY-MM-DD");
        JTextField reasonField = new JTextField();
        JTextField typeField = new JTextField("General");

        int y = 30;
        int gap = 40;

        dialog.add(lblMonth).setBounds(40, y, 100, 25); dialog.add(monthField).setBounds(180, y, 180, 25); y+=gap;
        dialog.add(lblFrom).setBounds(40, y, 100, 25); dialog.add(fromField).setBounds(180, y, 180, 25); y+=gap;
        dialog.add(lblTo).setBounds(40, y, 100, 25); dialog.add(toField).setBounds(180, y, 180, 25); y+=gap;
        dialog.add(lblReason).setBounds(40, y, 100, 25); dialog.add(reasonField).setBounds(180, y, 180, 25); y+=gap;
        dialog.add(lblType).setBounds(40, y, 100, 25); dialog.add(typeField).setBounds(180, y, 180, 25);

        JButton submit = new JButton("Submit");
        submit.setBounds(150, 280, 120, 35);
        submit.setBackground(new Color(0, 102, 204));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submit.setFocusPainted(false);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dialog.add(submit);

        submit.addActionListener(e -> {
            LeaveDAO.submitLeaveRequest(
                empName,
                monthField.getText(),
                fromField.getText(),
                toField.getText(),
                reasonField.getText(),
                typeField.getText()
            );

            dialog.dispose();
            loadData();
            updateSummary(summaryLabel);
        });

        dialog.setVisible(true);
    }
}
