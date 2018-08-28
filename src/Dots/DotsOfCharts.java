package Dots;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class DotsOfCharts {

    private ArrayList<XYChart.Series> accuracyDistanceBetweenCameras;

    //0 - distance between cameras
    //1 - Focal Length
    //2 - Quality of video
    //3 - Staff update period
    private ArrayList<String[]> accuracyDBCProperties;

    private ArrayList<XYChart.Series> accuracyQualityOfVideo;
    private ArrayList<String[]> accuracyQOVProperties;

    private ArrayList<XYChart.Series> accuracyStaffUpdatePeriod;
    private ArrayList<String[]> accuracySUPProperties;

    public DotsOfCharts() {
        accuracyDistanceBetweenCameras = new ArrayList<>();
        accuracyQualityOfVideo = new ArrayList<>();
        accuracyStaffUpdatePeriod = new ArrayList<>();
    }

    /**
     * Creating new series of accuracyDistanceBetweenCameras and setting name of this series
     * @param name
     */
    public void setAndAddNameOfCurrentSeriesOfADBC(String name) {
        accuracyDistanceBetweenCameras.add(new XYChart.Series());
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size()).setName(name);
    }

    /**
     * Creating new series of accuracyQualityOfVideo and setting name of this series
     * @param name
     */
    public void setAndAddNameOfCurrentSeriesOfAQOV(String name) {
        accuracyQualityOfVideo.add(new XYChart.Series());
        accuracyQualityOfVideo.get(accuracyQualityOfVideo.size()).setName(name);
    }

    /**
     * Creating new series of accuracyStaffUpdatePeriod and setting name of this series
     * @param name
     */
    public void setAndAddNameOfCurrentSeriesOfSUP(String name) {
        accuracyStaffUpdatePeriod.add(new XYChart.Series());
        accuracyStaffUpdatePeriod.get(accuracyStaffUpdatePeriod.size()).setName(name);
    }

    public void addDotToAccuracyDistanceBetweenCameras(double x, double y) {
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size()).getData().add(new XYChart.Data( x, y));
    }

    public void addDotToAccuracyDistanceBetweenCameras(XYChart.Data dot) {
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size()).getData().add(dot);
    }

    public void addDotToAccuracyQualityOfVideo(double x, double y) {
        accuracyQualityOfVideo.get(accuracyQualityOfVideo.size()).getData().add(new XYChart.Data( x, y));
    }

    public void addDotToAccuracyQualityOfVideo(XYChart.Data dot) {
        accuracyQualityOfVideo.get(accuracyQualityOfVideo.size()).getData().add(dot);
    }

    public void addDotToAccuracyStaffUpdatePeriod(double x, double y) {
        accuracyStaffUpdatePeriod.get(accuracyStaffUpdatePeriod.size()).getData().add(new XYChart.Data( x, y));
    }

    public void addDotToAccuracyStaffUpdatePeriod(XYChart.Data dot) {
        accuracyStaffUpdatePeriod.get(accuracyStaffUpdatePeriod.size()).getData().add(dot);
    }

    public ArrayList<XYChart.Series> getAccuracyDistanceBetweenCameras() {
        return accuracyDistanceBetweenCameras;
    }

    public ArrayList<XYChart.Series> getAccuracyQualityOfVideo() {
        return accuracyQualityOfVideo;
    }

    public ArrayList<XYChart.Series> getAccuracyStaffUpdatePeriod() {
        return accuracyStaffUpdatePeriod;
    }


}
