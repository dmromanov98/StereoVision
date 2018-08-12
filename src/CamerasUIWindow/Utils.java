package CamerasUIWindow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javafx.fxml.JavaFXBuilderFactory;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.swing.*;

import static org.opencv.core.CvType.CV_8UC3;

/**
 * Provide general purpose methods for handling OpenCV-JavaFX data conversion.
 * Moreover, expose some "low level" methods for matching few JavaFX behavior.
 *
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 * @author <a href="http://max-z.de">Maximilian Zuleger</a>
 * @version 1.0 (2016-09-17)
 * @since 1.0
 */
public final class Utils {
    /**
     * Convert a Mat object (OpenCV) in the corresponding Image for JavaFX
     *
     * @param frame the {@link Mat} representing the current frame
     * @return the {@link Image} to show
     */
    public static Image mat2Image(Mat frame) {
        try {
            return SwingFXUtils.toFXImage(matToBufferedImage(frame), null);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot convert the Mat obejct: " + e);
            return null;
        }
    }

    /**
     * Generic method for putting element running on a non-JavaFX thread on the
     * JavaFX thread, to properly update the UI
     *
     * @param property a {@link ObjectProperty}
     * @param value    the value to set for the given {@link ObjectProperty}
     */
    public static <T> void onFXThread(final ObjectProperty<T> property, final T value) {
        Platform.runLater(() -> {
            property.set(value);
        });
    }

    /**
     * Support for the {@link mat2image()} method
     *
     * @param original the {@link Mat} object in BGR or grayscale
     * @return the corresponding {@link BufferedImage}
     */
    private static BufferedImage matToBufferedImage(Mat original) {
        // init
        BufferedImage image = null;
        int width = original.width(), height = original.height(), channels = original.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        original.get(0, 0, sourcePixels);

        if (original.channels() > 1) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        }
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

        return image;
    }

    public static Mat getScaledImage(Mat mat, int w, int h) {
        Mat newMat = new Mat();
        int iNewMat = 0;
        int jNewMat = 0;

        newMat.create(w, h, CV_8UC3);

        int hStep = mat.height() / h;
        int wStep = mat.width() / w;

        for (int i = 0; i < mat.height(); i = i + hStep) {
            for (int j = 0; j < mat.width(); j = j + wStep) {
                double[] newPixel = new double[3];
                newPixel[0] = (int) ((mat.get(i, j)[0] + mat.get(i + 1, j)[0] + mat.get(i, j + 1)[0] + mat.get(i + 1, j + 1)[0]) / (hStep + wStep));
                newPixel[1] = (int) ((mat.get(i, j)[1] + mat.get(i + 1, j)[1] + mat.get(i, j + 1)[1] + mat.get(i + 1, j + 1)[1]) / (hStep + wStep));
                newPixel[2] = (int) ((mat.get(i, j)[2] + mat.get(i + 1, j)[2] + mat.get(i, j + 1)[2] + mat.get(i + 1, j + 1)[2]) / (hStep + wStep));
                newMat.put(iNewMat, jNewMat, newPixel);
                System.out.println(newPixel[0]+" "+newPixel[1]+" "+newPixel[2]);
                //System.out.println(mat.get(i,j)[0]+" "+mat.get(i,j)[1]+" "+mat.get(i,j)[2]);
                //newMat[iNewMat][jNewMat] = (mat.get(i,j)+mat.get(i+1,j)+mat.get(i,j+1)+mat.get(i+1,j+1))/(hStep+wStep);

            }
        }
        //System.out.println("hStep = "+hStep+" wStep = "+wStep);
        return newMat;
    }


    public static Image getScaledImage(Image img, int width, int height) {

        BufferedImage bufImage = SwingFXUtils.fromFXImage(img, null);
        java.awt.Image swImage = bufImage.getScaledInstance(width,height,1);
        java.awt.Image tmp = swImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        Image res = SwingFXUtils.toFXImage(resized, null);

        return res;
    }

    public static Mat bufferedImage2Mat(BufferedImage in)
    {
        Mat out;
        byte[] data;
        int r, g, b;
        int height = in.getHeight();
        int width = in.getWidth();
        if(in.getType() == BufferedImage.TYPE_INT_RGB || in.getType() == BufferedImage.TYPE_INT_ARGB)
        {
            out = new Mat(height, width, CvType.CV_8UC3);
            data = new byte[height * width * (int)out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
            for(int i = 0; i < dataBuff.length; i++)
            {
                data[i*3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i*3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
            }
        }
        else
        {
            out = new Mat(height, width, CvType.CV_8UC1);
            data = new byte[height * width * (int)out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
            for(int i = 0; i < dataBuff.length; i++)
            {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
            }
        }
        out.put(0, 0, data);
        return out;
    }

    public static String getOpenCvResource(Class<?> clazz, String path) {
        try {
            return Paths.get( clazz.getResource(path).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Convert image to Mat
    // alternate version http://stackoverflow.com/questions/21740729/converting-bufferedimage-to-mat-opencv-in-java
    public static Mat bufferedImage2Mat_v2(BufferedImage im) {

        im = toBufferedImageOfType(im, BufferedImage.TYPE_3BYTE_BGR);

        // Convert INT to BYTE
        //im = new BufferedImage(im.getWidth(), im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        // Convert bufferedimage to byte array
        byte[] pixels = ((DataBufferByte) im.getRaster().getDataBuffer()).getData();

        // Create a Matrix the same size of image
        Mat image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
        // Fill Matrix with image values
        image.put(0, 0, pixels);

        return image;

    }

    private static BufferedImage toBufferedImageOfType(BufferedImage original, int type) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }

        // Don't convert if it already has correct type
        if (original.getType() == type) {
            return original;
        }

        // Create a buffered image
        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), type);

        // Draw the image onto the new buffer
        Graphics2D g = image.createGraphics();
        try {
            g.setComposite(AlphaComposite.Src);
            g.drawImage(original, 0, 0, null);
        }
        finally {
            g.dispose();
        }

        return image;
    }

}