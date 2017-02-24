package com.delta.smt.ui.over_receive.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveWarning;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceivePresenter extends BasePresenter<OverReceiveContract.Model,OverReceiveContract.View> {

    @Inject
    public OverReceivePresenter(OverReceiveContract.Model model, OverReceiveContract.View mView) {
        super(model, mView);
    }

    public void getAllOverReceiveItems(){
        getModel().getAllOverReceiveItems().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }).subscribe(new Action1<OverReceiveWarning>() {
            @Override
            public void call(OverReceiveWarning overReceiveItems) {
                try{
                    if ("0".equals(overReceiveItems.getCode())) {

                        if (overReceiveItems.getRows().getData().size() == 0) {
                            getView().showEmptyView();
                        }else {
                            getView().showContentView();
                            getView().onSuccess(overReceiveItems);
                        }

                    } else {
                        getView().showErrorView();
                        getView().onFalied();

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                //getView().onSuccess(overReceiveItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try{
                    getView().showErrorView();
                    getView().onFalied();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    public void getOverReceiveItemsAfterSend(String str){
        getModel().getOverReceiveItemsAfterSend(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try {
                    getView().showLoadingView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).subscribe(new Action1<OverReceiveWarning>() {
            @Override
            public void call(OverReceiveWarning overReceiveWarning) {
                //getView().onSuccess(overReceiveWarning);
                try {
                    if ("0".equals(overReceiveWarning.getCode())) {

                        if (overReceiveWarning.getRows().getData().size() == 0) {
                            getView().showEmptyView();
                        } else {
                            getView().showContentView();
                            getView().onSuccess(overReceiveWarning);
                        }

                    } else {
                        getView().showErrorView();
                        getView().onFalied();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFalied();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void manualDebit(){
        getModel().getOverReceiveDebit().subscribe(new Action1<OverReceiveDebitResult>() {
            @Override
            public void call(OverReceiveDebitResult overReceiveDebitResult) {
                getView().onSuccessOverReceiveDebit(overReceiveDebitResult);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFaliedOverReceiveDebit();
            }
        });
    }

}
