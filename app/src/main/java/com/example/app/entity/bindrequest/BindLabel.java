package com.example.app.entity.bindrequest;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 16:55
 * @description :绑定可移动标签
 */

public class BindLabel {
    private String moveLabel;

    public String getMoveLabel() {
        return moveLabel;
    }

    public void setMoveLabel(String moveLabel) {
        this.moveLabel = moveLabel;
    }

    public BindLabel(String moveLabel) {

        this.moveLabel = moveLabel;
    }
}
