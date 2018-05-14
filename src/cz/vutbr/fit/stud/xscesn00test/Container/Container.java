package cz.vutbr.fit.stud.xscesn00test.Container;


public class Container {

//    private static final String TAG = "Container: ";
//
//    private int numberOfStarts = 0;
//    private ObjectInputStream inputStream;
//    private ObjectOutputStream outputStream;
//    private ArrayList<Object> outObjects = new ArrayList<>();
//    private ArrayList<Object> inObjects = new ArrayList<>();
//
//    /**
//     * Adding object into array, which will be saved calling method onInit().
//     *
//     * @param object to be saved.
//     */
//    @Override
//    public void addObject(Object object) {
//        System.out.println(TAG + "getObject(): " + object);
//
//        outObjects.add(object);
//    }
//
//    /**
//     * Adding array of objects, which will be saved calling method onInit().
//     *
//     * @param objects in array to be saved.
//     */
//    @Override
//    public void addObjects(ArrayList<Objects> objects) {
//        System.out.println(TAG + "getObject(): " + objects);
//
//        outObjects.addAll(objects);
//    }
//
//    /**
//     * Load object which is instance of objectClass which is defined in method's
//     * parameter.
//     *
//     * @param objectClass Class of object which should be loaded.
//     * @return object of required Class.
//     */
//    @Override
//    public Object loadObject(Class objectClass) {
//        // TODO: Determine what to do when there are objects with same class.
//        System.out.println(TAG + "setObject(): setObject " + objectClass);
//
//        for (Object object : inObjects) {
//            if (objectClass.isInstance(object)) {
//                return object;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Load objects of specific class which is defined in method's parameter.
//     *
//     * @param objectClass Class of object which should be loaded.
//     * @return objects of required Class.
//     */
//    @Override
//    public ArrayList<Object> loadObjects(Class objectClass) {
//        System.out.println(TAG + "loadObjects(): " + objectClass);
//
//        ArrayList<Object> result = new ArrayList<>();
//        for (Object object : inObjects) {
//            if (objectClass.isInstance(object)) {
//                result.add(object);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Load ObjectInputStream from file.
//     *
//     * @param file - name of required file.
//     */
//    @Override
//    public void loadFile(String file) {
//        System.out.println(TAG + "loadFile(): " + file);
//
//        if ((inputStream != null)) {
//            return;
//        }
//        try {
//            inputStream = new ObjectInputStream(new FileInputStream(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Set name of file where will be object saved.
//     *
//     * @param file - name of creating file.
//     */
//    @Override
//    public void setFile(String file) {
//        System.out.println(TAG + "setFile(): " + file);
//
//        if (outputStream != null) {
//            return;
//        }
//
//        try {
//            outputStream = new ObjectOutputStream(new FileOutputStream(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * Call this method when you are expecting another work with these objects.
//     */
//    @Override
//    public void onNext() {
//        System.out.println(TAG + "onNext()");
//
//        try {
//            outputStream.writeInt(++numberOfStarts);
//            outputStream.writeObject(outObjects);
//            outObjects.clear();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Call this method when you want to load all objects from file.
//     */
//    @Override
//    public void onContinue() {
//        System.out.println(TAG + "onContinue() ");
//
//        try {
//            numberOfStarts = inputStream.readInt();
//            inObjects = (ArrayList<Object>) inputStream.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Calling method, when everything is done.
//     */
//    @Override
//    public void onComplete() {
//        // TODO: Determine what to do.
//        System.out.println(TAG + "onComplete()");
//    }
//
//
//    @Override
//    public void onResumeContainer(String filePath) {
//        File f = new File(filePath);
//        if(!f.exists()){
//            return;
//        }
//
//        loadFile(filePath);
//        onContinue();
//    }
//
//    @Override
//    public void onSuspendContainer(String filePath) {
//        setFile(filePath);
//        onNext();
//    }
//
//    /**
//     * Get number of starts and jobs done with objects.
//     *
//     * @return integer
//     */
//    public int getNumberOfStarts() {
//        System.out.println(TAG + "getNumberOfStarts(): " + numberOfStarts);
//        return numberOfStarts;
//    }
//
//    /**
//     * Set number of starts and jobs done with objects.
//     *
//     * @param numberOfStarts number of starts.
//     */
//    public void setNumberOfStarts(int numberOfStarts) {
//        this.numberOfStarts = numberOfStarts;
//    }
}
