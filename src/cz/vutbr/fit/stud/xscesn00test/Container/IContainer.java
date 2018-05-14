package cz.vutbr.fit.stud.xscesn00test.Container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public interface IContainer extends Serializable {

    void addObject(Object object);

    void addObjects(ArrayList<Objects> objects);

    Object loadObject(Class object);

    ArrayList<Object> loadObjects(Class objects);

//    void isFirstRun(Boolean isFirstRun);

    void loadFile(String file);

    void setFile(String file);

    void onNext();

    void onContinue();

    void onComplete();

    void onResumeContainer(String filePath);

    void onSuspendContainer(String filePath);
}
