/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author marionne pascual
 */


import model.User;
import java.sql.*;
import javax.swing.*;


public class LoginDAO {
    public static User login(String username, String password) {
    User user = null;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        con = ConnectionDB.getConnection(); 
        String sql = "SELECT * FROM user WHERE Username = ? AND Password = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);
        rs = pst.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setUsername(rs.getString("Username"));
            user.setPassword(rs.getString("Password"));
            user.setRole(rs.getString("Role"));

            boolean filled = isStudentInfoFilled(con, rs.getString("User_ID"));
            user.setStudentInfoFilled(filled);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Login error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            
        }
    }
    return user;
}
    
    private static boolean isStudentInfoFilled(Connection con, String userId) {
    String sql = "SELECT COUNT(*) FROM student WHERE User_ID = ?";
    try (PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, userId);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Check error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return false;
}


}
