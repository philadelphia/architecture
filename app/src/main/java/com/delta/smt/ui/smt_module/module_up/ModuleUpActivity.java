package com.delta.smt.ui.smt_module.module_up;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.smt_module.module_up.di.DaggerModuleUpComponent;
import com.delta.smt.ui.smt_module.module_up.di.ModuleUpModule;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpPresenter;
import com.delta.smt.ui.smt_module.module_up_binding.ModuleUpBindingActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpActivity extends BaseActiviy<ModuleUpPresenter> implements ModuleUpContract.View, CommonBaseAdapter.OnItemClickListener<ModuleUpWarningItem> , WarningManger.OnWarning{

    @BindView(R.id.header_back)
    RelativeLayout headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.module_up_warning)
    LinearLayout moduleUpWarning;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    private List<ModuleUpWarningItem> dataList = new ArrayList<>();
    private CommonBaseAdapter<ModuleUpWarningItem> adapter;
    //private ItemCountdownViewAdapter <ModuleUpWarningItem> myAdapter;

    @Inject
    WarningManger warningManger;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpComponent.builder().appComponent(appComponent).moduleUpModule(new ModuleUpModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.SAMPLEWARING, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        //getPresenter().getAllModuleUpWarningItems();

    }

    @Override
    protected void initView() {
        headerTitle.setText("上模组");
        /*mMyAdapter = new ItemCountdownViewAdapter<FalutMesage>(this, datas) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_processing;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, FalutMesage falutMesage, int position) {

                holder.setText(R.id.tv_line, "产线："+falutMesage.getProduceline());
                holder.setText(R.id.tv_name, falutMesage.getProcessing() + "-" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_processing, "制程：" + falutMesage.getProcessing());
                holder.setText(R.id.tv_faultMessage, "故障信息：" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_code, "故障代码：" + falutMesage.getFaultCode());

            }
        };
        rvFaultProcessing.setLayoutManager(new LinearLayoutManager(this));
        rvFaultProcessing.setAdapter(mMyAdapter);*/
        adapter = new CommonBaseAdapter<ModuleUpWarningItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpWarningItem item, int position) {
                holder.setText(R.id.tv_title, "线别: " + item.getLineNumber());
                holder.setText(R.id.tv_line, "工单号: " + item.getWorkItemID());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFaceID());
                holder.setText(R.id.tv_add_count, "状态: " + item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpWarningItem item) {
                return R.layout.item_module_down_warning_list;
            }
        };

        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_warning;
    }

    @Override
    public void onSuccess(List<ModuleUpWarningItem> data) {
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"onSuccess",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFalied() {

    }

    @OnClick({R.id.header_back, R.id.header_title, R.id.header_setting/*, R.id.tl_title*/})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                onBackPressed();
                break;
            case R.id.header_title:
                break;
            case R.id.header_setting:
                break;
        }
    }

    @Override
    public void onItemClick(View view, ModuleUpWarningItem item, int position) {
        IntentUtils.showIntent(this, ModuleUpBindingActivity.class);
    }


    @Override
    protected void onStop() {
        warningManger.unregistWReceriver(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        warningManger.registWReceiver(this);
        super.onResume();
    }

    //预警
    @Override
    public void warningComming(String warningMessage) {
        showDialog(warningMessage);
    }

    public void showDialog(String message){
        //1.创建这个DialogRelativelayout
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("新工单");
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
                getPresenter().getAllModuleUpWarningItems();
            }
        }).show();
    }
}
