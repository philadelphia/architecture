package com.delta.smt.ui.production_warning.mvp.produce_warning_fragment;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.Constant;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */
@FragmentScope
public class ProduceWarningFragmentPresenter extends BasePresenter<ProduceWarningFragmentContract.Model,ProduceWarningFragmentContract.View>{

    @Inject
    public ProduceWarningFragmentPresenter(ProduceWarningFragmentContract.Model model, ProduceWarningFragmentContract.View mView) {
        super(model, mView);
    }


    public void getItemWarningDatas(String condition){
        getModel().getItemWarningDatas(condition).subscribe(new Action1<ProduceWarning>() {

            @Override
            public void call(ProduceWarning itemWarningInfos) {
                //getView().getItemWarningDatas(itemWarningInfos);
                if (itemWarningInfos.getCode().equals("0")) {
                    getView().getItemWarningDatas(itemWarningInfos.getRows().getAlarm());
                    Log.e("aaa", "fagment:预警数量"+String.valueOf(itemWarningInfos.getRows().getAlarm().size()) );
                }else {
//                    getView().getItemWarningDatasFailed(itemWarningInfos.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemWarningDatasFailed(throwable.getMessage());
            }
        });
    }


    public void getItemWarningConfirm(String condition){
        getModel().getItemWarningConfirm(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if ("0".equals(result.getCode())) {
                    getView().getItemWarningDatasFailed(result.getMessage());
                    getItemWarningDatas(Constant.initLine());
                }else {
                    getView().getItemWarningDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemWarningDatasFailed(throwable.getMessage());
            }
        });
    }

    public void getBarcodeInfo(String condition){
        getModel().getBarcodeInfo(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if ("0".equals(result.getCode())) {
                    getView().getItemWarningDatasFailed(result.getMessage());
                    getItemWarningDatas(Constant.initLine());
                }else {
                    getView().getItemWarningDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemWarningDatasFailed(throwable.getMessage());
            }
        });
    }


}
