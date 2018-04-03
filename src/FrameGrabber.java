import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FrameGrabber implements Runnable {

    private VideoCapture capture;
    private ScheduledExecutorService timer;
    private byte cameraId;
    private byte whichImageView;
    private MainWindowController mwc;
    private boolean cameraActive = false;

    public byte getCameraId() {
        return cameraId;
    }

    public void setCameraId(byte cameraId) {
        this.cameraId = cameraId;
    }

    public FrameGrabber(byte cameraId, byte whichImageView, MainWindowController mwc) {
        this.cameraId = cameraId;
        this.whichImageView = whichImageView;
        this.mwc = mwc;
    }

    public void toWindow(Image imageToShow) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.updateImageView(whichImageView, imageToShow);
            }
        });
    }

    public void toWindow(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.changeButtonText(text, whichImageView);
            }
        });
    }

    public void toWindow(String cause, String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.dialogWindow(cause, message);
            }
        });
    }

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
                this.timer.scheduleAtFixedRate(this, 0, 33, TimeUnit.MILLISECONDS);

                toWindow("Stop camera " + whichImageView);

            } else {

                toWindow("", "Impossible to open the camera connection... with ID = ");
            }

        } else { // if camera isnt active

            this.cameraActive = false;
            //Thread.currentThread().interrupt();
            toWindow("Start camera " + whichImageView);

            // stop the timer
            this.stopAcquisition();
        }
    }

    public void changeCamera() {
        init();
        if (testOnConnectionNextCamera())
            cameraId++;
        else
            cameraId = 0;

        if (whichImageView == 0)
            mwc.setCamerasID(new byte[]{cameraId, mwc.getCamerasID()[1]});
        else if (whichImageView == 1)
            mwc.setCamerasID(new byte[]{mwc.getCamerasID()[0], cameraId});

        init();
    }

    public boolean testOnConnectionNextCamera() {
        int move = 1;
        boolean isEnabled = false;

        for (int i = 0; i < mwc.getCamerasID().length; i++)
            if (mwc.getCamerasID()[i] == cameraId &&
                    mwc.getCamerasIsOnline()[i] == true)
                isEnabled = true;

        System.out.println(isEnabled);

        boolean result;
        VideoCapture captureTest = new VideoCapture();
        captureTest.open((cameraId + move));

        if (captureTest.isOpened())
            result = true;
        else
            result = false;

        if (captureTest.isOpened() && !isEnabled)
            captureTest.release();

        return result;
    }

    @Override
    public void run() {

        //Getting object with GrayColor
        Mat frame = grabFrame();

        //convert mat object to JavaFxImage
        Image imageToShow = Utils.mat2Image(frame);

        //imageView camera updating to imageToShow
        toWindow(imageToShow);

    }

//    private Mat grabFrame() {
//        // init everything
//        Mat frame = new Mat();
//
//        // check if the capture is open
//        if (this.capture.isOpened()) {
//            try {
//                // read the current frame
//                this.capture.read(frame);
//
//                // if the frame is not empty, process it
//                if (!frame.empty()) {
//                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
//                }
//
//            } catch (Exception e) {
//                toWindow("Exception during the image elaboration: ", e.toString());
//            }
//        }
//
//        return frame;
//    }


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
                    // init
                    Mat blurredImage = new Mat();
                    Mat hsvImage = new Mat();
                    Mat mask = new Mat();
                    Mat morphOutput = new Mat();

                    // remove some noise
                    Imgproc.blur(frame, blurredImage, new Size(5, 5));

                    // convert the frame to HSV
                    Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

                    // get thresholding values from the UI
                    // remember: H ranges 0-180, S and V range 0-255
                    Scalar minValues = new Scalar(mwc.getScrollHueStart().getValue(), mwc.getScrollSaturationStart().getValue(),
                            mwc.getScrollValueStart().getValue());

                    Scalar maxValues = new Scalar(mwc.scrollHueStop.getValue(), mwc.getScrollSaturationStop().getValue(),
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
                    System.out.println();

                    // find the objects contours and show them
                    frame = this.findAndDrawContours(morphOutput, frame);

                }

            } catch (Exception e) {
                // log the (full) error
                System.err.print("Exception during the image elaboration...");
                e.printStackTrace();
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
