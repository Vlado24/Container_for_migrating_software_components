package cz.vutbr.fit.stud.xscesn00.Container;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Container implements IContainer {

    private static final String TAG = "Container: ";

    private boolean mIsFirstRun;
    private int mNumberOfStarts;
    private ObjectInputStream mInputStream;
    private ObjectOutputStream mOutputStream;
    private ArrayList<Object> mOutObjects = new ArrayList<>();
    private ArrayList<Object> mInObjects = new ArrayList<>();

    /**
     * Adding object into array, which will be saved calling method onInit().
     *
     * @param object to be saved.
     */
    @Override
    public void addObject(Object object) {
        System.out.println(TAG + "addObject(): " + object);

        mOutObjects.add(object);
    }

    /**
     * Adding array of objects, which will be saved calling method onInit().
     *
     * @param objects in array to be saved.
     */
    @Override
    public void addObjects(ArrayList<Objects> objects) {
        System.out.println(TAG + "addObject(): " + objects);

        mOutObjects.addAll(objects);
    }

    /**
     * Load object which is instance of objectClass which is defined in method's
     * parameter.
     *
     * @param objectClass Class of object which should be loaded.
     * @return object of required Class.
     */
    @Override
    public Object loadObject(Class objectClass) {
        // TODO: Determine what to do when there are objects with same class.
        System.out.println(TAG + "loadObject(): loadObject " + objectClass);

        for (Object object : mInObjects) {
            System.out.println(TAG + "loadObject(): obj" + object);
            if (objectClass.isInstance(object)) {
                return object;
            }
        }
        return null;
    }

    /**
     * Load objects of specific class which is defined in method's parameter.
     *
     * @param objectClass Class of object which should be loaded.
     * @return objects of required Class.
     */
    @Override
    public ArrayList<Object> loadObjects(Class objectClass) {
        System.out.println(TAG + "loadObjects(): " + objectClass);

        ArrayList<Object> result = new ArrayList<>();
        for (Object object : mInObjects) {
            if (objectClass.isInstance(object)) {
                result.add(object);
            }
        }
        return result;
    }

    /**
     * Whether the Container was created for first time or not.
     * Should be used in methods like loadFile and setFile.
     *
     * @param isFirstRun true if yes, otherwise false.
     */
    @Override
    public void isFirstRun(Boolean isFirstRun) {
        System.out.println(TAG + "isFirstRun(): " + isFirstRun);

        mIsFirstRun = isFirstRun;
        mNumberOfStarts = 0;
//        mOutObjects.add(mNumberOfStarts);
    }

    /**
     * Load ObjectInputStream from file.
     *
     * @param file - name of required file.
     */
    @Override
    public void loadFile(String file) {
        System.out.println(TAG + "loadFile(): " + file);

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
        System.out.println(TAG + "setFile(): " + file);

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
     * Call this method when you are expecting another work with these objects.
     */
    @Override
    public void onNext() {
        System.out.println(TAG + "onNext()");

        try {
            mOutputStream.writeInt(mNumberOfStarts++);
            mOutputStream.writeObject(mOutObjects);
            mOutObjects.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this method when you want to load all objects from file.
     */
    @Override
    public void onContinue() {
        System.out.println(TAG + "onContinue()");

        try {
            mNumberOfStarts = mInputStream.readInt();
//            System.out.println(TAG + "Loaded int: " + mInputStream.readInt() + " saved num " + mNumberOfStarts);
            mInObjects.clear();
            mInObjects = (ArrayList<Object>) mInputStream.readObject();
//            mNumberOfStarts = (int) mInObjects.get(0);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calling method, when everything is done.
     */
    @Override
    public void onComplete() {
        // TODO: Determine what to do.
        System.out.println(TAG + "onComplete()");
    }

    /**
     * Get number of starts and jobs done with objects.
     *
     * @return integer
     */
    public int getNumberOfStarts() {
        return mNumberOfStarts;
    }

    /**
     * Set number of starts and jobs done with objects.
     *
     * @param numberOfStarts
     */
    public void setNumberOfStarts(int numberOfStarts) {
        mNumberOfStarts = numberOfStarts;
    }

//    public Container(ContainerBuilder builder) {
//        mIsFirstRun = builder.getIsFirstRun();
//        this.setFile(builder.getFileName());
//        Object object = builder.getObject();
//        if (object != null) {
//            this.addObject(object);
//        } else if (builder.getObjects() != null) {
//            this.addObjects(builder.getObjects());
//        }
//    }

//    public static class ContainerBuilder {
//
//        private boolean mIsFirstRun;
//        private String mFileName;
//        private Object mObject;
//        private ArrayList<Objects> mObjects;
//
//        public Container buildContainer() {
//            return new Container(this);
//        }
//
//        public boolean getIsFirstRun() {
//            return mIsFirstRun;
//        }
//
//        public void setIsFirstRun(boolean isFirstRun) {
//            mIsFirstRun = isFirstRun;
//        }
//
//        public String getFileName() {
//            return mFileName;
//        }
//
//        public void setFileName(String fileName) {
//            mFileName = fileName;
//        }
//
//        public Object getObject() {
//            return mObject;
//        }
//
//        public void setObject(Object object) {
//            mObject = mObject;
//        }
//
//        public ArrayList<Objects> getObjects() {
//            return mObjects;
//        }
//
//        public void setObjects(ArrayList<Objects> objects) {
//            mObjects = objects;
//        }
//    }
}
