/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.MedicamentService;
import Services.OrdonnanceService;
import com.esprit.entity.Medicament;
import com.esprit.entity.Ordonnance;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author asus
 */
public class formRepeater extends VBox {
    
    @FXML
    private ComboBox<String> nomMedicament;
    
    @FXML
    private TextField dosage;

    @FXML
    private TextField duration;
     @FXML
    private Button deleteButton;
     @FXML
    private VBox Container;
    @FXML
    private VBox myVBox;
    
    public int id;
    //private AjouterOrdonnanceController parentController = new AjouterOrdonnanceController();
    private ObservableList<formRepeater> parentController;
     public static final String ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
     public static final String AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
      
    MedicamentService serviceM = new MedicamentService();
    ObservableList<Medicament> listM = serviceM.getall();
    ObservableList<String> nomMedicaments = FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     */
    
    
 

    public void initialize(URL url, ResourceBundle rb) {
        ;
        /*OrdonnanceService service=new OrdonnanceService();
        ObservableList<Ordonnance> list = service.getall();*/
        for (Medicament med : listM) {
            nomMedicaments.add(med.getNom());
        }
        ObservableList<String> nomM = FXCollections.observableArrayList(nomMedicaments);
        nomMedicament.setItems(nomM);
    }    

    void initialize(int id,ObservableList<formRepeater> parentController ) {
        
        /*OrdonnanceService service=new OrdonnanceService();
        ObservableList<Ordonnance> list = service.getall();*/
        for (Medicament med : listM) {
            nomMedicaments.add(med.getNom());
        }
        this.setId("myVBox"+id);
        this.id =id;
        ObservableList<String> nomM = FXCollections.observableArrayList(nomMedicaments);
        nomMedicament.setItems(nomM);
        this.parentController = parentController;
        
    }
    @FXML
    private void deleteButton() {
        
        parentController.remove(this);
        Container.getChildren().remove(myVBox);
    }
    public String getDosageTextField() {
        return dosage.getText();
    }
    public String getDurationTextField() {
        return duration.getText();
    }
    public String getNomMedicamentComboBox() {
        return nomMedicament.getValue();
    }

    public void setDosageTextField(String dosage) {
    this.dosage.setText(dosage);
    }

    public void setDurationTextField(String dosage) {
    this.duration.setText(dosage);
    }
   public void setMedicamentComboBox(String medicamentName) {
    nomMedicament.setValue(medicamentName);
}
    
    
}
