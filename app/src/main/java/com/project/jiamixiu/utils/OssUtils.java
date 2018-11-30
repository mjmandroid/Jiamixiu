package com.project.jiamixiu.utils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.project.jiamixiu.BaseApplication;

/**
 * 文件上传工具类
 */
public class OssUtils {

    private static OssUtils instance;
    private ClientConfiguration conf = null;
    private OSS oss = null;
    private static final String ENDPOINT =  "http://oss-cn-shenzhen.aliyuncs.com";

    private OssUtils(){}

    public OSS getOss() {
        return oss;
    }

    public static OssUtils getInstance(){
        if (instance == null){
            synchronized (OssUtils.class){
                if (instance == null)
                    instance = new OssUtils();
            }
        }
        return instance;
    }

    public  void initOss( String accessKeyId,
                          String accessKeySecret,
                          String securityToken){
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(5*60*1000);
        conf.setSocketTimeout(5*60*1000);
        conf.setMaxConcurrentRequest(5);
        conf.setMaxErrorRetry(2);
        OSSLog.enableLog();
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
                accessKeyId, accessKeySecret,
                securityToken
        );

        oss = new OSSClient(BaseApplication.getContext(),ENDPOINT,credentialProvider,conf);

    }
}
