package com.delta.smt.di.module;
import com.delta.smt.api.ApiService;
import com.delta.smt.entity.FeederSupplyWorkItem;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.User;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.item.ItemInfo;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;
import com.delta.smt.ui.production_warning.item.TitleNumber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import rx.Observable;


@Module
public class ServiceModule {

    @Singleton
    @Provides
    ApiService provideCommonService(Retrofit retrofit) {

        return new ApiService() {
            @Override
            public Observable<LoginResult> login(@Body User user) {
                LoginResult result = new LoginResult();
                LoginResult.MessageBean messageBean = new LoginResult.MessageBean();
                messageBean.setUserName("王五");
                messageBean.setToken("---------------");
                result.setMessage(messageBean);
                return Observable.just(result);
            }

            @Override
            public Observable<List<WareHouse>> getAllWareHouse() {
                List<WareHouse> dataList = new ArrayList<>();
                dataList.add(new WareHouse(1, "仓库A"));
                dataList.add(new WareHouse(2, "仓库B"));
                dataList.add(new WareHouse(3, "仓库C"));
                dataList.add(new WareHouse(4, "仓库D"));
                dataList.add(new WareHouse(5, "仓库E"));
                dataList.add(new WareHouse(6, "仓库F"));
                dataList.add(new WareHouse(7, "仓库G"));
                dataList.add(new WareHouse(8, "仓库H"));
                dataList.add(new WareHouse(9, "仓库I"));
                dataList.add(new WareHouse(10, "尾数仓"));
                dataList.add(new WareHouse(11, "Feeder缓冲区"));

                return Observable.just(dataList);
            }

            @Override
            public Observable<List<StorageReady>> getStorageReadyDates() {
                List<StorageReady> datas = new ArrayList<>();
                datas.add(new StorageReady("H11","A","等待仓库A备货","2016121200000012","06:00:00"));
                datas.add(new StorageReady("H12","A","等待仓库A备货","2016121200000012","06:00:00"));
                datas.add(new StorageReady("H13","A","等待仓库A备货","2016121200000012","06:00:00"));
                datas.add(new StorageReady("H14","A","等待仓库A备货","2016121200000012","06:00:00"));

                return Observable.just(datas);
            }

            @Override
            public Observable<List<MantissaWarehouseReady>> getMantissaWarehouseReadyDates() {
                List<MantissaWarehouseReady> datas = new ArrayList<>();
                datas.add(new MantissaWarehouseReady("H11","A","等待仓库A备货","2016121200000012","06:00:00"));
                datas.add(new MantissaWarehouseReady("H12","A","等待仓库A备货","2016121200000012","06:00:00"));
                datas.add(new MantissaWarehouseReady("H13","A","等待仓库A备货","2016121200000012","06:00:00"));
                datas.add(new MantissaWarehouseReady("H14","A","等待仓库A备货","2016121200000012","06:00:00"));

                return Observable.just(datas);
            }

            @Override
            public Observable<List<FeederSupplyWorkItem>> getAllCheckedInFeeders() {
                List<FeederSupplyWorkItem> dataList = new ArrayList<>();
                dataList.add(new FeederSupplyWorkItem(1,"342","A","dsajg"));
                dataList.add(new FeederSupplyWorkItem(2,"342","B","dsajg"));
                dataList.add(new FeederSupplyWorkItem(3,"342","C","dsajg"));
                dataList.add(new FeederSupplyWorkItem(4,"342","D","dsajg"));
                dataList.add(new FeederSupplyWorkItem(5,"342","E","dsajg"));
                dataList.add(new FeederSupplyWorkItem(6,"342","F","dsajg"));

                return  Observable.just(dataList);

            }

            @Override
            public Observable<List<FeederSupplyWorkItem>> getAllSupplyWorkItems() {
                List<FeederSupplyWorkItem> dataList = new ArrayList<>();
                dataList.add(new FeederSupplyWorkItem(1,"342","A","dsajg"));
                dataList.add(new FeederSupplyWorkItem(2,"342","B","dsajg"));
                dataList.add(new FeederSupplyWorkItem(3,"342","C","dsajg"));
                dataList.add(new FeederSupplyWorkItem(4,"342","D","dsajg"));
                dataList.add(new FeederSupplyWorkItem(5,"342","E","dsajg"));
                dataList.add(new FeederSupplyWorkItem(6,"342","F","dsajg"));

                return  Observable.just(dataList);
            }

            /*预警模块的模拟service接口*/
            @Override
            public Observable<List<ItemProduceLine>> getLineDatas() {
                List<ItemProduceLine> datas = new ArrayList<>();
                for (int mI = 1; mI < 16; mI++) {
                    ItemProduceLine line = new ItemProduceLine("SMT_H"+mI,false);
                    datas.add(line);
                }
                return Observable.just(datas);
            }

            @Override
            public Observable<TitleNumber> getTitleDatas() {
                TitleNumber mTitleNumber=new TitleNumber(3,2,1);

                return Observable.just(mTitleNumber);
            }

            @Override
            public Observable<List<ItemWarningInfo>> getItemWarningDatas() {
                List<ItemWarningInfo> datas = new ArrayList<>();

                datas.add(new ItemWarningInfo("叠送一体机-PCB不足预警","产线：H13","制程：叠送一体机","预警信息：锡膏机需要换瓶"));
                datas.add(new ItemWarningInfo("叠送一体机-PCB不足预警","产线：H13","制程：叠送一体机","预警信息：锡膏机需要换瓶"));
                datas.add(new ItemWarningInfo("叠送一体机-PCB不足预警","产线：H13","制程：叠送一体机","预警信息：锡膏机需要换瓶"));
                return Observable.just(datas);
            }

            @Override
            public Observable<List<ItemBreakDown>> getItemBreakDownDatas() {
                 List<ItemBreakDown> datas=new ArrayList<>();

                datas.add(new ItemBreakDown("贴片机-卡料故障","产线：H13","制程：叠送一体机","料站：06T022","故障信息：卡料故障"));
                datas.add(new ItemBreakDown("贴片机-卷带故障","产线：H13","制程：贴片机","料站：06T022","故障信息：卷带故障"));
                return Observable.just(datas);
            }

            @Override
            public Observable<List<ItemInfo>> getItemInfoDatas() {
                List<ItemInfo> datas=new ArrayList<>();

                datas.add(new ItemInfo("锡膏配送中","产线：H13","消息：锡膏即将配送到产线，请确认"));
                datas.add(new ItemInfo("替换钢网配送中","产线：H13","消息：替换钢网配送产线，请确认"));
                return Observable.just(datas);
            }
        };
    }

}
