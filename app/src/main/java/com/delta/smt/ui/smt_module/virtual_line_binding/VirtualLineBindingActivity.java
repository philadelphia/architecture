package com.delta.smt.ui.smt_module.virtual_line_binding;

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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.VirtualModuleID;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModNumByMaterialResult;
import com.delta.smt.entity.ModuleDownMaintain;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.VirtualBindingResult;
import com.delta.smt.entity.VirtualLineBindingItem;
import com.delta.smt.entity.VirtualLineBindingItemNative;
import com.delta.smt.ui.smt_module.module_down_details.ModuleDownDetailsActivity;
import com.delta.smt.ui.smt_module.module_up_binding.ModuleUpBindingActivity;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.DaggerVirtualLineBindingComponent;
import com.delta.smt.ui.smt_module.virtual_line_binding.di.VirtualLineBindingModule;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingContract;
import com.delta.smt.ui.smt_module.virtual_line_binding.mvp.VirtualLineBindingPresenter;
import com.delta.smt.utils.BarCodeUtils;
import com.delta.smt.utils.StringUtils;

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

    private CommonBaseAdapter<VirtualLineBindingItemNative> adapterTitle;
    private CommonBaseAdapter<VirtualLineBindingItemNative> adapter;
    private List<VirtualLineBindingItemNative> dataList = new ArrayList<>();
    private List<VirtualLineBindingItemNative> dataSource = new ArrayList<>();

    //假数据
    //private List<String> virtualData = new ArrayList<>();

    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();

