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
public class WarningPresenter extends BasePresenter<WarningContract.Model,WarningContract.View> {
    @Inject
    public WarningPresenter(WarningContract.Model model, WarningContract.View mView) {
        super(model, mView);
    }

    public  void fatchWarning(){
        getModel().getWarning().subscribe(new Action1<List<ItemInfo>>() {
            @Override
            public void call(List<com.delta.smt.entity.ItemInfo> itemInfos) {
                getView().onSucess(itemInfos);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }
}
