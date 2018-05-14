package cz.vutbr.fit.stud.xscesn00test;

import java.io.Serializable;

public class TestingObject implements Serializable {

    private int startingPos = 0;
    private int finishingPos = 10;
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
        return startingPos;
    }

    public void setStartingPos(int startingPos) {
        this.startingPos = startingPos;
    }

    public int getFinishingPos() {
        return finishingPos;
    }

    public void setFinishingPos(int finishingPos) {
        this.finishingPos = finishingPos;
    }

    public void compute() {
        for (int i = startingPos; i <= finishingPos; i++) {
            System.out.println("Compute: i = " + i);
        }
    }
}
