/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.OrdonnanceService;
import Service.ServiceReservation;
import Service.ServiceUser;
import Entity.Reservation;
import Entity.User;
import Service.UserSession;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;
/**
 *
 * @author asus
 */
public class AfficherReservationController implements Initializable{
    /**
     * Initializes the controller class.
     */
     @FXML
    private TableView<Reservation> tftableview;

   @FXML
    private TableColumn<Reservation, Object> update;
    @FXML
        private TableColumn<Reservation, String> nomPatient;
    @FXML
    private TableColumn<Reservation, String> emailPatient;
    @FXML
    private TableColumn<Reservation, String> prenomPatient;
  
    
    public static final String ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
    ServiceReservation service = new ServiceReservation();
    ServiceUser userService = new ServiceUser();
    UserSession userSession = new UserSession();
    ObservableList<Reservation> list = service.getall(userSession.getUser().getId());
    ObservableList<Integer> idList = FXCollections.observableArrayList();
    ObservableList<String> listString = FXCollections.observableArrayList();
    @FXML
    private Button ret;
    


    /**
     * Initializes the controller class.
     */
    
    public void initialize(URL url, ResourceBundle rb) {
    
nomPatient.setCellValueFactory(cellData -> {
        StringBuilder sb = new StringBuilder();
        for (User medicament : cellData.getValue().getPatient()) {
            sb.append(medicament.getNom());
        }
        return new SimpleStringProperty(sb.toString());
        });
prenomPatient.setCellValueFactory(cellData -> {
        StringBuilder sb = new StringBuilder();
        for (User medicament : cellData.getValue().getPatient()) {
            sb.append(medicament.getPrenom());
        }
        return new SimpleStringProperty(sb.toString());
        });
emailPatient.setCellValueFactory(cellData -> {
        StringBuilder sb = new StringBuilder();
        for (User medicament : cellData.getValue().getPatient()) {
            sb.append(medicament.getEmail());
        }
        return new SimpleStringProperty(sb.toString());
        });

    
        update.setCellFactory(param -> new TableCell<Reservation, Object>() {
        private final Button button = new Button("Ordonnance");
                {
                    button.setOnAction(event -> {
                        Reservation res = getTableView().getItems().get(getIndex());
                        // Perform the desired action on the selected row
                        int id = res.getId();
                        int patientId = service.getIdPatient(id);
                        try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterOrdonnance.fxml"));
                        Parent root = loader.load();
                        AjouterOrdonnanceController formController = loader.getController();
                        formController.initialize(id,patientId);
                        Scene newScene = new Scene(root);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setScene(newScene);
                        primaryStage.show();
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
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
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherOrdonnance.fxml"));
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
