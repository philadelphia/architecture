package com.delta.smt.api;


import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.FalutMesage;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MantissaWarehouseReturn;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.OverReceiveWarning;
import com.delta.smt.entity.ProductToolsBack;
import com.delta.smt.entity.ProductToolsInfo;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.entity.Product_mToolsInfo;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.StorageSelect;
import com.delta.smt.entity.Success;
import com.delta.smt.entity.Update;
import com.delta.smt.entity.User;
import com.delta.smt.entity.VirtualLineBindingItem;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.item.ItemInfo;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;
import com.delta.smt.ui.production_warning.item.TitleNumber;

import java.util.List;
import java.util.concurrent.locks.Condition;

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
    @GET("http://172.22.34.21:8081/SMM/FeederBuffStorage/qFeederBuffStorageList")
    Observable<Result<FeederCheckInItem>> getAllCheckedInFeeders();

    @POST
    Observable<List<FeederSupplyWarningItem>> getAllSupplyWorkItems();

    @POST
    Observable<List<FeederSupplyItem>> getAllToBeSuppliedFeeders();

    @POST
    Observable<Result> upLoadFeederSupplyResult();

    @POST
    Observable<List<FeederSupplyItem>> getAllToBeCheckedInFeeders();

    /*预警模块的模拟接口*/
    Observable<List<ItemProduceLine>> getLineDatas();

    Observable<TitleNumber> getTitleDatas();

    Observable<List<ItemWarningInfo>> getItemWarningDatas();

    Observable<List<ItemBreakDown>> getItemBreakDownDatas();

    Observable<List<ItemInfo>> getItemInfoDatas();


    Observable<List<ItemHandAdd>> getItemHandAddDatas();

    Observable<String> sumbitLine();

    //接口PCB库房发料

    Observable<List<com.delta.smt.entity.ItemInfo>> getWarning();//获取所有预警信息

    Observable<List<ListWarning>> getListWarning();//获取发料列表

    Observable<List<CheckStock>> getCheckStock();//获取盘点列表

    Observable<String> getSuccessState();//是否成功?

    Observable<String> getStoreRoomSuccess();//是否成功?

    Observable<String> getCheckStockSuccess();//是否成功?

    Observable<List<ListWarning>> getWarningNumberSuccess();//获取Warning列表的数量

    Observable<List<CheckStock>> getCheckNumber();//获取盘点列表的数量

    @GET("webapi/pcb/management/inbound")
    Observable<Light> onLight(@Query("param") String s);//点灯操作
    @GET("webapi/pcb/management/inbound")
    Observable<Success> putInStorage(@Query("param") String s);//入库操作
    @GET("pcb/management/outbound/bill")
    Observable<OutBound> outBound(@Query("param") String s);//仓库发料清单
    @GET("pcb/management/capacity")
    Observable<PcbNumber> getPcbNumber(@Query("param") String s);//获取实际数量
    @GET("webapi/pcb/management/outbound")
    Observable<Success> getPcbSuccess(@Query("param") String s);//获取是否发料成功



    //Observable<List<MantissaWarehousePutstorage>> getBeginput();

    //故障处理预警
    Observable<List<FalutMesage>> getFalutMessages();

    //更新
    @GET(API.bundleJsonUrl)
    Observable<Update> getUpdate();


    //下载更新
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    Observable<List<ModuleUpWarningItem>> getModuleUpWarningItems();

    Observable<List<ModuleDownWarningItem>> getModuleDownWarningItems();

    Observable<List<ModuleUpBindingItem>> getModuleUpBindingItems();

    Observable<List<VirtualLineBindingItem>> getVirtualLineBindingItems();

    Observable<List<ModuleDownDetailsItem>> getModuleDownDetailsItems();

    Observable<List<ProductWorkItem>> getProductWorkItem();

    Observable<List<ProductToolsInfo>> getProductToolsInfoItem();

    Observable<List<Product_mToolsInfo>> getProduct_mToolsInfo();

    Observable<List<ProductToolsBack>> getProductToolsBack();

    //仓库房备料和尾数仓
    //Zhangfuxiang
    @GET("http://172.22.34.6:8081/SMM/IssueMana/queryWarehousePart")
    Observable<Result<String>> getStorageSelect();

  //  Observable<List<MantissaWarehouseDetailsResult>> getMantissaWarehouseDetails();

    Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn();

    //Observable<List<MantissaWarehousePutstorage>> getMantissaWarehousePutstorage();

    //  Observable<List<MantissaWarehousePutstorage>> getBeginput();

    //Zhangfuxiang
    @GET("http://172.22.34.6:8081/SMM/IssueMana/queryWorkOrder")
    Observable<Result<StorageReady>> getStorageReadyDates(@Query("condition") String argument);

  //  Observable<List<MantissaWarehouseReady>> getMantissaWarehouseReadyDates();

    //Zhangfuxiang
    @GET("http://172.22.34.6:8081/SMM/IssueMana/queryIssue")
    Observable<Result<StorageDetails>> getStorageDetails(@Query("condition") String argument);

//    Observable<String> sumbitLine();




    //liuzhenyu
    @GET("http://172.22.34.8:8081/SMM/ManToWareh/queryReturnedWarehList")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

    @GET("http://172.22.34.8:8081/SMM/ManToWareh/triggerListUpdate")
    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate();

    @GET("http://172.22.34.8:8081/SMM/ManToWareh/startStorage")
    Observable<MantissaWarehousePutstorageResult> getbeginPut();

    //尾数仓备料
    @GET("http://172.22.34.6:8081/SMM/IssueMana/querymantiss")
    Observable<MantissaWarehouseReady> getMantissaWarehouseReadyDates();
    //尾数仓备料详情
    @GET("http://172.22.34.6:8081/SMM/IssueMana/queryMantissIssue")
    Observable<MantissaWarehouseDetailsResult> getMantissaWarehouseDetails(@Query( "condition") String bind);


    @GET("http://172.22.34.6:8081/SMM/ExcessManagement/qExcessList")
    Observable<OverReceiveWarning> getOverReceiveItems();

    @GET("http://172.22.34.6:8081/SMM/ExcessManagement/execessIssure")
    Observable<OverReceiveWarning> getOverReceiveItemSend(@Query("condition") String content);


}
