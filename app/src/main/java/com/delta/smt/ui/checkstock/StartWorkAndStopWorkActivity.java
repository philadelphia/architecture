package com.delta.smt.ui.checkstock;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
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
    @BindView(R.id.startAndstop_uncheckedlist)
    RecyclerView startAndstopUncheckedlist;
    @BindView(R.id.startAndstop_checkedlist)
    RecyclerView startAndstopCheckedlist;
    private List<OnGoing.RowsBean.CompletedSubShelfBean> mUnCheckedList = new ArrayList<>();
    private List<OnGoing.RowsBean.CompletedSubShelfBean> mCheckedList = new ArrayList<>();
    private RecyclerView.Adapter mUnCheckedadapter;
    private RecyclerView.Adapter mCheckedadapter;

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
        getPresenter().OnGoing();
        mUnCheckedadapter= new CommonBaseAdapter<OnGoing.RowsBean.CompletedSubShelfBean>(this, mUnCheckedList) {
            @Override
            protected void convert(CommonViewHolder holder, OnGoing.RowsBean.CompletedSubShelfBean item, int position) {
                holder.setText(R.id.uncheck_text,item.getSubshelf());
            }

            @Override
            protected int getItemViewLayoutId(int position, OnGoing.RowsBean.CompletedSubShelfBean item) {
                return R.layout.item_uncheck;
            }
        };

        startAndstopUncheckedlist.setLayoutManager(new GridLayoutManager(this,4));
        startAndstopUncheckedlist.setAdapter(mUnCheckedadapter);
        mUnCheckedadapter.notifyDataSetChanged();
        mCheckedadapter= new CommonBaseAdapter<OnGoing.RowsBean.CompletedSubShelfBean>(this, mCheckedList) {
            @Override
            protected void convert(CommonViewHolder holder, OnGoing.RowsBean.CompletedSubShelfBean item, int position) {
                holder.setText(R.id.uncheck_text,item.getSubshelf());

            }

            @Override
            protected int getItemViewLayoutId(int position, OnGoing.RowsBean.CompletedSubShelfBean item) {
                return R.layout.item_uncheck;
            }
        };
        startAndstopCheckedlist.setLayoutManager(new GridLayoutManager(this,4));
        startAndstopCheckedlist.setAdapter(mCheckedadapter);


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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.startAndstop_startwork, R.id.startAndstop_continue, R.id.startAndstop_cancel})
    public void onClicks(View view) {

        switch (view.getId()) {
            case R.id.startAndstop_startwork:
                getPresenter().StartWork();
                break;
            case R.id.startAndstop_continue:
                final Intent intent = new Intent(this, CheckStockActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("FrameLocation", startAndstopText.getText().toString());
                Log.d("info",startAndstopText.getText().toString());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                break;
            case R.id.startAndstop_cancel:
                final AlertDialog builder= new AlertDialog.Builder(this).setMessage("请确认是否结束盘点？").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        getPresenter().fetchInventoryException();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();



                    }
                }).create();
                builder.show();
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
    public void ongoingSuccess(String s, List<OnGoing.RowsBean.CompletedSubShelfBean> list) {
        startAndstopStartwork.setVisibility(View.GONE);
        goneView.setVisibility(View.VISIBLE);
        goneView.setGravity(Gravity.CENTER);
        startAndstopText.setText(s);
        mCheckedList.clear();
        mUnCheckedList.clear();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getStatus()==2){
               mCheckedList.add(list.get(i));
            }else if (list.get(i).getStatus()==1){
               Log.i("info",list.get(i).getLabelCode());
            }else{
                mUnCheckedList.add(list.get(i));
            }
        }
        mCheckedadapter.notifyDataSetChanged();
        mUnCheckedadapter.notifyDataSetChanged();

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
      final AlertDialog mSummarizeDialog= new AlertDialog.Builder(this).setMessage(s).setTitle("提示").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                    getPresenter().onEndSuccess();

            }
        }).create();
        mSummarizeDialog.show();






    }

    @Override
    public void onEndSucess() {
        IntentUtils.showIntent(this, StartWorkAndStopWorkActivity.class);
//        IntentUtils.showIntent(this, StartWorkAndStopWorkActivity.class);
    }

}
