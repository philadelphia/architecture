package com.delta.smt.ui.smt_module.module_down_details;

import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.ui.smt_module.module_down_details.di.DaggerModuleDownDetailsComponent;
import com.delta.smt.ui.smt_module.module_down_details.di.ModuleDownDetailsModule;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsContract;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsPresenter;
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsActivity extends BaseActivity<ModuleDownDetailsPresenter> implements ModuleDownDetailsContract.View, BarCodeIpml.OnScanSuccessListener{

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

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
        //headerTitle.setText("下模组");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("下模组");

        dataList.add(new ModuleDownDetailsItem("Feeder号", "料号", "下模组时间", "模组料站", "归属","流水码"));
        adapterTitle = new CommonBaseAdapter<ModuleDownDetailsItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_serialID, item.getSerialID());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getModuleMaterialStationID());
                holder.setText(R.id.tv_ownership, item.getOwnership());
                holder.setText(R.id.tv_moduleDownTime, item.getModuleDownTime());

            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem item) {
                return R.layout.item_module_down_details;
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
                return R.layout.item_module_down_details;
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
}
