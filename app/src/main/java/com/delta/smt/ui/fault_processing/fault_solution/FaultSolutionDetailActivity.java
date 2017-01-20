package com.delta.smt.ui.fault_processing.fault_solution;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FaultSolutionMessage;
import com.delta.smt.ui.fault_processing.fault_solution.di.DaggerFaultSolutionComponent;
import com.delta.smt.ui.fault_processing.fault_solution.di.FaultSolutionModule;
import com.delta.smt.ui.fault_processing.fault_solution.mvp.FaultSolutionContract;
import com.delta.smt.ui.fault_processing.fault_solution.mvp.FaultSolutionPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/9 19:18
 */


public class FaultSolutionDetailActivity extends BaseActivity<FaultSolutionPresenter> implements FaultSolutionContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    List<FaultSolutionMessage.RowsBean> datas = new ArrayList<>();
    @BindView(R.id.button)
    Button button;
    private CommonBaseAdapter<FaultSolutionMessage.RowsBean> adapter;
    private String faultCode;
    private String faultId;
    private String faultSolutionId;
    private String faultSolutionName;
    private int size;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultSolutionComponent.builder().appComponent(appComponent).faultSolutionModule(new FaultSolutionModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        faultSolutionId = extras.getString(Constant.FAULTSOLUTIONID);
        faultCode = extras.getString(Constant.FAULTCODE);
        faultId = extras.getString(Constant.FAULTID);
        faultSolutionName = extras.getString(Constant.FAULTSOLUTIONNAME);
        getPresenter().getDetailSolutionMessage(faultSolutionId);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("故障处理");
        tvTitle.setText(faultSolutionName + ":");
        setSupportActionBar(toolbar);
        adapter = new CommonBaseAdapter<FaultSolutionMessage.RowsBean>(this, datas) {
            @Override
            protected void convert(CommonViewHolder holder, FaultSolutionMessage.RowsBean item, int position) {

                if (size == 1) {
                    holder.setText(R.id.tv_step_content, item.getContent());
                } else {

                    holder.setText(R.id.tv_step_content, item.getOrderNum() + "." + item.getContent());
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, FaultSolutionMessage.RowsBean item) {
                return R.layout.item_detail_solution;
            }
        };
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setAdapter(adapter);

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fault_solution;
    }


    @Override
    public void getDetailSolutionMessage(List<FaultSolutionMessage.RowsBean> rowsBean) {
        size = rowsBean.size();
        datas.clear();
        datas.addAll(rowsBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getMessageFailed(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void resolveFaultSucess(String message) {
        ToastUtils.showMessage(this, message);
        finish();
    }


    @OnClick(R.id.button)
    public void onClick() {
        if (SingleClick.isSingle(5000)) {
            Map map = new HashMap();
            map.put("faultId", faultId);
            map.put("solutionId", faultSolutionId);
            map.put("faultCode", faultCode);
            getPresenter().resolveFault(GsonTools.createGsonString(map));
        }
    }
}
