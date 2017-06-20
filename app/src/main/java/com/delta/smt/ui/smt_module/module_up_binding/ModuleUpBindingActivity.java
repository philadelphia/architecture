package com.delta.smt.ui.smt_module.module_up_binding;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.RecycleViewUtils;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.utils.ViewUtils;
import com.delta.commonlibs.widget.CustomPopWindow;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.UpLoadEntity;
import com.delta.smt.ui.smt_module.module_up_binding.di.DaggerModuleUpBindingComponent;
import com.delta.smt.ui.smt_module.module_up_binding.di.ModuleUpBindingModule;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingContract;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingActivity extends BaseActivity<ModuleUpBindingPresenter> implements ModuleUpBindingContract.View, CompoundButton.OnCheckedChangeListener {

    private String moduleUpAutomaticUpload = null;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyclerViewContent;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    FrameLayout frameLayout;
    @BindView(R.id.automatic_upload)
    AppCompatCheckBox ckb_automaticUpload;
    @BindView(R.id.btn_upload)
    AppCompatButton btn_upLoad_mes;


    @BindView(R.id.tv_work_order)
    TextView tv_workOrder;
    @BindView(R.id.tv_line_num)
    TextView tv_side;
    @BindView(R.id.tv_line_name)
    TextView tv_line;

    private int state = 1;
    @BindView(R.id.showMessage)
    TextView showMessage;
    private CommonBaseAdapter<ModuleUpBindingItem> adapterTitle;
    private CommonBaseAdapter<ModuleUpBindingItem> adapter;
    private final List<ModuleUpBindingItem> dataList = new ArrayList<>();
    private final List<ModuleUpBindingItem> dataSource = new ArrayList<>();
    private int scan_position = -1;
    private String workItemID;
    private String side;

    private String lineName;
    private String materialBlockNumber;
    private String serialNo;
    private String argument;
    private static final String TAG = "ModuleUpBindingActivity";
    private LinearLayoutManager linearLayoutManager;
    private String quantaty;
    private CustomPopWindow mCustomPopWindow;
    private CommonBaseAdapter<UpLoadEntity.FeedingListBean> undoList_adapter;
    private List<UpLoadEntity.FeedingListBean> mFeedingListBean = new ArrayList<>();
    private List<UpLoadEntity.MaterialListBean> mMaterialListBean = new ArrayList<>();
    private CommonBaseAdapter<UpLoadEntity.MaterialListBean> unSend_adapter;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpBindingComponent.builder().appComponent(appComponent).moduleUpBindingModule(new ModuleUpBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        ckb_automaticUpload.setOnCheckedChangeListener(this);
        moduleUpAutomaticUpload = SpUtil.getStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload");
        Intent intent = ModuleUpBindingActivity.this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        lineName = intent.getStringExtra(Constant.LINE_NAME);
//        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
//        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);
        tv_workOrder.setText(getResources().getString(R.string.WorkID) + ":   " + workItemID);
        tv_line.setText(getResources().getString(R.string.Line) + ":   " + lineName);
        tv_side.setText(getResources().getString(R.string.Side) + ":   " + side);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);
        argument = GsonTools.createGsonListString(map);
    }

    @Override
    protected void initView() {
        if (moduleUpAutomaticUpload == null) {
            SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "false");
            moduleUpAutomaticUpload = "false";
            ckb_automaticUpload.setChecked(false);
        } else if ("false".equals(moduleUpAutomaticUpload)) {
            ckb_automaticUpload.setChecked(false);
        } else {
            ckb_automaticUpload.setChecked(true);
        }

        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("上模组");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        dataList.add(new ModuleUpBindingItem());
        adapterTitle = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
                return R.layout.item_module_up_binding;
            }
        };
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTitle.setAdapter(adapterTitle);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerViewContent.setLayoutManager(linearLayoutManager);
        adapter = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());

            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
                return R.layout.item_module_up_binding;
            }

        };

        recyclerViewContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_binding;
    }

    @Override
    public void onSuccess(List<ModuleUpBindingItem> data) {
        Log.i(TAG, "onSuccess:后台返回的数据长度是： " + data.size());
        state = 1;
        scan_position = -1;
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
        if (isAllItemIsBound(data)) {
            ToastUtils.showMessage(this, "", Toast.LENGTH_SHORT);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("所有料盘上模组已经完成")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
            dialog.show();
        }
    }

    private boolean isAllItemIsBound(List<ModuleUpBindingItem> data) {
        boolean isAllItemBound = false;
        int size = data.size();
        for (int i = 0; i < size; i++) {
            ModuleUpBindingItem item = data.get(i);
            if (TextUtils.isEmpty(item.getFeeder_id())) {
                isAllItemBound = false;
                break;
            }
            isAllItemBound = true;
        }
        return isAllItemBound;
    }

    @Override
    public void onFailed(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void onNetFailed(Throwable throwable) {
        ToastUtils.showMessage(this, throwable.getMessage());
    }

    @SuppressWarnings("all")
    @Override
    public void onSuccessBinding(List<ModuleUpBindingItem> dataSource) {
        dataSource.clear();
        List<ModuleUpBindingItem> rowsBeen = dataSource;
        dataSource.addAll(rowsBeen);
        scan_position = -1;
        adapter.notifyDataSetChanged();
        state = 1;
        if (isAllFeederBound()) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("工单" + workItemID + "上模组完成！")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ModuleUpBindingActivity.this.finish();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        }


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
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleUpBindingItems(argument);
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleUpBindingItems(argument);
            }
        });
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void getNeedUpLoadToMESMaterialsSuccess(UpLoadEntity mT) {

        mFeedingListBean.clear();
        mMaterialListBean.clear();
        mFeedingListBean.addAll(mT.getFeeding_list());
        mMaterialListBean.addAll(mT.getMaterial_list());
        undoList_adapter.notifyDataSetChanged();
        unSend_adapter.notifyDataSetChanged();
        if (mCustomPopWindow != null) {
            mCustomPopWindow.showAsDropDown(toolbar);
        }

    }

    @Override
    public void getNeedUpLoadTOMESMaterislsFailed(String mMsg) {

    }

    @OnClick({R.id.btn_upload, R.id.showMessage})
    public void onClick(View view) {
        Map<String, Object> map = new HashMap<>();
        switch (view.getId()) {
            case R.id.btn_upload:
                map.put("work_order", workItemID);
                map.put("side", side);
                map.put("is_feeder_buffer", 0);
                String argument = GsonTools.createGsonListString(map);
                //getPresenter().upLoadToMESManually(argument);
                if (mCustomPopWindow == null) {
                    createCustomPopWindow();

                }
                if (SingleClick.isSingle(1000)) {
                    getPresenter().getneeduploadtomesmaterials(argument);
                }

                break;
            case R.id.showMessage:
                showMessage.setVisibility(View.GONE);
                break;
        }
    }

    private void createCustomPopWindow() {
        mCustomPopWindow = CustomPopWindow.builder().with(this).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).setAnimationStyle(R.style.popupAnimalStyle).setView(R.layout.dialog_upload_mes).build();
        View mContentView = mCustomPopWindow.getContentView();
        RecyclerView rv_feeder = ViewUtils.findView(mContentView, R.id.rv_feeder);
        RecyclerView rv_feeder_send = ViewUtils.findView(mContentView, R.id.rv_feeder_send);
        Button bt_cancel = ViewUtils.findView(mContentView, R.id.bt_sheet_back);
        Button bt_confirm = ViewUtils.findView(mContentView, R.id.bt_sheet_confirm);
        Button bt_select_all = ViewUtils.findView(mContentView, R.id.bt_sheet_select_all);
