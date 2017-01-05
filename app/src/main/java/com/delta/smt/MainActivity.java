package com.delta.smt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.GridItemDecoration;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.Download;
import com.delta.smt.entity.Update;
import com.delta.smt.service.WarningService;
import com.delta.smt.ui.checkstock.CheckStockActivity;
import com.delta.smt.ui.hand_add.mvp.HandAddActivity;
import com.delta.smt.ui.main.di.DaggerMainComponent;
import com.delta.smt.ui.main.di.MainModule;
import com.delta.smt.ui.main.mvp.MainContract;
import com.delta.smt.ui.main.mvp.MainPresenter;
import com.delta.smt.ui.main.update.DownloadService;
import com.delta.smt.ui.mantissa_warehouse.ready.MantissaWarehouseReadyActivity;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.MantissaWarehouseReturnAndPutStorageActivity;
import com.delta.smt.ui.smt_module.module_down.ModuleDownActivity;
import com.delta.smt.ui.smt_module.module_up.ModuleUpActivity;
import com.delta.smt.ui.storage_manger.StorageWarningActivity;
import com.delta.smt.ui.store.StoreIssueActivity;
import com.delta.smt.ui.storeroom.StoreRoomActivity;
import com.delta.smt.ui.warningSample.WarningSampleActivity;
import com.delta.smt.utils.PkgInfoUtils;
import com.delta.smt.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActiviy<MainPresenter> implements CommonBaseAdapter.OnItemClickListener<String>, MainContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<String> fuctionString;
    //更新
    private static ProgressDialog progressDialog = null;
    private LocalBroadcastManager bManager;

    @Override
    protected void componentInject(AppComponent appComponent) {
        //更新
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(new MainModule(this)).build().inject(this);

    }

    @Override
    protected void initView() {
        CommonBaseAdapter<String> adapter = new CommonBaseAdapter<String>(this, fuctionString) {
            @Override
            protected void convert(CommonViewHolder holder, String item, int position) {
                holder.setImageResource(R.id.iv_function, R.drawable.title);
                Log.e(TAG, "convert: " + item);
                holder.setText(R.id.tv_function, item);
            }

            @Override
            protected int getItemViewLayoutId(int position, String item) {
                return R.layout.item_function;
            }
        };
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new GridItemDecoration(this));
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {

        //6.0以上更新需要判断是否有写WRITE_EXTERNAL_STORAGE权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            getPresenter().checkUpdate();
        }

        Intent intent = new Intent(this, WarningService.class);
        startService(intent);
        fuctionString = new ArrayList<>();
        fuctionString.add("Feeder缓冲区");
        fuctionString.add("仓库房");
        fuctionString.add("PCB库房2");
        fuctionString.add("PCB库房3");
        fuctionString.add("PCB库房4");
        fuctionString.add("生产中预警");
        fuctionString.add("warningSample");
        fuctionString.add("手补件通知");
        fuctionString.add("尾数仓备料");
        fuctionString.add("尾数仓退料及入库");
        fuctionString.add("上模组");
        fuctionString.add("下模组");
        fuctionString.add("about");
        fuctionString.add("故障处理预警");

    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void onItemClick(View view, String item, int position) {
        Log.e(TAG, "onItemClick: " + item + position);
        ToastUtils.showMessage(this, item);

        switch (item) {
            case "Feeder缓冲区":
                IntentUtils.showIntent(this, com.delta.smt.ui.feeder.wareSelect.WareSelectActivity.class);
                break;
            case "仓库房":
                IntentUtils.showIntent(this, StorageWarningActivity.class);
                break;
            case "PCB库房2":
                IntentUtils.showIntent(this, StoreIssueActivity.class);
                break;
            case "PCB库房3":
                IntentUtils.showIntent(this, StoreRoomActivity.class);
                break;
            case "PCB库房4":
                IntentUtils.showIntent(this, CheckStockActivity.class);
                break;
            case "生产中预警":
                IntentUtils.showIntent(this, com.delta.smt.ui.fault_processing.produce_line.ProduceLineActivity.class);
                break;
            case "手补件通知":
                IntentUtils.showIntent(this, HandAddActivity.class);
                break;
            case "尾数仓备料":
                IntentUtils.showIntent(this, MantissaWarehouseReadyActivity.class);
                break;
            case "尾数仓退料及入库":
                IntentUtils.showIntent(this, MantissaWarehouseReturnAndPutStorageActivity.class);
                break;
            case "warningSample":
                IntentUtils.showIntent(this, WarningSampleActivity.class);
            case "上模组":
                IntentUtils.showIntent(this, ModuleUpActivity.class);
                break;
            case "下模组":
                IntentUtils.showIntent(this, ModuleDownActivity.class);
                break;
            case "about模组":
                break;
            case "故障处理预警":
                IntentUtils.showIntent(this, com.delta.smt.ui.fault_processing.produce_line.ProduceLineActivity.class);
                break;
            default:
                break;
        }
    }

    //展示app更新提示对话框
    @Override
    public void checkExistUpdateDialog(final Update update) {

        if (Integer.parseInt(update.getVersionCode()) > PkgInfoUtils.getVersionCode(MainActivity.this)) {
            String message_wifi = "当前版本为:" + PkgInfoUtils.getVersionName(this) + " Code:" + PkgInfoUtils.getVersionCode(this)
                    + "\n发现新版本:" + update.getVersion() + " Code:" + update.getVersionCode()
                    + "\n更新日志:"
                    + "\n" + update.getDescription();
            if (!DownloadService.isUpdating) {
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage(message_wifi)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //显示ProgerssDialog
                                showProgerssDialog(MainActivity.this);
                                getPresenter().download(MainActivity.this, update.getUrl());
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DownloadService.isUpdating = true;
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }


        }/*else{
            Toast.makeText(this,"没有发现新版本！",Toast.LENGTH_LONG).show();
        }*/
    }

    //更新状态
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constant.MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");
                int progress = download.getProgress();
                if (download.getProgress() == 100) {

                    progressDialog.setMessage("下载成功");
                    progressDialog.setProgress(progress);
                    progressDialog.setProgressNumberFormat(
                            StringUtils.getDataSize(download.getCurrentFileSize())
                                    + "/" +
                                    StringUtils.getDataSize(download.getTotalFileSize()));


                } else {
                    progressDialog.setProgress(progress);
                    progressDialog.setProgressNumberFormat(
                            StringUtils.getDataSize(download.getCurrentFileSize())
                                    + "/" +
                                    StringUtils.getDataSize(download.getTotalFileSize()));

                }
            } else if (intent.getAction().equals(Constant.MESSAGE_DIALOG_DISMISS)) {
                progressDialog.dismiss();
            } else if (intent.getAction().equals(Constant.MESSAGE_FAILED)) {
                progressDialog.setMessage("下载失败");
                progressDialog.setCancelable(true);
            }
        }
    };

    //运行时权限请求回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                //如果权限请求通过
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPresenter().checkUpdate();
                    //如果权限请求不通过
                } else {
                }
                return;
            }
        }
    }

    //显示更新ProgerssDialog
    private void showProgerssDialog(Context context) {
        registerReceiver();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("更新");
        progressDialog.setIcon(android.R.drawable.ic_dialog_info);
        progressDialog.setMessage("正在下载更新...");
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();
    }

    //更新
    private void registerReceiver() {

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.MESSAGE_PROGRESS);
        intentFilter.addAction(Constant.MESSAGE_DIALOG_DISMISS);
        intentFilter.addAction(Constant.MESSAGE_FAILED);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //更新
        //解除注册时，使用注册时的manager解绑
        if (broadcastReceiver != null && bManager != null) {
            bManager.unregisterReceiver(broadcastReceiver);
        }

    }

}
