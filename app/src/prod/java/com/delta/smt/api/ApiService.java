package com.delta.smt.api;


import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.FaultSolutionMessage;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.InventoryExecption;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.JsonProductBackRoot;
import com.delta.smt.entity.JsonProductBorrowRoot;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.JsonProductToolsLocation;
import com.delta.smt.entity.JsonProductToolsVerfyRoot;
import com.delta.smt.entity.JsonProduct_mToolsRoot;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MantissaWarehouseReturnResult;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveWarning;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ResultFeeder;
import com.delta.smt.entity.SolutionMessage;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.StoreEntity;
import com.delta.smt.entity.Success;
import com.delta.smt.entity.Update;
import com.delta.smt.entity.User;
import com.delta.smt.entity.VirtualLineBindingItem;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;
import com.delta.smt.ui.production_warning.item.ItemAcceptMaterialDetail;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


/**
 * Created by Administrator on 2016/3/23.
 */
public interface ApiService {
    @POST("ams/library/user/login2")
    Observable<LoginResult> login(@Body User user);

    @POST
    Observable<List<WareHouse>> getAllWareHouse();


    //  tao.zt.zhang

    //  获取feeder入库列表
//    @GET("http://172.17.52.29:8081/SMM/FeederBuffStorage/qFeederBuffStorageList")
    @GET("SMM/FeederBuffStorage/qFeederBuffStorageList")
    Observable<Result<FeederCheckInItem>> getAllCheckedInFeeders();

    //获取feeder入库时间
    @GET("SMM/unplugmod/feederBuffStorage")
    Observable<ModuleDownDetailsItem> getFeederCheckInTime(@Query("condition") String condition);


    //重置Feeder发料状态
    @GET("SMM/Buffer/completeBufferIssue")
    Observable<ResultFeeder> resetFeederSupplyStatus();


    //获取下模组列表
    @GET("SMM/FeederBuffStorage/feederBuffStorage")
    Observable<ModuleDownDetailsItem> getDownModuleList(@Query("condition") String condition);

    //获取所有的Feeder备料工单列表
    @GET("SMM/Buffer/querySchedule")
    Observable<Result<FeederSupplyWarningItem>> getAllSupplyWorkItems();

    //获取指定工单的Feeder备料列表
    @GET("SMM/Buffer/startBufferIssue")
    Observable<Result<FeederSupplyItem>> getAllToBeSuppliedFeeders(@Query("condition") String workID);

    //获取Feeder备料时间
    @GET("SMM/Buffer/bufferIssue")
    Observable<Result<FeederSupplyItem>> getFeederInsertionToSlotTimeStamp(@Query("condition") String condition);

    //上传feeder备料上模组结果
    @GET("SMM/Buffer/completeBufferIssue")
    Observable<ResultFeeder> upLoadFeederSupplyResult();


    /**
     * @description :
     * 1.生产中预警
     * 2.手补件通知
     * @author : Fuxiang.Zhang
     * @date : 2017/1/21 14:41
     */
    //请求产线列表数据
    @GET("SMM/LineManage/queryLines")
    Observable<Result<ItemProduceLine>> getLineDatas();

    //请求预警，故障，消息的item数量
    @GET("lineAlarmFault/alarmFaultInfos")
    Observable<ProduceWarning> getTitleDatas(@Query("condition") String condition);

    //请求预警中item数据
    @GET("lineAlarmFault/alarmFaultInfos")
    Observable<ProduceWarning> getItemWarningDatas(@Query("condition") String condition);

    //请求接料预警详情页面item数据
    @GET("lineAlarmFault/lineMaterialConnectDetail")
    Observable<ItemAcceptMaterialDetail> getAcceptMaterialsItemDatas(@Query("condition") String condition);

    //提交新旧流水号
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("lineAlarmFault/connectMaterial")
    Observable<Result> commitSerialNumber(@Field("condition") String condition);

    //请求关灯
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("lineAlarmFault/offMaterialLight")
    Observable<Result> requestCloseLight(@Field("condition") String condition);

    //请求故障中item数据
    @GET("lineAlarmFault/alarmFaultInfos")
    Observable<ProduceWarning> getItemBreakDownDatas(@Query("condition") String condition);

    //请求消息中item数据
    @GET("lineAlarmFault/alarmFaultInfos")
    Observable<ProduceWarning> getItemInfoDatas(@Query("condition") String condition);

    //确认信息中item
    @GET("lineAlarmFault/confirmMessage")
    Observable<Result> getItemInfoConfirm(@Query("condition") String condition);

    //确认预警中item
    @GET("lineAlarmFault/confirmAlarmMessage")
    Observable<Result> getItemWarningConfirm(@Query("condition") String condition);

