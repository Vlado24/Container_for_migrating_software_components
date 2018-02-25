package cz.vutbr.fit.stud.xscesn00;

import cz.vutbr.fit.stud.xscesn00.Container.Container;

public class Main {

    static TestingObject object;
    static Container container;

    private static final String OUTPUT_FILE = "objects.out";

    public static void main(String[] args) {
        System.out.println("Starting...");
        initObjects();
        container.isFirstRun(true);
        while (!isComplete()) {
            System.out.println("Number of starts: " + getNumberOfStarts());
            if (getNumberOfStarts() != 0) {
                loadObject();
            }
            compute();
            saveObject();
        }
    }

    public static void initObjects() {
        object = new TestingObject();
        container = new Container();
    }

    public static void compute() {
        System.out.println("Compute(): Number of starts: " + getNumberOfStarts());
        if (getNumberOfStarts() == 0) {
            object.setStartingPos(0);
            object.setFinishingPos(10);
        } else {
            int startPos = object.getStartingPos() + 10;
            int finPos = object.getFinishingPos() + 10;
            object.setStartingPos(startPos);
            object.setFinishingPos(finPos);
        }
        object.compute();
    }

    public static void saveObject() {
        container.setFile(OUTPUT_FILE);
        container.addObject(object);
        container.onNext();
    }

    public static void loadObject() {
        container.loadFile(OUTPUT_FILE);
        container.onContinue();
        object = (TestingObject) container.loadObject(TestingObject.class);
    }

    public static int getNumberOfStarts() {
        return container.getNumberOfStarts();
    }

    public static boolean isComplete() {
        if (getNumberOfStarts() == 10) {
            return true;
        }
        return false;
    }
}
