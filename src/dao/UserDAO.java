/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import javax.swing.*;
import java.sql.*;

/**
 *
 * @author marionne pascual
 */
public class UserDAO {
    
     public static void CreatingUser(User user) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDB.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Database connection failed!");
                return;
            }
            st = conn.createStatement();

            
            String checkSQL = "SELECT Username FROM user WHERE Username = '" + user.getUsername() + "'";
            rs = st.executeQuery(checkSQL);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Username already exists. Choose another.");
                rs.close();
                return;
            }
            rs.close();

           
            String lastIDQuery = "SELECT User_ID FROM user ORDER BY User_ID DESC LIMIT 1";
            rs = st.executeQuery(lastIDQuery);

            String newUser_ID;
            if (rs.next()) {
                String lastID = rs.getString("User_ID"); 
               
                String[] parts = lastID.split("-");
                int num = 0;
                try { num = Integer.parseInt(parts[1]); } catch (Exception ex) { num = 0; }
                newUser_ID = String.format("25-%06d", num + 1);
            } else {
                newUser_ID = "25-000001";
            }
            rs.close();
            
            String insertUser = "INSERT INTO user (User_ID, Username, Password, Role, Status) VALUES ('"
                    + newUser_ID + "', '"
                    + user.getUsername() + "', '"
                    + user.getPassword() + "', '"
                    + user.getRole() + "', 'Active')";
            st.executeUpdate(insertUser);
            JOptionPane.showMessageDialog(null, "User created successfully! UserID = " + newUser_ID);

     
            switch (user.getRole()) {
                case "Student" -> insertStudentUsingConnection(conn, newUser_ID);
                case "Instructor" -> insertInstructorUsingConnection(conn, newUser_ID);
                case "Cashier" -> insertCashierUsingConnection(conn, newUser_ID);
                case "Admin" -> {
                }
                default -> JOptionPane.showMessageDialog(null, "Unknown role type!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "SQL Error creating user: " + sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error creating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
            }
        }
    }

    
    private static void insertStudentUsingConnection(Connection conn, String user_ID) throws SQLException {
        Statement st = null;
        try {
            st = conn.createStatement();
            String student = "INSERT INTO student "
                    + "(FirstName, MiddleName, LastName, Gender, Birthdate, Address, ContactNo, Email, status, User_ID) "
                    + "VALUES ('', '', '', NULL, NULL, '', '', '', 'Pending', '" + user_ID + "')";
            st.executeUpdate(student);
            JOptionPane.showMessageDialog(null, "Student record created.");
        } finally {
            if (st != null) st.close();
        }
    }

    private static void insertInstructorUsingConnection(Connection conn, String user_ID) throws SQLException {
        Statement st = null;
        try {
            st = conn.createStatement();
            String instructor = "INSERT INTO instructor "
                    + "(Firstname, MiddleName, LastName, ContactNo, Email, DeptName, User_ID) "
                    + "VALUES ('', '', '', '', '', NULL, '" + user_ID + "')";
            st.executeUpdate(instructor);
            JOptionPane.showMessageDialog(null, "Instructor record created.");
        } finally {
            if (st != null) st.close();
        }
    }

    private static void insertCashierUsingConnection(Connection conn, String user_ID) throws SQLException {
        Statement st = null;
        try {
            st = conn.createStatement();
            String cashier = "INSERT INTO cashier "
                    + "(FirstName, MiddleName, LastName, ContactNo, Email, User_ID) "
                    + "VALUES ('', '', '', '', '', '" + user_ID + "')";
            st.executeUpdate(cashier);
            JOptionPane.showMessageDialog(null, "Cashier record created.");
        } finally {
            if (st != null) st.close();
        }
    }
}
