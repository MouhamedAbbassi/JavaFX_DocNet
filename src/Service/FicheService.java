/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Fiche;
import Entity.Medicament;
import Entity.Ordonnance;
import Config.MaConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class FicheService {
    Connection cnx;
     Statement st;
    public FicheService() {
        MaConnexion cs=MaConnexion.getInstance();
        cnx = cs.getInstance().getCnx();
    }
    
    public Fiche getFiche(int idDoctor , int idPatient) {
        Fiche fiche = null;
        try {
            String req = "SELECT * FROM fiche WHERE doctor_id = " + idDoctor + " AND patient_id = " + idPatient;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {
                fiche = new Fiche();
                fiche.setId(rs.getInt(1));
                fiche.setDate(rs.getString("date_naissance"));
                fiche.setTel(rs.getInt("tel"));
                fiche.setEtatClinique(rs.getString("etat_clinique"));
                fiche.setGenre(rs.getString("genre"));
                fiche.setTypeAssurance(rs.getString("type_assurance"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fiche;
    } 
    public void modifier(Fiche o, int id) {
        try {
 
          
          String qry = "UPDATE fiche SET date_naissance='"+o.getDate()+"',tel='"+o.getTel()+"',etat_clinique='"+o.getEtatClinique()+"',genre='"+o.getGenre()+"',type_assurance='"+o.getTypeAssurance()+"' WHERE ID="+id+"";
          System.out.println(qry);
          Statement statement = cnx.createStatement();
          int rowsUpdated1 = statement.executeUpdate(qry);
          if (rowsUpdated1 > 0) {
            System.out.println("The record delete has been updated successfully.");
          } else {
            System.out.println("Failed to delete the record.");
          }
           
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void InsererFiche(int doctor_id, int patient_id) {
    try {
        // Check if the row already exists
        String selectQry = "SELECT COUNT(*) FROM fiche WHERE doctor_id=? AND patient_id=?";
        PreparedStatement selectStmt = MaConnexion.getInstance().getCnx().prepareStatement(selectQry);
        selectStmt.setInt(1, doctor_id);
        selectStmt.setInt(2, patient_id);
        ResultSet rs = selectStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        if (count > 0) {
            // The row already exists
            System.out.println("The row already exists.");
            return;
        }

        // Insert the new row
        String insertQry = "INSERT INTO fiche (doctor_id, patient_id) VALUES (?, ?)";
        PreparedStatement insertStmt = MaConnexion.getInstance().getCnx().prepareStatement(insertQry);
        insertStmt.setInt(1, doctor_id);
        insertStmt.setInt(2, patient_id);
        int rowsUpdated = insertStmt.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("The record has been added successfully.");
        } else {
            System.out.println("Failed to add the record.");
        }

    } catch (SQLException ex) {
        Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}
