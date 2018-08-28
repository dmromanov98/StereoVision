package Dots;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class DotsOfChartQuality implements Dots {

    private ArrayList<XYChart.Series> accuracyQualityOfVideo;

    public DotsOfChartQuality(){
        this.accuracyQualityOfVideo = new ArrayList<>();
    }

    @Override
    public void setNameOfNewSeries(String name) {
        accuracyQualityOfVideo.add(new XYChart.Series());
        accuracyQualityOfVideo.get(accuracyQualityOfVideo.size() - 1).setName(name);
    }

    @Override
    public void addDot(double x, double y) {
        accuracyQualityOfVideo.get(accuracyQualityOfVideo.size() - 1).getData().add(new XYChart.Data(x, y));
    }

    @Override
    public void addDot(XYChart.Data dot) {
        accuracyQualityOfVideo.get(accuracyQualityOfVideo.size() - 1).getData().add(dot);
    }

    @Override
    public void addSeries(XYChart.Series series) {
        accuracyQualityOfVideo.add(series);
    }

    @Override
    public void setSeries(ArrayList<XYChart.Series> series) {
        accuracyQualityOfVideo = series;
    }

    @Override
    public ArrayList<XYChart.Series> getSeries() {
        return accuracyQualityOfVideo;
    }
}
