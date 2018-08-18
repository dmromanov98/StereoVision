package ChartsUIDistance;

import CamerasUIWindow.MainWindowController;
import Dots.DotsOfChartDistance;
import Utils.Timer;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
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
    private StackPane stackPane;

    private static MainWindowController mwc;

    public static void setMwc(MainWindowController mwc) {
        ChartUIConroller.mwc = mwc;
    }

    private int startDistance = 15;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        infoTextLabel.setText("Set the starting distance between the cameras in the main window, enter the measuring " +
                "step in the current window (default = 5) (the distance to which you will move the object)" +
                ",enter number of measurements (default = 5) and after click the button Start measuring, start distance from" +
                "cameras = " + startDistance + ", set your object on distance = " + startDistance);

        stepField.setText("5");
        colMeasurements.setText("5");
        Timer.setChartUic(this);

    }


    private int colMeasurement = 0;
    private int step;
    private ArrayList<XYChart.Series> series = new ArrayList<>();
    private double distanceBetweenCameras;
    private double accuracy;
    private double coordsResult = 0;
    DotsOfChartDistance dotsOfChartDistance = new DotsOfChartDistance();


    public void addDotToSeries() {
        System.out.println(startDistance+" "+coordsResult);
        accuracy = (double) startDistance / coordsResult;
        System.out.println(accuracy);
        series.get(series.size()-1).getData().add(new XYChart.Data(startDistance, accuracy));
        dotsOfChartDistance.addSeries(series.get(series.size()-1));

        startDistance = startDistance + step;
        infoTextLabel.setText("Now you must set distance from cameras to object = " + startDistance + " and click Start Measuring");

        if(colMeasurement == 0){
            startDistance = 15;
            infoTextLabel.setText("Set the starting distance between the cameras in the main window, enter the measuring " +
                    "step in the current window (default = 5) (the distance to which you will move the object)" +
                    ",enter number of measurements (default = 5) and after click the button Start measuring, start distance from" +
                    "cameras = " + startDistance + ", set your object on distance = " + startDistance);
        }
    }

    public void coordinateCalculation() {
        if (coordsResult == 0) {
            coordsResult = Double.valueOf(mwc.getLblDistance1().getText());
        } else {
            coordsResult = (coordsResult + Double.valueOf(mwc.getLblDistance1().getText())) / 2;
        }
    }


    public void measurmentBtn() {

        if (colMeasurement == 0) {

            if (testOnNumber(colMeasurements.getText())) {
                if (testOnNumber(stepField.getText())) {
                    System.out.println("here !!! ");
                    series.add(new XYChart.Series());

                    distanceBetweenCameras = Double.valueOf(mwc.getDistanceBetweenCamerasField().getText());

                    series.get(series.size()-1).setName(String.valueOf(distanceBetweenCameras) + " cm");
                    chart.getData().add(series.get(series.size()-1));


                    step = Integer.valueOf(stepField.getText());
                    colMeasurement = Integer.valueOf(colMeasurements.getText());

                } else {
                    colMeasurement = 0;
                    mwc.dialogWindow("Step(cm)", "Must be a number!", stackPane);
                }
            } else {
                mwc.dialogWindow("Number of measurements", "Must be a number!", stackPane);
            }
        }

        if (colMeasurement != 0) {
            Thread timer = new Thread(new Timer());

            timer.start();
            infoTextLabel.setText("Please wait...");

            colMeasurement--;
        }

    }
}
