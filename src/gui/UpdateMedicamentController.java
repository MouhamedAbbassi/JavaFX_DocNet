package gui;

import Service.ServiceMedicament;
import Entity.Medicament;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UpdateMedicamentController implements Initializable {

    private AnchorPane updateMedicamentPane;
    @FXML
    private TextField nomTextField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField doseTextField;
    @FXML
    private TextField prixTextField;
    @FXML
    private TextField stockTextField;
    private TextField idTextField;
    private Medicament medicament;
    private ServiceMedicament serviceMedicament;
    private ObservableList<Medicament> medicaments;

    private File file;
    private String path = null;
    @FXML
    private Button uploadButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button retourbtn;
     
    Medicament medi = new Medicament();
    ServiceMedicament serviceM = new ServiceMedicament();
    @Override

  public void initialize(URL url, ResourceBundle rb) {
        nomTextField.setText(medi.getNom());
        typeComboBox.setValue(medi.getType());
        doseTextField.setText(String.valueOf(medi.getNb_dose()));
        prixTextField.setText(String.valueOf(medi.getPrix()));
        stockTextField.setText(String.valueOf(medi.getStock()));
       
    }
  public void setMedicament(Medicament med) {
        medi = med;
        nomTextField.setText(med.getNom());
        typeComboBox.setValue(med.getType());
        doseTextField.setText(String.valueOf(med.getNb_dose()));
        prixTextField.setText(String.valueOf(med.getPrix()));
        stockTextField.setText(String.valueOf(med.getStock()));
    }
 
 
    /*path = medicament.getImagePath();
    if (path != null) {
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }*/



    @FXML
    private void updateButton(ActionEvent event) throws SQLException {
   
        
       
            // Get the selected item from the combo box
            String selectedItem = typeComboBox.getSelectionModel().getSelectedItem();

            // Find the corresponding Medicament object in the list
            /*Medicament selectedMedicament = null;
            for (Medicament m : medicaments) {
                if (m.getType().equals(selectedItem)) {
                    selectedMedicament = m;
                    break;
                }
            }*/

            // Update the fields of the selected Medicament object
            /*selectedMedicament.setNom(nomTextField.getText());
            selectedMedicament.setType(selectedItem);
            selectedMedicament.setNb_dose(Integer.parseInt(doseTextField.getText()));
            selectedMedicament.setPrix(Integer.parseInt(prixTextField.getText()));
            selectedMedicament.setStock(Integer.parseInt(stockTextField.getText()));*/

            /*if (path != null) {
                try {
                    byte[] imageBytes = Files.readAllBytes(Paths.get(path));
                    selectedMedicament.setImage(imageBytes.toString());
                } catch (IOException ex) {
                    Logger.getLogger(UpdateMedicamentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/

            
            //Update the selected Medicament object in the database
            Medicament m = new Medicament(medi.getId(),nomTextField.getText(), selectedItem, Integer.parseInt(doseTextField.getText()), Integer.parseInt(prixTextField.getText()), Integer.parseInt(stockTextField.getText()),"hhhhh");
            serviceM.modifierm(m);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Controle de medicament");
            alert.setHeaderText("Le medicament a été modifié!");
            alert.showAndWait();
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherMedicament.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
                return;

    

    }
    

    @FXML
    private void returnButton(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherMedicament.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
