package Service;

import Entity.Medicament;
import Config.MaConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Firas
 */
public class ServiceMedicament {
    Connection cnx;
    Statement st;
    
    public ServiceMedicament() {
        MaConnexion cs=MaConnexion.getInstance();
        cnx = cs.getInstance().getCnx();
    }
    
    public ObservableList<Medicament> getAll() {
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
        try {
            String req = "select * from medicament";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Medicament m = new Medicament();
                m.setId(rs.getInt(1));
                m.setNom(rs.getString("nom"));
                m.setNb_dose(rs.getInt("nb_dose"));
                m.setPrix(rs.getInt("prix"));
                m.setType(rs.getString("type"));
                m.setStock(rs.getInt("stock"));
                System.out.println(m);
                medicaments.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMedicament.class.getName()).log(Level.SEVERE, null, ex);
        }
        return medicaments;
    }
    
    
    public void ajouterm(Medicament m) {
        try {
            String req = "INSERT INTO medicament(nom, type, nb_dose, prix, stock, image) VALUES(?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, m.getNom());
            pst.setString(2, m.getType());
            pst.setInt(3, m.getNb_dose());
            pst.setInt(4, m.getPrix());
            pst.setInt(5, m.getStock());
            pst.setString(6, m.getImage());

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMedicament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void supprimerm(int id) {
        try {
            String req = "delete from medicament where id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMedicament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modifierm(Medicament m) {
        try {
            String requete = "UPDATE medicament SET nom=?, type=?, nb_dose=?, prix=?, stock=?, image=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);

            pst.setString(1, m.getNom());
            pst.setString(2, m.getType());
            pst.setInt(3, m.getNb_dose());
            pst.setInt(4, m.getPrix());
            pst.setInt(5, m.getStock());
            pst.setString(6, m.getImage());
            pst.setInt(7, m.getId());
            pst.executeUpdate();
         System.out.println("Pharmacie mise à jour avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
      public void deletem(int id) {
        try {
            String req = "DELETE FROM medicament WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Medicamnt supprimée avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      public ObservableList<Medicament> searchMedicament(String nom) {
    ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
    try {
        String req = "SELECT * FROM medicament WHERE nom LIKE '%" + nom + "%'";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Medicament m = new Medicament();
            m.setId(rs.getInt(1));
            m.setNom(rs.getString("nom"));
            m.setNb_dose(rs.getInt("nb_dose"));
            m.setPrix(rs.getInt("prix"));
            m.setType(rs.getString("type"));
            m.setStock(rs.getInt("stock"));
            medicaments.add(m);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ServiceMedicament.class.getName()).log(Level.SEVERE, null, ex);
    }
    return medicaments;
}
      public ObservableList<Medicament> getall() {
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList();
        try {
            String req = "select * from medicament";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Medicament o = new Medicament();
                o.setId(rs.getInt(1));
                o.setNom(rs.getString("nom"));
                o.setNb_dose(rs.getInt("nb_dose"));
                o.setPrix(rs.getInt("prix"));
                o.setType(rs.getString("type"));
                o.setStock(rs.getInt("stock"));
                medicaments.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return medicaments;
    }
    
    public int getIdByName(String nomMedicament) {
    ServiceMedicament service = new ServiceMedicament();
    ObservableList<Medicament> listM = service.getall();
    
    for (Medicament m : listM) {
        if (m.getNom().equals(nomMedicament)) {
            return m.getId();
        }
    }
    return -1; // return -1 if the medicament is not found in the list
    }
    public String getMedicamentNameById(int medicamentId) {
    String medicamentName = null;
    try {
        String req = "SELECT nom FROM medicament WHERE id ="+medicamentId;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
        
        
        if (rs.next()) {
            medicamentName = rs.getString("nom");
        }
       
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return medicamentName;
}
      
}