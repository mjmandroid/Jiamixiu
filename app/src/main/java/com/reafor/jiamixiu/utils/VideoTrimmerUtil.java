package com.reafor.jiamixiu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.reafor.jiamixiu.interfaces.VideoTrimListener;
import com.reafor.jiamixiu.interfaces.WaterMarskListener;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import iknow.android.utils.DeviceUtil;
import iknow.android.utils.UnitConverter;
import iknow.android.utils.callback.SingleCallback;
import iknow.android.utils.thread.BackgroundExecutor;

/**
 * Author：J.Chou
 * Date：  2016.08.01 2:23 PM
 * Email： who_know_me@163.com
 * Describe:
 */
public class VideoTrimmerUtil {

  private static final String TAG = VideoTrimmerUtil.class.getSimpleName();
  public static final long MIN_SHOOT_DURATION = 3000L;// 最小剪辑时间3s
  public static final int VIDEO_MAX_TIME = 10;// 10秒
  public static final long MAX_SHOOT_DURATION = VIDEO_MAX_TIME * 1000L;//视频最多剪切多长时间10s
  public static final int MAX_COUNT_RANGE = 10;  //seekBar的区域内一共有多少张图片
  private static final int SCREEN_WIDTH_FULL = DeviceUtil.getDeviceWidth();
  public static final int RECYCLER_VIEW_PADDING = UnitConverter.dpToPx(30);
  public static final int VIDEO_FRAMES_WIDTH = SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2;
  private static final int THUMB_WIDTH = (SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2) / VIDEO_MAX_TIME;
  private static final int THUMB_HEIGHT = UnitConverter.dpToPx(50);

  public static void trim(Context context, String inputFile, String outputFile, long startMs, long endMs, final VideoTrimListener callback,boolean isChangeSpeed,int speed) {
    final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    final String outputName = "trimmedVideo_" + timeStamp + ".mp4";
    String outputFile2 = outputFile + "/" + "new" + outputName;
    outputFile = outputFile + "/" + outputName;

    String start = convertSecondsToTime(startMs / 1000);
    String duration = convertSecondsToTime((endMs - startMs) / 1000);
    //String start = String.valueOf(startMs);
    //String duration = String.valueOf(endMs - startMs);

    /** 裁剪视频ffmpeg指令说明：
     * ffmpeg -ss START -t DURATION -i INPUT -codec copy -avoid_negative_ts 1 OUTPUT
     -ss 开始时间，如： 00:00:20，表示从20秒开始；
     -t 时长，如： 00:00:10，表示截取10秒长的视频；
     -i 输入，后面是空格，紧跟着就是输入视频文件；
     -codec copy -avoid_negative_ts 1 表示所要使用的视频和音频的编码格式，这里指定为copy表示原样拷贝；
     INPUT，输入视频文件；
     OUTPUT，输出视频文件
     */
    //TODO: Here are some instructions
    //https://trac.ffmpeg.org/wiki/Seeking
    //https://superuser.com/questions/138331/using-ffmpeg-to-cut-up-video

    //1. String cmd = "-ss " + start + " -t " + duration + " -accurate_seek" + " -i " + inputFile + " -codec copy -avoid_negative_ts 1 " + outputFile;
    //2. String cmd = "-ss " + start + " -i " + inputFile + " -ss " + start + " -t " + duration + " -vcodec copy " + outputFile;
    //3. {"ffmpeg", "-ss", "" + startTime, "-y", "-i", inputFile, "-t", "" + induration, "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", outputFile}
    String cmd = "-ss " + start + " -y " + "-i " + inputFile + " -t " + duration + " -vcodec " + "mpeg4 " + "-b:v " + "2097152 " + "-b:a " + "48000 " + "-ac " + "2 " + "-ar " + "22050 "+ outputFile;
    String[] command = cmd.split(" ");
    try {
      final String tempOutFile = outputFile;
      FFmpeg.getInstance(context).execute(command, new ExecuteBinaryResponseHandler() {

        @Override public void onSuccess(String s) {
          if (isChangeSpeed){
            changeVideoSpeed(context,tempOutFile,outputFile2,startMs,endMs,callback,speed);
          } else {
            callback.onFinishTrim(tempOutFile);
          }
        }

        @Override public void onStart() {
          callback.onStartTrim();
        }
      });
    } catch (FFmpegCommandAlreadyRunningException e) {
      e.printStackTrace();
    }
  }


  /**
   * setpts修改视频速率
   * ffmpeg -i input.mkv -an -filter:v "setpts=0.5*PTS" output.mkv
   * 调整速度倍率范围[0.25, 4]
   * @param context
   * @param inputFile
   * @param outputFile
   * @param startMs
   * @param endMs
   * @param callback
   */
  public static void changeVideoSpeed(Context context, String inputFile, String outputFile, long startMs, long endMs, final VideoTrimListener callback,int speed){
    float step = 0f;
    if (speed == 0){
        step = 3f;
    }else if (speed == 2){
      step = 2f;
    } else if (speed == 3){
      step = 0.5f;
    } else {
      step = 0.25f;
    }
      //ffmpeg -i input-video.mp4 -vf "setpts=0.68*PTS" -filter:a "atempo=1.467" output-video.mp4
    String cmd = "-i " + inputFile +" -vf setpts="+step+"*PTS "+"-filter:a atempo=1.467 "+outputFile;
    String[] command = cmd.split(" ");
    try {
      final String tempOutFile = outputFile;
      FFmpeg.getInstance(context).execute(command, new ExecuteBinaryResponseHandler() {

        @Override public void onSuccess(String s) {
            callback.onFinishTrim(tempOutFile);
        }

        @Override public void onStart() {
        }

        @Override
        public void onFailure(String message) {
          super.onFailure(message);
          ToastUtil.showTosat(context,message+"aaa");
          Log.e("FFmpeg",message);
          System.out.println("FFmpeg"+message);
        }

        @Override
        public void onProgress(String message) {
          super.onProgress("FFmpeg=="+message);
          System.out.println(message);
        }
      });
    } catch (FFmpegCommandAlreadyRunningException e) {
      e.printStackTrace();
    }
  }

