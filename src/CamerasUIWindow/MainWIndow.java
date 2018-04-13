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


    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
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
//            primaryStage.setOnCloseRequest(we -> {
//                MainWindowController.
//            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
