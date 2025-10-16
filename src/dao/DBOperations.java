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


public class DBOperations {
   
    
    public static void setDataorDelete(String Query, String msg){
        Connection conn = null;
        Statement st = null;
        try{
            conn = ConnectionDB.getConnection();
            st = conn.createStatement();
            st.executeUpdate(Query);
            if(!msg.equals(""))
                JOptionPane.showMessageDialog(null, msg);

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Error loading", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ignored) {}
        }
    }
    
    public static ResultSet getData(String query){
        try{
            Connection conn = ConnectionDB.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            return rs;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public static String getUserIDByUsername(String username) {
        String user_Id = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
    try {
        conn = ConnectionDB.getConnection();
        st = conn.createStatement();
        String q = "SELECT User_ID FROM user WHERE Username = '" + username + "' LIMIT 1";
        rs = st.executeQuery(q);
        if (rs.next()) {
            user_Id = rs.getString("User_ID");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error fetching UserID: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conn != null) conn.close();
        } catch (SQLException ignore) {}
    }
    return user_Id;
}
}
