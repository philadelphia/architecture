package com.delta.commonlibs.http.exception;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/4/5 16:27
 */


public class ServerException extends RuntimeException {

    public int code;
    public String message;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
