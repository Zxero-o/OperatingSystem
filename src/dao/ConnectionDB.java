/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author marionne pascual
 */

import java.sql.*;
import javax.swing.*;

public class ConnectionDB {
   
    private static final String URL = "jdbc:mysql://localhost:3306/enrollment_system";
    private static final String USER = "root";  
    private static final String PASSWORD = "Bjjmye112205";  

    public static Connection getConnection() throws SQLException {
        try {
            // Make sure the driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            return null; // <- important! prevents unhandled null usage
        }
    }
}
