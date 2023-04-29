/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Rate;
import Service.ServiceNote;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */

public class UpdateRateController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button cancelUpdateButton;

  

    @FXML
    private TextField noteTextField1;

    @FXML
    private TextField opinionTextField1;

    @FXML
    private Button updateRateButton;
    private Rate rate;
    private HomeController homeController;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setRate(Rate rate)
    {
        this.rate=rate;
        
        noteTextField1.setText(String.valueOf(rate.getnote()));
        opinionTextField1.setText(rate.getOpinion());
    }
    public void setHomeController(HomeController homeController)
    {
        this.homeController=homeController;
    }
    @FXML
    private void updateRate() throws SQLException, IOException {
      
        double note=Double.parseDouble(noteTextField1.getText());
        String opinion = opinionTextField1.getText();
        ServiceNote serviceNote = new ServiceNote();
        Rate ratee = serviceNote.getRateById(rate.getId());
        ratee = new Rate(rate.getId(),rate.getmed_id(),rate.getmake_rate_id(),note,opinion);
        
        ratee.setnote(note);
        ratee.setOpinion(opinion);
        serviceNote.modifier(ratee);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("rate modified");
        alert.setContentText("The rate has been modified in the database.");
        alert.showAndWait();
        homeController.refresh();
        cancel();
    }
    
      @FXML
    void cancel() throws IOException {
    // Load the FXML file of the UI you want to display
    Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));

    // Create a new scene with the loaded FXML file as its root node
    Scene scene = new Scene(root);

    // Get the current stage (window) from the button's scene
    Stage stage = (Stage) cancelUpdateButton.getScene().getWindow();

    // Set the new scene on the stage
    stage.setScene(scene);

    // Show the stage
    stage.show();
}
    
    
}
