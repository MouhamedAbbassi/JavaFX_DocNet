/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.FicheService;
import Services.ReservationService;
import Services.UserService;
import com.esprit.entity.Ordonnance;
import com.esprit.entity.User;
import com.esprit.entity.reservation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AjouterReservationController implements Initializable {

    @FXML
    private DatePicker datefield;
    @FXML
    private Button reserver;
    @FXML
    private Button annuler;
    
    ReservationService service = new ReservationService();
    FicheService serviceFiche = new FicheService();
    UserService userService = new UserService();
    int currentUserId = userService.getCurrentUserId();
    @FXML
    private ComboBox<String> medecin;
    List<User> medecins = userService.getuserbyRole("[\"ROLE_MEDECIN\"]");
    List<String> medecinNames = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        for (User medecin : medecins) {
    medecinNames.add(medecin.getPrenom()+ " " + medecin.getNom());
}

// Set the string list as the data source for the ComboBox
medecin.setItems(FXCollections.observableArrayList(medecinNames));
    }    

    @FXML
    private void reserverButton(ActionEvent event) {
        String selectedMedecinName = medecin.getSelectionModel().getSelectedItem();
    
    // Find the corresponding User object from the list medecins
    User selectedMedecin = null;
    for (User medecin : medecins) {
        if ((medecin.getPrenom() + " " + medecin.getNom()).equals(selectedMedecinName)) {
            selectedMedecin = medecin;
            break;
        }
    }
    
    if (selectedMedecin != null) {
        int selectedMedecinId = selectedMedecin.getId();
        
        // Create the reservation object with the selected date and medecin id
        reservation o = new reservation(datefield.getValue().toString());
        service.Inserer(o, selectedMedecinId, currentUserId);
        serviceFiche.InsererFiche(selectedMedecinId, currentUserId);
        userService.InsererUserUser(selectedMedecinId, currentUserId);
    }
       try {
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/Accueil.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void annulerButton(ActionEvent event) {
        try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/Accueil.fxml"));
                        Parent root = loader.load();
                        Scene newScene = new Scene(root);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setScene(newScene);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    }
    
}
