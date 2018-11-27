package com.project.jiamixiu.manger;



import android.content.SharedPreferences;

import com.project.jiamixiu.BaseApplication;
import com.project.jiamixiu.utils.HttpsUtils;
import com.project.jiamixiu.utils.SharedPreferencesUtil;
import com.project.jiamixiu.utils.UrlConst;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 单例模式创建一个Retrofit管理器
 */

public class RetrofitManager {
    public static final int TIME_OUT = 10;
    private Retrofit retrofit;
    private static RetrofitManager manager;
    private RetrofitManager(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                String token = SharedPreferencesUtil.getToken();
                Request newRequest = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-type", "application/json")
                                    .addHeader("device_type","android")
                                    .addHeader("access_token",token)
                                    .build();
                return chain.proceed(newRequest);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.sslSocketFactory(sslParams.sSLSocketFactory,trustManager);

        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConst.baseUrl)
//                .addConverterFactory(CustomGsonConverterFactory.create())//可设置自定义GsonConverterFactory
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static RetrofitManager getInstance(){
        if (manager == null){
            synchronized (RetrofitManager.class) {
                if (manager == null) {
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }
    public Retrofit getRetrofit() {
        return retrofit;
    }

}
