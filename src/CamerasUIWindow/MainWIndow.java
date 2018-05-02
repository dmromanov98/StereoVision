package CamerasUIWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;

public class MainWIndow extends Application {


    private Stage primaryStage;
    private StackPane rootLayout;

    private static MainWindowController mwc;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    public static void setMwc(MainWindowController mwc) {
        MainWIndow.mwc = mwc;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("StereoVisionWithOpenCv");
        initWindow();
    }

    public void initWindow() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWIndow.class.getResource("MainWindow.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(we -> {
                System.out.println(mwc.getGrabbers()[0]+" "+mwc.getGrabbers()[1]);

                if(mwc.getGrabbers()[0] != null)
                    mwc.startFirstCamera();

                if(mwc.getGrabbers()[1] != null)
                    mwc.startSecondCamera();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