    //提交预警中扫码数据
    @GET("lineAlarmFault/relayMaterial")
    Observable<Result> getBarcodeInfo(@Query("condition") String condition);

    //请求手补件item数据
    @GET("lineAlarmFault/getPatchMaterial")
    Observable<Result<ItemHandAdd>> getItemHandAddDatas(@Query("condition") String condition);

    //确认手补件item数据
    @GET("lineAlarmFault/confirmPatchMaterial")
    Observable<Result> getItemHandAddConfirm(@Query("condition") String condition);


    //接口PCB库房发料
    Observable<List<ListWarning>> getListWarning();//获取发料列表


    Observable<String> getStoreRoomSuccess();//是否成功?


    Observable<List<ListWarning>> getWarningNumberSuccess();//获取Warning列表的数量


    @GET("webapi/pcb/management/inbound/location")
    Observable<Light> onLight(@Query("param") String s);//点灯操作

    @GET("webapi/pcb/management/inbound")
    Observable<Success> putInStorage(@Query("param") String s);//入库操作

    @GET("pcb/management/alarminfo")
    Observable<AllQuery> getWarning();//获取所有预警信息

    @GET("pcb/management/scheduleinfo")
    Observable<AllQuery> getArrange();//获取所有排程信息

    @GET("pcb/management/outbound/alarm/bill")
    Observable<OutBound> outBound(@Query("id") int id, @Query("sapWorkOrderId") String sapWorkOrderId, @Query("partNum") String partNum, @Query("amount") int amount);//预警仓库发料清单

    @GET("pcb/management/outbound/schedule/bill")
    Observable<OutBound> getScheduleDetailed(@Query("id") int id, @Query("sapWorkOrderId") String sapWorkOrderId, @Query("partNum") String partNum, @Query("amount") int amount);//获取发料详情列表

    @GET("pcb/management/outbound/bill")
    Observable<OutBound> outBound(@Query("param") String s);//仓库发料清单

    //Observable<PcbNumber> getPcbNumber(@Query("serial") String s);//获取实际数量
    @GET("pcb/management/capacity")
    Observable<PcbNumber> getPcbNumber(@Query("serial") String s);//获取实际数量

    //    Observable<PcbNumber> getPcbNumber(@Query("serial") String s);//获取实际数量
    @GET("webapi/pcb/management/outbound")
    Observable<Success> getPcbSuccess(@Query("param") String s);//出料操作

    @GET("pcb/management/outbound/light/close")
    Observable<Success> closeLight(@Query("subShelfCode") String s);//关灯操作

    @GET("pcb/management/outbound/alarm/submit")
    Observable<Success> getAlarmOutSubmit(@Query("alarmId") int scheduleId);//提交

    @GET("pcb/management/outbound/schedule/submit")
    Observable<Success> getOutSubmit(@Query("scheduleId") int scheduleId);//提交

    @GET("pcb/management/outbound/alarm/submit")
    Observable<Success> getAlarmSuccessState(@Query("sapWorkOrderId") String sapWorkOrderId, @Query("alarmId") int alarmId);//预警出库完成

    @GET("pcb/management/outbound/schedule/submit")
    Observable<Success> getScheduleSuccessState(@Query("scheduleId") int scheduleId);//预警出库完成

    @GET("pcb/inventory/start")
    Observable<Success> onStartWork();//开始盘点

    @GET("pcb/inventory/ongoing")
    Observable<OnGoing> onGoing();//盘点界面判断

    @GET("pcb/inventory/end")
    Observable<Success> onEnd();//结束盘点

    @GET("pcb/inventory/detail")
    Observable<CheckStock> getCheckStock(@Query("subShelfCode") String s);//获取盘点列表

    @GET("pcb/inventory/subinventory")
    Observable<Success> getCheckNumber(@Query("id") int id, @Query("realCount") int realCount);//发送盘点数量

    @GET("pcb/inventory/alteration")
    Observable<Success> getError(@Query("boxSerial") String boxSerial, @Query("subShelfCode") String subShelfCode);//发送盘点异常

    @GET("pcb/inventory/sub/exception")
    Observable<ExceptionsBean> getException(@Query("subShelfCode") String boxSerial);//盘点结果

    @GET("pcb/inventory/exception")
    Observable<InventoryExecption> getInventoryException();//获取总结

    @GET("pcb/inventory/submit")
    Observable<Success> getSubmit(@Query("subShelfCode") String boxSerial);//发送盘点结果


    Observable<String> getCheckStockSuccess();//是否成功?

    //Observable<List<MantissaWarehousePutstorage>> getBeginput();

    /**
     * @description :
     * 1.故障处理预警
     * 2.仓库房备料
     * 3. 位数仓备料
     * @author :  V.Wenju.Tian
     * @date : 2017/1/21 13:53
     */
    //故障处理预警
    @GET("lineAlarmFault/getSeriousFaultInfos")
    Observable<FaultMessage> getFalutMessages(@Query("condition") String s);

