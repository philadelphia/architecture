package com.example.commonlibs.rxerrorhandler;

import android.content.Context;




public interface ResponseErrorListener {
    void handlerResponseError(Context context, Exception e);
}
