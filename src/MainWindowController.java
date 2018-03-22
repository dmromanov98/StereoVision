import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    ImageView cameraOne; //id = 0
    @FXML
    ImageView cameraTwo; //id = 1

    @FXML
    JFXButton buttonOne; //id = 0
    @FXML
    JFXButton buttonTwo; //id = 1
    @FXML
    StackPane stackPane;

    private FrameGrabber[] grabbers;
    private static byte[] camerasID;
    private static boolean[] camerasIsOnline;

    public static boolean[] getCamerasIsOnline() {
        return camerasIsOnline;
    }

    public static void setCamerasIsOnline(boolean[] camerasIsOnline) {
        MainWindowController.camerasIsOnline = camerasIsOnline;
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
        if (grabbers[0] != null) {
            grabbers[0].changeCamera();
        } else
            dialogWindow("First press the start button", "");
    }

    public void changeSecondCamera() {
        if (grabbers[1] != null) {
            grabbers[1].changeCamera();
        } else
            dialogWindow("First press the start button", "");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grabbers = new FrameGrabber[2];
        camerasID = new byte[]{0, 1};
        camerasIsOnline = new boolean[]{false, false};
        //cameraOne.fitWidthProperty().bind(stackPane.widthProperty());
        //cameraOne.fitHeightProperty().bind(stackPane.heightProperty());
    }

}
