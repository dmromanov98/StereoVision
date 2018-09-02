package ChartsUI.Dots;

import Utils.Utils;
import javafx.scene.chart.XYChart.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesOfChartDistance implements ChartsUI.Dots.Series {

    private List<SeriesOfDots> accuracyDistanceBetweenCameras;
    
    public SeriesOfChartDistance() {
        accuracyDistanceBetweenCameras = new ArrayList<>();
    }


    @Override
    public ArrayList<Series> getSeries() {
        return (ArrayList<Series>) Utils.StringArrayToSeries(accuracyDistanceBetweenCameras);
    }

    @Override
    public void addSeries(SeriesOfDots seriesOfDots) {
        this.accuracyDistanceBetweenCameras.add(seriesOfDots);
    }

}
