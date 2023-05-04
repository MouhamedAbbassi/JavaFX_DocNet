package gui;

import Service.ServiceMedicament;
import Service.ServicePanier;
import Entity.Medicament;
import Entity.Panier;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PanierController implements Initializable {
        Connection cnx;

    ServicePanier servicePanier = new ServicePanier();
    ServiceMedicament serviceMedicament = new ServiceMedicament();
    Panier panier;
    Medicament medicament;
    Medicament med = new Medicament();
    
    
    @FXML
    private TableColumn<?, ?> nomColp;
    @FXML
    private TableColumn<?, ?> prixColp;
    @FXML
    private TableColumn<?, ?> qp;
    @FXML
    private TableView<Panier> panierlist;

    ObservableList<Panier> list = servicePanier.getAll();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void initialize(Medicament m ,int qtePanier){
        med = m;
        Panier p = servicePanier.getPrix(med);
       
        System.out.println(p.getPrix());
          nomColp.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixColp.setCellValueFactory(new PropertyValueFactory<>("prix"));
        qp.setCellValueFactory(new PropertyValueFactory<>("qte"));
        int qte = p.getQte() +qtePanier;
        servicePanier.modifierQte(m, qte);
         System.out.println(p.getQte());
       Panier panierItem = new Panier(p.getPrix(),p.getNom(),qte);

    // Load the panier items into the table, including the new Panier object
    ObservableList<Panier> panierItems = FXCollections.observableArrayList(panierItem);
    panierlist.setItems(panierItems);
    }
 private ObservableList<Panier> getPanierItems() {
        ObservableList<Panier> panierItems = FXCollections.observableArrayList();
        // TODO: Retrieve the panier items from the database or somewhere else
        return panierItems;
    }
    
    // Add a medicament to the panier
    public void ajouterPanier(Medicament medicament) {
        // TODO: Add the medicament to the panier in the database or somewhere else
        // Then update the panier table view
        panierlist.setItems(getPanierItems());
    }
    
    public void ajouterp(Medicament medicament) {
        // Create a new Panier object using the Medicament details
        Panier panierItem = new Panier();
        panierItem.setId_medica(medicament.getId());
        panierItem.setNom(medicament.getNom());
        panierItem.setPrix(medicament.getPrix());
        panierItem.setQte(1); // Default quantity is 1
        
        // Add the Panier object to the panier list
        //panier.add(panierItem);
    }
    @FXML
    private void returnButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shop.fxml"));
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
