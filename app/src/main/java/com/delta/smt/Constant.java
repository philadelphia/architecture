package com.delta.smt;

import org.apache.http.io.SessionOutputBuffer;

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
    public static final int PRODUCE_WARNING=5;
    public static final int HAND_ADD=6;
    public static final int MODULE_UP_WARNING = 10;
    public static final int MODULE_DOWN_WARNING = 11;

    public static final int STORAGEREAD = 4;


    public static final String WORK_ITEM_ID = "WorkItemID";



    //更新状态
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final String MESSAGE_DIALOG_DISMISS = "message_dialog_dismiss";
    public static final String MESSAGE_FAILED = "message_failed";


}
