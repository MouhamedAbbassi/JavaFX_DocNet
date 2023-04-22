/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.OrdonnanceService;
import Services.UserService;
import com.esprit.entity.Ordonnance;
import com.esprit.entity.Personne;
import com.esprit.utils.ConnexionSingleton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

/**
 *
 * @author asus
 */
public class AfficheOrdonnanceController implements Initializable {
    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Ordonnance> tftableview;
    @FXML
    private TableColumn<?, ?> nomMedecin;
    @FXML
    private TableColumn<?, ?> nomPatient;
    @FXML
    private TableColumn<?, ?> commentaire;
    @FXML
    private TableColumn<Ordonnance, Object> update;
    @FXML
    private TableColumn<Ordonnance, Object> delete;
    @FXML
    private TableColumn<?, ?> date;

    public static final String ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
    
    UserService userService = new UserService();
    int currentUserId = userService.getCurrentUserId();
    
    OrdonnanceService service = new OrdonnanceService();
    ObservableList<Ordonnance> list = service.getall(currentUserId);
    
    @FXML
    private Button patient;
    @FXML
    private Button reservation;
    @FXML
    private Button logOut;
    @FXML
    private Button recherche;

    @FXML
    private TextField commentaireSearchField;

    private FilteredList<Ordonnance> filteredList;
    @FXML
    private DatePicker dateSearchField;
    @FXML
    private Button ResetFields;
    @FXML
    private ComboBox<String> triCombobox;

    /**
     * Initializes the controller class.
     */

    public void initialize(URL url, ResourceBundle rb) {
        filteredList = new FilteredList<>(list, p -> true);
        triCombobox.setItems(FXCollections.observableArrayList("Date","Commentaire","Reset"));
        nomMedecin.setCellValueFactory(new PropertyValueFactory<>("nomMedecin"));
        nomPatient.setCellValueFactory(new PropertyValueFactory<>("nomPatient"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        commentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        update.setCellFactory(param -> new TableCell<Ordonnance, Object>() {
            private final Button button = new Button("Update");

            {
                button.setOnAction(event -> {
                    Ordonnance ordonnance = getTableView().getItems().get(getIndex());
                    // Perform the desired action on the selected row
                    int id = ordonnance.getId();
                    int patID = service.getPatientId(id);
                    String patEmail = userService.getPatientEmail(patID);
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/updateOrdonnance.fxml"));
                        Parent root = loader.load();
                        updateOrdonnanceController formController = loader.getController();
                        formController.setOrdonnance(ordonnance);
                        formController.initialize(ordonnance, patEmail);
                        Scene newScene = new Scene(root);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setScene(newScene);
                        primaryStage.show();
                    } catch (IOException e) {
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
        delete.setCellFactory(param -> new TableCell<Ordonnance, Object>() {
            private final Button button = new Button("Delete");

            {
                button.setOnAction(event -> {
                    Ordonnance ordonnance = getTableView().getItems().get(getIndex());
                    // Perform the desired action on the selected row
                    int id = ordonnance.getId();

                    service.delete(id);
                    Image img = new Image("/uploads/tick1.png");
                    Notifications.create()
                            .title("Ordonnance")
                            .text("Ordonnance deleted successfully")
                            .graphic(new ImageView(img))
                            .darkStyle()
                            .show();

                    try {
                        Parent page1 = FXMLLoader.load(getClass().getResource("/com/esprit/view/AfficherPersonne.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
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
        recherche.setOnAction(this::searchButton);
        triCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tftableview.getSortOrder().clear(); // clear any existing sorting
                if (newValue.equals("Date")) {
                    TableColumn<Ordonnance, LocalDate> dateColumn = (TableColumn<Ordonnance, LocalDate>) date;
                    tftableview.getSortOrder().add(dateColumn); // sort by date column
                    dateColumn.setSortType(TableColumn.SortType.ASCENDING); // set sorting order to ascending
                } else if (newValue.equals("Commentaire")) {
                    TableColumn<Ordonnance, String> commentaireColumn = (TableColumn<Ordonnance, String>) commentaire;
                    tftableview.getSortOrder().add(commentaireColumn); // sort by commentaire column
                    commentaireColumn.setSortType(TableColumn.SortType.ASCENDING); // set sorting order to ascending
                }
                tftableview.sort(); // sort the table view
            } else { // reset button selected
                tftableview.getSortOrder().clear(); // clear any existing sorting
            }
        });
        tftableview.setItems(list);
    } 
    
    @FXML
    private void reservationButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/afficherReservation.fxml"));
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
    private void patientButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/listePatient.fxml"));
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
    private void logoutButton(ActionEvent event) {
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
    private void searchButton(ActionEvent event) {
        String query = commentaireSearchField.getText().toLowerCase();
        String query1 = dateSearchField.getValue() != null ? dateSearchField.getValue().toString() : "";

        filteredList.setPredicate(ord -> {
            LocalDate ordDate = LocalDate.parse(ord.getDate());
            LocalDate queryDate = query1.isEmpty() ? null : LocalDate.parse(query1);

            return (query.isEmpty() || ord.getCommentaire().toLowerCase().contains(query))
                    && (queryDate == null || ordDate.equals(queryDate));
        });

        tftableview.setItems(filteredList);
    }

    @FXML
    private void ResetFields(ActionEvent event) {
        commentaireSearchField.setText("");
        dateSearchField.setValue(null);
    }
    
    
}
