package ChartsUI.ChartsUIQuality;

import CamerasUIWindow.MainWindowController;
import ChartsUI.ChartUiController;
import ChartsUI.Dots.Gson.Deserialization;
import ChartsUI.Dots.Gson.Serialization;
import ChartsUI.Dots.SeriesOfChartQuality;
import ChartsUI.Dots.SeriesOfDots;
import Utils.Timer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Utils.Test.testOnInteger;
import static Utils.Test.testOnDouble;

public class ChartUIQualityController implements ChartUiController {

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
        ChartUIQualityController.mwc = mwc;
    }

    private double startDistance = 15;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        infoTextLabel.setText("Set the quality of the video in the main window, enter the measuring " +
                "step in the current window (the distance to which you will move the object)" +
                ",enter number of measurements, Duration of measurement(Second), " +
                "Number of measurements for given time and after click the button Start measuring, start distance from" +
                "cameras = " + startDistance + ", set your object on distance = " + startDistance);

        stepField.setText("5");
        colMeasurements.setText("4");
        durationOfMeasurementField.setText("5");
        numberOfMeasurementsForGivenTimeField.setText("5");
        distanceFromCamerasToObjectField.setText("15");

        Timer.setChartUic(this);

    }

    private int colMeasurement = 0;
    private int step;
    private ArrayList<XYChart.Series> series = new ArrayList<>();
    private String qualityOfVideo;
    private double accuracy;
    private double cordsResult = 0;
    private SeriesOfChartQuality seriesOfChartQuality = new SeriesOfChartQuality();
    private SeriesOfDots seriesOfDots;
    private String finalStartDistance = "15";
    private String finalColMeasurement = "5";
    @Override
    public void addDotToSeries() {

        if (startDistance < cordsResult)
            accuracy = startDistance / cordsResult;
        else
            accuracy = cordsResult / startDistance;

        series.get(series.size() - 1).getData().add(new XYChart.Data(String.valueOf(startDistance), accuracy));

        seriesOfDots.addData(new XYChart.Data(String.valueOf(startDistance), accuracy));

        String[] parameters = new String[13];
        parameters[0] = String.valueOf(mwc.getScrollHueStart().getValue()) + " " + String.valueOf(mwc.getScrollHueStop().getValue());
        parameters[1] = String.valueOf(mwc.getScrollSaturationStart().getValue()) + " " + String.valueOf(mwc.getScrollSaturationStop().getValue());
        parameters[2] = String.valueOf(mwc.getScrollValueStart().getValue()) + " " + String.valueOf(mwc.getScrollValueStop().getValue());
        parameters[3] = mwc.getDistanceBetweenCamerasField().getText();
        parameters[4] = mwc.getFocalLengthField().getText();
        parameters[5] = qualityOfVideo;
        parameters[6] = mwc.getStaffUpdatePeriodField().getText();
        parameters[7] = (mwc.getCheckBoxAlgorithmOne().isSelected()) ? "1" : "2";
        parameters[8] = finalStartDistance;
        parameters[9] = String.valueOf(step);
        parameters[10] = finalColMeasurement;
        parameters[11] = String.valueOf(Timer.getDurationOfMeasurement());
        parameters[12] = String.valueOf(Timer.getNumberOfMeasurementsForGivenTime());

        seriesOfDots.addParameters(parameters);

        startDistance = startDistance + step;
        infoTextLabel.setText("Now you must set distance from cameras to object = " + startDistance + " and click Start Measuring");

        if (colMeasurement == 0) {
            seriesOfChartQuality.addSeries(seriesOfDots);
            setStartDistance();
            infoTextLabel.setText("Set the quality of the video in the main window, enter the measuring " +
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

                        if (mwc.getQualityComboBox().getSelectionModel().selectedItemProperty().getValue() != null)
                            qualityOfVideo = mwc.getQualityComboBox().getSelectionModel().selectedItemProperty().getValue();
                        else
                            qualityOfVideo = "480p";

                        seriesOfDots = new SeriesOfDots(qualityOfVideo);

                        finalStartDistance = String.valueOf(startDistance);
                        series.get(series.size() - 1).setName(qualityOfVideo);
                        chart.getData().add(series.get(series.size() - 1));

                        step = Integer.valueOf(stepField.getText());
                        colMeasurement = Integer.valueOf(colMeasurements.getText());
                        finalColMeasurement = String.valueOf(colMeasurement);
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

            infoTextLabel.setText("Set the quality of the video in the main window, enter the measuring " +
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
        fileChooser.setCurrentDirectory(new java.io.File("C:/"));
        fileChooser.setDialogTitle("Choose quality chart file");
        fileChooser.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
        }

        String fileName = fileChooser.getSelectedFile().getAbsolutePath();

        seriesOfChartQuality = Deserialization.deserializeQuality(fileName);

        for(XYChart.Series s : seriesOfChartQuality.getSeries()) {
            chart.getData().add(s);
        }

        mwc.setDistanceBetweenCameras();

        String[] parameters = seriesOfChartQuality.getAccuracyQualityOfVideo().
                get(seriesOfChartQuality.getAccuracyQualityOfVideo().size()-1).getLastParameters();


        distanceFromCamerasToObjectField.setText(parameters[8]);
        setStartDistance();
        stepField.setText(parameters[9]);
        colMeasurements.setText(parameters[10]);
        durationOfMeasurementField.setText(parameters[11]);
        numberOfMeasurementsForGivenTimeField.setText(parameters[12]);

        loadParameters(parameters);
    }

    public void loadParameters(String[] parameters) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.loadParameters(parameters);
            }
        });
    }

    @Override
    public void saveGraphs() {
        mwc.dialogWindow("Saving...", Serialization.serialize(seriesOfChartQuality), stackPane);
    }

}
