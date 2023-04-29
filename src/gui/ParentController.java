/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */
public class ParentController implements Initializable {

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    
@FXML
public void handleReservationAction(ActionEvent event) {
    try {
        // Get the Stage object from the ActionEvent
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        // Close the stage
        stage.close();

        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("displayReservation.fxml"));
        Parent root = loader.load();

        // Create a new stage and display the new scene
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    @FXML
public void handleRateAction(ActionEvent event) {
   try {
        // Get the Stage object from the ActionEvent
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        // Close the stage
        stage.close();

        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();

        // Create a new stage and display the new scene
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    
}
