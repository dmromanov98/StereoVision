package ChartsUI.Dots;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesOfChartDistance implements Dots {

    private ArrayList<SeriesOfDots> accuracyDistanceBetweenCameras;



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

    public SeriesOfChartDistance() {
        accuracyDistanceBetweenCameras = new ArrayList<>();
        parameters = new ArrayList<>();
    }

    @Override
    public void setNameOfNewSeries(String name) {

    }

    public void addParameters(String[] strings){
        parameters.add(strings);
    }

    @Override
    public void addDot(double x, double y) {

    }

    public void addSeries(SeriesOfDots series){
        accuracyDistanceBetweenCameras.add(series);
    }

    @Override
    public void addDot(XYChart.Data dot) {

    }

    @Override
    public void addSeries(Series series) {

    }

    @Override
    public void setSeries(ArrayList<Series> series) {

    }

    @Override
    public ArrayList<Series> getSeries() {
        List<Series> series = new ArrayList<>();
        for(SeriesOfDots sod: accuracyDistanceBetweenCameras){
            Series series1 = new Series();
            for(String dots:sod.getDots()){
                String[] strings = dots.split(" ");
                series1.setName(sod.getNameOfSeries());
                series1.getData().add(Integer.parseInt(strings[0]),strings[1]);
                series.add(series1);
            }
        }
        return (ArrayList<Series>) series;
    }


}
