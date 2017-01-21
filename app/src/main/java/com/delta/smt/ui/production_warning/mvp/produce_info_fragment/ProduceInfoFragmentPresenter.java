package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.Constant;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;

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
                    Log.e("aaa", "fagment:信息数量"+String.valueOf(itemInfos.getRows().getMessage().size()) );

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
                    getItemInfoDatas(Constant.initLine());
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
