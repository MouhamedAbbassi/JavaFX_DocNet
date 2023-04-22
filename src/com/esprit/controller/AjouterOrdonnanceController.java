/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.controller;

import Services.MedicamentService;
import Services.OrdonnanceService;
import Services.UserService;
import com.esprit.entity.Medicament;
import com.esprit.entity.Ordonnance;
import com.esprit.entity.OrdonnanceMedicament;
import com.esprit.entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.silentsoft.actlist.plugin.tray.TrayNotification;
import org.controlsfx.control.Notifications;
/**
 *
 * @author asus
 */
public class AjouterOrdonnanceController implements Initializable {

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

    private ObservableList<formRepeater> formControllers = FXCollections.observableArrayList();

    public ObservableList<formRepeater> getFormControllers() {
        return formControllers;
    }

    private int ordPatientId;
    private int ord;

    public void setFormControllers(ObservableList<formRepeater> formControllers) {
        this.formControllers = formControllers;
    }

    public static final String ACCOUNT_SID = "TWILIO_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "TWILIO_AUTH_TOKEN";

    UserService userService = new UserService();
    int currentUserId = userService.getCurrentUserId();
    User doctor;
    User patient;
    private int lenght = 0;
    OrdonnanceService service = new OrdonnanceService();

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initialize(int id, int patientId) throws SQLException {
        ord = id;
        ordPatientId = patientId;
        User doc = userService.findUser(currentUserId);
        User pat = userService.findUser(ordPatientId);
        doctor = doc;
        patient = pat;
        nomMedecinField.setText(doctor.getNom() + " " + doctor.getPrenom());
        nomPatientField.setText(patient.getNom() + " " + patient.getPrenom());
        nomMedecinField.setEditable(false);
        nomPatientField.setEditable(false);
    }

    @FXML
    private void insertButton(ActionEvent event) throws IOException, InterruptedException {
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
        service.Inserer(o, ord, currentUserId, ordPatientId);
        // get the values of the dosage and duration fields
        for (formRepeater formController : formControllers) {
            String dosage = formController.getDosageTextField();
            String duration = formController.getDurationTextField();
            String nomMedicament = formController.getNomMedicamentComboBox();
            if (dosage.trim().isEmpty() || duration.trim().isEmpty() || nomMedicament.trim().isEmpty()) {
                // show an error message and return
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields for each medication!");
                alert.showAndWait();
                return;
            }
            MedicamentService medicamentService = new MedicamentService();
            int medicamentId = medicamentService.getIdByName(nomMedicament);
            // check if dosage is within the desired range
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
            OrdonnanceMedicament om = new OrdonnanceMedicament(Integer.parseInt(dosage), Integer.parseInt(duration), medicamentId);
            service.InsererOm(om);
        }
        Image img = new Image("/uploads/tick1.png");
        Notifications.create()
                .title("Ordonnance")
                .text("New Ordonnance added successfully")
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
        formController.initialize(this.lenght, formControllers);
        formControllers.add(formController);
        formContainer.getChildren().add(formRoot);
        formRoot.setVisible(true);
        this.lenght = this.lenght + 1;
    }

}
