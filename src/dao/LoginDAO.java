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
    try {
        ResultSet rs = DBOperations.getData("SELECT * FROM user WHERE Username = '" + username + "' AND Password = '" + password + "'");

        if (rs.next()) {
            user = new User();
            user.setUsername(rs.getString("Username"));
            user.setPassword(rs.getString("Password"));
            user.setRole(rs.getString("Role"));
        }

        rs.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Login error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return user;
    }

}
