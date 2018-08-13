package ChartUI;

import CamerasUIWindow.MainWindow;
import CamerasUIWindow.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;

public class MainTest extends Application {


    private Stage primaryStage;

    private AnchorPane rootLayout;


    public static void main(String[] args) {

        //load openCv lib
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        //init window
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ChartUi");

        initWindow();
    }

    public void initWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainTest.class.getResource("ChartUi.fxml"));

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(10);
        }

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
