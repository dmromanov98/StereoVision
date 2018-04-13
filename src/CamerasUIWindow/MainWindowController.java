package CamerasUIWindow;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private ImageView cameraOne; //id = 0
    @FXML
    private ImageView cameraTwo; //id = 1

    @FXML
    private JFXButton buttonOne; //id = 0
    @FXML
    private JFXButton buttonTwo; //id = 1
    @FXML
    private StackPane stackPane;

    @FXML
    private JFXSlider scrollHueStart;
    @FXML
    private JFXSlider scrollHueStop;

    @FXML
    private JFXSlider scrollSaturationStart;
    @FXML
    private JFXSlider scrollSaturationStop;

    @FXML
    private JFXSlider scrollValueStart;
    @FXML
    private JFXSlider scrollValueStop;

    @FXML
    private ImageView mode2Camera11;
    @FXML
    private ImageView mode2Camera12;

    @FXML
    private ImageView mode2Camera21;
    @FXML
    private ImageView mode2Camera22;

    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    private JFXTextField distanceBetweenCamerasField;

    @FXML
    private JFXTextField focalLengthField;

    @FXML
    private Label lblDistance1;

    @FXML
    private Label lblDistance2;

    @FXML
    private Label lblDistance3;

    @FXML
    private JFXButton startShowDistanceButton;

    private DistanceThread distanceThread;
    private Thread distanceShow;
    protected static FrameGrabber[] grabbers;
    private static byte[] camerasID;


    public JFXSlider getScrollHueStart() {
        return scrollHueStart;
    }

    public JFXSlider getScrollHueStop() {
        return scrollHueStop;
    }

    public JFXSlider getScrollSaturationStart() {
        return scrollSaturationStart;
    }

    public JFXSlider getScrollSaturationStop() {
        return scrollSaturationStop;
    }

    public JFXSlider getScrollValueStart() {
        return scrollValueStart;
    }

    public JFXSlider getScrollValueStop() {
        return scrollValueStop;
    }

    public static byte[] getCamerasID() {
        return camerasID;
    }

    public static void setCamerasID(byte[] camerasID) {
        MainWindowController.camerasID = camerasID;
    }

    public void changeButtonText(String text, Byte buttonId) {

        if (buttonId == 0)
            buttonOne.setText(text);
        else
            buttonTwo.setText(text);

    }

    @FXML
    public void startFirstCamera() {

        if (grabbers[0] == null) {
            grabbers[0] = new FrameGrabber(camerasID[0], (byte) 0, this);
            grabbers[0].init();
        } else {
            startShowingDistance();
            grabbers[0].init();
            grabbers[0] = null;
        }

    }

    //инициализация окна Dialog
    public void dialogWindow(String cause, String message) {
        JFXDialogLayout content = new JFXDialogLayout();
        Text txt = new Text(cause);
        txt.setFont(Font.font("Verdana", 18));
        content.setHeading(txt);
        content.setBody(new Text(message));
        JFXButton btn = new JFXButton("Ok");
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        btn.setOnAction(event -> dialog.close());
        content.setActions(btn);
        dialog.show();
    }

    @FXML
    public void startSecondCamera() {

        if (grabbers[1] == null) {
            grabbers[1] = new FrameGrabber(camerasID[1], (byte) 1, this);
            grabbers[1].init();
        } else {
            startShowingDistance();
            grabbers[1].init();
            grabbers[1] = null;
        }

    }


    public void updateImageView(byte viewID, Image image) {
        if (viewID == 0)
            Utils.onFXThread(cameraOne.imageProperty(), image);
        else if (viewID == 1)
            Utils.onFXThread(cameraTwo.imageProperty(), image);
    }

    public void changeFirstCamera() {

    }

    public void changeSecondCamera() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grabbers = new FrameGrabber[2];
        camerasID = new byte[]{0, 1};
        scrollHueStart.setValue(0);
        scrollHueStop.setValue(180);
        scrollSaturationStart.setValue(0);
        scrollSaturationStop.setValue(255);
        scrollValueStart.setValue(0);
        scrollValueStop.setValue(255);

        distanceBetweenCamerasField.setText(String.valueOf(DistanceToTheObject.getDistanceBetweenCameras()));
        focalLengthField.setText(String.valueOf(DistanceToTheObject.getFocus()));

    }

    public void mode2ImageShow(String cam, Image image) {
        if (cam.equals("01"))
            Utils.onFXThread(mode2Camera11.imageProperty(), image);
        if (cam.equals("02"))
            Utils.onFXThread(mode2Camera12.imageProperty(), image);
        if (cam.equals("11"))
            Utils.onFXThread(mode2Camera21.imageProperty(), image);
        if (cam.equals("12"))
            Utils.onFXThread(mode2Camera22.imageProperty(), image);
    }


    public void setColor() {
        Color color = colorPicker.getValue();
        Mat mat = new Mat(1, 1, CvType.CV_8UC3,
                new Scalar(color.getRed() * 256, color.getGreen() * 256, color.getBlue() * 256));
        Mat hsv = new Mat();
        Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);
        double[] hcvValues = hsv.get(0, 0);

        System.out.println(mat.dump() + "\n" + hsv.dump());

        scrollHueStart.setValue(hcvValues[0]);
        scrollHueStop.setValue((int) hcvValues[0] * 4.8);

        scrollSaturationStart.setValue((int) hcvValues[1] * 0.52);
        scrollSaturationStop.setValue(hcvValues[1]);

        scrollValueStart.setValue((int) hcvValues[2] * 0.54);
        scrollValueStop.setValue(hcvValues[2]);
    }


    public void setDistanceBetweenCameras() {
        if (Test.testOnNumber(distanceBetweenCamerasField.getText()))
            DistanceToTheObject.setDistanceBetweenCameras(Double.valueOf(distanceBetweenCamerasField.getText()));
        else
            dialogWindow("Distance between cameras", "MUST BE A NUMBER!");
    }

    public void setFocalLength() {
        if (Test.testOnNumber(focalLengthField.getText()))
            DistanceToTheObject.setFocus(Double.valueOf(focalLengthField.getText()));
        else
            dialogWindow("Focal length", "MUST BE A NUMBER!");
    }

    public void startShowingDistance() {
        if (distanceThread == null && grabbers[0] != null && grabbers[1] != null) {
            distanceThread = new DistanceThread(this);
            distanceShow = new Thread(distanceThread);
            distanceShow.start();
            startShowDistanceButton.setText("Stop show distance");
        } else if (distanceShow != null && distanceThread != null) {
            distanceThread.setWork(false);
            distanceShow = null;
            distanceThread = null;
            startShowDistanceButton.setText("Start showing distance");
        }
    }

    public void showDistance(double distance) {
        lblDistance1.setText(String.valueOf(distance));
        lblDistance2.setText(String.valueOf(distance));
        lblDistance3.setText(String.valueOf(distance));
    }

}
