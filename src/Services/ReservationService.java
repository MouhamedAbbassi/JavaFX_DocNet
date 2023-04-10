/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.esprit.entity.Ordonnance;
import com.esprit.entity.reservation;
import com.esprit.utils.ConnexionSingleton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author asus
 */
public class ReservationService {
    Connection cnx;
     Statement st;
    public ReservationService() {
        ConnexionSingleton cs=ConnexionSingleton.getInstance();
        cnx = cs.getInstance().getCnx();
    }

      
    public ObservableList<reservation> getall(int id) {
        ObservableList<reservation> ordonnances = FXCollections.observableArrayList();
        try {
            String req = "select * from reservation where users_id="+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                reservation o = new reservation();
                o.setId(rs.getInt(1));
                o.setDate(rs.getString("date"));
            
               ordonnances.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }
    public int getIdPatient(int id) {
        int patientId = 0;
        try {
            String req = "select patient_id from reservation where id="+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {
            patientId = rs.getInt("patient_id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return patientId;
    }
    public ObservableList<reservation> getReservationForPatient(int patientId,int doctorid) {
    ObservableList<reservation> reservations = FXCollections.observableArrayList();
    try {
        String req = "SELECT * FROM reservation where users_id = " + doctorid + " AND patient_id = " + patientId;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
        
             while (rs.next()) {
               reservation o = new reservation();
                o.setId(rs.getInt(1));
                o.setDate(rs.getString("date"));
                reservations.add(o);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return reservations;
    }
    public void Inserer(reservation o  , int doctor_id, int patient_id) {
        try {
            System.out.println("444");
            String qry = "INSERT INTO reservation (users_id, patient_id, date) VALUES ('" + doctor_id + "','"+ patient_id + "','"+ o.getDate() +"')";
            Statement statement = cnx.createStatement();
            int rowsUpdated = statement.executeUpdate(qry);
            if (rowsUpdated > 0) {
              System.out.println("The record has been add it successfully.");
            } else {
              System.out.println("Failed to add it the record.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
