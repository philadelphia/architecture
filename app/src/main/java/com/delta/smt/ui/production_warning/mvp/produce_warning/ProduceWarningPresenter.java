package com.delta.smt.ui.production_warning.mvp.produce_warning;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.ProduceWarning;
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


    public void getTitileNumber(String condition){

        getModel().getTitleDatas(condition).subscribe(new Action1<ProduceWarning>() {
            @Override
            public void call(ProduceWarning produceWarning) {
                if (produceWarning.getCode().equals("0")) {
                    getView().getTitleDatas(new TitleNumber(produceWarning.getRows().getAlarm().size(),
                            produceWarning.getRows().getFault().size(),
                            produceWarning.getRows().getMessage().size()));
                    Log.e("aaa", "预警数量："+String.valueOf(produceWarning.getRows().getAlarm().size()) );
                    Log.e("aaa", "故障数量："+String.valueOf(produceWarning.getRows().getFault().size()) );
                    Log.e("aaa", "信息数量："+String.valueOf(produceWarning.getRows().getMessage().size()) );

                }else {
                    getView().getTitleDatasFailed(produceWarning.getMsg());
                    Log.i("aaa", produceWarning.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
//                getView().getTitleDatasFailed(throwable.getMessage());
                Log.i("aaa", throwable.getMessage());
            }
        });
    }




}
