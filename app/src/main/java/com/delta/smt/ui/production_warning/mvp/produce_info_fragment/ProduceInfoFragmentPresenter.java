package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.ui.production_warning.item.ItemInfo;

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

    public void getItemInfoDatas(){
        getModel().getItemInfoDatas().subscribe(new Action1<List<ItemInfo>>() {
            @Override
            public void call(List<ItemInfo> itemInfos) {
                getView().getItemInfoDatas(itemInfos);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemInfoDatasFailed();
            }
        });
    }
}
