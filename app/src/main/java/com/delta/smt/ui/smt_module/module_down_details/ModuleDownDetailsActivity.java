package com.delta.smt.ui.smt_module.module_down_details;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FeederBuffer;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.RecycleViewUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownDebit;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.ui.smt_module.module_down_details.di.DaggerModuleDownDetailsComponent;
import com.delta.smt.ui.smt_module.module_down_details.di.ModuleDownDetailsModule;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsContract;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.commonlibs.utils.ViewUtils;
import com.delta.commonlibs.widget.CustomPopWindow;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shu feng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsActivity extends BaseActivity<ModuleDownDetailsPresenter> implements ModuleDownDetailsContract.View {

    private static final String TAG = "ModuleDownDetailsActivi";
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.btn_feederMaintain)
    AppCompatButton btnFeederMaintain;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.btn_debitManually)
    Button btn_debitManually;
    @BindView(R.id.tv_work_order)
    TextView tv_workOrder;
    @BindView(R.id.tv_line_name)
    TextView tv_side;
    @BindView(R.id.tv_line_num)
    TextView tv_line;

    String workItemID;
    String side;
    String productNameMain;
    String productName;
    String lineName;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private CommonBaseAdapter<ModuleDownDebit> unDebitadapter;
    private CommonBaseAdapter<ModuleDownDetailsItem> adapter;
    private List<ModuleDownDetailsItem> dataList = new ArrayList<>();
    private List<ModuleDownDetailsItem> dataSource = new ArrayList<>();
    private List<ModuleDownDetailsItem> dataSourceForCheckIn = new ArrayList<>();
    private String mCurrentWorkOrder;
    private String mCurrentMaterialID;
    private String mCurrentSerialNumber;
    private String mCurrentQuantity;

    private String mCurrentSlot;
    private int index = -1;

    private int flag = 1;
    private String argument;
    private LinearLayoutManager linearLayoutManager;
    private CustomPopWindow popUpWindow;
    private List<ModuleDownDebit> unDebitItemList = new ArrayList<>();

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownDetailsComponent.builder().appComponent(appComponent).moduleDownDetailsModule(new ModuleDownDetailsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Intent intent = this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        lineName = intent.getStringExtra(Constant.LINE_NAME);
        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);
        tv_workOrder.setText(getResources().getString(R.string.WorkID) + ":   " + workItemID);
        tv_line.setText(getResources().getString(R.string.Line) + ":   " + lineName);
        tv_side.setText(getResources().getString(R.string.Side) + ":   " + side);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);
        argument = GsonTools.createGsonListString(map);
        mCurrentWorkOrder = workItemID;
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        toolbarTitle.setText("下模组");

        dataList.add(new ModuleDownDetailsItem("料号", "流水码", "Feeder ID", "模组料站", "归属"));
        CommonBaseAdapter<ModuleDownDetailsItem> adapterTitle = new CommonBaseAdapter<ModuleDownDetailsItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem item, int position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem item) {
                return R.layout.item_module_down_details;
            }
        };
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTitle.setAdapter(adapterTitle);

        adapter = new CommonBaseAdapter<ModuleDownDetailsItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_serialID, item.getSerial_no());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());
                if ("0".equals(item.getDest())) {
                    holder.setText(R.id.tv_ownership, "尾数仓");
                } else if ("1".equals(item.getDest())) {
                    holder.setText(R.id.tv_ownership, "Feeder缓存区");
                } else if ("2".equals(item.getDest())) {
                    holder.setText(R.id.tv_ownership, "Feeder维护区");
                } else {
                    holder.setText(R.id.tv_ownership, item.getDest());
                }


                if (item.getMaterial_no().equalsIgnoreCase(mCurrentMaterialID) && item.getSerial_no().equalsIgnoreCase(mCurrentSerialNumber)) {
                    Log.i(TAG, "convert: " + item.toString());
                    Log.i(TAG, "position: " + position);

                    holder.itemView.setBackgroundColor(Color.YELLOW);
                    mCurrentSlot = item.getSlot();

                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem item) {
                return R.layout.item_module_down_details;
            }

        };

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerViewContent.setLayoutManager(linearLayoutManager);
        recyclerViewContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_down_details;
    }

    @Override
    public void onSuccess(List<ModuleDownDetailsItem> data) {
        dataSource.clear();
        dataSourceForCheckIn.clear();

        flag = 1;
        Log.i(TAG, "index: == " + index);
        dataSource.addAll(data);

        for (ModuleDownDetailsItem bean : dataSource) {
            if (bean.getDest().equalsIgnoreCase("1"))
                dataSourceForCheckIn.add(bean);
        }
        Log.i(TAG, "onSuccess: 后台返回的数据长度是" + dataSource.size());
        Log.i(TAG, "onSuccess: 后台返回的待入库数据长度是" + dataSourceForCheckIn.size());
        adapter.notifyDataSetChanged();
        if (dataSourceForCheckIn.isEmpty()) {
            btnFeederMaintain.setEnabled(true);
        }

    }

    @Override
    public void showModuleDownUnDebitedItemList(List<ModuleDownDebit> data) {
        Log.i(TAG, "扣账列表数据长度: " + data.size());
        if (0 == data.size()) {
            ToastUtils.showMessage(this, "没有未扣账列表");
            return;
        }

        unDebitItemList.clear();
        unDebitItemList.addAll(data);
        unDebitadapter.notifyDataSetChanged();

        popUpWindow.showAsDropDown(toolbar);
    }

    private void createPopupWindow() {
        popUpWindow = CustomPopWindow.builder().with(this).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.popupAnimalStyle)
                .setView(R.layout.dialog_bottom_sheet)
