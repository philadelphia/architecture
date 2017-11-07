package com.example.app.api;


import com.example.app.entity.AddSuccess;
import com.example.app.entity.BaseEntity;
import com.example.app.entity.DebitData;
import com.example.app.entity.FaultFilter;
import com.example.app.entity.FaultMessage;
import com.example.app.entity.FaultSolutionMessage;
import com.example.app.entity.FeederCheckInItem;
import com.example.app.entity.FeederSupplyItem;
import com.example.app.entity.FeederSupplyWarningItem;
import com.example.app.entity.MaterialCar;
import com.example.app.entity.ModuleDownDebit;
import com.example.app.entity.ModuleDownDetailsItem;
import com.example.app.entity.Result;
import com.example.app.entity.ResultFault;
import com.example.app.entity.ResultFeeder;
import com.example.app.entity.ScheduleResult;
import com.example.app.entity.WareHouse;
import com.example.app.entity.bindmaterial.BindCarBean;
import com.example.app.entity.bindmaterial.BindLabelBean;
import com.example.app.entity.bindmaterial.FinishPda;
import com.example.app.entity.bindmaterial.ScanMaterialPanBean;
import com.example.app.entity.bindmaterial.StartStoreBean;
import com.example.app.entity.bindmaterial.WheatherBindStart;
import com.example.updatelibs.entity.Update;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    Observable<Result<FeederSupplyWarningItem>> getSupplyWorkItemList();

    //获取指定工单的Feeder备料列表
    @POST("ams/smm/buffer/startbufferissue")
    Observable<Result<FeederSupplyItem>> getFeederList(@Query("value") String value);

    //下模组灭灯
    @GET("ams/smm/unplugmodcontroller/turnoffalllight")
    Observable<Result> moduleDownlightOff(@Query("condition") String condition);

    //获取Feeder备料时间
    @POST("ams/smm/buffer/bufferissue")
    Observable<Result<FeederSupplyItem>> getFeederInsertionToSlotTimeStamp(@Query("value") String condition);

    //上传feeder备料上模组结果
    @GET("SMM/Buffer/completeBufferIssue")
    Observable<ResultFeeder> upLoadFeederSupplyResult();


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



    //仓库房扣账
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("ams/smm/warehissue/deduction")
    Observable<Result<DebitData>> deduction(@Field("value") String mS);



    // 将物料发送给备料区
    @GET("ams/smm/warehouse/workorder/materials")
    Observable<Result> sendBackArea(@Query("condition") String mS);

    //料盘绑定标签
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded










    //位数仓入库获取入库工单排程
    @GET("ams/smm/mantissastorage/schedul")
    Observable<ScheduleResult> getSchedule();
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
    Observable<com.example.app.entity.production_scan.ProduceWarning> getWorkOrderDatas(@Query("condition") String condition);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("/ams/smm/linealarmfault/uploadtomesfromprocessline")
    Observable<Result> uploadToMesFromProcessline(@Field("value") String value);


}
