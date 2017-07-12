package com.delta.smt.ui.maintain;


import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.PcbFrameLocation;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.LedLight;
import com.delta.smt.ui.maintain.di.DaggerPCBMaintainComponent;
import com.delta.smt.ui.maintain.di.PCBMaintainModule;
import com.delta.smt.ui.maintain.mvp.PCBMaintainContract;
import com.delta.smt.ui.maintain.mvp.PCBMaintainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Lin.Hou on 2017-03-13.
 */

public class PCBMaintainActivity extends BaseActivity<PCBMaintainPresenter> implements PCBMaintainContract.View, View.OnClickListener {


    @BindView(R.id.pcbmaintain_ed)
    EditText pcbmaintainEd;
    @BindView(R.id.pcbmaintain_btn)
    Button pcbmaintainBtn;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.activity_mianview)
    LinearLayout activityMianview;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrow;
    private CommonBaseAdapter<LedLight.RowsBean> mAdapter;
    private List<LedLight.RowsBean> mList = new ArrayList<>();
    private AlertDialog mUnboundDialog;
    private int mPosstion;
    private AlertDialog.Builder builder;
    private String mId;
    private int status=1;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerPCBMaintainComponent.builder().appComponent(appComponent).pCBMaintainModule(new PCBMaintainModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("PCB维护");
        builder = new AlertDialog.Builder(this);
        mAdapter = new CommonBaseAdapter<LedLight.RowsBean>(this, mList) {

            @Override
            protected void convert(CommonViewHolder holder, LedLight.RowsBean item, int position) {
                holder.setText(R.id.pcbmaintain_number, "" + (position + 1));
                holder.setText(R.id.pcbmaintain_framelocation, item.getLabelCode());
                if (item.getLightSerial() == null && "".equals(item.getLightSerial())) {
                    holder.setText(R.id.pcbmaintain_Led, "");
                } else {
                    holder.setText(R.id.pcbmaintain_Led, item.getLightSerial());
                }
                if (item.getColor() == 0) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (item.getColor() == 1) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else if (item.getColor() == 2) {
                    holder.itemView.setBackgroundColor(Color.GREEN);
                } else {
                    holder.itemView.setBackgroundColor(Color.RED);
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, LedLight.RowsBean item) {
                return R.layout.item_pcbmaintain;
            }
        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter.addHeaderView(View.inflate(this, R.layout.item_pcbmaintain, null));
        recyContetn.setAdapter(mAdapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pcbmaintain;
    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (status) {
            case 1:
                try {
                    PcbFrameLocation mFrameLocation = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.PCB_FRAME_LOCATION);
                    if (mList.size() != 0) {
                        int i = 0;
                        for (; i < mList.size(); i++) {

                            if (mFrameLocation.getSource().equals(mList.get(i).getCode())) {
                                break;
                            }

                        }
                        if (i < mList.size()) {
                            mList.get(i).setColor(1);
                            mPosstion = i;
//                    mId = "" + mList.get(i).getId();
                            mAdapter.notifyDataSetChanged();
                            status=2;

                        } else {
                            ToastUtils.showMessage(this, "未找到该架位，请进入添加架位!");
                        }
                    }
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    status=1;

//            mList.get(mPosstion).setFramelocation(barcode);
//            getPresenter().getUpdate(mList.get(mPosstion).getId() + "", barcode);
                } catch (Exception es) {

                }
                break;
            case 2:
                try {
                    PcbFrameLocation Location = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.PCB_FRAME_LOCATION);
                    mList.get(mPosstion).setFramelocation(Location.getSource());
                    getPresenter().getUpdate(mList.get(mPosstion).getId() + "", Location.getSource());
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
//            mList.get(mPosstion).setFramelocation(barcode);
//            getPresenter().getUpdate(mList.get(mPosstion).getId() + "", barcode);
                } catch (Exception es) {

                }
                break;
        }


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
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
    }

    @Override
    public void getSubshelf(List<LedLight.RowsBean> wareHouses) {
        mList.clear();
        mList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getUpdate(String wareHouses) {
        SnackbarUtil.showMassage(activityMianview, "绑灯成功");
        getPresenter().getSubshelf(pcbmaintainEd.getText().toString());
        status=1;
    }

    @Override
    public void onFailed(String error) {
        SnackbarUtil.show(activityMianview, error);
    }

    @Override
    public void Unbound(String wareHouses) {
        SnackbarUtil.showMassage(activityMianview, "解绑成功");
        getPresenter().getSubshelf(pcbmaintainEd.getText().toString());
    }

    @Override
    public void UnboundDialog(String code) {
        mUnboundDialog = builder.create();
        mUnboundDialog.show();
        mUnboundDialog.setContentView(R.layout.dialog_rollback);
        TextView textView = (TextView) mUnboundDialog.findViewById(R.id.dialog_rollback_content);
        textView.setText("请确认是否解绑!!");
        Button affirmbutton = (Button) mUnboundDialog.findViewById(R.id.rollback_affirm);
        Button cancelbutton = (Button) mUnboundDialog.findViewById(R.id.rollback_cancel);
        affirmbutton.setOnClickListener(this);
        cancelbutton.setOnClickListener(this);
        mList.get(mPosstion).setColor(0);
        mId=code;
        status=1;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rollback_affirm:
                if (mUnboundDialog.isShowing()) {
                    mUnboundDialog.dismiss();
                }
                getPresenter().getUnbound(mId);
                break;
            case R.id.rollback_cancel:
                if (mUnboundDialog.isShowing()) {
                    mUnboundDialog.dismiss();
                    pcbmaintainEd.setFocusable(true);
                }
                break;
        }
    }

    @OnClick({R.id.pcbmaintain_btn, R.id.pcbmaintainnext_btn})
    public void onClicks(View view) {
        switch (view.getId()) {
            case R.id.pcbmaintain_btn:
                if (pcbmaintainEd.getText()!=null){
            getPresenter().getSubshelf(pcbmaintainEd.getText().toString());
            pcbmaintainEd.clearFocus();
           pcbmaintainEd.setFocusable(false);}
                break;
            case R.id.pcbmaintainnext_btn:
                pcbmaintainEd.setText("");
                pcbmaintainEd.setFocusable(true);
                pcbmaintainEd.setFocusableInTouchMode(true);
                pcbmaintainEd.requestFocus();
                pcbmaintainEd.findFocus();
                mList.clear();
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
