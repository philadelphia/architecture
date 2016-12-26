package com.delta.smt.ui.feederwarning.FeederSupply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.FeederSupplyWorkItem;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public class FeederSupplyPresenter extends BasePresenter<FeederSupplyContract.Model, FeederSupplyContract.View>{
    @Inject
    public FeederSupplyPresenter(FeederSupplyContract.Model model, FeederSupplyContract.View mView) {
        super(model, mView);
    }

    public void getAllSupplyWorkItems(){
        getModel().getAllSupplyWorkItems().subscribe(new Action1<List<FeederSupplyWorkItem>>() {
            @Override
            public void call(List<FeederSupplyWorkItem> feederSupplyWorkItems) {
                getView().onSuccess(feederSupplyWorkItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
