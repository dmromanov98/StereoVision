package WorkWithImages;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ColorWork {

    public static int[] getARGB(int pixel) {
        int[] argb = new int[4];
        argb[0] = (pixel >> 24) & 0xff; // alpha

        argb[3] = pixel & 0xff; // blue
        argb[2] = (pixel >> 8) & 0xff; // green
        argb[1] = (pixel >> 16) & 0xff; // red

        return argb;
    }


//    public static void compareTwoImages(CompareImagesController cic, BufferedImage imageL, BufferedImage imageR) {
//
//        for (int i = 0; i < imageL.getWidth(); i++) {
//            for (int j = 0; j < imageL.getHeight(); j++) {
//
//                for (int i1 = 0; i1 < imageR.getWidth()/2; i1++) {
//                    for (int j1 = 0; j1 < imageR.getWidth()/2; j1++) {
//                        if (imageL.getRGB(i, j) != imageR.getRGB(i1, j1)) {
//                            imageL.setRGB(i, j, 00000000);
//                            imageR.setRGB(i1, j1, 00000000);
//                            Image imageLeft = SwingFXUtils.toFXImage(imageL, null);
//                            Image imageRight = SwingFXUtils.toFXImage(imageR, null);
//                            toWindow(cic,imageLeft,imageRight);
//                        }
//                    }
//                }
//            }
//
//        }
//    }

//    public static void toWindow(CompareImagesController cic, Image imageLeft, Image imageRight) {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                cic.setImages(imageLeft,imageRight);
//            }
//        });
//    }

}
