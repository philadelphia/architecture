package com.delta.app.entity;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/3/9 16:19
 */


public class WaringDialogEntity {

    private String title;
    private String Content;

    public WaringDialogEntity(String title, String content) {
        this.title = title;
        Content = content;
    }

    public WaringDialogEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
