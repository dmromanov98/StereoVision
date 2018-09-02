package ChartsUI.ChartsUIDistance;

import CamerasUIWindow.MainWindowController;
import ChartsUI.ChartUiController;
import ChartsUI.Dots.Gson.Deserialization;
import ChartsUI.Dots.Gson.Serialization;
import ChartsUI.Dots.SeriesOfChartDistance;
import ChartsUI.Dots.SeriesOfDots;
import Utils.Timer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.zip.ZipFile;

import static Utils.Test.testOnInteger;
import static Utils.Test.testOnDouble;

public class ChartUIDistanceController implements ChartUiController {
    @FXML
    private AreaChart chart;

    @FXML
    private Label infoTextLabel;

    @FXML
    private JFXTextField stepField;

    @FXML
    private JFXButton measurementButton;

    @FXML
    private JFXTextField colMeasurements;

    @FXML
    private JFXTextField distanceFromCamerasToObjectField;

    @FXML
    private JFXTextField durationOfMeasurementField;

    @FXML
    private JFXTextField numberOfMeasurementsForGivenTimeField;

    @FXML
    private StackPane stackPane;

    private static MainWindowController mwc;

    public static void setMwc(MainWindowController mwc) {
        ChartUIDistanceController.mwc = mwc;
    }

    private double startDistance = 15;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        infoTextLabel.setText("Set the distance between the cameras in the main window, enter the measuring " +
                "step in the current window (the distance to which you will move the object)" +
                ",enter number of measurements, Duration of measurement(Second), " +
                "Number of measurements for given time and after click the button Start measuring, start distance from" +
                "cameras = " + startDistance + ", set your object on distance = " + startDistance);

