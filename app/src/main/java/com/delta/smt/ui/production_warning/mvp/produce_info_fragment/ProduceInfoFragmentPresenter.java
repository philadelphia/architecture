package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemInfo;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */
@FragmentScope
public class ProduceInfoFragmentPresenter extends BasePresenter<ProduceInfoFragmentContract.Model,ProduceInfoFragmentContract.View>{

    @Inject
    public ProduceInfoFragmentPresenter(ProduceInfoFragmentContract.Model model, ProduceInfoFragmentContract.View mView) {
        super(model, mView);
    }

    public void getItemInfoDatas(String condition){
        getModel().getItemInfoDatas(condition).subscribe(new Action1<ProduceWarning>() {
            @Override
            public void call(ProduceWarning itemInfos) {
//                getView().getItemInfoDatas(itemInfos);
                if ("0".equals(itemInfos.getCode())) {
                    getView().getItemInfoDatas(itemInfos.getRows().getMessage());
                }else {
                    getView().getItemInfoDatasFailed(itemInfos.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemInfoDatasFailed(throwable.getMessage());
            }
        });
    }

    public void getItemInfoConfirm(String condition){
        getModel().getItemInfoConfirm(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if ("0".equals(result.getCode())) {
                    getView().getItemInfoDatasFailed(result.getMessage());
                    getItemInfoDatas(ProduceWarningActivity.initLine());
                }else {
                    getView().getItemInfoDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemInfoDatasFailed(throwable.getMessage());
            }
        });
    }
}
