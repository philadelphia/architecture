package com.delta.smt.ui.smt_module.module_down_details;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;
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

    private CommonBaseAdapter<ModuleDownDetailsItem.RowsBean> adapterTitle;
    private CommonBaseAdapter<ModuleDownDetailsItem.RowsBean> adapter;
    private List<ModuleDownDetailsItem.RowsBean> dataList = new ArrayList<>();
    private List<ModuleDownDetailsItem.RowsBean> dataSource = new ArrayList<>();

    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();
    SharedPreferences preferences=null;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownDetailsComponent.builder().appComponent(appComponent).moduleDownDetailsModule(new ModuleDownDetailsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        preferences=getSharedPreferences("module_down", Context.MODE_PRIVATE);
        getPresenter().getAllModuleDownDetailsItems(preferences.getString("work_order",""));
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

        dataList.add(new ModuleDownDetailsItem.RowsBean(1,"料号","流水码","Feeder号", "模组料站", "归属", "下模组时间"));
        adapterTitle = new CommonBaseAdapter<ModuleDownDetailsItem.RowsBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_ID,item.getId()+"");
                holder.setText(R.id.tv_materialID, item.getMaterial_num());
                holder.setText(R.id.tv_serialID, item.getSerial_num());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());
                holder.setText(R.id.tv_ownership, item.getBelong());
                holder.setText(R.id.tv_moduleDownTime, item.getEnd_time());

            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem.RowsBean item) {
                return R.layout.item_module_down_details;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<ModuleDownDetailsItem.RowsBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_ID,item.getId()+"");
                holder.setText(R.id.tv_materialID, item.getMaterial_num());
                holder.setText(R.id.tv_serialID, item.getSerial_num());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());
                holder.setText(R.id.tv_ownership, item.getBelong());
                holder.setText(R.id.tv_moduleDownTime, item.getEnd_time());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem.RowsBean item) {
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
    public void onSuccess(ModuleDownDetailsItem data) {
        dataSource.clear();
        List<ModuleDownDetailsItem.RowsBean> rowsBean = data.getRows();
        dataSource.addAll(rowsBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFalied() {

    }

    @Override
    public void onSuccessMaintain(ModuleDownMaintain maintain) {
        if(maintain.getMsg().toLowerCase().equals("success")){
            getPresenter().getAllModuleDownDetailsItems(preferences.getString("work_order",""));
        }
    }

    @Override
    public void onFailMaintain() {

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

    @OnClick({R.id.btn_feederMaintain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_feederMaintain:

                if(dataSource.size()>0){
                    String res ="";
                    for (int i=0;i<dataSource.size()-1;i++){
                        res += dataSource.get(i).getId()+",";
                    }
                    res += dataSource.get(dataSource.size()-1).getId();
                    Toast.makeText(this,res,Toast.LENGTH_SHORT).show();
                    getPresenter().getAllModuleDownMaintainResult(res);
                }

                break;
        }
    }
}
