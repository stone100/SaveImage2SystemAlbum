package com.stone.saveimage2systemalbum.album;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.stone.saveimage2systemalbum.ApplicationProvider;

import java.io.File;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * @author stone
 * @date 17/3/24
 */
public class AlbumManager {

    /**
     * 下载图片
     * @param imageUrl
     * @param downloadCallback
     */
    public static void download(final String imageUrl, final DownloadCallback downloadCallback) {
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(imageUrl).build();
                Call call = client.newCall(request);
                Response response = call.execute();

                if(response != null && response.body() != null) {
                    BufferedSink sink = null;
                    try {
                        sink = Okio.buffer(Okio.sink(FileUtils.createFileFrom(imageUrl)));
                        sink.write(response.body().bytes());
                        e.onNext(true);
                    } catch (Exception exception) {
                        Log.e("_stone_", "AlbumManager-download-subscribe(): " + exception.getMessage());
                        e.onNext(false);
                    } finally {
                        if(sink != null) sink.close();
                    }
                } else {
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean result) throws Exception {
                        //将下载的图片插入到系统相册, 并同步刷新系统相册(更新UI)
                        if(result) insertSystemAlbumAndRefresh(imageUrl);

                        //回调更新UI
                        if(downloadCallback != null) downloadCallback.onDownloadCompleted(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d("_stone_", "AlbumManager-download-OnError-accept: " + throwable.getMessage());
                        if(downloadCallback != null) downloadCallback.onDownloadCompleted(false);
                    }
                });
    }

    /**
     * 插入到系统相册, 并刷新系统相册
     * @param imageUrl
     */
    private static void insertSystemAlbumAndRefresh(final String imageUrl) {
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> e) throws Exception {
                File file = FileUtils.createFileFrom(imageUrl);
                String imageUri = MediaStore.Images.Media.insertImage(ApplicationProvider.IMPL.getApp().getContentResolver(), file.getAbsolutePath(), file.getName(), "图片: " + file.getName());
                Log.d("_stone_", "insertSystemAlbumAndRefresh-subscribe: imageUri=" + imageUri);


                //讲数据插入到系统图库, 在系统相册APP中就可以看到保存的图片了.
                //为了保险起见, 再同步一下系统图库
                syncAlbum(imageUrl);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    /**
     * 同步刷新系统相册
     * @param imageUrl
     */
    private static void syncAlbum(String imageUrl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            final Uri contentUri = Uri.fromFile(FileUtils.createFileFrom(imageUrl).getAbsoluteFile());
            scanIntent.setData(contentUri);
            ApplicationProvider.IMPL.getApp().sendBroadcast(scanIntent);
        } else {
            //4.4开始不允许发送"Intent.ACTION_MEDIA_MOUNTED"广播, 否则会出现: Permission Denial: not allowed to send broadcast android.intent.action.MEDIA_MOUNTED from pid=15410, uid=10135
            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            ApplicationProvider.IMPL.getApp().sendBroadcast(intent);
        }
    }
}
