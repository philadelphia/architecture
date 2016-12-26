package com.delta.smt.ui.production_warning.mvp.produce_warning;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.ui.production_warning.item.TitleNumber;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */
@ActivityScope
public class ProduceWarningPresenter extends BasePresenter<ProduceWarningContract.Model,ProduceWarningContract.View> {

    @Inject
    public ProduceWarningPresenter(ProduceWarningContract.Model model, ProduceWarningContract.View mView) {
        super(model, mView);
    }

    public void getTitileNumber(){

        getModel().getTitleDatas().subscribe(new Action1<TitleNumber>() {
            @Override
            public void call(TitleNumber titleNumber) {
                getView().getTitleDatas(titleNumber);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getTitleDatasFailed();
            }
        });
    }


}
