package CamerasUIWindow;

import javafx.application.Platform;

public class DistanceThread implements Runnable {

    private MainWindowController mwc;
    private boolean work = true;

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
            toWindow(DistanceToTheObject.getDistanceByMethodOne(mwc.grabbers));
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
