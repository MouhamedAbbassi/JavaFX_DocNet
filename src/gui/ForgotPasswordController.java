/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.User;
import Service.ServiceUser;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author muham
 */
public class ForgotPasswordController implements Initializable {
    
     private Stage stage;
    private Scene scene;
    private Parent root;
    public static final String ACCOUNT_SID = "ACe0d39e374a4b33c50217f1c834d14a13";
    public static final String AUTH_TOKEN = "9af472b97f13a4c72892cb7726de82d1";
 ServiceUser serviceUser = new ServiceUser();
    public String y, z;
    public String username, pass, mesg;
    @FXML
    private TextField numero;
    @FXML
    private Button sendSms;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    

        protected String getToken() 
        {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    @FXML
    private void sendSms(MouseEvent event) throws SQLException, IOException 
    {
                ServiceUser u = new ServiceUser();

        serviceUser.sendSMS(numero.getText());
            y = getToken();
            z = numero.getText();
           // SharedData.data = z ;
            try {
                String user = serviceUser.sendSMS(numero.getText());   
              

            } catch (SQLException ex) 
            {
                System.out.println(ex);
            }
            mesg = "votre code est : " + y;

           Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            com.twilio.rest.api.v2010.account.Message messages = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber(serviceUser.sendSMS(numero.getText()) ),
                    new PhoneNumber("+15076093506"), y).create();
          //  User userSetToken = new User(y, true);           
                u.setToken(y,numero.getText());
              // System.out.println(y);
       root = FXMLLoader.load(getClass().getResource("verificationCode.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
            
    }
    
}
