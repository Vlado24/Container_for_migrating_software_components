package cz.vutbr.fit.stud.xscesn00;

import java.beans.Transient;
import java.io.Serializable;

public class TestingObject implements Serializable {

    private int mStartingPos;
    private int mFinishingPos;
    private transient AnotherTestingObject testingObject;

    public AnotherTestingObject getTestingObject() {
        return testingObject;
    }

    public void initTestingObject(int counter) {
        testingObject = new AnotherTestingObject();
        testingObject.setState(AnotherTestingObject.State.INIT);
        testingObject.setCounter(counter);
    }

    public void pauseTestingObject(int counter) {
        int actualCounter = testingObject.getCounter();
        testingObject.setState(AnotherTestingObject.State.WAITING);
        testingObject.setCounter(actualCounter + counter);
    }

    public void finishTestingObject(int counter) {
        int actualCounter = testingObject.getCounter();
        testingObject.setState(AnotherTestingObject.State.COMPLETE);
        testingObject.setCounter(actualCounter + counter);
    }

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
