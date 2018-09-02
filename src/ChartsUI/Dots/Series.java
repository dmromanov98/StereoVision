package ChartsUI.Dots;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public interface Series {

    ArrayList<XYChart.Series> getSeries();
    void addSeries(SeriesOfDots seriesOfDots);

}
