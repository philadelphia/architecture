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
    public void getItemWarningDatas(){
        getModel().getItemWarningDatas().subscribe(new Action1<List<ItemWarningInfo>>() {
            @Override
            public void call(List<ItemWarningInfo> itemWarningInfos) {
                getView().getItemWarningDatas(itemWarningInfos);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemWarningDatasFailed();
            }
        });
    }

/*    public void makePopupWindow(PopupWindow mPopupWindow, DialogRelativelayout mDialogRelativelayout
            , ArrayList<String> barcodedatas) {
        mPopupWindow=new PopupWindow(mDialogRelativelayout, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);

        mDialogRelativelayout.setStrSecondTitle("请进行接料");
        barcodedatas.add("从备料车亮灯位置取出接料盘进行接料，" +
                "接料完成后请扫描新料盘/FeederID/料站 完成接料操作");
        mDialogRelativelayout.setStrContent(barcodedatas);

        //动态生成布局
        RelativeLayout layout = new RelativeLayout(getmActivity());
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView = new TextView(getmActivity());
        textView.setText("确定");
        textView.setTextColor(Color.RED);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        textView.setOnClickListener(this);
        RelativeLayout.LayoutParams parm = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        parm.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.addView(textView,parm);
        mDialogRelativelayout.addView(layout);
    }*/
}
