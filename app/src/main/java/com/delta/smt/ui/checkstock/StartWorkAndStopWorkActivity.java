package com.delta.smt.ui.checkstock;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.ui.checkstock.di.DaggerStartWorkComponent;
import com.delta.smt.ui.checkstock.di.StartWorkModule;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkContract;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lin.Hou on 2017-02-10.
 */

public class StartWorkAndStopWorkActivity extends BaseActivity<StartWorkAndStopWorkPresenter> implements StartWorkAndStopWorkContract.View {
    @BindView(R.id.startAndstop_startwork)
    Button startAndstopStartwork;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.goneView)
    AutoLinearLayout goneView;
    @BindView(R.id.startAndstop_text)
    TextView startAndstopText;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerStartWorkComponent.builder().appComponent(appComponent).startWorkModule(new StartWorkModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(this.getResources().getString(R.string.pcbcheck));
        getPresenter().OnGoing();

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
        return R.layout.activity_startworkandstopwork;
    }

    @OnClick({R.id.startAndstop_startwork,R.id.startAndstop_continue,R.id.startAndstop_cancel})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.startAndstop_startwork:
                getPresenter().StartWork();
                break;
            case R.id.startAndstop_continue:
                startActivity(new Intent(this, CheckStockActivity.class));
                break;
            case R.id.startAndstop_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onFailed(String s) {
        ToastUtils.showMessage(this, s);

    }

    @Override
    public void onStartWork(String s) {
        startActivity(new Intent(this, CheckStockActivity.class));
    }

    @Override
    public void ongoingSuccess(OnGoing s) {
        startAndstopStartwork.setVisibility(View.GONE);
        goneView.setVisibility(View.VISIBLE);
        goneView.setGravity(Gravity.CENTER);
        if (s.getRows() != null) {
            startAndstopText.setText(s.getRows().getCompletedSubShelf().get(s.getRows().getCompletedSubShelf().size() - 1));
        } else {

        }
    }

    @Override
    public void ongoingFailed() {


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
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
    }

}
