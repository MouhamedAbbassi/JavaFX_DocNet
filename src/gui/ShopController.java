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
import com.google.zxing.BinaryBitmap;
import java.util.Comparator;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class ShopController implements Initializable {

    Connection cnx;

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

    private TableColumn<Medicament, Object> addpanier;
private ServiceMedicament serviceMedicament;
    ServiceMedicament service = new ServiceMedicament();
    ObservableList<Medicament> list = service.getAll();
    int qtePanier =1;
    @FXML
    private Pagination pagination;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nbDoseCol.setCellValueFactory(new PropertyValueFactory<>("nb_dose"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
medicamentsTable.setItems(list);
addpanier.setCellFactory(param -> new TableCell<Medicament, Object>() {
            private final Button button = new Button("addpanier");

            {
                button.setOnAction(event -> {
                    Medicament ordonnance = getTableView().getItems().get(getIndex());
                    // Perform the desired action on th-e selected row
                    int id = ordonnance.getId();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("panier.fxml"));
                        Parent root = loader.load();
                         PanierController formController = loader.getController();
                        formController.initialize(ordonnance,qtePanier);
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

        medicamentsTable.setItems(list);
    }

    private void handleAddToCart() {
        // Get the selected medicament from the table
        Medicament medicament = medicamentsTable.getSelectionModel().getSelectedItem();

        // Add the medicament to the panier using the PanierController
       // PanierController.ajouterPanier(panier);
    }
     public void back(ActionEvent event) {
   try {
        // Get the reference of the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileUser.fxml"));
        Parent root = loader.load();

        // Create a new stage and display the new scene
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();

        // Close the current stage
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
