package com.delta.app;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/15 10:33
 */


public class Constant {


    public static final String WARNING_RECEIVE = "warning_receive";
    public static final String WARNING_MESSAGE = "warning_message";
    //预警
    public static final int HAND_ADD = 6;

    //工单ID
    public static final String WORK_ITEM_ID = "WorkItemID";
    public static final String PRODUCT_NAME_MAIN = "product_name_main";
    public static final String PRODUCT_NAME = "product_name";
    public static final String LINE_NAME = "line_name";
    public static final String SIDE = "side";


    //更新状态
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static final String MESSAGE_DIALOG_DISMISS = "message_dialog_dismiss";
    public static final String MESSAGE_FAILED = "message_failed";


    public static final int ACTIVITY_REQUEST_WORK_ITEM_ID = 1;
    public static final int ACTIVITY_RESULT_WORK_ITEM_ID = 10;

    public static final String WARE_HOUSE_NAME = "wareHouseName";

    //预警
    public static final String PCB_WARE_ISSUE_ALARM_FLAG = String.valueOf(0);
    /**
     * 进行小仓库发料预警
     */
    public static final String WARE_ALARM_FLAG = String.valueOf(1);
    /**
     * 进行小仓库超领预警
     */
    public static final String EXCESS_ALARM_FLAG = String.valueOf(2);
    /**
     * 进行Feeder缓存区发料预警
     */
    public static final String FEEDER_BUFF_ALARM_FLAG = String.valueOf(3);
    /**
     * 进行上模组预警
     */
    public static final String PLUG_MOD_ALARM_FLAG = String.valueOf(4);
    /**
     * 进行工程师故障预警
     */
    public static final String ENGINEER_FAULT_ALARM_FLAG = String.valueOf(5);
    /**
     * 进行操作员故障预警
     */
    public static final String OPERATOR_FAULT_ALARM_FLAG = String.valueOf(6);
    /**
     * 进行产线接料预警
     */
    public static final String PRODUCTION_LINE_ALARM_FLAG = String.valueOf(7);
    /**
     * 进行线外人员预警
     */
    public static final String OFF_LINE_ALARM_FLAG = String.valueOf(8);
    /**
     * 进行下模组预警
     */
    public static final String UNPLUG_MOD_ALARM_FLAG = String.valueOf(9);
    /**
     * 进行尾数仓入库预警
     */
    public static final String WAREH_MANTISSA_ALARM_FLAG = String.valueOf(10);
    /**
     * 进行尾数仓退入主仓库预警
     */
    public static final String WARE_MAIN_WARE_ALARM_FLAG = String.valueOf(11);
    /**
     * 进行Feeder缓存区入库预警
     */
    public static final String FEEDER_BUFF_TO_WARE_ALARM_FLAG = String.valueOf(12);
    /**
     *
     */
    public static final String MANTISSA_WAREHOUSE_ALARM_FLAG = String.valueOf(13);
    public static final String PRODUCE_WARNING_LINE_NAME = "produce_warning_line_name";
    public static final String FAULT_PROCESSING_LINE_NAME = "fault_processing_line_name";
    public static final String HAND_ADD_LINE_NAME = "hand_add_line_name";
    public static final String STORAGE_NAME = "storage_name";
    public static final String WORK_ORDER = "work_order";
    public static final String ACTION ="000" ;
    //后台需要判断的字段
    public static final String NEED_BIND_PRECAR_STRING = "需要绑定备料车";
    public static final String FAILURE_BIND_PRECAR_STRING = "绑定备料车失败，请重试";
    public static final String PRECAR_WAREHOUSE_STRING = "小仓库只能绑定备料车";
    public static final String PRECAR_MANTISS_STRING = "尾数仓只能绑定余料车";
    public static final String NO_WORKORDER_MATERIALS_LIST_STRING = "暂无工单发料列表";
    public static final String CAR_HAD_BIND_STRING = "该料车已经被绑定过，请重新选择";
    public static final String FAILURE_STARTISSUE_STRING = "请求发料失败，请退出重试";
    public static final String FAILURE_STARTISSUE_STRING2 = "当前工单已有发料人，不允许同时操作";
    public static final String NO_WORKORDER_STRING = "当前没有发料工单，请确认";
    public static final String FAILURE_MATERIALS_STRING = "当前所发料号错误，请确认";
    public static final String FAILURE_TRAYS_STRING = "该料盘已经发过，请换新的料盘";
    public static final String ADD_PRE_CAR_STRING = "该备料车已用完，请绑定新的备料车";
    public static final String SURE_END_ISSUE_STRING = "发料没有完成，确定要结束本次发料么？";
    public static final String JUMP_MATERIALS_STRING = "上一个料没有发完，确定要跳过上一个料么？";
    public static final String NO_ISSUE_MATERIALS_STRING = "没有要发的料了，请确认";
    public static final String FAILURE_MANTISS_TRAYS_STRING = "该料盘不属于该工单，请确认";
    public static final String CAN_NOT_END_ISSUE_STRING = "发料没有完成，不能完成发料";
    public static final String FAILURE_START_ISSUE_STRING = "有未完成的发料工单，不允许再次开始发料";
    public static final String SCAN_FAILED = "扫描有误，请确认所扫二维码";
    /**
     * 品管确认
     */
    public static final String QUALITY_MANAGE = "quality_manage";
    /**
     * 仓库发料尾数仓发料
     */
    public static final String DIVIDE_MATERIAL_AND_ISSUE = "请先拆分料盘，然后再进行发料";
    public static String FAULT_SOLUTION_ID = "fault_solution_id";
    /**
     * 产线跳转 生产中预警  故障处理
     **/
    public static String SELECT_TYPE = "select_type";
    public static String FAULT_SOLUTION_NAME = "fault_solution_name";
    public static String FAULT_CODE = "fault_code";
    public static String FAULT_ID = "fault_id";
    public static String PRODUCTION_LINE = "production_line";
    public static String ACCEPT_MATERIALS_LINES = "accept_materials_line";
    public static String ACCEPT_MATERIALS_NUM = "accept_materials_num";
    public static String ACCEPT_MATERIALS_FACE = "accept_materials_face";
    public static String ACCEPT_MATERIALS_WORK = "accept_materials_work";
    public static String CONDITION = null;

}
