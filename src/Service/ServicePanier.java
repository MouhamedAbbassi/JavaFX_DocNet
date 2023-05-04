package Service;

import Entity.Medicament;
import Entity.Panier;
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

public class ServicePanier {

    Connection cnx;
    Statement st;

    public ServicePanier() {
        MaConnexion cs=MaConnexion.getInstance();
        cnx = cs.getInstance().getCnx();
    }

    public ObservableList<Panier> getAll() {
        ObservableList<Panier> paniers = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM panier";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Panier p = new Panier();
                p.setId(rs.getInt("id"));
                p.setId_medica(rs.getInt("id_medica"));
                p.setId_user(rs.getInt("id_user"));
                p.setPrix(rs.getInt("prix"));
                p.setQte(rs.getInt("qte"));
                paniers.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paniers;
    }

    public void ajouterp(Panier m) {
        try {
            String req = "INSERT INTO panier(id_user, id_medica, qte, prix) VALUES(?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, m.getId_user());
            pst.setInt(2, m.getId_medica());
            pst.setInt(3, m.getQte());
            pst.setInt(4, m.getPrix());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerp(int id) {
        try {
            String req = "DELETE FROM panier WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Medicamnt supprimée avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierp(Panier m) {
        try {
            String req = "UPDATE panier SET id_user=?, id_medica=?, qte=?, prix=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, m.getId_user());
            pst.setInt(2, m.getId_medica());
            pst.setInt(3, m.getQte());
            pst.setInt(4, m.getPrix());
            pst.setInt(5, m.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Panier> rechercherp(String keyword) {
        List<Panier> paniers = new ArrayList<>();
        try {
            String req = "SELECT * FROM panier WHERE id_user LIKE '%" + keyword + "%' OR id_medica LIKE '%" + keyword + "%'";
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Panier p = new Panier();
                p.setId(rs.getInt("id"));
                p.setId_medica(rs.getInt("id_medica"));
                p.setId_user(rs.getInt("id_user"));
                p.setPrix(rs.getInt("prix"));
                p.setQte(rs.getInt("qte"));
                paniers.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paniers;
    }
    public Panier getPrix(Medicament m) {
        System.out.println(m.getId());
        Panier p= new Panier();
        try {
            String req = "SELECT m.*, p.qte FROM medicament m JOIN panierx p ON m.id = p.id_medica_id WHERE m.id = " + m.getId();
            
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            /*ResultSet rspanier = st.executeQuery(pa);
            while (rspanier.next()) {
               
               
                
                p.setQte(rs.getInt("qte"));
                
            }*/
            while (rs.next()) {
               
                p.setId(rs.getInt(1));
                p.setPrix(rs.getInt("prix"));
                p.setNom(rs.getString("nom"));
                p.setQte(rs.getInt("qte"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMedicament.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
   public void modifierQte(Medicament m , int qte) {
        try {
            String req = "UPDATE panierx SET qte=? WHERE id_medica_id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
           
            pst.setInt(1, qte);
            
            pst.setInt(2, m.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