//        ViewUtils.findView(mContentView, R.id.bt_sheet_select_cancel).setOnClickListener(this);
//        bt_cancel.setOnClickListener(this);
//        bt_confirm.setOnClickListener(this);
//        bt_select_all.setOnClickListener(this);

        undoList_adapter = new CommonBaseAdapter<UpLoadEntity.FeedingListBean>(getContext(), mFeedingListBean) {
            @Override
            protected void convert(CommonViewHolder holder, final UpLoadEntity.FeedingListBean item, int position) {
                holder.setText(R.id.tv_material_id, "料号：" + item.getMaterial_no());
                holder.setText(R.id.tv_slot, "FeederId：" + item.getFeeder_id());
                holder.setText(R.id.tv_amount, "流水号：" + String.valueOf(item.getSerial_no()));
                holder.setText(R.id.tv_issue, "架位：" + String.valueOf(item.getSlot()));
                final CheckBox mCheckBox = holder.getView(R.id.cb_debit);
                mCheckBox.setChecked(item.isChecked());
                holder.getView(R.id.al).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckBox.setChecked(!item.isChecked());
                        item.setChecked(!item.isChecked());
                    }
                });

            }

            @Override
            protected int getItemViewLayoutId(int position, UpLoadEntity.FeedingListBean item) {
                return R.layout.item_debit_list;

            }

        };
        unSend_adapter = new CommonBaseAdapter<UpLoadEntity.MaterialListBean>(getContext(), mMaterialListBean) {
            @Override
            protected void convert(CommonViewHolder holder, final UpLoadEntity.MaterialListBean item, int position) {
                holder.setText(R.id.tv_material_id, "料号：" + item.getMaterial_no());
                holder.setText(R.id.tv_slot, "流水号：" + String.valueOf(item.getSerial_no()));
                holder.setText(R.id.tv_issue, "架位：" + String.valueOf(item.getSlot()));
                final CheckBox mCheckBox = holder.getView(R.id.cb_debit);
                mCheckBox.setChecked(item.isChecked());
                holder.getView(R.id.al).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckBox.setChecked(!item.isChecked());
                        item.setChecked(!item.isChecked());
                    }
                });

            }

            @Override
            protected int getItemViewLayoutId(int position, UpLoadEntity.MaterialListBean item) {
                return R.layout.item_feeder_upload;
            }

        };
        setAdapter(rv_feeder, undoList_adapter);
        setAdapter(rv_feeder_send, unSend_adapter);