    @GET("lineAlarmFault/faultSolutionList")
    Observable<SolutionMessage> getSolutionMessage(@Query("condition") String s);

    @GET("lineAlarmFault/faultSolutionDetailList")
    Observable<FaultSolutionMessage> getDetailSolutionMessage(@Query("condition") String s);

    @GET("lineAlarmFault/resolveFault")
    Observable<BaseEntity> resolveFault(@Query("condition") String content);

    @GET("lineAlarmFault/addFaultSolution")
    Observable<BaseEntity> addSolution(@Query("condition") String content);

    //仓库房
    @GET("SMM/WareHIssue/qPrepCarIDByWorkOrder")
    Observable<MaterialCar> queryMaterialCar(@Query("condition") String content);

    @GET("SMM/WareHIssue/bindPrepCarIDByWorkOrder")
    Observable<BindPrepCarIDByWorkOrderResult> bindMaterialCar(@Query("condition") String content);

    @GET("SMM/WareHIssue/issureToWareh")
    Observable<Result<StorageDetails>> issureToWareh(@Query("condition") String content);

    @GET("SMM/WareHIssue/issureToWarehFinish")
    Observable<IssureToWarehFinishResult> issureToWarehFinish(@Query("condition") String content);

    @GET("SMM/WareHIssue/startWareHIssure")
    Observable<Result<StorageDetails>> getStorageDetails(@Query("condition") String argument);

    @GET("SMM/WareHIssue/jumpMaterials")
    Observable<Result<StorageDetails>> jumpMaterials(@Query("condition") String mS);

    @GET("SMM/WareHIssue/sureCompleteIssue")
    Observable<IssureToWarehFinishResult> sureCompleteIssue();

    //仓库房扣账
    @GET("SMM/WareHIssue/deduction")
    Observable<Result> deduction(@Query("condition") String mS);

    //尾数仓备料
    @GET("SMM/IssueMana/querymantiss")
    Observable<MantissaWarehouseReady> getMantissaWarehouseReadyDates();

    //尾数仓备料详情
    @GET("SMM/WareHIssue/startMantissIssue")
    Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(@Query("condition") String bind);

    //料盘绑定标签
    @GET("SMM/ManToWareh/materBoundLabel")
    Observable<MantissaWarehousePutstorageResult> getBingingLable(@Query("condition") String bind);

    //查询尾数仓备料车
    @GET("SMM/WareHIssue/qPrepCarIDByWorkOrder")
    Observable<MaterialCar> getFindCar(@Query("condition") String bind);

    //绑定尾数仓备料车
    @GET("SMM/WareHIssue/bindPrepCarIDByWorkOrder")
    Observable<MaterialCar> getbingingCar(@Query("condition") String bind);

    //尾数仓发料
    @GET("SMM/WareHIssue/mantissIssue")
    Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(@Query("condition") String bind);

    //尾数仓扣账
    @GET("SMM/WareHIssue/deduction")
    Observable<Result> debit();

    //尾数仓发料完成
    @GET("SMM/WareHIssue/completeMantissIssue")
    Observable<IssureToWarehFinishResult> getMantissaWareOver(String s);

    //仓库房备料和尾数仓选择
    @GET("SMM/IssueMana/queryWarehousePart")
    Observable<Result<StoreEntity>> getStorageSelect();

    //根据小仓库分区查询需要备料的工单列表
    @GET("SMM/IssueMana/queryWorkOrder")
    Observable<Result<StorageReady>> getStorageReadyDates(@Query("condition") String argument);

    //TODO shaoqiang,8Interfance
//    @GET("http://172.22.34.100:8081/sms/jig/life/use/loan/order/list/page")
    @GET("sms/jig/life/use/loan/order/list/page")
    Observable<JsonProductBorrowRoot> getProductWorkItem(@Query("pageSize") int pageSize, @Query("pageCurrent") int pageCurrent);

    //    @GET("http://172.22.34.100:8081/sms/jig/life/use/loan/jig")
    @GET("sms/jig/life/use/loan/jig")
    Observable<JsonProductRequestToolsRoot> getProductToolsInfoItem(@Query("condition") String condition);

