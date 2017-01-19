package com.delta.smt.ui.hand_add.mvp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@ActivityScope
public class HandAddPresenter extends BasePresenter<HandAddContract.Model,HandAddContract.View> {

    @Inject
    public HandAddPresenter(HandAddContract.Model model, HandAddContract.View mView) {
        super(model, mView);
    }

    public void getItemHandAddDatas(){
        getModel().getItemHandAddDatas().subscribe(new Action1<Result<ItemHandAdd>>() {
            @Override
            public void call(Result<ItemHandAdd> itemHandAdds) {
                if ("0".equals(itemHandAdds.getCode())) {
                    getView().getItemHandAddDatas(itemHandAdds.getRows());
                }else {
                    getView().getItemHandAddDatasFailed(itemHandAdds.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemHandAddDatasFailed(throwable.getMessage());
            }
        });
    }

    public void getItemHandAddConfirm(String codition){
        getModel().getItemHandAddConfirm(codition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (result.getCode().equals("0")) {

                }else {
                    getView().getItemHandAddDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemHandAddDatasFailed(throwable.getMessage());
            }
        });
    }
}
