package Utils;

import ChartsUI.ChartUiController;
import javafx.application.Platform;

import javafx.scene.control.Label;


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


    private static int durationOfMeasurement = 5;

    public static int getDurationOfMeasurement() {
        return durationOfMeasurement;
    }

    public static int getNumberOfMeasurementsForGivenTime() {
        return numberOfMeasurementsForGivenTime;
    }

    private static int numberOfMeasurementsForGivenTime = 5;

    public static int startingTimer(int colMeasurement, Label infoTextLabel){
        if (colMeasurement != 0) {
            Thread timer = new Thread(new Timer());

            timer.start();
            infoTextLabel.setText("Please wait...");

            return colMeasurement-1;
        }else
            return 0;
    }

    public void addDotToChart() {
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
                coordinateCalculation();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time++;
        }
        addDotToChart();
    }
}
