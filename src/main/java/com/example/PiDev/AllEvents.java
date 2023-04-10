package com.example.PiDev;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AllEvents implements Initializable {
    @FXML
    private TableColumn<EventModel, Integer> id;

    @FXML
    private TableColumn<EventModel, String> nom;

    @FXML
    private TableColumn<EventModel, Double> prix;
    @FXML
    private TableColumn<EventModel, String> local;
    @FXML
    private TableView<EventModel> table;

    @FXML
    private Button deleteEventBtn;

    @FXML
    private Button updateEventBtn;
    @FXML
    private Button save;

    @FXML
    private TextField upcapacite;

    @FXML
    private DatePicker update;

    @FXML
    private TextField updescription;

    @FXML
    private TextField uplocal;

    @FXML
    private TextField upnom;

    @FXML
    private TextField upprix;

    private   EventModel item;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().idProperty().asObject());
        nom.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().nomProperty());
        prix.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().prixProperty().asObject());
        local.setCellValueFactory(cell -> cell.getValue().localProperty());
        upcapacite.setVisible(false);
        update.setVisible(false);
        upprix.setVisible(false);
        uplocal.setVisible(false);
        upnom.setVisible(false);
        updescription.setVisible(false);
        save.setVisible(false);
        getAll();
    }

    void getAll() {
    DBHelper dbHelper = new DBHelper();
    ObservableList<EventModel> events = FXCollections.observableList(dbHelper.getAllEvents());
        table.setItems(events);
}
    @FXML
    void deleteEvent(){
         var cell =  table.getFocusModel().getFocusedCell();
         var id = table.getItems().get(cell.getRow());
        System.out.println(id);
        DBHelper helper = new DBHelper();
        helper.deleteEvent(id.getId());
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        VBox dialogBox = new VBox();
        dialogBox.setAlignment(Pos.CENTER);
        Label dialogLabel = new Label("Event deleted Successfully !");
        Button closeButton = new Button("OK");
        closeButton.setOnAction(closeEvent -> dialogStage.close());
        dialogBox.getChildren().addAll(dialogLabel, closeButton);
        Scene dialogScene = new Scene(dialogBox, 200, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.show();

        getAll();
    }

    @FXML
    void updateEvent(){
        var cell =  table.getFocusModel().getFocusedCell();
          item = table.getItems().get(cell.getRow());
        System.out.println(item);
        DBHelper helper = new DBHelper();
        upcapacite.setText(String.valueOf(item.getCapacite()));
        upcapacite.setVisible(true);
        update.setVisible(false);
        upprix.setText(String.valueOf(item.getPrix()));
        upprix.setVisible(true);
        uplocal.setText(item.getLocal());
        uplocal.setVisible(true);
        upnom.setText(item.getNom());
        upnom.setVisible(true);
        updescription.setText(item.getDescription());
        updescription.setVisible(true);
        updateEventBtn.setVisible(false);
        save.setVisible(true);

    }


    @FXML
    void saveEvent() {
        DBHelper dbHelper = new DBHelper();
        Event e = new Event(
                item.getId(),
                upnom.getText(),
                Integer.parseInt(upcapacite.getText()),
                uplocal.getText(),
                item.getDate(),
                Double.parseDouble(upprix.getText()),
                updescription.getText(), item.getCategorie());
        dbHelper.updateEvent(e); ;

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        VBox dialogBox = new VBox();
        dialogBox.setAlignment(Pos.CENTER);
        Label dialogLabel = new Label("Event updated Successfully !");
        Button closeButton = new Button("OK");
        closeButton.setOnAction(closeEvent -> {dialogStage.close();
            upcapacite.setVisible(false);
            update.setVisible(false);
            upprix.setVisible(false);
            uplocal.setVisible(false);
            upnom.setVisible(false);
            updescription.setVisible(false);
            save.setVisible(false);
            updateEventBtn.setVisible(true);
            getAll();
        });
        dialogBox.getChildren().addAll(dialogLabel, closeButton);
        Scene dialogScene = new Scene(dialogBox, 300, 400);
        dialogStage.setScene(dialogScene);
        dialogStage.show();

    }

}
