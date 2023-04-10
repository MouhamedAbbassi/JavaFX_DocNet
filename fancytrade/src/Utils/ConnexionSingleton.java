/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnexionSingleton {
      private Connection cnx;
    
    
    private final String URL = "jdbc:mysql://localhost:3306/docnet2";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private static ConnexionSingleton instance;

    private ConnexionSingleton() {
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connecting !!");
        } catch (SQLException ex) {
            Logger.getLogger(ConnexionSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ConnexionSingleton getInstance() {
        if(instance == null) {
            instance = new ConnexionSingleton();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
    
}
