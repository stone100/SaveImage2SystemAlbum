package com.stone.saveimage2systemalbum.album;

/**
 * @author stone
 * @date 17/3/24
 */
public interface DownloadCallback {
    /**
     * 下载动作完成后的回调
     * @param downloadResult true-下载成功; false-下载失败
     */
    void onDownloadCompleted(boolean downloadResult);
}
