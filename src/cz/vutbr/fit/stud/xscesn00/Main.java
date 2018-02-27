package cz.vutbr.fit.stud.xscesn00;

import cz.vutbr.fit.stud.xscesn00.Container.Container;

public class Main {

    private static TestingObject object;
    private static Container container;

    private static final String OUTPUT_FILE = "objects.out";

    public static void main(String[] args) {
        System.out.println("Starting...");
        initObjects();
        container.isFirstRun(true);
        while (!isComplete()) {
            if (getNumberOfStarts() != 0) {
                loadObject();
            }
            compute();
            saveObject();
        }
    }

    private static void initObjects() {
        object = new TestingObject();
        container = new Container();
        object.initTestingObject(getNumberOfStarts());
    }

    private static void compute() {
        int numberOfStarts = getNumberOfStarts();
        if (numberOfStarts == 1) {
            object.setStartingPos(0);
            object.setFinishingPos(10);
            object.initTestingObject(numberOfStarts);
        } else {
            int startPos = object.getStartingPos() + 10;
            int finPos = object.getFinishingPos() + 10;
            object.setStartingPos(startPos);
            object.setFinishingPos(finPos);
            object.pauseTestingObject(numberOfStarts);
        }
        object.compute();
    }

    private static void saveObject() {
        container.setFile(OUTPUT_FILE);
        container.addObject(object);
        container.onNext();
    }

    private static void loadObject() {
        container.loadFile(OUTPUT_FILE);
        container.onContinue();
        object = (TestingObject) container.loadObject(TestingObject.class);
    }

    private static int getNumberOfStarts() {
        return container.getNumberOfStarts();
    }

    private static boolean isComplete() {
        int numberOfStarts = getNumberOfStarts();
        if (numberOfStarts == 10) {
            object.finishTestingObject(numberOfStarts);
            return true;
        }
        return false;
    }
}
