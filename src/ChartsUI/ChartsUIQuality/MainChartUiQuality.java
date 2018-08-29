package ChartsUI.ChartsUIQuality;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainChartUiQuality extends Application {


    private static Stage primaryStage;

    private static StackPane rootLayout;


    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        //init window
        this.primaryStage = primaryStage;

        initWindow();
    }

    public static void initWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainChartUiQuality.class.getResource("ChartUiQuality.fxml"));

        try {
            if(primaryStage == null)
                primaryStage = new Stage();
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(10);
        }

        primaryStage.setTitle("Chart Accuracy/Quality");
        primaryStage.setResizable(false);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
