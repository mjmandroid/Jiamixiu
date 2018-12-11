package com.project.jiamixiu.function.upload.prenster;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSRetryCallback;
import com.alibaba.sdk.android.oss.common.utils.BinaryUtil;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.project.jiamixiu.function.upload.view.IpublishView;
import com.project.jiamixiu.manger.HttpManager;
import com.project.jiamixiu.manger.listener.HttpRequestListener;
import com.project.jiamixiu.utils.OssUtils;
import com.project.jiamixiu.utils.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PublishPrenster {

    private IpublishView view;

    public PublishPrenster(IpublishView view) {
        this.view = view;
    }

    public void commit(String title,String coverImg,String tags,String videoUrl,String ossid,String description,String fid){
        Map<String,String> sMap = new HashMap<>();
        sMap.put("title",title);
        sMap.put("coverImg",coverImg);
        sMap.put("tags",tags);
        sMap.put("videoUrl",videoUrl);
        sMap.put("ossid",ossid);
        sMap.put("description",description);
        sMap.put("categoryId",fid);
        HttpManager.sendRequest(UrlConst.video_publish, sMap, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                view.commitsuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                view.onError(result);
            }

            @Override
            public void onCompleted() {
                view.onFinish();
            }
        });
    }

    public void uploadVideo2Oss(String videoPath,String objectID){
        OSS oss = OssUtils.getInstance().getOss();
        PutObjectRequest request = new PutObjectRequest("jiamixiu",objectID+".mp4",videoPath);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        try {
            // 设置Md5以便校验
            metadata.setContentMD5(BinaryUtil.calculateBase64Md5(videoPath)); // 如果是从文件上传
            // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(buff)); // 如果是上传二进制数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setMetadata(metadata);
        //request.setCRC64(OSSRequest.CRC64Config.YES);
        oss.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                String requestId = result.getRequestId();
                Log.e("Send", "Bucket: " + request.getBucketName()
                        + "\nObject: " + request.getObjectKey()
                        + "\nETag: " + result.getETag()
                        + "\nRequestId: " + result.getRequestId()
                        + "\nCallback: " + result.getServerCallbackReturnBody()
                        +"\ncode："+result.getStatusCode()
                        +"\nresultString:"+result.toString());
                String objectKey = request.getObjectKey();
                if (objectKey.contains(".mp4")){
                    objectKey = objectKey.substring(0,objectKey.indexOf(".mp4"));
                }
                view.uploadVideoSuccess(objectKey);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                Log.e("Send+onFailure", clientException.toString());
                view.onError("上传视频失败");
            }
        });

    }

    public void getObjectId(){
        HttpManager.sendRequest(UrlConst.object_uuid, new HashMap<>(), new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String objectId = object.optString("data");
                    view.generateUUid(objectId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.onError("获取uuid失败");
                }
            }

            @Override
            public void onRequestFail(String result, String code) {
                view.onError("获取uuid失败");
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public void uploadPicture(String imag){
        HttpManager.sendRequestFile(UrlConst.uploadFile,imag ,new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    view.uploadPictureSuccess(object.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onRequestFail(String result, String code) {
                view.onError(result);
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
