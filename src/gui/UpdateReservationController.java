/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import Entity.Reservation;
import Service.ServiceReservation;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */
public class UpdateReservationController implements Initializable {

   
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextArea commentArea;
    @FXML
    private Button cancelUpButton;
    @FXML
    private Button updateResButton;
private Reservation res;
    private DisplayReservationController homeController;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    public void setReservation(Reservation res) 
    {
        this.res=res;
        
        startDatePicker.setValue(res.getstart().toLocalDate());
        endDatePicker.setValue(res.getend().toLocalDate());
        commentArea.setText(res.getComment());
    }
     public void setHomeController(DisplayReservationController homeController)
    {
        this.homeController=homeController;
    }
    @FXML
    private void updateRes()throws SQLException, IOException {
        
    
        
        LocalDate localStartDate = startDatePicker.getValue();
        java.util.Date start = java.util.Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlStartDate = new java.sql.Date(start.getTime());
         
        LocalDate localEndDate = endDatePicker.getValue();
        java.util.Date end = java.util.Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlEndDate = new java.sql.Date(end.getTime());
         
        String comment = commentArea.getText();
        
   
        ServiceReservation servicerese = new ServiceReservation();
        Reservation reees = servicerese.getReservationById(res.getId());
        reees = new Reservation(res.getId(),res.getusers_id(),res.getpatient_id(),sqlStartDate,sqlEndDate,comment);
       
        reees.setstart(sqlStartDate);
        reees.setend(sqlEndDate);
        reees.setComment(comment);
             if (sqlStartDate.after(sqlEndDate)) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Invalid dates");
    alert.setContentText("The start date must be before the end date.");
    alert.showAndWait();
    return;
}
             else{
        servicerese.modifier(reees);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Reservation modified");
        alert.setContentText("The Reservation has been modified in the database.");
        alert.showAndWait();
        homeController.refresh();
        cancelll();}
    }
    
    
    @FXML
    private void cancelll() throws IOException {
        
         // Load the FXML file of the UI you want to display
    Parent root = FXMLLoader.load(getClass().getResource("displayReservation.fxml"));

    // Create a new scene with the loaded FXML file as its root node
    Scene scene = new Scene(root);

    // Get the current stage (window) from the button's scene
    Stage stage = (Stage) cancelUpButton.getScene().getWindow();

    // Set the new scene on the stage
    stage.setScene(scene);

    // Show the stage
    stage.show();
    }


    
}
