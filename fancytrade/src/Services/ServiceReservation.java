/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entity.Reservation;
import Utils.ConnexionSingleton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ServiceReservation implements IService<Reservation>{
     private Connection cnx = ConnexionSingleton.getInstance().getCnx();

    
    public void ajouter(Reservation r) {
         try {
                    String req = "INSERT INTO reservation(users_id,patient_id,start,end,comment) VALUES(?,?,?,?,?)";
            
                    java.sql.PreparedStatement pst = cnx.prepareStatement(req);
            
        
        
        pst.setInt(1, r.getusers_id());
        pst.setInt(2, r.getpatient_id());
        pst.setDate(3, r.getstart() );
        pst.setDate(4, r.getend() );
          pst.setString(5, r.getComment());
      
        

            pst.executeUpdate();
            System.out.println("Reservation ajoutee !");
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      
    }

  
    public void modifier(Reservation r) {
        try {
        

        String requete = "UPDATE reservation SET users_id=?,patient_id=?,start=?,end=?,comment=? where id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);

       

       
         
            pst.setInt(1, r.getusers_id());
        pst.setInt(2, r.getpatient_id());
        pst.setDate(3, r.getstart() );
        pst.setDate(4, r.getend() );
         pst.setString(5, r.getComment());
         pst.setInt(6, r.getId());


        pst.executeUpdate();

        System.out.println("reservation est modifiée !");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    } catch (IllegalArgumentException ex) {
        System.err.println(ex.getMessage());
    }
        
    }

    public void supprimer(Reservation r) {
        String req = "DELETE FROM reservation WHERE id="+r.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("reservation supprimee !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public List<Reservation> afficher() {
        List<Reservation> list = new ArrayList<>();
        
        
        try{
 String requete = "SELECT * FROM reservation";
  PreparedStatement pst = cnx.prepareStatement(requete);
  ResultSet rs = pst.executeQuery();

  while (rs.next()) {

java.sql.Date date1 =rs.getDate("start");
LocalDate Localdate1= date1.toLocalDate();

java.sql.Date date2 =rs.getDate("end");
LocalDate Localdate2= date2.toLocalDate();
    int id = rs.getInt("id");
    int users_id = rs.getInt("users_id");
    int patient_id = rs.getInt("patient_id");
   
    String comment = rs.getString("comment");
 
    
   

    
   

    Reservation r = new Reservation(id,users_id,patient_id,Localdate1,Localdate2,comment);
    list.add(r);
      System.out.println(list);
  }}
catch(SQLException ex){
        System.err.println(ex.getMessage());
        }
return list ;

    }  
}

    

