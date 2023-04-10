/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.FicheService;
import Services.MedicamentService;
import Services.OrdonnanceService;
import Services.ReservationService;
import Services.UserService;
import com.esprit.entity.Fiche;
import com.esprit.entity.Ordonnance;
import com.esprit.entity.OrdonnanceMedicament;
import com.esprit.entity.reservation;
import com.esprit.entity.Medicament;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfiicherFicheController {

    @FXML
    private ImageView patientImage;
    @FXML
    private VBox detailFiche;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab tabReservation;
    @FXML
    private Tab tabOrdonnance;
    
     private ObjectProperty<Image> patientImages = new SimpleObjectProperty<>();
    @FXML
    private Label dNaissance;
    @FXML
    private Label tel;
    @FXML
    private Label genre;
    @FXML
    private Label eClinique;
    @FXML
    private Label tAssurance;
    @FXML
    private Button ret;
    @FXML
    private TableColumn<?,?> dateColumn;
    @FXML
    private TableView<reservation> reservationTable;
    
    @FXML
    private TableView<Ordonnance> ordonnanceTable;
    @FXML
    private TableColumn<Ordonnance, String> dateColumns;
    @FXML
    private TableColumn<Ordonnance, String> commentaireColumn;
    @FXML
    private TableColumn<Ordonnance, String> medicamentsColumn;

    OrdonnanceService ordonnanceService = new OrdonnanceService();
    
    
Fiche f = new Fiche();

    public ObjectProperty<Image> patientImageProperty() {
        return patientImages;
    }

    public Image getPatientImage() {
        return patientImages.get();
    }

    public void setPatientImage(Image image) {
        patientImages.set(image);
    }
    
    int patientId;
    UserService userService = new UserService();
    int currentUserId = userService.getCurrentUserId();
    
    FicheService ficheService = new FicheService();
    ReservationService reservationService = new ReservationService();
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
     
     public void initialize(int id) {
            
        patientId = id; 
        ObservableList<reservation> reservations = reservationService.getReservationForPatient(patientId,currentUserId);
        ObservableList<Ordonnance> ordonnances = ordonnanceService.getOrdonnanceForPatient(patientId, currentUserId);
         System.out.println(ordonnances.size());
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        reservationTable.setItems(reservations);
        dateColumns.setCellValueFactory(new PropertyValueFactory<>("date"));
        commentaireColumn.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        medicamentsColumn.setCellValueFactory(cellData -> {
        StringBuilder sb = new StringBuilder();
        for (OrdonnanceMedicament medicament : cellData.getValue().getMedicaments()) {
            MedicamentService medicamentService = new MedicamentService();
            Medicament med = new Medicament();
            
            med.setNom(medicamentService.getMedicamentNameById(medicament.getIdMedicament()));
            sb.append(med.getNom()).append(" (Dosage: ")
            .append(medicament.getDosage()).append(", Durée: ")
            .append(medicament.getDuration()).append(")\n");
    }
    return new SimpleStringProperty(sb.toString());
    });

        ordonnanceTable.setItems(ordonnances);
        System.out.println("pateitn id:"+id);
        patientImage.setFitWidth(200); // set the width to 200
        patientImage.setFitHeight(200); // set the height to 200
        patientImage.setPreserveRatio(true); // preserve the aspect ratio of the image
        patientImage.imageProperty().bind(patientImages);
        Fiche fiche = ficheService.getFiche(currentUserId, patientId);
        f=fiche;
        if(fiche.getDate() == null)
        {
            dNaissance.setText("Date de Naissance : --------");
        }else{
            dNaissance.setText("Date de Naissance : "+fiche.getDate());
        }
        if(fiche.getGenre() == null)
        {
            genre.setText("Genre : --------");
        }else{
            genre.setText("Genre : "+fiche.getGenre());
        }
        if(fiche.getTel() == 0)
        {
            tel.setText("Tel : --------");
        }else{
            tel.setText("Tel : "+String.valueOf(fiche.getTel()));
        }
        if(fiche.getEtatClinique() == null)
        {
            eClinique.setText("Etat Clinique : --------");
        }else{
            eClinique.setText("Etat Clinique : "+fiche.getEtatClinique());
        }
        if(fiche.getTypeAssurance() == null)
        {
            tAssurance.setText("Type d'assurance : --------");
        }else{
            tAssurance.setText("Type d'assurance : "+fiche.getTypeAssurance());
        }
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
    @FXML
    private void modifierButton(ActionEvent event) {
    	try { 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/updateFiche.fxml"));
            Parent root = loader.load();
            UpdateFicheController controller = loader.getController();
            controller.initialize(f);
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
