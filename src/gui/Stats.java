package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Stats implements Initializable {
    @FXML
    private StackedBarChart<String  , Number> chart;


    private ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBHelper dbHelper = new DBHelper();
        List<EventModel> events = dbHelper.getAllEvents();
        for (EventModel e:events){
            data.add(new XYChart.Data<>(e.getNom(), e.getCapacite()));
        }
        // Create a bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        chart.setTitle("Events Capacity");
        xAxis.setLabel("nom");
        yAxis.setLabel("capacity");
        // Add data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>(data);
        chart.getData().add(series);
    }
}
