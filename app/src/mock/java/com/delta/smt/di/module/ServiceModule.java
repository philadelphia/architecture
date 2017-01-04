package com.delta.smt.di.module;

import com.delta.smt.api.ApiService;
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
import com.delta.smt.entity.Update;
import com.delta.smt.entity.User;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;
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
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Url;
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
            public Observable<List<StorageDetails>> getStorageDetails() {
                List<StorageDetails> datas = new ArrayList<>();
                datas.add(new StorageDetails("0351234701","D33E02-02","20000","10000","发料中"));
                datas.add(new StorageDetails("0351234702","D33E02-03","30000","60000","未开始"));
                datas.add(new StorageDetails("0351234703","D33E02-04","40000","70000","未开始"));
                datas.add(new StorageDetails("0351234704","D33E02-05","50000","70000","完成"));
                datas.add(new StorageDetails("0351234705","D33E02-06","60000","70000","完成"));
                datas.add(new StorageDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new StorageDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new StorageDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new StorageDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new StorageDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new StorageDetails("0351234706","D33E02-07","70000","70000","完成"));

                return Observable.just(datas);
            }

            @Override
            public Observable<List<com.delta.smt.entity.ItemInfo>> getWarning() {
                List<com.delta.smt.entity.ItemInfo>list=new ArrayList<>();
                for (int i=0;i<10;i++){
                    com.delta.smt.entity.ItemInfo item=new com.delta.smt.entity.ItemInfo();
                    //TODO  控件有问题
                    item.setText("产线:H"+i+"\n"+"工单号:24561215"+i+"\n"+"PCB料号：457485645"+i+"\n"+"机种：H123-"+i+"\n"+"需求量："+50+"\n"+"状态:"+"备料");
                    item.setCountdown(9000);
                    long current = System.currentTimeMillis();
                    item.setEndTime(current+9000);
                    item.setWorkNumber("245612152");
                    item.setMachine("H1231");
                    item.setMaterialNumber("4574856451");
                    list.add(item);
                }
                return Observable.just(list);
            }



            @Override
            public Observable<List<ListWarning>> getListWarning() {
                List<ListWarning>  mList=new ArrayList<>();
                for (int i=0;i<10;i++){
                    ListWarning l=new ListWarning();
                    l.setPcb("0343352030"+i);
                    l.setJia("J21-3"+i);
                    l.setDangqaian("");
                    l.setXuqiu("100");
                    l.setPcbCode("0"+i);
                    l.setDc("1637");
                    mList.add(l);
                }
                return Observable.just(mList);
            }

            @Override
            public Observable<List<MantissaWarehouseDetails>> getMantissaWarehouseDetails() {
                List<MantissaWarehouseDetails> datas = new ArrayList<>();
                datas.add(new MantissaWarehouseDetails("0351234701","D33E02-02","20000","10000","发料中"));
                datas.add(new MantissaWarehouseDetails("0351234702","D33E02-03","30000","60000","未开始"));
                datas.add(new MantissaWarehouseDetails("0351234703","D33E02-04","40000","70000","未开始"));
                datas.add(new MantissaWarehouseDetails("0351234704","D33E02-05","50000","70000","完成"));
                datas.add(new MantissaWarehouseDetails("0351234705","D33E02-06","60000","70000","完成"));
                datas.add(new MantissaWarehouseDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new MantissaWarehouseDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new MantissaWarehouseDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new MantissaWarehouseDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new MantissaWarehouseDetails("0351234706","D33E02-07","70000","70000","完成"));
                datas.add(new MantissaWarehouseDetails("0351234706","D33E02-07","70000","70000","完成"));

                return Observable.just(datas);
            }

            @Override
            public Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn() {
                List<MantissaWarehouseReturn> datas = new ArrayList<>();
                datas.add(new MantissaWarehouseReturn("201512121234","0351234701","1234567890","-","等待入库"));
                datas.add(new MantissaWarehouseReturn("201512121234","0351234701","1234567890","-","等待入库"));
                datas.add(new MantissaWarehouseReturn("201512121234","0351234701","1234567890","-","等待入库"));
                datas.add(new MantissaWarehouseReturn("201512121235","0351234702","1234567890","D33E02-08","完成"));
                datas.add(new MantissaWarehouseReturn("201512121236","0351234703","1234567890","D33E02-09","完成"));
                return Observable.just(datas);
            }

            @Override
            public Observable<List<MantissaWarehousePutstorage>> getMantissaWarehousePutstorage() {
                List<MantissaWarehousePutstorage> datas = new ArrayList<>();
                datas.add(new MantissaWarehousePutstorage("201512121234","0351234701","D33E02-02","-","等待退入"));
                datas.add(new MantissaWarehousePutstorage("201512121234","0351234701","D33E02-02","-","等待退入"));
                datas.add(new MantissaWarehousePutstorage("201512121234","0351234701","D33E02-02","-","等待退入"));
                datas.add(new MantissaWarehousePutstorage("201512121235","0351234702","D33E02-02","","等待退入"));
                datas.add(new MantissaWarehousePutstorage("201512121236","0351234703","D33E02-02","","等待退入"));
                return Observable.just(datas);
            }

            @Override
            public Observable<List<MantissaWarehousePutstorage>> getBeginput() {
                List<MantissaWarehousePutstorage> datas = new ArrayList<>();
                datas.add(new MantissaWarehousePutstorage("201512121234","0351234701","D33E02-02","-","等待退入"));
                datas.add(new MantissaWarehousePutstorage("201512121234","0351234701","D33E02-02","-","等待退入"));
                datas.add(new MantissaWarehousePutstorage("201512121234","0351234701","D33E02-02","T-001","开始退库"));
                return Observable.just(datas);
            }

            @Override
            public Observable<Update> getUpdate() {
                //测试数据
                Update update = new Update();
                update.setVersion("1.0");
                update.setVersionCode("1");
                update.setDescription("无更新");
                update.setUrl("http://172.22.35.177:8081/app-debug.apk");
                return Observable.just(update);
            }

            @Override
            public Observable<ResponseBody> download(@Url String url) {
                return null;
            }

            /*@Override
            public Observable<Update> getUpdate() {
                return null;
            }

            @Override
            public Observable<ResponseBody> download(@Url String url) {
                return null;
            }*/

            @Override
            public Observable<List<FeederCheckInItem>> getAllCheckedInFeeders() {
                List<FeederCheckInItem> dataList = new ArrayList<>();
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));
                dataList.add(new FeederCheckInItem("201689763", "KT8FL 139060", "0351234707", "001-02023", "等待入库"));

                return  Observable.just(dataList);

            }

            @Override
            public Observable<List<FeederSupplyWarningItem>> getAllSupplyWorkItems() {
                List<FeederSupplyWarningItem> dataList = new ArrayList<>();
                dataList.add(new FeederSupplyWarningItem(1,"342","A","dsajg"));
                dataList.add(new FeederSupplyWarningItem(2,"342","B","dsajg"));
                dataList.add(new FeederSupplyWarningItem(3,"342","C","dsajg"));
                dataList.add(new FeederSupplyWarningItem(4,"342","D","dsajg"));
                dataList.add(new FeederSupplyWarningItem(5,"342","E","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));
                dataList.add(new FeederSupplyWarningItem(6,"342","F","dsajg"));

                return  Observable.just(dataList);
            }

            @Override
            public Observable<List<FeederSupplyItem>> getAllToBeSuppliedFeeders() {
                List<FeederSupplyItem> list = new ArrayList<>();
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                return Observable.just(list);
            }

            @Override
            public Observable<Result> upLoadFeederSupplyResult() {
                return Observable.just(new Result("success","OK"));
            }

            @Override
            public Observable<List<FeederSupplyItem>> getAllToBeCheckedInFeeders() {
                List<FeederSupplyItem> list = new ArrayList<>();
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));
                list.add(new FeederSupplyItem("001-02023","KT8FL 139060","0351234707","05T021","","等待上模组"));

                return Observable.just(list);
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

            @Override
            public Observable<List<ItemHandAdd>> getItemHandAddDatas() {
                List<ItemHandAdd> datas=new ArrayList<>();
                datas.add(new ItemHandAdd("料站Pass预警","产线：H13","模组料站：06T021","预计Pass数量：2","预警信息：IC201位置需要手补件"));
                datas.add(new ItemHandAdd("料站Pass预警","产线：H14","模组料站：06T022","预计Pass数量：4","预警信息：IC201位置需要手补件"));

                return Observable.just(datas);
            }

            //LIN
            public Observable<List<CheckStock>>getCheckStock(){
                List<CheckStock> data=new ArrayList<>();
                for (int i=0;i<20;i++){
                    CheckStock checkStock=new CheckStock();
                    checkStock.setPcb("034335230"+i);
                    checkStock.setLiu("2016876500"+i);
                    checkStock.setNumber("");
                    checkStock.setCheck("200");
                    if (i==6||i==3||i==3){
                        checkStock.setZhuangtai("未开始");
                    }else if (i==0){
                        checkStock.setZhuangtai("开始盘点");
                    }else {
                    checkStock.setZhuangtai("盘点完成");
                    }
                    data.add(checkStock);


                }
                return Observable.just(data);
            }

            @Override
            public Observable<String> getSuccessState() {
                return Observable.just("成功");
            }

            @Override
            public Observable<String> getStoreRoomSuccess() {
                return Observable.just("成功");
            }

            @Override
            public Observable<String> getCheckStockSuccess() {
                return Observable.just("成功");
            }

            @Override
            public Observable<List<ListWarning>> getWarningNumberSuccess() {
                List<ListWarning>  mList=new ArrayList<>();
                for (int i=0;i<10;i++){
                    ListWarning l=new ListWarning();
                    l.setPcb("0343352030"+i);
                    l.setJia("J21-3"+i);
                    l.setDangqaian("5"+i);
                    l.setXuqiu("100");
                    l.setPcbCode("0"+i);
                    l.setDc("1637");
                    mList.add(l);
                }
                return Observable.just(mList);
            }

            @Override
            public Observable<List<CheckStock>> getCheckNumber() {
                List<CheckStock> data=new ArrayList<>();
                for (int i=0;i<20;i++){
                    CheckStock checkStock=new CheckStock();
                    checkStock.setPcb("034335230"+i);
                    checkStock.setLiu("2016876500"+i);
                    checkStock.setNumber("200");
                    checkStock.setCheck("200");
                    if (i==6||i==3||i==3){
                        checkStock.setZhuangtai("未开始");
                    }else if (i==0){
                        checkStock.setZhuangtai("开始盘点");
                    }else {
                        checkStock.setZhuangtai("盘点完成");
                    }
                    data.add(checkStock);


                }
                return Observable.just(data);
            }

        };
    }

}
