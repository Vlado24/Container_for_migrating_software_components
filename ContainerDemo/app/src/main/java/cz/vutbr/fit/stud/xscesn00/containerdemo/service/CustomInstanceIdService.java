package cz.vutbr.fit.stud.xscesn00.containerdemo.service;

import com.google.firebase.iid.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.*;
import retrofit2.converter.gson.*;

import timber.log.*;

import cz.vutbr.fit.stud.xscesn00.containerdemo.common.*;
import cz.vutbr.fit.stud.xscesn00.containerdemo.model.*;
import okhttp3.*;

public class CustomInstanceIdService extends FirebaseInstanceIdService {

  private static final String ANDROID_TYPE = "android";

  @Override
  public void onTokenRefresh() {
    Timber.d("onTokenRefresh()");

    //For registration of token
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    postToken(refreshedToken);
  }

  private void postToken(String refreshedToken) {
    Timber.d("postToken(): refreshedToken: %s", refreshedToken);

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(UrlConst.SERVER_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    ManagerApiService service = retrofit.create(ManagerApiService.class);

    DeviceRegistration device = new DeviceRegistration();
    device.setName(
      android.os.Build.BRAND + "_" + android.os.Build.MODEL + "_" + android.os.Build.USER);
    device.setActive(true);
    device.setRegistration_id(refreshedToken);
    device.setType(ANDROID_TYPE);

    Call<ResponseBody> call = service.registerDevice(device);

    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call,
        Response<ResponseBody> response) {
        Timber.d("onResponse(): call: %s, response: %s", call, response.body());
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Timber.d(t, "onResponse()");
      }
    });
  }
}
