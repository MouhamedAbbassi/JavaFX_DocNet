package GUI;

import Entity.Rate;
import Services.ServiceNote;
import Utils.ConnexionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddRateController {
    
    @FXML
    private TextField medIdTextField;
    
    @FXML
    private TextField makeRateIdTextField;
    
    @FXML
    private TextField noteTextField;
    
    @FXML
    private TextField opinionTextField;
    
    @FXML
    private Button addRateButton;
    
    @FXML
private void handleAddRate() {
    // Get the values from the text fields
    int medId = Integer.parseInt(medIdTextField.getText());
    int makeRateId = Integer.parseInt(makeRateIdTextField.getText());
    int note = Integer.parseInt(noteTextField.getText());
    String opinion = opinionTextField.getText();
    
    // Insert the rate data into the database using the existing connection
    Rate rate=new Rate(medId,makeRateId,note,opinion);
    ServiceNote rateService = new ServiceNote();
    rateService.ajouter(rate);
}

    
}
