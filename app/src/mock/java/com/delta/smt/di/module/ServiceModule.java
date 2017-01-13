package com.delta.smt.di.module;

import com.delta.smt.api.ApiService;
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
                datas.add(new StorageReady("H11", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new StorageReady("H12", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new StorageReady("H13", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new StorageReady("H14", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));

                return Observable.just(datas);
            }


            @Override
            public Observable<List<MantissaWarehouseReady>> getMantissaWarehouseReadyDates() {
                List<MantissaWarehouseReady> datas = new ArrayList<>();
                datas.add(new MantissaWarehouseReady("H11", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new MantissaWarehouseReady("H12", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new MantissaWarehouseReady("H13", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new MantissaWarehouseReady("H14", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new MantissaWarehouseReady("H14", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));
                datas.add(new MantissaWarehouseReady("H14", "A", "等待仓库A备货", "2016121200000012", "06:00:00"));

                return Observable.just(datas);
            }

            @Override
            public Observable<List<StorageDetails>> getStorageDetails() {
                List<StorageDetails> datas = new ArrayList<>();
                datas.add(new StorageDetails("0351234701", "D33E02-02", "20000", "10000", "发料中"));
                datas.add(new StorageDetails("0351234702", "D33E02-03", "30000", "60000", "未开始"));
                datas.add(new StorageDetails("0351234703", "D33E02-04", "40000", "70000", "未开始"));
                datas.add(new StorageDetails("0351234704", "D33E02-05", "50000", "70000", "完成"));
                datas.add(new StorageDetails("0351234705", "D33E02-06", "60000", "70000", "完成"));
                datas.add(new StorageDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new StorageDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new StorageDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new StorageDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new StorageDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new StorageDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));

                return Observable.just(datas);
            }

            @Override
            public Observable<List<com.delta.smt.entity.ItemInfo>> getWarning() {
                List<com.delta.smt.entity.ItemInfo> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    com.delta.smt.entity.ItemInfo item = new com.delta.smt.entity.ItemInfo();
                    //TODO  控件有问题
                    item.setText("产线:H" + i + "\n" + "工单号:24561215" + i + "\n" + "PCB料号：457485645" + i + "\n" + "机种：H123-" + i + "\n" + "需求量：" + 50 + "\n" + "状态:" + "备料");
                    item.setCountdown(9000);
                    long current = System.currentTimeMillis();
                    item.setEndTime(current + 9000);
                    item.setWorkNumber("245612152");
                    item.setMachine("H1231");
                    item.setMaterialNumber("4574856451");
                    list.add(item);
                }
                return Observable.just(list);
            }


            @Override
            public Observable<List<ListWarning>> getListWarning() {
                List<ListWarning> mList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    ListWarning l = new ListWarning();
                    l.setPcb("0343352030" + i);
                    l.setJia("J21-3" + i);
                    l.setDangqaian("");
                    l.setXuqiu("100");
                    l.setPcbCode("0" + i);
                    l.setDc("1637");
                    mList.add(l);
                }
                return Observable.just(mList);
            }

            @Override
            public Observable<List<MantissaWarehouseDetails>> getMantissaWarehouseDetails() {
                List<MantissaWarehouseDetails> datas = new ArrayList<>();
                datas.add(new MantissaWarehouseDetails("0351234701", "D33E02-02", "20000", "10000", "发料中"));
                datas.add(new MantissaWarehouseDetails("0351234702", "D33E02-03", "30000", "60000", "未开始"));
                datas.add(new MantissaWarehouseDetails("0351234703", "D33E02-04", "40000", "70000", "未开始"));
                datas.add(new MantissaWarehouseDetails("0351234704", "D33E02-05", "50000", "70000", "完成"));
                datas.add(new MantissaWarehouseDetails("0351234705", "D33E02-06", "60000", "70000", "完成"));
                datas.add(new MantissaWarehouseDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new MantissaWarehouseDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new MantissaWarehouseDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new MantissaWarehouseDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new MantissaWarehouseDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));
                datas.add(new MantissaWarehouseDetails("0351234706", "D33E02-07", "70000", "70000", "完成"));

                return Observable.just(datas);
            }

            @Override
            public Observable<List<MantissaWarehouseReturn>> getMantissaWarehouseReturn() {
                List<MantissaWarehouseReturn> datas = new ArrayList<>();
                datas.add(new MantissaWarehouseReturn("201512121234", "0351234701", "1234567890", "-", "等待入库"));
                datas.add(new MantissaWarehouseReturn("201512121234", "0351234701", "1234567890", "-", "等待入库"));
                datas.add(new MantissaWarehouseReturn("201512121234", "0351234701", "1234567890", "-", "等待入库"));
                datas.add(new MantissaWarehouseReturn("201512121235", "0351234702", "1234567890", "D33E02-08", "完成"));
                datas.add(new MantissaWarehouseReturn("201512121236", "0351234703", "1234567890", "D33E02-09", "完成"));
                return Observable.just(datas);
            }

            @Override
            public Observable<MantissaWarehousePutstorageResult> getMantissaWarehousePutstorage() {
                return null;
            }

            //故障预警
            @Override
            public Observable<List<FalutMesage>> getFalutMessages() {
                List<FalutMesage> datas = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    FalutMesage falutMesage = new FalutMesage("H13", "回焊炉", "炉温过低", "H-00001", 50000l);
                    falutMesage.setEndTime(System.currentTimeMillis()+falutMesage.getCountdown());
                    falutMesage.setId(i);
                    datas.add(falutMesage);
                }

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

            @Override
            public Observable<List<ModuleUpWarningItem>> getModuleUpWarningItems() {
                List<ModuleUpWarningItem> dataList = new ArrayList<ModuleUpWarningItem>();
                /*dataList.add(new ModuleUpWarningItem("A","H13","01:00","仓库物料正在上模组","2016121010000001",3600L));
                falutMesage.setEndTime(System.currentTimeMillis()+falutMesage.getCountdown());
                falutMesage.setId(i);*/
                dataList.add(new ModuleUpWarningItem("A","H14","仓库物料正在上模组","2016121010000002",360000L));
                dataList.add(new ModuleUpWarningItem("A","H15","仓库物料正在上模组","2016121010000003",360000L));
                dataList.add(new ModuleUpWarningItem("A","H16","仓库物料正在上模组","2016121010000004",360000L));
                dataList.add(new ModuleUpWarningItem("A","H17","仓库物料正在上模组","2016121010000005",360000L));
                dataList.add(new ModuleUpWarningItem("A","H18","仓库物料正在上模组","2016121010000006",360000L));
                dataList.add(new ModuleUpWarningItem("A","H19","仓库物料正在上模组","2016121010000007",360000L));
                dataList.add(new ModuleUpWarningItem("A","H20","仓库物料正在上模组","2016121010000008",360000L));
                dataList.add(new ModuleUpWarningItem("A","H21","仓库物料正在上模组","2016121010000009",360000L));
                for(int i=0;i<dataList.size();i++){
                    dataList.get(i).setEndTime(System.currentTimeMillis()+dataList.get(i).getCountdown());
                    dataList.get(i).setId(i);
                }
                return Observable.just(dataList);
            }

            @Override
            public Observable<List<ModuleDownWarningItem>> getModuleDownWarningItems() {
                List<ModuleDownWarningItem> dataList = new ArrayList<ModuleDownWarningItem>();
                dataList.add(new ModuleDownWarningItem("A","H13","等待下模组","2016121010000001",360000L));
                dataList.add(new ModuleDownWarningItem("A","H14","等待下模组","2016121010000002",540000L));
                dataList.add(new ModuleDownWarningItem("A","H15","等待下模组","2016121010000003",680000L));
                dataList.add(new ModuleDownWarningItem("A","H16","等待下模组","2016121010000004",760000L));
                dataList.add(new ModuleDownWarningItem("A","H17","等待下模组","2016121010000005",260000L));
                dataList.add(new ModuleDownWarningItem("A","H18","等待下模组","2016121010000006",390000L));
                dataList.add(new ModuleDownWarningItem("A","H19","等待下模组","2016121010000007",500000L));
                dataList.add(new ModuleDownWarningItem("A","H20","等待下模组","2016121010000008",830000L));
                dataList.add(new ModuleDownWarningItem("A","H21","等待下模组","2016121010000009",550000L));
                for (int i=0;i<dataList.size();i++){
                    dataList.get(i).setEndTime(System.currentTimeMillis()+dataList.get(i).getCountdown());
                    dataList.get(i).setId(i);
                }
                return Observable.just(dataList);
            }

            @Override
            public Observable<List<ModuleUpBindingItem>> getModuleUpBindingItems() {
                List<ModuleUpBindingItem> dataList = new ArrayList<ModuleUpBindingItem>();
                dataList.add(new ModuleUpBindingItem("-","0353104700","03T021","-","2016082500"));
                dataList.add(new ModuleUpBindingItem("-","1512445A00","03T022","-","2016082500"));
                dataList.add(new ModuleUpBindingItem("-","15D2067A00","03T023","-","2016082500"));
                dataList.add(new ModuleUpBindingItem("-","1511508A00","03T024","-","2016082500"));
                return Observable.just(dataList);
            }

            @Override
            public Observable<List<VirtualLineBindingItem>> getVirtualLineBindingItems() {
                List<VirtualLineBindingItem> dataList = new ArrayList<VirtualLineBindingItem>();
                dataList.add(new VirtualLineBindingItem("1","-"));
                dataList.add(new VirtualLineBindingItem("2","-"));
                dataList.add(new VirtualLineBindingItem("3","-"));
                return Observable.just(dataList);
            }

            @Override
            public Observable<List<ModuleDownDetailsItem>> getModuleDownDetailsItems() {
                List<ModuleDownDetailsItem> dataList = new ArrayList<ModuleDownDetailsItem>();
                dataList.add(new ModuleDownDetailsItem("KT8FL 139053","0351234700","-","03T021","Feeder缓存区","2016082500"));
                dataList.add(new ModuleDownDetailsItem("KT8FL 139054","0351234701","-","03T022","Feeder缓存区","2016082500"));
                dataList.add(new ModuleDownDetailsItem("KT8FL 139055","0351234702","-","03T023","Feeder缓存区","2016082500"));
                dataList.add(new ModuleDownDetailsItem("KT8FL 139056","0351234703","-","03T024","Feeder维护区","2016082500"));
                dataList.add(new ModuleDownDetailsItem("KT8FL 139057","0351234704","-","03T025","Feeder维护区","2016082500"));
                dataList.add(new ModuleDownDetailsItem("KT8FL 139058","0351234705","-","03T026","尾数仓","2016082500"));
                dataList.add(new ModuleDownDetailsItem("KT8FL 139059","0351234706","-","03T027","尾数仓","2016082500"));
                dataList.add(new ModuleDownDetailsItem("KT8FL 1390510","0351234707","-","03T028","尾数仓","2016082500"));
                return Observable.just(dataList);
            }

            @Override
            public Observable<List<ProductWorkItem>> getProductWorkItem() {
                //TODO ZSQgetProductWorkItem
                List<ProductWorkItem> list=new ArrayList<ProductWorkItem>();
                list.add(new ProductWorkItem("23141232","内部","23141232","01","42692256","H11","42692256","A","2016-12-12 18:00:00","准备就绪"));
                list.add(new ProductWorkItem("23141232","内部","23141232","01","42692256","H11","42692256","A","2016-12-12 19:00:00","等待"));
                list.add(new ProductWorkItem("23141232","内部","23141232","01","42692256","H11","42692256","A","2016-12-12 21:00:00","等待"));
                list.add(new ProductWorkItem("23141232","内部","23141232","01","42692256","H11","42692256","A","2016-12-12 15:00:00","等待"));
                return Observable.just(list);
            }

            @Override
            public Observable<List<ProductToolsInfo>> getProductToolsInfoItem() {
                //TODO ZSQgetProductToolsInfoItem

                List<ProductToolsInfo> list=new ArrayList<>();
                list.add(new ProductToolsInfo("1","11458754","钢网","A11-002","更多","待确认"));
                list.add(new ProductToolsInfo("2","11458756","钢网","A11-003","更多","待确认"));
                list.add(new ProductToolsInfo("3","11458756","钢网","A11-006","更多","待确认"));
                list.add(new ProductToolsInfo("4","11458756","钢网","A11-005","更多","待确认"));
                return Observable.just(list);
            }

            @Override
            public Observable<List<Product_mToolsInfo>> getProduct_mToolsInfo() {

                //TODO ZSQgetProduct_mToolsInfo
                List<Product_mToolsInfo> list=new ArrayList<>();
                list.add(new Product_mToolsInfo("1","32325432","钢网","D9-9001"));
                list.add(new Product_mToolsInfo("2","32325432","钢网","D2-9001"));
                list.add(new Product_mToolsInfo("3","32325432","钢网","D6-9001"));
                list.add(new Product_mToolsInfo("4","32325432","钢网","D8-9001"));

                return Observable.just(list);
            }

            @Override
            public Observable<List<ProductToolsBack>> getProductToolsBack() {

                //TODO ZSQgetProductToolsBack
                List<ProductToolsBack> list=new ArrayList<>();
                list.add(new ProductToolsBack("1","20003034","23141224","刮刀","已归还"));
                list.add(new ProductToolsBack("2","20003034","23141224","钢网","未归还"));
                list.add(new ProductToolsBack("3","20003034","23141224","钢网","未归还"));
                list.add(new ProductToolsBack("4","20003034","23141224","钢网","未归还"));

                return Observable.just(list);
            }

            @Override
            public Observable<List<StorageSelect>> getStorageSelect() {
                    List<StorageSelect> dataList = new ArrayList<>();
                    dataList.add(new StorageSelect(1, "仓库A"));
                    dataList.add(new StorageSelect(2, "仓库B"));
                    dataList.add(new StorageSelect(3, "仓库C"));
                    dataList.add(new StorageSelect(4, "仓库D"));
                    dataList.add(new StorageSelect(5, "仓库E"));
                    dataList.add(new StorageSelect(6, "仓库F"));
                    dataList.add(new StorageSelect(7, "仓库G"));
                    dataList.add(new StorageSelect(8, "仓库H"));
                    dataList.add(new StorageSelect(9, "仓库I"));
                    dataList.add(new StorageSelect(10, "尾数仓"));
                    dataList.add(new StorageSelect(11, "Feeder缓冲区"));
                    return Observable.just(dataList);
            }

            @Override
            public Observable<List<FeederCheckInItem>> getAllCheckedInFeeders() {
                List<FeederCheckInItem> dataList = new ArrayList<>();
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));
                dataList.add(new FeederCheckInItem(" ", "KT8FL 139060", "0351234707", "001-02023", 0,"", "201689763"));

                return Observable.just(dataList);

            }

            @Override
            public Observable<List<FeederSupplyWarningItem>> getAllSupplyWorkItems() {
                List<FeederSupplyWarningItem> dataList = new ArrayList<>();
                for (int i = 0; i < 100; i++){
                    FeederSupplyWarningItem feederCheckInItem = new FeederSupplyWarningItem(1, "342", "A", "等待上模组",230000);
                    feederCheckInItem.setEndTime(System.currentTimeMillis() + feederCheckInItem.getCountdown());
                    feederCheckInItem.setId(i);
                    dataList.add(feederCheckInItem);
                }

                return Observable.just(dataList);
            }

            @Override
            public Observable<List<FeederSupplyItem>> getAllToBeSuppliedFeeders() {
                List<FeederSupplyItem> list = new ArrayList<>();
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                return Observable.just(list);
            }

            @Override
            public Observable<Result> upLoadFeederSupplyResult() {
                return null;
            }

            @Override
            public Observable<List<FeederSupplyItem>> getAllToBeCheckedInFeeders() {
                List<FeederSupplyItem> list = new ArrayList<>();
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));
                list.add(new FeederSupplyItem("001-02023", "KT8FL 139060", "0351234707", "05T021", "", "等待上模组"));

                return Observable.just(list);
            }

            /*预警模块的模拟service接口*/
            @Override
            public Observable<List<ItemProduceLine>> getLineDatas() {
                List<ItemProduceLine> datas = new ArrayList<>();
                for (int mI = 1; mI < 16; mI++) {
                    ItemProduceLine line = new ItemProduceLine("SMT_H" + mI, false);
                    datas.add(line);
                }
                return Observable.just(datas);
            }

            @Override
            public Observable<TitleNumber> getTitleDatas() {
                TitleNumber mTitleNumber = new TitleNumber(3, 2, 1);

                return Observable.just(mTitleNumber);
            }

            @Override
            public Observable<List<ItemWarningInfo>> getItemWarningDatas() {
                List<ItemWarningInfo> datas = new ArrayList<>();

                datas.add(new ItemWarningInfo("叠送一体机-PCB不足预警", "产线：H13",
                        "制程：叠送一体机", "预警信息：锡膏机需要换瓶",
                        50000l,System.currentTimeMillis()+50000l,1));

                datas.add(new ItemWarningInfo("叠送一体机-PCB不足预警",
                        "产线：H13", "制程：叠送一体机", "预警信息：锡膏机需要换瓶",
                        60000l,System.currentTimeMillis()+60000l,2));

                datas.add(new ItemWarningInfo("接料预警", "产线：H13",
                        "制程：叠送一体机", "预警信息：锡膏机需要换瓶",
                        40000l,System.currentTimeMillis()+40000l,3));


                return Observable.just(datas);
            }

            @Override
            public Observable<List<ItemBreakDown>> getItemBreakDownDatas() {
                List<ItemBreakDown> datas = new ArrayList<>();

                datas.add(new ItemBreakDown("贴片机-卡料故障", "产线：H13", "制程：叠送一体机", "料站：06T022", "故障信息：卡料故障"));
                datas.add(new ItemBreakDown("贴片机-卷带故障", "产线：H13", "制程：贴片机", "料站：06T022", "故障信息：卷带故障"));
                return Observable.just(datas);
            }

            @Override
            public Observable<List<ItemInfo>> getItemInfoDatas() {
                List<ItemInfo> datas = new ArrayList<>();

                datas.add(new ItemInfo("锡膏配送中", "产线：H13", "消息：锡膏即将配送到产线，请确认"));
                datas.add(new ItemInfo("替换钢网配送中", "产线：H13", "消息：替换钢网配送产线，请确认"));
                return Observable.just(datas);
            }

            @Override
            public Observable<List<ItemHandAdd>> getItemHandAddDatas() {
                List<ItemHandAdd> datas = new ArrayList<>();

                for (int mI = 0; mI < 20; mI++) {
                    ItemHandAdd mItemHandAdd=new ItemHandAdd("料站Pass预警", "产线：H13",
                            "模组料站：06T021", "预计Pass数量：2", "预警信息：IC201位置需要手补件",
                            40000l,System.currentTimeMillis()+40000l);
                    mItemHandAdd.setId(mI);
                    datas.add(mItemHandAdd);
                    mItemHandAdd=new ItemHandAdd("料站Pass预警", "产线：H14",
                            "模组料站：06T022", "预计Pass数量：4", "预警信息：IC201位置需要手补件",
                            30000l,System.currentTimeMillis()+30000l);
                    mItemHandAdd.setId(mI+20);
                    datas.add(mItemHandAdd);
                }
/*                for (int i=0;i<3;i++){
                    ItemHandAdd mItemHandAdd=new ItemHandAdd("料站Pass预警", "产线：H13",
                            "模组料站：06T021", "预计Pass数量：2", "预警信息：IC201位置需要手补件",40000l);
                    mItemHandAdd.setEndTime(System.currentTimeMillis()+mItemHandAdd.getCountdown());
                    mItemHandAdd.setId(i);
                    datas.add(mItemHandAdd);
                }*/
                return Observable.just(datas);
            }

            @Override
            public Observable<String> sumbitLine() {
                return null;
            }

            //LIN
            public Observable<List<CheckStock>> getCheckStock() {
                List<CheckStock> data = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    CheckStock checkStock = new CheckStock();
                    checkStock.setPcb("034335230" + i);
                    checkStock.setLiu("2016876500" + i);
                    checkStock.setNumber("");
                    checkStock.setCheck("200");
                    if (i == 6 || i == 3 || i == 3) {
                        checkStock.setZhuangtai("未开始");
                    } else if (i == 0) {
                        checkStock.setZhuangtai("开始盘点");
                    } else {
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
                List<ListWarning> mList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    ListWarning l = new ListWarning();
                    l.setPcb("0343352030" + i);
                    l.setJia("J21-3" + i);
                    l.setDangqaian("5" + i);
                    l.setXuqiu("100");
                    l.setPcbCode("0" + i);
                    l.setDc("1637");
                    mList.add(l);
                }
                return Observable.just(mList);
            }

            @Override
            public Observable<List<CheckStock>> getCheckNumber() {
                List<CheckStock> data = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    CheckStock checkStock = new CheckStock();
                    checkStock.setPcb("034335230" + i);
                    checkStock.setLiu("2016876500" + i);
                    checkStock.setNumber("200");
                    checkStock.setCheck("200");
                    if (i == 6 || i == 3 || i == 3) {
                        checkStock.setZhuangtai("未开始");
                    } else if (i == 0) {
                        checkStock.setZhuangtai("开始盘点");
                    } else {
                        checkStock.setZhuangtai("盘点完成");
                    }
                    data.add(checkStock);


                }
                return Observable.just(data);
            }

        };
    }


}
