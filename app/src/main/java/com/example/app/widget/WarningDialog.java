package com.example.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.common.CommonBaseAdapter;
import com.example.app.common.CommonViewHolder;
import com.example.app.entity.WaringDialogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/3/9 15:27
 */


public class WarningDialog extends Dialog {


    private RecyclerView rv_warning;
    private Button bt_sure;
    private List<WaringDialogEntity> datas = new ArrayList<>();

    private Context context;
    private CommonBaseAdapter waringDialogEntityCommonBaseAdapter;
    private OnClickListener onClickListener;
    private TextView mTv_main_title;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public WarningDialog(@NonNull Context context) {
        super(context, R.style.AlertDialogCustom);
        this.context = context;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int)(d.getHeight() *0.6);
        p.width= ((int) (d.getWidth() * 0.9));
        p.dimAmount = 0.0f;
        getWindow().setAttributes(p);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
        this.setCancelable(false);
    }


    //初始化界面
    private void initView() {
        setContentView(R.layout.dialog_warning);
        mTv_main_title = (TextView) findViewById(R.id.waring_main_title);
        rv_warning = (RecyclerView) findViewById(R.id.rv_warning);
        bt_sure = (Button) findViewById(R.id.bt_sure);
    }

    public void setMainTitle(String message) {
        mTv_main_title.setText(message);
    }

    //初始化数据
    private void initData() {
        waringDialogEntityCommonBaseAdapter = new CommonBaseAdapter<WaringDialogEntity>(context, datas) {

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

    public List<WaringDialogEntity> getDatas() {
        return datas;
    }

    public void notifyData() {
        waringDialogEntityCommonBaseAdapter.notifyDataSetChanged();
    }


    //初始化时间
    private void initEvent() {
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onclick(view);
                    if(WarningDialog.this.isShowing()){
                        WarningDialog.this.dismiss();
                    }
                }
            }
        });
    }

    public interface OnClickListener {
        void onclick(View view);
    }

}
