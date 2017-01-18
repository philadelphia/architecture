package com.delta.smt.entity;

import java.util.List;

/**
 * Created by Shaoqiang.Zhang on 2017/1/17.
 */

public class JsonProduct_mToolsRoot {

    private int total;

    private int pageSize;

    private int currentPage;

    private int totalPage;

    private int returnTotal;

    private List<JsonProduct_mToolsList> rows ;

    private int code;

    private String message;

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
    public int getPageSize(){
        return this.pageSize;
    }
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public int getCurrentPage(){
        return this.currentPage;
    }
    public void setTotalPage(int totalPage){
        this.totalPage = totalPage;
    }
    public int getTotalPage(){
        return this.totalPage;
    }
    public void setReturnTotal(int returnTotal){
        this.returnTotal = returnTotal;
    }
    public int getReturnTotal(){
        return this.returnTotal;
    }
    public void setRows(List<JsonProduct_mToolsList> rows){
        this.rows = rows;
    }
    public List<JsonProduct_mToolsList> getRows(){
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
