package com.delta.app.api;


import com.example.app.entity.CheckStock;
import com.example.app.entity.FalutMesage;
import com.example.app.entity.FeederCheckInItem;
import com.example.app.entity.FeederSupplyItem;
import com.example.app.entity.FeederSupplyWarningItem;
import com.example.app.entity.MantissaWarehouseReturn;
import com.example.app.entity.ModuleDownDetailsItem;
import com.example.app.entity.ModuleDownWarningItem;
import com.example.app.entity.ModuleUpBindingItem;
import com.example.app.entity.ModuleUpWarningItem;
import com.example.app.entity.ProductToolsBack;
import com.example.app.entity.ProductToolsInfo;
import com.example.app.entity.ProductWorkItem;
import com.example.app.entity.Product_mToolsInfo;
import com.example.app.entity.Result;
import com.example.app.entity.VirtualLineBindingItem;
import com.example.app.entity.WareHouse;
import com.example.app.ui.hand_add.item.ItemHandAdd;
import com.example.app.entity.production_warining_item.ItemBreakDown;
import com.example.app.entity.production_warining_item.ItemInfo;
import com.example.app.entity.production_warining_item.ItemProduceLine;
import com.example.app.entity.production_warining_item.ItemWarningInfo;
import com.example.app.entity.production_warining_item.TitleNumber;

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

    Observable<List<com.example.app.entity.ItemInfo>> getWarning();//获取所有预警信息

    Observable<List<ListWarning>> getListWarning();//获取发料列表

    Observable<List<CheckStock>> getCheckStock();//获取盘点列表

    Observable<String> getSuccessState();//是否成功?

    Observable<String> getStoreRoomSuccess();//是否成功?

    Observable<String> getCheckStockSuccess();//是否成功?

    Observable<List<ListWarning>> getWarningNumberSuccess();//获取Warning列表的数量

    Observable<List<CheckStock>> getCheckNumber();//获取盘点列表的数量

    //故障处理预警
    Observable<List<FalutMesage>> getFalutMessages();

    Observable<MantissaWarehousePutstorageResult> getbeginput();

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

    Observable<List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails>> getMantissaWarehouseDetails();

    Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn();

    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage();

    Observable<List<StorageReady>> getStorageReadyDates();

    Observable<MantissaWarehouseReady> getMantissaWarehouseReadyDates();

    Observable<List<StorageDetails>> getStorageDetails();

   // Observable<List<MantissaWarehousePutstorageResult>> getBeginput();

    //Observable<List<OverReceiveItem>> getOverReceiveItems();
    Observable<OverReceiveWarning> getOverReceiveItems();

    Observable<OverReceiveWarning> getOverReceiveItemSend(String content);

    Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorageUpdate();

    Observable<MantissaWarehousePutstorageResult> getbeginPut();


}
