/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package resourcemanagement;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JFrame;

/**
 *
 * @author tchit
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfUsername = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tfPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Username");

        jLabel2.setText("Password");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Login");

        tfPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(tfPassword))
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(btnLogin)
                .addGap(71, 71, 71))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPasswordActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        
        if (username.isEmpty() | password.isEmpty()){
            JOptionPane.showMessageDialog(this, 
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Get the database connection string
            java.sql.Connection connection = ResourceManagement.getConnection();
            
            // Prepare the SELECT statement
            String sqlSelect = "SELECT User_Id FROM User WHERE Username=? AND Password=?";
            
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect)) {
                
                // Set the parameters for the PreparedStatement
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                
                // Execute the query and get the result set
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        // Login successful message
                        JOptionPane.showMessageDialog(this,
                                "Login successful",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        
                        int userId = resultSet.getInt("User_Id");
                        String role = determineUserRole(userId);
                        openHomeFrame(role, userId);
                        this.dispose();
            
                    } else {
                        // Login failed
                        
                        // return the Login frame with errors
                        JOptionPane.showMessageDialog(this,
                                "Invalid username or password",
                                "Login failed",
                                JOptionPane.ERROR_MESSAGE);
                        
                        String role = "Unknown";
                        openHomeFrame(role, 0);
                        this.dispose();
                        
                    }
                }
                
            } catch (SQLException ex){
                ex.printStackTrace();
            } finally {
                // Close the database connection
                try {
                    if (connection != null){
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private String determineUserRole(int userId){
        String role = null;
        
        // Define SQL queries for each table
        String studentQuery = "SELECT User_Id FROM Student WHERE User_Id = ?";
        String facultyQuery = "SELECT User_Id FROM FacultyMember WHERE User_Id = ?";
        String departmentHeadsQuery = "SELECT User_Id FROM DepartmentHeads WHERE User_Id = ?";
        String adminQuery = "SELECT User_Id FROM Administrator WHERE User_Id = ?";
        
        try (Connection connection  = ResourceManagement.getConnection()){
            //Check in the Student table
            if (isUserInTable(connection, studentQuery, userId)){
                role = "Student";
            }
            else if (isUserInTable(connection, facultyQuery, userId)){
                role = "Faculty";
            }
            else if (isUserInTable(connection, departmentHeadsQuery, userId)){
                role = "Department";
            }
            else if (isUserInTable(connection, adminQuery, userId)){
                role = "Asministrator";
            }
        } catch (SQLException ex){
        ex.printStackTrace();
        }
        return role;
    }
    
    private boolean isUserInTable(Connection connection, String query, int userId) throws SQLException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        }
    }
    
    private void openHomeFrame(String role, int user_id){
        switch (role) {
            case "Student":
                StudentHome studentHomeFrame = new StudentHome();
                studentHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                studentHomeFrame.setVisible(true);
                studentHomeFrame.getStudentDetails(user_id);
                break;
            case "Faculty":
                FacultyHome facultyHomeFrame = new FacultyHome();
                facultyHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                facultyHomeFrame.setVisible(true);
                break;
            case "Department":
                DepartmentHome departmentHomeFrame = new DepartmentHome();
                departmentHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                departmentHomeFrame.setVisible(true);
                break;
            case "Administrator":
                AdminHome adminHomeFrame = new AdminHome();
                adminHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                adminHomeFrame.setVisible(true);
                break;
            case "Unknown":
                Enroll enrolmentFrame = new Enroll();
                enrolmentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                enrolmentFrame.setVisible(true);
                break;
            default:
                break;
        }
        
    }
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField tfPassword;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