    //    @GET("http://172.22.34.100:8081/sms/jig/life/use/loan/jig")
    @GET("sms/jig/life/use/loan/jig")
    Observable<JsonProduct_mToolsRoot> getProduct_mToolsInfo(@Query("pageSize") int pageSize, @Query("pageCurrent") int pageCurrent, @Query("condition") String condition_and_jigTypeID);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/instore/verify")
    @GET("webapi/sms/jig/life/use/instore/verify")
    Observable<JsonProductToolsLocation> getLocationVerify(@Query("param") String param);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/instore/submit")
    @GET("webapi/sms/jig/life/use/instore/submit")
    Observable<JsonProductToolsLocation> getLocationSubmit(@Query("param") String param);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/back/submit")
    @GET("webapi/sms/jig/life/use/back/submit")
    Observable<JsonProductBackRoot> getProductToolsBack(@Query("param") String param);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/loan/verify")
    @GET("webapi/sms/jig/life/use/loan/verify")
    Observable<JsonProductToolsVerfyRoot> getProductToolsVerfy(@Query(("param")) String param);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/loan/submit")
    @GET("webapi/sms/jig/life/use/loan/submit")
    Observable<JsonProductToolsLocation> getProductToolsBorrowSubmit(@Query("param") String param);


    //liuzhenyu
    //尾数仓退入主仓库
    @GET("SMM/ManToWareh/queryReturnedWarehList")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

    //点击清理按钮
    @GET("SMM/ManToWareh/triggerListUpdate")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate();

    //尾数仓点击开始入库
    @GET("SMM/ManToWareh/startReturnedWareh")
    Observable<MantissaWarehousePutstorageResult> getbeginPut();

    //尾数仓点击开始入库上架位完成
    @GET("SMM/ManToWareh/materToShel")
    Observable<MantissaWarehousePutstorageResult> getUpLocation(@Query("condition") String bind);

    //确定点击下一个架位
    @GET("SMM/ManToWareh/sureNextShelf")
    Observable<MantissaWarehousePutstorageResult> getYesNext();

    //确定点击完成
    @GET("SMM/ManToWareh/sureComplete")
    Observable<MantissaWarehousePutstorageResult> getYesok();

    //尾数仓入库
    @GET("SMM/MantissaStorage/qMantissaStorageList")
    Observable<MantissaWarehouseReturnResult> getMantissaWarehouseReturn();

    //尾数仓查询料盘的位置
    @GET("SMM/MantissaStorage/findshelf")
    Observable<MantissaWarehouseReturnResult> getMaterialLocation(@Query("condition") String bind);

    //尾数仓查料盘入库
    @GET("SMM/MantissaStorage/mantissaStorage")
    Observable<MantissaWarehouseReturnResult> getputinstrage(@Query("condition") String bind);

    /**
     * @description :
     * 1.更新
     * 2.超领
     * 3.上模组
     * 4.下模组
     * @author :  shufeng.wu
     * @date : 2017/1/21 13:46
     */
    //更新
    @GET(API.bundleJsonUrl)
    Observable<Update> getUpdate();

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //超领
    @GET("SMM/ExcessManagement/qExcessList")
    Observable<OverReceiveWarning> getOverReceiveItems();

    @GET("SMM/ExcessManagement/execessIssure")
    Observable<OverReceiveWarning> getOverReceiveItemSend(@Query("condition") String content);

    @GET("SMM/ExcessManagement/delivery")
    Observable<OverReceiveWarning> getOverReceiveItemSendArrive(@Query("condition") String content);

    @GET("SMM/ExcessManagement/debit")
    Observable<OverReceiveDebitResult> getOverReceiveDebit();

    //上模组
    @GET("SMM/plugmod/getProductionLines")
    Observable<ModuleUpWarningItem> getModuleUpWarningItems();

    @GET("SMM/plugmod/getModsByWordOrder")
    Observable<ModuleUpBindingItem> getModuleUpBindingItems(@Query("condition") String condition);

    @GET("SMM/plugmod/updateMod")
    Observable<ModuleUpBindingItem> getMaterialAndFeederBindingResult(@Query("condition") String condition);

    //下模组
    @GET("SMM/unplugmod/getProductionLines")
    Observable<ModuleDownWarningItem> getModuleDownWarningItems();

    //smm/unplugmod/getModelList?condition={"work_order":"1","side":"A"}
    //@GET("smm/unplugmod/getVirtualLine")
    @GET("SMM/unplugmod/getModelList")
    Observable<VirtualLineBindingItem> getVirtualLineBindingItems(@Query("condition") String condition);

    @GET("SMM/unplugmod/getModsByWordOrder")
    Observable<ModuleDownDetailsItem> getModuleDownDetailsItems(@Query("condition") String condition);

    @GET("SMM/unplugmod/feederMaintain")
    Observable<ModuleDownMaintain> getModuleDownMaintainResult(@Query("condition") String condition);

    @GET("SMM/unplugmod/bindVirtualLine")
    Observable<VirtualLineBindingItem> getVirtualBindingResult(@Query("condition") String condition);


    //@GET("SMM/unplugmod/getModNumByMaterial")
    //Observable<ModNumByMaterialResult> getModNumByMaterial(@Query("material_num") String material_num, @Query("workOrderNum") String num);
}
