package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import model.User;

public class UserDAO {

    public static boolean register(User user) {
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return false;

            String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getRole());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User login(String email, String password) {
        User user = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
