package cz.vutbr.fit.stud.xscesn00;

import java.io.Serializable;

public class AnotherTestingObject implements Serializable {

    private static final String TAG = "AnotherTestingObject: ";

    protected enum State {
        INIT,
        WAITING,
        COMPLETE
    }

    private State state;
    private int counter;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        System.out.println(TAG + "State: " + state);
        this.state = state;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        System.out.println(TAG + "Counter " + counter);
        this.counter = counter;
    }
}