  /**
   * 使用ffmpeg命令行给视频添加水印
   * @param srcFile 源文件
   * @param waterMark 水印文件路径
   * @param targetFile 目标文件
   * @return 添加水印后的文件
   *
   * StringBuilder sb1 = new StringBuilder();
    sb1.append("ffmpeg");
    sb1.append(" -i");
    sb1.append(" " + vediooutput);
    sb1.append(" -i");
    sb1.append(" " + imagePath);
    sb1.append(" -filter_complex");
    sb1.append(" overlay=20:35");
    sb1.append(" -vcodec libx264 -profile:v baseline -preset ultrafast -b:v 3000k -g 25");
    sb1.append(" -y");
    sb1.append(" " + mergeVideo);
   */
  public static  void addWaterMark(final Context context, String srcFile, String waterMark, String targetFile,
                                   final WaterMarskListener listener){
//    StringBuilder sb = new StringBuilder();
//    sb.append(" -i");
//    sb.append(" " + srcFile);
//    sb.append(" -codec");
//    sb.append(" copy");
//    sb.append(" -bsf");
//    sb.append(" h264_mp4toannexb ");
//    sb.append("-r 25  ");
//    sb.append("-s 720x1280 ");
//    sb.append("-pix_fmt nv21  ");
//    sb.append("-ar 44100  ");
//    sb.append("-ac 1  ");
//    sb.append("-analyzeduration 500  ");
//    sb.append("-vcodec libx264  ");
//    sb.append("-profile:v baseline  ");
//    sb.append("-preset ultrafast  ");
//    sb.append("-b:v 4m -g 30  ");
//    sb.append("-acodec libfdk_aac  ");
//    sb.append("-b:a 512k  ");
//    sb.append(" -f");
//    sb.append(" mpegts");
//    sb.append(" " + targetFile);

    String waterMarkCmd = "-i %s -i %s -filter_complex overlay=0:0 %s";
    waterMarkCmd = String.format(waterMarkCmd, srcFile, waterMark, targetFile);
    try {
      FFmpeg.getInstance(context).execute(waterMarkCmd.split(" "), new ExecuteBinaryResponseHandler(){
        @Override
        public void onSuccess(String message) {
          super.onSuccess(message);
          if (listener != null)
          listener.addWaterMarskSuccess(message);
          //ToastUtil.showTosat(context,"添加成功");
        }
          @Override
          public void onProgress(String message) {
              super.onProgress(message);
              Log.e("onProgress",message);
          }

          @Override
        public void onFailure(String message) {
          super.onFailure(message);
          System.out.println("message="+message);
          //ToastUtil.showTosat(context,"添加失败");
          if (listener != null)
          listener.addWaterMarskFailed(message);
        }
      });
    } catch (FFmpegCommandAlreadyRunningException e) {
      e.printStackTrace();
    }
  }

  public static void shootVideoThumbInBackground(final Context context, final Uri videoUri, final int totalThumbsCount, final long startPosition,
      final long endPosition, final SingleCallback<Bitmap, Integer> callback) {
    BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0L, "") {
      @Override public void execute() {
        try {
          MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
          mediaMetadataRetriever.setDataSource(context, videoUri);
          // Retrieve media data use microsecond
          long interval = (endPosition - startPosition) / (totalThumbsCount - 1);
          for (long i = 0; i < totalThumbsCount; ++i) {
            long frameTime = startPosition + interval * i;
            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(frameTime * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            try {
              bitmap = Bitmap.createScaledBitmap(bitmap, THUMB_WIDTH, THUMB_HEIGHT, false);
            } catch (final Throwable t) {
              t.printStackTrace();
            }
            callback.onSingleCallback(bitmap, (int) interval);
          }
          mediaMetadataRetriever.release();
        } catch (final Throwable e) {
          Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
        }
      }
    });
  }

  public static String getVideoFilePath(String url) {
    if (TextUtils.isEmpty(url) || url.length() < 5) return "";
    if (url.substring(0, 4).equalsIgnoreCase("http")) {

    } else {
      url = "file://" + url;
    }

    return url;
  }

  private static String convertSecondsToTime(long seconds) {
    String timeStr = null;
    int hour = 0;
    int minute = 0;
    int second = 0;
    if (seconds <= 0) {
      return "00:00";
    } else {
      minute = (int) seconds / 60;
      if (minute < 60) {
        second = (int) seconds % 60;
        timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
      } else {
        hour = minute / 60;
        if (hour > 99) return "99:59:59";
        minute = minute % 60;
        second = (int) (seconds - hour * 3600 - minute * 60);
        timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
      }
    }
    return timeStr;
  }

  private static String unitFormat(int i) {
    String retStr = null;
    if (i >= 0 && i < 10) {
      retStr = "0" + Integer.toString(i);
    } else {
      retStr = "" + i;
    }
    return retStr;
  }
}
