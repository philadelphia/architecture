package com.delta.smt.ui.smt_module.module_up_binding;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.Constant;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.ui.main.update.DownloadService;
import com.delta.smt.ui.smt_module.module_up_binding.di.DaggerModuleUpBindingComponent;
import com.delta.smt.ui.smt_module.module_up_binding.di.ModuleUpBindingModule;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingContract;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingPresenter;
import com.delta.smt.utils.BarCodeUtils;
import com.delta.smt.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingActivity extends BaseActivity<ModuleUpBindingPresenter> implements ModuleUpBindingContract.View, BarCodeIpml.OnScanSuccessListener, CommonBaseAdapter.OnItemClickListener<ModuleUpBindingItem> {

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.btn_upload)
    AppCompatButton btnUpload;
    @BindView(R.id.tv_moduleStationID)
    TextView tvModuleID;

    private CommonBaseAdapter<ModuleUpBindingItem.RowsBean> adapterTitle;
    private CommonBaseAdapter<ModuleUpBindingItem.RowsBean> adapter;
    private List<ModuleUpBindingItem.RowsBean> dataList = new ArrayList<>();
    private List<ModuleUpBindingItem.RowsBean> dataSource = new ArrayList<>();

    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();

    private int scan_position = -1;

    private String materialBlockCodeCache = null;
    private String feederCodeCache = null;

    private String workItemID;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpBindingComponent.builder().appComponent(appComponent).moduleUpBindingModule(new ModuleUpBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Intent intent = ModuleUpBindingActivity.this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        getPresenter().getAllModuleUpBindingItems(workItemID);
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("上模组");

        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        dataList.add(new ModuleUpBindingItem.RowsBean(1,"料号", "流水码","Feeder号", "模组料站", "上模组时间"));
        adapterTitle = new CommonBaseAdapter<ModuleUpBindingItem.RowsBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_ID,item.getId()+"");
                holder.setText(R.id.tv_materialID, item.getMaterial_num());
                holder.setText(R.id.tv_serialID, item.getSerial_num());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSolt());
                holder.setText(R.id.tv_moduleUpTime, item.getCreate_time());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem.RowsBean item) {
                return R.layout.item_module_up_binding;
            }
        };

        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<ModuleUpBindingItem.RowsBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem.RowsBean item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_ID,item.getId()+"");
                holder.setText(R.id.tv_materialID, item.getMaterial_num());
                holder.setText(R.id.tv_serialID, item.getSerial_num());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSolt());
                holder.setText(R.id.tv_moduleUpTime, item.getCreate_time());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem.RowsBean item) {
                return R.layout.item_module_up_binding;
            }

        };

        recyContent.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_binding;
    }

    @Override
    public void onSuccess(ModuleUpBindingItem data) {
        dataSource.clear();
        List<ModuleUpBindingItem.RowsBean> rowsBeen = data.getRows();
        dataSource.addAll(rowsBeen);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFalied() {

    }

    @OnClick({R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                getPresenter().upLoadToMES();
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

        /*BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (BarCodeUtils.barCodeType(barcode)) {
            case FEEDER:
                try {
                    if (materialBlockCodeCache != null && feederCodeCache == null) {
                        Feeder feederCode = (Feeder) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                        setItemFeederNumber(feederCode.getSource(), materialBlockCodeCache);
                        setItemModuleUpDateAndTime(StringUtils.getNowDateAndTime(), materialBlockCodeCache);
                        feederCodeCache = feederCode.getSource();
                        if (isAllFeederScaned()) {
                            new AlertDialog.Builder(this)
                                    .setTitle("上模组完成")
                                    .setMessage("工单" + workItemID + "上模组完成！")
                                    .setCancelable(false)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent();
                                            intent.putExtra(Constant.WORK_ITEM_ID, workItemID);
                                            setResult(Constant.ACTIVITY_RESULT_WORK_ITEM_ID, intent);
                                            dialogInterface.dismiss();
                                            ModuleUpBindingActivity.this.finish();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    } else {
                        Toast.makeText(this, "请首先扫描料盘码!", Toast.LENGTH_SHORT).show();
                    }
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                }
                break;
            case MATERIAL_BLOCK_BARCODE:
                try {
                    MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    String materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                    if (isExistInDataSource(materialBlockNumber, dataSource)) {
                        if (feederCodeCache != null) {
                            materialBlockCodeCache = null;
                            feederCodeCache = null;
                        }
                        setItemHighLightBasedOnMID(materialBlockNumber);
                        materialBlockCodeCache = materialBlockNumber;
                    } else {
                        Toast.makeText(this, "列表中不包含此码!", Toast.LENGTH_SHORT).show();
                    }
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Toast.makeText(this, "此处不支持此类型码!", Toast.LENGTH_SHORT).show();
                break;
        }*/
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (barCodeIpml.isEventFromBarCode(event)) {
            barCodeIpml.analysisKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    public void setItemHighLightBasedOnMID(String materialID) {
        for (int i = 0; i < dataSource.size(); i++) {
            if (dataSource.get(i).getMaterial_num().equals(materialID)) {
                scan_position = i;
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setItemHighLightBasedOnMMSID(String moduleMaterialStationID) {
        for (int i = 0; i < dataSource.size(); i++) {
            if (dataSource.get(i).getSolt().equals(moduleMaterialStationID)) {
                scan_position = i;
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setItemFeederNumber(String feederNumber, String materialBlockCode) {
        if (dataSource.size() > 0) {
            for (ModuleUpBindingItem.RowsBean listItem : dataSource) {
                if (listItem.getMaterial_num().equals(materialBlockCode)) {
                    listItem.setFeeder_id(feederNumber);
                }
            }
            adapter.notifyDataSetChanged();
        }

    }

    public void setItemModuleUpDateAndTime(String upTime, String materialBlockCode) {
        if (dataSource.size() > 0) {
            for (ModuleUpBindingItem.RowsBean listItem : dataSource) {
                if (listItem.getMaterial_num().equals(materialBlockCode)) {
                    listItem.setCreate_time(upTime);
                }
            }
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onItemClick(View view, ModuleUpBindingItem item, final int item_position) {

    }

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

    public boolean isExistInDataSource(String item, List<ModuleUpBindingItem.RowsBean> list) {
        if (list.size() > 0) {
            for (ModuleUpBindingItem.RowsBean list_item : list) {
                if (list_item.getMaterial_num().equals(item)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    public boolean isAllFeederScaned() {
        if (dataSource.size() > 0) {
            for (ModuleUpBindingItem.RowsBean listItem : dataSource) {
                if (listItem.getFeeder_id().equals("-")) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }


}
