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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    void login(ActionEvent event) throws IOException
    {
        if (event.getSource() == loginBtn) 
        {
                User user = ServiceUser.login( email.getText(), password.getText());
                ServiceUser.userSession = new UserSession();            
                ServiceUser.userSession.setUserEmail(user.getEmail());
                Role roles = user.getRole();             
                Parent root = FXMLLoader.load(getClass().getResource("profileUser.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
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
   
}


