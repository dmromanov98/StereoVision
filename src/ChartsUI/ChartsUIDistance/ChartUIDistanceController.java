package ChartsUI.ChartsUIDistance;

import CamerasUIWindow.MainWindowController;
import ChartsUI.ChartUiController;
import ChartsUI.Dots.Gson.Deserialization;
import ChartsUI.Dots.Gson.Serialization;
import ChartsUI.Dots.Series;
import ChartsUI.Dots.SeriesOfChartDistance;
import ChartsUI.Dots.SeriesOfDots;
import Utils.Timer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Utils.Test.testOnInteger;
import static Utils.Test.testOnDouble;

public class ChartUIDistanceController implements ChartUiController, Initializable {
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
    private String finalStartDistance = "15";
    private String finalColMeasurement = "5";

    @Override
    public void addDotToSeries() {

        if (startDistance < cordsResult)
            accuracy = startDistance / cordsResult;
        else
            accuracy = cordsResult / startDistance;

        System.out.println("CordsResult = " + cordsResult + " StartDistance = " + startDistance + " Accuracy = " + accuracy);

        cordsResult = 0;
        XYChart.Data<Number, Number> data = new XYChart.Data<>(startDistance, accuracy);
        series.get(series.size() - 1).getData().add(data);


        seriesOfDots.addData(data);
        data.getNode().setOnMouseClicked(e -> outInfoAboutData(data));

        String[] parameters = new String[13];
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
            seriesOfChartDistance.addSeries(seriesOfDots);
            setStartDistance();
            infoTextLabel.setText("Set the distance between the cameras in the main window, enter the measuring " +
                    "step in the current window (the distance to which you will move the object)" +
                    ",enter number of measurements, Duration of measurement(Second), " +
                    "Number of measurements for given time and after click the button Start measuring, start distance from" +
                    "cameras = " + startDistance + ", set your object on distance = " + startDistance);
        }
    }

    private void outInfoAboutData(XYChart.Data<Number, Number> data) {

        List<SeriesOfDots> seriesOfDotsH = seriesOfChartDistance.getAccuracyDistanceBetweenCameras();
        boolean found = false;
        for (int j = 0; j < seriesOfDotsH.size(); j++) {
            List<String> dots = seriesOfDotsH.get(j).getDots();
            for (int i = 0; i < dots.size(); i++) {
                String[] dotArr = dots.get(i).split(" ");
                if (dotArr[0].equals(data.getXValue().toString()) && dotArr[1].equals(data.getYValue().toString())) {
                    String params[] = seriesOfDotsH.get(j).getParameters().get(i);
                    System.out.println(params);
                    String info = "Hue : " + params[0] + "\n" +
                            "Saturation : " + params[1] + "\n" +
                            "Value : " + params[2] + "\n" +
                            "Distance between cameras : " + params[3] + "\n" +
                            "Focal length : " + params[4] + "\n" +
                            "Quality of video : " + params[5] + "\n" +
                            "Staff update period : " + params[6] + "\n" +
                            "Algorithm : " + params[7] + "\n" +
                            "Duration of measurement : " + params[11] + "\n" +
                            "Number of measurement for given time : " + params[12];

                    showInfoAboutDot(info, dotArr[0] + ";" + dotArr[1]);
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        if (!found) {
            List<String> dots = seriesOfDots.getDots();
            for (int i = 0; i < dots.size(); i++) {
                String[] dotArr = dots.get(i).split(" ");
                if (dotArr[0].equals(data.getXValue().toString()) && dotArr[1].equals(data.getYValue().toString())) {
                    String params[] = seriesOfDots.getParameters().get(i);
                    String info = "Hue : " + params[0] + "\n" +
                            "Saturation : " + params[1] + "\n" +
                            "Value : " + params[2] + "\n" +
                            "Distance between cameras : " + params[3] + "\n" +
                            "Focal length : " + params[4] + "\n" +
                            "Quality of video : " + params[5] + "\n" +
                            "Staff update period : " + params[6] + "\n" +
                            "Algorithm : " + params[7] + "\n" +
                            "Duration of measurement : " + params[11] + "\n" +
                            "Number of measurement for given time : " + params[12];

                    showInfoAboutDot(info, dotArr[0] + ";" + dotArr[1]);
                    found = true;
                    break;
                }
            }
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
                        finalStartDistance = String.valueOf(startDistance);
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

    public void showInfoAboutDot(String info, String dot) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.dialogWindow("Information about dot(" + dot + ")", info, stackPane);
            }
        });
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
        fileChooser.setCurrentDirectory(new java.io.File("A:\\IdeaProjects\\StereoVision\\GraphsSaves\\"));
        fileChooser.setDialogTitle("Choose distance chart file");
        fileChooser.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
        }

        String fileName = fileChooser.getSelectedFile().getAbsolutePath();

        for (SeriesOfDots s : Deserialization.deserializeDistance(fileName).getAccuracyDistanceBetweenCameras()) {
            seriesOfChartDistance.addSeries(s);
            XYChart.Series series = new XYChart.Series();
            chart.getData().add(series);
            s.getDots().forEach(dot -> {
                String dotArr[] = dot.split(" ");
                XYChart.Data data = new XYChart.Data(Double.valueOf(dotArr[0]), Double.valueOf(dotArr[1]));
                series.setName(s.getNameOfSeries());
                series.getData().add(data);
                data.getNode().setOnMouseClicked(event -> outInfoAboutData(data));
            });

        }

        String[] parameters = seriesOfChartDistance.getAccuracyDistanceBetweenCameras().
                get(seriesOfChartDistance.getAccuracyDistanceBetweenCameras().size() - 1).getLastParameters();


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
        mwc.dialogWindow("Saving...", Serialization.serialize(seriesOfChartDistance), stackPane);
    }
}
