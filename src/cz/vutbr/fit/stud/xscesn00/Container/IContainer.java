package cz.vutbr.fit.stud.xscesn00.Container;

public interface IContainer {

    void addObject(Object object);

    Object loadObject(Class object);

    void isFirstRun(Boolean isFirstRun);

    void loadFile(String file);

    void setFile(String file);

    void onComplete();

    void onContinue();
}
