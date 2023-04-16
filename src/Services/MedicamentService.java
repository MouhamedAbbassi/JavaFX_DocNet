/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.esprit.entity.Medicament;
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
public class MedicamentService {
    Connection cnx;
     Statement st;
    public MedicamentService() {
        ConnexionSingleton cs=ConnexionSingleton.getInstance();
        cnx = cs.getInstance().getCnx();
    }

      
    public ObservableList<Medicament> getall() {
        ObservableList<Medicament> ordonnances = FXCollections.observableArrayList();
        try {
            String req = "select * from medicament";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Medicament o = new Medicament();
                o.setId(rs.getInt(1));
                o.setNom(rs.getString("nom"));
                o.setNb_dose(rs.getString("nb_dose"));
                o.setPrix(rs.getInt("prix"));
                o.setType(rs.getString("type"));
                o.setStock(rs.getInt("stock"));
                ordonnances.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }
    
    public int getIdByName(String nomMedicament) {
    MedicamentService service = new MedicamentService();
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
