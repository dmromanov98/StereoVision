package ChartsUIDistance;

import CamerasUIWindow.MainWindowController;
import Utils.Timer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

import static Utils.Test.testOnNumber;

public class ChartUIConroller implements Initializable {
    @FXML
    private StackedAreaChart chart;

    @FXML
    private Label infoTextLabel;

    @FXML
    private JFXTextField stepField;

    @FXML
    private JFXTextField colMeasurements;

    @FXML
    private JFXButton measurementButton;

    @FXML
    private StackPane stackPane;

    private static MainWindowController mwc;

    public static void setMwc(MainWindowController mwc) {
        ChartUIConroller.mwc = mwc;
    }

    private int startDistance = 10;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        infoTextLabel.setText("Set the starting distance between the cameras in the main window, enter the measuring " +
                "step in the current window (default = 5) (the distance to which you will move the object)" +
                ",enter number of measurements (default = 5) and after click the button Start measuring, start distance from" +
                "cameras = " + startDistance + ", set your object on distance = " + startDistance);

        stepField.setText("5");
        colMeasurements.setText("5");
        Timer.setChartUic(this);

//        XYChart.Series series = new XYChart.Series();
//        series.setName("My");
//        series.getData().add(new XYChart.Data( 0, 567));
//        series.getData().add(new XYChart.Data( 1, 612));
//        series.getData().add(new XYChart.Data( 2, 800));
//        series.getData().add(new XYChart.Data( 3, 780));
//        series.getData().add(new XYChart.Data( 4, 650));
//        series.getData().add(new XYChart.Data( 5, 610));
//        series.getData().add(new XYChart.Data( 6, 590));
//        chart.getData().add(series);
//
//        XYChart.Series dataSeries2 = new XYChart.Series();
//        dataSeries2.setName("Mobile");
//
//        dataSeries2.getData().add(new XYChart.Data( 0, 101));
//        dataSeries2.getData().add(new XYChart.Data( 1, 110));
//        dataSeries2.getData().add(new XYChart.Data( 2, 140));
//        dataSeries2.getData().add(new XYChart.Data( 3, 132));
//        dataSeries2.getData().add(new XYChart.Data( 4, 115));
//        dataSeries2.getData().add(new XYChart.Data( 5, 109));
//        dataSeries2.getData().add(new XYChart.Data( 6, 105));
//
//        chart.getData().add(dataSeries2);
    }


    private int colMeasurement = 0;
    private int step;
    private static XYChart.Series dotsSeries;
    private double distanceBetweenCameras;
    private double accuracy;
    private double coordsResult = 0;

    public void addDotToSeries(){
        accuracy = (double) startDistance/coordsResult;
        dotsSeries.getData().add(new XYChart.Data(accuracy,startDistance));
        //chart.getData().remove(dotsSeries);
        //chart.getData().
        chart.getData().add(dotsSeries);
        startDistance = startDistance + step;
        infoTextLabel.setText("Now you must set distance from cameras to object = "+startDistance+" and click Start Measuring");
    }

    public void coordinateCalculation(){
        if(coordsResult == 0){
            coordsResult = Double.valueOf(mwc.getLblDistance1().getText());
        }else{
            coordsResult = (coordsResult + Double.valueOf(mwc.getLblDistance1().getText()))/2;
        }
    }


    public void measurmentBtn() throws InterruptedException {
        if (measurementButton.getText().equals("Start Measuring")) {

            if (colMeasurement == 0) {

                dotsSeries = new XYChart.Series();
                distanceBetweenCameras = Double.valueOf(mwc.getDistanceBetweenCamerasField().getText());
                dotsSeries.setName(String.valueOf(distanceBetweenCameras)+" cm");
                //chart.getData().add(dotsSeries);

                if (testOnNumber(colMeasurements.getText())) {
                    colMeasurement = Integer.valueOf(colMeasurements.getText());
                } else {
                    mwc.dialogWindow("Number of measurements", "Must be a number!", stackPane);
                }

                if (testOnNumber(stepField.getText())) {
                    step = Integer.valueOf(stepField.getText());
                } else {
                    colMeasurement = 0;
                    mwc.dialogWindow("Step(cm)", "Must be a number!", stackPane);
                }

            }


            if (colMeasurement != 0) {
                Thread timer = new Thread(new Timer());

                timer.start();
                //timer.join();

                colMeasurement--;
            }
            if (colMeasurement == 0) {
                System.out.println("finish");
            }
        }
    }
}
