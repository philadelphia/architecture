package com.delta.smt.ui.smt_module.module_down_details;

import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.ui.smt_module.module_down_details.di.DaggerModuleDownDetailsComponent;
import com.delta.smt.ui.smt_module.module_down_details.di.ModuleDownDetailsModule;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsContract;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsActivity extends BaseActiviy<ModuleDownDetailsPresenter> implements ModuleDownDetailsContract.View, BarCodeIpml.OnScanSuccessListener{

    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.btn_feederMaintain)
    AppCompatButton btnFeederMaintain;

    private CommonBaseAdapter<ModuleDownDetailsItem> adapterTitle;
    private CommonBaseAdapter<ModuleDownDetailsItem> adapter;
    private List<ModuleDownDetailsItem> dataList = new ArrayList<>();
    private List<ModuleDownDetailsItem> dataSource = new ArrayList<>();

    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownDetailsComponent.builder().appComponent(appComponent).moduleDownDetailsModule(new ModuleDownDetailsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getAllModuleDownDetailsItems();
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("下模组");
        dataList.add(new ModuleDownDetailsItem("Feeder号", "料号", "下模组时间", "模组料站", "归属","流水码"));
        adapterTitle = new CommonBaseAdapter<ModuleDownDetailsItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem item, int position) {
                holder.itemView.setBackgroundColor(Color.GRAY);
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_serialID, item.getSerialID());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getModuleMaterialStationID());
                holder.setText(R.id.tv_ownership, item.getOwnership());
                holder.setText(R.id.tv_moduleDownTime, item.getModuleDownTime());

            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem item) {
                return R.layout.module_down_details_item;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<ModuleDownDetailsItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_serialID, item.getSerialID());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getModuleMaterialStationID());
                holder.setText(R.id.tv_ownership, item.getOwnership());
                holder.setText(R.id.tv_moduleDownTime, item.getModuleDownTime());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem item) {
                return R.layout.module_down_details_item;
            }

        };
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_down_details;
    }

    @Override
    public void onSuccess(List<ModuleDownDetailsItem> data) {
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFalied() {

    }

    @OnClick({R.id.header_back, R.id.header_setting/*, R.id.btn_upload*/})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                onBackPressed();
                break;
            case R.id.header_setting:

                break;
            /*case R.id.btn_upload:
                //getPresenter().upLoadToMES();
                break;*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            barCodeIpml.hasConnectBarcode();
        } catch (DevicePairedNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        barCodeIpml.onComplete();
    }

    @Override
    public void onScanSuccess(String barcode) {
        Toast.makeText(this,barcode,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (barCodeIpml.isEventFromBarCode(event)) {
            barCodeIpml.analysisKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
