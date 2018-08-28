package Dots;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import java.util.ArrayList;

public class DotsOfChartDistance implements Dots {

    private ArrayList<Series> accuracyDistanceBetweenCameras;

    public DotsOfChartDistance() {
        accuracyDistanceBetweenCameras = new ArrayList<>();
    }

    @Override
    public void setNameOfNewSeries(String name) {
        accuracyDistanceBetweenCameras.add(new Series());
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size() - 1).setName(name);
    }

    @Override
    public void addDot(double x, double y) {
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size() - 1).getData().add(new XYChart.Data(x, y));
    }

    @Override
    public void addDot(XYChart.Data dot) {
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size() - 1).getData().add(dot);
    }

    @Override
    public void addSeries(Series series) {
        accuracyDistanceBetweenCameras.add(series);
    }

    @Override
    public void setSeries(ArrayList<Series> series) {
        accuracyDistanceBetweenCameras = series;
    }

    @Override
    public ArrayList<Series> getSeries() {
        return accuracyDistanceBetweenCameras;
    }


}
