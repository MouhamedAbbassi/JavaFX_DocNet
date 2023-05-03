/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.OrdonnanceMedicament;
import Config.MaConnexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author asus
 */
public class OrdonnanceMedicamentService {
        Connection cnx;
     Statement st;
    public OrdonnanceMedicamentService() {
        MaConnexion cs=MaConnexion.getInstance();
        cnx = cs.getInstance().getCnx();
    }
    public ObservableList<OrdonnanceMedicament> getMedicamentByOrdonnance(int id) {
        ObservableList<OrdonnanceMedicament> ordonnances = FXCollections.observableArrayList();
        try {
            String req = "select * from ordonnance_medicament where ordonnance_id="+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                OrdonnanceMedicament o = new OrdonnanceMedicament();
                o.setId(rs.getInt(1));
                o.setDosage(rs.getInt("dosage"));
                o.setDuration(rs.getInt("duration"));
                o.setIdMedicament(rs.getInt("medicament_id"));
                System.out.println(o);
               ordonnances.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }
}
