package com.delta.smt.ui.production_warning.produce_warning_fragment.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Author Fuxiang.Zhang
 * Date   2016/12/23
 */
@FragmentScope
public class ProduceWarningFragmentPresenter extends BasePresenter<ProduceWarningFragmentContract.Model, ProduceWarningFragmentContract.View> {

    @Inject
    public ProduceWarningFragmentPresenter(ProduceWarningFragmentContract.Model model, ProduceWarningFragmentContract.View mView) {
        super(model, mView);
    }


    public void getWarningItemList(String condition) {
        getModel().getWarningItemList(condition).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<ProduceWarning>() {

            @Override
            public void call(ProduceWarning produceWarning) {

                if (produceWarning.getCode().equals("0")) {

                    if (produceWarning.getRows().getAlarm().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                        getView().onGetWarningItemSuccess(produceWarning.getRows().getAlarm());
                        Log.e("aaa", "fragment:预警数量" + String.valueOf(produceWarning.getRows().getAlarm().size()));
                    }

                } else {
                    getView().onGetWarningItemFailed(produceWarning.getMsg());
                    //getView().showErrorView();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    //getView().showErrorView();
                    getView().onGetWarningItemFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getItemWarningConfirm(String condition) {
        getModel().getItemWarningConfirm(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()) {
                    getView().getItemWarningConfirmSuccess();

                } else {
                    getView().onGetWarningItemFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onGetWarningItemFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getBarcodeInfo(String condition) {
        getModel().getBarcodeInfo(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()) {
                    getView().getItemWarningConfirmSuccess();
                } else {
                    getView().onGetWarningItemFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onGetWarningItemFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
