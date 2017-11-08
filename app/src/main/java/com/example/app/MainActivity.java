package com.example.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.base.BaseActivity;
import com.example.app.common.CommonBaseAdapter;
import com.example.app.common.CommonViewHolder;
import com.example.app.common.GridItemDecoration;
import com.example.app.di.component.AppComponent;
import com.example.commonlibs.utils.DeviceUtil;
import com.example.commonlibs.utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements CommonBaseAdapter.OnItemClickListener<Function>, MainContract.View {

    private static final int CODE = 100;
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
    private Bundle bundle;


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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //程序启动时申请WRITE_EXTERNAL_STORAGE权限，设置requestPermissions方法参数requestCode为5
            int requestCode = 5;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
//如果已经授权WRITE_EXTERNAL_STORAGE权限
        } else {
            //检查更新，第三个参数为0
            UpdateUtils.checkUpdateInfo(getApplication(), this.getPackageName() + ".fileprovider", 0);
            Log.i(TAG, "onClick: " + this.getPackageName());
        }
        checkTTS();
        boolean appInstalled = DeviceUtil.isAppInstalled(this, "com.iflytek.tts");

        Log.e(TAG, "initData: " + appInstalled);

        functionList = new ArrayList<>();

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

//
            default:
                break;
        }
    }

    //运行时权限请求回调方法z
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            //程序启动时请求WRITE_EXTERNAL_STORAGE权限对应回调
            case 5:
                //如果权限请求通过
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //程序启动时检查更新，checkUpdateInfo方法第三个参数设置为0
                    UpdateUtils.checkUpdateInfo(getApplication(), MainActivity.this.getPackageName() + ".fileprovider", 0);
                } else {
                }
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @OnClick(R.id.tv_setting)
    public void onClick() {
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
//    /**
//     * 安装语音相关资源包
//     */
//    private void installTTS() {
//        AlertDialog.Builder alertInstall = new AlertDialog.Builder(this)
//                .setTitle("缺少语音包")
//                .setMessage("下载语音包")
//                .setPositiveButton("去下载",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                // 下载eyes-free的语音数据包
//                                String ttsDataUrl = "http://eyes-free.googlecode.com/files/tts_3.1_market.apk";
//                                Uri ttsDataUri = Uri.parse(ttsDataUrl);
//                                Intent ttsIntent = new Intent(
//                                        Intent.ACTION_VIEW, ttsDataUri);
//                                startActivity(ttsIntent);
//                            }
//                        })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//        alertInstall.create().show();
//    }

}

