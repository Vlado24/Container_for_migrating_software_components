package cz.vutbr.fit.stud.xscesn00;

public class BubbleSortWrapper {

    private BubbleSort object;

    public void resumeContainer() {
        System.out.println("resumeContainer()");
        if (this.object == null) {
            this.object = new BubbleSort();
        }
        sort();
    }

    public void suspendContainer() {
        System.out.println("suspendContainer()");
    }

    public void setObject(Object loadingObject) {
        System.out.println("setObject: object: " + loadingObject);

        this.object = (BubbleSort) loadingObject;
    }

    public Object getObject() {
        System.out.println("getObject()");

        return (this.object == null) ? this.object = new BubbleSort() : this.object;
    }

    public void sort() {
        this.object.bubbleSort();
        int startpos = this.object.getStartingPos();
        int finPos = this.object.getFinishingPos() + 2;
        this.object.setStartingPos(startpos);
        this.object.setFinishingPos(finPos);
    }
}
