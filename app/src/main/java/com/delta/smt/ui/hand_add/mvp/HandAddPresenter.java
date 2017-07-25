package com.delta.smt.ui.hand_add.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ItemHandAdd;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action0;
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

    public void getItemHandAddDatas(String producelines){

        Map<String,String> maps=new HashMap<>();
        maps.put("lines",producelines);
        producelines=GsonTools.createGsonListString(maps);
        Log.e("aaa", "getItemHandAddDatas: "+producelines );


        getModel().getItemHandAddDatas(producelines).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result<ItemHandAdd>>() {
            @Override
            public void call(Result<ItemHandAdd> itemHandAdds) {
                if (0 == itemHandAdds.getCode()) {

                    if (itemHandAdds.getRows().size() == 0) {
                        getView().showEmptyView();
                    }else {
                        getView().showContentView();
                        getView().getItemHandAddDatas(itemHandAdds.getRows());
                    }


                }else {
                    getView().getItemHandAddDatasFailed(itemHandAdds.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().getItemHandAddDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getItemHandAddConfirm(String codition, final String line){

        Map<String, String> mMap = new HashMap<>();
        mMap.put("id", codition);
        String mS = GsonTools.createGsonListString(mMap);
        Log.i(TAG, mS);

        getModel().getItemHandAddConfirm(mS).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (result.getCode() == 0) {
                    getItemHandAddDatas(line);
                }else {
                    getView().getItemHandAddDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemHandAddDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
