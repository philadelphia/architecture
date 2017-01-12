package com.delta.smt.ui.smt_module.module_up_binding;

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
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.ui.smt_module.module_up_binding.di.DaggerModuleUpBindingComponent;
import com.delta.smt.ui.smt_module.module_up_binding.di.ModuleUpBindingModule;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingContract;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingPresenter;
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingActivity extends BaseActivity<ModuleUpBindingPresenter> implements ModuleUpBindingContract.View, BarCodeIpml.OnScanSuccessListener,CommonBaseAdapter.OnItemClickListener<ModuleUpBindingItem>{

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

    private CommonBaseAdapter<ModuleUpBindingItem> adapterTitle;
    private CommonBaseAdapter<ModuleUpBindingItem> adapter;
    private List<ModuleUpBindingItem> dataList = new ArrayList<ModuleUpBindingItem>();
    private List<ModuleUpBindingItem> dataSource = new ArrayList<ModuleUpBindingItem>();

    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();

    private int scan_position = -1;

    private String materialBlockCodeCache = null;
    private String feederCodeCache = null;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpBindingComponent.builder().appComponent(appComponent).moduleUpBindingModule(new ModuleUpBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getAllModuleUpBindingItems();
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        //headerTitle.setText("上模组");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("上模组");

        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        dataList.add(new ModuleUpBindingItem("Feeder号", "料号", "模组料站", "上模组时间", "流水码"));
        adapterTitle = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_serialID, item.getSerialID());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getModuleMaterialStationID());
                holder.setText(R.id.tv_moduleUpTime, item.getModuleUpTime());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
                return R.layout.item_module_up_binding;
            }
        };

        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
                if (scan_position==-1){
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position==position){
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else{
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_serialID, item.getSerialID());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getModuleMaterialStationID());
                holder.setText(R.id.tv_moduleUpTime, item.getModuleUpTime());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
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
    public void onSuccess(List<ModuleUpBindingItem> data) {
        dataSource.clear();
        dataSource.addAll(data);
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

        //Toast.makeText(this,"不支持此类型码！",Toast.LENGTH_SHORT).show();
        switch (BarCodeUtils.barCodeType(barcode)){
            case FEEDER:
                Toast.makeText(this,"支持此类型码！",Toast.LENGTH_SHORT).show();
                break;
        }

        /*BarCodeParseIpml barCodeParseIpml=new BarCodeParseIpml();
        switch (BarCodeUtils.barCodeType(barcode)){
            case FEEDER:
                //Toast.makeText(this,"不支持此类型码！",Toast.LENGTH_SHORT).show();
                try {
                    if(materialBlockCodeCache!=null){
                        Feeder feederCode = (Feeder)barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                        setItemFeederNumber(feederCode.getSource(),materialBlockCodeCache);
                    }else {
                        Toast.makeText(this,"请首先扫描料盘码！",Toast.LENGTH_SHORT).show();
                    }
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                }
                break;
            case MATERIAL_BLOCK_BARCODE:
                try {
                    MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode)barCodeParseIpml.getEntity(barcode,BarCodeType.MATERIAL_BLOCK_BARCODE);
                    String materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                    if(isExistInDataSource(materialBlockNumber,dataSource)){
                        setItemHighLightBasedOnMID(materialBlockNumber);
                        materialBlockCodeCache = materialBlockNumber;
                    }else{
                        Toast.makeText(this,"列表中不包含此码！",Toast.LENGTH_SHORT).show();
                    }
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Toast.makeText(this,"此处不支持此类型码！",Toast.LENGTH_SHORT).show();
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


    public void setItemHighLightBasedOnMID(String materialID){
        for (int i=0;i<dataSource.size();i++){
            if (dataSource.get(i).getMaterialID().equals(materialID)){
                scan_position = i;
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setItemHighLightBasedOnMMSID(String moduleMaterialStationID){
        for (int i=0;i<dataSource.size();i++){
            if (dataSource.get(i).getModuleMaterialStationID().equals(moduleMaterialStationID)){
                scan_position = i;
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setItemFeederNumber(String feederNumber,String materialBlockCode){
        for(ModuleUpBindingItem listItem:dataSource){
            if(listItem.getMaterialID().equals(materialBlockCode)){
                listItem.setFeederID(feederNumber);
            }
        }
        adapter.notifyDataSetChanged();
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

    public boolean isExistInDataSource(String item,List<ModuleUpBindingItem> list){
        for(ModuleUpBindingItem list_item:list){
            if(list_item.getMaterialID().equals(item)){
                return true;
            }
        }
        return false;
    }
}
