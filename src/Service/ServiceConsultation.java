/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Consultation;
import Config.MaConnexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author selmi
 */
public class ServiceConsultation {
     private Connection cnx = MaConnexion.getInstance().getCnx();
    
     public ObservableList<Consultation> afficher() {
        ObservableList<Consultation> c = FXCollections.observableArrayList();

        try {
            String req = "SELECT * FROM consultation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {

                int id = rs.getInt("id");
                String date = rs.getString("date");
               String comment = rs.getString("comment");
                String medecin = rs.getString("medecin");

                Consultation r = new Consultation(id, date, comment, medecin);
                c.add(r);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            
        }
        
        return c
                ;

    }
    
}
