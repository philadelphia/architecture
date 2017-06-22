package com.delta.smt.ui.smt_module.JumpOver;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/6/21 10:37
 */


public class JumpOverModel extends BaseModel<ApiService> {

    @Inject
    public JumpOverModel(ApiService mApiService) {
        super(mApiService);
    }


    public Observable<Result> jumpOver(String value) {

        return getService().jumpOver(value).compose(RxsRxSchedulers.<Result>io_main());
    }

}
