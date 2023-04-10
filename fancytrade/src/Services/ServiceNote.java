
package Services;
import Entity.Rate;
import Utils.ConnexionSingleton;
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
    
    private Connection cnx = ConnexionSingleton.getInstance().getCnx();
    
    
       public void ajouter(Rate r) {
         try {
                    String req = "INSERT INTO rate( med_id , make_rate_id,note,opinion) VALUES(?,?,?,?)";
            
PreparedStatement pst = cnx.prepareStatement(req);            
        
        
        pst.setInt(1, r.getmed_id());
        pst.setInt(2, r.getmake_rate_id());
        pst.setDouble(3, r.getnote() );
        pst.setString(4, r.getOpinion() );
         
      
        

            pst.executeUpdate();
            System.out.println("Rate ajoutee !");
        } 
        catch (SQLException ex) {
            Logger.getLogger(ServiceNote.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
       
       
           public void modifier(Rate r) {
        try {
        

        String requete = "UPDATE rate SET med_id=?,make_rate_id=?,note=?,opinion=? where id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);

        pst.setInt(1, r.getmed_id());
        pst.setInt(2, r.getmake_rate_id());
        pst.setDouble(3, r.getnote());
        pst.setString(4, r.getOpinion() );
        pst.setInt(5, r.getId());


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
        
        
        try{
 String req = "SELECT * FROM rate";
      Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

  while (rs.next()) {


    int id = rs.getInt("id");
    int med_id = rs.getInt("med_id");
    int make_rate_id = rs.getInt("make_rate_id");
    double note = rs.getInt("note");
    String opinion = rs.getString("opinion");
 
  
    Rate r = new Rate(id,med_id,make_rate_id,note,opinion);
    rates.add(r);
   
  }}
catch(SQLException ex){
        System.err.println(ex.getMessage());
        }
return rates ;

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
           
           
}
