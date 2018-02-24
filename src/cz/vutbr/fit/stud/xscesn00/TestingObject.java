package cz.vutbr.fit.stud.xscesn00;

import java.io.Serializable;

public class TestingObject implements Serializable {

    private int mStartingPos;
    private int mFinishingPos;

    public int getStartingPos() {
        return mStartingPos;
    }

    public void setStartingPos(int startingPos) {
        mStartingPos = startingPos;
    }

    public int getFinishingPos() {
        return mFinishingPos;
    }

    public void setFinishingPos(int finishingPos) {
        mFinishingPos = finishingPos;
    }

    public void compute() {
        for (int i = mStartingPos; i <= mFinishingPos; i++) {
            System.out.println("Compute: i = " + i);
        }
    }
}
