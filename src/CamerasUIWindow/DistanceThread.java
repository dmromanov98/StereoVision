package CamerasUIWindow;

import javafx.application.Platform;

/***
 * Thread for showing distance in window
 */
public class DistanceThread implements Runnable {

    private MainWindowController mwc;

    //Cycle operation variable
    private boolean work = true;

    //algorithm(method) of measuring distance
    private static byte method = 1;

    public static void setMethod(byte method) {
        DistanceThread.method = method;
    }

    public DistanceThread(MainWindowController mwc) {
        this.mwc = mwc;
    }

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    @Override
    public void run() {
        while (work) {

            if (method == 1)
                toWindow(DistanceToTheObject.getDistanceByMethodOne(mwc.grabbers));
            if (method == 2)
                toWindow(DistanceToTheObject.getDistanceByMethodTwo(mwc.grabbers));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void toWindow(double distance) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mwc.showDistance(distance);
            }
        });
    }

}
