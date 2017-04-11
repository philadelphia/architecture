package com.delta.smt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.DeviceUtil;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.GridItemDecoration;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.Download;
import com.delta.smt.entity.Function;
import com.delta.smt.entity.Update;
import com.delta.smt.ui.checkstock.StartWorkAndStopWorkActivity;
import com.delta.smt.ui.feeder.warning.supply.FeederSupplyListActivity;
import com.delta.smt.ui.main.di.DaggerMainComponent;
import com.delta.smt.ui.main.di.MainModule;
import com.delta.smt.ui.main.mvp.MainContract;
import com.delta.smt.ui.main.mvp.MainPresenter;
import com.delta.smt.ui.main.update.DownloadService;
import com.delta.smt.ui.maintain.PCBMaintainActivity;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.MantissaWarehouseReturnAndPutStorageActivity;
import com.delta.smt.ui.over_receive.OverReceiveActivity;
import com.delta.smt.ui.product_tools.back.ProduceToolsBackActivity;
import com.delta.smt.ui.product_tools.borrow.ProduceToolsBorrowActivity;
import com.delta.smt.ui.product_tools.location.ProduceToolsLocationActivity;
import com.delta.smt.ui.production_warning.mvp.produce_line.ProduceLineActivity;
import com.delta.smt.ui.setting.SettingActivity;
import com.delta.smt.ui.smt_module.module_down.ModuleDownActivity;
import com.delta.smt.ui.smt_module.module_up.ModuleUpActivity;
import com.delta.smt.ui.storage_manger.storage_select.StorageSelectActivity;
import com.delta.smt.ui.store.StoreIssueActivity;
import com.delta.smt.ui.storeroom.StoreRoomActivity;
import com.delta.smt.ui.warningSample.WarningSampleActivity;
import com.delta.smt.utils.PkgInfoUtils;
import com.delta.smt.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements CommonBaseAdapter.OnItemClickListener<Function>, MainContract.View {

    private static final int CODE = 100;
    //更新
    private static ProgressDialog progressDialog = null;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.rv_main)
    RecyclerView rv;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.drawer_layout)
    LinearLayout drawerLayout;
    private List<Function> functionList;
    private LocalBroadcastManager bManager;
    private String downloadStr = null;
    private AlertDialog retryAlertDialog = null;
    private Bundle bundle;
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
                if (retryAlertDialog == null) {

                    retryAlertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage("下载失败，请重试或取消更新！")
                            .setCancelable(false)
                            .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog.dismiss();
                                    //显示ProgerssDialog
                                    showProgerssDialog(MainActivity.this);
                                    getPresenter().download(MainActivity.this, downloadStr);
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog.dismiss();
                                    dialogInterface.dismiss();
                                }
                            })
                            .create();
                }
                if (!retryAlertDialog.isShowing()) {
                    retryAlertDialog.show();
                }
            }
        }
    };

    @Override
    protected void componentInject(AppComponent appComponent) {
        //更新
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(new MainModule(this)).build().inject(this);

    }

    @Override
    protected void initView() {
        toolbarTitle.setText("首页");
        tvSetting.setVisibility(View.VISIBLE);
        CommonBaseAdapter<Function> adapter = new CommonBaseAdapter<Function>(this, functionList) {
            @Override
            protected void convert(CommonViewHolder holder, Function item, int position) {
                holder.setImageResource(R.id.iv_function, item.getId());
                Log.e(TAG, "convert: " + item);
                holder.setText(R.id.tv_function, item.getTitle());
            }

            @Override
            protected int getItemViewLayoutId(int position, Function item) {
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
        checkTTS();
        boolean appInstalled = DeviceUtil.isAppInstalled(this, "com.iflytek.tts");

        Log.e(TAG, "initData: "+appInstalled);
        //6.0以上更新需要判断是否有写WRITE_EXTERNAL_STORAGE权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            getPresenter().checkUpdate();
        }
        functionList = new ArrayList<>();
        functionList.add(new Function("PCB入库", R.drawable.ic_warehousestorage));
        functionList.add(new Function("PCB盘点", R.drawable.ic_warehouseinventory));
        functionList.add(new Function("PCB发料", R.drawable.ic_warehouseforsending));
        functionList.add(new Function("PCB维护", R.drawable.ic_pcbsetting));
        functionList.add(new Function("仓库备料", R.drawable.ic_warehouseroompreparation));
        functionList.add(new Function("仓库超领", R.drawable.ic_warehouseroomchaoling));
        functionList.add(new Function("Feeder缓冲区", R.drawable.ic_feederbuffer));
        //functionList.add(new Function("尾数仓备料", R.drawable.ic_mantissawarehousestock));
        functionList.add(new Function("上模组", R.drawable.ic_onthemodule));
        functionList.add(new Function("下模组", R.drawable.ic_themodule));
        functionList.add(new Function("故障处理", R.drawable.ic_faulthandling));
        functionList.add(new Function("尾数仓入库及退料", R.drawable.ic_return));
        functionList.add(new Function("生产中预警", R.drawable.ic_warning));
        functionList.add(new Function("治具入架位", R.drawable.ic_intherack));
        functionList.add(new Function("治具借出", R.drawable.ic_lend));
        functionList.add(new Function("治具归还", R.drawable.ic_thereturn));
        functionList.add(new Function("手补件", R.drawable.ic_handpatch));
       // functionList.add(new Function("warningSample", R.drawable.title));
    }
    private void checkTTS() {
        Intent in = new Intent();
        in.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(in, CODE);
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onItemClick(View view, Function item, int position) {

        switch (item.getTitle()) {
            case "Feeder缓冲区":
                IntentUtils.showIntent(this, FeederSupplyListActivity.class);
                break;
            case "仓库备料":
                IntentUtils.showIntent(this, StorageSelectActivity.class);
                break;
            case "仓库超领":
                IntentUtils.showIntent(this, OverReceiveActivity.class);
                break;
            case "PCB发料":
                IntentUtils.showIntent(this, StoreIssueActivity.class);
                break;
            case "PCB入库":
                IntentUtils.showIntent(this, StoreRoomActivity.class);
                break;
            case "PCB盘点":
                IntentUtils.showIntent(this, StartWorkAndStopWorkActivity.class);
                break;
            case "PCB维护":
                IntentUtils.showIntent(this, PCBMaintainActivity.class);
                break;
            case "生产中预警":
                bundle = new Bundle();
                bundle.putInt(Constant.SELECT_TYPE, 0);
                IntentUtils.showIntent(this, ProduceLineActivity.class, bundle);
                break;

            case "手补件":
                bundle=new Bundle();
                bundle.putInt(Constant.SELECT_TYPE,2);
                IntentUtils.showIntent(this,ProduceLineActivity.class,bundle);
//                IntentUtils.showIntent(this, HandAddActivity.class);
                break;

            case "尾数仓入库及退料":
                IntentUtils.showIntent(this, MantissaWarehouseReturnAndPutStorageActivity.class);
                break;
            case "warningSample":
                IntentUtils.showIntent(this, WarningSampleActivity.class);
                break;
            case "上模组":
                IntentUtils.showIntent(this, ModuleUpActivity.class);
                break;
            case "下模组":
                IntentUtils.showIntent(this, ModuleDownActivity.class);
                break;
            case "故障处理":
                bundle = new Bundle();
                bundle.putInt(Constant.SELECT_TYPE, 1);
                IntentUtils.showIntent(this, ProduceLineActivity.class, bundle);
                break;
            case "治具借出":
                IntentUtils.showIntent(this, ProduceToolsBorrowActivity.class);
                break;
            case "治具归还":
                IntentUtils.showIntent(this, ProduceToolsBackActivity.class);
                break;
            case "治具入架位":
                IntentUtils.showIntent(this, ProduceToolsLocationActivity.class);
                break;
            default:
                break;
        }
    }

    //展示app更新提示对话框
    @Override
    public void checkExistUpdateDialog(final Update update) {

        if (Integer.parseInt(update.getVersionCode()) > PkgInfoUtils.getVersionCode(MainActivity.this)) {

            if (!DownloadService.isUpdating) {
                new AlertDialog.Builder(this)
                        .setTitle("发现新版本 " + update.getVersion())
                        .setMessage(update.getDescription())
                        .setCancelable(false)
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                downloadStr = update.getUrl();
                                //显示ProgerssDialog
                                showProgerssDialog(MainActivity.this);
                                getPresenter().download(MainActivity.this, downloadStr);
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
        }
    }

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


    @OnClick(R.id.tv_setting)
    public void onClick() {
        IntentUtils.showIntent(this, SettingActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            switch (resultCode) {
                case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
                   // Toast.makeText(this, "恭喜您，TTS可用", Toast.LENGTH_SHORT).show();
                   // mTts = new TextToSpeech(this, this);
                    break;
                case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:// 发音数据已经损坏
                    // 下载TTS对应的资源
                    Intent dataIntent = new Intent(
                            TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(dataIntent);
                    break;

            }
        }
    }
    /**
     * 安装语音相关资源包
     */
    private void installTTS() {
        AlertDialog.Builder alertInstall = new AlertDialog.Builder(this)
                .setTitle("缺少语音包")
                .setMessage("下载语音包")
                .setPositiveButton("去下载",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // 下载eyes-free的语音数据包
                                String ttsDataUrl = "http://eyes-free.googlecode.com/files/tts_3.1_market.apk";
                                Uri ttsDataUri = Uri.parse(ttsDataUrl);
                                Intent ttsIntent = new Intent(
                                        Intent.ACTION_VIEW, ttsDataUri);
                                startActivity(ttsIntent);
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertInstall.create().show();
    }

}

