package com.delta.smt.ui.store;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.PcbFrameLocation;
import com.delta.buletoothio.barcode.parse.exception.DCTimeFormatException;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.ui.store.di.DaggerWarningListComponent;
import com.delta.smt.ui.store.di.WarningListModule;
import com.delta.smt.ui.store.mvp.WarningListContract;
import com.delta.smt.ui.store.mvp.WarningListPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.PCB_FRAME_LOCATION;


/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListActivity extends BaseActivity<WarningListPresenter> implements WarningListContract.View,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ed_work)
    TextView edWork;
    @BindView(R.id.ed_pcb)
    TextView edPcb;
    @BindView(R.id.ed_machine)
    TextView edMachine;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.activity_mianview)
    LinearLayout activityMianview;
    @BindView(R.id.ed_pcb_demand)
    TextView edPcbDemand;
    @BindView(R.id.ed_littleBoard)
    TextView edLittleBoard;
    List<OutBound.DataBean> mList = new ArrayList<>();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    private CommonBaseAdapter<OutBound.DataBean> mAdapter;
    private int position = 0;
    private String mWorkNumberString;
    private String mMachineString;
    private String mMaterialNumberString;
    private MaterialBlockBarCode mMaterbarCode;
    private PcbFrameLocation mFramebarCode;
    private int mAmout = 0;
    private int mAdapterposition;
    private int mId;
    private int mAmoutString;
    private String mAlarminfoId;
    private boolean mIsAlarmInfo;
    private String mMainBoard;
    private String mSubBoard;
    private int mSunAmout;
    private int mSingleAmout;
    private int status=0;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerWarningListComponent.builder().appComponent(appComponent).warningListModule(new WarningListModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mWorkNumberString = bundle.getString("workNumber");
            mMachineString = bundle.getString("machine");
            mMaterialNumberString = bundle.getString("materialNumber");
            mAmoutString = bundle.getInt("amout");
            mAlarminfoId = bundle.getString("alarminfoid");
            mIsAlarmInfo = bundle.getBoolean("alarminfo");
            mMainBoard = bundle.getString("mainBoard");
            mSubBoard = bundle.getString("subBoard");
        }
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(this.getResources().getString(R.string.storetitle));
        edWork.setFocusable(true);
        edWork.setText(mWorkNumberString);
        edPcb.setText(mMaterialNumberString);
        edMachine.setText(mMainBoard);
        edLittleBoard.setText(mSubBoard);
        edPcbDemand.setText("" + mAmoutString);
        List<ListWarning> list = new ArrayList<>();
        list.add(new ListWarning("", "", "", "", ""));

        CommonBaseAdapter<ListWarning> AdapterTitle = new CommonBaseAdapter<ListWarning>(this, list) {
            @Override
            protected void convert(CommonViewHolder holder, ListWarning item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ListWarning item) {
                return R.layout.item_warning;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyTitle.setAdapter(AdapterTitle);

        mAdapter = new CommonBaseAdapter<OutBound.DataBean>(this, mList) {

            @Override
            protected void convert(CommonViewHolder holder, OutBound.DataBean item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.pcb_number, item.getPartNum());
                holder.setText(R.id.pcb_price, item.getLabelCode());
                holder.setText(R.id.pcb_thenumber, "" + item.getCount());
                if (item.getCount() != 0) {
                    mSingleAmout = item.getCount();

                    Log.e("info", mAmout + "");
                }
                holder.setText(R.id.pcb_code, item.getPcbCode());
                holder.setText(R.id.pcb_time, item.getDateCode());

//                if ("0".equals(String.valueOf(item.getAmount()))) {
//
//                    if (mFramebarCode != null) {
//                        if (mFramebarCode.getSource().equals(mList.get(0).getSubShelfSerial())) {
//                        }
//                    }
//                }

                if (item.getLevel() == 0) {
                    holder.itemView.setBackgroundColor(Color.WHITE);

                } else if (item.getLevel() == 1) {
                    holder.itemView.setBackgroundColor(Color.GRAY);
                } else if (item.getLevel() == 2) {
                    holder.itemView.setBackgroundColor(Color.GREEN);
                } else if (item.getLevel() == 3)
                    holder.itemView.setBackgroundColor(Color.YELLOW);


            }


            @Override
            protected int getItemViewLayoutId(int position, OutBound.DataBean item) {
                return R.layout.item_warning;
            }
        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyContetn.setAdapter(mAdapter);
        if (mIsAlarmInfo) {
            getPresenter().fetchAlarminfoOutBound(mAlarminfoId, mWorkNumberString, mMaterialNumberString, mAmoutString);
        } else {
            getPresenter().fetchScheduleOutBound(mAlarminfoId, mWorkNumberString, mMaterialNumberString, mAmoutString);
        }
        swipeRefreshWidget.setOnRefreshListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning;
    }


    @Override
    public void onFailed(String s) {
        ToastUtils.showMessage(this, s);
        if(swipeRefreshWidget.isRefreshing()){
            swipeRefreshWidget.setRefreshing (false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                IntentUtils.showIntent(this, StoreIssueActivity.class);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSucessState(String s) {
        Snackbar.make(activityMianview, "发料成功", Snackbar.LENGTH_LONG).show();
        Log.e("info", "------------------");
        mList.get(mAdapterposition).setLevel(3);
        mAdapter.notifyDataSetChanged();
        mSunAmout += mList.get(mAdapterposition).getCount();
        if ((mAmoutString - mList.get(mAdapterposition).getCount()) > 0) {
            mAmoutString = mAmoutString - mList.get(mAdapterposition).getCount();
            edPcbDemand.setText("" + mAmoutString);
        } else if ((mAmoutString - mList.get(mAdapterposition).getCount()) <= 0) {
            mAmoutString = 0;
            edPcbDemand.setText("0");
//            if (mIsAlarmInfo) {
//                getPresenter().getAlarmSuccessfulState(mWorkNumberString, mAlarminfoId);
////                getPresenter().fetchAlarminfoOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
//            } else {
//                getPresenter().getScheduleSuccessState(mAlarminfoId);
////                getPresenter().fetchScheduleOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
//            }
        }
        status=0;


    }

    @Override
    public void onSucessStates(String s) {
        Snackbar.make(activityMianview, "发料成功", Snackbar.LENGTH_LONG).show();
        if ((mAmoutString - mAmout) == 0) {
            mList.get(position).setLevel(3);
            mAdapter.notifyDataSetChanged();
            mAmoutString = 0;
            edPcbDemand.setText("0");
        }

    }

    @Override
    public void onFailedSate(String s) {
        ToastUtils.showMessage(this, s);
        status=0;
    }

    @Override
    public void onOutSubmit(String s) {
        SnackbarUtil.showMassage(activityMianview, "成功");
        IntentUtils.showIntent(this, StoreIssueActivity.class);


    }

    @Override
    public void onOutSuccess(List<OutBound.DataBean> dataBeanList) {
        mList.clear();
        mList.addAll(dataBeanList);
        mAdapter.notifyDataSetChanged();
        if(swipeRefreshWidget.isRefreshing()){
            swipeRefreshWidget.setRefreshing (false);
        }
    }

    @Override
    public void getNumberSucces(PcbNumber.DataBean dataBean) {
        mAmout += dataBean.getAmount();
        mList.get(position).setCount(dataBean.getAmount());
        mList.get(position).setId(dataBean.getId());
        mAdapter.notifyDataSetChanged();
        mId = dataBean.getId();
        if (mAmoutString < mAmout) {

        }
        if (mAmoutString >= mAmout) {

        }

    }

    @Override
    public void onCloseLightSucces(String s) {
        status=0;
        ToastUtils.showMessage(this, s);

    }


    @Override
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (status){
            case 0:
                try {
                    mMaterbarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    if (mMaterbarCode.getStreamNumber() != null) {
//                    getPresenter().fetchPcbNumber(mMaterbarCode.getStreamNumber());
                        if (mList != null) {
                            for (int i = 0; i < mList.size(); i++) {
                                if (mList.get(i).getBoxSerial().equals(mMaterbarCode.getStreamNumber())) {
                                    mId = mList.get(i).getId();
                                    mAdapterposition = i;
                                    if (!mList.get(i).isDelivery()) {
                                        mList.get(i).setDelivery(true);
                                        if (mAmoutString > 0) {
                                            if (mList.get(i).getCount() > mAmoutString) {
                                                //ToastUtils.showMessage(this, mList.get(i).getCount() - mAmoutString);
                                                if (mIsAlarmInfo) {
                                                    VibratorAndVoiceUtils.correctVibrator(this);
                                                    VibratorAndVoiceUtils.correctVoice(this);
                                                    getPresenter().fetchPcbSuccess(mAlarminfoId, mAmoutString, mId, 0);

                                                } else {
                                                    VibratorAndVoiceUtils.correctVibrator(this);
                                                    VibratorAndVoiceUtils.correctVoice(this);
                                                    getPresenter().fetchPcbSuccess(mAlarminfoId, mAmoutString, mId, 1);
                                                }
                                            } else {
                                                if (mIsAlarmInfo) {
                                                    VibratorAndVoiceUtils.correctVibrator(this);
                                                    VibratorAndVoiceUtils.correctVoice(this);
                                                    getPresenter().fetchPcbSuccess(mAlarminfoId, mList.get(i).getCount(), mId, 0);

                                                } else {
                                                    VibratorAndVoiceUtils.correctVibrator(this);
                                                    VibratorAndVoiceUtils.correctVoice(this);
                                                    getPresenter().fetchPcbSuccess(mAlarminfoId, mList.get(i).getCount(), mId, 1);

                                                }
                                            }
                                            if (mAmoutString - mList.get(i).getCount() < 0) {
                                                VibratorAndVoiceUtils.correctVibrator(this);
                                                VibratorAndVoiceUtils.correctVoice(this);
                                                ToastUtils.showMessage(this, "请拆箱取出" + (mAmoutString) + "片", 10000);
                                            }
                                            mList.get(i).setLevel(3);
                                            mAdapter.notifyDataSetChanged();
                                        } else {
                                            VibratorAndVoiceUtils.wrongVibrator(this);
                                            VibratorAndVoiceUtils.wrongVoice(this);
                                            SnackbarUtil.showMassage(activityMianview, "这箱料已经发过了，请不要反复扫码");
                                        }
                                    }
                                }else {
                                    VibratorAndVoiceUtils.wrongVibrator(this);
                                    VibratorAndVoiceUtils.wrongVoice(this);

                                }
                            }

                        }
                    }
                }catch (DCTimeFormatException exception){
                    ToastUtils.showMessage(this, exception.getMessage());
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    status=1;
                }
                catch (EntityNotFountException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    status=1;

                } catch (Exception e) {

                }
                break;
            case 1:
                try {
                    mFramebarCode = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, PCB_FRAME_LOCATION);
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    getPresenter().closeLight(mFramebarCode.getSource());

                } catch (EntityNotFountException e1) {
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    e1.printStackTrace();
                    status=0;
                } catch (Exception es) {

                }
                break;
        }


    }

    @Override
    public void showLoadingView() {
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        statusLayout.showErrorView();
        if (mIsAlarmInfo) {
            getPresenter().fetchAlarminfoOutBound(mAlarminfoId, mWorkNumberString, mMaterialNumberString, mAmoutString);
        } else {
            getPresenter().fetchScheduleOutBound(mAlarminfoId, mWorkNumberString, mMaterialNumberString, mAmoutString);
        }

    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
    }


    @OnClick(R.id.warning_sum)
    public void onClick() {
        if (mIsAlarmInfo) {
            getPresenter().getAlarmOutSumbit(mAlarminfoId);
        } else {
            getPresenter().getOutSumbit(mAlarminfoId);
        }
    }


    @Override
    public void onRefresh() {
        swipeRefreshWidget.setColorSchemeResources (android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        if (mIsAlarmInfo) {
            getPresenter().getRefresh(mAlarminfoId,mMaterialNumberString,Integer.parseInt(edPcbDemand.getText().toString()),0);
        } else {
            getPresenter().getRefresh(mAlarminfoId,mMaterialNumberString,Integer.parseInt(edPcbDemand.getText().toString()),1);
        }
    }
}