//                .enableBlur(true)
                .build();
        View contentView = popUpWindow.getContentView();
        RecyclerView recyclerView = ViewUtils.findView(contentView, R.id.rv_sheet);
        Button btn_cancel = ViewUtils.findView(contentView, R.id.bt_sheet_select_cancel);
        Button btn_confirm = ViewUtils.findView(contentView, R.id.bt_sheet_confirm);
        Button btn_selectAll = ViewUtils.findView(contentView, R.id.bt_sheet_select_all);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow.dissmiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                Map<String, String> mapItem = new HashMap<>();
                map.put("work_order", workItemID);
                map.put("side", side);
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();

                for (ModuleDownDebit debitData : unDebitItemList) {
                    if (debitData.isChecked())
                    mapItem.put("material_no", debitData.getMaterial_no());
                    mapItem.put("serial_no" , String.valueOf(debitData.getSerial_no()));
                    list.add(mapItem);
                }
                map.put("list", list);
                map.put("part", "FeederBuffer");
                String argument = GsonTools.createGsonListString(map);
                Log.i(TAG, "手动扣账参数为:  " + argument);
                getPresenter().debitManually(argument);
            }
        });

        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ModuleDownDebit debitData : unDebitItemList) {
                    debitData.setChecked(true);
                }
            }
        });
        unDebitadapter = new CommonBaseAdapter<ModuleDownDebit>(this, unDebitItemList) {
            @Override
            protected void convert(final CommonViewHolder holder, final ModuleDownDebit item, int position) {
                holder.setText(R.id.tv_material_id, "料号 :\t" + item.getMaterial_no());
                holder.setText(R.id.tv_serial_num, "流水号 :\t" + item.getSerial_no());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = holder.getView(R.id.cb_debit);
                        checkBox.setChecked(!item.isChecked());
                        item.setChecked(!item.isChecked());
                    }
                });

            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDebit item) {
                return R.layout.module_down_item_undebit_list;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(unDebitadapter);
    }


    @Override
    public void onFailed(String message) {
        flag = 2;
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onResult(String message) {
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onMaintainResult(String message) {
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
        statusLayout.showEmptyView();
    }


    @Override
    public void onNetFailed(Throwable throwable) {
        ToastUtils.showMessage(this, throwable.getMessage());
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
                getPresenter().getAllModuleDownDetailsItems(argument);
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleDownDetailsItems(argument);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getAllModuleDownDetailsItems(argument);
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

    @OnClick({R.id.btn_feederMaintain, R.id.btn_debitManually})
    public void onClick(View view) {
        Map<String, String> map = new HashMap<>();
        map.put("work_order", mCurrentWorkOrder);
        map.put("side", side);
        String argument = GsonTools.createGsonListString(map);
        switch (view.getId()) {
            case R.id.btn_feederMaintain:
                getPresenter().getAllModuleDownMaintainResult(argument);
                break;
            case R.id.btn_debitManually:
                getPresenter().getModuleListUnDebitList(argument);
                if (popUpWindow == null) {
                    createPopupWindow();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.i(TAG, "onScanSuccess: ");
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (flag) {
            case 1:
                try {
                    parseMaterial(barcode, barCodeParseIpml);
                } catch (EntityNotFountException e) {
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    Toast.makeText(this, "请扫描料盘", Toast.LENGTH_SHORT).show();
                    flag = 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    flag = 1;
                    Toast.makeText(this, "解析错误,请重新扫描料盘", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                try {
                    FeederBuffer frameLocation = (FeederBuffer) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER_BUFFER);
                    String mCurrentLocation = frameLocation.getSource();
                    Log.i(TAG, "mCurrentLocation: " + frameLocation.toString());
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    Map<String, String> map = new HashMap<>();
                    map.put("work_order", mCurrentWorkOrder);
                    map.put("side", side);
                    map.put("material_no", mCurrentMaterialID);
                    map.put("serial_no", mCurrentSerialNumber);
                    map.put("shelf_no", mCurrentLocation);
                    map.put("qty", mCurrentQuantity);
                    map.put("slot", mCurrentSlot);
                    map.put("code", checkBox.isChecked() ? "1" : "0");
                    String argument = GsonTools.createGsonListString(map);
                    Log.i(TAG, "argument== " + argument);
                    Log.i(TAG, "料架已经扫描完成，接下来入库: ");

                    getPresenter().getFeederCheckInTime(argument);


                } catch (EntityNotFountException e1) {
                    try {
                        parseMaterial(barcode, barCodeParseIpml);
                    } catch (EntityNotFountException e) {
                        e.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
                        Toast.makeText(this, "请扫描架位", Toast.LENGTH_SHORT).show();
                        flag = 2;

                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
                        Toast.makeText(this, "解析错误,请重新扫描架位", Toast.LENGTH_SHORT).show();
                        flag = 2;
                    }

                }
                break;
            default:
                break;
        }
    }

    private void parseMaterial(String barcode, BarCodeParseIpml barCodeParseIpml) throws EntityNotFountException {
        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
        mCurrentMaterialID = materialBlockBarCode.getDeltaMaterialNumber();
        mCurrentSerialNumber = materialBlockBarCode.getStreamNumber();
        mCurrentQuantity = materialBlockBarCode.getCount();
        getMatchedMaterialIndex(materialBlockBarCode);
        adapter.notifyDataSetChanged();
        RecycleViewUtils.scrollToMiddle(linearLayoutManager, index, recyclerViewContent);

        Log.i(TAG, "mCurrentMaterialID: " + mCurrentMaterialID);
        Log.i(TAG, "mCurrentSerialNumber: " + mCurrentSerialNumber);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", mCurrentWorkOrder);
        map.put("material_no", materialBlockBarCode.getDeltaMaterialNumber());
        map.put("serial_no", materialBlockBarCode.getStreamNumber());
        map.put("side", side);
        map.put("qty", mCurrentQuantity);
        map.put("slot", mCurrentSlot);
        Gson gson = new Gson();
        String argument = gson.toJson(map);
        Log.i(TAG, "argument== " + argument);
        Log.i(TAG, "料盘已经扫描完成，接下来扫描料架: ");
        if (isMaterialExists(materialBlockBarCode)) {
            if (!dataSourceForCheckIn.isEmpty()) {
                if (isMaterialInFeederCheckInList(materialBlockBarCode)) {
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                } else {
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    ToastUtils.showMessage(this, "请先扫描待入库的料盘");
                }
            }
            flag = 2;


        } else {
            flag = 1;
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            ToastUtils.showMessage(this, "该料盘不存在此工单，请重新扫描料盘或检查料盘二维码是否损坏");
        }
    }

    public int getMatchedMaterialIndex(MaterialBlockBarCode material) {
        int length = dataSource.size();

        for (int i = 0; i < length; i++) {
            ModuleDownDetailsItem rowsBean = dataSource.get(i);
            if (rowsBean.getMaterial_no().equalsIgnoreCase(material.getDeltaMaterialNumber()) && rowsBean.getSerial_no().equalsIgnoreCase(material.getStreamNumber())) {
                index = i;
                break;
            }
        }
        Log.i(TAG, "getMatchedMaterialIndex: " + index);

        return index;
    }

    public boolean isMaterialExists(MaterialBlockBarCode material) {
        boolean flag = false;
        for (int i = 0; i < dataSource.size(); i++) {
            ModuleDownDetailsItem item = dataSource.get(i);
            if (material.getDeltaMaterialNumber().equalsIgnoreCase(item.getMaterial_no()) && material.getStreamNumber().equalsIgnoreCase(item.getSerial_no())) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }

        return flag;
    }

    public boolean isMaterialInFeederCheckInList(MaterialBlockBarCode material) {
        boolean flag = false;
        for (int i = 0; i < dataSourceForCheckIn.size(); i++) {
            ModuleDownDetailsItem item = dataSourceForCheckIn.get(i);
            if (material.getDeltaMaterialNumber().equalsIgnoreCase(item.getMaterial_no()) && material.getStreamNumber().equalsIgnoreCase(item.getSerial_no())) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }

        return flag;
    }


}