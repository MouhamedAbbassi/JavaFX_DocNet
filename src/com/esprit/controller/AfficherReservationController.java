/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.OrdonnanceService;
import Services.ReservationService;
import Services.UserService;
import com.esprit.entity.reservation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author asus
 */
public class AfficherReservationController implements Initializable{
    /**
     * Initializes the controller class.
     */
     @FXML
    private TableView<reservation> tftableview;
    @FXML
    private TableColumn<?, ?> date;
   @FXML
    private TableColumn<reservation, Object> update;
    
  
    
     public static final String ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
     public static final String AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
      ReservationService service = new ReservationService();
      UserService userService = new UserService();
    int currentUserId = userService.getCurrentUserId();
ObservableList<reservation> list = service.getall(currentUserId);
ObservableList<Integer> idList = FXCollections.observableArrayList();
    @FXML
    private Button ret;


    /**
     * Initializes the controller class.
     */
    
     public void initialize(URL url, ResourceBundle rb) {
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        update.setCellFactory(param -> new TableCell<reservation, Object>() {
        private final Button button = new Button("Ordonnance");
                {
                    button.setOnAction(event -> {
                        reservation res = getTableView().getItems().get(getIndex());
                        // Perform the desired action on the selected row
                        int id = res.getId();
                        int patientId = service.getIdPatient(id);
                        try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/AjouterOrdonnance.fxml"));
                        Parent root = loader.load();
                        AjouterOrdonnanceController formController = loader.getController();
                        
                        formController.initialize(id,patientId);
                        Scene newScene = new Scene(root);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setScene(newScene);
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }   catch (SQLException ex) {
                            Logger.getLogger(AfficherReservationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    

                        
                    });
                }
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        setGraphic(button);
                    } else {
                        setGraphic(null);
                    }
                }
            });
          
          tftableview.setItems(list);
    }    

    @FXML
    private void returnButton(ActionEvent event) {
        try {
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/AfficherPersonne.fxml"));
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
