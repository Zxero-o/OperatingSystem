/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Yang
 */
    
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopupDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/enrollment_system";
    private static final String USER = "root";  // change if needed
    private static final String PASSWORD = "SechyAcire1118";  // add password if your MySQL has one

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public int getTotalStudents() {
        String query = "SELECT COUNT(*) AS total FROM student";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            
        }
        return 0;
    }

    public int getTotalInstructors() {
        String query = "SELECT COUNT(*) AS total FROM instructor";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
        }
        return 0;
    }
}
