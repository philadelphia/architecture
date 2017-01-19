package com.delta.smt.api;


import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.FalutMesage;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.FaultSolutionMessage;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
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
import com.delta.smt.entity.MaterialAndFeederBindingResult;
import com.delta.smt.entity.ModNumByMaterialResult;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveWarning;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.ProductToolsBack;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.SolutionMessage;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.Success;
import com.delta.smt.entity.Update;
import com.delta.smt.entity.User;
import com.delta.smt.entity.VirtualBindingResult;
import com.delta.smt.entity.VirtualLineBindingItem;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.item.TitleNumber;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
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


    /*
     获取feeder入库列表
     tao.zt.zhang
     */
    @GET("http://172.22.34.6:8081/SMM/FeederBuffStorage/qFeederBuffStorageList")
    Observable<Result<FeederCheckInItem>> getAllCheckedInFeeders();

    @GET("http://172.22.34.6:8081/SMM/FeederBuffStorage/qMaterialPlace")
    Observable<Result<FeederCheckInItem>> getFeederLocation(@Query("condition") String condition);

    @GET("http://172.22.34.6:8081/SMM/FeederBuffStorage/feederBuffStorages")
    Observable<Result<FeederCheckInItem>> getFeederCheckInTime(@Query("condition") String condition);

    //获取所有的Feeder备料工单列表
    @GET("http://172.22.34.34:8081/SMM/Buffer/querySchedule")
    Observable<Result<FeederSupplyWarningItem>> getAllSupplyWorkItems();

    //获取指定工单的Feeder备料列表
    @GET("http://172.22.34.34:8081/SMM/Buffer/startBufferIssue")
    Observable<Result<FeederSupplyItem>> getAllToBeSuppliedFeeders(@Query("condition") String workID);

    //获取Feeder备料时间
    @GET("http://172.22.34.34:8081/SMM/Buffer/bufferIssue")
    Observable<Result<FeederSupplyItem>> getFeederSuppliedTime(@Query("condition") String workID);

    @POST
    Observable<Result> upLoadFeederSupplyResult();

    @POST
    Observable<List<FeederSupplyItem>> getAllToBeCheckedInFeeders();

    /*预警模块的模拟接口*/
    Observable<List<ItemProduceLine>> getLineDatas();

    Observable<TitleNumber> getTitleDatas();

    //Zhangfuxiang
    //请求预警中item数据
    @GET("http://172.22.34.10:8081/lineAlarmFault/alarmFaultInfos")
    Observable<ProduceWarning> getItemWarningDatas(@Query("condition") String condition);

    //Zhangfuxiang
    //请求故障中item数据
    @GET("http://172.22.34.10:8081/lineAlarmFault/alarmFaultInfos")
    Observable<ProduceWarning> getItemBreakDownDatas(@Query("condition") String condition);

    //Zhangfuxiang
    //请求消息中item数据
    @GET("http://172.22.34.10:8081/lineAlarmFault/alarmFaultInfos")
    Observable<ProduceWarning> getItemInfoDatas(@Query("condition") String condition);

    //Zhangfuxiang
    //确认信息中item
    @GET("http://172.22.34.10:8081/lineAlarmFault/confirmMessage")
    Observable<Result> getItemInfoConfirm(@Query("condition") String condition);


    //Zhangfuxiang
    //确认预警中item
    @GET("http://172.22.34.10:8081/lineAlarmFault/confirmAlarmMessage")
    Observable<Result> getItemWarningConfirm(@Query("condition") String condition);


    //Zhangfuxiang
    //提交预警中扫码数据
    @GET("http://172.22.34.10:8081/lineAlarmFault/relayMaterial")
    Observable<Result> getBarcodeInfo(@Query("condition") String condition);

    //Zhangfuxiang
    //请求手补件item数据
    @GET("http://172.22.34.10:8081/lineAlarmFault/getPatchMaterial?condition={}")
    Observable<Result<ItemHandAdd>> getItemHandAddDatas();

    //Zhangfuxiang
    //确认手补件item数据
    @GET("http://172.22.34.10:8081/lineAlarmFault/confirmPatchMateria")
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
    Observable<OutBound> outBound(@Query("id") int id,@Query("sapWorkOrderId") String sapWorkOrderId,@Query("partNum") String partNum,@Query("amount") int amount);//预警仓库发料清单
    @GET("pcb/management/outbound/schedule/bill")
    Observable<OutBound> getScheduleDetailed(@Query("sapWorkOrderId") String sapWorkOrderId,@Query("partNum") String partNum,@Query("amount") int amount);//获取发料详情列表

    @GET("pcb/management/outbound/bill")
    Observable<OutBound> outBound(@Query("param") String s);//仓库发料清单

    Observable<PcbNumber> getPcbNumber(@Query("serial") String s);//获取实际数量
    @GET("webapi/pcb/management/outbound")
    Observable<Success> getPcbSuccess(@Query("param") String s);//出料操作
    @GET("pcb/management/outbound/alarm/submit")
    Observable<Success> getAlarmSuccessState(@Query("sapWorkOrderId") String sapWorkOrderId,@Query("alarmId") int alarmId );//预警出库完成
    @GET("pcb/management/outbound/schedule/submit")
    Observable<Success> getScheduleSuccessState(@Query("sapWorkOrderId") String sapWorkOrderId);//预警出库完成
    @GET("pcb/inventory/detail")
    Observable<CheckStock> getCheckStock(@Query("subShelfSerial") String s);//获取盘点列表
     @GET("pcb/inventory/subinventory")
    Observable<Success> getCheckNumber(@Query("id") int id,@Query("realCount") int realCount);//发送盘点数量
    @GET("pcb/inventory/alteration")
    Observable<Success> getError(@Query("boxSerial") String boxSerial,@Query("subShelfCode") String subShelfCode);//发送盘点异常
    @GET("pcb/inventory/exception")
    Observable<Success> getException(@Query("subShelfSerial") String boxSerial);//盘点结果
    @GET("pcb/inventory/submit")
    Observable<Success> getSubmit(@Query("subShelfSerial") String boxSerial);//发送盘点结果



     Observable<String> getCheckStockSuccess();//是否成功?

    //Observable<List<MantissaWarehousePutstorage>> getBeginput();

    //故障处理预警
    @GET("http://172.22.34.19:8081/lineAlarmFault/getSeriousFaultInfos")
    Observable<FaultMessage> getFalutMessages(@Query("condition") String s);

    @GET("http://172.22.34.19:8081/lineAlarmFault/faultSolutionList")
    public Observable<SolutionMessage> getSolutionMessage(@Query("condition") String s);

    @GET("http://172.22.34.19:8081/lineAlarmFault/faultSolutionDetailList")
    public Observable<FaultSolutionMessage> getDetailSolutionMessage(@Query("condition") String s);

    @GET("http://172.22.34.19:8081/lineAlarmFault/resolveFault")
    Observable<BaseEntity> resolveFault(@Query("condition") String content);

    @GET("http://172.22.34.19:8081/lineAlarmFault/addFaultSolution")
    Observable<BaseEntity> addSolution(@Query("condition") String content);

    //更新
    @GET(API.bundleJsonUrl)
    Observable<Update> getUpdate();


    //下载更新
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /*@GET("http://172.22.35.155:8081/smm/plugmod/getProductionLines")
    Observable<ModuleUpWarningItem> getModuleUpWarningItems(@Query("workOrderNum") String content);

   @GET("http://172.22.35.155:8081/smm/unplugmod/getProductionLines")
    Observable<ModuleDownWarningItem> getModuleDownWarningItems(@Query("workOrderNum") String content);*/

    @GET("http://172.22.35.155:8081/smm/plugmod/getProductionLines")
    Observable<ModuleUpWarningItem> getModuleUpWarningItems();

    @GET("http://172.22.35.155:8081/smm/unplugmod/getProductionLines")
    Observable<ModuleDownWarningItem> getModuleDownWarningItems();


    @GET("http://172.22.35.155:8081/smm/plugmod/getModsByWordOrder")
    Observable<ModuleUpBindingItem> getModuleUpBindingItems(@Query("workOrderNum") String content);

    @GET("http://172.22.35.155:8081/smm/unplugmod/getVirtualLine")
    Observable<VirtualLineBindingItem> getVirtualLineBindingItems(@Query("workOrderNum") String content);

    @GET("http://172.22.35.155:8081/smm/unplugmod/getModsByWordOrder")
    Observable<ModuleDownDetailsItem> getModuleDownDetailsItems(@Query("workOrderNum")String content);

    //TODO shaoqiang,8Interfance
    @GET("http://172.22.34.122:8081/sms/jig/life/use/loan/order/list/page")
    Observable<JsonProductBorrowRoot> getProductWorkItem(@Query("pageSize") int pageSize, @Query("pageCurrent") int pageCurrent);

    @GET("http://172.22.34.122:8081/sms/jig/life/use/loan/jig")
    Observable<JsonProductRequestToolsRoot> getProductToolsInfoItem(@Query("condition") String condition);

    @GET("http://172.22.34.122:8081/sms/jig/life/use/loan/jig")
    Observable<JsonProduct_mToolsRoot> getProduct_mToolsInfo(@Query("pageSize") int pageSize, @Query("pageCurrent") int pageCurrent, @Query("condition") String condition_and_jigTypeID);

    @GET("http://172.22.34.122:8081/webapi/sms/jig/life/use/instore/verify")
    Observable<JsonProductToolsLocation> getLocationVerify(@Query("param")String param);

    @GET("http://172.22.34.122:8081/webapi/sms/jig/life/use/instore/submit")
    Observable<JsonProductToolsLocation> getLocationSubmit(@Query("param")String param);

    @GET("http://172.22.34.122:8081/webapi/sms/jig/life/use/back/submit")
    Observable<JsonProductBackRoot> getProductToolsBack(@Query("param")String param);

    @GET("http://172.22.34.122:8081/webapi/sms/jig/life/use/loan/verify")
    Observable<JsonProductToolsVerfyRoot> getProductToolsVerfy(@Query(("param"))String param);

    @GET("http://172.22.34.122:8081/webapi/sms/jig/life/use/loan/submit")
    Observable<JsonProductToolsLocation> getProductToolsBorrowSubmit(@Query("param")String param);


    //仓库房备料和尾数仓
    //Zhangfuxiang
    @GET("http://172.22.34.6:8081/SMM/IssueMana/queryWarehousePart")
    Observable<Result<String>> getStorageSelect();

    //  Observable<List<MantissaWarehouseDetailsResult>> getMantissaWarehouseDetails();

    // Observable<List<MantissaWarehouseReturnResult>> getMantissaWarehouseReturn();




    //Zhangfuxiang
    @GET("http://172.22.34.6:8081/SMM/IssueMana/queryWorkOrder")
    Observable<Result<StorageReady>> getStorageReadyDates(@Query("condition") String argument);


    Observable<List<StorageDetails>> getStorageDetails();


    //Zhangfuxiang
    @GET("http://172.22.34.40:8081/SMM/Issue/startIssue")
    Observable<Result<StorageDetails>> getStorageDetails(@Query("condition") String argument);


    //liuzhenyu
    //尾数仓退入主仓库
    @GET("http://172.22.34.34:8081/SMM/ManToWareh/queryReturnedWarehList")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

    //点击清理按钮
    @GET("http://172.22.34.34:8081/SMM/ManToWareh/triggerListUpdate")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate();

    //尾数仓点击开始入库
    @GET("http://172.22.34.34:8081/SMM/ManToWareh/startStorage")
    Observable<MantissaWarehousePutstorageResult> getbeginPut();

    //尾数仓点击开始入库上架位完成
    @GET("http://172.22.34.34:8081/SMM/ManToWareh/materToShel")
    Observable<MantissaWarehousePutstorageResult> getUpLocation(@Query( "condition") String bind);


    //尾数仓入库
    @GET("http://172.22.34.22:8081/SMM/MantissaStorage/qMantissaStorageList")
    Observable<MantissaWarehouseReturnResult> getMantissaWarehouseReturn();

    //尾数仓查询料盘的位置
    @GET("http://172.22.34.22:8081/SMM/MantissaStorage/qMaterialPlace")
    Observable<MantissaWarehouseReturnResult> getMaterialLocation(@Query( "condition") String bind);

   //尾数仓查料盘入库
   @GET("http://172.22.34.22:8081/SMM/MantissaStorage/qMaterialPlace")
   Observable<MantissaWarehouseReturnResult> getputinstrage(@Query( "condition") String bind);

    //尾数仓备料
    @GET("http://172.22.34.34:8081/SMM/IssueMana/querymantiss")
    Observable<MantissaWarehouseReady> getMantissaWarehouseReadyDates();

    //尾数仓备料详情
    @GET("http://172.22.34.34:8081/SMM/IssueMana/queryMantissIssue")
    Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(@Query("condition") String bind);

    //料盘绑定标签
    @GET("http://172.22.34.34:8081/SMM/ManToWareh/materBoundLabel")
    Observable<MantissaWarehousePutstorageResult> getBingingLable(@Query("condition") String bind);




    @GET("http://172.22.34.22:8081/SMM/ExcessManagement/qExcessList")
    Observable<OverReceiveWarning> getOverReceiveItems();

    @GET("http://172.22.34.22:8081/SMM/ExcessManagement/execessIssure")
    Observable<OverReceiveWarning> getOverReceiveItemSend(@Query("condition") String content);

    @GET("http://172.22.34.22:8081/SMM/ExcessManagement/delivery")
    Observable<OverReceiveWarning> getOverReceiveItemSendArrive(@Query("condition") String content);

    @GET("http://172.22.35.155:8081/SMM/WareHIssue/debit")
    Observable<OverReceiveDebitResult> getOverReceiveDebit();

    @GET("http://172.22.35.155:8081/smm/plugmod/updateMod")
    Observable<MaterialAndFeederBindingResult> getMaterialAndFeederBindingResult(@Query("id")String id,@Query("feeder")String feederID);

    @GET("http://172.22.35.155:8081/smm/unplugmod/updateMod")
    Observable<ModuleDownMaintain> getModuleDownMaintainResult(@Query("ids")String content);

    @GET("http://172.22.35.155:8081/smm/unplugmod/bindVirtualLine")
    Observable<VirtualBindingResult> getVirtualBindingResult(@Query("id")String id, @Query("vitualId")String vitualId);

    @GET("http://172.22.35.155:8081/smm/unplugmod/getModNumByMaterial")
    Observable<ModNumByMaterialResult> getModNumByMaterial(@Query("material_num") String material_num);
}
