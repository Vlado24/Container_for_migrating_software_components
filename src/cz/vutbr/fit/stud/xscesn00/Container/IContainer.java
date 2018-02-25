package cz.vutbr.fit.stud.xscesn00.Container;

import java.util.ArrayList;
import java.util.Objects;

public interface IContainer {

    void addObject(Object object);

    void addObjects(ArrayList<Objects> objects);

    Object loadObject(Class object);

    ArrayList<Object> loadObjects(Class objects);

    void isFirstRun(Boolean isFirstRun);

    void loadFile(String file);

    void setFile(String file);

    void onNext();

    void onContinue();

    void onComplete();
}
