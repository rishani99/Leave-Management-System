package dao;

import db.DBConnection;
import model.Leave;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {

    public static void submitLeaveRequest(String empName, String month, String from,
                                          String to, String reason, String type) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT INTO leaves(emp_name, month, fromDate, toDate, reason, leaveType, status, note) " +
                "VALUES(?,?,?,?,?,?, 'Pending', '')"
             )) {

            ps.setString(1, empName);
            ps.setString(2, month);
            ps.setString(3, from);
            ps.setString(4, to);
            ps.setString(5, reason);
            ps.setString(6, type);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Leave> getLeavesByEmployee(String empName) {
        List<Leave> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("SELECT * FROM leaves WHERE emp_name = ?")) {

            pst.setString(1, empName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Leave leave = new Leave(
                    rs.getInt("id"),
                    rs.getString("emp_name"),
                    rs.getString("month"),
                    rs.getString("fromDate"),
                    rs.getString("toDate"),
                    rs.getString("reason"),
                    rs.getString("leaveType"),
                    rs.getString("status"),
                    rs.getString("note")
                );
                list.add(leave);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Leave> getAllLeaves() {
        List<Leave> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("SELECT * FROM leaves")) {

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Leave leave = new Leave(
                    rs.getInt("id"),
                    rs.getString("emp_name"),
                    rs.getString("month"),
                    rs.getString("fromDate"),
                    rs.getString("toDate"),
                    rs.getString("reason"),
                    rs.getString("leaveType"),
                    rs.getString("status"),
                    rs.getString("note")
                );
                list.add(leave);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getLeaveCountForEmployeeMonth(String empName, String month) {
        int count = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                 "SELECT COUNT(*) FROM leaves WHERE emp_name=? AND month=?")) {

            pst.setString(1, empName);
            pst.setString(2, month);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) count = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

	public void updateStatus(int id, String status, String hrName) {
		// TODO Auto-generated method stub
		
	}
}
