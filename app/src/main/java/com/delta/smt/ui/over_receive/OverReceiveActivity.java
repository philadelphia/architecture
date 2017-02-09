package com.delta.smt.ui.over_receive;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.OverReceiveDebitResult;
import com.delta.smt.entity.OverReceiveMaterialSend;
import com.delta.smt.entity.OverReceiveMaterialSendArrive;
import com.delta.smt.entity.OverReceiveWarning;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.over_receive.di.DaggerOverReceiveComponent;
import com.delta.smt.ui.over_receive.di.OverReceiveModule;
import com.delta.smt.ui.over_receive.mvp.OverReceiveContract;
import com.delta.smt.ui.over_receive.mvp.OverReceivePresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveActivity extends BaseActivity<OverReceivePresenter> implements OverReceiveContract.View, /*ItemOnclick, */WarningManger.OnWarning, BarCodeIpml.OnScanSuccessListener{

    private BarCodeIpml barCodeIpml=new BarCodeIpml();
    private Gson gson = new Gson();

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

    private CommonBaseAdapter<OverReceiveWarning.RowsBean.DataBean> adapterTitle;
    private CommonBaseAdapter<OverReceiveWarning.RowsBean.DataBean> adapter;
    private List<OverReceiveWarning.RowsBean.DataBean> dataList = new ArrayList<>();
    private List<OverReceiveWarning.RowsBean.DataBean> dataSource = new ArrayList<>();

    /*@BindView(R.id.testSend)
    AppCompatButton testSend;*/
    //@BindView(R.id.testSendArrive)
    //AppCompatButton testSendArrive;

    @Inject
    WarningManger warningManger;

    String materialBlockNumber = "4020108700";
    String serialNumber = "12344";
    String count = "2000";

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerOverReceiveComponent.builder().appComponent(appComponent).overReceiveModule(new OverReceiveModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.EXCESS_ALARM_FLAG, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        getPresenter().getAllOverReceiveItems();
        barCodeIpml.setOnGunKeyPressListener(this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("仓库房超领");

        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        dataList.add(new OverReceiveWarning.RowsBean.DataBean("线别", "料号", "架位", "需求量", "剩余料使用时间","状态"));
        adapterTitle = new CommonBaseAdapter<OverReceiveWarning.RowsBean.DataBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, OverReceiveWarning.RowsBean.DataBean item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_lineID,item.getLine());
                holder.setText(R.id.tv_materialID,item.getMaterial_num());
                holder.setText(R.id.tv_shelfPositionID,item.getShevles());
                holder.setText(R.id.tv_demandAmount,item.getRe_quantity());
                holder.setText(R.id.tv_materialRemainingUsageTime,item.getRest_time());
                holder.setText(R.id.tv_state,item.getStatus());

            }

            @Override
            protected int getItemViewLayoutId(int position, OverReceiveWarning.RowsBean.DataBean item) {
                return R.layout.item_over_receive_list;
            }
        };

        recyTitle.setAdapter(adapterTitle);

        adapter = new CommonBaseAdapter<OverReceiveWarning.RowsBean.DataBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, OverReceiveWarning.RowsBean.DataBean item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_lineID,item.getLine());
                holder.setText(R.id.tv_materialID,item.getMaterial_num());
                holder.setText(R.id.tv_shelfPositionID,item.getShevles());
                holder.setText(R.id.tv_demandAmount,item.getRe_quantity());
                holder.setText(R.id.tv_materialRemainingUsageTime,item.getRest_time());
                holder.setText(R.id.tv_state,item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, OverReceiveWarning.RowsBean.DataBean item) {
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
        Toast.makeText(this,"onSuccess",Toast.LENGTH_SHORT).show();
        if(data.getMsg().toLowerCase().equals("success")){
            dataSource.clear();
            List<OverReceiveWarning.RowsBean.DataBean> rowsBeanList = data.getRows().getData();
            dataSource.addAll(rowsBeanList);
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this,data.getMsg(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFalied() {
        Toast.makeText(this,"onFalied",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessOverReceiveDebit(OverReceiveDebitResult data) {
        if (data.getMsg().toLowerCase().equals("success")){
            //
            //Toast.makeText(this,data.getMsg(),Toast.LENGTH_SHORT).show();
        }else{
            //
            //Toast.makeText(this,data.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFaliedOverReceiveDebit() {

    }

    @OnClick({R.id.manual_debit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.manual_debit:
                getPresenter().manualDebit();
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

    /*@Override
    public void onItemClick(View item, int position) {

    }*/

    @Override
    public void warningComing(String warningMessage) {
        showDialog(warningMessage);
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
    protected void onResume() {
        warningManger.registerWReceiver(this);
        super.onResume();
        try {
            barCodeIpml.hasConnectBarcode();
        } catch (DevicePairedNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(String message){
        //1.创建这个DialogRelativelayout
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("预警信息");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("请进行捡料");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add(message);
        dialogRelativelayout.setStrContent(titleList);
        //5.构建Dialog，setView的时候把这个View set进去。
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(dialogRelativelayout)
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
        barCodeIpml.onComplete();
    }

    @Override
    protected void onStop() {
        warningManger.unregisterWReceriver(this);
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
            OverReceiveMaterialSend overReceiveMaterialSend = new OverReceiveMaterialSend(materialBlockNumber,serialNumber,count);
            String str = gson.toJson(overReceiveMaterialSend);
            //Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
            getPresenter().getOverReceiveItemsAfterSend(str);
        } catch (EntityNotFountException e) {
            Toast.makeText(this, "此处不支持此类型码!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
