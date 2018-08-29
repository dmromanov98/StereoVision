package ChartsUI.Dots;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public interface Dots {

    void setNameOfNewSeries(String name);
    void addDot(double x, double y);
    void addDot(XYChart.Data dot);
    void addSeries(XYChart.Series series);
    void setSeries(ArrayList<XYChart.Series> series);
    ArrayList<XYChart.Series> getSeries();
    void addParameters(String[] strings);

}