//        rv_debit.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setSmoothScrollbarEnabled(true);
//        rv_debit.setLayoutManager(linearLayoutManager);
//        rv_debit.setAdapter(undoList_adapter);
    }

    private void setAdapter(RecyclerView rv_debit, CommonBaseAdapter mUndoList_adapter) {
        rv_debit.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        rv_debit.setLayoutManager(linearLayoutManager);
        rv_debit.setAdapter(undoList_adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getAllModuleUpBindingItems(argument);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("all")
    @Override
    public void onScanSuccess(String barcode) {
        showMessage.setVisibility(View.GONE);
        Log.i(TAG, "onScanSuccess: " + barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            //扫描料盘
            case 1:
                try {
                    parseMaterial(barcode, barCodeParseIpml);

                } catch (EntityNotFountException e) {
                    VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                    showMessage.setText("请先扫描料盘码！");
                    showMessage.setVisibility(View.VISIBLE);
                    state = 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                    showMessage.setText("请先扫描料盘码！");
                    showMessage.setVisibility(View.VISIBLE);
                    state = 1;
                }
                break;
            //扫描Feeder架位
            case 2:
                try {
                    Feeder feederCode = (Feeder) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                    showMessage.setVisibility(View.GONE);
                    VibratorAndVoiceUtils.correctVibrator(ModuleUpBindingActivity.this);
                    VibratorAndVoiceUtils.correctVoice(ModuleUpBindingActivity.this);
                    JsonArray jsonArray = new JsonArray();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("work_order", workItemID);
                    jsonObject.addProperty("material_no", materialBlockNumber);
                    jsonObject.addProperty("feeder_id", barcode);
                    jsonObject.addProperty("serial_no", serialNo);
                    jsonObject.addProperty("side", side);
                    jsonObject.addProperty("qty", quantaty);
                    jsonObject.addProperty("code", ckb_automaticUpload.isChecked() ? "1" : "0");
                    jsonObject.addProperty("is_feeder_buffer", "0");

                    jsonArray.add(jsonObject);
                    String argument = jsonArray.toString();
                    Log.i(TAG, "argument==  " + argument);
                    getPresenter().getMaterialAndFeederBindingResult(argument);

                } catch (EntityNotFountException e) {

                    try {
                        parseMaterial(barcode, barCodeParseIpml);

                    } catch (EntityNotFountException ee) {
                        ee.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        showMessage.setText("此处不能识别此码！");
                        showMessage.setVisibility(View.VISIBLE);
                    } catch (ArrayIndexOutOfBoundsException ee) {
                        ee.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        showMessage.setText("此处不能识别此码！");
                        showMessage.setVisibility(View.VISIBLE);
                    }
                    e.printStackTrace();
                }
                break;
        }
    }

    private void parseMaterial(String barcode, BarCodeParseIpml barCodeParseIpml) throws EntityNotFountException {
        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
        Log.i(TAG, "onScanSuccess: " + barcode);
        showMessage.setVisibility(View.GONE);
        materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
        serialNo = materialBlockBarCode.getStreamNumber();
        quantaty = materialBlockBarCode.getCount();
        Log.i(TAG, "materialBlockNumber: " + materialBlockNumber);
        Log.i(TAG, "serialNo: " + serialNo
        );
        if (!isExistInDataSourceAndHighLight(materialBlockNumber, serialNo, dataSource)) {
            VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
            showMessage.setText("该料盘不属于此套工单，请确认工单及扫描是否正确！");
            showMessage.setVisibility(View.VISIBLE);
            state = 1;
        } else {
            if (isMaterialBound(materialBlockBarCode)) {
                VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                Toast.makeText(this, "注意:该料盘已经绑定", Toast.LENGTH_SHORT).show();
            } else {
                VibratorAndVoiceUtils.correctVibrator(ModuleUpBindingActivity.this);
                VibratorAndVoiceUtils.correctVoice(ModuleUpBindingActivity.this);
            }
            state = 2;
            Log.i(TAG, "onScanSuccess: " + "开始扫描Feeder");
        }
    }


    public void setItemHighLightBasedOnMID(int position) {
        scan_position = position;
        RecycleViewUtils.scrollToMiddle(linearLayoutManager, position, recyclerViewContent);
        adapter.notifyDataSetChanged();
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

    public boolean isExistInDataSourceAndHighLight(String item_m, String item_s, List<ModuleUpBindingItem> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMaterial_no().equals(item_m) && list.get(i).getSerial_no().equals(item_s)) {
                    setItemHighLightBasedOnMID(i);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    @SuppressWarnings("all")
    public boolean isAllFeederBound() {
        boolean res = true;
        if (dataSource.size() > 0) {
            for (ModuleUpBindingItem listItem : dataSource) {
                if (listItem.getFeeder_id() != null && listItem.getFeeder_id().length() > 0) {
                } else {
                    res = false;
                    break;
                }
            }
        } else {
            res = false;
        }
        return res;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == ckb_automaticUpload) {
            if (isChecked) {
                SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "true");
            } else {
                SpUtil.SetStringSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", "false");
            }
        }
    }

    public boolean isMaterialBound(MaterialBlockBarCode materialBlockBarCode) {
        boolean flag = false;
        for (ModuleUpBindingItem rowsBean : dataSource) {
            if (rowsBean.getMaterial_no().equalsIgnoreCase(materialBlockBarCode.getDeltaMaterialNumber()) && rowsBean.getSerial_no().equalsIgnoreCase(materialBlockBarCode.getStreamNumber())) {
                if (!TextUtils.isEmpty(rowsBean.getFeeder_id())) {
                    flag = true;
                    break;
                }

            }
        }
        return flag;
    }

}
