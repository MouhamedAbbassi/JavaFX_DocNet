package gui;

import Entity.Role;
import Entity.User;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class addEvent {

    @FXML
    private DatePicker date;

    @FXML
    private TextField desc;

    @FXML
    private TextField etcapacite;

    @FXML
    private TextField etnom;

    @FXML
    private TextField local;

    @FXML
    private MenuButton categorie;
    @FXML
    private TextField prix;
    private Button savebtn;
    @FXML
    private Button cancelbtn;
    @FXML
    private Button save;

    @FXML
    void saveEvent(MouseEvent event) throws SQLException, IOException {
       DBHelper dbHelper = new DBHelper();
       int  capacity = Integer.parseInt(etcapacite.getText().trim());
       double price = Double.parseDouble(prix.getText().trim());
        String location = local.getText().trim();
         String description = desc.getText().trim();
          String eventName = etnom.getText().trim();
            
              LocalDate eventDate = date.getValue();
               
               Window owner = save.getScene().getWindow();
                Event e = new Event(eventName, capacity, location, eventDate.toString(), price, description);
                dbHelper.register(e);
                
                 Parent root = FXMLLoader.load(getClass().getResource("AllEvents.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();     

    }
 
            private boolean validateString(TextField string) {
        Pattern p = Pattern.compile("[a-zA-Z0-9_]+");
        Matcher m = p.matcher(string.getText());
        if ((string.getText().length() == 0)
                || (!m.find() && m.group().equals(string.getText()))) {
            new animatefx.animation.Shake(string).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            string.setEffect(in);
            return false;
        } else {
            string.setEffect(null);
            return true;
        }
    }


}
