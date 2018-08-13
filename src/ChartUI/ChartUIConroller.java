package ChartUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartUIConroller implements Initializable {
    @FXML
    private StackedAreaChart chart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series = new XYChart.Series();
        series.setName("My");
        series.getData().add(new XYChart.Data( 0, 567));
        series.getData().add(new XYChart.Data( 1, 612));
        series.getData().add(new XYChart.Data( 2, 800));
        series.getData().add(new XYChart.Data( 3, 780));
        series.getData().add(new XYChart.Data( 4, 650));
        series.getData().add(new XYChart.Data( 5, 610));
        series.getData().add(new XYChart.Data( 6, 590));
        chart.getData().add(series);

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Mobile");

        dataSeries2.getData().add(new XYChart.Data( 0, 101));
        dataSeries2.getData().add(new XYChart.Data( 1, 110));
        dataSeries2.getData().add(new XYChart.Data( 2, 140));
        dataSeries2.getData().add(new XYChart.Data( 3, 132));
        dataSeries2.getData().add(new XYChart.Data( 4, 115));
        dataSeries2.getData().add(new XYChart.Data( 5, 109));
        dataSeries2.getData().add(new XYChart.Data( 6, 105));

        chart.getData().add(dataSeries2);
    }
}
