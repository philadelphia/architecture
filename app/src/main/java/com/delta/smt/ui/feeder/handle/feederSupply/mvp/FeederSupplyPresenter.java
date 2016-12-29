package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

@FragmentScope
public class FeederSupplyPresenter extends BasePresenter<FeederSupplyContract.Model, FeederSupplyContract.View>{
    @Inject
    public FeederSupplyPresenter(FeederSupplyContract.Model model, FeederSupplyContract.View mView) {
        super(model, mView);
    }

    public void getAllToBeSuppliedFeeders(){
        getModel().getAllToBeSuppliedFeeders().subscribe(new Action1<List<FeederSupplyItem>>() {
            @Override
            public void call(List<FeederSupplyItem> feederSupplyItems) {
                getView().onSuccess(feederSupplyItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }


    public void upLoadFeederSupplyResult(){
        getModel().upLoadFeederSupplyResult().subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                getView().onUpLoadSuccess(result);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
