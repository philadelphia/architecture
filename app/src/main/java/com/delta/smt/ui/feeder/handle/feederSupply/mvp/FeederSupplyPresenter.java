package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

@FragmentScope
public class FeederSupplyPresenter extends BasePresenter<FeederSupplyContract.Model, FeederSupplyContract.View>{
    @Inject
     FeederSupplyPresenter(FeederSupplyContract.Model model, FeederSupplyContract.View mView) {
        super(model, mView);
    }

    public void getAllToBeSuppliedFeeders(String workID){
        getModel().getAllToBeSuppliedFeeders(workID).subscribe(new Action1<Result<FeederSupplyItem>>() {
            @Override
            public void call(Result<FeederSupplyItem> feederSupplyItems) {
                getView().onSuccess(feederSupplyItems.getRows());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public void getFeederInsertionToSlot(String serinal_num, String material_num, String quantity){
        getModel().getFeederInsertionToSlot(serinal_num, material_num, quantity).subscribe(new Action1<Result<FeederSupplyItem>>() {
            @Override
            public void call(Result<FeederSupplyItem> feederSupplyItems) {
                getView().onSuccess(feederSupplyItems.getRows());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }


    public void upLoadFeederSupplyResult(String workID){
        getModel().getAllToBeSuppliedFeeders(workID).subscribe(new Action1<Result<FeederSupplyItem>>() {
            @Override
            public void call(Result<FeederSupplyItem> feederSupplyItems) {
                getView().onSuccess(feederSupplyItems.getRows());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public void upLoadToMES(){}
}
