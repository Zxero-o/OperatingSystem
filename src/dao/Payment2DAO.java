/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Yang
 */

import java.sql.*;
import javax.swing.JOptionPane;

public class Payment2DAO {
    
   public static void insertPayment(int enrollmentID, double amountDue, String method) {
        String url = "jdbc:mysql://localhost:3306/enrollment_system";
        String user = "root";
        String pass = "SechyAcire1118";

        String sql = "INSERT INTO payment (AmountPaid, AmountDue, PaymentStatus, PaymentDay, Enrollment_ID) "
                   + "VALUES (?, ?, ?, CURDATE(), ?)";

        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, 0.00); // Initially unpaid
            pst.setDouble(2, amountDue);
            pst.setString(3, "Pending"); // Default status
            pst.setInt(4, enrollmentID);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, 
                    "Payment record created successfully for " + method + " method.\nTotal due: ₱" + amountDue);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error inserting payment: " + e.getMessage());
        }
    }
   
   public static int getLastEnrollmentID(String studentID) {
    String sql = "SELECT Enrollment_ID FROM enrollment WHERE Student_ID = ? ORDER BY Enrollment_ID DESC LIMIT 1";
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/enrollment_system", "root", "SechyAcire1118");
         PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, studentID);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt("Enrollment_ID");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error retrieving enrollment ID: " + e.getMessage());
    }
    return -1;
}
}
