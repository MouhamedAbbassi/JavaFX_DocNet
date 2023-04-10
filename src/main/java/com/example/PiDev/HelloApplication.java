package com.example.PiDev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DocNet");
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}