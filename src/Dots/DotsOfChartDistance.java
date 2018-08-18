package Dots;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class DotsOfChartDistance {

    private ArrayList<XYChart.Series> accuracyDistanceBetweenCameras;

    public DotsOfChartDistance() {
        accuracyDistanceBetweenCameras = new ArrayList<>();
    }

    public void setNameOfNewSeries(String name) {
        accuracyDistanceBetweenCameras.add(new XYChart.Series());
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size()).setName(name);
    }

    public void addDot(double x, double y) {
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size()).getData().add(new XYChart.Data(x, y));
    }

    public void addDot(XYChart.Data dot) {
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size()).getData().add(dot);
    }

    public void addSeries(XYChart.Series series) {
        accuracyDistanceBetweenCameras.add(series);
    }

    public void setSeries(ArrayList<XYChart.Series> series) {
        accuracyDistanceBetweenCameras = series;
    }

    public ArrayList<XYChart.Series> getAccuracyDistanceBetweenCameras() {
        return accuracyDistanceBetweenCameras;
    }


}
