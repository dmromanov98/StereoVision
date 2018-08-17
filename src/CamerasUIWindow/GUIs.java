package CamerasUIWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIs extends Application {

    private static Stage primaryStage;
    private StackPane stackPane;
    private Scene scene;
    private static MainWindowController mwc;

    public static void setMwc(MainWindowController mwc) {
        GUIs.mwc = mwc;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        MainWindowController.setGuis(this);
        this.primaryStage.setTitle("StereoVisionWithOpenCv");
        initWindow();
    }

    public void initWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWindow.class.getResource("MainWindow.fxml"));

        try {
            if (stackPane != null)
                stackPane.getChildren().clear();
            else {
                stackPane = new StackPane();
                scene = new Scene(stackPane);
            }
            stackPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(10);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(we -> {

            //TODO: IDK FOR WHAT IT
            //System.out.println(mwc.getGrabbers()[0] + " " + mwc.getGrabbers()[1]);

            if (mwc.getGrabbers()[0] != null)
                mwc.startFirstCamera();

            if (mwc.getGrabbers()[1] != null)
                mwc.startSecondCamera();
        });
    }

}
