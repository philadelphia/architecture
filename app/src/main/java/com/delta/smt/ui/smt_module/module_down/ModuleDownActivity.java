package com.delta.smt.ui.smt_module.module_down;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.smt_module.module_down.di.DaggerModuleDownComponent;
import com.delta.smt.ui.smt_module.module_down.di.ModuleDownModule;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownContract;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownPresenter;
import com.delta.smt.ui.smt_module.virtual_line_binding.VirtualLineBindingActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownActivity extends BaseActivity<ModuleDownPresenter> implements ModuleDownContract.View,WarningManger.OnWarning,com.delta.libs.adapter.ItemOnclick<ModuleDownWarningItem.RowsBean>{

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.module_up_warning)
    LinearLayout moduleUpWarning;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    @Inject
    WarningManger warningManger;
    String workOrderID = "";
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private List<ModuleDownWarningItem.RowsBean> dataList = new ArrayList<>();
    private ItemCountViewAdapter<ModuleDownWarningItem.RowsBean> myAdapter;

    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time);
        return t;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownComponent.builder().appComponent(appComponent).moduleDownModule(new ModuleDownModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.MODULE_DOWN_WARNING, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        getPresenter().getAllModuleDownWarningItems();
        /*statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleDownWarningItems();
            }
        });

        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleDownWarningItems();
            }
        });*/



    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("下模组");

        myAdapter = new ItemCountViewAdapter<ModuleDownWarningItem.RowsBean>(this, dataList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_module_down_warning_list;
            }

            @Override
            protected void convert(com.delta.libs.adapter.ItemTimeViewHolder holder, ModuleDownWarningItem.RowsBean moduleUpWarningItem, int position) {

                holder.setText(R.id.tv_lineID, "线别: " + moduleUpWarningItem.getLine_name());
                holder.setText(R.id.tv_workID, "工单号: " + moduleUpWarningItem.getWork_order());
                holder.setText(R.id.tv_faceID, "面别: " + moduleUpWarningItem.getSide());
                holder.setText(R.id.tv_product_name_main,"主板: "+moduleUpWarningItem.getProduct_name_main());
                holder.setText(R.id.tv_product_name,"小板: "+moduleUpWarningItem.getProduct_name());
                //holder.setText(R.id.tv_status,"状态: "+moduleUpWarningItem.getStatus());
                holder.setText(R.id.tv_status, "状态: " + "等待下模组");
            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myAdapter);
        myAdapter.setOnItemTimeOnclick(this);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_down_warning;
    }

    @Override
    public void onSuccess(ModuleDownWarningItem data) {
            dataList.clear();
            List<ModuleDownWarningItem.RowsBean> rowsList = data.getRows();
            for (int i = 0; i < rowsList.size(); i++) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date parse = format.parse(rowsList.get(i).getUnplug_mod_actual_finish_time());
                    rowsList.get(i).setCreat_time(parse.getTime());
                    rowsList.get(i).setEntityId(i);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dataList.addAll(rowsList);
            myAdapter.notifyDataSetChanged();
        ToastUtils.showMessage(this, data.getMsg());

    }

    @Override
    public void onFalied(ModuleDownWarningItem data) {
        ToastUtils.showMessage(this, data.getMsg());
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
                getPresenter().getAllModuleDownWarningItems();
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleDownWarningItems();
            }
        });
    }

    @Override
    protected void onStop() {
        warningManger.unregisterWReceriver(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != myAdapter) {
            myAdapter.startRefreshTime();
        }
        warningManger.registerWReceiver(this);

    }

    //预警
    @Override
    public void warningComing(String warningMessage) {
        showDialog(warningMessage);
    }

    public void showDialog(String message){
        //1.创建这个DialogRelativelayout
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("下模组提醒");
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
                getPresenter().getAllModuleDownWarningItems();
            }
        }).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != myAdapter) {
            myAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != myAdapter) {
            myAdapter.cancelRefreshTime();
        }
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

    public String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onItemClick(View item, ModuleDownWarningItem.RowsBean rowsBean, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID,dataList.get(position).getWork_order());
        bundle.putString(Constant.PRODUCT_NAME_MAIN,dataList.get(position).getProduct_name_main());
        bundle.putString(Constant.PRODUCT_NAME,dataList.get(position).getProduct_name());
        bundle.putString(Constant.SIDE,dataList.get(position).getSide());
        bundle.putString(Constant.LINE_NAME,dataList.get(position).getLine_name());

        //Intent intent = new Intent(this, ModuleUpBindingActivity.class);
        //intent.putExtras(bundle);
        //this.startActivity(intent);
        //startActivityForResult(intent, Constant.ACTIVITY_REQUEST_WORK_ITEM_ID);
        IntentUtils.showIntent(this, VirtualLineBindingActivity.class,bundle);
    }

}
