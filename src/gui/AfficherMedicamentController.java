/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.ServiceMedicament;
import Entity.Medicament;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.Comparator;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class AfficherMedicamentController implements Initializable {

    Connection cnx;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Medicament> medicamentsTable;
    @FXML
    private TableColumn<?, ?> nomCol;
    @FXML
    private TableColumn<?, ?> prixCol;
    @FXML
    private TableColumn<?, ?> typeCol;
    @FXML
    private TableColumn<?, ?> nbDoseCol;
    @FXML
    private TableColumn<?, ?> stockCol;
    @FXML
    private TableColumn<?, ?> imageCol;
    @FXML
    private TableColumn<Medicament, Object> update;
    @FXML
    private TableColumn<Medicament, Object> delete;
    private ServiceMedicament serviceMedicament;
    ServiceMedicament service = new ServiceMedicament();
    ObservableList<Medicament> list = service.getAll();
    @FXML
    private Pagination pagination;

    private FilteredList<Medicament> filteredData;
    Comparator<Medicament> byName = Comparator.comparing(Medicament::getNom);

   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        filteredData = new FilteredList<>(list, p -> true);
        SortedList<Medicament> sortedData = new SortedList<>(filteredData);
        sortedData.setComparator(byName);

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nbDoseCol.setCellValueFactory(new PropertyValueFactory<>("nb_dose"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        //medicamentsTable.setItems(list);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(medicament -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return medicament.getNom().toLowerCase().contains(lowerCaseFilter);
            });
           // medicamentsTable.setItems(filteredData);
            medicamentsTable.setItems(sortedData);

          });
        update.setCellFactory(param -> new TableCell<Medicament, Object>() {
            private final Button button = new Button("Update");

            {
                button.setOnAction(event -> {
                    Medicament ordonnance = getTableView().getItems().get(getIndex());
                    // Perform the desired action on th-e selected row
                    int id = ordonnance.getId();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gui/UpdateMedicament.fxml"));
                        Parent root = loader.load();
                        /*UpdateMedicamentController formController = loader.getController();
                        formController.setMedicament(ordonnance);*/
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
        delete.setCellFactory(param -> new TableCell<Medicament, Object>() {
            private final Button button = new Button("Delete");

            {
                button.setOnAction(event -> {
                    Medicament ordonnance = getTableView().getItems().get(getIndex());
                    // Perform the desired action on the selected row
                    int id = ordonnance.getId();

                    service.deletem(id);
                    try {
                        Parent page1 = FXMLLoader.load(getClass().getResource("/edu/esprit/gui/AfficherMedicament.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
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

        medicamentsTable.setItems(list);
    }

    @FXML
    private void returnButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gui/Home.fxml"));
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
    private void recherche(ActionEvent event) {

    }

    @FXML
    private void sortByName(ActionEvent event) {
        Comparator<Medicament> byName = Comparator.comparing(Medicament::getNom);
        SortedList<Medicament> sortedData = new SortedList<>(filteredData);
        sortedData.setComparator(byName);
        medicamentsTable.setItems(sortedData);
    }
    



}
