package ChartsUI.Dots;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class SeriesOfChartQuality implements Series {

    public List<SeriesOfDots> getAccuracyQualityOfVideo() {
        return accuracyQualityOfVideo;
    }

    private List<SeriesOfDots> accuracyQualityOfVideo;

    public SeriesOfChartQuality() {
        this.accuracyQualityOfVideo = new ArrayList<>();
    }


    @Override
    public List<XYChart.Series> getSeries() {
        return (ArrayList<XYChart.Series>) Utils.Utils.StringArrayToSeries(accuracyQualityOfVideo);
    }


    @Override
    public void addSeries(SeriesOfDots seriesOfDots) {
        this.accuracyQualityOfVideo.add(seriesOfDots);
    }
}
