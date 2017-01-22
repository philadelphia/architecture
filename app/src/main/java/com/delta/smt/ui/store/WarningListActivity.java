package com.delta.smt.ui.store;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.PcbFrameLocation;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
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
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.R.attr.type;
import static com.delta.buletoothio.barcode.parse.BarCodeType.FRAME_LOCATION;
import static com.delta.buletoothio.barcode.parse.BarCodeType.PCB_FRAME_LOCATION;
import static com.delta.smt.R.id.status;


/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListActivity extends BaseActivity<WarningListPresenter> implements WarningListContract.View {

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

    List<OutBound.DataBean> mList = new ArrayList<>();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    private CommonBaseAdapter<OutBound.DataBean> mAdapter;
    private int position = 0;
    private String mWorkNumberString;
    private String mMachineString;
    private String mMaterialNumberString;
    private MaterialBlockBarCode mMaterbarCode;
    private PcbFrameLocation mFramebarCode;
    private int mAmout;
    private int mId;
    private int mAmoutString;
    private int mAlarminfoId;
    private boolean mIsAlarmInfo;


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
            mAlarminfoId= bundle.getInt("alarminfoid");
            mIsAlarmInfo = bundle.getBoolean("alarminfo");
        }
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(this.getResources().getString(R.string.storetitle));
        edWork.setText(mWorkNumberString);
        edPcb.setText(mMaterialNumberString);
        edMachine.setText(mMachineString);
        edPcbDemand.setText(""+mAmoutString);
        List<ListWarning> list = new ArrayList<>();
        list.add(new ListWarning("", "", "", "", ""));

        CommonBaseAdapter<ListWarning> AdapterTitle = new CommonBaseAdapter<ListWarning>(this, list) {
            @Override
            protected void convert(CommonViewHolder holder, ListWarning item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.waring_editext));
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
                holder.setText(R.id.pcb_number, item.getPartNum());
                holder.setText(R.id.pcb_price, item.getSubShelfSerial());
                if (item.getAmount() == 0) {
                    holder.setText(R.id.pcb_thenumber, "");
                }else {
                    holder.setText(R.id.pcb_thenumber, ""+item.getAmount());
                }
                holder.setText(R.id.pcb_code, item.getPcbCode());
                holder.setText(R.id.pcb_time, item.getPcbCode());
                if (mMaterbarCode != null) {
                    if (mList.get(0).getPcbCode().equals(mMaterbarCode.getDeltaMaterialNumber())) {
                     //  getPresenter().fetchWarningNumber(); // TODO: 2017-01-15 网络请求操作
                    }

                }
                if ("0".equals(String.valueOf(item.getAmount()))) {

                    if (mFramebarCode != null) {
                        if (mFramebarCode.getSource().equals(mList.get(0).getSubShelfSerial())) {
                        }
                    }
                }
                if (item.isColor()) {
                    holder.setBackgroundColor(R.id.pcb_number, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_price, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_thenumber, Color.YELLOW);
         //           holder.setBackgroundColor(R.id.pcb_demand, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_code, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_time, Color.YELLOW);
                }

            }


            @Override
            protected int getItemViewLayoutId(int position, OutBound.DataBean item) {
                return R.layout.item_warning;
            }
        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyContetn.setAdapter(mAdapter);
        if (mIsAlarmInfo){
            getPresenter().fetchAlarminfoOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
        }else {
            getPresenter().fetchScheduleOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
        }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning;
    }


    @Override
    public void onFailed(String s) {
        ToastUtils.showMessage(this,s);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSucessState(String s) {
        Snackbar.make(activityMianview, "发料成功", Snackbar.LENGTH_INDEFINITE).show();
        if (mAmoutString - mAmout>0){
        recyContetn.scrollToPosition(position + 1);//请求+1
        mList.get(position).setColor(false);
        mList.get(position + 1).setColor(true);
        mAdapter.notifyDataSetChanged();
         mAmoutString=mAmoutString - mAmout;
        edPcbDemand.setText("" + mAmoutString);
        }
        if ((mAmoutString - mAmout)==0){
            mList.get(position).setColor(false);
            edPcbDemand.setText("0");
            if (mIsAlarmInfo){
                getPresenter().getAlarmSuccessfulState(mWorkNumberString,mAlarminfoId);
//                getPresenter().fetchAlarminfoOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
            }else {
                getPresenter().getScheduleSuccessState(mAlarminfoId);
//                getPresenter().fetchScheduleOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
            }
        }
        if(mAmoutString<mAmout){
            mList.get(position).setColor(false);
            mAmoutString=mAmout-mAmoutString;
            edPcbDemand.setText(""+mAmoutString);
            if (mIsAlarmInfo){
                getPresenter().getAlarmSuccessfulState(mWorkNumberString,mAlarminfoId);
//                getPresenter().fetchAlarminfoOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
            }else {
                getPresenter().getScheduleSuccessState(mAlarminfoId);
//                getPresenter().fetchScheduleOutBound(mAlarminfoId,mWorkNumberString,mMaterialNumberString,mAmoutString);
            }
        }


    }

    @Override
    public void onSucessStates(String s) {
        Snackbar.make(activityMianview, "发料成功", Snackbar.LENGTH_INDEFINITE).show();
        if ((mAmoutString - mAmout)==0){
            mList.get(position).setColor(false);
            mAmoutString=0;
            edPcbDemand.setText("0");
        }

    }

    @Override
    public void onOutSuccess(List<OutBound.DataBean> dataBeanList) {
        mList.clear();
        mList.addAll(dataBeanList);
        mList.get(0).setColor(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNumberSucces(PcbNumber.DataBean dataBean) {
        mList.get(position).setAmount(dataBean.getAmount());
        mList.get(position).setId(dataBean.getId());
        mAdapter.notifyDataSetChanged();
        mAmout = dataBean.getAmount();
        mId = dataBean.getId();
    }



    @Override
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();

            try {
                mMaterbarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                if (mMaterbarCode.getStreamNumber() != null) {
                    getPresenter().fetchPcbNumber(mMaterbarCode.getStreamNumber());
                }
            } catch (EntityNotFountException e) {
                e.printStackTrace();
                try {
                    mFramebarCode = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, PCB_FRAME_LOCATION);
                    if (mAmoutString <= mAmout) {
                        ToastUtils.showMessage(WarningListActivity.this,"请拆箱取出" + mAmoutString + "片", Snackbar.LENGTH_INDEFINITE);
                        //Snackbar.make(activityMianview, "请拆箱取出" + mAmoutString + "片", Snackbar.LENGTH_INDEFINITE).show();
                        if (mIsAlarmInfo) {
                            getPresenter().fetchPcbSuccess(mAlarminfoId, mAmoutString, mId, 0);
                        } else {
                            getPresenter().fetchPcbSuccess(mAlarminfoId, mAmoutString, mId, 1);
                        }
                    }
                    if (mAmoutString >= mAmout) {
                        if (mIsAlarmInfo) {
                            getPresenter().fetchPcbSuccess(mAlarminfoId, mAmout, mId, 0);
                        } else {
                            getPresenter().fetchPcbSuccess(mAlarminfoId, mAmout, mId, 1);
                        }
                    }

            } catch (EntityNotFountException e1) {
                e1.printStackTrace();

        }
        }


    }
}
