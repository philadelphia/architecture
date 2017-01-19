package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shaoqiang.Zhang on 2017/1/18.
 */

public class JsonProductToolsVerfyRoot {

    private int returnTotal;

    private List<JsonProductToolsVerfyList> rows ;

    private int code;

    private String message;

    public void setReturnTotal(int returnTotal){
        this.returnTotal = returnTotal;
    }
    public int getReturnTotal(){
        return this.returnTotal;
    }
    public void setRows(List<JsonProductToolsVerfyList> rows){
        this.rows = rows;
    }
    public List<JsonProductToolsVerfyList> getRows(){
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
