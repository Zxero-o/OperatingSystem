/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Enrollment_System;

/**
 *
 * @author marionne pascual
 */
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class StudentLogin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StudentLogin.class.getName());
    static String firstName = "";
    static String middleInitial= "";
    static String surname= "";
    static String fullname= "";
    static String gender= "";
    static String birthdate= "";
    static String mail= "";
    static String contactNo= "";
    static String EMAIL= "";
    static String yearlvl ="";

    /**
     * Creates new form StudentLogin
     */
    public StudentLogin(){
        initComponents();
        populateStudentTable();
    }
    
    private void populateStudentTable() {
        jTable1.setIntercellSpacing(new Dimension(10, 10));
        jTable1.setShowGrid(true);
        jTable1.setRowHeight(69);
        
        String[] columnNames = {"", ""};
        Object[][] data = {
        {"First name", firstName},
        {"Middle Initial", middleInitial},
        {"Surname", surname},  
        {"Gender", gender},
        {"Birthdate", birthdate},
        {"Email", mail},
        {"Contact No.", contactNo},
        {"Year Level", yearlvl}
        };

        jTable1.setModel(new DefaultTableModel(data, columnNames));
        
        jTable1.setSelectionBackground(Color.WHITE); // change selected row color
        jTable1.setSelectionForeground(Color.BLACK);      // text color when selected
        
        DefaultCellEditor editor = new DefaultCellEditor(new JTextField());
        editor.getComponent().setFont(new Font("Arial", Font.PLAIN, 30));
        jTable1.setDefaultEditor(Object.class, editor);
}
    
    private void insertStudentToDatabase() {
    String url = "jdbc:mysql://localhost:3306/enrollment_system";
    String user = "root";
    String password = "SechyAcire1118";

    // 🔹 get the currently logged in user_id from the user table based on username
    String userId = null;
    String username = Login.username; // assuming Login.java stores it statically after login

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        // First, find the User_ID based on the logged-in username
        String getUserIdQuery = "SELECT User_ID FROM user WHERE Username = ?";
        try (PreparedStatement ps = conn.prepareStatement(getUserIdQuery)) {
            ps.setString(1, username);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getString("User_ID");
                }
            }
        }

        if (userId == null) {
            JOptionPane.showMessageDialog(this, "Error: Could not find User_ID for this user.");
            return;
        }

        // 🔹 Check if this student already exists in the table
        String checkQuery = "SELECT COUNT(*) FROM student WHERE User_ID = ?";
        boolean exists = false;
        try (PreparedStatement ps = conn.prepareStatement(checkQuery)) {
            ps.setString(1, userId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    exists = true;
                }
            }
        }

        if (exists) {
            // 🔹 Update existing record
            String updateQuery = """
                UPDATE student 
                SET FirstName = ?, MiddleName = ?, LastName = ?, Gender = ?, Birthdate = ?, 
                    Email = ?, ContactNo = ?, year_level = ?, status = 'Pending' 
                WHERE User_ID = ?
            """;

            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, middleInitial);
                pstmt.setString(3, surname);
                pstmt.setString(4, gender);
                pstmt.setString(5, birthdate);
                pstmt.setString(6, mail);
                pstmt.setString(7, contactNo);
                pstmt.setString(8, yearlvl);
                pstmt.setString(9, userId);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Student information updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No rows updated. Please check your data.");
                }
            }
        } else {
            // 🔹 Insert if it doesn't exist yet
            String insertQuery = """
                INSERT INTO student (FirstName, MiddleName, LastName, Gender, Birthdate, 
                                     Email, ContactNo, year_level, status, User_ID, DateRegistered)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Pending', ?, CURDATE())
            """;

            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, middleInitial);
                pstmt.setString(3, surname);
                pstmt.setString(4, gender);
                pstmt.setString(5, birthdate);
                pstmt.setString(6, mail);
                pstmt.setString(7, contactNo);
                pstmt.setString(8, yearlvl);
                pstmt.setString(9, userId);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "New student record created successfully!");
                }
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
    }
}


  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(210, 226, 235));

        jPanel2.setBackground(new java.awt.Color(191, 218, 232));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 84)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(122, 133, 193));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ABAKADA UNIVERSITY");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1540, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(191, 218, 232));

        jPanel6.setBackground(new java.awt.Color(165, 215, 241));

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(122, 133, 193));
        jLabel2.setText("STUDENT LOGIN");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(26, 26, 26))
        );

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {""},
                {""},
                {""},
                {""},
                {""},
                {""},
                {""},
                {"Tile 2"},
                {null},
                {null}
            },
            new String [] {
                ""
            }
        ));
        jTable1.setFocusable(false);
        jTable1.setOpaque(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);

        jButton8.setFont(new java.awt.Font("Garamond", 0, 20)); // NOI18N
        jButton8.setText("SIGN-UP");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1541, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        if (jTable1.isEditing()) {
            jTable1.getCellEditor().stopCellEditing();
        }

        Object fName = jTable1.getValueAt(0, 1);
        if (fName != null) {
            firstName = fName.toString();
        } else {
            firstName = "";
        }
        Object mInit = jTable1.getValueAt(1, 1);
        if (mInit != null) {
            middleInitial = mInit.toString();
        } else {
            middleInitial = "";
        }
        Object sname       = jTable1.getValueAt(2, 1);
        if (sname != null) {
            surname = sname.toString();
        } else {
            surname = "";
        }
        Object gndr        = jTable1.getValueAt(3, 1);
        if (gndr != null) {
            gender = gndr.toString();
        } else {
            gender = "";
        }

        Object bdate     = jTable1.getValueAt(4, 1);
        if (bdate != null) {
            birthdate = bdate.toString();
        } else {
            birthdate = "";
        }

        Object mailVal = jTable1.getValueAt(5, 1);
        if (mailVal != null) {
            mail = mailVal.toString();
        } else {
            mail = "";
        }

        Object Contactval = jTable1.getValueAt(6, 1);
        if (Contactval != null) {
            contactNo = Contactval.toString();
        } else {
            contactNo = "";
        }

        Object YearLevel = jTable1.getValueAt(7,1);
        if (YearLevel != null){
            yearlvl = YearLevel.toString();
        } else {
            yearlvl = "";
        }
        fullname = firstName+ " " + middleInitial+ " " + surname;
        EMAIL = mail;
        //Enroll profileFrame = new Enroll(fullname, mail);
        
        insertStudentToDatabase();

        StudentDashboard dashboard = new StudentDashboard(Login.username);
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new StudentLogin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
