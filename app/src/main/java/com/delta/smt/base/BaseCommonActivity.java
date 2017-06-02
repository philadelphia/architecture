package com.delta.smt.base;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.app.App;
import com.delta.smt.entity.EventNothing;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @description :不带Presenter的Activity
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/14 11:24
 */


public abstract class BaseCommonActivity extends SupportActivity {
    public static final String ACTION_RECEIVER_ACTIVITY = "com.delta.smt";
    private static final String IS_NOT_ADD_ACTIVITY_LIST = "is_add_activity_list";//是否加入到activity的list，管理
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    private String TAG = getClass().getSimpleName();
    private BroadcastReceiver mBroadcastReceiver;
    private BaseApplication application;
    private Unbinder bind;


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = ((BaseApplication) getApplication());
        boolean isNotAdd = getIntent().getBooleanExtra(IS_NOT_ADD_ACTIVITY_LIST, false);
        synchronized (BaseCommonActivity.class) {
            if (!isNotAdd)
                application.getActivityList().add(this);
        }
        if (UseEventBus()) {
            EventBus.getDefault().register(this);
        }
        initWindow();
        setContentView(getContentViewId());
        bind = ButterKnife.bind(this);
        initCData();
        initCView();
    }

    protected void initWindow() {
    }

    public boolean UseEventBus() {
        return false;
    }

    /**
     * 不添加在注册过的时候发送事件回报its super classes have no public methods with the @Subscribe annotation
     *
     * @param event
     */
    @Subscribe
    public void recieve(EventNothing event) {


    }

    protected abstract void initCView();

    protected abstract void initCData();


    public BaseApplication getMApplication() {
        return application;
    }


    @Override
    protected void onResume() {
        super.onResume();
        registReceiver();//注册广播
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregistReceriver();
    }

    @Override
    protected void onDestroy() {
        synchronized (BaseCommonActivity.class) {
            application.getActivityList().remove(this);
        }
        if (bind != Unbinder.EMPTY) {
            bind.unbind();
        }
        if (UseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    protected abstract int getContentViewId();

    /**
     * 注册广播
     */
    public void registReceiver() {
        try {
            mBroadcastReceiver = new ActivityReceriver();
            IntentFilter filter = new IntentFilter(ACTION_RECEIVER_ACTIVITY);
            registerReceiver(mBroadcastReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解除注册广播
     */
    public void unregistReceriver() {
        if (mBroadcastReceiver == null) return;
        try {
            unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    /**
     * 用于处理当前activity需要
     */
    class ActivityReceriver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getStringExtra("type")) {
                    case "startActivity"://启动activity
                        Intent content = intent.getExtras().getParcelable("content");
                        startActivity(content);
                        break;
                    case "showSnackbar"://显示snackbar
                        String text = intent.getStringExtra("content");
                        boolean isLong = intent.getBooleanExtra("long", false);
                        View view = BaseCommonActivity.this.getWindow().getDecorView().findViewById(android.R.id.content);
                        Snackbar.make(view, text, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
                        break;
                    case "handError"://处理错误
                        String contents = intent.getStringExtra("content");
                        handError(contents);
                        break;
                    case "showToast":
                        String toastText = intent.getStringExtra("content");
                        if (intent.getBooleanExtra("long", false)) {
                            ToastUtils.showMessageLong(BaseCommonActivity.this, toastText);
                        } else {
                            ToastUtils.showMessage(BaseCommonActivity.this, toastText);
                        }
                        break;
                    case "killAll":
                        LinkedList<BaseCommonActivity> copy;
                        synchronized (BaseCommonActivity.class) {
                            copy = new LinkedList<>(application.getActivityList());
                        }
                        for (BaseCommonActivity baseActivity : copy) {
                            baseActivity.finish();
                        }
                        //		android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                }
            }
        }
    }

    // 处理错误
    protected void handError(String contents) {

    }


    //判断Activity是否在栈顶
    public boolean isTaskTop(Context context, String currentActivityName) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = am.getRunningTasks(1);
        String topActivityName = null;
        if (null != runningTaskInfoList) {
            topActivityName = (runningTaskInfoList.get(0).topActivity).getClassName();
        }
        if (TextUtils.isEmpty(topActivityName))
            return false;
        if (TextUtils.isEmpty(currentActivityName))
            throw new IllegalArgumentException();
        return topActivityName.equals(currentActivityName);
    }

}
