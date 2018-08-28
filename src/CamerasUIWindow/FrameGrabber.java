package CamerasUIWindow;

import Utils.Utils;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FrameGrabber implements Runnable {

    public VideoCapture getCapture() {
        return capture;
    }

    private VideoCapture capture;

    private ScheduledExecutorService timer;

    private int staffUpdatePeriod = 33;

    private byte cameraId;
    private byte whichImageView;

    private MainWindowController mwc;

    private boolean cameraActive = false;

    private int centerOfObject[];


    public int[] getCenterOfObject() {
        return centerOfObject;
    }

    public FrameGrabber(byte cameraId, byte whichImageView, MainWindowController mwc) {
        this.cameraId = cameraId;
        this.whichImageView = whichImageView;
        this.mwc = mwc;
    }

    public FrameGrabber(byte cameraId, byte whichImageView, MainWindowController mwc, int staffUpdatePeriod) {
        this.cameraId = cameraId;
        this.whichImageView = whichImageView;
        this.mwc = mwc;
        this.staffUpdatePeriod = staffUpdatePeriod;
    }

    //update ImageView
    public void toWindow(Image imageToShow) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.updateImageView(whichImageView, imageToShow);
            }
        });
    }

    //change Button Text
    public void toWindow(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.changeButtonText(text, whichImageView);
            }
        });
    }

    //message to Dialog Window
    public void toWindow(String cause, String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.dialogWindow(cause, message,null);
            }
        });
    }

    //update imageView in Mode 2
    public void toWindow(String cam, Image imageToShow) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.mode2ImageShow(cam, imageToShow);
            }
        });
    }

    public void init() {

        if (!this.cameraActive) {
            capture = new VideoCapture();
            this.capture.open(cameraId);

            if (this.capture.isOpened()) {

                this.cameraActive = true;

                this.run();

                //timer, every 33msc image will update
                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(this, 0, staffUpdatePeriod, TimeUnit.MILLISECONDS);

                toWindow("Stop camera " + (whichImageView + 1));

            } else {
                if (!mwc.isOpenedFromChange()) {
                    toWindow("", "Impossible to open the camera connection... with ID = " + cameraId);
                }
            }

        } else { // if camera is active

            this.cameraActive = false;

            //Thread.currentThread().interrupt();

            toWindow("Start camera " + (whichImageView + 1));

            // stop the timer
            this.stopAcquisition();
        }
    }

    @Override
    public void run() {

        //Getting object with GrayColor
        Mat frame = grabFrame();
        Image imageToShow;
        try {
            //convert mat object to JavaFxImage
            imageToShow = Utils.mat2Image(frame);

            //System.out.println(nImage.getHeight()+" "+nImage.getWidth());
            //imageView camera updating to imageToShow
            toWindow(imageToShow);

        }catch (IllegalArgumentException e){}

    }

    private Image getScaledImage(Image im, int w, int h) {

        return null;
    }

    static public Mat getMorph() {
        return morph;
    }

    static private Mat morph;


    private Mat grabFrame() {
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened()) {
            try {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty()) {

                    Image imageToShow = Utils.mat2Image(frame);
                    Image nImage = Utils.getScaledImage(imageToShow, mwc.getWidthOfVideo(), mwc.getHeightOfVideo());

                    frame = Utils.bufferedImage2Mat_v2(SwingFXUtils.fromFXImage(nImage, null));

                    // init
                    Mat blurredImage = new Mat();
                    Mat hsvImage = new Mat();
                    Mat mask = new Mat();
                    Mat morphOutput = new Mat();


                    // remove some noise
                    Imgproc.blur(frame, blurredImage, new Size(5, 5));

                    //System.out.println(blurredImage.height()+" "+blurredImage.width());

                    // convert the frame to HSV
                    Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

                    // get thresholding values from the UI
                    // remember: H ranges 0-180, S and V range 0-255
                    Scalar minValues = new Scalar(mwc.getScrollHueStart().getValue(), mwc.getScrollSaturationStart().getValue(),
                            mwc.getScrollValueStart().getValue());

                    Scalar maxValues = new Scalar(mwc.getScrollHueStop().getValue(), mwc.getScrollSaturationStop().getValue(),
                            mwc.getScrollValueStop().getValue());


                    // threshold HSV image to select objects
                    Core.inRange(hsvImage, minValues, maxValues, mask);

                    // show the partial output
                    toWindow(String.valueOf(whichImageView) + "1", Utils.mat2Image(mask));

                    // morphological operators
                    // dilate with large element, erode with small ones
                    Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
                    Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));

                    Imgproc.erode(mask, morphOutput, erodeElement);
                    Imgproc.erode(morphOutput, morphOutput, erodeElement);

                    Imgproc.dilate(morphOutput, morphOutput, dilateElement);
                    Imgproc.dilate(morphOutput, morphOutput, dilateElement);

                    morph = morphOutput;

                    // show the partial output
                    toWindow(String.valueOf(whichImageView) + "2", Utils.mat2Image(morphOutput));

                    Image image = Utils.mat2Image(morphOutput);
                    //System.out.println();

                    // find the objects contours and show them
                    frame = this.findAndDrawContours(morphOutput, frame);

                    Moments moments = Imgproc.moments(mask);
                    int centerX = (int) (moments.m10 / moments.m00);
                    int centerY = (int) (moments.m01 / moments.m00);

                    //System.out.println(centerX+" "+centerY);

                    centerOfObject = new int[]{centerX, centerY};

                    Imgproc.circle(frame, new Point(centerX, centerY), 7,
                            new Scalar(255, 255, 255), -1);

                    Imgproc.putText(frame, "Center", new Point(centerX - 20, centerY - 20),
                            Core.FONT_HERSHEY_COMPLEX, 0.5, new Scalar(255, 255, 255), 2);

                }

            } catch (Exception e) {
                if (!mwc.isOpenedFromChange()) {
                    // log the (full) error
                    toWindow("Error", "Exception during the image elaboration... " + e.getMessage());
                }
            }
        }

        return frame;
    }

    private Mat findAndDrawContours(Mat maskedImage, Mat frame) {
        // init
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        // find contours
        Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        // if any contour exist...
        if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
            // for each contour, display it in blue
            for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
                Imgproc.drawContours(frame, contours, idx, new Scalar(250, 0, 0));
            }
        }

        return frame;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition() {

        if (this.timer != null && !this.timer.isShutdown()) {
            try {

                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);

            } catch (InterruptedException e) {
                e.printStackTrace();
                toWindow("Exception in stopping the frame capture, trying to release the camera now... ", e.toString());
            }
        }

        if (this.capture.isOpened()) {
            // release the camera
            this.capture.release();
        }
    }


}
