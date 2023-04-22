/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.OrdonnanceService;
import Services.UserService;
import com.esprit.entity.Ordonnance;
import com.esprit.entity.User;
import com.esprit.entity.user_user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 *
 * @author asus
 */
public class ListePatientController implements Initializable{
    @FXML
    private TableView<User> tftableview;
    @FXML
    private TableColumn<User, String> nomPatient;
    @FXML
    private VBox patientContainer;
    @FXML
    private Pagination pagination;
    UserService userService = new UserService();
    int currentUserId = userService.getCurrentUserId();
    OrdonnanceService service = new OrdonnanceService();
    ObservableList<user_user> list = service.getPatientListe(currentUserId);
    ObservableList<User> namelistePatient = userService.getPatientsFromUserUserList(list);
    public void initialize(URL url, ResourceBundle rb) {
         int pageSize = 2; // Number of items to display per page
    int pageCount = (int) Math.ceil(namelistePatient.size() / (double) pageSize);
    pagination.setPageCount(pageCount);
    pagination.setPageFactory(new Callback<Integer, Node>() {
        public Node call(Integer pageIndex) {
            int startIndex = pageIndex * pageSize;
            int endIndex = Math.min(startIndex + pageSize, namelistePatient.size());
            return createPage(startIndex, endIndex);
        }
    });
        

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
    private Node createPage(int startIndex, int endIndex) {
    VBox page = new VBox(10);
    for (int i = startIndex; i < endIndex; i++) {
        User patient = namelistePatient.get(i);
        VBox patientBox = new VBox();
        ImageView imageView;
        if (patient.getImage() == null) {
            imageView = new ImageView(new Image(("/uploads/placeholder.jpg")));
        } else {
            imageView = new ImageView(new Image("/uploads/" + patient.getImage()));
        }
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        Button voirFicheBtn = new Button("Voir Fiche");
        voirFicheBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/afiicherFiche.fxml"));
                    Parent root = loader.load();
                    AfiicherFicheController controller = loader.getController();
                    controller.setPatientImage(imageView.getImage());
                    controller.initialize(patient.getId());
                    Scene newScene = new Scene(root);
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(newScene);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Label nameLabel = new Label(patient.getNom() + " " + patient.getPrenom());
        patientBox.getChildren().addAll(imageView, nameLabel,voirFicheBtn);
        patientBox.setAlignment(Pos.CENTER);
        page.getChildren().add(patientBox);
    }
    return page;
}
}
