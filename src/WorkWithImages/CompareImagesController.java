package WorkWithImages;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
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

//            for(int i = 0;i<imageL.getWidth();i++){
//                for(int j = 0;j<imageL.getHeight();j++){
//                    System.out.println("A: "+ColorWork.getARGB(imageL.getRGB(i,j))[0]+"\t"+
//                            "R: "+ColorWork.getARGB(imageL.getRGB(i,j))[1]+"\t"+
//                            "G: "+ColorWork.getARGB(imageL.getRGB(i,j))[2]+"\t"+
//                            "B: "+ColorWork.getARGB(imageL.getRGB(i,j))[3]);
//
//                }
//
//            }
            compareTwoImages(imageL, imageR);

            System.out.println("Images loaded to bufferedImages");

        } catch (IOException e) {
            System.err.println("Cant load images to buffered Images!\n" + e);
        }

    }

    public void compareTwoImages(BufferedImage imageL, BufferedImage imageR) {

        for (int i = imageL.getWidth()/2; i < imageL.getWidth(); i++) {
            for (int j = imageL.getHeight()/2; j < imageL.getHeight(); j++) {

                for (int i1 = i; i1 < imageR.getWidth() / 2; i1++) {
                    for (int j1 = j; j1 < imageR.getHeight() / 2; j1++) {
                        if (imageL.getRGB(i, j) != imageR.getRGB(i1, j1)) {
                            imageL.setRGB(i, j, 0000000000);
                            //imageR.setRGB(i1, j1, 00000000);
                            Image imageLeft = SwingFXUtils.toFXImage(imageL, null);
                            //Image imageRight = SwingFXUtils.toFXImage(imageR, null);
                            setImages(imageLeft);
                        }
                    }
                }
            }

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
