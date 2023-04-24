/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.User;
import Service.ServiceUser;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author muham
 */
public class ProfileAdminController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private FontAwesomeIcon logout;
    @FXML
    private AnchorPane addagent_form;
    @FXML
    private TableView<User> table_view;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prenom;
    @FXML
    private TableColumn<?, ?> telephone;
    @FXML
    private TableColumn<?, ?> mail;
    @FXML
    private TextField search;
    private TextField tnom;
    private TextField tprenom;
    private TextField temail;
    private TextField ttelephone;
    @FXML
    private Button ban;
    @FXML
    private TableColumn<?, ?> status;
    @FXML
    private Button showMedecin;
    @FXML
    private Button deban;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        addUserShowListData();
        addEmployeeSearch();

    }    


    @FXML
    private void logout(ActionEvent event) throws IOException
    {
     Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

 @FXML
        public void UserSelect() {
        User tab = table_view.getSelectionModel().getSelectedItem();
        int num = table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        
        tnom.setText(String.valueOf(tab.getNom()));
        tprenom.setText(String.valueOf(tab.getPrenom()));
        temail.setText(String.valueOf(tab.getEmail()));
        ttelephone.setText(String.valueOf(tab.getNumero()));
    }

    private ObservableList<User> addUsersList;
    public void addUserShowListData() {
        ServiceUser su = new ServiceUser();
        addUsersList = su.afficherPatient();

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("numero"));  
        status.setCellValueFactory(new PropertyValueFactory<>("baned"));

        

        table_view.setItems(addUsersList);

    }

    @FXML
    private void ban(MouseEvent event)
    {
    try{
             
                User bd_tab = table_view.getSelectionModel().getSelectedItem();
                int idUser =bd_tab.getId();
                User bd= new User(idUser);
                ServiceUser BC = new ServiceUser();
                BC.Ban(bd);
                addUserShowListData(); 
        
        }
                catch (Exception e) {
        }
    }

    @FXML
    private void showMedecin(MouseEvent event) throws IOException
    {
                 Parent root2 = FXMLLoader.load(getClass().getResource("medecinView.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root2);
                stage.setScene(scene);
                stage.show();
    }

    @FXML
    private void deban(MouseEvent event) 
    {
     try{
             
                User bd_tab = table_view.getSelectionModel().getSelectedItem();
                int idUser =bd_tab.getId();
                User bd= new User(idUser);
                ServiceUser BC = new ServiceUser();
                BC.DeBan(bd);
                addUserShowListData(); 
        
        }
                catch (Exception e) {
        }
    }
        public void addEmployeeSearch() {

        FilteredList<User> filter = new FilteredList<>(addUsersList, e -> true);

        search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateUserData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateUserData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getNom().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getPrenom().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getEmail().toLowerCase().contains(searchKey)) {
                    return true;
                }
                 else if (predicateUserData.getBaned().toLowerCase().contains(searchKey)) {
                    return true;
                }
                 else {
                    return false;
                }
            });
        });

        SortedList<User> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(table_view.comparatorProperty());
        table_view.setItems(sortList);
    }

    
    
}
