package com.delta.smt.ui.checkstock;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.CheckStockDemo;
import com.delta.smt.entity.OnGoing;
import com.delta.smt.ui.checkstock.di.DaggerStartWorkComponent;
import com.delta.smt.ui.checkstock.di.StartWorkModule;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkContract;
import com.delta.smt.ui.checkstock.mvp.StartWorkAndStopWorkPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Lin.Hou on 2017-02-10.
 */

public class StartWorkAndStopWorkActivity extends BaseActivity<StartWorkAndStopWorkPresenter> implements StartWorkAndStopWorkContract.View,View.OnClickListener{
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
    private AlertDialog mStopWorkDialog;
    private AlertDialog.Builder builder;
    private AlertDialog mSummarizeDialog;
    private AlertDialog unCheckDialog;
    private List<OnGoing.RowsBean.CompletedSubShelfBean> list=new ArrayList<>();

//    private String mFrameLocation;


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
        builder = new AlertDialog.Builder(this);
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
    public void onClicks(View view) {

        switch (view.getId()) {
            case R.id.startAndstop_startwork:
                getPresenter().StartWork();
                break;
            case R.id.startAndstop_continue:
                final Intent intent=new Intent(this, CheckStockActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("FrameLocation",startAndstopText.getText().toString());
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                break;
            case R.id.startAndstop_uncheck:
                unCheckDialog = builder.create();
                unCheckDialog.show();
                unCheckDialog.setContentView(R.layout.dialog_uncheck);
                RecyclerView dialogRecycler = (RecyclerView) unCheckDialog.findViewById(R.id.dialog_recycler);
                CommonBaseAdapter<OnGoing.RowsBean.CompletedSubShelfBean> adapter=new CommonBaseAdapter<OnGoing.RowsBean.CompletedSubShelfBean>(this,list) {
                    @Override
                    protected void convert(CommonViewHolder holder, OnGoing.RowsBean.CompletedSubShelfBean item, int position) {
                        holder.setText(R.id.uncheck_text,item.getSubshelf());
                    }

                    @Override
                    protected int getItemViewLayoutId(int position, OnGoing.RowsBean.CompletedSubShelfBean item) {
                        return R.layout.item_uncheck;
                    }
                };
                dialogRecycler.setLayoutManager(new GridLayoutManager(this,4));
                dialogRecycler.setAdapter(adapter);
                break;
            case R.id.startAndstop_cancel:
                mStopWorkDialog = builder.create();
                mStopWorkDialog.show();
                mStopWorkDialog.setContentView(R.layout.dialog_stopwork);
                mStopWorkDialog.findViewById(R.id.stopwork_affirm).setOnClickListener(this);
                mStopWorkDialog.findViewById(R.id.stopwork_cancel).setOnClickListener(this);
                break;

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
      case R.id.stopwork_affirm:
      if (mStopWorkDialog.isShowing()) {
        mStopWorkDialog.dismiss();
        SpUtil.SetString(StartWorkAndStopWorkActivity.this,"FrameLocation","");
        getPresenter().fetchInventoryException();}
                break;
      case R.id.stopwork_cancel:
      if (mStopWorkDialog.isShowing()) {
        mStopWorkDialog.dismiss();
        }
                break;
            case R.id.dialog_summarize_cancel:
                if (mSummarizeDialog.isShowing()) {
                    mSummarizeDialog.dismiss();
                    getPresenter().onEndSuccess();
                }
                break;
    }
    }

    @Override
    public void onFailed(String s) {
        ToastUtils.showMessage(this, s);

    }

    @Override
    public void onStartWork(String s)
    {
        startActivity(new Intent(this, CheckStockActivity.class));

    }

    @Override
    public void ongoingSuccess(String s, List<OnGoing.RowsBean.CompletedSubShelfBean> list) {
        startAndstopStartwork.setVisibility(View.GONE);
        goneView.setVisibility(View.VISIBLE);
        goneView.setGravity(Gravity.CENTER);
        startAndstopText.setText(s);
        this.list=list;

    }



    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().OnGoing();
    }

    @Override
    public void ongoingFailed() {
        startAndstopStartwork.setVisibility(View.VISIBLE);
        goneView.setVisibility(View.GONE);
        startAndstopStartwork.setGravity(Gravity.CENTER);

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

    @Override
    public void onInventoryException(String s) {
        mSummarizeDialog = builder.create();
        mSummarizeDialog.show();
        mSummarizeDialog.setContentView(R.layout.dialog_summarize);
        TextView textView= (TextView) mSummarizeDialog.findViewById(R.id.dialog_summarize_content);
        textView.setText(s);
        mSummarizeDialog.findViewById(R.id.dialog_summarize_cancel).setOnClickListener(this);
    }
    @Override
    public void onEndSucess() {
        IntentUtils.showIntent(this, StartWorkAndStopWorkActivity.class);
//        IntentUtils.showIntent(this, StartWorkAndStopWorkActivity.class);
    }

}
