package ChartsUI.Dots;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class DotsOfChartQuality implements Dots {

    private ArrayList<XYChart.Series> accuracyQualityOfVideo;

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

    @Override
    public void addParameters(String[] strings) {
        parameters.add(strings);
    }
}
