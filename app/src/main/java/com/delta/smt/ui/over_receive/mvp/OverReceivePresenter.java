package com.delta.smt.ui.over_receive.mvp;

import android.content.Context;
import android.widget.Toast;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OverReceiveItem;

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
        getModel().getAllOverReceiveItems().subscribe(new Action1<List<OverReceiveItem>>() {
            @Override
            public void call(List<OverReceiveItem> overReceiveItems) {
                getView().onSuccess(overReceiveItems);
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
    }
}
