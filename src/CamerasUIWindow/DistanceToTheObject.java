package CamerasUIWindow;

public class DistanceToTheObject {

    //distance between cameras
    private static double distanceBetweenCameras = 9.3;

    //center of ImageView
    private static double centerOfImages[] = {319, 239};

    //focal length
    private static double focus = 681;

    //displacement of the center of the object
    // relative to the center of the camera
    private static double dx1;
    private static double dx2;


    public static double getDistanceBetweenCameras() {
        return distanceBetweenCameras;
    }

    public static void setDistanceBetweenCameras(double distanceBetweenCameras) {
        DistanceToTheObject.distanceBetweenCameras = distanceBetweenCameras;
    }

    public static double[] getCenterOfImages() {
        return centerOfImages;
    }

    public static void setCenterOfImages(double[] centerOfImages) {
        DistanceToTheObject.centerOfImages = centerOfImages;
    }

    public static double getFocus() {
        return focus;
    }

    public static void setFocus(double focus) {
        DistanceToTheObject.focus = focus;
    }

    public static double getDx1() {
        return dx1;
    }

    public static void setDx1(double dx1) {
        DistanceToTheObject.dx1 = dx1;
    }

    public static double getDx2() {
        return dx2;
    }

    public static void setDx2(double dx2) {
        DistanceToTheObject.dx2 = dx2;
    }

    public static double getDistanceByMethodOne(FrameGrabber[] grabbers) {
        dx1 = Math.abs(centerOfImages[0] - grabbers[0].getCenterOfObject()[0]);
        dx2 = Math.abs(centerOfImages[0] - grabbers[1].getCenterOfObject()[0]);

        double alpha = Math.toDegrees(Math.atan(dx1 / focus));
        double beta = Math.toDegrees(Math.atan(dx2 / focus));

        double alphahatch = 90 - alpha;
        double betahatch = 90 - beta;
        double teta = 180 - alphahatch - betahatch;

        double b = (distanceBetweenCameras / Math.sin(Math.toRadians(teta))) * Math.sin(Math.toRadians(betahatch));
        double c = (distanceBetweenCameras / Math.sin(Math.toRadians(teta))) * Math.sin(Math.toRadians(alphahatch));

        double s = 0.5 * Math.sqrt(2 * b * b + 2 * c * c - distanceBetweenCameras * distanceBetweenCameras);

        return s;
    }
}
