package WorkWithImages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompareImagesController implements Initializable {
    @FXML
    private ImageView imgL;
    @FXML
    private ImageView imgR;

    private Image imageLeft;
    private Image imageRight;
    private final String pathOfLeftImage = "testImages/imgL.jpg";
    private final String pathOfRighImage = "testImages/imgR.jpg";


    public void compareImages() {

        System.out.println("Info about left image :\n\tHEIGHT = " + imageLeft.heightProperty().getValue() +
                "\n\tWIDTH = " + imageLeft.widthProperty().getValue() + "\n" +
                "Info about right image :\n\tHEIGHT = " + imageRight.heightProperty().getValue() +
                "\n\tWIDTH = " + imageRight.widthProperty().getValue());

        try {
            BufferedImage imageL = ImageIO.read(new File(pathOfLeftImage));
            BufferedImage imageR = ImageIO.read(new File(pathOfRighImage));
            System.out.println("Images loaded to bufferedImages");



        } catch (IOException e) {
            System.err.println("Cant load images to buffered Images!\n" + e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            imageLeft = new Image("file:" + pathOfLeftImage);
            imageRight = new Image("file:" + pathOfRighImage);
            System.out.println("Images loaded!");
        } catch (Exception e) {
            System.err.println("Cant load images ! \n" + e);
        }

        imgL.setImage(imageLeft);
        imgR.setImage(imageRight);
    }

    public void setImages(Image left) {
        //System.out.println("here");
        imgL.setImage(left);
        //imgR.setImage(right);
    }


}
