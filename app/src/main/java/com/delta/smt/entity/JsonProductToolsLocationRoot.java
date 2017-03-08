package com.delta.smt.entity;

/**
 * Created by Shaoqiang.Zhang on 2017/1/18.
 */

public class JsonProductToolsLocationRoot {

    private int returnTotal;

    private JsonProductToolsLocationList rows;

    private int code;

    private String message;

    public void setReturnTotal(int returnTotal){
        this.returnTotal = returnTotal;
    }
    public int getReturnTotal(){
        return this.returnTotal;
    }
    public void setRows(JsonProductToolsLocationList rows){
        this.rows = rows;
    }
    public JsonProductToolsLocationList getRows(){
        return this.rows;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
