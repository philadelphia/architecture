package com.example.app.entity.bindmaterial;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/12 12:16
 * @description :
 */

public class FinishPda {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FinishPda(int code, String message) {

        this.code = code;
        this.message = message;
    }
}
