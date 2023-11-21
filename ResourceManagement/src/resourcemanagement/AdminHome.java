/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package resourcemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tchit
 */
public class AdminHome extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public AdminHome() {
        initComponents();
        getStudents();
        getDepartmentHeads();
        getFacultyMembers();
        getCourses();
    }
    
    private void getFacultyMembers(){
        String facultyQuery = "SELECT * FROM Faculty_Member";
        
        try (Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(facultyQuery);
                ResultSet resultSet = preparedStatement.executeQuery()){
            
            DefaultTableModel model = (DefaultTableModel) facultyTable.getModel();
            
            // Clear the table data before populating it with new data
            model.setRowCount(0);
            
            while (resultSet.next()){
                String firstName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("Phone_Number");
                String hireDate = resultSet.getString("Hire_Date");
                String faculty = getFaculty(resultSet.getInt("Faculty_Id"));
                String role = getUserRole(resultSet.getInt("User_Id"));
                
                // Add the retrieved data to the table model
                model.addRow(new Object[]{firstName, lastName, email, phoneNumber, hireDate, faculty, role});
                returnUsers(firstName, lastName, email, phoneNumber, "Faculty_Member");
                
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    private String getUserRole(int userId){
        String userRoleQuery = "SELECT * FROM UserRole WHERE User_Id=?";
        
        try (Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(userRoleQuery)){
            
            preparedStatement.setInt(1, userId);
            
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                int RoleId = resultSet.getInt("Role_Id");
                String roleName = getRoleName(RoleId);
                return roleName;
            }
            
        } catch (SQLException e){
            e.printStackTrace();
            return "Unknown";
        }
    }
    
    private String getRoleName(int roleId){
        String roleQuery = "SELECT * FROM Role WHERE Role_Id=?";
        
        try (Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(roleQuery)){
            
            preparedStatement.setInt(1, roleId);
            
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                String roleName = resultSet.getString("Role_Name");       
                return roleName;
            }
            
        } catch (SQLException e){
            e.printStackTrace();
            return "Unknown";
        }
    }
    
    private String getFaculty(int facultyId){
        String facultyQuery = "SELECT Faculty_Name FROM Faculty WHERE Faculty_Id=?";
        
        try (Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(facultyQuery)){
            
            preparedStatement.setInt(1, facultyId);
            
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                
                String faculty = resultSet.getString("Faculty_Name");
                return faculty;
            }
            
        } catch (SQLException e){
            e.printStackTrace();
            return "Unassigned";
        }
    }
    
    private void getDepartmentHeads(){
        String departmentQuery = "SELECT * FROM Department_Heads";
        
        try (Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(departmentQuery);
                ResultSet resultSet = preparedStatement.executeQuery()){
            
            DefaultTableModel model = (DefaultTableModel) departmentTable.getModel();
            
            // Clear the table data before populating it with new data
            model.setRowCount(0);
            
            while (resultSet.next()){
                String firstName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("Phone_Number");
                String department = getDepartment(resultSet.getInt("Department_Head_Id"));
                
                // Add the retrieved data to the table model
                model.addRow(new Object[]{firstName, lastName, email, phoneNumber, department});
                returnUsers(firstName, lastName, email, phoneNumber, "Department_Heads");
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    private String getDepartment(int departmentId){
        String departmentQuery = "SELECT Department_Name FROM Department WHERE Department_Id=?";
        
        try (Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(departmentQuery)){
            
            preparedStatement.setInt(1, departmentId);
            
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                
                String department = resultSet.getString("Department_Name");
                return department;
            }
            
        } catch (SQLException e){
            e.printStackTrace();
            return "Unassigned";
        }
    }
    
    private void getCourses(){
        String courseQuery = "SELECT * FROM Course";
        
        try (Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(courseQuery);
                ResultSet resultSet = preparedStatement.executeQuery()){
            
            DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
            
            // Clear the table data before populating it with new data
            model.setRowCount(0);
            
            while (resultSet.next()){
                String code = resultSet.getString("Code");
                String name = resultSet.getString("Course_Name");
                String credits = resultSet.getString("Credits");
                
                // Add the retrieved data to the table model
                model.addRow(new Object[]{code, name, credits});
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void getStudents(){
        String studentQuery = "SELECT * FROM Student";
        
        try(Connection connection = ResourceManagement.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(studentQuery);
                ResultSet resultSet = preparedStatement.executeQuery()){
            
            DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
            
            // Clear the table data before populating it with new data
            model.setRowCount(0);
            
            while (resultSet.next()){
                String firstName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("Phone_Number");
                
                // Add the retrieved data to the table model
                model.addRow(new Object[]{firstName, lastName, email, phoneNumber});
                returnUsers(firstName, lastName, email, phoneNumber, "Student");
                
            }
            
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    private void returnUsers(String firstName, String lastName, String email, String phoneNumber, String position){
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.addRow(new Object[]{firstName, lastName, email, phoneNumber, position});
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        courseTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        facultyTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        departmentTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        btnUpdateUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Student Page");

        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Email", "Phone Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(studentTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Student", jPanel1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Course Page");

        courseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Code", "Name", "Credits"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(courseTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Course", jPanel2);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Faculty Page");

        facultyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Email", "Phone Number", "Hire Date", "Faculty", "Role"
            }
        ));
        jScrollPane4.setViewportView(facultyTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Faculty", jPanel3);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Department Page");

        departmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Email", "Phone Number", "Department"
            }
        ));
        jScrollPane5.setViewportView(departmentTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Department", jPanel4);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("User Profile Management");

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Email", "Phone Number", "Position"
            }
        ));
        jScrollPane6.setViewportView(userTable);

        btnUpdateUser.setText("Edit User");
        btnUpdateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserActionPerformed(evt);
            }
        });

        btnDeleteUser.setText("Delete User");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnUpdateUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(53, 53, 53)
                        .addComponent(btnDeleteUser))
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnUpdateUser)
                    .addComponent(btnDeleteUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Profiles", jPanel5);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("System Settings");

        jButton1.setText("Backup Data");

        jButton2.setText("System Report");

        jButton3.setText("System Updates");

        jButton4.setText("Manage Documents");

        jButton5.setText("Form Builder");

        jButton6.setText("Communication Center");

        jButton7.setText("Security and Compliance");

        jButton8.setText("Payment and Fee Settings");

        jButton9.setText("Integration Settings");

        jButton10.setText("Automation Rules");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Deadline Management");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Interview Scheduling");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Waitlist Management");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Admissions Events Management");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("Feedback and Reporting Tools");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Accessibility Settings");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton8)
                    .addComponent(jButton7)
                    .addComponent(jButton6)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(jButton14)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addGap(60, 60, 60))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton9))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton10))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton12))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton13))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton14))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton15))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton16))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("System", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        int row = userTable.getSelectedRow();
        
        if (row < 0){
            JOptionPane.showMessageDialog(this,
                    "No row is selected. Please select a row",
                    "Select row",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Get the database connection from the TaskManager class
            Connection connection = ResourceManagement.getConnection();
            
            try{
                String userToDelete = (String) userTable.getValueAt(row, 0);
                // Prepare the INSERT statement
                String sqlInsert = "DELETE FROM User WHERE Username = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)){
                    preparedStatement.setString(1, userToDelete);           

                    // Execute the DELETE statement
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0){
                        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
                        model.removeRow(row);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnUpdateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserActionPerformed
        int row = userTable.getSelectedRow();
        
        
        if (row < 0){
            JOptionPane.showMessageDialog(this,
                    "No row is selected. Please select a row",
                    "Select row",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            String tableName;
            String position = (String) userTable.getValueAt(row, 4);
            
            if (position.equals("Faculty_Member")){
                tableName = "Faculty_Member";
                updateRelevantPositionTable(tableName, row);
            } else if (position.equals("Department_Heads")){
                tableName = "Department_Heads";
                updateRelevantPositionTable(tableName, row);
            } else if (position.equals("Student")){
                tableName = "Student";
                updateRelevantPositionTable(tableName, row);
            } 
        }
    }//GEN-LAST:event_btnUpdateUserActionPerformed

    private void updateRelevantPositionTable(String tableName, int row){
        // Get the database connection from the TaskManager class
        String firstName = (String) userTable.getValueAt(row, 0);
        String lastName = (String) userTable.getValueAt(row, 1);
        String email = (String) userTable.getValueAt(row, 2);
        String phoneNumber = (String) userTable.getValueAt(row, 3);
        
        Connection connection = ResourceManagement.getConnection();
            
        try{
            String userToUpdate = (String) userTable.getValueAt(row, 0);
            // Prepare the INSERT statement
            String sqlInsert = "UPDATE "+ tableName +" SET First_Name=?, Last_Name=?, Email=?, PhoneNumber=? WHERE First_Name=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)){
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(1, lastName);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(1, phoneNumber);
                    preparedStatement.setString(1, userToUpdate);

                    // Execute the DELETE statement
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0){
                        
                        JOptionPane.showMessageDialog(this, 
                        "User has been edited",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnUpdateUser;
    private javax.swing.JTable courseTable;
    private javax.swing.JTable departmentTable;
    private javax.swing.JTable facultyTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTree jTree1;
    private javax.swing.JTable studentTable;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
