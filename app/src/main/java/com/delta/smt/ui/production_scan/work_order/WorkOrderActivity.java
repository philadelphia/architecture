package com.delta.smt.ui.production_scan.work_order;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.production_scan.ItemWarningInfo;
import com.delta.smt.ui.production_scan.binding.BindingActivity;
import com.delta.smt.ui.production_scan.work_order.di.DaggerWorkOrderComponent;
import com.delta.smt.ui.production_scan.work_order.di.WorkOrderModule;
import com.delta.smt.ui.production_scan.work_order.mvp.WorkOrderContract;
import com.delta.smt.ui.production_scan.work_order.mvp.WorkOrderPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by Shufeng.Wu on 2017/1/3.
 */
public class WorkOrderActivity extends BaseActivity<WorkOrderPresenter> implements
        WorkOrderContract.View, CommonBaseAdapter.OnItemClickListener<ItemWarningInfo>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "WorkOrderActivity";
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ryv_produce_warning)
    RecyclerView mRyvProduceWarning;
    @BindView(R.id.statusLayout)
    StatusLayout mStatusLayout;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout mSrfRefresh;
    private CommonBaseAdapter<ItemWarningInfo> mAdapter;
    private List<ItemWarningInfo> datas = new ArrayList<>();
    private String lines;
    private String[] line;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerWorkOrderComponent.builder().appComponent(appComponent).workOrderModule(new WorkOrderModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        lines = SpUtil.getStringSF(this, Constant.PRODUCE_WARNING_LINE_NAME);
        line = lines.split(",");
        if (initLine() != null) {
            getPresenter().getWorkOrderItems(initLine());
        }
    }

    @Override
    protected void initView() {
        //headerTitle.setText("上模组");
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("产中扫描");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        Log.i(TAG, "initView: ");

        mAdapter = new CommonBaseAdapter<ItemWarningInfo>(this, datas) {

            @Override
            protected int getItemViewLayoutId(int position, ItemWarningInfo item) {
                return R.layout.item_pro_scan_work_order_list;
            }

            @Override
            protected void convert(CommonViewHolder holder, ItemWarningInfo itemWarningInfo, int position) {

                holder.setText(R.id.tv_line_name, "线别：" + itemWarningInfo.getProductionline());
                holder.setText(R.id.tv_workID, "工单号：" + itemWarningInfo.getWorkcode());
                holder.setText(R.id.tv_faceID, "面别：" + itemWarningInfo.getFace());
                holder.setText(R.id.tv_status, "状态：" + itemWarningInfo.getStatus());
            }


        };
        mRyvProduceWarning.setLayoutManager(new LinearLayoutManager(this));
        mRyvProduceWarning.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mSrfRefresh.setOnRefreshListener(this);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pro_scan_work_order_list;
    }


    @Override
    public void getItemWarningDatas(List<ItemWarningInfo> itemWarningInfo) {
        datas.clear();

        /*for (int i = 0; i < itemWarningInfo.size(); i++) {
*//*            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                Date parse = format.parse(itemWarningInfo.get(i).getTime());*//*
            //Log.e("aaa", "getItemWarningDatas: " + itemWarningInfo.get(i).getTime());
            //long time = System.currentTimeMillis();
            //itemWarningInfo.get(i).setEnd_time(time + Math.round(itemWarningInfo.get(i).getTime()) * 1000);
            itemWarningInfo.get(i).setEntityId(i);

        }*/


        datas.addAll(itemWarningInfo);

        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemWarningDatasFailed(String message) {
        if ("Error".equals(message)) {
            Snackbar.make(this.getCurrentFocus(), this.getString(R.string.server_error_message), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showLoadingView() {
        mStatusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        mStatusLayout.showContentView();
    }

    @Override
    public void showEmptyView() {
        mStatusLayout.showEmptyView();
        mStatusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getWorkOrderItems(initLine());
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getWorkOrderItems(initLine());
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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

    public String initLine() {
        Map<String, String> map = new HashMap<>();
        map.put("lines", lines);
        String line = GsonTools.createGsonListString(map);
        Log.e(TAG, "initLine: " + line);
        return line;
    }

    @Override
    public void showErrorView() {
        mStatusLayout.showErrorView();
        mStatusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getWorkOrderItems(initLine());
            }
        });
    }

    @Override
    public void onRefresh() {
        getPresenter().getWorkOrderItems(initLine());
        mSrfRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, ItemWarningInfo item, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("pro_scan_work_order", item.getWorkcode());
        bundle.putString("pro_scan_face", item.getFace());
        bundle.putString("pro_scan_line", item.getProductionline());
        IntentUtils.showIntent(this, BindingActivity.class, bundle);
    }
}
