package com.delta.smt.ui.over_receive;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveWarning;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.over_receive.di.DaggerOverReceiveComponent;
import com.delta.smt.ui.over_receive.di.OverReceiveModule;
import com.delta.smt.ui.over_receive.mvp.OverReceiveContract;
import com.delta.smt.ui.over_receive.mvp.OverReceivePresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.smt.widget.DialogLayout;
import com.delta.smt.widget.WarningDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveActivity extends BaseActivity<OverReceivePresenter> implements OverReceiveContract.View, /*ItemOnclick, */WarningManger.OnWarning, CompoundButton.OnCheckedChangeListener {

    public String overReceiveAutomaticDebit = null;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.manual_debit)
    AppCompatButton manualDebit;
    @BindView(R.id.automatic_debit)
    AppCompatCheckBox automaticDebit;
    @Inject
    WarningManger warningManger;
    String materialBlockNumber = "4020108700";
    String serialNumber = "12344";
    String count = "2000";
    String dc = "";
    String po = "";
    String lc = "";
    String unit = "";
    String inv_no = "";
    String tc = "";
    String vendor = "";
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;

    /*@BindView(R.id.testSend)
    AppCompatButton testSend;*/
    //@BindView(R.id.testSendArrive)
    //AppCompatButton testSendArrive;
    Timer timer = new Timer();
    int recLen = 0;
    boolean isAllTimerEnd = true;
    @BindView(R.id.showMessage)
    TextView showMessage;
    //private Barcode barCodeIpml = BarcodeFactory.getJigcode(this);
    private Gson gson = new Gson();
    private CommonBaseAdapter<OverReceiveWarning.RowsBean> adapterTitle;
    private CommonBaseAdapter<OverReceiveWarning.RowsBean> adapter;
    private List<OverReceiveWarning.RowsBean> dataList = new ArrayList<>();
    private List<OverReceiveWarning.RowsBean> dataSource = new ArrayList<>();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    isAllTimerEnd = true;
                    for (int i = 0; i < dataSource.size(); i++) {
                        try {
                            int remainTime = Integer.parseInt(dataSource.get(i).getRemain_time());
                            if (remainTime > 0) {
                                isAllTimerEnd = false;
                                dataSource.get(i).setRemain_time((remainTime - 1) + "");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (isAllTimerEnd) {
                        timer.cancel();
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };
    private WarningDialog warningDialog;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerOverReceiveComponent.builder().appComponent(appComponent).overReceiveModule(new OverReceiveModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        automaticDebit.setOnCheckedChangeListener(this);
        overReceiveAutomaticDebit = SpUtil.getStringSF(OverReceiveActivity.this, "over_receive_automatic_debit");

        //接收那种预警
        warningManger.addWarning(Constant.EXCESS_ALARM_FLAG, getClass());
        //需要定制的信息
        warningManger.sendMessage(new SendMessage(Constant.EXCESS_ALARM_FLAG, 0));
        //是否接收预警 可以控制预警时机
        warningManger.setReceive(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);

        getPresenter().getAllOverReceiveItems();
        //barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        if (overReceiveAutomaticDebit == null) {
            SpUtil.SetStringSF(OverReceiveActivity.this, "over_receive_automatic_debit", "false");
            overReceiveAutomaticDebit = "false";
            automaticDebit.setChecked(false);
        } else if ("false".equals(overReceiveAutomaticDebit)) {
            automaticDebit.setChecked(false);
        } else {
            automaticDebit.setChecked(true);
        }
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("仓库房超领");

        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        dataList.add(new OverReceiveWarning.RowsBean("工单", "线别", "模组料站", "料号", "需求量", "已发数量", "状态", "剩余料使用时间", "架位"));
        adapterTitle = new CommonBaseAdapter<OverReceiveWarning.RowsBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, OverReceiveWarning.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_lineID, item.getLine_name());
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_shelfPositionID, item.getShelf_no());
                holder.setText(R.id.tv_demandAmount, item.getAmount());
                holder.setText(R.id.tv_materialRemainingUsageTime, item.getRemain_time());
                holder.setText(R.id.tv_state, item.getStatus());

            }

            @Override
            protected int getItemViewLayoutId(int position, OverReceiveWarning.RowsBean item) {
                return R.layout.item_over_receive_list;
            }
        };

        recyTitle.setAdapter(adapterTitle);

        adapter = new CommonBaseAdapter<OverReceiveWarning.RowsBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, OverReceiveWarning.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_lineID, item.getLine_name());
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_shelfPositionID, item.getShelf_no());
                holder.setText(R.id.tv_demandAmount, item.getAmount());

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                try {
                    long beginDate = Long.parseLong(item.getRemain_time()) * 1000;
                    String sd = sdf.format(new Date(beginDate));
                    holder.setText(R.id.tv_materialRemainingUsageTime, sd);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if ("4".equals(item.getStatus())) {
                    holder.setText(R.id.tv_state, "等待超领发料");
                } else if ("5".equals(item.getStatus())) {
                    holder.setText(R.id.tv_state, "正在超领发料");
                } else if ("6".equals(item.getStatus())) {
                    holder.setText(R.id.tv_state, "等待送到产线");
                } else {
                    holder.setText(R.id.tv_state, item.getStatus());
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, OverReceiveWarning.RowsBean item) {
                return R.layout.item_over_receive_list;
            }

        };

        recyContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_over_receive;
    }

    @Override
    public void onSuccess(OverReceiveWarning data) {
        ToastUtils.showMessage(this, data.getMessage());
        dataSource.clear();
        List<OverReceiveWarning.RowsBean> rowsBeanList = data.getRows();
        dataSource.addAll(rowsBeanList);
        adapter.notifyDataSetChanged();
        timer.schedule(task, 1000, 1000);

    }

    @Override
    public void onFalied(OverReceiveWarning data) {
        ToastUtils.showMessage(this, data.getMessage());
    }

    @Override
    public void onSuccessOverReceiveDebit(OverReceiveDebitResult data) {
        ToastUtils.showMessage(this, data.getMessage());
    }

    @Override
    public void onFaliedOverReceiveDebit(OverReceiveDebitResult data) {
        ToastUtils.showMessage(this, data.getMessage());
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
                getPresenter().getAllOverReceiveItems();
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllOverReceiveItems();
            }
        });
    }

    /*@Override
    public void onItemClick(View item, int position) {

    }*/

    @OnClick({R.id.manual_debit, R.id.showMessage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.manual_debit:
                getPresenter().manualDebit();
                break;
            case R.id.showMessage:
                showMessage.setVisibility(View.GONE);
                break;
        }
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

    @Override
    public void warningComing(String message) {
        //showDialog(warningMessage);
        Log.e(TAG, "warningComing: " + message);

        if (warningDialog == null) {
            warningDialog = createDialog(message);
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage(message);
    }


    public WarningDialog createDialog(String message) {
        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
                getPresenter().getAllOverReceiveItems();
                warningDialog.dismiss();
            }
        });
        warningDialog.show();

        return warningDialog;
    }

    /**
     * type == 9  代表你要发送的是哪个
     *
     * @param message
     */
    private void updateMessage(String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("仓库房超领");
        String content = "";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                //可能有多种预警的情况
                Object message1 = jsonObject.get("message");
                content = content + message1 + "\n";
            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        /*if (barCodeIpml.isEventFromBarCode(event)) {
            barCodeIpml.analysisKeyEvent(event);
            return true;
        }*/
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        warningManger.registerWReceiver(this);
        super.onResume();
        /*try {
            barCodeIpml.hasConnectBarcode();
        } catch (DevicePairedNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void showDialog(String message) {
        //1.创建这个DialogRelativelayout
        DialogLayout dialogLayout = new DialogLayout(this);
        //2.传入的是红色字体的标题
        dialogLayout.setStrTitle("预警信息");
        //3.传入的是黑色字体的二级标题
        dialogLayout.setStrSecondTitle("请进行捡料");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add(message);
        dialogLayout.setStrContent(titleList);
        //5.构建Dialog，setView的时候把这个View set进去。
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(dialogLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //getPresenter().getAllModuleUpWarningItems();
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        warningManger.unregisterWReceiver(this);
        warningManger.sendMessage(new SendMessage(Constant.EXCESS_ALARM_FLAG, 1));
        //barCodeIpml.onComplete();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public void onScanSuccess(String barcode) {

        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
            MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
            materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
            serialNumber = materialBlockBarCode.getStreamNumber();
            count = materialBlockBarCode.getCount();
            //OverReceiveMaterialSend overReceiveMaterialSend = new OverReceiveMaterialSend(materialBlockNumber, serialNumber, count);
            dc = materialBlockBarCode.getDC();
            po = materialBlockBarCode.getPO();
            unit = materialBlockBarCode.getUnit();
            vendor = materialBlockBarCode.getVendor();
            tc = materialBlockBarCode.getBusinessCode();
            inv_no = materialBlockBarCode.getInvNo();
            //String str = gson.toJson(overReceiveMaterialSend);
            //Toast.makeText(this,str,Toast.LENGTH_SHORT).show();

            boolean isExistInList = false;
            int position_scan = -1;
            for (int i = 0; i < dataSource.size(); i++) {
                if (dataSource.get(i).getMaterial_no().equals(materialBlockNumber)) {
                    isExistInList = true;
                    position_scan = i;
                    break;
                }
            }

            if (isExistInList) {
                if (position_scan >= 0) {
                    showMessage.setVisibility(View.GONE);
                    VibratorAndVoiceUtils.correctVibrator(OverReceiveActivity.this);
                    VibratorAndVoiceUtils.correctVoice(OverReceiveActivity.this);
                    String work_order_id = dataSource.get(position_scan).getId();
                    String slot = dataSource.get(position_scan).getSlot();

                    Map<String, String> map = new HashMap<>();
                    map.put("material_no", materialBlockNumber);
                    map.put("serial_no", serialNumber);
                    map.put("work_order_id", work_order_id);
                    map.put("dc", dc);
                    map.put("lc", "");
                    map.put("po", po);
                    map.put("qty", count);
                    map.put("unit", unit);
                    map.put("vendor", vendor);
                    map.put("tc", tc);
                    map.put("inv_no", inv_no);
                    map.put("slot", slot);
                    Gson gson = new Gson();
                    String argument = gson.toJson(map);
                    getPresenter().getOverReceiveItemsAfterSend("[" + argument + "]");
                }
            } else {
                VibratorAndVoiceUtils.wrongVibrator(OverReceiveActivity.this);
                VibratorAndVoiceUtils.wrongVoice(OverReceiveActivity.this);
                Toast.makeText(this, "列表中不存在此料盘码", Toast.LENGTH_SHORT).show();
            }

        } catch (EntityNotFountException e) {
            VibratorAndVoiceUtils.wrongVibrator(OverReceiveActivity.this);
            VibratorAndVoiceUtils.wrongVoice(OverReceiveActivity.this);
            //Toast.makeText(this, "此处不支持此类型码!", Toast.LENGTH_SHORT).show();
            showMessage.setText("此处不支持此类型码!");
            showMessage.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == automaticDebit) {
            if (isChecked) {
                SpUtil.SetStringSF(OverReceiveActivity.this, "over_receive_automatic_debit", "true");
            } else {
                SpUtil.SetStringSF(OverReceiveActivity.this, "over_receive_automatic_debit", "false");
            }
        }
    }


}
