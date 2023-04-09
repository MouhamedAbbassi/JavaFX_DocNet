package Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entity.User;
import Config.MaConnexion;
import Entity.Role;
import java.sql.ResultSet;
import java.sql.Statement;
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
            String pass = "";
            String role = null;
        try {
            String requete = "Select nom,prenom,email,image,numero from user where email = ?";
            PreparedStatement pst = MaConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, mail);
            ResultSet rs;
            rs = pst.executeQuery();

            requete = "SELECT nom,prenom,email,image,numero FROM user WHERE email like ?";
            pst = MaConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, mail);
            rs = pst.executeQuery();
               while (rs.next()) 
               {
                   user = new User(rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("image"),rs.getString("numero") );               
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

}
