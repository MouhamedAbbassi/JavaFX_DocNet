package Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entity.User;
import Config.MaConnexion;
import Entity.Role;
import Entity.user_user;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;
public class ServiceUser
{
public static UserSession userSession;
     
     
public boolean register(User user) 
     {
         String roleType="";
         if (user.getRole()==Role.patient)
         {
          roleType = "[\"ROLE_PATIENT\"]";
         }
         else if (user.getRole()==Role.medecin)
         {
         roleType = "[\"ROLE_MEDECIN\"]";
         }
        try
           {
           String requete = "insert into user (nom,prenom,email,password,roles)values (?,?,?,?,?)";
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt() ));
            pst.setString(5, roleType) ;

            if (pst.executeUpdate() > 0) 
            {
                System.out.println("You have registered successfully.");
                return true;
            } 
            else
            {
                System.out.println("Something went wrong.");
                return false;
            }
           
            }
        catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
                return false;
            }
    }
    
public static User login(String email, String password) 
        {
            User user = null;
            String pass = "";
            String role = null;
        try {
            String requete = "Select email,password from user where email = ?";
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, email);
            ResultSet rs;
            rs = pst.executeQuery();
            if (rs.next())
            {
            pass = rs.getString("password");
            email = rs.getString("email");
            }
            
            if (BCrypt.checkpw(password, pass)) 
            {
                requete = "Select nom,prenom,email,numero from user where email = ?";
                pst = MaConnexion.getInstance().getCnx().prepareStatement(requete);
                pst.setString(1, email);
                rs = pst.executeQuery();
                while (rs.next()) 
                {user = new User( rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("numero") );}                       
            } 
            else 
            {
                System.out.println("password or email invalid");return null;
            }

            } 
        catch (SQLException ex) 
            {
                System.out.println(ex.getMessage());
                return null;
            } 
        return user;
    }

   

public User GetUserByMailSession(String mail) {
            User user = null;
        try {
            String requete = "Select id,nom,prenom,email,image,numero,roles,baned,status from user where email = ?";
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, mail);
            ResultSet rs;
            rs = pst.executeQuery();

               while (rs.next()) 
               {
                   user = new User(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("image"),rs.getString("numero"),rs.getString("roles"),rs.getString("baned"),rs.getInt("status") );               
               }
            } 
        catch (SQLException ex) 
            {
            System.out.println(ex.getMessage());
            return null;
            } 
        return user;
    }
    
