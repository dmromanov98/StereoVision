package ChartsUI.Dots;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class SeriesOfDots {

    /**
     * contains data in style : X Y
     */
    private List<String> dots;

    private String nameOfSeries;

    /**
     * This value stores the camera settings when you add each point
     * <p>
     * Object definition parameters , style ("@scroll**.value @scroll**.value",example: "100 200"):
     * 0 - Hue
     * 1 - Saturation
     * 2 - Value
     * <p>
     * Settings:
     * 3 - Distance between cameras
     * 4 - Focal length
     * 5 - Quality of video
     * 6 - Staff update period
     */
    private List<String[]> parameters;


    public SeriesOfDots(String nameOfSeries) {
        this.nameOfSeries = nameOfSeries;
        this.dots = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    public String getNameOfSeries() {
        return nameOfSeries;
    }

    public void addParameters(String[] parameters) {
        this.parameters.add(parameters);
    }

    public void setNameOfSeries(String nameOfSeries) {
        this.nameOfSeries = nameOfSeries;
    }

    public List<String> getDots() {
        return dots;
    }

    public void setDots(List<String> dots) {
        this.dots = dots;
    }

    public SeriesOfDots(ArrayList<String> dots) {
        this.dots = dots;
    }

    public SeriesOfDots() {
        dots = new ArrayList<>();
    }

    public void setSeries(XYChart.Series series) {
        ObservableList<XYChart.Data> dotsOfSeries = series.getData();
        nameOfSeries = series.getName();
        for (XYChart.Data d : dotsOfSeries)
            dots.add(d.getXValue().toString() + " " + d.getYValue().toString());
    }

    public void addData(XYChart.Data data) {
        dots.add(data.getXValue().toString() + " " + data.getYValue().toString());
    }

}