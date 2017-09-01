package com.delta.smt.ui.production_scan.binding;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_scan.binding.di.BindingModule;
import com.delta.smt.ui.production_scan.binding.di.DaggerBindingComponent;
import com.delta.smt.ui.production_scan.binding.mvp.BindingContract;
import com.delta.smt.ui.production_scan.binding.mvp.BindingPresenter;
import com.delta.ttsmanager.TextToSpeechManager;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class BindingActivity extends BaseActivity<BindingPresenter> implements BindingContract.View/*, View.OnClickListener*/ {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.tv_work_order)
    TextView tvWorkOrder;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.tv_line_num)
    TextView tvLineNum;
    @Inject
    TextToSpeechManager textToSpeechManager;
    private Dialog mConfirmDialog;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerBindingComponent.builder().appComponent(appComponent).bindingModule(new BindingModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        Log.e(TAG, "onConfigurationChanged: " + newConfig.toString());
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        mToolbarTitle.setText("仓库");
        Bundle bundle = getIntent().getExtras();
        tvWorkOrder.setText(bundle.getString("pro_scan_work_order", ""));
        tvLineName.setText(bundle.getString("pro_scan_line", ""));
        tvLineNum.setText(bundle.getString("pro_scan_face", ""));

    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_pro_scan_binding;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void getSuccess(Result result) {

    }

    @Override
    public void getFailed(Result message) {

    }

    @Override
    public void getFailed(Throwable throwable) {

    }

    @Override
    public void showLoadingView() {

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
    public void onWindowFocusChanged(boolean hasFocus) {
        //mToolbarHeight = mToolbar.getHeight();
        super.onWindowFocusChanged(hasFocus);
    }


}
