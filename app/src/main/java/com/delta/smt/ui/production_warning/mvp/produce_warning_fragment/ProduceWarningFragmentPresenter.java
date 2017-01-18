package com.delta.smt.ui.production_warning.mvp.produce_warning_fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;

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
//                getView().getItemWarningDatas(itemWarningInfos);
                if (itemWarningInfos.getCode().equals("0")) {
                    getView().getItemWarningDatas(itemWarningInfos.getRows().getAlarm());
                }else {
                    getView().getItemWarningDatasFailed(itemWarningInfos.getMsg());
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
