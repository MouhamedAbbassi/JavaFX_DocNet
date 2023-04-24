module com.example.sameh {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires java.mail;

    opens com.example.PiDev to javafx.fxml , com.google.gson , com.fasterxml.jackson.databind;
    exports com.example.PiDev;
}