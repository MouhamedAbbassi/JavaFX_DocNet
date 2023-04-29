package Service;

import Entity.Rate;
import Config.MaConnexion;
import java.io.IOException;
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
 * @author selmi
 */
public class ServiceNote {

    private Connection cnx = MaConnexion.getInstance().getCnx();

    public void ajouter(Rate r) {
        try {
            String req = "INSERT INTO rate( med_id , make_rate_id,note,opinion) VALUES(?,?,?,?)";

            PreparedStatement pst = cnx.prepareStatement(req);

            pst.setInt(1, r.getmed_id());
            pst.setInt(2, r.getmake_rate_id());
            pst.setDouble(3, r.getnote());
            pst.setString(4, r.getOpinion());

            pst.executeUpdate();
            System.out.println("Rate ajoutee !");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceNote.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifier(Rate r) {
        try {

            String requete = "UPDATE rate SET note=?,opinion=? where id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);

            
            pst.setDouble(1, r.getnote());
            pst.setString(2, r.getOpinion());
            pst.setInt(3, r.getId());

            pst.executeUpdate();

            System.out.println("rate est modifiée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public ObservableList<Rate> afficher() {
        ObservableList<Rate> rates = FXCollections.observableArrayList();

        try {
            String req = "SELECT id,note,opinion FROM rate";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {

                int id = rs.getInt("id");
                /*int med_id = rs.getInt("med_id");
                int make_rate_id = rs.getInt("make_rate_id");*/
                Double note = rs.getDouble("note");
                String opinion = rs.getString("opinion");

                Rate r = new Rate(id, note, opinion);
                rates.add(r);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            
        }
        
        return rates;

    }

    public Rate getRateById(int id) throws SQLException {
        String query = "SELECT * FROM rate WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int rateId = resultSet.getInt("id");
                int med_id = resultSet.getInt("med_id");
                int make_rate_id = resultSet.getInt("make_rate_id");
                Double note = resultSet.getDouble("note");
                String opinion = resultSet.getString("opinion");
                return new Rate(rateId, med_id, make_rate_id, note, opinion);
            } else {
                return null;
            }
        }
    }
    
public ObservableList<Rate> getRatesByMedId(int med_id) throws SQLException {
    ObservableList<Rate> rates = FXCollections.observableArrayList();
    String query = "SELECT * FROM rate WHERE med_id = ?";
    try (PreparedStatement statement = cnx.prepareStatement(query)) {
        statement.setInt(1, med_id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int rateId = resultSet.getInt("id");
            int make_rate_id = resultSet.getInt("make_rate_id");
            Double note = resultSet.getDouble("note");
            String opinion = resultSet.getString("opinion");
            Rate rate = new Rate(rateId, med_id, make_rate_id, note, opinion);
            rates.add(rate);
        }
        return rates;
    }
}

    
    

    public void supprimer(int id) {
        try {
            String req = "delete from rate where id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceNote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public ObservableList<Rate> test() {
        ObservableList<Rate> rates = FXCollections.observableArrayList();

        try {
            String req = "SELECT note FROM rate";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {

                
                double note = rs.getDouble("note");
               

                Rate r = new Rate( note);
                rates.add(r);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            
        }
        
        return rates;

    }

}
