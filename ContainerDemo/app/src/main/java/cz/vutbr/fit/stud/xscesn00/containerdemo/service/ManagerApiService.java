package cz.vutbr.fit.stud.xscesn00.containerdemo.service;

import retrofit2.Call;
import retrofit2.http.*;

import cz.vutbr.fit.stud.xscesn00.containerdemo.model.*;
import okhttp3.*;

public interface ManagerApiService {

  @GET
  @Streaming
  Call<ResponseBody> downloadFile(@Url String fileUrl);

  @POST("api/device/")
  Call<ResponseBody> registerDevice(@Body DeviceRegistration device);

  @Multipart
  @POST("api/file-input/")
  Call<Void> uploadBinaryFile(@Part MultipartBody.Part file);

  @Multipart
  @POST("api/file-response/")
  Call<Void> uploadResponseFile(@Part MultipartBody.Part file,
    @Part("file_input") RequestBody fileInputName,
    @Part("device_id") RequestBody deviceId);
}
