 
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

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
        if (validateString(nom) & validateString(prenom) & validateEmail(email)
                & validatePassword(password)) {
     
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
    
    
        private boolean validateString(TextField string) {
        Pattern p = Pattern.compile("[a-zA-Z0-9_]+");
        Matcher m = p.matcher(string.getText());
        if ((string.getText().length() == 0)
                || (!m.find() && m.group().equals(string.getText()))) {
            new animatefx.animation.Shake(string).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            string.setEffect(in);
            return false;
        } else {
            string.setEffect(null);
            return true;
        }
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
    
}
