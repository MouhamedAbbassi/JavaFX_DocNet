package Config;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MaConnexion {

    public String url="jdbc:mysql://localhost:3306/docnet";
    public String user="root";
    public String pwd="";
    public static MaConnexion conx;
    private Connection cnx;

    private MaConnexion(){
        try {
            cnx=DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion etablie");
        } catch (SQLException ex) {
            System.out.println("error");

            System.out.println(ex.getMessage());
        }
    }
    public static MaConnexion getInstance(){
        if(conx==null)
            conx= new MaConnexion();
        return conx;

    }

    public Connection getCnx() {
        return cnx;
    }

}