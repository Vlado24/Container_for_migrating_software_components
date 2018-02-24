package cz.vutbr.fit.stud.xscesn00;

public class Main {

    private static final String OUTPUT_FILE = "objects.out";

    public static void main(String[] args) {

        // Create new object and initializing.
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

        // Load objects.
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
