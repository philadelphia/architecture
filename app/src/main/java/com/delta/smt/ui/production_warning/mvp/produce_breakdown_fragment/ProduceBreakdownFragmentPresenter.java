package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;

import java.util.List;

import javax.inject.Inject;

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

    public void getItemBreakdownDatas(){
        getModel().getItemBreakdownDatas().subscribe(new Action1<List<ItemBreakDown>>() {
            @Override
            public void call(List<ItemBreakDown> itemBreakDowns) {
                getView().getItemBreakdownDatas(itemBreakDowns);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemBreakdownDatasFailed();
            }
        });
    }
}
