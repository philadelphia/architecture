package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.ItemInfo;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@FragmentScope
public class ArrangePresenter extends BasePresenter<ArrangeContract.Model,ArrangeContract.View> {
    @Inject
    public ArrangePresenter(ArrangeContract.Model model, ArrangeContract.View mView) {
        super(model, mView);
    }
    public void fatchArrange(){
        getModel().getArrange().subscribe(new Action1<List<ItemInfo>>() {
            @Override
            public void call(List<ItemInfo> itemInfos) {
                getView().onSucess(itemInfos);
            }
        },new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
