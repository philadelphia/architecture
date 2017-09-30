package com.delta.smt.ui.over_receive.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.OverReceiveDebitList;
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

                        if (overReceiveItems.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFalied(overReceiveItems);
                        }else {
                            getView().showContentView();
                            getView().onSuccess(overReceiveItems);
                        }

                    } else {
                        getView().showErrorView();
                        getView().onFalied(overReceiveItems);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try{
                    getView().showErrorView();
                    getView().onNetFailed(throwable);
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
                /*try {
                    getView().showLoadingView();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
        }).subscribe(new Action1<OverReceiveWarning>() {
            @Override
            public void call(OverReceiveWarning overReceiveWarning) {
                //getView().onGetWarningListSuccess(overReceiveWarning);
                try {
                    if ("0".equals(overReceiveWarning.getCode())) {

                        if (overReceiveWarning.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFalied(overReceiveWarning);
                        } else {
                            getView().showContentView();
                            getView().onSuccess(overReceiveWarning);
                        }

                    } else {
                        //getView().showErrorView();
                        getView().onFalied(overReceiveWarning);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    //getView().showErrorView();
                    getView().onNetFailed(throwable);
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
                if ("0".equals(overReceiveDebitResult.getCode())) {
                    getView().onSuccessOverReceiveDebit(overReceiveDebitResult);
                } else {
                    getView().onFaliedOverReceiveDebit(overReceiveDebitResult);
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onNetFailed(throwable);
            }
        });
    }

    public void getNoDebit() {
        getModel().getNoDebit().subscribe(new Action1<OverReceiveDebitList>() {
            @Override
            public void call(OverReceiveDebitList overReceiveDebitList) {
                if ("0".equals(overReceiveDebitList.getCode())) {
                    getView().onGetNoDebitSuccess(overReceiveDebitList);
                } else {
                    getView().onGetNoDebitFailed(overReceiveDebitList);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onGetNoDebitFailed(throwable);
            }
        });
    }

    public void debit(String value) {
        getModel().debit(value).subscribe(new Action1<OverReceiveDebitList>() {
            @Override
            public void call(OverReceiveDebitList overReceiveDebitList) {
                if ("0".equals(overReceiveDebitList.getCode())) {
                    getView().onGetNoDebitSuccess(overReceiveDebitList);
                } else {
                    getView().onGetNoDebitFailed(overReceiveDebitList);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onGetNoDebitFailed(throwable);
            }
        });
    }
}