public void Update(User u ,String mail ) {
        try {
            String req =  "UPDATE user SET nom = '" + u.getNom() + "', prenom = '"
                + u.getPrenom() + "', numero = '"
                + u.getNumero()+ "' WHERE `email` =  '"+ mail +"'" ;
  
            Statement st = MaConnexion.getInstance().getCnx().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Utilisateur updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
  
public void addImage(User u ,String mail ) {
        try {
            String req =  "UPDATE user SET image = '" + u.getImage() + "' WHERE `email` =  '"+ mail +"'" ;
  
            Statement st = MaConnexion.getInstance().getCnx().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Image updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
       
       
public String sendSMS(String numero) throws SQLException {

        String requete = "SELECT numero FROM user WHERE numero=? ";
        PreparedStatement pst =MaConnexion.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, numero);
        ResultSet rs;
        rs = pst.executeQuery();
        while (rs.next()) {
            numero = rs.getString("numero");
            //password = rs.getString("password");
        }
        return numero;

    }
public void setToken(String y ,String numero ) {
        try {
            String req =  "UPDATE user SET reset_token = '" +y+ "' WHERE `numero` =  '"+ numero +"'" ;
  
            Statement st = MaConnexion.getInstance().getCnx().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("code sent !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
public boolean verifCode(String token) throws SQLException {

        String requete = "SELECT reset_token FROM user WHERE reset_token=? ";
        PreparedStatement pst =MaConnexion.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, token);
        ResultSet rs;
        rs = pst.executeQuery();
       String verif = null;

        while (rs.next()) {
            verif = rs.getString("reset_token");
            //password = rs.getString("password");
        }
        if(verif==null)
        return false;
        else
        return true;

    }
          
public void resetPassword(String pass ,String numero ) {
        try {
            String req =  "UPDATE user SET password = '" +BCrypt.hashpw(pass, BCrypt.gensalt() )+ "' WHERE `numero` =  '"+ numero +"'" ;
  
            Statement st = MaConnexion.getInstance().getCnx().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("password updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
                
public ObservableList<User> afficherMedecin(){
        ObservableList<User> mylist = FXCollections.observableArrayList();
        try {
            
            String req="SELECT * FROM user WHERE roles = '[\"ROLE_MEDECIN\"]' ";
            Statement st =MaConnexion.getInstance().getCnx().prepareStatement(req);
            ResultSet rs= st.executeQuery(req);
            while(rs.next()){
                User u =new User();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(6));
                u.setPrenom(rs.getString(7));
                u.setEmail(rs.getString(3));
                u.setNumero(rs.getString(11));   
                u.setStatus(rs.getInt(15));

                
                mylist.add(u);
                
            }
        } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        return mylist;
        
        
    }
public void Ban(User u) {
    
    try {
        String requete = "UPDATE user SET baned='banned' WHERE id=?";
        PreparedStatement ps = MaConnexion.getInstance().getCnx().prepareStatement(requete);
    
        ps.setInt(1, u.getId());
        ps.executeUpdate();
        System.out.println("L'etat a été changé avec succès !");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    
    
}
public void DeBan(User u) {
    
    try {
        String requete = "UPDATE user SET baned='actif' WHERE id=?";
        PreparedStatement ps = MaConnexion.getInstance().getCnx().prepareStatement(requete);
    
        ps.setInt(1, u.getId());
        ps.executeUpdate();
        System.out.println("L'etat a été changé avec succès !");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    
    
}

public ObservableList<User> afficherPatient(){
        ObservableList<User> mylist = FXCollections.observableArrayList();
        try {
            
            String req="SELECT * FROM user WHERE roles = '[\"ROLE_PATIENT\"]' ";
            Statement st =MaConnexion.getInstance().getCnx().prepareStatement(req);
            ResultSet rs= st.executeQuery(req);
            while(rs.next()){
                User u =new User();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(6));
                u.setPrenom(rs.getString(7));
                u.setEmail(rs.getString(3));
                u.setNumero(rs.getString(11));   
                u.setBaned(rs.getString(17));

                
                mylist.add(u);
                
            }
        } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        return mylist;
        
        
    }
public void Approve(User u) {
    
    try {
        String requete = "UPDATE user SET status='1' WHERE id=?";
        PreparedStatement ps = MaConnexion.getInstance().getCnx().prepareStatement(requete);
    
        ps.setInt(1, u.getId());
        ps.executeUpdate();
        System.out.println("L'etat a été changé avec succès !");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    
    
}

public ObservableList<User> afficher() {
        ObservableList<User> c = FXCollections.observableArrayList();

        try {
            String req = "SELECT id,nom,prenom,email,rates FROM user";
            Statement st =MaConnexion.getInstance().getCnx().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {

           int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                double rates = rs.getDouble("rates");
               

                User r = new User( id,nom, prenom, email,rates);
                c.add(r);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            
        }
        
        return c
                ;

    }

public void modifier(User u) {
        try {

            String req = "UPDATE user SET rates=? where id=?";
            PreparedStatement pst =MaConnexion.getInstance().getCnx().prepareStatement(req);

            pst.setDouble(1, u.getRates());
            
            pst.setInt(2, u.getId());

            pst.executeUpdate();

            System.out.println("note est modifiée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public User getUserById(int id) throws SQLException {
        String req = "SELECT id,nom,prenom,email,rates FROM user WHERE id = ?";
        try (PreparedStatement statement = MaConnexion.getInstance().getCnx().prepareStatement(req);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int Id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                double rates = resultSet.getDouble("rates");
                return new User(Id, nom, prenom, email, rates);
            } else {
                return null;
            }
        }
    }
    
    public String getPatientEmail(int id) {
        String patientEmail = ""; // initialize with an invalid value
        try {
            String req = "SELECT email FROM user WHERE id = " + id;
           PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(req);
           ResultSet rs;
            rs = pst.executeQuery();
            if (rs.next()) {
                // Fetch the patient id from the patient_id column
                patientEmail = rs.getString("email");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return patientEmail;
    }
    public User getPatientInfo(int id) {
        User patient = null; // initialize with an invalid value
        try {
            String req = "SELECT * FROM user WHERE id = " + id;
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(req);
           ResultSet rs;
            rs = pst.executeQuery();

            if (rs.next()) {
                // Fetch the patient id from the patient_id column
                patient = new User();
                patient.setId(rs.getInt("id"));
                patient.setEmail(rs.getString("email"));
                patient.setNom(rs.getString("nom"));
                patient.setPrenom(rs.getString("prenom"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return patient;
    }
    public User getDoctorInfo(int id) {
        User doctor = null; // initialize with an invalid value
        try {
            String req = "SELECT * FROM user WHERE id = " + id;
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(req);
           ResultSet rs;
            rs = pst.executeQuery();

            if (rs.next()) {
                // Fetch the patient id from the patient_id column
                doctor = new User();
                doctor.setId(rs.getInt("id"));
                doctor.setEmail(rs.getString("email"));
                doctor.setNom(rs.getString("nom"));
                doctor.setPrenom(rs.getString("prenom"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return doctor;
    }
     public User findUser(int id) throws SQLException{
      
        String query = "SELECT * FROM user WHERE id = '"+id+ "'";
        PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(query);
           ResultSet rs;
            rs = pst.executeQuery();
        User user = null;
        if (rs.next()) {
            int id1 = rs.getInt("id");
            String name = rs.getString("nom");
            String email = rs.getString("email");
            String lastname = rs.getString("prenom");
            user = new User(id1, name, lastname, email);
        }
       
        return user;
    }
     public ObservableList<User> getPatientsFromUserUserList(ObservableList<user_user> userUserList) {
    ObservableList<User> patients = FXCollections.observableArrayList();
    try {
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement statement = MaConnexion.getInstance().getCnx().prepareStatement(query);
        for (user_user userUser : userUserList) {
            statement.setInt(1, userUser.getUser_target());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User patient = new User();
                patient.setId(rs.getInt("id"));
                patient.setNom(rs.getString("nom"));
                patient.setEmail(rs.getString("email"));
                patient.setPrenom(rs.getString("prenom"));
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
}
