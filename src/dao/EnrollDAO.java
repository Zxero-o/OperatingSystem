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

public class EnrollDAO {

    public static void insertEnrollment(String studentID, String courseCode, String semester, int yearLevel) {
        String url = "jdbc:mysql://localhost:3306/enrollment_system";
        String user = "root";
        String pass = "SechyAcire1118";
        
        String schoolyear = getCurrentSchoolYear();
        
        if (isAlreadyEnrolled(studentID, schoolyear, semester)) {
            JOptionPane.showMessageDialog(null,
                "You are already enrolled for " + semester + " of " + schoolyear + "!");
            return; 
        }

        String sql = "INSERT INTO enrollment (Status, SchoolYear, Semester, Student_ID, CourseCode) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "Enrolled"); 
            pst.setString(2, getCurrentSchoolYear());
            pst.setString(3, semester);
            pst.setString(4, studentID);
            pst.setString(5, courseCode);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Enrollment successfully added!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting enrollment: " + e.getMessage());
        }
    }
    
    private static boolean isAlreadyEnrolled(String studentID, String schoolYear, String semester) {
        String url = "jdbc:mysql://localhost:3306/enrollment_system";
        String user = "root";
        String pass = "SechyAcire1118";

        String sql = "SELECT 1 FROM enrollment WHERE Student_ID = ? AND SchoolYear = ? AND Semester = ?";

        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, studentID);
            pst.setString(2, schoolYear);
            pst.setString(3, semester);

            ResultSet rs = pst.executeQuery();
            return rs.next(); 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking enrollment: " + e.getMessage());
            return false;
        }
    }

    private static String getCurrentSchoolYear() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int year = cal.get(java.util.Calendar.YEAR);
        return year + "-" + (year + 1);
    }
}
    
