package cz.vutbr.fit.stud.xscesn00.Container;

import java.io.*;
import java.util.ArrayList;

public class Container implements IContainer {

    private boolean mIsFirstRun;
    private ObjectInputStream mInputStream;
    private ObjectOutputStream mOutputStream;
    private ArrayList<Object> mOutObjects = new ArrayList<>();
    private ArrayList<Object> mInObjects = new ArrayList<>();

    /**
     * Adding object into array, which will be saved calling method onComplete().
     *
     * @param object to be saved.
     */
    @Override
    public void addObject(Object object) {
        mOutObjects.add(object);
    }

    /**
     * Load object which is instance of objectClass which is defined in method's
     * parameter.
     *
     * @param objectClass Class of object which should be loaded.
     * @return object of required Class.
     */
    // TODO: Determine what to do when there are objects with same class.
    @Override
    public Object loadObject(Class objectClass) {
        System.out.println("Container: loadObject(): loadObject " + objectClass);

        for (Object object : mInObjects) {
            if (objectClass.isInstance(object)) {
                return object;
            }
        }
        return null;
    }

    /**
     * Whether the Container was created for first time or not.
     * Should be used in methods like loadFile and setFile.
     *
     * @param isFirstRun true if yes, otherwise false.
     */
    @Override
    public void isFirstRun(Boolean isFirstRun) {
        System.out.println("Container: isFirstRun()");
        mIsFirstRun = isFirstRun;
    }

    /**
     * Load ObjectInputStream from file.
     *
     * @param file - name of required file.
     */
    @Override
    public void loadFile(String file) {
        System.out.println("Container: loadFile: " + file);
        if ((mInputStream != null) && (mIsFirstRun)) {
            return;
        }
        try {
            mInputStream = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set name of file where will be object saved.
     *
     * @param file - name of creating file.
     */
    @Override
    public void setFile(String file) {
        System.out.println("Container: setFile: " + file);
        if (mOutputStream != null) {
            return;
        }

        try {
            mOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this method when you want to save all objects into file.
     */
    @Override
    public void onComplete() {
        System.out.println("Container: onComplete()");
        try {
            mOutputStream.writeObject(mOutObjects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this method when you want to load all objects from file.
     */
    @Override
    public void onContinue() {
        System.out.println("Container: onContinue()");
        try {
            mInObjects = (ArrayList<Object>) mInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
