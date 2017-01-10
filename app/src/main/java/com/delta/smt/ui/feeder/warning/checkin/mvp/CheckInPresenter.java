package com.delta.smt.ui.feeder.warning.checkin.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

@FragmentScope
public class CheckInPresenter extends BasePresenter<CheckInContract.Model, CheckInContract.View> {
    @Inject
    public CheckInPresenter(CheckInContract.Model model, CheckInContract.View mView) {
        super(model, mView);
    }

    public void getAllCheckedInFeeders() {
        getModel().getAllCheckedInFeeders().subscribe(new Action1<List<FeederCheckInItem>>() {
            @Override
            public void call(List<FeederCheckInItem> wareHouses) {
                getView().onSuccess(wareHouses);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

}
