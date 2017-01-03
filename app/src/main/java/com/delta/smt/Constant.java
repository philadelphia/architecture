package com.delta.smt;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/15 10:33
 */


public class Constant {
    public static final int NOMAL = 0;
    public static final int PUllTOREFRESH = 1;
    public static final int UPLOADMORE = 2;
    public static final String WARNINGTYPE = "warning_type";
    public static final String WARNINGRECIEVE = "warning_Recieve";
    public static final String WAKE = "wake";
    //预警
    public static final int PCBWARNING = 0;
    public static final int FAULTWARNING = 1;
    public static final int GENERATEDWARNING = 2;
    public static final int HANDPARHTSWARNING = 3;
    public static final int SAMPLEWARING = 4;


    public static final String WORK_ITEM_ID = "WorkItemID";

    //更新配置文件update.json url
    public static final String bundleJsonUrl = "http://172.22.35.177:8081/update.json";

    //更新状态
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final String MESSAGE_DIALOG_DISMISS = "message_dialog_dismiss";
    public static final String MESSAGE_FAILED = "message_failed";
}
