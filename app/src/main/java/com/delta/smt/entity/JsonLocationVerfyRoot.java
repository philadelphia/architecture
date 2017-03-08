package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shaoqiang.Zhang on 2017/3/7.
 */

public class JsonLocationVerfyRoot {

    private int returnTotal;

    private List<JsonLocationVerfyList> rows ;

    private int code;

    private String message;

    public void setReturnTotal(int returnTotal){
        this.returnTotal = returnTotal;
    }
    public int getReturnTotal(){
        return this.returnTotal;
    }
    public void setRows(List<JsonLocationVerfyList> rows){
        this.rows = rows;
    }
    public List<JsonLocationVerfyList> getRows(){
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
