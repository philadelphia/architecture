package com.delta.smt.ui.smt_module.module_up;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseCommonActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.ui.smt_module.module_up.di.DaggerModuleUpComponent;
import com.delta.smt.ui.smt_module.module_up.di.ModuleUpModule;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpPresenter;
import com.delta.smt.ui.smt_module.module_up_binding.ModuleUpBindingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpActivity extends BaseActiviy<ModuleUpPresenter> implements ModuleUpContract.View,CommonBaseAdapter.OnItemClickListener<ModuleUpWarningItem>{

    @BindView(R.id.header_back)
    TextView headerBack;
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

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpComponent.builder().appComponent(appComponent).moduleUpModule(new ModuleUpModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getAllModuleUpWarningItems();
    }

    @Override
    protected void initView() {
        headerTitle.setText("上模组");
        adapter = new CommonBaseAdapter<ModuleUpWarningItem>(this,dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpWarningItem item, int position) {
                holder.setText(R.id.tv_title, "线别: " + item.getLineNumber());
                holder.setText(R.id.tv_line, "工单号: " + item.getWorkItemID());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFaceID());
                holder.setText(R.id.tv_add_count, "状态: " + item.getStatus());
                //holder.setText(R.id.)
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpWarningItem item) {
                return R.layout.module_down_warning_list_item;
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
}
