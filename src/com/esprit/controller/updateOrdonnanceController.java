/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.MedicamentService;
import Services.OrdonnanceMedicamentService;
import Services.OrdonnanceService;
import com.esprit.entity.Medicament;
import com.esprit.entity.Ordonnance;
import com.esprit.entity.OrdonnanceMedicament;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import org.controlsfx.control.Notifications;


/**
 *
 * @author asus
 */
public class updateOrdonnanceController {
    /**
     * Initializes the controller class.
     */
    @FXML
    private ListView<formRepeater> formListView;
    private ObservableList<formRepeater> formList;
    @FXML
    private Button insertButton;
     @FXML
    private Button returnButton;
     @FXML
    private ComboBox<Integer> idField;
     @FXML
    private ComboBox<String> nomMedicament;
    
    @FXML
    private TextField nomMedecinField;

    @FXML
    private TextField nomPatientField;

    @FXML
    private TextArea commentaireField;
    @FXML
    private VBox formContainer;
    
    private Ordonnance ordonnance;
    
    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }
    private ObservableList<formRepeater> formControllers = FXCollections.observableArrayList();

    public ObservableList<formRepeater> getFormControllers() {
        
            
        return formControllers;
    }

    

    public void setFormControllers(ObservableList<formRepeater> formControllers) {
        this.formControllers = formControllers;
    }
         
     public static final String ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
     public static final String AUTH_TOKEN = "TWILIO_AUTH_TOKEN";
     
     private int lenght = 0;
     int idO;
     String recipientEmail;
      OrdonnanceService service = new OrdonnanceService();
    //ObservableList<Ordonnance> list = service.getall();
    MedicamentService serviceM = new MedicamentService();
    ObservableList<Medicament> listM = serviceM.getall();
    OrdonnanceMedicamentService serviceMO = new OrdonnanceMedicamentService();



    /**
     * Initializes the controller class.
     */
    
     public void initialize(URL url, ResourceBundle rb) {
         if (ordonnance != null) {
            nomMedecinField.setText(ordonnance.getNomMedecin());
            nomPatientField.setText(ordonnance.getNomPatient());
            commentaireField.setText(ordonnance.getCommentaire());
        }
         
    }    
     public void initialize(Ordonnance ordonnance,String patientEmail) throws IOException {
         idO = ordonnance.getId();
         recipientEmail = patientEmail;
         if (ordonnance != null) {
            nomMedecinField.setText(ordonnance.getNomMedecin());
            nomPatientField.setText(ordonnance.getNomPatient());
            commentaireField.setText(ordonnance.getCommentaire());
        }
        nomMedecinField.setEditable(false);
        nomPatientField.setEditable(false);
         ObservableList<OrdonnanceMedicament> omList = serviceMO.getMedicamentByOrdonnance(ordonnance.getId());
        
        // Initialize each formRepeater instance with the values obtained from the database
        for (int i = 0; i < omList.size(); i++) {
            OrdonnanceMedicament om = omList.get(i);
            if (i < formControllers.size()) {
                formRepeater formController = formControllers.get(i);
                formController.setMedicamentComboBox(serviceM.getMedicamentNameById(om.getId()));
                formController.setDosageTextField(String.valueOf(om.getDosage()));
                formController.setDurationTextField(String.valueOf(om.getDuration()));
            } else {
                // If there are more OrdonnanceMedicament instances than formRepeater instances, add a new formRepeater
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/formRepeater.fxml"));
                Parent formRoot = loader.load();
                formRepeater formController = loader.getController();
                formController.initialize(this.lenght, formControllers);
           
                formController.setMedicamentComboBox(serviceM.getMedicamentNameById(om.getIdMedicament()));
                formController.setDosageTextField(String.valueOf(om.getDosage()));
                formController.setDurationTextField(String.valueOf(om.getDuration()));
                formControllers.add(formController);
                formContainer.getChildren().add(formRoot);
                formRoot.setVisible(true);
                this.lenght = this.lenght + 1;
            }
            
        }
        
    }    
    
    @FXML
    private void insertButton(ActionEvent event) throws IOException, MessagingException {
        String commentaire = commentaireField.getText();
        if (commentaire.length() < 3) {
            // show an error message and return
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Commentaire should have at least 3 characters!");
            alert.showAndWait();
            return;
        }
        Ordonnance o = new Ordonnance(nomMedecinField.getText(), nomPatientField.getText(), commentaireField.getText());
         service.modifier(o,idO);
        // get the values of the dosage and duration fields
     
        for (formRepeater formController : formControllers) {
      
        String dosage = formController.getDosageTextField();
        String duration = formController.getDurationTextField();
        String nomMedicament = formController.getNomMedicamentComboBox();
        if (dosage.isEmpty() || duration.isEmpty() || nomMedicament.isEmpty()) {
            // show an error message and return
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required!");
            alert.showAndWait();
            return;
        }
        int dosageValue = Integer.parseInt(dosage);
        if (dosageValue <= 0 || dosageValue >= 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Dosage should be between 1 and 5!");
            alert.showAndWait();
            return;
        }
        
        // check if duration is greater than 0
        int durationValue = Integer.parseInt(duration);
        if (durationValue <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Duration should be greater than 0!");
            alert.showAndWait();
            return;
        }
        }
        for (formRepeater formController : formControllers) {
            
            String dosage = formController.getDosageTextField();
            String duration = formController.getDurationTextField();
            String nomMedicament = formController.getNomMedicamentComboBox();
            MedicamentService medicamentService = new MedicamentService();
            int medicamentId = medicamentService.getIdByName(nomMedicament);
           
            OrdonnanceMedicament om = new OrdonnanceMedicament(Integer.parseInt(dosage), Integer.parseInt(duration) ,medicamentId);
            service.modifierOM(om,idO);
            
        }
        EmailSender.sendEmail(recipientEmail);
            Image img = new Image("/uploads/tick1.png");
                    Notifications.create()
                            .title("Ordonnance")
                            .text("Ordonnance Updated successfully")
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
    private void addForm(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/formRepeater.fxml"));
            Parent formRoot = loader.load();
            formRepeater formController = loader.getController();
            formController.initialize(this.lenght,formControllers);
            formControllers.add(formController);
            formContainer.getChildren().add(formRoot);
            formRoot.setVisible(true);
            this.lenght = this.lenght + 1;
    }
}
