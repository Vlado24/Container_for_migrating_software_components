package cz.vutbr.fit.stud.xscesn00.containerdemo.service;

import java.io.*;
import java.util.*;

import android.content.*;
import android.os.*;

import com.google.firebase.iid.*;
import com.google.firebase.messaging.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.*;
import retrofit2.converter.gson.*;

import timber.log.*;

import cz.vutbr.fit.stud.xscesn00.androidcontainer.*;
import cz.vutbr.fit.stud.xscesn00.containerdemo.common.*;
import okhttp3.*;

public class CustomMessagingService extends FirebaseMessagingService {

  // region Private Attributes

  /**
   * AndroidContainer used on reflection.
   */
  private AndroidContainer container;
  /**
   * Service for communication with API using Retrofit.
   */
  private ManagerApiService service;
  /**
   * Number of loops executed by SCREEN_OFF/SCREEN_ON.
   */
  private int loops;
  /**
   * If is execution of AndroidContainer finished, then unregister BroadcastReceivers.
   */
  private boolean unregister;

  // endregion Private Attributes

  // region Public Methods

  @Override
  public void onCreate() {
    super.onCreate();

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(UrlConst.SERVER_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    service = retrofit.create(ManagerApiService.class);
    container = new AndroidContainer();
  }

  @Override
  public void onMessageReceived(final RemoteMessage message) {
    Timber.d("onMessageReceived(): message: %s", message);

    // Looking only for data here.
    if (message.getData() != null) {
      processMessage(message.getData());
    }
  }

  // region Public Getters and Setters

  public int getLoops() {
    return loops;
  }

  public void setLoops(int loops) {
    this.loops = loops;
  }

  public boolean isUnregister() {
    return unregister;
  }

  public void setUnregister(boolean unregister) {
    this.unregister = unregister;
  }

  // endregion Public Getters and Setters

  // endregion Public Methods

  // region Private Methods

  /**
   * Parsing and setting data
   *
   * @param data from message.
   */
  private void processMessage(Map<String, String> data) {
    Timber.d("processMessage(): data: %s", data);

    String fileName = data.get("filename");
    String numberOfLoops = data.get("loops");
    // Set default value for looping.
    if ("0".equals(numberOfLoops)) {
      numberOfLoops = "1";
    }
    downloadFile(fileName, numberOfLoops);
  }

  /**
   * Download file from server, if it's possible.
   *
   * @param filePath URL of downloaded file.
   * @param numberOfLoops number of loops defined in message.
   */
  private void downloadFile(final String filePath, final String numberOfLoops) {
    Timber.d("downloadFile(): filePath: %s, numberOfLoops: %s", filePath, numberOfLoops);

    String fileURL = UrlConst.SERVER_URL_MEDIA + filePath;
    int index = filePath.lastIndexOf('/') + 1;
    final String fileName = filePath.substring(index, filePath.length());

    Call<ResponseBody> call = service.downloadFile(fileURL);

    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Timber.d("onDownloadFileResponse(): call: %s, response: %s", call, response);

        saveFile(response, fileName);
        // Download additional file with serialized data, if exists.
        tryToDownloadSerializedData(filePath, numberOfLoops);
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Timber.d(t, "onDownloadFileFailure()");
      }
    });
  }

  /**
   * Downloading file with serialized data from server.
   *
   * @param filePath URL of parent file.
   * @param numberOfLoops number of loops defined in message.
   */
  private void tryToDownloadSerializedData(final String filePath, final String numberOfLoops) {
    Timber.d("tryToDownloadSerializedData(): filePath: %s, numberOfLoops: %s",
      filePath, numberOfLoops);

    // Parsing files name, paths...
    int j = filePath.lastIndexOf('/') + 1;
    final String fileNameDex = filePath.substring(j, filePath.length());
    int index = filePath.lastIndexOf('.') + 1;
    final String fileResponseURL =
      UrlConst.SERVER_URL_MEDIA + filePath.substring(0, index).concat("out");
    int i = fileResponseURL.lastIndexOf('/') + 1;
    final String fileName = fileResponseURL.substring(i, fileResponseURL.length());

    Call<ResponseBody> call = service.downloadFile(fileResponseURL);

    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Timber.d("onyTryToDownloadSerializedDataResponse(): call: %s, response: %s",
          call, response);

        // If file exists on a server, then save it for work.
        if (response.code() == 200) {
          saveFile(response, fileName);
        }
        exploreFile(fileNameDex, numberOfLoops);
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Timber.d(t, "onyTryToDownloadSerializedDataFailure()");
      }
    });
  }

  /** Exploring file with Android Container.
   * @param dexFile to be reviewed.
   * @param numberOfLoops number of loops defined in message.
   */
  private void exploreFile(String dexFile, final String numberOfLoops) {
    Timber.d("exploreFile(): outputFile: %s", dexFile);

    // Names processing.
    int index = dexFile.lastIndexOf('.') + 1;
    String fileName = dexFile.substring(0, index).concat("out");
    final String classToLoad = "cz.vutbr.fit.stud.xscesn00.BubbleSortWrapper";
    final String dexPath = getExternalFilesDir(null) + File.separator + dexFile;
    final String outPath = getExternalFilesDir(null) + File.separator + fileName;

    setLoops(0);
    setUnregister(false);

    // Initialize BroadcastReceiver for SCREEN_OFF
    IntentFilter filter = new IntentFilter();
    filter.addAction(Intent.ACTION_SCREEN_OFF);
    registerReceiver(new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Timber.d("onReceiveScreenOff()");
        // If is number of loops same as was on server, then upload file with serialized data,
        // and unregister itself.
        if (getLoops() == Integer.parseInt(numberOfLoops)) {
          uploadFileToServer(dexPath, outPath);
          setUnregister(true);
          unregisterReceiver(this);
          return;
        }
        setLoops(getLoops() + 1);
        container
          .onContainerCreate(getApplicationContext(), classToLoad, dexPath, outPath);
        container.onContainerResume();
      }
    }, filter);

    // Initialize BroadcastReceiver for SCREEN_ON
    IntentFilter filter1 = new IntentFilter();
    filter1.addAction(Intent.ACTION_SCREEN_ON);
    registerReceiver(new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Timber.d("onReceiveScreenOn()");
        // If is execution of Android Container finished, then unregister itself.
        if (isUnregister()) {
          unregisterReceiver(this);
        }
        // Check if number of loops is greater, because we don't want to run it before
        // onContainerResume() called is.
        if (getLoops() != 0) {
          container.onContainerSuspend();
        }
      }
    }, filter1);
  }

  /**
   * Upload file with serialized objects to server.
   *
   * @param dexPath path of file which was reviewed.
   * @param outPath path of file which will be upload.
   */
  private void uploadFileToServer(String dexPath, String outPath) {
    Timber.d("uploadFileToServer(): dexPath: %s, outPath: %s", dexPath, outPath);

    String device = FirebaseInstanceId.getInstance().getToken();
    int index = dexPath.lastIndexOf('/') + 1;
    String withoutFiles = "files/" + dexPath.substring(index, dexPath.length());

    File f = new File(outPath);
    if (!f.exists()) {
      Timber.e("uploadFileToServer(): outPath: %s NOT EXISTS", outPath);
      return;
    }

    // creates RequestBody instance from file
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
    RequestBody deviceId =
      RequestBody.create(MediaType.parse("multipart/form-data"),
        device);
    RequestBody requestFileNameDetail =
      RequestBody.create(MediaType.parse("multipart/form-data"), withoutFiles);
    // MultipartBody.Part is used to send also the actual filename
    MultipartBody.Part body =
      MultipartBody.Part.createFormData("datafile", f.getName(), requestFile);

    Call<Void> call = service.uploadResponseFile(body, requestFileNameDetail, deviceId);

    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        Timber.d("uploadFileToServer(): call: %s, response: %s", call, response);
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        Timber.d(t, "uploadFileToServer()");
      }
    });
  }

  /**
   * Method for saving file.
   *
   * @param response from server.
   * @param fileName name of the saved file.
   */
  private void saveFile(Response<ResponseBody> response, String fileName) {
    Timber.d("saveFile()");

    File file = new File(getExternalFilesDir(null) + File.separator + fileName);
    if (file.exists()) {
      file.delete();
    }

    try {
      InputStream in = null;
      FileOutputStream out = null;

      try {
        if (response.body() != null) {
          Timber.d("saveFile(): Downloading !!!");
          in = response.body().byteStream();
          out = new FileOutputStream(getExternalFilesDir(null) + File.separator + fileName);
          Timber.d("saveFile(): externalFileName: %s",
            getExternalFilesDir(null) + File.separator + fileName);
          int c;

          while ((c = in.read()) != -1) {
            out.write(c);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (in != null) {
          in.close();
        }
        if (out != null) {
          out.close();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // endregion Private Methods
}