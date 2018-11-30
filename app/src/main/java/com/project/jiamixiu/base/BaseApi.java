package com.project.jiamixiu.base;




import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface BaseApi {
    @FormUrlEncoded
    @POST
    Call<String> getStringResponse(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST()
    Observable<String> getStringData(@Url String url, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST()
    Observable<String> getFileData(@Url String url, @FieldMap Map<String, File> map);

    @Multipart
    @POST()
    Observable<String> upImg(@Url String url,@Part("FunName") RequestBody funName,
                                   @Part("path") RequestBody path,
                                   @Part("appfile") RequestBody appfile,
                                   @Part MultipartBody.Part file);
}
