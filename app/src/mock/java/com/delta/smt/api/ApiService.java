package com.delta.smt.api;


import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.User;
import com.delta.smt.entity.WareHouse;
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


    Observable<List<ItemProduceLine>> getLineDatas();

    Observable<TitleNumber> getTitleDatas();

    Observable<List<ItemWarningInfo>> getItemWarningDatas();

}
