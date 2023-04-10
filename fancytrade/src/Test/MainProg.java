/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Entity.Rate;
import Entity.Reservation;

import Services.ServiceReservation;
import Services.ServiceNote;
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainProg extends Application {
    
    
    
    
     @Override
public void start(Stage primaryStage) throws Exception{
    //FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/displayRates.fxml"));
    //FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/Home.fxml"));
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/addRate.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
}
    public static void main(String[] args) throws IOException {
     launch(args);
       // ServiceReservation r=new ServiceReservation();
        //r.ajouter(new Reservation(1,2,3,LocalDate.of(2023, 2, 25),LocalDate.of(2023, 2, 26),"fairouz")); 
        
        //ServiceNote r2=new ServiceNote();
      //r2.ajouter(new Rate(1,1,1,"ok")); 
    //r2.modifier(new Rate(2,2,2,"bad"));
    
   //r2.supprimer(11);
       
   //System.out.println(r2.afficher());
       
    }

    
    
}
