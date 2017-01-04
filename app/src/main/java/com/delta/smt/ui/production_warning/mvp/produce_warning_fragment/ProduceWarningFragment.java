package com.delta.smt.ui.production_warning.mvp.produce_warning_fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;
import com.delta.smt.R;
import com.delta.smt.app.App;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.di.produce_warning_fragment.DaggerProduceWarningFragmentCompnent;
import com.delta.smt.ui.production_warning.di.produce_warning_fragment.ProduceWarningFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningFragment extends BaseFragment<ProduceWarningFragmentPresenter>
        implements ProduceWarningFragmentContract.View,
        CommonBaseAdapter.OnItemClickListener<ItemWarningInfo>, BaseActiviy.OnBarCodeSucess, View.OnClickListener {


    @BindView(R.id.ryv_produce_warning)
    RecyclerView mRyvProduceWarning;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    private CommonBaseAdapter<ItemWarningInfo> mAdapter;
    private List<ItemWarningInfo> datas = new ArrayList<>();

    DialogRelativelayout mDialogRelativelayout;
    public ArrayList<String> barcodedatas = new ArrayList<>();
    private BaseActiviy baseActiviy;
    private String currentBarcode;
    private AlertDialog mAlertDialog;
    private PopupWindow mPopupWindow;
    private int tag=0;
    private static final String TAG = "ProduceWarningFragment";

    private android.os.Handler hanlder = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                if(mPopupWindow!=null&&mPopupWindow.isShowing()){
                    mPopupWindow.dismiss();
                    tag=0;
                }
            }

        }
    };
    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        mAdapter = new CommonBaseAdapter<ItemWarningInfo>(getContext(), datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemWarningInfo item, int position) {
                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_produce_line, item.getProductionline());
                holder.setText(R.id.tv_make_process, item.getMakeprocess());
                holder.setText(R.id.tv_warning_info, item.getWarninginfo());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemWarningInfo item) {
                return R.layout.item_produce_warning;
            }
        };
        mRyvProduceWarning.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceWarning.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: ");
        super.onAttach(context);
        if (context instanceof BaseActiviy) {
            this.baseActiviy = ((BaseActiviy) context);
            baseActiviy.addOnBarCodeSucess(this);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i(TAG, "onHiddenChanged: ");
        super.onHiddenChanged(hidden);
        if(mPopupWindow!=null&&mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
        tag=0;
        Log.e(TAG, "onHiddenChanged: " + hidden);
        if (hidden) {
            baseActiviy.removeOnBarCodeSuecss(this);
        } else {
            baseActiviy.addOnBarCodeSucess(this);
        }
    }


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceWarningFragmentCompnent.builder().appComponent(appComponent).
                produceWarningFragmentModule(new ProduceWarningFragmentModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getItemWarningDatas();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_produce_warning;
    }


    @Override
    public void getItemWarningDatas(List<ItemWarningInfo> itemWarningInfo) {
        datas.clear();
        datas.addAll(itemWarningInfo);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemWarningDatasFailed() {

    }


    @Override
    public void onItemClick(View view, ItemWarningInfo item, int position) {

        mDialogRelativelayout = new DialogRelativelayout(getContext());
        barcodedatas.clear();


        if (item.getTitle().equals("接料预警")) {

            makePopupWindow();

/*            mAlertDialog = new AlertDialog.Builder(getContext()).setView(mDialogRelativelayout)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tag = false;
                            dialog.dismiss();
                        }
                    }).show();
//            mAlertDialog.onWindowFocusChanged(false);
            if (mAlertDialog.isShowing()) {
//                barcodedatas.add(mScanSucess.getScanCode());
                mDialogRelativelayout.setDatas(barcodedatas);
            }*/



        } else {

        }
    }

    private void makePopupWindow() {
        mPopupWindow=new PopupWindow(mDialogRelativelayout, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);

        mDialogRelativelayout.setStrSecondTitle("请进行接料");
        barcodedatas.add("从备料车亮灯位置取出接料盘进行接料");
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

/*        ColorDrawable mColorDrawable = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(mColorDrawable);
        WindowManager.LayoutParams mLayoutParams=getmActivity().getWindow().getAttributes();
        mLayoutParams.alpha=0.4f;
        getmActivity().getWindow().setAttributes(mLayoutParams);
        mPopupWindow.update();*/

        //展示popupwindow
        mPopupWindow.showAtLocation(getView(), Gravity.CENTER,0,0);
    }

    @Override
    public void onScanSucess( String barcode) {
        Log.i(TAG, "onScanSucess: ");

        Log.e("barcode", barcode + Thread.currentThread().getName());
        currentBarcode = barcode;

        if (currentBarcode != null && mDialogRelativelayout != null&& mPopupWindow.isShowing()) {
            barcodedatas.add(currentBarcode);
            mDialogRelativelayout.setDatas(barcodedatas);
            tag++;
        }
        if (tag==3){
            hanlder.sendEmptyMessageDelayed(1, 1000);
        }


    }

    //popupwindow点击事件
    @Override
    public void onClick(View v) {
        mPopupWindow.dismiss();
        tag=0;
    }



}
