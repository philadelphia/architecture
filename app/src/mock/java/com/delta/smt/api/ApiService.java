package com.delta.smt.api;


import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.FalutMesage;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.MantissaWarehouseDetails;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MantissaWarehouseReturn;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.ProductToolsBack;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.entity.ProductToolsInfo;
import com.delta.smt.entity.Product_mToolsInfo;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.StorageSelect;
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

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST
    Observable<List<FeederCheckInItem>> getAllCheckedInFeeders();

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
    @POST
    Observable<List<StorageSelect>> getStorageSelect();

    Observable<List<MantissaWarehouseDetails>> getMantissaWarehouseDetails();

    Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn();

    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

    Observable<List<StorageReady>> getStorageReadyDates();

    Observable<List<MantissaWarehouseReady>> getMantissaWarehouseReadyDates();

    Observable<List<StorageDetails>> getStorageDetails();

   // Observable<List<MantissaWarehousePutstorageResult>> getBeginput();







}
