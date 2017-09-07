package com.delta.smt.ui.production_warning.produce_info_fragment.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action0;
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

    public void getItemInfoDatas(String condition){
        getModel().getItemInfoDatas(condition).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<ProduceWarning>() {
            @Override
            public void call(ProduceWarning itemInfos) {
//                getView().getItemInfoDatas(itemInfos);
                if ("0".equals(itemInfos.getCode())) {

                    if (itemInfos.getRows().getMessage().size()==0){
                        getView().showEmptyView();
                    }else {
                        getView().showContentView();
                        getView().getItemInfoDatas(itemInfos.getRows().getMessage());
                        Log.e("aaa", "fagment:信息数量" + String.valueOf(itemInfos.getRows().getMessage().size()));
                    }
                }else {
                    getView().getItemInfoDatasFailed(itemInfos.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemInfoDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getItemInfoConfirm(String condition){

        Map<String, String > map = new HashMap<>();
        map.put("id", condition);
        condition = GsonTools.createGsonListString(map);

        getModel().getItemInfoConfirm(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0==result.getCode()) {
                    getView().getItemInfoConfirmSucess();
                    getView().getItemInfoDatasFailed(result.getMessage());

                }else {
                    getView().getItemInfoDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemInfoDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
