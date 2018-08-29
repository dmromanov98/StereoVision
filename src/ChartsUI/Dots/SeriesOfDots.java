package ChartsUI.Dots;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeriesOfDots {

    /**
     * contains data in style : X Y
     */
    private List<String> dots;
    private String nameOfSeries;

    public SeriesOfDots(String nameOfSeries) {
        this.nameOfSeries = nameOfSeries;
        this.dots = new ArrayList<>();
    }

    public String getNameOfSeries() {
        return nameOfSeries;
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
