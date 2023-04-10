package com.example.PiDev;

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

    @FXML
    void saveEvent() {
        DBHelper dbHelper = new DBHelper();
        Event e = new Event(
        1,
                etnom.getText(),
                Integer.parseInt(etcapacite.getText()),
                local.getText(),
                date.getValue().toString(),
                Double.parseDouble(prix.getText()),
                desc.getText(), categorie.getText());
        dbHelper.saveEvent( e) ;
        etnom.clear();
        etcapacite.clear();
        local.clear();
        prix.clear();
        desc.clear();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        VBox dialogBox = new VBox();
        dialogBox.setAlignment(Pos.CENTER);
        Label dialogLabel = new Label("Event added Successfully !");
        Button closeButton = new Button("OK");
        closeButton.setOnAction(closeEvent -> dialogStage.close());
        dialogBox.getChildren().addAll(dialogLabel, closeButton);
        Scene dialogScene = new Scene(dialogBox, 200, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.show();


    }


}
