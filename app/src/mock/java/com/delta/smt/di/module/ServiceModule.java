package com.delta.smt.di.module;
import com.delta.smt.api.ApiService;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.User;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

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
                dataList.add(new WareHouse(1, "刘能"));
                dataList.add(new WareHouse(2, "张三"));
                dataList.add(new WareHouse(3, "赵四"));

                return Observable.just(dataList);
            }
            @Override
            public Observable<List<ItemProduceLine>> getLineDatas() {
                List<ItemProduceLine> datas = new ArrayList<>();
                for (int mI = 1; mI < 16; mI++) {
                    ItemProduceLine line = new ItemProduceLine("SMT_H"+mI,false);
                    datas.add(line);
                }
                return Observable.just(datas);
            }

        };
    }

}
