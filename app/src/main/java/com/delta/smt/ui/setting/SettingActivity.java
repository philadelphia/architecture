package com.delta.smt.ui.setting;

import android.Manifest;
import android.app.Dialog;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.delta.commonlibs.di.module.ClientModule;
import com.delta.commonlibs.utils.DialogUtils;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.api.API;
import com.delta.smt.app.App;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseApplication;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.di.component.DaggerAppComponent;
import com.delta.smt.entity.Download;
import com.delta.smt.entity.Update;
import com.delta.smt.ui.main.di.DaggerMainComponent;
import com.delta.smt.ui.main.di.MainModule;
import com.delta.smt.ui.main.mvp.MainContract;
import com.delta.smt.ui.main.mvp.MainPresenter;
import com.delta.smt.ui.main.update.DownloadService;
import com.delta.smt.utils.PkgInfoUtils;
import com.delta.smt.utils.StringUtils;
import com.delta.smt.utils.ViewUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.api.API.BASE_URL;

/**
 * Created by Lin.Hou on 2017-01-09.
 */

public class SettingActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    //更新
    private static ProgressDialog progressDialog = null;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.setting_update)
    TextView checkUpdateButton;
    @BindView(R.id.setting_server_address)
    TextView settingServerAddress;
    Pattern sAddressPattern;
    private LocalBroadcastManager bManager;
    private String downloadStr = null;
    private AlertDialog retryAlertDialog = null;
    private View dialog_view;
    private EditText et_ip;
    private EditText et_port;
    private Dialog dialog;
    private String ip;
    private String port;
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
                    retryAlertDialog = new AlertDialog.Builder(SettingActivity.this)
                            .setTitle("提示")
                            .setMessage("下载失败，请重试或取消更新！")
                            .setCancelable(false)
                            .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog.dismiss();
                                    //显示ProgerssDialog
                                    showProgerssDialog(SettingActivity.this);
                                    getPresenter().download(SettingActivity.this, downloadStr);
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
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(new MainModule(this)).build().inject(SettingActivity.this);
    }

    @Override
    protected void initData() {
        checkUpdateButton.setText("检查更新 (" + PkgInfoUtils.getVersionName(this) + ")");
        sAddressPattern = Pattern.compile(
                "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE
        );
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        ip = SpUtil.getStringSF(SettingActivity.this, "ip");
        port = SpUtil.getStringSF(SettingActivity.this, "port");
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {

            BASE_URL = "http://" + ip + ":" + port + "/";
            settingServerAddress.setText("配置服务器地址" + "\n(" + BASE_URL + ")");

        } else {
            settingServerAddress.setText("配置服务器地址" + "\n(" + API.BASE_URL + ")");
        }
//        if (SpUtil.getStringSF(SettingActivity.this, "server_address") == null) {
//            settingServerAddress.setText("配置服务器地址" + "\n(" + BASE_URL + ")");
//        } else if ("".equals(SpUtil.getStringSF(SettingActivity.this, "server_address"))) {
//            settingServerAddress.setText("配置服务器地址" + "()");
//        } else {
//            settingServerAddress.setText("配置服务器地址" + "\n(" + SpUtil.getStringSF(SettingActivity.this, "server_address") + ")");
//        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("设置");
    }

    private Dialog createDialog() {
        dialog_view = LayoutInflater.from(this).inflate(R.layout.item_dialog_sever_edit, null);
        et_ip = ViewUtils.findView(dialog_view, R.id.et_ip);
        et_port = ViewUtils.findView(dialog_view, R.id.et_port);
        ip = SpUtil.getStringSF(this, "ip");
        port = SpUtil.getStringSF(this, "port");
        if (ip != null && port != null) {
            et_ip.setText(ip);
            et_port.setText(port);
        }
        ViewUtils.findView(dialog_view, R.id.bt_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_ip.getText().toString())) {
                    ToastUtils.showMessage(SettingActivity.this, "ip不能为空");
                    return;
                }
                if (TextUtils.isEmpty(et_port.getText().toString())) {
                    ToastUtils.showMessage(SettingActivity.this, "端口号不能为空");
                    return;
                }

                /*SpUtil.SetStringSF(SettingActivity.this, "ip", et_ip.getText().toString());
                SpUtil.SetStringSF(SettingActivity.this, "port", et_port.getText().toString());*/
                /*ip = SpUtil.getStringSF(SettingActivity.this, "ip");
                port = SpUtil.getStringSF(SettingActivity.this, "port");*/
                ip = et_ip.getText().toString();
                port = et_port.getText().toString();
                BASE_URL = "http://" + ip + ":" + port + "/";
                Matcher m = sAddressPattern.matcher(BASE_URL);
                if (m.matches()) {
                    settingServerAddress.setText("配置服务器地址" + "\n(" + BASE_URL + ")");
                    ClientModule mClientModule = ClientModule//用于提供okhttp和retrofit的单列
                            .buidler()
                            .baseurl(BASE_URL)
                            .globeHttpHandler(App.getHttpHandler())
                            .interceptors(App.getInterceptors())
                            .responseErroListener(((BaseApplication) App.getContext()))
                            .build();
                    App.appComponent = DaggerAppComponent.builder().clientModule(mClientModule).appModule(App.getAppModule()).serviceModule(App.getServiceModule()).build();
                    SpUtil.SetStringSF(SettingActivity.this, "ip", ip);
                    SpUtil.SetStringSF(SettingActivity.this, "port", port);
                } else {
                    ToastUtils.showMessage(SettingActivity.this, "此地址无效");
                }

                dialog.dismiss();
            }
        });
        return DialogUtils.showDefineDialog(this, dialog_view);
    }

    @OnClick({R.id.setting_update, R.id.setting_server_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_update:

                DownloadService.isUpdating = false;
                //6.0以上更新需要判断是否有写WRITE_EXTERNAL_STORAGE权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    getPresenter().checkUpdate();
                }
                break;
            case R.id.setting_server_address:

                if (dialog == null) {
                    dialog = createDialog();
                } else {
                    if (!dialog.isShowing()) {
                        ip = SpUtil.getStringSF(this, "ip");
                        port = SpUtil.getStringSF(this, "port");
                        if (ip != null && port != null) {
                            et_ip.setText(ip);
                            et_port.setText(port);
                        }
                        dialog.show();
                    }
                }

                break;
        }
    }

    //展示app更新提示对话框
    @Override
    public void checkExistUpdateDialog(final Update update) {
        if (Integer.parseInt(update.getVersionCode()) > PkgInfoUtils.getVersionCode(SettingActivity.this)) {
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
                                showProgerssDialog(SettingActivity.this);
                                getPresenter().download(SettingActivity.this, downloadStr);
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


        } else {
            if (!DownloadService.isUpdating) {
                new AlertDialog.Builder(this)
                        .setTitle("未发现新版本！")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //显示ProgerssDialog
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
}
