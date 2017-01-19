package com.delta.smt.ui.over_receive.mvp;

import android.content.Context;
import android.widget.Toast;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveWarning;

import java.util.List;

import javax.inject.Inject;

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
        getModel().getAllOverReceiveItems().subscribe(new Action1<OverReceiveWarning>() {
            @Override
            public void call(OverReceiveWarning overReceiveItems) {
                getView().onSuccess(overReceiveItems);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }

    public void getOverReceiveItemsAfterSend(String str){
        getModel().getOverReceiveItemsAfterSend(str).subscribe(new Action1<OverReceiveWarning>() {
            @Override
            public void call(OverReceiveWarning overReceiveWarning) {
                getView().onSuccess(overReceiveWarning);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }

    public void getOverReceiveItemsAfterSendArrive(String str){
        getModel().getOverReceiveItemsAfterSendArrive(str).subscribe(new Action1<OverReceiveWarning>() {
            @Override
            public void call(OverReceiveWarning overReceiveWarning) {
                getView().onSuccess(overReceiveWarning);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFalied();
            }
        });
    }

    public void manualDebit(){
        Toast.makeText((Context) getView(),"手动扣账",Toast.LENGTH_SHORT).show();
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
