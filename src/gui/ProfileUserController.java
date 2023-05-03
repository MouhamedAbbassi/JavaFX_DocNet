/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.ServiceUser;
import Service.UserSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author muham
 */
public class ProfileUserController implements Initializable {

    @FXML
    private Circle imageView;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label email;
    @FXML
    private Label numero;
     @FXML
    private Button rate;
      @FXML
    private Button reservation;
 
    @FXML
    private Label logout;
    @FXML
    private Button ordonnance;
    @FXML
    private Button update;
    @FXML
    private Label numero1;
    @FXML
    private Label email1;
    @FXML
    private Label prenom1;
 
 
    
           public List<File> findAllFilesInFolder(File folder) {
        List<File> list = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                list.add(file);

            } else {
                findAllFilesInFolder(file);
            }
        }
        return list;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
     UserSession userSession = new UserSession();
          ServiceUser userService = new ServiceUser();

        File folder = new File("C:\\xampp\\htdocs\\uploads\\images");
        Circle cir2 = new Circle(250, 250, 100);
        for (int i = 0; i < findAllFilesInFolder(folder).size(); i++) {
            if (findAllFilesInFolder(folder).get(i).getName().equals(userSession.getUser().getImage())) {
                try {
                    Image imge = new Image(new FileInputStream("C:\\xampp\\htdocs\\uploads\\images\\" + userSession.getUser().getImage()));
                    imageView.setFill(new ImagePattern(imge));
                    cir2.setFill(new ImagePattern(imge));
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        nom.setText(userSession.getUser().getNom());
        prenom.setText(userSession.getUser().getPrenom());
        email.setText(userSession.getUser().getEmail()); 
        numero.setText(userSession.getUser().getNumero()); 
 

    }    

    @FXML
    private void GererProfile(MouseEvent event) throws IOException
    {
             Parent root = FXMLLoader.load(getClass().getResource("gererProfile.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void logout(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }
    
    
    
     @FXML
    void rate() throws IOException {
        // Load the FXML file of the UI you want to display
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));

        // Create a new scene with the loaded FXML file as its root node
        Scene scene = new Scene(root);

        // Get the current stage (window) from the button's scene
        Stage stage = (Stage) rate.getScene().getWindow();

        // Set the new scene on the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
     
    }
    
    
    @FXML
    void reservation() throws IOException {
        // Load the FXML file of the UI you want to display
        Parent root = FXMLLoader.load(getClass().getResource("displayReservation.fxml"));

        // Create a new scene with the loaded FXML file as its root node
        Scene scene = new Scene(root);

        // Get the current stage (window) from the button's scene
        Stage stage = (Stage) rate.getScene().getWindow();

        // Set the new scene on the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
     
    }

    @FXML
    private void ordonnance(MouseEvent event) throws IOException {
        UserSession userSession = new UserSession();
        if("[\"ROLE_MEDECIN\"]".equals(userSession.getUser().getRoles()))
                {
        Parent root = FXMLLoader.load(getClass().getResource("AfficherOrdonnance.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
                }else if("[\"ROLE_PATIENT\"]".equals(userSession.getUser().getRoles())){
                    Parent root = FXMLLoader.load(getClass().getResource("AfficherOrdonnancePatient.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
                }
    }
    
}
