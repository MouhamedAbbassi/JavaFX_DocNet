package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;


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
    private TextField searchTxt;
    @FXML
    private TextField upprix;

    private   EventModel item;

    @FXML
    Label quote;
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
        try {
            getQuote();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getQuote() throws IOException {
        String u = "https://api.quotable.io/random";
        URL url = new URL(u);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        // Read the response from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        ObjectMapper objectMapper = new ObjectMapper();
        Qoute q = objectMapper.readValue(response.toString(),Qoute.class);


        quote.setText(q.getContent()+"\n"+q.getAuthor());
    }


    @FXML
    void findEvents(){
        String filter=this.searchTxt.getText().toString();
        System.out.println(filter);
        DBHelper dbHelper = new DBHelper();
        ObservableList<EventModel> events = FXCollections.observableList(dbHelper.findBy(filter));
        table.setItems(events);
    }
    @FXML
    void showQr() throws IOException {
        TablePosition cell =  table.getFocusModel().getFocusedCell();
        EventModel item = table.getItems().get(cell.getRow());
         Event e = new Event(item.getId(),
                    item.getNom(),
                item.getCapacite(),
                item.getLocal(),
                item.getDate(),
                item.getPrix(),
                item.getDescription(),
                item.getCategorie()
        );

         String u = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+ "L'EVENEMENT: "+"   "+e.getNom() +"    "+"DANS "+ e.getLocal() +"DE CAPACITE "+ "      "+e.getCapacite();
        URL url = new URL(u);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        // Read the response from the server
         InputStream in = con.getInputStream();

         Image image = new Image(in);
        System.out.println(con.getResponseMessage());
        System.out.println(con.getContent().toString());
        in.close();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        VBox dialogBox = new VBox();
        dialogBox.setAlignment(Pos.CENTER);
        Label dialogLabel = new Label("Scan ");
        Button closeButton = new Button("OK");
         ImageView img = new ImageView(image);
         img.setX(10);
         img.setY(10);
            img.setFitHeight(200);
            img.setFitHeight(200);
         closeButton.setOnAction(closeEvent -> dialogStage.close());
        dialogBox.getChildren().addAll( dialogLabel,img , closeButton);
        Scene dialogScene = new Scene(dialogBox, 400, 400);
        dialogStage.setScene(dialogScene);
        upcapacite.setVisible(false);
         upprix.setVisible(false);
        uplocal.setVisible(false);
        upnom.setVisible(false);
        updescription.setVisible(false);
         save.setVisible(false);
        dialogStage.show();

    }

    void getAll() {
    DBHelper dbHelper = new DBHelper();
    ObservableList<EventModel> events = FXCollections.observableList(dbHelper.getAllEvents());
        table.setItems(events);
}
    @FXML
    void deleteEvent(){
         TablePosition cell =  table.getFocusModel().getFocusedCell();
         EventModel id = table.getItems().get(cell.getRow());
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
        TablePosition cell =  table.getFocusModel().getFocusedCell();
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
