package com.delta.smt.ui.fault_processing.fault_solution;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FaultSolutionMessage;
import com.delta.smt.ui.fault_processing.fault_solution.di.DaggerFaultSolutionComponent;
import com.delta.smt.ui.fault_processing.fault_solution.di.FaultSolutionModule;
import com.delta.smt.ui.fault_processing.fault_solution.mvp.FaultSolutionContract;
import com.delta.smt.ui.fault_processing.fault_solution.mvp.FaultSolutionPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description :
 * @author :  V.Wenju.Tian
 * @date : 2017/1/9 19:18
 */


public class FaultSolutionDetailActivity extends BaseActivity<FaultSolutionPresenter> implements FaultSolutionContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.button)
    Button button;

    private String faultCode;
    private String lineName;
    private String faultSolutionName;
    private static final String TAG = "FaultSolutionDetailActi";

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultSolutionComponent.builder().appComponent(appComponent).faultSolutionModule(new FaultSolutionModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        lineName = extras.getString(Constant.FAULT_PROCESSING_LINE_NAME);
        faultCode = extras.getString(Constant.FAULT_CODE);
        faultSolutionName = extras.getString(Constant.FAULT_SOLUTION_NAME);
        Map<String, String> map = new HashMap<>();
        map.put("fileName", faultSolutionName);

        getPresenter().getDetailSolutionMessage(GsonTools.createGsonListString(map));
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        toolbarTitle.setText("故障处理");

        setSupportActionBar(toolbar);


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
    }

    @Override
    public void getMessageFailed(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void resolveFaultSuccess(String message) {
        ToastUtils.showMessage(this, message);
        finish();
    }

    @Override
    public void onSuccess(String message) {
        Log.i(TAG, "onSuccess: " + message);
        mWebView.loadDataWithBaseURL(null, message, "text/html","UTF-8", null);

    }

    @Override
    public void onFailed(String message) {

    }


    @OnClick(R.id.button)
    public void onClick() {
        if (SingleClick.isSingle(5000)) {
            Map<String, String> map = new HashMap<>();
            map.put("solution_name", faultSolutionName);
            map.put("exception_code", faultCode);
            map.put("line", lineName);
            getPresenter().resolveFault(GsonTools.createGsonListString(map));
        }
    }
}
