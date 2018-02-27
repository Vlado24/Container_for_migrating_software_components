package cz.vutbr.fit.stud.xscesn00;

import java.io.Serializable;

public class AnotherTestingObject implements Serializable {

    private static final String TAG = "AnotherTestingObject: ";

    protected enum State {
        INIT,
        WAITING,
        COMPLETE
    }

    private State mState;
    private int mCounter;

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        System.out.println(TAG + "State: " + state);
        mState = state;
    }

    public int getCounter() {
        return mCounter;
    }

    public void setCounter(int counter) {
        System.out.println(TAG + "Counter " + counter);
        mCounter = counter;
    }
}
