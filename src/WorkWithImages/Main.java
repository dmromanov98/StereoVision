package WorkWithImages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import javax.swing.*;
import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;


    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ComparingImagesTest");
        initWindow();
    }

    public void initWindow() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("CompareImages.fxml"));

        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

}
