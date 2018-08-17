package CamerasUIWindow;

import javafx.application.Application;


import javafx.stage.Stage;
import org.opencv.core.Core;


public class MainWindow extends Application {

    public static void main(String[] args) {

        //load openCv lib
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        launch(GUIs.class, args);
    }

    @Override
    public void start(Stage primaryStage) { }

}
