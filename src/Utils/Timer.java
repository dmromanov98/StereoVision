package Utils;

import ChartsUIDistance.ChartUIConroller;
import javafx.application.Platform;

public class Timer implements Runnable {

    public static void setChartUic(ChartUIConroller chartUic) {
        Timer.chartUic = chartUic;
    }

    private static ChartUIConroller chartUic;

    //TODO GIVE THIS PARAMETERS IN SETTINGS!
    private static int durationOfMeasurement = 5;
    private static int numberOfMeasurementsForGivenTime = 5;

    public void addDot() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chartUic.addDotToSeries();
            }
        });
    }

    public void coordinateCalculation() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chartUic.coordinateCalculation();
            }
        });
    }

    @Override
    public void run() {
        int time = 0;
        while(time<durationOfMeasurement*1000){
            if(time%((durationOfMeasurement/numberOfMeasurementsForGivenTime)*1000) == 0){
                //TODO SOMETHING
                coordinateCalculation();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time++;
        }
        addDot();
    }
}
