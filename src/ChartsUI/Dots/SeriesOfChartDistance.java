package ChartsUI.Dots;

import Utils.Utils;
import javafx.scene.chart.XYChart.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesOfChartDistance implements ChartsUI.Dots.Series {

    public List<SeriesOfDots> getAccuracyDistanceBetweenCameras() {
        return accuracyDistanceBetweenCameras;
    }

    private List<SeriesOfDots> accuracyDistanceBetweenCameras;
    
    public SeriesOfChartDistance() {
        accuracyDistanceBetweenCameras = new ArrayList<>();
    }


    @Override
    public List<Series> getSeries() {
        return Utils.StringArrayToSeries(accuracyDistanceBetweenCameras);
    }

    @Override
    public void addSeries(SeriesOfDots seriesOfDots) {
        this.accuracyDistanceBetweenCameras.add(seriesOfDots);
    }

    public SeriesOfChartDistance addSeriesToThis(SeriesOfChartDistance socd){
        for(SeriesOfDots sod : socd.getAccuracyDistanceBetweenCameras()){
            accuracyDistanceBetweenCameras.add(sod);
        }
        return this;
    }
}