        stepField.setText("5");
        colMeasurements.setText("5");
        durationOfMeasurementField.setText("5");
        numberOfMeasurementsForGivenTimeField.setText("5");
        distanceFromCamerasToObjectField.setText("15");
        Timer.setChartUic(this);

    }

    private int colMeasurement = 0;
    private int step;
    private ArrayList<XYChart.Series> series = new ArrayList<>();
    private double distanceBetweenCameras;
    private double accuracy;
    private double cordsResult = 0;
    private SeriesOfChartDistance seriesOfChartDistance = new SeriesOfChartDistance();
    private SeriesOfDots seriesOfDots;

    @Override
    public void addDotToSeries() {

        if (startDistance < cordsResult)
            accuracy = startDistance / cordsResult;
        else
            accuracy = cordsResult / startDistance;

        series.get(series.size() - 1).getData().add(new XYChart.Data(String.valueOf(startDistance), accuracy));

        seriesOfDots.addData(new XYChart.Data(String.valueOf(startDistance), accuracy));


        String[] parameters = new String[7];
        parameters[0] = String.valueOf(mwc.getScrollHueStart().getValue()) + " " + String.valueOf(mwc.getScrollHueStop().getValue());
        parameters[1] = String.valueOf(mwc.getScrollSaturationStart().getValue()) + " " + String.valueOf(mwc.getScrollSaturationStop().getValue());
        parameters[2] = String.valueOf(mwc.getScrollValueStart().getValue()) + " " + String.valueOf(mwc.getScrollValueStop().getValue());
        parameters[3] = String.valueOf(distanceBetweenCameras);
        parameters[4] = mwc.getFocalLengthField().getText();

        if (mwc.getQualityComboBox().getSelectionModel().selectedItemProperty().getValue() != null)
            parameters[5] = mwc.getQualityComboBox().getSelectionModel().selectedItemProperty().getValue();
        else
            parameters[5] = "480p";

        parameters[6] = mwc.getStaffUpdatePeriodField().getText();

        seriesOfDots.addParameters(parameters);

        startDistance = startDistance + step;
        infoTextLabel.setText("Now you must set distance from cameras to object = " + startDistance + " and click Start Measuring");


        if (colMeasurement == 0) {
            seriesOfChartDistance.addSeries(seriesOfDots);
            startDistance = 15;
            infoTextLabel.setText("Set the distance between the cameras in the main window, enter the measuring " +
                    "step in the current window (the distance to which you will move the object)" +
                    ",enter number of measurements, Duration of measurement(Second), " +
                    "Number of measurements for given time and after click the button Start measuring, start distance from" +
                    "cameras = " + startDistance + ", set your object on distance = " + startDistance);
        }
    }

    @Override
    public void coordinateCalculation() {
        if (cordsResult == 0) {
            cordsResult = Double.valueOf(mwc.getLblDistance1().getText());
        } else {
            cordsResult = (cordsResult + Double.valueOf(mwc.getLblDistance1().getText())) / 2;
        }
    }

    @Override
    public void measurementBtn() {

        if (colMeasurement == 0) {

            if (testOnInteger(colMeasurements.getText()) &&
                    testOnInteger(stepField.getText()) &&
                    testOnInteger(durationOfMeasurementField.getText()) &&
                    testOnInteger(numberOfMeasurementsForGivenTimeField.getText())) {
                if (Integer.valueOf(durationOfMeasurementField.getText()) >= 0) {
                    if (Integer.valueOf(numberOfMeasurementsForGivenTimeField.getText()) > 0) {
                        Timer.setDurationOfMeasurement(Integer.valueOf(durationOfMeasurementField.getText()));
                        Timer.setNumberOfMeasurementsForGivenTime(Integer.valueOf(numberOfMeasurementsForGivenTimeField.getText()));

                        series.add(new XYChart.Series());

                        distanceBetweenCameras = Double.valueOf(mwc.getDistanceBetweenCamerasField().getText());

                        series.get(series.size() - 1).setName(String.valueOf(distanceBetweenCameras) + " cm");

                        seriesOfDots = new SeriesOfDots(String.valueOf(distanceBetweenCameras) + " cm");

                        chart.getData().add(series.get(series.size() - 1));

                        step = Integer.valueOf(stepField.getText());
                        colMeasurement = Integer.valueOf(colMeasurements.getText());
                        measurementButton.setText("Start Measuring");
                    } else {
                        mwc.dialogWindow("Error entering data!", "Number of measurements for given time" +
                                "Must be > 1", stackPane);
                    }
                } else {
                    mwc.dialogWindow("Error entering data!", "Duration of measurement(Second) " +
                            "Must be >0!", stackPane);
                }

            } else {
                mwc.dialogWindow("Error entering data!", "Step(cm), Number of measurements, " +
                        "Duration of measurement(Second), Number of measurements for given time" +
                        "Must be a number!", stackPane);
            }

        }

        colMeasurement = Timer.startingTimer(colMeasurement, infoTextLabel);

    }

    @Override
    public void setStartDistance() {
        if (testOnDouble(distanceFromCamerasToObjectField.getText()) &&
                Double.valueOf(distanceFromCamerasToObjectField.getText()) > 0) {

            startDistance = Double.valueOf(distanceFromCamerasToObjectField.getText());

            infoTextLabel.setText("Set the distance between the cameras in the main window, enter the measuring " +
                    "step in the current window (the distance to which you will move the object)" +
                    ",enter number of measurements, Duration of measurement(Second), " +
                    "Number of measurements for given time and after click the button Start measuring, start distance from" +
                    "cameras = " + startDistance + ", set your object on distance = " + startDistance);

        } else {
            mwc.dialogWindow("Error entering data!",
                    "Start distance from cameras to object must be a number > 0", stackPane);
        }
    }

    @Override
    public void loadGraphs() {
        JButton open = new JButton();
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new java.io.File("C:/"));
        fileChooser.setCurrentDirectory(new java.io.File("A:\\IdeaProjects\\StereoVision\\GraphsSaves\\D(02.09.2018).json"));
        fileChooser.setDialogTitle("Choose distance chart file");
        fileChooser.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
        }

        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println(fileName);
        seriesOfChartDistance = Deserialization.deserializeDistance(fileName);

        for(XYChart.Series s : seriesOfChartDistance.getSeries()) {
            //System.out.println(s.getName()+" "+s.getData());
            ObservableList<XYChart.Data> data = s.getData();
            //for(XYChart.Data d:s.getData())
            chart.getData().addAll(s);
        }

    }

    @Override
    public void saveGraphs() {
        mwc.dialogWindow("Saving...", Serialization.serialize(seriesOfChartDistance), stackPane);
    }
}
