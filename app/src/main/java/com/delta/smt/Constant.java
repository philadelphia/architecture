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
    public static final String WARNINGRECIEVE = "warning_recieve";
    public  static final  String WARNINGMESSAGE="warning_message";
    public static final String WAKE = "wake";
    //预警
    public static final int PCBWARNING = 0;
    public static final int FAULTWARNING = 1;
    public static final int GENERATEDWARNING = 2;
    public static final int HANDPARHTSWARNING = 3;
    public static final int SAMPLEWARING = 13;
    public static final int PRODUCE_WARNING=5;
    public static final int HAND_ADD=6;
    public static final int MODULE_UP_WARNING = 10;
    public static final int MODULE_DOWN_WARNING = 11;

    public static final int STORAGEREAD = 4;

    //工单ID
    public static final String WORK_ITEM_ID = "WorkItemID";



    //更新状态
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final String MESSAGE_DIALOG_DISMISS = "message_dialog_dismiss";
    public static final String MESSAGE_FAILED = "message_failed";


    public static final int ACTIVITY_REQUEST_WORK_ITEM_ID = 1;
    public static final int ACTIVITY_RESULT_WORK_ITEM_ID = 10;

    public static  String WARE_HOUSE_NAME = "wareHouseName";

    //预警
    public static final Integer PCB_WAREH_ISSUE_ALARM_FLAG = 0;
    /** 进行小仓库发料预警 */
    public static final Integer WAREH_ALARM_FLAG = 1;
    /** 进行小仓库超领预警 */
    public static final Integer EXCESS_ALARM_FLAG = 2;
    /** 进行Feeder缓存区发料预警 */
    public static final Integer FEEDER_BUFF_ALARM_FLAG = 3;
    /** 进行上模组预警 */
    public static final Integer PLUG_MOD_ALARM_FLAG = 4;
    /** 进行工程师故障预警 */
    public static final Integer ENGINEER_FAULT_ALARM_FLAG = 5;
    /** 进行操作员故障预警 */
    public static final Integer OPERATOR_FAULT_ALARM_FLAG = 6;
    /** 进行产线接料预警 */
    public static final Integer PRODUCTION_LINE_ALARM_FLAG = 7;
    /** 进行线外人员预警 */
    public static final Integer OFF_LINE_ALARM_FLAG = 8;
    /** 进行下模组预警 */
    public static final Integer UNPLUG_MOD_ALARM_FLAG = 9;
    /** 进行尾数仓入库预警 */
    public static final Integer WAREH_MANTISSA_ALARM_FLAG = 10;
    /** 进行尾数仓退入主仓库预警 */
    public static final Integer WAREH_MANTO_WAREH_ALARM_FLAG = 11;
    /** 进行Feeder缓存区入库预警 */
    public static final Integer FEEDER_BUFF_TO_WAREH_ALARM_FLAG = 12;


}
