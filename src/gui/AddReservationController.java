/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Reservation;
import Entity.User;
import Service.ServiceReservation;
import Service.ServiceUser;
import Service.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.Date; 
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */
public class AddReservationController implements Initializable {

    
    @FXML
    private Button cancelButton;
    @FXML
    private Button addRateButton;
    
     @FXML
    private Button check;
 
    @FXML
private ComboBox<User> userComboBox;

    
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextArea commentArea;
    
    private DisplayReservationController homeController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ObservableList<User> userList = FXCollections.observableArrayList();
ServiceUser userService = new ServiceUser();
ObservableList<User> users = userService.afficher(); 
userList.addAll(users);
userComboBox.setItems(userList);


   
    }    
 public void setHomeController(DisplayReservationController homeController)
    {
        this.homeController=homeController;
    }
    
    
    @FXML
    private void handleAddReservation() throws IOException,SQLException {
        
        
     
        
            // Get the values from the text fields
    User selectedUser = userComboBox.getSelectionModel().getSelectedItem();
int userid = selectedUser.getId();



UserSession userSession = new UserSession();
User currentUser = userSession.getUser();
   
    int patientid = currentUser.getId();

    LocalDate localStartDate = startDatePicker.getValue();
    Date start = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlStartDate = new java.sql.Date(start.getTime());
    LocalDate localEndDate = endDatePicker.getValue();
    Date end = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
     java.sql.Date sqlEndDate = new java.sql.Date(end.getTime());
        
    String comment = commentArea.getText();
    
    
     if (sqlStartDate.after(sqlEndDate)) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("Invalid dates");
    alert.setContentText("The start date must be before the end date.");
    alert.showAndWait();
    return;
}
     else {
    // Insert the rate data into the database using the existing connection
    Reservation reservation=new Reservation(userid,patientid,sqlStartDate,sqlEndDate,comment);
    ServiceReservation reservationService = new ServiceReservation();
    reservationService.ajouter(reservation);
    
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Reservation added ");
        alert.setContentText("The Reservation has been added succesfully.");
        alert.showAndWait();
        homeController.refresh();
        cancellres();
     }
    }
    
    @FXML
    private void cancellres() throws IOException {
         
         // Load the FXML file of the UI you want to display
    Parent root = FXMLLoader.load(getClass().getResource("displayReservation.fxml"));

    // Create a new scene with the loaded FXML file as its root node
    Scene scene = new Scene(root);

    // Get the current stage (window) from the button's scene
    Stage stage = (Stage) cancelButton.getScene().getWindow();

    // Set the new scene on the stage
    stage.setScene(scene);

    // Show the stage
    stage.show();
    }

    
      @FXML
    private void check() throws IOException {
         
         // Load the FXML file of the UI you want to display
    Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));

    // Create a new scene with the loaded FXML file as its root node
    Scene scene = new Scene(root);

    // Get the current stage (window) from the button's scene
    Stage stage = (Stage) cancelButton.getScene().getWindow();

    // Set the new scene on the stage
    stage.setScene(scene);

    // Show the stage
    stage.show();
    }
    
    
}
