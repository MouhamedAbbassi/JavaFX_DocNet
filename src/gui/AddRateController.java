package gui;

import Entity.Rate;
import Service.ServiceNote;
import Entity.User;
import Service.ServiceUser;

import Config.MaConnexion;
import Service.UserSession;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddRateController implements Initializable {
    
 
    @FXML
private ComboBox<User> userComboBox;
   
    
    @FXML
    private TextField noteTextField;
    
    @FXML
    private TextField opinionTextField;
    
    @FXML
    private Button addRateButton;
    
    @FXML
    private Button cancelButton;
    private HomeController homeController;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
         ObservableList<User> userList = FXCollections.observableArrayList();
ServiceUser userService = new ServiceUser();
ObservableList<User> users = userService.afficherMedecin();
userList.addAll(users);
userComboBox.setItems(userList);
        
    }
    
     public void setHomeController(HomeController homeController)
    {
        this.homeController=homeController;
    }
    
    @FXML
private void handleAddRate() throws IOException,SQLException {
    // Get the values from the text fields
     User selectedUser = userComboBox.getSelectionModel().getSelectedItem();
int medId = selectedUser.getId();

UserSession userSession = new UserSession();
User currentUser = userSession.getUser();
   
    int makeRateId = currentUser.getId();

double note = Double.parseDouble(noteTextField.getText());
    String opinion = opinionTextField.getText();
    
    // Insert the rate data into the database using the existing connection
    Rate rate=new Rate(medId,makeRateId,note,opinion);
    ServiceNote rateService = new ServiceNote();
    rateService.ajouter(rate);
    
    
     //change rates for doctor
        /* ServiceUser serviceu = new ServiceUser();
        User u = serviceu.getUserById(rate.getmed_id());
        u = new User(u.getId(),u.getNom(),u.getPrenom(),u.getEmail(),u.getRates());

        u.setRates(rate.getnote());
       
        serviceu.modifier(u);*/
        
        
        
        
        // Get all the users from the database
ServiceUser serviceu = new ServiceUser();
List<User> users = serviceu.afficherMedecin();

// Loop through each user and calculate their average rate
for (User user : users) {
    // Retrieve all the rates for the current user from the database
    ObservableList<Rate> rates = rateService.getRatesByMedId(user.getId());

    // Calculate the average rate for the current user
    double total = 0;
    for (Rate ratee : rates) {
        total += ratee.getnote();
    }
    double average = total / rates.size();

    // Update the user's average rate in the database
    user.setRates(average);
    serviceu.modifier(user);
}

        
        
        
        
        
        
        
        
        
        
        
    
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Rate added ");
        alert.setContentText("The rate has been added succesfully.");
        alert.showAndWait();
        homeController.refresh();
        cancell();
        
        
       
        
        
      // updateUser(rate.getmed_id());
    
    
   
}


    


    

    @FXML
    void cancell() throws IOException {
    // Load the FXML file of the UI you want to display
    Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));

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
