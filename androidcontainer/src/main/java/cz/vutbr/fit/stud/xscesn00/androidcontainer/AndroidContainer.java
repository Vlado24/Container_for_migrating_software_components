package cz.vutbr.fit.stud.xscesn00.androidcontainer;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import android.content.*;
import android.os.*;

import dalvik.system.*;

public class AndroidContainer implements IAndroidContainer {

  // region Private Static Attributes

  /**
   * Name of temporary directory name.
   */
  private static final String TEMP_DIR_NAME = "containerDex";

  // endregion Private Static Attributes

  // region Private Attributes

  /**
   * Custom implementation of AsyncTask, to be executed.
   */
  private AndroidContainerAsyncTask asyncTask;
  /**
   * DexClassLoader to investigate file using reflection.
   */
  private DexClassLoader classLoader;
  /**
   * Class which will be investigate using reflection.
   */
  private Class<Object> classToLoad;
  /**
   * List of Objects which will be loaded from output file.
   */
  private ArrayList<Object> inObjects = new ArrayList<>();
  /**
   * Custom ObjectInputStream to read from file.
   */
  private CustomObjectInputStream inputStream;
  /**
   * Object returned from investigate file, which will be saved or loaded.
   */
  private Object loadedObject;
  /**
   * Instance of object which we are working with.
   */
  private Object objectInstance;
  /**
   * List of Objects which will be saved in output file.
   */
  private ArrayList<Object> outObjects = new ArrayList<>();
  /**
   * Path of output file.
   */
  private String outPath;
  /**
   * ObjectOutputStream to write to a output file.
   */
  private ObjectOutputStream outputStream;

  // endregion Private Attributes

  // region Container related methods

  // region Public Methods

  /**
   * Initializing Container and fields.
   * @param context Android Context
   * @param className Class which should be reviewed.
   * @param dexPath Path to .dex file
   * @param outPath Path to output file where are object serialized.
   */
  @Override
  public void onContainerCreate(
    Context context, String className, String dexPath, String outPath
  ) {

    this.outPath = outPath;
    File tempDir = context.getDir(TEMP_DIR_NAME, Context.MODE_PRIVATE);

    classLoader = new DexClassLoader(
      dexPath,
      tempDir.getAbsolutePath(),
      null,
      context.getClass().getClassLoader()
    );

    try {
      //noinspection unchecked
      this.classToLoad = (Class<Object>) this.classLoader.loadClass(className);
      this.objectInstance = this.classToLoad.newInstance();
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }

    this.asyncTask = new AndroidContainerAsyncTask(this.objectInstance);
  }

  /**
   * Loading saved object from output file and executing AsyncTask to run investigated program.
   */
  @Override
  public void onContainerResume() {

    // Check if there is file with serialized objects.
    if (hasPrevious()) {
      setPrevious();
    }

    // Run method resumeContainer(String file) using reflection.
    try {
      Method methodResume = this.classToLoad.getMethod("resumeContainer");
      this.asyncTask.setResumeMethod(methodResume);
      //      noinspection unchecked
      this.asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  /**
   * Canceling execution of AsyncTask and saving object to output file.
   */
  @Override
  public void onContainerSuspend() {
    // Run method suspendContainer(String file) using reflection.
    try {
      Method methodSuspend = this.classToLoad.getMethod("suspendContainer");
      this.asyncTask.setSuspendMethod(methodSuspend);
      this.asyncTask.cancel(true);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    // Load object with updated field and save to output file.
    if (isObjectLoaded()) {
      addObject(this.loadedObject);
    }
    setFile(this.outPath);
    onSaveState();
  }

  // endregion Container related methods

  // region Container Serialization methods

  /**
   * Add object which will be save in output file.
   *
   * @param object to save
   */
  @Override
  public void addObject(Object object) {
    outObjects.add(object);
  }

  /**
   * Add List of Objects which will be save in output file
   *
   * @param objects to be saved.
   */
  @Override
  public void addObjects(List<Object> objects) {
    outObjects.addAll(objects);
  }

  /**
   * Load Object of specific Class from output file.
   *
   * @param objectClass Class to be loaded.
   *
   * @return Object of Class objectClass.
   */
  @Override
  public Object loadObject(Class objectClass) {
    for (Object object : inObjects) {
      if (object.getClass().getName().equals(objectClass.getName())) {
        return object;
      }
    }
    return null;
  }

  /**
   * Load Objects of specific Class from output file.
   *
   * @param objectClass
   *
   * @return objects of specific Class.
   */
  @Override
  public List<Object> loadObjects(Class objectClass) {
    ArrayList<Object> result = new ArrayList<>();
    for (Object object : inObjects) {
      if (object.getClass().getName().equals(objectClass.getName())) {
        result.add(object);
      }
    }
    return result;
  }

  /**
   * Load output file and initialize ObjectInputStream
   *
   * @param file to open
   * @param aClass reviewed Class
   */
  @Override
  public void loadFile(String file, Class<?> aClass) {
    try {
      inputStream = new CustomObjectInputStream(new FileInputStream(file));
      inputStream.setClassName(aClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Set file name and initialize ObjectOutputStream
   *
   * @param file to save
   */
  @Override
  public void setFile(String file) {
    try {
      outputStream = new ObjectOutputStream(new FileOutputStream(file));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writing objects to output file.
   */
  @Override
  public void onSaveState() {
    try {
      outputStream.writeObject(outObjects);
      outObjects.clear();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reading objects from output file.x
   */
  @Override
  public void onLoadState() {
    try {
      //      noinspection unchecked
      inObjects = (ArrayList<Object>) inputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  // endregion Public Methods

  // region Private Methods

  /**
   * Check if object is loaded from investigate file.
   *
   * @return true if yes, otherwise false.
   */
  private boolean isObjectLoaded() {
    try {
      Method methodGetObject = this.classToLoad.getMethod("getObject");
      this.loadedObject = methodGetObject.invoke(objectInstance);
      return true;
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Check if object has saved previous serialized object.
   *
   * @return true if yes, otherwise false.
   */
  private boolean hasPrevious() {
    File f = new File(this.outPath);

    if (!f.exists()) {
      return false;
    }

    if (isObjectLoaded()) {
      loadFile(this.outPath, this.loadedObject.getClass());
      onLoadState();
      this.loadedObject = loadObject(this.loadedObject.getClass());
      return true;
    }

    return false;
  }

  /**
   * Set loaded object to investigate file.
   */
  private void setPrevious() {
    try {
      Method methodSetObject = this.classToLoad.getMethod("setObject", Object.class);
      methodSetObject.invoke(this.objectInstance, this.loadedObject);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  // endregion Private Methods

  // endregion Container Serialization methods

  // region Inner class

  /**
   * Custom implementation of ObjectInputStream to resolving Class through reflection.
   */
  class CustomObjectInputStream extends ObjectInputStream {

    private Class className;

    CustomObjectInputStream(InputStream in) throws IOException {
      super(in);
    }

    void setClassName(Class className) {
      this.className = className;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc)
      throws IOException, ClassNotFoundException {
      if (desc.getName().equals(className.getName())) {
        return classLoader.loadClass(className.getName());
      }
      return super.resolveClass(desc);
    }
  }

  // endregion Inner class
}
