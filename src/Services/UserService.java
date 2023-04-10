/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.esprit.entity.User;
import com.esprit.entity.user_user;
import com.esprit.utils.ConnexionSingleton;
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
 * @author asus
 */
public class UserService {
    Connection cnx;
     Statement st;
     
    public UserService() {
        ConnexionSingleton cs=ConnexionSingleton.getInstance();
        cnx = cs.getInstance().getCnx();
    }
    
    private static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public int getUserConnectedId() {
    if (currentUser != null) {
        return currentUser.getId();
    }
    return -1; // or throw an exception
    }
    public User findUserByUsernameAndPassword(String username, String password) throws SQLException{
      
        String query = "SELECT * FROM user WHERE email = '"+username+ "'";
        Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
        User user = null;
        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("nom");
            String email = rs.getString("email");
            user = new User(id, name, password, email);
        }
       
        return user;
    }
    
    public int getCurrentUserId() {
        return currentUser.getId();
    }
    
    public ObservableList<User> getPatientsFromUserUserList(ObservableList<user_user> userUserList) {
    ObservableList<User> patients = FXCollections.observableArrayList();
    try {
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        for (user_user userUser : userUserList) {
            statement.setInt(1, userUser.getUser_target());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User patient = new User();
                patient.setId(rs.getInt("id"));
                patient.setUsername(rs.getString("nom"));
                patient.setEmail(rs.getString("email"));
                patient.setLastname(rs.getString("prenom"));
                patient.setImage(rs.getString("image"));
                // Add other fields as needed
                patients.add(patient);
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return patients;
    }
    public User findUser(int id) throws SQLException{
      
        String query = "SELECT * FROM user WHERE id = '"+id+ "'";
        Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
        User user = null;
        if (rs.next()) {
            int id1 = rs.getInt("id");
            String name = rs.getString("nom");
            String email = rs.getString("email");
            String lastname = rs.getString("prenom");
            user = new User(id1, name, lastname, email);
        }
       
        return user;
    }public void InsererUserUser(int doctor_id, int patient_id) {
        try {
            
            String qry = "INSERT INTO user_user (user_source, user_target) VALUES ('" + doctor_id + "','"+ patient_id +"')";
            Statement statement = cnx.createStatement();
            int rowsUpdated = statement.executeUpdate(qry);
            if (rowsUpdated > 0) {
              System.out.println("The record has been add it successfully.");
            } else {
              System.out.println("Failed to add it the record.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdonnanceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     public List<User> getuserbyRole(String role) {
    List<User> users = new ArrayList<>();
    try {
        String qry = "SELECT * FROM user WHERE roles = ?";
        PreparedStatement statement = cnx.prepareStatement(qry);
        statement.setString(1, role);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setLastname(resultSet.getString("nom"));
            user.setUsername(resultSet.getString("prenom"));
            users.add(user);
        }

    } catch (SQLException ex) {
        Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return users;
     }
}
