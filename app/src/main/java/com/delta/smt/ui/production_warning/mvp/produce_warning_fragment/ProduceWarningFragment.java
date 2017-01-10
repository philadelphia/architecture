package com.delta.smt.ui.production_warning.mvp.produce_warning_fragment;

import android.app.usage.UsageEvents;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;
import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.MaterialStation;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.smt.R;
import com.delta.smt.app.App;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BroadcastBegin;
import com.delta.smt.entity.BroadcastCancel;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.ui.production_warning.di.produce_warning_fragment.DaggerProduceWarningFragmentCompnent;
import com.delta.smt.ui.production_warning.di.produce_warning_fragment.ProduceWarningFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;
import com.delta.smt.utils.BarCodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        BaseActiviy.OnBarCodeSucess, View.OnClickListener, ItemOnclick {


    @BindView(R.id.ryv_produce_warning)
    RecyclerView mRyvProduceWarning;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    private ItemCountdownViewAdapter<ItemWarningInfo> mAdapter;
    private List<ItemWarningInfo> datas = new ArrayList<>();

    DialogRelativelayout mDialogRelativelayout;
    public ArrayList<String> barcodedatas = new ArrayList<>();
    private BaseActiviy baseActiviy;
    private String currentBarcode;
    private AlertDialog mAlertDialog;
    private PopupWindow mPopupWindow;
    private int tag=0;
    private static final String TAG = "ProduceWarningFragment";


    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        mAdapter = new ItemCountdownViewAdapter<ItemWarningInfo>(getContext(), datas) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_produce_warning;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ItemWarningInfo itemWarningInfo, int position) {
                holder.setText(R.id.tv_title, itemWarningInfo.getTitle());
                holder.setText(R.id.tv_produce_line, itemWarningInfo.getProductionline());
                holder.setText(R.id.tv_make_process, itemWarningInfo.getMakeprocess());
                holder.setText(R.id.tv_warning_info, itemWarningInfo.getWarninginfo());
            }

        };
        mRyvProduceWarning.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceWarning.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemTimeOnclck(this);
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
    public void onResume() {
        super.onResume();
        if (null != mAdapter) {
            mAdapter.startRefreshTime();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
    }
    //注入初始化
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


/*    @Override
    public void onItemClick(View view, ItemWarningInfo item, int position) {
        EventBus.getDefault().post(new BroadcastCancel());
        mDialogRelativelayout = new DialogRelativelayout(getContext());
        barcodedatas.clear();


        if (item.getTitle().equals("接料预警")) {

            makePopupWindow();

            mAlertDialog = new AlertDialog.Builder(getContext()).setView(mDialogRelativelayout)
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
            }



        } else {

        }
    }*/
    //item点击事件处理
    @Override
    public void onItemClick(View item, int position) {
        EventBus.getDefault().post(new BroadcastCancel());
        mDialogRelativelayout = new DialogRelativelayout(getContext());
        barcodedatas.clear();
        final ItemWarningInfo mItemWarningInfo=datas.get(position);
        if (mItemWarningInfo.getTitle().equals("接料预警")) {

            makePopupWindow();

        } else {
            final ArrayList<String> dialogDatas = new ArrayList<>();
            mDialogRelativelayout.setStrSecondTitle("请求确认");
            dialogDatas.add("是否已经完成？");
            mDialogRelativelayout.setStrContent(dialogDatas);
            new AlertDialog.Builder(getContext()).setCancelable(false).setView(mDialogRelativelayout)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            EventBus.getDefault().post(new BroadcastBegin());
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mItemWarningInfo.setWarninginfo("预警信息：操作完成");
                            mAdapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new BroadcastBegin());
                        }
                    }).show();
        }
    }


    private void makePopupWindow() {
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

        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        Log.i("barcode",BarCodeUtils.barCodeType(barcode)+"  :"+ barcode + Thread.currentThread().getName());
        switch (BarCodeUtils.barCodeType(barcode)){
            case MATERIAL_BLOCK_BARCODE:
                if(tag==0){
                    try {
                        MaterialBlockBarCode mMaterialBlockBarCode =
                                (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                        currentBarcode ="料盘："+mMaterialBlockBarCode.getDeltaMaterialNumber();
                    } catch (EntityNotFountException e) {
                        e.printStackTrace();
                    }
                }else currentBarcode=null;

                break;

            case FEEDER:
                if (tag==1){
                    currentBarcode ="FeederID："+barcode;
                }else currentBarcode=null;

/*                    try {
                        Log.i("barcode", barcode);
                        Feeder mFeeder=(Feeder) barCodeParseIpml.getEntity(barcode,BarCodeType.FEEDER);
                        currentBarcode =mFeeder.getSource();
                        Log.i("barcode", currentBarcode);
                    } catch (EntityNotFountException e) {
                        e.printStackTrace();
                    }*/

                break;

            case MATERIAL_STATION:
                if (tag==2){
                    try {
                        MaterialStation mMaterialStation=(MaterialStation)barCodeParseIpml.getEntity(barcode,BarCodeType.MATERIAL_STATION);
                        currentBarcode ="料站："+mMaterialStation.getSource();
                    } catch (EntityNotFountException e) {
                        e.printStackTrace();
                    }
                }else currentBarcode=null;

                break;
            default:
                currentBarcode=null;
                break;

        }

//        currentBarcode = barcode;

        if (currentBarcode != null && mDialogRelativelayout != null&& mPopupWindow.isShowing()) {
            barcodedatas.add(currentBarcode);
            mDialogRelativelayout.setDatas(barcodedatas);
            tag++;
        }
        if (tag==3){
            hanlder.sendEmptyMessageDelayed(1, 1000);
        }

    }

    private android.os.Handler hanlder = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                if(mPopupWindow!=null&&mPopupWindow.isShowing()){
                    mPopupWindow.dismiss();
                    Toast.makeText(getContext(),"绑定成功",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new BroadcastBegin());
                    tag=0;
                }
            }

        }
    };

    //popupwindow点击事件
    @Override
    public void onClick(View v) {
        mPopupWindow.dismiss();
        EventBus.getDefault().post(new BroadcastBegin());
        tag=0;
    }

    //Activity预警广播触发事件
    @Override
    protected boolean UseEventBus() {
        return true;
    }

    //Activity预警广播触发事件处理
    @Subscribe
    public void event(ProduceWarningMessage produceWarningMessage){
        getPresenter().getItemWarningDatas();
        Log.e(TAG, "event1: ");
    }



}
