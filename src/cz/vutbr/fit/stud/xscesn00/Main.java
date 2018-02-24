package cz.vutbr.fit.stud.xscesn00;

import cz.vutbr.fit.stud.xscesn00.Container.Container;

public class Main {

    private static final String OUTPUT_FILE = "objects.out";

    public static void main(String[] args) {
        System.out.println("Starting...");
    }

    public void createObject() {
        // Create testing object.
        TestingObject testingObject = new TestingObject();
        testingObject.setStartingPos(0);
        testingObject.setFinishingPos(10);

        testingObject.compute();

        // Save objects.
        Container container = new Container();
        container.isFirstRun(true);
        container.setFile(OUTPUT_FILE);
        container.addObject(testingObject);
        container.onComplete();
    }

    public void loadObject() {
        // Load objects.
        Container container = new Container();
        container.isFirstRun(false);
        container.loadFile(OUTPUT_FILE);
        container.onContinue();
        TestingObject newTestingObject = (TestingObject) container.loadObject(TestingObject.class);
        int newStartingPosition = newTestingObject.getFinishingPos();
        newTestingObject.setStartingPos(newStartingPosition);
        newTestingObject.setFinishingPos(20);

        newTestingObject.compute();

    }
}
