package com.delta.smt.ui.hand_add.mvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.delta.smt.Constant;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.hand_add.di.DaggerHandAddCompent;
import com.delta.smt.ui.hand_add.di.HandAddModule;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class HandAddActivity extends BaseActiviy<HandAddPresenter>
        implements HandAddContract.View,WarningManger.OnWarning, ItemOnclick {

    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.ryv_hand_add)
    RecyclerView mRyvHandAdd;

    @Inject
    WarningManger mWarningManger;

    private AlertDialog alertDialog;
    private AlertDialog mItemDialog;

    private ItemCountdownViewAdapter<ItemHandAdd> mAdapter;
    private List<ItemHandAdd> datas=new ArrayList<>();

    DialogRelativelayout mDialogRelativelayout;
    private boolean tag=false;
    private String dialogwarningMessage;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerHandAddCompent.builder().appComponent(appComponent)
                .handAddModule(new HandAddModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        //设置接收哪种预警
        mWarningManger.addWarning(Constant.HAND_ADD,getClass());
        //是否接收预警 可以控制预警时机
        mWarningManger.setRecieve(true);

        mWarningManger.setOnWarning(this);

        getPresenter().getItemHandAddDatas();
    }

    @Override
    protected void initView() {
        mHeaderTitle.setText("线外人员");
        mAdapter=new ItemCountdownViewAdapter<ItemHandAdd>(this,datas) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_hand_add;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ItemHandAdd itemHandAdd, int position) {
                holder.setText(R.id.tv_title,itemHandAdd.getTitle());
                holder.setText(R.id.tv_line,itemHandAdd.getProduce_line());
                holder.setText(R.id.tv_add_count,itemHandAdd.getAdd_count());
                holder.setText(R.id.tv_material_station,itemHandAdd.getMaterial_station());
                holder.setText(R.id.tv_warning_info,itemHandAdd.getInfo());
            }


        };
        mRyvHandAdd.setLayoutManager(new LinearLayoutManager(this));
        mRyvHandAdd.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemTimeOnclck(this);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_hand_add;
    }

    @Override
    protected void onResume() {
        mWarningManger.registWReceiver(this);
        if (null != mAdapter) {
            mAdapter.startRefreshTime();
        }
        Log.e(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWarningManger.unregistWReceriver(this);
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
        Log.e(TAG, "onPause: " );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop: " );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
        super.onDestroy();
    }

    @OnClick({R.id.header_back, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.header_setting:
                break;
        }
    }

    @Override
    public void getItemHandAddDatas(List<ItemHandAdd> itemHandAdds) {
        datas.clear();
        datas.addAll(itemHandAdds);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemHandAddDatasFailed() {

    }



    @Override
    public void warningComming(String warningMessage) {

            if(mItemDialog !=null&& mItemDialog.isShowing()){
                tag=true;
                dialogwarningMessage=warningMessage;
            }else if (alertDialog!=null&&alertDialog.isShowing()){
                alertDialog.dismiss();
                alertDialog = createDialog(warningMessage);
            }else {
                alertDialog = createDialog(warningMessage);
            }

    }

    private AlertDialog createDialog(String warningMessage) {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警提示");
        //传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datas);
        return new AlertDialog.Builder(this).setCancelable(false).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().getItemHandAddDatas();
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onItemClick(final View item, int position) {
        final ItemHandAdd mItemHandAdd = datas.get(position);
        mDialogRelativelayout=new DialogRelativelayout(this);
        mDialogRelativelayout.setStrSecondTitle("请求确认");
        final ArrayList<String> datas = new ArrayList<>();
        datas.add("手补件完成？");
        mDialogRelativelayout.setStrContent(datas);
        mItemDialog = new AlertDialog.Builder(this).setCancelable(false).setView(mDialogRelativelayout)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (tag) {
                            createDialog(dialogwarningMessage);
                            tag=false;
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mItemHandAdd.setInfo("预警信息：手补件完成，等待品管确认");
                        mAdapter.notifyDataSetChanged();
                        if (tag) {
                            createDialog(dialogwarningMessage);
                            tag=false;
                        }
                    }
                }).create();
        mItemDialog.show();
    }
}
