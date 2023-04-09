 
package gui;

import Entity.Role;
import Entity.User;
import Service.ServiceUser;
 
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
 import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

 import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;

import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * FXML Controller class
 *
 * @author muham
 */
public class RegisterController implements Initializable
{
    private String[] roles = {
            "patient",
            "medecin",        
    };
    ServiceUser userService = new ServiceUser();
    private Stage stage;
    private Parent root;
    
     @FXML
    private TextField email;

    @FXML
    private TextField nom;

    @FXML
    private PasswordField password;

    @FXML
    private TextField prenom;

    @FXML
    private Button registerBtn;

    @FXML
    private ChoiceBox<String> role;
    @FXML
    private Button login;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){role.getItems().addAll(roles);}  
     @FXML
     
    private void register(MouseEvent event) throws IOException, Exception 
    {
             Role rolee = Role.valueOf(role.getValue());  
             Window owner = registerBtn.getScene().getWindow();                
             User user = new User(nom.getText(),prenom.getText(),
                                  email.getText(), password.getText(),
                                  rolee);           
                userService.register(user);
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();     
    }

    @FXML
    private void login(MouseEvent event) throws IOException
    {
       Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
    }
    
}
