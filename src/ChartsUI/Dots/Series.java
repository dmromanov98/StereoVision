package ChartsUI.Dots;

import javafx.scene.chart.XYChart;

import java.util.List;

public interface Series {

    List<XYChart.Series> getSeries();
    void addSeries(SeriesOfDots seriesOfDots);

}
