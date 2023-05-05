errr/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Rate;
import Services.ServiceNote;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author selmi
 */
public class DisplayRatesController  implements Initializable {
    
    @FXML
    private Label medIdLabel;
    
    @FXML
    private Label makeRateIdLabel;
    
    @FXML
    private Label noteLabel;
    
    @FXML
    private Label opinionLabel;
    
 
    
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        ServiceNote serviceNote = new ServiceNote();
     
        ObservableList<Rate> WorkshopList = serviceNote.afficher();
        
        
        
    }
 
    }
 
    
    

