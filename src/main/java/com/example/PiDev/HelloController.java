package com.example.PiDev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;

public class HelloController  {
    @FXML
    BorderPane main;

    @FXML
    void addNewEvent(MouseEvent event)   {
        showPage("add_event");

    }

    @FXML
    void allEvents( ) {
        showPage("AllEvents");

    }


    private void showPage(String page  )  {
        Parent loaded = null;
        try {
            loaded = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        main.setCenter(loaded);
    }

}