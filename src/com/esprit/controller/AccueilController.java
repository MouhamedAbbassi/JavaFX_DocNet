/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.UserService;
import com.esprit.entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wiemhjiri
 */
public class AccueilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private AnchorPane rootLayout;
    private Stage primaryStage;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_display;
    UserService service = new UserService();
    @FXML
    private Button btnReservation;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn_add.setOnAction(event -> {
            String username = "nassim.benali@esprit.tn";
            String password = "";
            try {
            User user = service.findUserByUsernameAndPassword(username, password);
            if (user != null) {
                UserService.setCurrentUser(user);
                int currentUserId = service.getCurrentUserId();
                System.out.println("ID de l'utilisateur connecté: " + currentUserId);
            } else {
                System.out.println("Nom d'utilisateur ou mot de passe incorrect");
            }
            } catch (SQLException e) {
            e.printStackTrace();
            }
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/com/esprit/view/AfficherOrdonnancePatient.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn_display.setOnAction(event -> {
            String username = "benalinassim412@gmail.com";
            String password = "";
            try {
            User user = service.findUserByUsernameAndPassword(username, password);
            if (user != null) {
                UserService.setCurrentUser(user);
                int currentUserId = service.getCurrentUserId();
                System.out.println("ID de l'utilisateur connecté: " + currentUserId);
            } else {
                System.out.println("Nom d'utilisateur ou mot de passe incorrect");
            }
            } catch (SQLException e) {
            e.printStackTrace();
            }
            try {//FXMLLoader loader = new FXMLLoader();
                //loader.setLocation(getClass().getResource("/com/esprit/view/Accueil.fxml"));
                Parent page2 = FXMLLoader.load(getClass().getResource("/com/esprit/view/AfficherPersonne.fxml"));
                // Give the controller access to the main app.
//                AfficherPersonneController controller =loader.getController();
//                controller.setListData(new ListData());
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void reservationButton(ActionEvent event) {
         String username = "benali@gmail.com";
            String password = "";
            try {
            User user = service.findUserByUsernameAndPassword(username, password);
            if (user != null) {
                UserService.setCurrentUser(user);
                int currentUserId = service.getCurrentUserId();
                System.out.println("ID de l'utilisateur connecté: " + currentUserId);
            } else {
                System.out.println("Nom d'utilisateur ou mot de passe incorrect");
            }
            } catch (SQLException e) {
            e.printStackTrace();
            }
            try {//FXMLLoader loader = new FXMLLoader();
                //loader.setLocation(getClass().getResource("/com/esprit/view/Accueil.fxml"));
                Parent page2 = FXMLLoader.load(getClass().getResource("/com/esprit/view/AjouterReservation.fxml"));
                // Give the controller access to the main app.
//                AfficherPersonneController controller =loader.getController();
//                controller.setListData(new ListData());
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

}
