package com.delta.smt.service.warningService;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.ActivityMonitor;
import com.delta.smt.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarningActivity extends AppCompatActivity {

    private RecyclerView rv_warning;
    private Button bt_sure;
    private List<WaringDialogEntity> datas = new ArrayList<>();
    private static final String TAG = "WarningActivity";
    private Context context;
    private CommonBaseAdapter waringDialogEntityCommonBaseAdapter;
    private WarningDialog.OnClickListener onClickListener;
    private String message="";
    private Map<String, String> title_type = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
        initView();
        initData();
        initEvent();

//        ListView listView = (ListView) findViewById(R.id.lv);
//        listView.setAdapter(new DialogListAdapter(WarningActivity.this));
////        listView.setAlpha((float)0.2);
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        LinearLayout layout = (LinearLayout) findViewById(R.id.list_container);
//        layout.setPadding(metrics.widthPixels/20,metrics.heightPixels/4,metrics.widthPixels/20,0);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e(TAG, "onNewIntent: ");
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: ");
        datas.clear();
        message = getIntent().getStringExtra(Constant.WARNINGMESSAGE);
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("");
        String content = "";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object message1 = jsonObject.get("message");
                content = content + message1 + "\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        warningEntity.setContent(content + "\n");
        datas.add(warningEntity);
        notifyData();
        super.onResume();
    }

    //初始化界面
    private void initView() {

        setContentView(R.layout.dialog_warning);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        LinearLayout layout = (LinearLayout) findViewById(R.id.container);
        layout.setPadding(metrics.widthPixels/20,metrics.heightPixels/6,metrics.widthPixels/20,metrics.heightPixels/6);
        rv_warning = (RecyclerView) findViewById(R.id.rv_warning);
        bt_sure = (Button) findViewById(R.id.bt_sure);
    }

    //初始化数据
    private void initData() {

        waringDialogEntityCommonBaseAdapter = new CommonBaseAdapter<WaringDialogEntity>(this, datas) {

            @Override
            protected void convert(CommonViewHolder holder, WaringDialogEntity item, int position) {

                holder.setText(R.id.tv_sub_title, item.getTitle());
                holder.setText(R.id.tv_content, item.getContent());
            }

            @Override
            protected int getItemViewLayoutId(int position, WaringDialogEntity item) {
                return R.layout.dialog_warning_item;
            }
        };
        rv_warning.setLayoutManager(new LinearLayoutManager(context));
        rv_warning.setAdapter(waringDialogEntityCommonBaseAdapter);
    }
    public void notifyData() {
        waringDialogEntityCommonBaseAdapter.notifyDataSetChanged();
    }

    //初始化时间
    private void initEvent() {
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WarningActivity.this, ActivityMonitor.getInstance().getPenultActivity().getClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                WarningActivity.this.finish();

            }
        });
    }
    public interface OnClickListener {
        void onclick(View view);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (true) {
            final View view = getWindow().getDecorView();
            final WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
            lp.gravity = Gravity.CENTER;
//            lp.width = getResources().getDisplayMetrics().widthPixels / 2;
//            lp.height = getResources().getDisplayMetrics().heightPixels / 3;
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(new BitmapDrawable(getWallPaper()));
            } else {
                view.setBackgroundColor(Color.WHITE);
            }
            getWindowManager().updateViewLayout(view, lp);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public Bitmap getWallPaper() {
        WallpaperManager wallpaperManager = WallpaperManager
                .getInstance(WarningActivity.this);
        // 获取当前壁纸
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();

        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int with = bm.getHeight() * widthPixels / heightPixels > bm.getWidth() ? bm.getWidth() : bm.getHeight() * widthPixels / heightPixels;
        Bitmap pbm = Bitmap.createBitmap(bm, 0, 0, with, bm.getHeight());
        // 设置 背景
        return pbm;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
