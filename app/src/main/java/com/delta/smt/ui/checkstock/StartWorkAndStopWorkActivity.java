package com.delta.smt.ui.checkstock;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkContract;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Lin.Hou on 2017-02-10.
 */

public class StartWorkAndStopWorkActivity extends BaseActivity<StartWorkAndStopWorkPresenter> implements StartWorkAndStopWorkContract.View {
    @BindView(R.id.startAndstop_startwork)
    Button startAndstopStartwork;
    @BindView(R.id.startAndstop_stopwork)
    Button startAndstopStopwork;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;

    @Override
    protected void componentInject(AppComponent appComponent) {

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

    @OnClick({R.id.startAndstop_startwork, R.id.startAndstop_stopwork})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.startAndstop_startwork:
                startActivity(new Intent(this,CheckStockActivity.class));
                break;
            case R.id.startAndstop_stopwork:

                break;
        }
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
