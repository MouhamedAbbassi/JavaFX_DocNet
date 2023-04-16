/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.FicheService;
import com.esprit.entity.Fiche;
import com.esprit.entity.Ordonnance;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author asus
 */
public class UpdateFicheController implements Initializable {

    @FXML
    private TextField tel;
    @FXML
    private DatePicker date;
    private RadioButton genre;
    @FXML
    private ComboBox<String> etatC;
    @FXML
    private ComboBox<String> typeA;
    @FXML
    private Button modifierButton;
    @FXML
    private Button annuler;
    @FXML
    private RadioButton genreHomme;
    @FXML
    private RadioButton genreFemme;
    
    Fiche fiche = new Fiche();
    int idf;
    FicheService service = new FicheService();
    ToggleGroup group = new ToggleGroup();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        genreHomme.setToggleGroup(group);
        genreFemme.setToggleGroup(group);
        ObservableList<String> etatList = FXCollections.observableArrayList("Bon", "Moyen", "Mauvais");
        etatC.setItems(etatList);
        ObservableList<String> assuranceList = FXCollections.observableArrayList("Commune", "Privé", "professionnelle");
        typeA.setItems(assuranceList);
        
        
      
    }    
    public void initialize(Fiche f) {
        fiche = f;
        idf = fiche.getId();
        if(fiche.getTel() != 0 ){
            tel.setText(String.valueOf(fiche.getTel()));
        }
        if(fiche.getGenre() != null){
            if (fiche.getGenre().equals("Homme")) {
                genreHomme.setSelected(true);
            } else {
                genreFemme.setSelected(true);
            }
        }
        if(fiche.getEtatClinique()!= null){
            etatC.getSelectionModel().select(fiche.getEtatClinique());  
        }
        if(fiche.getTypeAssurance()!= null){
            typeA.getSelectionModel().select(fiche.getTypeAssurance());  
        }
        if (fiche.getDate() != null) {
            LocalDate localDate = LocalDate.parse(fiche.getDate());
            date.setValue(localDate);
        }
    }    

    @FXML
    private void annulerButton(ActionEvent event) {
        try { 
            System.out.println(idf);
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
    private void modifierButton(ActionEvent event) {
        if (!validateTel()) {
            return;
        }
        RadioButton selectedGenre = (RadioButton) group.getSelectedToggle();
        LocalDate selectedDate = date.getValue();
        LocalDate currentDate = LocalDate.now();
        if (selectedDate.isAfter(currentDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La date sélectionnée ne peut pas dépasser la date actuelle.");
            alert.showAndWait();
            return;
        }
        Fiche o = new Fiche(Integer.parseInt(tel.getText()), date.getValue().toString(), selectedGenre.getText(), etatC.getValue(), typeA.getValue());
        service.modifier(o,idf);
        try { 
            System.out.println(idf);
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
    private boolean validateTel() {
    String telText = tel.getText().trim();
    // Check if the input matches the regex for 8 digits
    if (!telText.matches("\\d{8}")) {
        // Show an error message to the user
        Alert alert = new Alert(Alert.AlertType.ERROR, "Le numéro de téléphone doit contenir exactement 8 chiffres.");
        alert.showAndWait();
        return false;
    }
    return true;
}
    
}
