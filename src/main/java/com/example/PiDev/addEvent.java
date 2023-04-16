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

import java.time.LocalDate;

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
        // Validate event name
        String eventName = etnom.getText().trim();
        if (eventName.isEmpty()) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter event name");
            alert.showAndWait();
            return;
        }

// Validate event capacity
        int capacity = 0;
        try {
            capacity = Integer.parseInt(etcapacite.getText().trim());
            if (capacity <= 0) {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a positive integer for capacity");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Show error messages wow
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid integer for capacity");
            alert.showAndWait();
            return;
        }

// Validate event location
        String location = local.getText().trim();
        if (location.isEmpty()) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter event location");
            alert.showAndWait();
            return;
        }

// Validate event date
        LocalDate eventDate = date.getValue();
        if (eventDate == null) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select event date pleaaase");
            alert.showAndWait();
            return;
        }

// Validate event price
        double price = 0.0;
        try {
            price = Double.parseDouble(prix.getText().trim());
            if (price <= 0.0) {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a positive number for price");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number for price");
            alert.showAndWait();
            return;
        }

// Validate event description
        String description = desc.getText().trim();
        if (description.isEmpty()) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter event description");
            alert.showAndWait();
            return;
        }

// Validate event category
        String category = categorie.getText().trim();
        if (category.isEmpty()) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter event category");
            alert.showAndWait();
            return;
        }

// If all input is valid, create event object and save to database
        Event e = new Event(
                1,
                eventName,
                capacity,
                location,
                eventDate.toString(),
                price,
                description,
                category);

        dbHelper.saveEvent(e);

// Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event saved successfully");
        alert.showAndWait();


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
        Scene dialogScene = new Scene(dialogBox, 100, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.show();


    }


}
