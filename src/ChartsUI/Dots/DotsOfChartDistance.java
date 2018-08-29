package ChartsUI.Dots;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import java.util.ArrayList;

public class DotsOfChartDistance implements Dots {

    private ArrayList<Series> accuracyDistanceBetweenCameras;

    /**
     *
     * This value stores the camera settings when you add each point
     *
     * Object definition parameters , style ("@scroll**.value @scroll**.value",example: "100 200"):
     * 0 - Hue
     * 1 - Saturation
     * 2 - Value
     *
     * Settings:
     * 3 - Distance between cameras
     * 4 - Focal length
     * 5 - Quality of video
     * 6 - Staff update period
     *
     */
    private ArrayList<String[]> parameters;

   // private boolean writeParameters = true;

    public DotsOfChartDistance() {
        accuracyDistanceBetweenCameras = new ArrayList<>();
        parameters = new ArrayList<>();
    }

    @Override
    public void setNameOfNewSeries(String name) {
        accuracyDistanceBetweenCameras.add(new Series());
        accuracyDistanceBetweenCameras.get(accuracyDistanceBetweenCameras.size() - 1).setName(name);
    }

    public void addParameters(String[] strings){
        parameters.add(strings);
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
