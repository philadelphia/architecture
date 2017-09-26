package com.delta.smt.api;


import com.delta.smt.entity.AcceptMaterialResult;
import com.delta.smt.entity.AddSuccess;
import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.FaultFilter;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.FaultSolutionMessage;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.InventoryExecption;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.ItemHandAdd;
import com.delta.smt.entity.JsonLocationVerfyRoot;
import com.delta.smt.entity.JsonProductBackRoot;
import com.delta.smt.entity.JsonProductBorrowRoot;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.JsonProductToolsLocationRoot;
import com.delta.smt.entity.JsonProductToolsSubmitRoot;
import com.delta.smt.entity.JsonProductToolsVerfyRoot;
import com.delta.smt.entity.JsonProduct_mToolsRoot;
import com.delta.smt.entity.LedLight;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MantissaWarehousePutstorageBindTagResult;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MantissaWarehouseReturnResult;
import com.delta.smt.entity.ManualDebitBean;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.ModuleDownDebit;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.OverReceiveDebitList;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveWarning;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.QualityManage;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ResultFault;
import com.delta.smt.entity.ResultFeeder;
import com.delta.smt.entity.SolutionMessage;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.StoreEntity;
import com.delta.smt.entity.Success;
import com.delta.smt.entity.UpLoadEntity;
import com.delta.smt.entity.Update;
import com.delta.smt.entity.User;
import com.delta.smt.entity.VirtualLineItem;
import com.delta.smt.entity.VirtualModuleID;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.entity.bindmaterial.BindCarBean;
import com.delta.smt.entity.bindmaterial.BindLabelBean;
import com.delta.smt.entity.bindmaterial.FinishPda;
import com.delta.smt.entity.bindmaterial.ScanMaterialPanBean;
import com.delta.smt.entity.bindmaterial.StartStoreBean;
import com.delta.smt.entity.bindmaterial.WheatherBindStart;
import com.delta.smt.entity.production_warining_item.ItemAcceptMaterialDetail;
import com.delta.smt.entity.production_warining_item.ItemProduceLine;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    @GET("SMM/FeederBuffStorage/qFeederBuffStorageList")
    Observable<Result<FeederCheckInItem>> getAllCheckedInFeeders();

    //feeder入Feeder缓冲区
    @GET("ams/smm/unplugmodcontroller/feederbuffstorage")
    Observable<Result<ModuleDownDetailsItem>> getFeederCheckInTime(@Query("condition") String condition);


    //重置Feeder发料状态
    @POST("ams/smm/buffer/completebufferissue")
    Observable<Result> resetFeederSupplyStatus(@Query("value") String condition);

    //Feeder 发料跳过MES
    @POST("ams/smm/plugmodcontroller/updateprepworkorderstatus")
    Observable<Result> jumpMES(@Query("value") String value);


    //获取下模组列表
    @GET("SMM/FeederBuffStorage/feederBuffStorage")
    Observable<ModuleDownDetailsItem> getDownModuleList(@Query("condition") String condition);

    //获取所有的Feeder备料工单列表
    @GET("ams/smm/buffer/queryschedule")
    Observable<Result<FeederSupplyWarningItem>> getAllSupplyWorkItems();

    //获取指定工单的Feeder备料列表
    @POST("ams/smm/buffer/startbufferissue")
    Observable<Result<FeederSupplyItem>> getAllToBeSuppliedFeeders(@Query("value") String value);

    //下模组灭灯
    @GET("ams/smm/unplugmodcontroller/turnoffalllight")
    Observable<Result> moduleDownlightOff(@Query("condition") String condition);

    //获取Feeder备料时间
    @POST("ams/smm/buffer/bufferissue")
    Observable<Result<FeederSupplyItem>> getFeederInsertionToSlotTimeStamp(@Query("value") String condition);

    //上传feeder备料上模组结果
    @GET("SMM/Buffer/completeBufferIssue")
    Observable<ResultFeeder> upLoadFeederSupplyResult();

    //获取没有上传到MES的列表
    @GET("ams/smm/plugmodcontroller/getneeduploadtomesmaterials")
    Observable<BaseEntity<UpLoadEntity>> getUnUpLoadToMESList(@Query("condition") String condition);

    //上传feeder备料到MES
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/plugmodcontroller/uploadtomes")
    Observable<Result> upLoadFeederSupplyToMES(@Field("value") String value);

    @GET("ams/smm/buffer/light/off")
    Observable<Result> lightOff(@Query("condition") String condition);

    //Feeder发料手动扣账
    @POST("ams/smm/warehissue/deduction")
    Observable<Result<DebitData>> deductionAutomatically(@Query("value") String value);

    //Feeder发料获取没有扣账的列表
    @GET("ams/smm/warehissue/getnodebit")
    Observable<Result<DebitData>> getUnDebitedItemList(@Query("condition") String condition);

    //下模组获取没有扣账的列表
    @GET("ams/smm/feederbuffstorage/getnodebit")
    Observable<Result<ModuleDownDebit>> getModuleListUnDebitList(@Query("condition") String condition);

    //下模组手动扣账
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/feederbuffstorage/deduction")
    Observable<Result<ModuleDownDebit>> debitManually(@Field("value") String value);


    //超领
    //超领
    //@GET("SMM/ExcessManagement/qExcessList")
    @GET("ams/smm/excessmanagement/qexcesslist")
    Observable<OverReceiveWarning> getOverReceiveItems();

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/excessmanagement/execessissure")
    Observable<OverReceiveWarning> getOverReceiveItemSend(@Field("value") String value);


    @GET("ams/smm/excessmanagement/getnodebit")
    Observable<OverReceiveDebitList> getNoDebit();

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/excessmanagement/debit")
    Observable<OverReceiveDebitList> debit(@Field("value") String value);

    @GET("ams/smm/excessmanagement/debit")
    Observable<OverReceiveDebitResult> getOverReceiveDebit();

    //上模组排程
    @GET("ams/smm/plugmodcontroller/getproductionlines")
    Observable<Result<ModuleUpWarningItem>> getModuleUpWarningItems();

    //对应工单的上模组列表
    @GET("ams/smm/plugmodcontroller/getmodsbywordorder")
    Observable<Result<ModuleUpBindingItem>> getModuleUpBindingItems(@Query("condition") String condition);

    //上模组,料盘feeder绑定
    @GET("ams/smm/plugmodcontroller/updatemod")
    Observable<Result<ModuleUpBindingItem>> getMaterialAndFeederBindingResult(@Query("condition") String condition);

    @POST("ams/smm/plugmodcontroller/uploadtomes")
    @FormUrlEncoded
    Observable<Result> upLoadToMesManually(@Field("value") String value);

    //获取所有需要上传到MES的数据
    @POST("")
    Observable<Result> getAllItemsNeedTobeUpLoadToMES(String value);

    //下模组排程
    @GET("ams/smm/unplugmodcontroller/getproductionlines")
    Observable<Result<ModuleDownWarningItem>> getModuleDownWarningItems();

    //虚拟线体绑定列表
    @GET("ams/smm/unplugmodcontroller/getmodellist")
    Observable<Result<VirtualLineItem>> getVirtualLineBindingItems(@Query("condition") String condition);

    //虚拟线体绑定接口
    @GET("ams/smm/unplugmodcontroller/bindvirtualline")
    Observable<Result<VirtualLineItem>> getVirtualBindingResult(@Query("condition") String condition);

    @GET("ams/smm/unplugmodcontroller/locationmodel")
    Observable<VirtualModuleID> getVirtualModuleID(@Query("condition") String condition);


    //对应工单的下模组列表
    @GET("/ams/smm/unplugmodcontroller/getmodsbywordorder")
    Observable<Result<ModuleDownDetailsItem>> getModuleDownDetailsItems(@Query("condition") String condition);

    //Feeder保养
    @GET("ams/smm/unplugmodcontroller/feedermaintain")
    Observable<Result> getModuleDownMaintainResult(@Query("condition") String condition);


    /**
     * @description :
     * 1.生产中预警
     * 2.手补件通知
     * @author : Fuxiang.Zhang
     * @date : 2017/1/21 14:41
     */
    //请求产线列表数据
    @GET("ams/smm/linemanage/querylines")
    Observable<Result<ItemProduceLine>> getLineDatas();

    //请求预警，故障，消息的item数量
    @GET("ams/smm/linealarmfault/alarmfaultinfos")
    Observable<ProduceWarning> getTitleDatas(@Query("condition") String condition);

    //请求预警中item数据
    @GET("ams/smm/linealarmfault/alarmfaultinfos")
    Observable<ProduceWarning> getItemWarningDatas(@Query("condition") String condition);

    //请求接料预警详情页面item数据
    @GET("ams/smm/linealarmfault/getlinematerialconnectdetail")
    Observable<ItemAcceptMaterialDetail> getAcceptMaterialsItemDatas(@Query("condition") String condition);

    //提交新旧流水号
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/linealarmfault/doconnectmaterial")
    Observable<AcceptMaterialResult> commitSerialNumber(@Field("value") String condition);

    //请求关灯
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/linealarmfault/offmateriallight")
    Observable<Result> requestCloseLight(@Field("value") String condition);

    //请求故障中item数据
    @GET("ams/smm/linealarmfault/alarmfaultinfos")
    Observable<ProduceWarning> getItemBreakDownDatas(@Query("condition") String condition);

    //请求消息中item数据
    @GET("ams/smm/linealarmfault/alarmfaultinfos")
    Observable<ProduceWarning> getItemInfoDatas(@Query("condition") String condition);

    //确认信息中item
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/smm/linealarmfault/confirmmessage")
    Observable<Result> getItemInfoConfirm(@Field("value") String condition);

    //确认预警中item
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/smm/linealarmfault/confirmalarmmessage")
    Observable<Result> getItemWarningConfirm(@Field("value") String condition);

    //提交预警中扫码数据
    @GET("lineAlarmFault/relayMaterial")
    Observable<Result> getBarcodeInfo(@Query("condition") String condition);

    //请求手补件item数据
    @GET("ams/smm/linealarmfault/getpatchmaterial")
    Observable<Result<ItemHandAdd>> getItemHandAddDatas(@Query("condition") String condition);

    //确认手补件item数据
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/smm/linealarmfault/confirmpatchmaterial")
    Observable<Result> getItemHandAddConfirm(@Field("value") String condition);


    //接口PCB库房发料
    Observable<List<ListWarning>> getListWarning();//获取发料列表


    Observable<String> getStoreRoomSuccess();//是否成功?


    Observable<List<ListWarning>> getWarningNumberSuccess();//获取Warning列表的数量


    @GET("ams/pcb/management/inbound/location")
    Observable<Light> onLight(@Query("condition") String s);//点灯操作

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/pcb/management/inbound")
    Observable<Success> putInStorage(@Field("value") String s);//入库操作

    @GET("ams/pcb/management/alarminfo")
    Observable<AllQuery> getWarning();//获取所有预警信息

    @GET("ams/pcb/management/scheduleinfo")
    Observable<AllQuery> getArrange();//获取所有排程信息

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/management/outbound/lights/close")
    Observable<Success> closeLights(@Field("value") String id);//退出后又进入

    @GET("ams/pcb/management/outbound/alarm/bill")
    Observable<OutBound> outBound(@Query("condition") String s);//预警仓库发料清单

    @GET("ams/pcb/management/outbound/schedule/bill")
    Observable<OutBound> getScheduleDetailed(@Query("condition") String s);//获取发料详情列表

    @GET("ams/pcb/management/outbound/bill")
    Observable<OutBound> outBounds(@Query("condition") String s);//仓库发料清单

    //Observable<PcbNumber> getPcbNumber(@Query("serial") String s);//获取实际数量
    @GET("ams/pcb/management/capacity")
    Observable<PcbNumber> getPcbNumber(@Query("serial") String s);//获取实际数量

    //    Observable<PcbNumber> getPcbNumber(@Query("serial") String s);//获取实际数量
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/pcb/management/outbound")
    Observable<Success> getPcbSuccess(@Field("value") String s);//出料操作

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/management/outbound/light/close")
    Observable<Success> closeLight(@Field("value") String s);//关灯操作

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/management/outbound/alarm/submit")
    Observable<Success> getAlarmOutSubmit(@Field("value") String scheduleId);//提交

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/management/outbound/schedule/submit")
    Observable<Success> getOutSubmit(@Field("value") String scheduleId);//提交

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/management/outbound/alarm/submit")
    Observable<Success> getAlarmSuccessState(@Field("value") String sapWorkOrderId);//预警出库完成

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/management/outbound/schedule/submit")
    Observable<Success> getScheduleSuccessState(@Field("value") String scheduleId);//预警出库完成

    @GET("ams/pcb/management/outbound/alternative/bill")
    Observable<OutBound> getRefresh(@Query("condition") String s);

    @POST("ams/pcb/inventory/start")
    Observable<Success> onStartWork();//开始盘点

    @GET("ams/pcb/inventory/ongoing")
    Observable<OnGoing> onGoing();//盘点界面判断

    @PUT("ams/pcb/inventory/end")
    Observable<Success> onEnd();//结束盘点

    @GET("ams/pcb/inventory/detail")
    Observable<CheckStock> getCheckStock(@Query("condition") String s);//获取盘点列表

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/inventory/subinventory")
    Observable<Success> getCheckNumber(@Field("value") String value);//发送盘点数量

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/pcb/inventory/alteration")
    Observable<Success> getError(@Field("value") String values);//发送盘点异常

    @GET("ams/pcb/inventory/sub/exception")
    Observable<ExceptionsBean> getException(@Query("condition") String boxSerial);//盘点结果

    @GET("ams/pcb/inventory/exception")
    Observable<InventoryExecption> getInventoryException();//获取总结

    @GET("ams/pcb/inventory/alteration/judge")
    Observable<Success> getJudgeSuccsee(@Query("condition") String boxSerial);


    // TODO: 2017-05-26 查找是否存在
    @GET("ams/pcb/inventory/submit")
    Observable<Success> getSubmit(@Query("labelCode") String boxSerial);//发送盘点结果


    Observable<String> getCheckStockSuccess();//是否成功?

    @GET("ams/pcb/subshelf")
    Observable<LedLight> getSubshelf(@Query("labelCode") String s);

    @GET("ams/pcb/subshelf/update")
    Observable<Success> getUpdate(@Query("id") String id, @Query("lightSerial") String lightSerial);

    @GET("ams/pcb/subshelf/unbound")
    Observable<Success> getUnbound(@Query("param") String id);


    //新接口

    //判断标签是否重复
    @GET("ams/pcb/management/serial")
    Observable<AddSuccess> isBoxSerialExist(@Query("condition") String boxSerial);
    //判断标签是否在库存
    @GET("ams/pcb/management/label")
    Observable<AddSuccess> isLabelExist(@Query("condition") String labelCode);



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
    @GET("ams/smm/linefailure/getseriousfaultinfos")
    Observable<FaultMessage> getFalutMessages(@Query("condition") String s);

    @GET("ams/smm/linefailure/faultsolutionlist")
    Observable<SolutionMessage> getSolutionMessage(@Query("condition") String s);

    @GET("ams/smm/linefailure/faultsolutiondetaillist")
    Observable<FaultSolutionMessage> getDetailSolutionMessage(@Query("condition") String s);

    @GET("ams/smm/linefailure/resolvefault")
    Observable<BaseEntity> resolveFault(@Query("condition") String content);


    @GET("ams/smm/linefailure/addfaultsolution")
    Observable<BaseEntity> addSolution(@Query("condition") String content);

    //获取模板内容

    @GET("ams/smm/linefailure/gethtmlcontent")
    Observable<BaseEntity<String>> getTemplateContent(@Query("condition") String fileName);

    //上传文件
    @Multipart
    @POST("webapi/ams/smm/linefailure/uploadfiles")
    Observable<ResultFault> upLoadFile(@Part("description") RequestBody description,
                                       @Part MultipartBody.Part file,
                                       @Query("param") String argument);

    @GET("ams/smm/linefailure/getfilters")
    Observable<FaultFilter> getFaultFilterMessage();

    //查询指定工单备料车ID
    @GET("ams/smm/warehissue/qprepcaridbyworkorder")
    Observable<Result<MaterialCar>> queryMaterialCar(@Query("condition") String content);

    //绑定指定工单备料车ID
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/bindprepcaridbyworkorder")
    Observable<BindPrepCarIDByWorkOrderResult> bindMaterialCar(@Field("value") String content);

    //发料
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/issuretowareh")
    Observable<Result<StorageDetails>> issureToWareh(@Field("value") String content);

    //完成仓库发料
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/issuretowarehfinish")
    Observable<IssureToWarehFinishResult> issureToWarehFinish(@Field("value") String content);

    //开始某个小仓库指定工单发料
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/startwarehissure")
    Observable<Result<StorageDetails>> getStorageDetails(@Field("value") String argument);

    //调料盘
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/smm/warehissue/jumpmaterials")
    Observable<Result<StorageDetails>> jumpMaterials(@Field("value") String mS);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/surecompleteissue")
    Observable<IssureToWarehFinishResult> sureCompleteIssue(@Field("value") String mS);

    //仓库房扣账
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/deduction")
    Observable<Result<DebitData>> deduction(@Field("value") String mS);

    //尾数仓备料
    @GET("ams/smm/issuemana/querymantiss")
    Observable<MantissaWarehouseReady> getMantissaWarehouseReadyDates();

    //尾数仓备料详情
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/startmantississue")
    Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(@Field("value") String bind);



    //料盘绑定标签
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehouse/backing/label")
    Observable<MantissaWarehousePutstorageBindTagResult> getBingingLable(@Field("value") String bind);

    //查询尾数仓备料车
    @GET("ams/smm/warehissue/qprepcaridbyworkorder")
    Observable<Result<MaterialCar>> getFindCar(@Query("condition") String bind);

    //绑定尾数仓备料车
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/bindprepcaridbyworkorder")
    Observable<Result<MaterialCar>> getbingingCar(@Field("value") String bind);

    //尾数仓发料
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/mantississue")
    Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseput(@Field("value") String bind);

    //尾数仓扣账
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/deduction")
    Observable<Result> debit();

    //尾数仓发料完成
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/completemantississue")
    Observable<IssureToWarehFinishResult> getMantissaWareOver(@Field("value") String s);

    //仓库房备料和尾数仓选择
    @GET("ams/smm/issuemana/querywarehousepart")
    Observable<Result<StoreEntity>> getStorageSelect();

    //根据小仓库分区查询需要备料的工单列表
    @GET("ams/smm/issuemana/queryworkorder")
    Observable<Result<StorageReady>> getStorageReadyDates(@Query("condition") String argument);

    //TODO shaoqiang,8Interfance
    @GET("ams/jig/life/schedule/query?condition=[{\"column\":\"orderStatus\",\"value\": \"3\",\"opt\":\"=\",\"relation\":\"OR\"},{\"column\":\"orderStatus\",\"value\": \"5\",\"opt\":\"=\",\"relation\":\"AND\"}]")
    Observable<JsonProductBorrowRoot> getProductWorkItem();

    //    @GET("http://172.22.34.100:8081/sms/jig/life/use/loan/jig")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/jig/life/use/loan/jig")
    Observable<JsonProductRequestToolsRoot> getProductToolsInfoItem(@Field("value") String parm);

    //    @GET("http://172.22.34.100:8081/sms/jig/life/use/loan/jig")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/jig/life/use/loan/jig")
    Observable<JsonProduct_mToolsRoot> getProduct_mToolsInfo(@Field("value") String parm);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/jig/life/use/shelf/verify")
    Observable<JsonProductToolsLocationRoot> getLocationVerify(@Field("value") String param);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/instore/submit")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/jig/life/use/shelf/submit")
    Observable<JsonLocationVerfyRoot> getLocationSubmit(@Field("value") String param);

    @POST("ams/jig/life/use/back/submit")
    @FormUrlEncoded
    Observable<JsonProductBackRoot> getProductToolsBack(@Field("value") String param);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/loan/verify")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("/ams/jig/life/use/loan/verify")
    Observable<JsonProductToolsVerfyRoot> getProductToolsVerfy(@Field("value") String parm);

    //    @GET("http://172.22.34.100:8081/webapi/sms/jig/life/use/loan/submit")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/jig/life/use/loan/submit")
    Observable<JsonProductToolsSubmitRoot> getProductToolsBorrowSubmit(@Field("value") String parm);


    //liuzhenyu
    //尾数仓退入主仓库
    @GET("ams/smm/warehouse/backing/materials")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

    //点击清理按钮
    @PUT("ams/smm/warehouse/backing/materials")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate();

    //位数仓点击后退关灯
    @GET("ams/smm/mantissastorage/back")
    Observable<ManualDebitBean> offLights();

    //点击开始绑定
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/mantowareh/startbound")
    Observable<MantissaWarehousePutstorageResult> getOnclickBeginButton();

    //尾数仓点击开始入库
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehouse/backing")
    Observable<MantissaWarehousePutstorageBindTagResult> getbeginPut(@Field("value") String parm);

    //尾数仓绑定入库车
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehouse/backing/car")
    Observable<MantissaWarehousePutstorageBindTagResult> bindMantissaWarehouseCar(@Field("value") String parm);



    //尾数仓扫描料盘
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehouse/backing/materials")
    Observable<MantissaWarehousePutstorageBindTagResult> getUpLocation(@Field("value") String bind);

    //点击提交按钮结束本次绑定
    @PUT("ams/smm/warehouse/backing")
    Observable<MantissaWarehousePutstorageBindTagResult> onlickSubmit();

    //确定点击下一个架位
    @PUT("ams/smm/mantowareh/surenextshelf")
    Observable<MantissaWarehousePutstorageResult> getYesNext();

    //确定点击完成
    @PUT("ams/smm/mantowareh/surecomplete")
    Observable<MantissaWarehousePutstorageResult> getYesok();

    //尾数仓入库
    @GET("ams/smm/mantissastorage/qmantissastoragelist")
    Observable<MantissaWarehouseReturnResult> getMantissaWarehouseReturn();

    //尾数仓查询料盘的位置
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/mantissastorage/findshelf")
    Observable<MantissaWarehouseReturnResult> getMaterialLocation(@Field("value") String bind);

    //尾数仓查料盘入库
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/mantissastorage/mantissastorage")
    Observable<MantissaWarehouseReturnResult> getputinstrage(@Field("value") String bind);

    //品管确认列表查询
    @GET("ams/smm/linequality/getqualitylist")
    Observable<QualityManage> getQualityList(@Query("condition") String bind);

    //品管确认列表查询
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @PUT("ams/smm/linequality/confirmqualityok")
    Observable<QualityManage> getQualityOK(@Field("value") String bind);

    //尾数仓获取手动扣账列表
    @GET("ams/smm/mantissastorage/getnodebit")
    Observable<ManualDebitBean> getManualmaticDebit();

    //尾数仓手动扣账
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/mantissastorage/deduction")
    Observable<ManualDebitBean> getdeduction(@Field("value") String bind);

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

    @GET("ams/smm/warehissue/getnodebit")
    Observable<Result<DebitData>> getDebitDataList(@Query("condition") String mMs);

    @GET("ams/smm/plugmodcontroller/getneeduploadtomesmaterials")
    Observable<BaseEntity<UpLoadEntity>> getneeduploadtomesmaterials(@Query("condition") String mArgument);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/plugmodcontroller/updateprepworkorderstatus")
    Observable<Result> jumpOver(@Field("value") String bind);

    /**
     * 仓库入库相关的Api
     */
    @GET("/ams/smm/warehouse/storage")
    Observable<WheatherBindStart> wheatherBindStart();

    @POST("/ams/smm/warehouse/storage")
    Observable<StartStoreBean> startStore();

    @POST("/ams/smm/warehouse/storage/car")
    Observable<BindCarBean> bindCar(@Query("value") String carName);

    @POST("/ams/smm/warehouse/storage/materials")
    Observable<ScanMaterialPanBean> scanMatePan(@Query("value") String materialPan);

    @POST("ams/smm/warehouse/storage/label")
    Observable<BindLabelBean> bindLabel(@Query("value") String moveLabel);

    @PUT("/ams/smm/warehouse/storage")
    Observable<FinishPda> finishedPda();

    //尾数仓更改架位
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("/ams/smm/warehissue/changecarshelf")
    Observable<Result> changecarshelf(@Field("value") String mGsonListString);

    @GET("/ams/smm/warehissue/offcarshelflight")
    Observable<Result> offcarshelflight(@Query("condition") String mS);




    //@GET("SMM/unplugmod/getModNumByMaterial")
    //Observable<ModNumByMaterialResult> getModNumByMaterial(@Query("material_num") String material_num, @Query("workOrderNum") String num);

    /**
     * 产中扫描
     */
    //请求预警中item数据
    @GET("/ams/smm/linealarmfault/getworkorderalarmlist")
    Observable<com.delta.smt.entity.production_scan.ProduceWarning> getWorkOrderDatas(@Query("condition") String condition);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("/ams/smm/linealarmfault/uploadtomesfromprocessline")
    Observable<Result> uploadToMesFromProcessline(@Field("value") String value);
}
