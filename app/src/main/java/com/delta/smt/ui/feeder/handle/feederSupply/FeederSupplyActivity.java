package com.delta.smt.ui.feeder.handle.feederSupply;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.ui.feeder.handle.feederSupply.di.DaggerFeederSupplyComponent;
import com.delta.smt.ui.feeder.handle.feederSupply.di.FeederSupplyModule;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

public class FeederSupplyActivity extends BaseActiviy<FeederSupplyPresenter> implements FeederSupplyContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrow;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_moduleID)
    TextView tvModuleID;
    private CommonBaseAdapter<FeederSupplyItem> adapterTitle;
    private CommonBaseAdapter<FeederSupplyItem> adapter;
    private List<FeederSupplyItem> dataList = new ArrayList<>();
    private List<FeederSupplyItem> dataSource = new ArrayList<>();
    private static final String TAG = "FeederSupplyActivity";
    private boolean isHandleOVer = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feeder_supply;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerFeederSupplyComponent.builder().appComponent(appComponent).feederSupplyModule(new FeederSupplyModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: ");
        getPresenter().getAllToBeSuppliedFeeders();
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("备料");
        dataList.add(new FeederSupplyItem("", "", "", "", ""));
        adapterTitle = new CommonBaseAdapter<FeederSupplyItem>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyItem item, int position) {
                holder.itemView.setBackgroundColor(Color.GRAY);
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyItem item) {
                return R.layout.feeder_supply_item;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);


        adapter = new CommonBaseAdapter<FeederSupplyItem>(getContext(), dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyItem item, int position) {
                holder.setText(R.id.tv_location, item.getLocation());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_module, item.getModuleID());
                holder.setText(R.id.tv_timestamp, item.getTimeStamp());
                holder.setText(R.id.tv_status, item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyItem item) {
                return R.layout.feeder_supply_item;
            }

        };
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyContent.setAdapter(adapter);


    }


    @OnClick({R.id.tv_setting, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting:
                break;
            case R.id.btn_upload:
                getPresenter().upLoadToMES();
                break;
        }
    }

    @Override
    public void onSuccess(List<FeederSupplyItem> data) {
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
        for (FeederSupplyItem item : dataSource) {
            if (item.getStatus().equalsIgnoreCase("等待上模组")) {
                isHandleOVer = false;
                break;
            } else {
                isHandleOVer = true;
            }
        }

        if (isHandleOVer) {
            getPresenter().upLoadToMES();
        }
    }


    @Override
    public void onFalied() {

    }


    @Override
    public void onScanSuccess(String barcode) {
        Log.i(TAG, "onScanSuccess: ");
        super.onScanSuccess(barcode);
        Log.i(TAG, "barcode == " + barcode);
        for (FeederSupplyItem feederSupplyItem : dataSource) {
            if (!TextUtils.isEmpty(barcode)) {
                if (barcode.trim().equalsIgnoreCase(feederSupplyItem.getMaterialID())) {
                    tvModuleID.setText("模组料站: " + feederSupplyItem.getModuleID());
                    getPresenter().upLoadFeederSupplyResult();
                }
            }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
