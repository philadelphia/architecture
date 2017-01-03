package com.delta.smt.api;


import com.delta.smt.entity.BeginPut;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.MantissaWarehouseDetails;
import com.delta.smt.entity.MantissaWarehousePutstorage;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MantissaWarehouseReturn;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.User;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.item.ItemInfo;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;
import com.delta.smt.ui.production_warning.item.TitleNumber;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by Administrator on 2016/3/23.
 */
public interface  ApiService {
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

    Observable<List<StorageReady>> getStorageReadyDates();

    Observable<List<ItemHandAdd>> getItemHandAddDatas();

    Observable<List<MantissaWarehouseReady>> getMantissaWarehouseReadyDates();

    Observable<List<StorageDetails>> getStorageDetails();


    //接口PCB库房发料

    Observable<List<com.delta.smt.entity.ItemInfo>> getWarning();//获取所有预警信息
    Observable<List<ListWarning>> getListWarning();//获取发料列表
    Observable<List<CheckStock>> getCheckStock();//获取盘点列表
    Observable<String> getSuccessState();//是否成功?
    Observable<String> getStoreRoomSuccess();//是否成功?
    Observable<String> getCheckStockSuccess();//是否成功?



    Observable<List<MantissaWarehouseDetails>> getMantissaWarehouseDetails();

    Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn();

    Observable<List<MantissaWarehousePutstorage>> getMantissaWarehousePutstorage();

    Observable<List<MantissaWarehousePutstorage>> getBeginput();

}
