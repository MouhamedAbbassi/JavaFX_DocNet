/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entity.Reservation;
import Config.MaConnexion;
import Entity.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ServiceReservation /*implements IService<Reservation>*/{
    
private Connection cnx = MaConnexion.getInstance().getCnx();
    
    public void ajouter(Reservation r) {
         try {
                    String req = "INSERT INTO reservation(users_id,patient_id,start,end,comment) VALUES(?,?,?,?,?)";
            
                    java.sql.PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, r.getusers_id());
        pst.setInt(2, r.getpatient_id());
        pst.setDate(3, (Date) r.getstart());
        pst.setDate(4, (Date) r.getend());
          pst.setString(5, r.getComment());
      
        

            pst.executeUpdate();
            System.out.println("Reservation ajoutee !");
        } 
        catch (SQLException ex) {
                      Logger.getLogger(ServiceReservation.class.getName()).log(Level.SEVERE, null, ex);

        }
      
    }

  
    public void modifier(Reservation r) {
        try {
        

        String requete = "UPDATE reservation SET start=?,end=?,comment=? where id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);

        
        pst.setDate(1, r.getstart());
        pst.setDate(2, r.getend());
        pst.setString(3, r.getComment());
        pst.setInt(4, r.getId());


        pst.executeUpdate();

        System.out.println("reservation est modifiée !");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    } catch (IllegalArgumentException ex) {
        System.err.println(ex.getMessage());
    }
        
    }

     public void supprimer(int id) {
        try {
            String req = "delete from reservation where id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public ObservableList<Reservation> afficher() {
        
        
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        
        
        try{
 String requete = "SELECT id,start,end,comment FROM reservation";
  Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            
            
  while (rs.next()) {
    int id = rs.getInt("id");
    java.sql.Date date1 =rs.getDate("start");
    java.sql.Date date2 =rs.getDate("end");
    String comment = rs.getString("comment");
 
    Reservation r = new Reservation(id,date1,date2,comment);
    list.add(r);
      //System.out.println(list);
  }}
catch(SQLException ex){
        System.err.println(ex.getMessage());
        }
return list ;

    }  
    
    
    public Reservation getReservationById(int id) throws SQLException {
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int reservationId = resultSet.getInt("id");
                int users_id = resultSet.getInt("users_id");
                int patient_id = resultSet.getInt("patient_id");
                Date start = resultSet.getDate("start");
                Date end = resultSet.getDate("end");
                String comment = resultSet.getString("comment");
                return new Reservation(reservationId, users_id, patient_id, start, end,comment);
            } else {
                return null;
            }
        }
    }
    public ObservableList<Reservation> getReservationForPatient(int patientId,int doctorid) {
    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    try {
        String req = "SELECT * FROM reservation where users_id = " + doctorid + " AND patient_id = " + patientId;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
        
             while (rs.next()) {
               Reservation o = new Reservation();
                o.setId(rs.getInt(1));
                o.setstart(rs.getDate("start"));
                reservations.add(o);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return reservations;
    }
   
    public ObservableList<Reservation> getall(int id) {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try {
             String req = "SELECT * FROM reservation WHERE users_id = " + id +
                " AND id NOT IN (SELECT reservations_id FROM ordonnance WHERE doctor_id = " + id + ")";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt(1));
                r.setpatient_id(rs.getInt("patient_id"));

                // Retrieve additional information from the user table
                int patientId = rs.getInt("patient_id");
                String userReq = "SELECT * FROM user WHERE id = " + patientId;
                Statement userSt = cnx.createStatement();
                ResultSet userRs = userSt.executeQuery(userReq);

                if (userRs.next()) {
                    User p = new User();
                    p.setNom(userRs.getString("nom"));
                    p.setPrenom(userRs.getString("prenom"));
                    p.setEmail(userRs.getString("email"));
                    System.out.println(p.getNom());
                    if (p != null) {
                        r.addUser(p);
                    }
                }

                reservations.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reservations;
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
}

    

