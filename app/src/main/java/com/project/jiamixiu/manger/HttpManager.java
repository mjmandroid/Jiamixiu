package com.project.jiamixiu.manger;
import android.util.Log;

import com.google.gson.Gson;
import com.project.jiamixiu.BaseApplication;
import com.project.jiamixiu.base.BaseApi;
import com.project.jiamixiu.bean.BaseBean;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.MyLogUtils;
import com.project.jiamixiu.utils.NetWorkUtil;
import com.project.jiamixiu.utils.SharedPreferencesUtil;
import com.project.jiamixiu.utils.ToastUtil;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2017/4/24 0024.
 * 网络请求管理
 */

public class HttpManager {
    public final static String CODE_SUCCESS = "0"; // 成功
    public final static String CODE_DEFAULT_ERROR = "-1";// 默认码错误
    public final static String CODE_DEFAULT = "-100";//未授权（默认）
    public final static String CODE_API_UNAUTHORIZED = "-200";//api未授权
    public final static String CODE_USER_NO_LOGIN = "-300";//用户未登录(或session token过期)
    public final static String CODE_USER_UNAUTHORIZED = "-301";//用户未授权

    /**
     * 发送请求
     *
     * @param url      地址
     * @param params   参数
     * @param listener 监听器
     */
    public static void sendRequest(final String url, Map<String, String> params, final HttpRequestListener listener) {
        if (!NetWorkUtil.isNetworkConnected()){
            ToastUtil.showTosat(BaseApplication.getContext(),"网络异常，请检查是否连接！");
            listener.onRequestFail("网络异常，请检查是否连接！","-1");
            return;
        }
        BaseApi api = RetrofitManager.getInstance().getRetrofit().create(BaseApi.class);
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", params.toString());
        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", url);
        api.getStringData(url, params).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String s) {
                MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "onNext == "+s.toString());
                Gson gson = new Gson();
                BaseBean baseBean = gson.fromJson(s, BaseBean.class);
                switch (baseBean.error_code) {
                    case CODE_SUCCESS:
                        listener.onRequestSuccess(s);
                        break;
                    case CODE_DEFAULT_ERROR:
                        listener.onRequestFail(baseBean.error_msg, baseBean.error_code);
                        break;
                    case CODE_DEFAULT:
                        listener.onRequestFail(baseBean.error_msg, baseBean.error_code);
                        break;
                    case CODE_API_UNAUTHORIZED:
                        listener.onRequestFail(baseBean.error_msg, baseBean.error_code);
                        break;
                    case CODE_USER_NO_LOGIN:
                        SharedPreferencesUtil.saveToken("");
                        listener.onRequestFail(baseBean.error_msg, baseBean.error_code);
                        break;
                    case CODE_USER_UNAUTHORIZED:
                        listener.onRequestFail(baseBean.error_msg, baseBean.error_code);
                        break;
                    default:
                        listener.onRequestFail(baseBean.error_msg, baseBean.error_code);
                        break;
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (e instanceof HttpException){
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    try {
                        MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "onError500 == "+body.string());
                        listener.onRequestFail("网络请求失败", "0");
                    } catch (IOException e1) {
                        e.printStackTrace();
                    }
                }else {
                    MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "onError == "+e.toString());
                    listener.onRequestFail("网络请求失败", "0");
                }

            }

            @Override
            public void onComplete() {
                listener.onCompleted();
                MyLogUtils.printf(MyLogUtils.DEBUG, "SendRequstToServer", "onCompleted");
            }
        });

    }
}
