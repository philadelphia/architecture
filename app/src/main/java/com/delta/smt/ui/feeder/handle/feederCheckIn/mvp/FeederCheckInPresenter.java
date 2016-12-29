package com.delta.smt.ui.feeder.handle.feederCheckIn.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInContract;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@FragmentScope
public class FeederCheckInPresenter extends BasePresenter<CheckInContract.Model, CheckInContract.View> {
    @Inject
    public FeederCheckInPresenter(CheckInContract.Model model, CheckInContract.View mView) {
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
