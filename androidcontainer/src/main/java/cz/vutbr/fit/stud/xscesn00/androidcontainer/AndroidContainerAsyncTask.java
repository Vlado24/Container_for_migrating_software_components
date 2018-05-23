package cz.vutbr.fit.stud.xscesn00.androidcontainer;

import java.lang.reflect.*;

import android.os.*;

public class AndroidContainerAsyncTask extends AsyncTask {

  // region Private Attributes

  /**
   * Instance of object to load methods.
   */
  private Object objectInstance;
  /**
   * Method which should be invoked on resuming.
   */
  private Method resumeMethod;
  /**
   * Method which should be invoked on suspending.
   */
  private Method suspendMethod;

  /**
   * Constructor
   *
   * @param objectInstance to invoke methods.
   */
  public AndroidContainerAsyncTask(Object objectInstance) {
    this.objectInstance = objectInstance;
    //    isRunning = true;
  }
  // endregion Private Attributes

  // region Public Setters Methods

  public void setResumeMethod(Method resumeMethod) {
    this.resumeMethod = resumeMethod;
  }

  public void setSuspendMethod(Method suspendMethod) {
    this.suspendMethod = suspendMethod;
  }

  // endregion Public Setters Methods

  // region AsyncTask Methods

  /**
   * Overriding @AsyncTask#doInBackground() to invoke resume method.
   *
   * @param objects of null.
   *
   * @return nothing, just run investigated program.
   */
  @Override
  protected Object doInBackground(Object[] objects) {
    while (!isCancelled()) {
      try {
        Thread.currentThread();
        try {
          this.resumeMethod.invoke(this.objectInstance);
        } catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    return null;
  }

  /**
   * Overriding @AsyncTak#onCancelled() to invoke suspend method and stop execution.
   */
  @Override
  protected void onCancelled() {
    super.onCancelled();

    try {
      this.suspendMethod.invoke(this.objectInstance);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
  // endregion AsyncTask Methods
}
