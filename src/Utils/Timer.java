package Utils;

import javafx.application.Platform;

public class Timer implements Runnable {

    public static void setChartUic(ChartUiController chartUic) {
        Timer.chartUic = chartUic;
    }

    private static ChartUiController chartUic;

    public static void setDurationOfMeasurement(int durationOfMeasurement) {
        Timer.durationOfMeasurement = durationOfMeasurement;
    }

    public static void setNumberOfMeasurementsForGivenTime(int numberOfMeasurementsForGivenTime) {
        Timer.numberOfMeasurementsForGivenTime = numberOfMeasurementsForGivenTime;
    }

    //TODO GIVE THIS PARAMETERS IN SETTINGS!
    private static int durationOfMeasurement = 5;
    private static int numberOfMeasurementsForGivenTime = 5;

    public void addDot() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    chartUic.addDotToSeries();
                }catch (IndexOutOfBoundsException e){}
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

        if(durationOfMeasurement==0){
            coordinateCalculation();
        }

        while(time<durationOfMeasurement*1000){
            if(time%(((double)durationOfMeasurement/(double) numberOfMeasurementsForGivenTime)*1000) == 0){
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
