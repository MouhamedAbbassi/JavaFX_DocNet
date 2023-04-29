/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.User;
import Service.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */

public class UserController implements Initializable {

    @FXML
    private TableView<Image> stars;
    @FXML
    private TableColumn<Image,Image> s;
@FXML
    private TableView<User> rateTable;

 //@FXML
   // private TableColumn<User, Integer> id;
   @FXML
    private TableColumn<User, String> idc;
   @FXML
    private TableColumn<User, String> d;
     @FXML
    private TableColumn<User, String> c;
       @FXML
    private TableColumn<User, String> m;
        @FXML
    private Button cancelButton;

  @FXML
    ObservableList<User> list = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
  private ServiceUser userService;

    private ObservableList<User> List;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    this.userService = new ServiceUser();
    this.List = FXCollections.observableArrayList();
 
        
      // Bind the table columns to the data model properties
   // id.setCellValueFactory(new PropertyValueFactory<>("id"));

    idc.setCellValueFactory(new PropertyValueFactory<>("nom"));
    d.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    c.setCellValueFactory(new PropertyValueFactory<>("email"));
    m.setCellValueFactory(new PropertyValueFactory<>("rates"));

        
        // Load the reservations from the database and add them to the table
        this.List.addAll(this.userService.afficher());
        this.rateTable.setItems(this.List);
 
    }    
    
     @FXML
    private void cancellres() throws IOException {
         
         // Load the FXML file of the UI you want to display
    Parent root = FXMLLoader.load(getClass().getResource("addReservation.fxml"));

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
