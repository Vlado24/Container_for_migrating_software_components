package cz.vutbr.fit.stud.xscesn00test;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private TestingObject object;
    private AnotherTestingObject anotherObject;
    private List<Object> objects;

    public static void main(String[] args) {

    }

    public void resumeContainer(String filePath) {
        System.out.println("resumeContainer: file: " + filePath);
        if (this.object == null) {
            this.object = new TestingObject();
        }
        if (this.anotherObject == null) {
            this.anotherObject = new AnotherTestingObject();
        }
        if (objects == null) {
            this.objects = new ArrayList<>();
        }
        compute();
    }

    public void suspendContainer(String filePath) {
        System.out.println("suspendContainer: file: " + filePath);
    }

    public void setObject(Object loadingObject) {
        System.out.println("setObject: object: " + loadingObject);

        this.object = (TestingObject) loadingObject;
    }

    public void setObjects(List<Object> loadingObjects) {
        for (Object obj : loadingObjects) {
            if (obj instanceof TestingObject) {
                this.object = (TestingObject) obj;
            } else if (obj instanceof AnotherTestingObject) {
                this.anotherObject = (AnotherTestingObject) obj;
            }
        }
    }

    public Object getObject() {
        System.out.println("getObject()");

        return (this.object == null) ? this.object = new TestingObject() : this.object;
    }

    public List<Object> getObjects() {
        System.out.println("getObject()");

        return this.objects;
    }

    private void compute() {
        System.out.println("compute()");

        this.object.compute();
        int startPos = this.object.getStartingPos() + 10;
        int finPos = this.object.getFinishingPos() + 10;
        this.object.setStartingPos(startPos);
        this.object.setFinishingPos(finPos);
        this.anotherObject.setState(AnotherTestingObject.State.INIT);

        // Add to list.
        // TODO: Maybe is better to use Set.
        objects.add(object);
        objects.add(anotherObject);
    }
}
