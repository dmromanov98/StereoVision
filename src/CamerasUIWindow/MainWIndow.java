package CamerasUIWindow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import org.opencv.core.Core;

import javax.swing.*;
import java.io.IOException;

public class MainWIndow extends Application {


    private Stage primaryStage;

    private StackPane rootLayout;

    private static MainWindowController mwc;

    public static void main(String[] args) {

        //load openCv lib
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        launch(args);
    }

    public static void setMwc(MainWindowController mwc) {
        MainWIndow.mwc = mwc;
    }

    @Override
    public void start(Stage primaryStage) {

        //init window
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("StereoVisionWithOpenCv");

        initWindow();
    }

    public void initWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainWIndow.class.getResource("MainWindow.fxml"));

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(10);
        }

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(we -> {

            //TODO: IDK FOR WHAT IT
            System.out.println(mwc.getGrabbers()[0] + " " + mwc.getGrabbers()[1]);

            if (mwc.getGrabbers()[0] != null)
                mwc.startFirstCamera();

            if (mwc.getGrabbers()[1] != null)
                mwc.startSecondCamera();
        });


    }


}
