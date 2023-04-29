package gui;


import Entity.Reservation;
import Service.ServiceReservation;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */
public class DisplayReservationController implements Initializable {

    @FXML
    private Button deleteReservation;
    @FXML
    private Button addresButton;
     @FXML
    private Button calendar;
    @FXML
private TableView<Reservation> reservationTable;

@FXML
private TableColumn<Reservation, Integer> idc;

@FXML
private TableColumn<Reservation, Integer> u;

@FXML
private TableColumn<Reservation, Integer> patient_idc;

@FXML
private TableColumn<Reservation, String> startc;

@FXML
private TableColumn<Reservation, LocalDateTime> endc;

@FXML
private TableColumn<Reservation, String> c;

    @FXML
    private Button updateresButton;
  @FXML
private Button sortButton;


    
@FXML
    ObservableList<Reservation> list = FXCollections.observableArrayList();
    
  private ServiceReservation reservationService;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.reservationService = new ServiceReservation();
        
        // Load the reservations from the database and add them to the table
       
        idc.setCellValueFactory(new PropertyValueFactory<>("id"));
        startc.setCellValueFactory(new PropertyValueFactory<>("start"));
        endc.setCellValueFactory(new PropertyValueFactory<>("end"));
        c.setCellValueFactory(new PropertyValueFactory<>("comment"));
        
        
       // Set date cell factory to display dates without the time component
   /* startc.setCellFactory(column -> {
        return new TableCell<Reservation, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        };
    });
    
    

    endc.setCellFactory(column -> {
        return new TableCell<Reservation, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        };
    });*/

 
        this.reservationTable.setItems(this.list);
        this.list.addAll(this.reservationService.afficher());
System.out.println(list);
    }
    
    
    
    
    @FXML
private void handleSortButton(ActionEvent event) {
    reservationTable.getSortOrder().clear(); // Clear any existing sort order
    reservationTable.getSortOrder().add(c); // Sort by the c column
    reservationTable.sort();
}

    
        public void refresh()
{
ServiceReservation serviceres = new ServiceReservation();
ObservableList<Reservation> resList = serviceres.afficher();
reservationTable.setItems(resList);
}
    
    
    @FXML
     void addReservations() throws IOException {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("addReservation.fxml"));
Scene scene = new Scene(root);
Stage stage = new Stage();
stage.setScene(scene);
stage.show();
          // Get the current stage (window) from the button's scene
        Stage homeStage = (Stage) updateresButton.getScene().getWindow();
        // Close the current stage (window)
        homeStage.close();
        
    }

      @FXML
    public void calendar() throws IOException {
         Stage primaryStage = new Stage() ;
       FXMLLoader loader = new FXMLLoader(getClass().getResource("fullCalendar.fxml"));
        primaryStage.setTitle("Full Calendar");
        primaryStage.setScene(new Scene(loader.load()));
        // Get the controller and add the calendar view to it
        Controller controller = loader.getController();
        controller.calendarPane.getChildren().add(new FullCalendarView(YearMonth.now()).getView());
        primaryStage.show();
       
        
    }
     
    @FXML
     void updateReservation() throws IOException {
         Reservation reservation = reservationTable.getSelectionModel().getSelectedItem();
        if (reservation != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateReservation.fxml"));
            Parent root = loader.load();
            UpdateReservationController updateresController = loader.getController();
            updateresController.setReservation(reservation);
            updateresController.setHomeController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
              // Get the current stage (window) from the button's scene
        Stage homeStage = (Stage) updateresButton.getScene().getWindow();
        // Close the current stage (window)
        homeStage.close();
            
        } else {
            // Display an error message if no workshop is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Reservation Selected");
            alert.setContentText("Please select a Reservation to update.");
            alert.showAndWait();
        }
        
    }
    

    
    @FXML
     void deleteReservation() {
        
         ServiceReservation serviceres = new ServiceReservation();
        // Get the selected row from the table
        int selectedIndex = reservationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Extract the ID of the event from the selected row
            int resId = idc.getCellData(selectedIndex);
            // Call the deleteEvent() method with the ID of the event
            serviceres.supprimer(resId);
            // Remove the selected row from the table
            reservationTable.getItems().remove(selectedIndex);
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
