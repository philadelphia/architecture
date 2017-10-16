package com.delta.smt.ui.quality_manage;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.libs.adapter.ItemTimeViewHolder;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.QualityManage;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.quality_manage.di.DaggerQualityManageComponent;
import com.delta.smt.ui.quality_manage.di.QualityManageModule;
import com.delta.smt.ui.quality_manage.mvp.QualityManageContract;
import com.delta.smt.ui.quality_manage.mvp.QualityManagePresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.delta.smt.R.id.statusLayout;

/**
 * Created by Zhenyu.Liu on 2017/4/18.
 */

public class QualityManageActivity extends BaseActivity<QualityManagePresenter> implements QualityManageContract.View,
        WarningManger.OnWarning, ItemOnclick<QualityManage.RowsBean> {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(statusLayout)
    StatusLayout mStatusLayout;
    private String lines;
    private AlertDialog dialog;
    private List<QualityManage.RowsBean> dataList = new ArrayList();
    private ItemCountViewAdapter<QualityManage.RowsBean> adapter;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerQualityManageComponent.builder().appComponent(appComponent).qualityManageModule(new QualityManageModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        lines = SpUtil.getStringSF(this, Constant.QUALITY_MANAGE);
        Map<String, String> hmap = new HashMap();
        hmap.put("lines", lines);
        Gson gson = new Gson();
        String s = GsonTools.createGsonListString(hmap);
        getPresenter().getQualityList(s);

    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("品管确认");
        adapter = new ItemCountViewAdapter<QualityManage.RowsBean>(this, dataList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.acrivity_quality_manage;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, QualityManage.RowsBean item, int position) {
                holder.setText(R.id.tv_line_name, "产线名: " + item.getLine());
                holder.setText(R.id.tv_order, "料站: " + item.getSlot());
                holder.setText(R.id.tv_sides, "缺料量: " + item.getExpected_amount());
                holder.setText(R.id.tv_five, "实际补料量: " + item.getReal_amount());
                if (1 == item.getStatus()) {
                    holder.setText(R.id.tv_states, "状态: " + "等待品管核验");
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else {
                    holder.setText(R.id.tv_states, "状态：品管已核验");
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                }
            }
        };
        adapter.setOnItemTimeOnclick(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mantissa;
    }

    @Override
    public void onItemClick(View item, final QualityManage.RowsBean rowsBean, int position) {

        dialog = new AlertDialog.Builder(this).setTitle("提示")//设置对话框标题

                .setMessage("确认核验?")//设置显示的内容

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        Map hmap = new HashMap();
                        hmap.put("quality_id", rowsBean.getQuality_id());

                        String s = GsonTools.createGsonListString(hmap);
                        getPresenter().getYesok(s);

                    }

                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                }).show();//在按键响应事件中显示此对话框

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
    public void showErrorView() {

        mStatusLayout.showErrorView();
        mStatusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lines = SpUtil.getStringSF(v.getContext(), Constant.QUALITY_MANAGE);
                Map hmap = new HashMap();
                hmap.put("lines", lines);

                String s = GsonTools.createGsonListString(hmap);
                getPresenter().getQualityList(s);
            }
        });
    }

    @Override
    public void showEmptyView() {

        mStatusLayout.showEmptyView();
        mStatusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lines = SpUtil.getStringSF(v.getContext(), Constant.QUALITY_MANAGE);
                Map hmap = new HashMap();
                hmap.put("lines", lines);
                String s = GsonTools.createGsonListString(hmap);
                getPresenter().getQualityList(s);
            }
        });
    }

    @Override
    public void getQualityListSuccess(List<QualityManage.RowsBean> qualityManage) {
        dataList.clear();

        for (int i = 0; i < qualityManage.size(); i++) {
            qualityManage.get(i).setCreat_time(System.currentTimeMillis() - Math.round(qualityManage.get(i).getDuration_time()) * 1000);
            qualityManage.get(i).setEntityId(i);
        }

        dataList.addAll(qualityManage);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getQualityListFailed(String message) {

    }

    @Override
    public void getokSuccess() {
        lines = SpUtil.getStringSF(this, Constant.QUALITY_MANAGE);
        Map hmap = new HashMap();
        hmap.put("lines", lines);
        String s = GsonTools.createGsonListString(hmap);
        getPresenter().getQualityList(s);
        dataList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getokFailed(String message) {

    }

    @Override
    public void warningComing(String warningMessage) {

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
}
