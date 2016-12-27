package com.delta.smt.ui.production_warning.mvp.presenter;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */
@FragmentScope
public class ProduceWarningFragmentPresenter extends BasePresenter<ProduceWarningFragmentContract.Model,ProduceWarningFragmentContract.View>{

    @Inject
    public ProduceWarningFragmentPresenter(ProduceWarningFragmentContract.Model model, ProduceWarningFragmentContract.View mView) {
        super(model, mView);
    }
    public void getItemWarningDatas(){
        getModel().getItemWarningDatas().subscribe(new Action1<List<ItemWarningInfo>>() {
            @Override
            public void call(List<ItemWarningInfo> itemWarningInfos) {
                getView().getItemWarningDatas(itemWarningInfos);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemWarningDatasFailed();
            }
        });
    }
}
