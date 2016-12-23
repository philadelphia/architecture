package com.delta.smt.api;


import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.User;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by Administrator on 2016/3/23.
 */
public interface ApiService {
    @POST("ams/library/user/login2")
    Observable<LoginResult> login(@Body User user);

    Observable<List<ItemProduceLine>> getLineDatas();
}