/*
    private String moduleIDCache = null;
    private String virtualModuleIDCache = null;
*/

    private int scan_position = -1;

    private String moduleCodeCache = null;
    private String virtualModuleCodeCache = null;
    List<String> data_tmp = null;

    String materialBlockNumber;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerVirtualLineBindingComponent.builder().appComponent(appComponent).virtualLineBindingModule(new VirtualLineBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //假数据
        /*virtualData.clear();
        virtualData.add("0353104700");
        virtualData.add("1512445A00");
        virtualData.add("15D2067A00");*/


        getPresenter().getAllVirtualLineBindingItems("1");
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("虚拟线体绑定");

        dataList.add(new VirtualLineBindingItemNative("模组编号", "虚拟模组ID"));
        adapterTitle = new CommonBaseAdapter<VirtualLineBindingItemNative>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineBindingItemNative item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_moduleID, item.getModule_id());
                holder.setText(R.id.tv_virtualModuleID, item.getVirtual_module_id());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineBindingItemNative item) {
                return R.layout.item_virtual_line_binding;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<VirtualLineBindingItemNative>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, VirtualLineBindingItemNative item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_moduleID, item.getModule_id());
                holder.setText(R.id.tv_virtualModuleID, item.getVirtual_module_id());
            }

            @Override
            protected int getItemViewLayoutId(int position, VirtualLineBindingItemNative item) {
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
    public void onSuccess(VirtualLineBindingItem data) {
        if(data.getMsg().toLowerCase().equals("success")){
            dataSource.clear();
            data_tmp = data.getRows();
            for(int i=0;i<data_tmp.size();i++){
                VirtualLineBindingItemNative virtualLineBindingItemNative = new VirtualLineBindingItemNative(data_tmp.get(i),"");
                dataSource.add(virtualLineBindingItemNative);
            }
            adapter.notifyDataSetChanged();
            adapterTitle.notifyDataSetChanged();
        }

    }

    @Override
    public void onFalied() {

    }

    @Override
    public void onSuccessBinding(VirtualBindingResult data) {

        if(data.getMsg().toLowerCase().equals("success")){
            Toast.makeText(this,"虚拟线体绑定完成！",Toast.LENGTH_SHORT).show();
            IntentUtils.showIntent(this, ModuleDownDetailsActivity.class);
            this.finish();
        }else{

        }
    }

    @Override
    public void onFailBinding() {

    }

    @Override
    public void onSuccessGetModByMate(ModNumByMaterialResult data) {
        String virtualID = data.getRows();
        if (isExistInDataSource(virtualID, data_tmp)) {
            if (virtualModuleCodeCache != null) {
                moduleCodeCache = null;
                virtualModuleCodeCache = null;
            }
            setItemHighLightBasedOnMID(virtualID);
            moduleCodeCache = virtualID;
            state = 2;
        } else {
            Toast.makeText(this, "列表中无与此模组对应料盘码!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailGetModByMate() {

    }

    @OnClick({R.id.btn_virtualLineBindingFinish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_virtualLineBindingFinish:
                if(dataSource.size()>0){
                    Toast.makeText(this,dataSource.size()+"",Toast.LENGTH_SHORT).show();
                    String res_id = "";
                    String res_virtual_id = "";
                    for (int i=0;i<dataSource.size()-1;i++){
                        res_id+=dataSource.get(i).getModule_id()+",";
                        res_virtual_id+=dataSource.get(i).getVirtual_module_id()+",";
                    }
                    res_id+=dataSource.get(dataSource.size()-1).getModule_id();
                    System.out.println(res_id);
                    res_virtual_id+=dataSource.get(dataSource.size()-1).getVirtual_module_id();
                    System.out.println(res_virtual_id);
                    getPresenter().getAllVirtualBindingResult(res_id,res_virtual_id);

                }
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

    int state = 1;


    @Override
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            case 1:
                try {
                    MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                    //通过料盘码获取模组编号
                    getPresenter().getModNumByMaterial(materialBlockNumber);
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "请扫描料盘码！", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (moduleCodeCache != null && virtualModuleCodeCache == null) {
                    try {
                        VirtualModuleID virtualModuleID = (VirtualModuleID) barCodeParseIpml.getEntity(barcode,BarCodeType.VIRTUALMODULE_ID);
                        if(isVirtualExistInDataSource(virtualModuleID.getSource(),dataSource)){
                            Toast.makeText(this, "此虚拟模组已经被绑定！", Toast.LENGTH_SHORT).show();
                        }else{
                            virtualModuleCodeCache = virtualModuleID.getSource();
                            setItemVirtualModuleID(virtualModuleCodeCache,moduleCodeCache);
                            updateBindingFinishButtonState();
                            //getPresenter().
                            /*getMaterialAndFeederBindingResult(dataSource.get(scan_position).getId()+"",feederCodeCache);*/
                            state = 1;
                        }
                    } catch (EntityNotFountException e) {
                        //Toast.makeText(this, "请扫描虚拟模组ID码！", Toast.LENGTH_SHORT).show();
                        try {
                            MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                            materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                            //通过料盘码获取模组编号
                            getPresenter().getModNumByMaterial(materialBlockNumber);

                        } catch (EntityNotFountException ee) {
                            ee.printStackTrace();
                            Toast.makeText(this, "不支持此码！", Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this, "请扫描料盘码！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        /*switch (state)
        if (BarCodeUtils.barCodeType(barcode) != null) {
            switch (BarCodeUtils.barCodeType(barcode)) {
                case MATERIAL_BLOCK_BARCODE:
                    try {
                        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                        String materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
                        int index = getModuleIndex(materialBlockNumber);
                        if (index != -1) {
                            if (virtualModuleIDCache != null) {
                                moduleIDCache = null;
                                virtualModuleIDCache = null;
                            }
                            //设置高亮
                            moduleIDCache = index + "";
                            setItemHighLightBasedOnMID(moduleIDCache);
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
            }
        } else {
            //Toast.makeText(this, "虚拟模组!", Toast.LENGTH_SHORT).show();
            if (moduleIDCache != null && virtualModuleIDCache == null) {
                setItemVirtualModuleID(barcode, moduleIDCache);
                updateBindingFinishButtonState();
            } else {
                Toast.makeText(this, "请首先扫描料盘码!", Toast.LENGTH_SHORT).show();
            }
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

    public void updateBindingFinishButtonState() {
        boolean temp = false;
        if (dataSource.size() > 0) {
            for (VirtualLineBindingItemNative list_item : dataSource) {
                if (list_item.getVirtual_module_id().equals("")) {
                    temp = true;
                    break;
                }
            }
            if(temp){
                btnVirtualLineBindingFinish.setEnabled(false);
            }else{
                btnVirtualLineBindingFinish.setEnabled(true);
            }
        }
    }

    /*public int getModuleIndex(String materialBlockNum) {
        for (int i = 0; i < virtualData.size(); i++) {
            if (virtualData.get(i).equals(materialBlockNum)) {
                return i + 1;
            }
        }
        return -1;
    }*/

    public void setItemVirtualModuleID(String virtualModuleID, String moduleID) {
        if (dataSource.size() > 0) {
            for (VirtualLineBindingItemNative listItem : dataSource) {
                if (listItem.getModule_id().equals(moduleID)) {
                    listItem.setVirtual_module_id(virtualModuleID);
                }
            }
            adapter.notifyDataSetChanged();
        }

    }

    public void setItemHighLightBasedOnMID(String moduleID) {
        for (int i = 0; i < dataSource.size(); i++) {
            if (dataSource.get(i).getModule_id().equals(moduleID)) {
                scan_position = i;
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public boolean isExistInDataSource(String item,List<String> data_tmp) {
        if (data_tmp.size() > 0) {
            for (String list_item : data_tmp) {
                if (list_item.equals(item)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    public boolean isVirtualExistInDataSource(String item, List<VirtualLineBindingItemNative> list) {
        if (list.size() > 0) {
            for (VirtualLineBindingItemNative list_item : list) {
                if (list_item.getVirtual_module_id().equals(item)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }
}
