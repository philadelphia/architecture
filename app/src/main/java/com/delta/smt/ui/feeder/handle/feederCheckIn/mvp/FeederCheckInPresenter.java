package com.delta.smt.ui.feeder.handle.feederCheckIn.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInContract;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

@FragmentScope
public class FeederCheckInPresenter extends BasePresenter<CheckInContract.Model, CheckInContract.View> {
    @Inject
    public FeederCheckInPresenter(CheckInContract.Model model, CheckInContract.View mView) {
        super(model, mView);
    }

    public void getAllCheckedInFeeders() {
        getModel().getAllCheckedInFeeders().subscribe(new Action1<Result<FeederCheckInItem>>() {
            @Override
            public void call(Result<FeederCheckInItem> dataSource) {
                if (dataSource.getMessage().equalsIgnoreCase("success")){
                    getView().onSuccess(dataSource.getRows());
                }else {
                    getView().onFailed(dataSource.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

}
