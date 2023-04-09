import Config.MaConnexion;
import Entity.User;

import Service.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {

     try
     {
         MaConnexion.getInstance();
      Parent root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/login.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
     }
     catch(IOException ex )
     {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    public static void main(String[] args)
    {launch(args);}

}