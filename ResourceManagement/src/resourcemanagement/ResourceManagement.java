/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package resourcemanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tchit
 */
public class ResourceManagement {
    
    public static Connection getConnection(){
        Connection connection = null;
            
        try{
            // Load the JDBC driver
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/Users/ic westgate/Documents/SQL Lite Programmes/University2.db";
            
            // Establish the connection
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        
        return connection;
    };
    
    public static void main(String[] args) {
        Connection connection = ResourceManagement.getConnection();
        
        if (connection != null){
            System.out.println("Connection to database established.");
            try {
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            System.err.println("Failed to connect to database");
        }
        
            Login login = new Login();
            login.setVisible(true);
                        
    }   
}
