package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shaoqiang.Zhang on 2017/1/19.
 */

public class JsonProductBackRoot {

    private int returnTotal;

    private List<JsonProductBackList> rows ;

    private int code;

    private String message;

    public void setReturnTotal(int returnTotal){
        this.returnTotal = returnTotal;
    }
    public int getReturnTotal(){
        return this.returnTotal;
    }
    public void setRows(List<JsonProductBackList> rows){
        this.rows = rows;
    }
    public List<JsonProductBackList> getRows(){
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
