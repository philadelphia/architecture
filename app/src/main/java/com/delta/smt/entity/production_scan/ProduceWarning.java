package com.delta.smt.entity.production_scan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fuxiang.Zhang on 2017/1/17.
 */

public class ProduceWarning {

    /**
     * code : 0
     * msg : Success
     * rows : {"alarm":[{"alarmType":"接料预警","workOrder":"10000","line":"H12","side":"A","slot":"008","createTime":"Jan 16, 2017 6:30:16 PM","isOk":0,"feedTime":"Jan 16, 2017 6:30:20 PM","unusedMaterials":"8000","state":"即将缺料","id":1},{"alarmType":"锡膏机换瓶提醒","workOrder":"10000","line":"H12","side":"A","process":"锡膏机","createTime":"Jan 17, 2017 8:24:39 AM","isOk":0,"changeBottleTime":"Jan 17, 2017 8:26:41 AM","alarmMessage":"锡膏机需要换瓶","id":2}],"fault":[{"faultType":"贴片机过热","line":"H12","process":"贴片机","faultMessage":"卷带故障","faultCode":"0100200","isOk":0,"createTime":"Jan 16, 2017 7:04:08 PM","id":1}],"message":[{"type":"锡膏正在运送","line":"H12","message":"替换钢网即将配送到产线，请确认","isOk":0,"id":1}]}
     */

    private String code;
    @SerializedName("message")
    private String msg;
    private List<ItemWarningInfo> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ItemWarningInfo> getRows() {
        return rows;
    }

    public void setRows(List<ItemWarningInfo> rows) {
        this.rows = rows;
    }
}
