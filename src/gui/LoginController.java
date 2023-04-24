/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Role;
import Entity.User;
import Service.ServiceUser;
import Service.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
public class LoginController implements Initializable
{    
     private Stage stage;
     private Parent root;
    
    @FXML
    private TextField email;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField password;
    @FXML
    private Button register;
    @FXML
    private Label forgetPassword;

    @FXML
    void login(ActionEvent event) throws IOException
    {
        if (event.getSource() == loginBtn) 
        {
            if (validateEmail(email) & validatePassword(password)){
                User user = ServiceUser.login( email.getText(), password.getText());
                ServiceUser.userSession = new UserSession();            
                ServiceUser.userSession.setUserEmail(user.getEmail());
                User u = ServiceUser.userSession.getUser(); 
                String roles = u.getRoles();
                if("[\"ROLE_ADMIN\"]".equals(roles))
                {
                System.out.println(roles);
                Parent root1 = FXMLLoader.load(getClass().getResource("profileAdmin.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root1);
                stage.setScene(scene);
                stage.show();
                }
                if("[\"ROLE_PATIENT\"]".equals(roles))
                {
                
                    if("banned".equals(u.getBaned()))
                    {
                Parent root2 = FXMLLoader.load(getClass().getResource("banned.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root2);
                stage.setScene(scene);
                stage.show();
                
                   } else
                    {   Parent root2 = FXMLLoader.load(getClass().getResource("profileUser.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root2);
                        stage.setScene(scene);
                        stage.show();
                    }
                
                   }
                if("[\"ROLE_MEDECIN\"]".equals(roles))
                {
                 
                    if(1!=u.getStatus())
                    {
                        Parent root2 = FXMLLoader.load(getClass().getResource("approve.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root2);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else
                    {   Parent root2 = FXMLLoader.load(getClass().getResource("profileUser.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root2);
                        stage.setScene(scene);
                        stage.show();
                    }
                } 
                          
                }      
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void register(MouseEvent event) throws IOException 
    {
     Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
     stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }
    
        private boolean validateEmail(TextField email) {

        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher m = p.matcher(email.getText());
        if (((email.getText().length() > 8))
                && (m.find() && m.group().equals(email.getText()))) {
            email.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(email).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            email.setEffect(in);
            return false;
        }

    }
      private boolean validatePassword(TextField password) {

        Pattern p = Pattern.compile("[a-zA-Z_0-9]+");
        Matcher m = p.matcher(password.getText());
        if (((password.getText().length() > 6))
                && (m.find() && m.group().equals(password.getText()))) {
            password.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(password).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            password.setEffect(in);
            return false;
        }

    }   

    @FXML
    private void forgetPassword(MouseEvent event) throws IOException 
    {
         Parent root = FXMLLoader.load(getClass().getResource("forgotPassword.fxml"));
     stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }


   
}


