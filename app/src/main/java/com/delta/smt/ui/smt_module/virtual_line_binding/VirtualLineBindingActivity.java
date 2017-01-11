package com.delta.smt.ui.smt_module.virtual_line_binding;

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

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.VirtualLineBindingItem;
import com.delta.smt.ui.smt_module.module_down_details.ModuleDownDetailsActivity;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.DaggerVirtualLineBindingComponent;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.VirtualLineBindingModule;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingContract;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingActivity extends BaseActivity<VirtualLineBindingPresenter> implements VirtualLineBindingContract.View, BarCodeIpml.OnScanSuccessListener{

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.btn_virtualLineBindingFinish)
    AppCompatButton btnVirtualLineBindingFinish;

    private CommonBaseAdapter<VirtualLineBindingItem> adapterTitle;
    private CommonBaseAdapter<VirtualLineBindingItem> adapter;
    private List<VirtualLineBindingItem> dataList = new ArrayList<VirtualLineBindingItem>();
    private List<VirtualLineBindingItem> dataSource = new ArrayList<VirtualLineBindingItem>();

    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerVirtualLineBindingComponent.builder().appComponent(appComponent).virtualLineBindingModule(new VirtualLineBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getAllVirtualLineBindingItems();
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("虚拟线体绑定");

        dataList.add(new VirtualLineBindingItem("模组编号", "虚拟模组ID"));
        adapterTitle = new CommonBaseAdapter<VirtualLineBindingItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineBindingItem item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_moduleID, item.getModuleID());
                holder.setText(R.id.tv_virtualModuleID, item.getVirtualModuleID());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineBindingItem item) {
                return R.layout.item_virtual_line_binding;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<VirtualLineBindingItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineBindingItem item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_moduleID, item.getModuleID());
                holder.setText(R.id.tv_virtualModuleID, item.getVirtualModuleID());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineBindingItem item) {
                return R.layout.item_virtual_line_binding;
            }

        };
        recyContent.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_virtual_line_binding;
    }

    @Override
    public void onSuccess(List<VirtualLineBindingItem> data) {
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
        adapterTitle.notifyDataSetChanged();
    }

    @Override
    public void onFalied() {

    }

    @OnClick({R.id.btn_virtualLineBindingFinish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_virtualLineBindingFinish:
                this.finish();
                IntentUtils.showIntent(this, ModuleDownDetailsActivity.class);
                break;

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
