package gui;

import Entity.Rate;
import Service.ServiceNote;

import java.io.IOException;
import java.net.URL;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */
public class HomeController implements Initializable {

    
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView<Rate> rateTable;
    @FXML
    private TableColumn<Rate, Integer> idColumn;
    
    @FXML
    private TableColumn<Rate, String> n;
    @FXML
    private TableColumn<Rate, String> opinionColumn;
    @FXML
    private Button deleteRate;
      @FXML
private Button sortButton;
    

     private ObservableList<Rate> List;
   private ServiceNote serviceNote;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
         this.serviceNote = new ServiceNote();
             this.List = FXCollections.observableArrayList();
            
           
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        n.setCellValueFactory(new PropertyValueFactory<>("note"));
        opinionColumn.setCellValueFactory(new PropertyValueFactory<>("opinion"));
        
        
        this.List.addAll(this.serviceNote.afficher());
         this.rateTable.setItems(this.List);
                System.out.println(List);

    }
  
     

  private ObservableList<Rate> getRateList() {
        ObservableList<Rate> RateListe = FXCollections.observableArrayList();
        ServiceNote serviceNote = new ServiceNote();
        RateListe = serviceNote.afficher();
        return RateListe;
    }
    
   
      @FXML
private void handleSortButton(ActionEvent event) {
    rateTable.getSortOrder().clear(); // Clear any existing sort order
    rateTable.getSortOrder
        ().add(opinionColumn); // Sort by the c column
    rateTable.sort();
}
    @FXML
    void addRate() throws IOException {
        // Load the FXML file of the UI you want to display
        Parent root = FXMLLoader.load(getClass().getResource("addRate.fxml"));

        // Create a new scene with the loaded FXML file as its root node
        Scene scene = new Scene(root);

        // Get the current stage (window) from the button's scene
        Stage stage = (Stage) addButton.getScene().getWindow();

        // Set the new scene on the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
          // Get the current stage (window) from the button's scene
        Stage homeStage = (Stage) updateButton.getScene().getWindow();
        // Close the current stage (window)
        homeStage.close();
        
    }

    @FXML
    void updateRate() throws IOException {
        Rate rate = rateTable.getSelectionModel().getSelectedItem();
        if (rate != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateRate.fxml"));
            Parent root = loader.load();
            UpdateRateController updateRateController = loader.getController();
            updateRateController.setRate(rate);
            updateRateController.setHomeController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
              // Get the current stage (window) from the button's scene
        Stage homeStage = (Stage) updateButton.getScene().getWindow();
        // Close the current stage (window)
        homeStage.close();
            
        } else {
            // Display an error message if no workshop is selected
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("No Rate Selected");
            alert.setContentText("Please select a rate to update.");
            alert.showAndWait();
        }

    }
public void refresh()
{
ServiceNote serviceNote = new ServiceNote();
ObservableList<Rate> rateList = serviceNote.afficher();
rateTable.setItems(rateList);
}
    @FXML
    void deleteRate() {
        ServiceNote serviceNote = new ServiceNote();
        // Get the selected row from the table
        int selectedIndex = rateTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Extract the ID of the event from the selected row
            int rateId = idColumn.getCellData(selectedIndex);
            // Call the deleteEvent() method with the ID of the event
            serviceNote.supprimer(rateId);
            // Remove the selected row from the table
            rateTable.getItems().remove(selectedIndex);
        }
    }
    
    
           @FXML
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
