package com.delta.smt.entity;

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
    private String msg;
    private RowsBean rows;

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

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private List<AlarmBean> alarm;
        private List<FaultBean> fault;
        private List<com.delta.smt.ui.production_warning.item.ItemInfo> message;

        public List<AlarmBean> getAlarm() {
            return alarm;
        }

        public void setAlarm(List<AlarmBean> alarm) {
            this.alarm = alarm;
        }

        public List<FaultBean> getFault() {
            return fault;
        }

        public void setFault(List<FaultBean> fault) {
            this.fault = fault;
        }

        public List<com.delta.smt.ui.production_warning.item.ItemInfo> getMessage() {
            return message;
        }

        public void setMessage(List<com.delta.smt.ui.production_warning.item.ItemInfo> message) {
            this.message = message;
        }

        public static class AlarmBean {
            /**
             * alarmType : 接料预警
             * workOrder : 10000
             * line : H12
             * side : A
             * slot : 008
             * createTime : Jan 16, 2017 6:30:16 PM
             * isOk : 0
             * feedTime : Jan 16, 2017 6:30:20 PM
             * unusedMaterials : 8000
             * state : 即将缺料
             * id : 1
             * process : 锡膏机
             * changeBottleTime : Jan 17, 2017 8:26:41 AM
             * alarmMessage : 锡膏机需要换瓶
             */

            private String alarmType;
            private String workOrder;
            private String line;
            private String side;
            private String slot;
            private String createTime;
            private int isOk;
            private String feedTime;
            private String unusedMaterials;
            private String state;
            private int id;
            private String process;
            private String changeBottleTime;
            private String alarmMessage;

            public String getAlarmType() {
                return alarmType;
            }

            public void setAlarmType(String alarmType) {
                this.alarmType = alarmType;
            }

            public String getWorkOrder() {
                return workOrder;
            }

            public void setWorkOrder(String workOrder) {
                this.workOrder = workOrder;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getSide() {
                return side;
            }

            public void setSide(String side) {
                this.side = side;
            }

            public String getSlot() {
                return slot;
            }

            public void setSlot(String slot) {
                this.slot = slot;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getIsOk() {
                return isOk;
            }

            public void setIsOk(int isOk) {
                this.isOk = isOk;
            }

            public String getFeedTime() {
                return feedTime;
            }

            public void setFeedTime(String feedTime) {
                this.feedTime = feedTime;
            }

            public String getUnusedMaterials() {
                return unusedMaterials;
            }

            public void setUnusedMaterials(String unusedMaterials) {
                this.unusedMaterials = unusedMaterials;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProcess() {
                return process;
            }

            public void setProcess(String process) {
                this.process = process;
            }

            public String getChangeBottleTime() {
                return changeBottleTime;
            }

            public void setChangeBottleTime(String changeBottleTime) {
                this.changeBottleTime = changeBottleTime;
            }

            public String getAlarmMessage() {
                return alarmMessage;
            }

            public void setAlarmMessage(String alarmMessage) {
                this.alarmMessage = alarmMessage;
            }
        }

        public static class FaultBean {
            /**
             * faultType : 贴片机过热
             * line : H12
             * process : 贴片机
             * faultMessage : 卷带故障
             * faultCode : 0100200
             * isOk : 0
             * createTime : Jan 16, 2017 7:04:08 PM
             * id : 1
             */

            private String faultType;
            private String line;
            private String process;
            private String faultMessage;
            private String faultCode;
            private int isOk;
            private String createTime;
            private int id;

            public String getFaultType() {
                return faultType;
            }

            public void setFaultType(String faultType) {
                this.faultType = faultType;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getProcess() {
                return process;
            }

            public void setProcess(String process) {
                this.process = process;
            }

            public String getFaultMessage() {
                return faultMessage;
            }

            public void setFaultMessage(String faultMessage) {
                this.faultMessage = faultMessage;
            }

            public String getFaultCode() {
                return faultCode;
            }

            public void setFaultCode(String faultCode) {
                this.faultCode = faultCode;
            }

            public int getIsOk() {
                return isOk;
            }

            public void setIsOk(int isOk) {
                this.isOk = isOk;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

/*        public static class MessageBean {
            *//**
             * type : 锡膏正在运送
             * line : H12
             * message : 替换钢网即将配送到产线，请确认
             * isOk : 0
             * id : 1
             *//*

            private String type;
            private String line;
            private String message;
            private int isOk;
            private int id;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLine() {
                return line;
            }

            public void setLine(String line) {
                this.line = line;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getIsOk() {
                return isOk;
            }

            public void setIsOk(int isOk) {
                this.isOk = isOk;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }*/
    }
}
