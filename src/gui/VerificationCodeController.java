/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author muham
 */
public class VerificationCodeController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private Button verifCode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void verifCode(MouseEvent event) throws SQLException, IOException
    {
           ServiceUser u = new ServiceUser();

       if(validateString(code)){
           boolean a=u.verifCode(code.getText());
           System.out.println(a);
           if(a==true)
           {
               Parent   root = FXMLLoader.load(getClass().getResource("resetPassword.fxml"));
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               Scene scene = new Scene(root);
               stage.setScene(scene);
               stage.show();
           }else{
               new animatefx.animation.Shake(code).play();
               InnerShadow in = new InnerShadow();
               in.setColor(Color.web("#f80000"));
               code.setEffect(in);}
       }
    }
    
    
private boolean validateString(TextField string) {      
        if ((string.getText().length() == 0)) {
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
    
}
