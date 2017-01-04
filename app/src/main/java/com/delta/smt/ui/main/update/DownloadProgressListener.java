package com.delta.smt.ui.main.update;

/**
 * 更新
 * 下载进度listener
 * Created by Shufeng.Wu on 16/5/11.
 */
public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
