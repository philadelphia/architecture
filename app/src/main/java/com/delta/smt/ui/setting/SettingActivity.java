package com.delta.smt.ui.setting;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.delta.commonlibs.di.module.ClientModule;
import com.delta.commonlibs.utils.DialogUtils;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.utils.ViewUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.api.API;
import com.delta.smt.app.App;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseApplication;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.di.component.DaggerAppComponent;
import com.delta.smt.service.warningService.WarningService;
import com.delta.smt.ui.main.di.DaggerMainComponent;
import com.delta.smt.ui.main.di.MainModule;
import com.delta.smt.ui.main.mvp.MainContract;
import com.delta.smt.ui.main.mvp.MainPresenter;
import com.delta.smt.utils.DataClearManager;
import com.delta.smt.utils.PkgInfoUtils;
import com.delta.ttsmanager.TextToSpeechManager;
import com.delta.updatelibs.UpdateUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.delta.smt.api.API.BASE_URL;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Lin.Hou on 2017-01-09.
 */

public class SettingActivity extends BaseActivity<MainPresenter> implements MainContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.setting_update)
    TextView checkUpdateButton;
    @BindView(R.id.setting_server_address)
    TextView settingServerAddress;
    Pattern sAddressPattern;
    @BindView(R.id.sc_speech)
    SwitchCompat scSpeech;
    @Inject
    TextToSpeechManager textToSpeechManager;
    @BindView(R.id.tv_cache_data)
    TextView mTvCacheData;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.tv_speech)
    TextView mTvSpeech;
    private View dialog_view;
    private EditText et_ip;
    private EditText et_port;
    private Dialog dialog;
    private String ip;
    private String port;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("设置");
        boolean speech_switch = SpUtil.getBooleanSF(this, "speech_switch");
        try {
            mTvCacheData.setText(DataClearManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        scSpeech.setChecked(speech_switch);
        scSpeech.setOnCheckedChangeListener(this);
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
        } else {
            et_ip.setText(API.IP);
            et_port.setText(API.PORT);
            SpUtil.SetStringSF(SettingActivity.this, "ip", API.IP);
            SpUtil.SetStringSF(SettingActivity.this, "port", API.PORT);
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
                    API.IP = ip;
                    API.PORT = port;
                    BASE_URL = "http://" + API.IP + ":" + API.PORT + "/";
                    //重启service
                    Intent intent = new Intent(SettingActivity.this, WarningService.class);
                    startService(intent);
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

    @OnClick({R.id.setting_update, R.id.setting_server_address,R.id.setting_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_update:
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //手动更新时申请WRITE_EXTERNAL_STORAGE权限，设置requestPermissions方法参数requestCode设置为6
                    int requestCode = 6;
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                } else {
                    //检查更新，第三个参数为1
                    UpdateUtils.checkUpdateInfo(getApplication(), SettingActivity.this.getPackageName() + ".fileprovider", 1);
                    Log.i(TAG, "onClick: " + SettingActivity.this.getPackageName());
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
            case R.id.setting_cache:
                DialogUtils.showCommonDialog(this, "是否清除缓存？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataClearManager.clearAllCache(getContext());
                        try {
                            mTvCacheData.setText(DataClearManager.getTotalCacheSize(getContext()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //更新
        //解除注册时，使用注册时的manager解绑
        /*if (broadcastReceiver != null && bManager != null) {
            bManager.unregisterReceiver(broadcastReceiver);
        }*/

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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SpUtil.SetBooleanSF(this, "speech_switch", isChecked);
        textToSpeechManager.setRead(isChecked);
    }

    //运行时权限请求回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            //手动检查更新时请求WRITE_EXTERNAL_STORAGE权限对应回调
            case 6:
                //如果权限请求通过
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //手动检查更新，checkUpdateInfo方法第三个参数设置为1
                    UpdateUtils.checkUpdateInfo(getApplication(), SettingActivity.this.getPackageName() + ".fileprovider", 1);
                } else {
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
