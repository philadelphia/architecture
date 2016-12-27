package com.delta.smt.ui.feederwarning.feederCheckIn.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyWorkItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@FragmentScope
public class FeederCheckInPresenter extends BasePresenter<FeederCheckInContract.Model, FeederCheckInContract.View> {
    @Inject
    public FeederCheckInPresenter(FeederCheckInContract.Model model, FeederCheckInContract.View mView) {
        super(model, mView);
    }

    public void getAllCheckedInFeeders() {
        getModel().getAllCheckedInFeeders().subscribe(new Action1<List<FeederSupplyWorkItem>>() {
            @Override
            public void call(List<FeederSupplyWorkItem> wareHouses) {
                getView().onSuccess(wareHouses);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

}
