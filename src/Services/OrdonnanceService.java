/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import com.esprit.entity.Ordonnance;
import com.esprit.entity.OrdonnanceMedicament;
import com.esprit.entity.User;
import com.esprit.entity.reservation;
import com.esprit.entity.user_user;
import com.esprit.utils.ConnexionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author asus
 */
public class OrdonnanceService {
     Connection cnx;
     Statement st;
     int ordId;
    public OrdonnanceService() {
        ConnexionSingleton cs=ConnexionSingleton.getInstance();
        cnx = cs.getInstance().getCnx();
    }
    public ObservableList<user_user> getPatientListe(int id) {
        ObservableList<user_user> ordonnances = FXCollections.observableArrayList();
       
        try {
            String req = "select * from user_user where user_source="+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                user_user o = new user_user();
                o.setUser_source(rs.getInt(1));
                o.setUser_target(rs.getInt("user_target"));
                ordonnances.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }
    public ObservableList<Ordonnance> getallOrdonnance(int id) {
        ObservableList<Ordonnance> ordonnances = FXCollections.observableArrayList();
        
        try {
            
            String req = "SELECT ordonnance.*, user.nom, user.prenom, user.image FROM ordonnance JOIN user ON ordonnance.doctor_id = user.id WHERE patient_id = " + id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Ordonnance o = new Ordonnance();
            o.setId(rs.getInt(1));
            o.setNomMedecin(rs.getString("nom_medecin"));
            o.setNomPatient(rs.getString("nom_patient"));
            o.setDate(rs.getString("date"));
            o.setCommentaire(rs.getString("commentaire"));
            o.setIdOrdonnance(rs.getInt("doctor_id"));
            // Retrieve doctor information from the user table
            User doctor = new User();
             doctor.setNom(rs.getString("nom"));
            doctor.setPrenom(rs.getString("prenom"));
            doctor.setImage(rs.getString("image"));
            o.addDoctors(doctor);
                String medicamentReq = "SELECT medicament_id, dosage, duration FROM ordonnance_medicament WHERE ordonnance_id = " + o.getId();
                Statement medicamentSt = cnx.createStatement();
                ResultSet medicamentRs = medicamentSt.executeQuery(medicamentReq);
                while (medicamentRs.next()) {
                OrdonnanceMedicament m = new OrdonnanceMedicament();
                m.setIdMedicament(medicamentRs.getInt("medicament_id"));
                m.setDosage(medicamentRs.getInt("dosage"));
                m.setDuration(medicamentRs.getInt("duration"));
                o.addMedicament(m);
            }
               ordonnances.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }
      
    public ObservableList<Ordonnance> getall(int id) {
        ObservableList<Ordonnance> ordonnances = FXCollections.observableArrayList();
     
        try {
            String req = "select * from ordonnance where doctor_id="+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Ordonnance o = new Ordonnance();
                o.setId(rs.getInt(1));
                o.setNomMedecin(rs.getString("nom_medecin"));
                o.setNomPatient(rs.getString("nom_patient"));
                o.setDate(rs.getString("date"));
                o.setCommentaire(rs.getString("commentaire"));
               
               ordonnances.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }
      public void modifier(Ordonnance o, int id) {
        try {
 
          String qry1="DELETE from ordonnance_medicament where ordonnance_id="+id;
          String qry = "UPDATE ordonnance SET nom_medecin='"+o.getNomMedecin()+"',nom_patient='"+o.getNomPatient()+"',date= CURRENT_DATE() ,commentaire='"+o.getCommentaire()+"' WHERE ID="+id+"";
        
          Statement statement = cnx.createStatement();
          int rowsUpdated1 = statement.executeUpdate(qry1);
          int rowsUpdated = statement.executeUpdate(qry);
          if (rowsUpdated > 0) {
            System.out.println("The record has been updated successfully.");
          } else {
            System.out.println("Failed to update the record.");
          }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      public void modifierOM(OrdonnanceMedicament om, int id) {
        try {
  
          
          String qry2 = "INSERT INTO ordonnance_medicament (ordonnance_id, medicament_id, dosage, duration) VALUES ('" + id + "','" + om.getIdMedicament()+ "','" + om.getDosage() + "','" + om.getDuration() + "')";
          Statement statement = cnx.createStatement();
          statement.executeUpdate(qry2);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     public void Inserer(Ordonnance o , int reservation_id, int doctor_id, int patient_id) {
        try {
           
            String qry = "INSERT INTO ordonnance (doctor_id, patient_id, reservations_id, nom_medecin, nom_patient, date, commentaire) VALUES ('" + doctor_id + "','"+ patient_id + "','"+ reservation_id + "','" + o.getNomMedecin() + "','" + o.getNomPatient() + "',CURRENT_DATE(),'" + o.getCommentaire() + "')";
            Statement statement = cnx.createStatement();
            int rowsUpdated = statement.executeUpdate(qry);
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
            rs.next();
            int ordonnanceId = rs.getInt(1);
            ordId=ordonnanceId;
            if (rowsUpdated > 0) {
              System.out.println("The record has been add it successfully.");
            } else {
              System.out.println("Failed to add it the record.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void InsererOm(OrdonnanceMedicament om) {
        try {
            String qry1 = "INSERT INTO ordonnance_medicament (ordonnance_id, medicament_id, dosage, duration) VALUES ('" + ordId + "','" + om.getIdMedicament()+ "','" + om.getDosage() + "','" + om.getDuration() + "')";
            Statement statement = cnx.createStatement();
            statement.executeUpdate(qry1);  
        }catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void delete(int id) {
        try {
          String qry1="delete from ordonnance_medicament where ordonnance_id="+id;
          String qry="delete from ordonnance where id="+id;
         
          Statement statement = cnx.createStatement();
          statement.executeUpdate(qry1);
          int rowsUpdated = statement.executeUpdate(qry);
          if (rowsUpdated > 0) {
            System.out.println("The record has been delete  successfully.");
          } else {
            System.out.println("Failed to delete the record.");
          }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      
    public ObservableList<Ordonnance> getOrdonnanceForPatient(int patientId,int doctorid) {
    ObservableList<Ordonnance> ordonnances = FXCollections.observableArrayList();
    try {
        String req = "SELECT * FROM ordonnance where doctor_id = " + doctorid + " AND patient_id = " + patientId;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
        
             while (rs.next()) {
                Ordonnance o = new Ordonnance();
                o.setId(rs.getInt(1));
                o.setDate(rs.getString("date"));
                o.setCommentaire(rs.getString("commentaire"));
                String medicamentReq = "SELECT medicament_id, dosage, duration FROM ordonnance_medicament WHERE ordonnance_id = " + o.getId();
                Statement medicamentSt = cnx.createStatement();
                ResultSet medicamentRs = medicamentSt.executeQuery(medicamentReq);
                while (medicamentRs.next()) {
                OrdonnanceMedicament m = new OrdonnanceMedicament();
                m.setIdMedicament(medicamentRs.getInt("medicament_id"));
                m.setDosage(medicamentRs.getInt("dosage"));
                m.setDuration(medicamentRs.getInt("duration"));
                o.addMedicament(m);
            }
                ordonnances.add(o);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return ordonnances;
    }
    public int getPatientId(int id) {
        int patientId = -1; // initialize with an invalid value
        try {
            String req = "SELECT patient_id FROM ordonnance WHERE id = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {
                // Fetch the patient id from the patient_id column
                patientId = rs.getInt("patient_id");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return patientId;
    }
    
}
