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

    public static void setCenterOfImages(double[] centerOfImages) {
        DistanceToTheObject.centerOfImages = centerOfImages;
    }

    public static double getFocus() {
        return focus;
    }

    public static void setFocus(double focus) {
        DistanceToTheObject.focus = focus;
    }


    public static double getDistanceByMethodOne(FrameGrabber[] grabbers) {

        //System.out.println(centerOfImages[0]+" "+centerOfImages[1]);

        try {
            dx1 = Math.abs(centerOfImages[0] - grabbers[0].getCenterOfObject()[0]);
            dx2 = Math.abs(centerOfImages[0] - grabbers[1].getCenterOfObject()[0]);
        }catch (NullPointerException npex){}

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


    public static double getDistanceByMethodTwo(FrameGrabber[] grabbers){
        try {
            dx1 = Math.abs(centerOfImages[0] - grabbers[0].getCenterOfObject()[0]);
            dx2 = Math.abs(centerOfImages[0] - grabbers[1].getCenterOfObject()[0]);
        }catch (NullPointerException npex){}

        double dy1 = Math.abs(centerOfImages[1] - grabbers[0].getCenterOfObject()[1]);
        double dy2 = Math.abs(centerOfImages[1] - grabbers[1].getCenterOfObject()[1]);

        double dy = (dy1 + dy2) / 2;

        //System.out.println("dy = " + dy);

        double alphay = dy / 319 * 26.5;

        //System.out.println("alpha y = " + alphay);


        double alpha = dx1 / 319 * 26.5;
        double beta = dx2 / 319 * 26.5;

        double alphahatch = 90 - alpha;
        double betahatch = 90 - beta;
        double teta = 180 - alphahatch - betahatch;


        double b = (distanceBetweenCameras / Math.sin(Math.toRadians(teta))) * Math.sin(Math.toRadians(betahatch))
                / Math.cos(Math.toRadians(alphay));
        double c = (distanceBetweenCameras / Math.sin(Math.toRadians(teta))) * Math.sin(Math.toRadians(alphahatch))
                / Math.cos(Math.toRadians(alphay));

        //System.out.println(alphay);

        double s = 0.5 * Math.sqrt(2 * b * b + 2 * c * c - distanceBetweenCameras * distanceBetweenCameras);

        return s;
    }


}
