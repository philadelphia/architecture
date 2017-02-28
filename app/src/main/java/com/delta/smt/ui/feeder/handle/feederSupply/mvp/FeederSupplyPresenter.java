package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ResultFeeder;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

@FragmentScope
public class FeederSupplyPresenter extends BasePresenter<FeederSupplyContract.Model, FeederSupplyContract.View> {
    @Inject
    FeederSupplyPresenter(FeederSupplyContract.Model model, FeederSupplyContract.View mView) {
        super(model, mView);
    }

    public void getAllToBeSuppliedFeeders(String workID) {
        getModel().getAllToBeSuppliedFeeders(workID).subscribe(new Action1<Result<FeederSupplyItem>>() {
            @Override
            public void call(Result<FeederSupplyItem> feederSupplyItems) {
                if (feederSupplyItems.getMessage().equalsIgnoreCase("success")) {
                        getView().showContentView();
                        getView().onSuccess(feederSupplyItems.getRows());

                } else {
                    getView().onFailed(feederSupplyItems.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    //获取feeder上模组时间
    public void getFeederInsertionToSlotTimeStamp(String condition) {
        getModel().getFeederInsertionToSlotTimeStamp(condition).subscribe(new Action1<Result<FeederSupplyItem>>() {
            @Override
            public void call(Result<FeederSupplyItem> feederSupplyItems) {
                if (feederSupplyItems.getCode().equalsIgnoreCase("0")) {
                    getView().onSuccess(feederSupplyItems.getRows());
                } else {
                    getView().onFailed(feederSupplyItems.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void upLoadToMES() {
        getModel().upLoadFeederSupplyResult().subscribe(new Action1<ResultFeeder>() {
            @Override
            public void call(ResultFeeder resultFeeder) {
                if (!resultFeeder.isRows()) {
                    getView().onUpLoadFailed("上传失败，请手动上传到MES");
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
