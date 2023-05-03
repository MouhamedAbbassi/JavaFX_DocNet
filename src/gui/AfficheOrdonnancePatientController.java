/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.ServiceMedicament;
import Service.OrdonnanceService;
import Service.ServiceUser;
import Entity.Medicament;
import Entity.Ordonnance;
import Entity.OrdonnanceMedicament;
import Service.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficheOrdonnancePatientController implements Initializable {

    
    @FXML
    private VBox ordonnancesBox;
    
    OrdonnanceService ordonnanceService = new OrdonnanceService();
    ServiceUser userService = new ServiceUser();
    UserSession userSession = new UserSession();
    OrdonnanceService service = new OrdonnanceService();
    ObservableList<Ordonnance> list = service.getallOrdonnance(userSession.getUser().getId());
    @FXML
    private VBox vbox;
    @FXML
    private Button ret;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        for (Ordonnance ordonnance : list) {
            
        // Create a VBox to hold the information for each ordonnance
            VBox vbox = new VBox();
            HBox hbox = new HBox(10); // 10 is the spacing between the image and name
            hbox.setAlignment(Pos.CENTER_LEFT);
            vbox.setMinWidth(200); // set the minimum width of the VBox
            vbox.setMinHeight(300);
           String imagePath = "/resource/dddd_cleanup.png";
        Image image = new Image(imagePath);
        BackgroundImage backgroundImage = new BackgroundImage(
        image, 
        BackgroundRepeat.NO_REPEAT, 
        BackgroundRepeat.NO_REPEAT, 
        BackgroundPosition.CENTER, 
        new BackgroundSize(
                BackgroundSize.AUTO, 
                BackgroundSize.AUTO, 
                false, 
                false, 
                true, 
                false
        )
        );
        Background background = new Background(backgroundImage);
        vbox.setBackground(background);
            ImageView doctorImage;
    // Add the doctor's image
            if(ordonnance.getDoctors().get(0).getImage()==null)
            {
                doctorImage = new ImageView(new Image(("/resource/placeholder.jpg")));
            }else{
                doctorImage = new ImageView(new Image("file:///C:/xampp/htdocs/uploads/images/" + ordonnance.getDoctors().get(0).getImage()));
            }
            doctorImage.setFitWidth(100);
            doctorImage.setFitHeight(100);
            
            Circle clip = new Circle(35, 35, 35);
            doctorImage.setClip(clip);
            Group group = new Group(doctorImage);
            doctorImage.setTranslateX(6);
            doctorImage.setTranslateY(37);
            Label doctorName = new Label(ordonnance.getDoctors().get(0).getNom() + " " + ordonnance.getDoctors().get(0).getPrenom());
            doctorName.setStyle("-fx-font-size: 20;-fx-font-family: serif;");
            doctorName.setPadding(new Insets(20, 0, 0, 10));

            hbox.getChildren().addAll(doctorImage, doctorName);
            vbox.getChildren().add(hbox);

            // Add the comment
            Label comm = new Label("Commentaire de votre medecin : ");
            Label comment = new Label(ordonnance.getCommentaire());
            comm.setStyle("-fx-font-size: 16; -fx-font-family: Arial; -fx-font-weight: bold;");
            comment.setStyle("-fx-font-size: 16;");
            comm.setPadding(new Insets(20, 0, 0, 10));
            comment.setPadding(new Insets(17, 0, 0, 10));
            HBox hbox1 = new HBox(comm, comment);
            hbox.setSpacing(10);

            vbox.getChildren().add(hbox1);
            StringBuilder sb = new StringBuilder();
            for (OrdonnanceMedicament medicament : ordonnance.getMedicaments()) {
                ServiceMedicament medicamentService = new ServiceMedicament();
                Medicament med = new Medicament();
                med.setNom(medicamentService.getMedicamentNameById(medicament.getIdMedicament()));
                sb.append(med.getNom()).append(" (Dosage: ")
                  .append(medicament.getDosage()).append(", Durée: ")
                  .append(medicament.getDuration()).append(")\n");
            }
            
            Label label = new Label("   Medicaments : ");
            Label medicamentsLabel = new Label(sb.toString());
            label.setStyle("-fx-font-size: 16; -fx-font-family: Arial; -fx-font-weight: bold;");
            medicamentsLabel.setStyle("-fx-font-size: 14;");
            HBox hbox2 = new HBox(label, medicamentsLabel);
            hbox.setSpacing(10);

            vbox.getChildren().add(hbox2);
          
            // Add some padding between each ordonnance
            vbox.setPadding(new Insets(10));

            // Add the ordonnance VBox to the parent VBox
            ordonnancesBox.getChildren().add(vbox);
        }
    }

    @FXML
    private void returnButton(ActionEvent event) {
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
