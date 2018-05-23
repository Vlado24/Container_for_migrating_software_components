package cz.vutbr.fit.stud.xscesn00.androidcontainer;

import java.util.*;

import android.content.*;

public interface IAndroidContainer {

  // region Container main methods

  /**
   * Initialize Container and all of necessary fields using in Class of implementation.
   * @param context Android Context
   * @param className Class which should be reviewed.
   * @param dexPath Path to .dex file
   * @param outPath Path to output file where are object serialized.
   */
  void onContainerCreate(Context context, String className, String dexPath, String outPath);

  /**
   * Loading saved object from output file and executing AsyncTask to run program.
   */
  void onContainerResume();

  /**
   * Canceling execution of AsyncTask and saving object to output file.
   */
  void onContainerSuspend();

  // endregion Container main methods

  // region Container Serialization methods

  /**
   * Add object which will be save in output file.
   *
   * @param object to save
   */
  void addObject(Object object);

  /**
   * Add List of Objects which will be save in output file.
   *
   * @param objects to be saved
   */
  void addObjects(List<Object> objects);

  /**
   * Load Object of specific Class from output file.
   *
   * @param object Class
   *
   * @return Object of Class object
   */
  Object loadObject(Class object);

  /**
   * Load Objects of specific Class from output file.
   *
   * @param objects
   *
   * @return objects of specific Class.
   */
  List<Object> loadObjects(Class objects);

  /**
   * Load output file and initialize ObjectInputStream
   *
   * @param file to open
   * @param aClass reviewed Class
   */
  void loadFile(String file, Class<?> aClass);

  /**
   * Set file name and initialize ObjectOutputStream
   *
   * @param file to save
   */
  void setFile(String file);

  /**
   * Writing objects to output file.
   */
  void onSaveState();

  /**
   * Reading objects from output file.x
   */
  void onLoadState();

  // endregion Container Serialization methods
}
