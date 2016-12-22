package com.delta.smt.api;


import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by Administrator on 2016/3/23.
 */
public interface ApiService {
    @POST("ams/library/user/login2")
    Observable<LoginResult> login(@Body User user);
}
