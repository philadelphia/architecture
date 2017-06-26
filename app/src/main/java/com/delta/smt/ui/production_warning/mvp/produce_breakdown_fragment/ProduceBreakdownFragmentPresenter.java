package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.ProduceWarning;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */
@FragmentScope
public class ProduceBreakdownFragmentPresenter extends BasePresenter<ProduceBreakdownFragmentContract.Model,ProduceBreakdownFragmentContract.View>{
    @Inject
    public ProduceBreakdownFragmentPresenter(ProduceBreakdownFragmentContract.Model model, ProduceBreakdownFragmentContract.View mView) {
        super(model, mView);
    }

    public void getItemBreakdownDatas(String condition){
        getModel().getItemBreakdownDatas(condition).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<ProduceWarning>() {
            @Override
            public void call(ProduceWarning itemBreakDowns) {
//                getView().getItemBreakdownDatas(itemBreakDowns);
                if (itemBreakDowns.getCode().equals("0")) {

                    if (itemBreakDowns.getRows().getFault().size()==0){
                        getView().showEmptyView();
                    }else {
                        getView().showContentView();
                        getView().getItemBreakdownDatas(itemBreakDowns.getRows().getFault());
                        Log.e("aaa", "fagment:故障数量" + String.valueOf(itemBreakDowns.getRows().getFault().size()));
                    }
                }else {
                    getView().getItemBreakdownDatasFailed(itemBreakDowns.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemBreakdownDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
