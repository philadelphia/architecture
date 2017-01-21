package com.delta.smt.ui.hand_add.mvp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.hand_add.di.DaggerHandAddCompent;
import com.delta.smt.ui.hand_add.di.HandAddModule;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class HandAddActivity extends BaseActivity<HandAddPresenter>
        implements HandAddContract.View, WarningManger.OnWarning, ItemOnclick {



    @Inject
    WarningManger mWarningManger;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.ryv_hand_add)
    RecyclerView mRyvHandAdd;

    private AlertDialog alertDialog;
    private AlertDialog mItemDialog;

    private ItemCountdownViewAdapter<ItemHandAdd> mAdapter;
    private List<ItemHandAdd> datas = new ArrayList<>();

    DialogRelativelayout mDialogRelativelayout;
    private boolean tag = false;
    private String dialogwarningMessage;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerHandAddCompent.builder().appComponent(appComponent)
                .handAddModule(new HandAddModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {


        //设置接收哪种预警
        mWarningManger.addWarning(Constant.HAND_ADD, getClass());
        //是否接收预警 可以控制预警时机
        mWarningManger.setRecieve(true);

        mWarningManger.setOnWarning(this);

        getPresenter().getItemHandAddDatas();
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("线外人员");

        mAdapter = new ItemCountdownViewAdapter<ItemHandAdd>(this, datas) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_hand_add;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ItemHandAdd itemHandAdd, int position) {
                if(itemHandAdd.getState()==1){
                    holder.setText(R.id.tv_title, itemHandAdd.getTitle());
                    holder.setText(R.id.tv_line,"产线：" +itemHandAdd.getProduce_line());
                    holder.setText(R.id.tv_material_station, "模组料站："+itemHandAdd.getMaterial_station());
                    holder.setText(R.id.tv_realAmount,"手补件数量："+String.valueOf(itemHandAdd.getRealAmount()));
                    holder.setText(R.id.tv_message, "信息："+itemHandAdd.getInfo());

                    holder.getView(R.id.tv_realAmount).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_expectedAmount).setVisibility(View.GONE);
                }else {

                    holder.setText(R.id.tv_title, itemHandAdd.getTitle());
                    holder.setText(R.id.tv_line, "产线：" +itemHandAdd.getProduce_line());
                    holder.setText(R.id.tv_material_station, "模组料站："+itemHandAdd.getMaterial_station());
                    holder.setText(R.id.tv_expectedAmount,"预计Pass数量："+String.valueOf(itemHandAdd.getExpectedAmount()));
                    holder.setText(R.id.tv_message,  "信息："+itemHandAdd.getInfo());

                    holder.getView(R.id.tv_expectedAmount).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_realAmount).setVisibility(View.GONE);

                }


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
        mWarningManger.registerWReceiver(this);
        if (null != mAdapter) {
            mAdapter.startRefreshTime();
        }
        Log.e(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWarningManger.unregisterWReceriver(this);
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
        Log.e(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
        super.onDestroy();
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
    public void getItemHandAddDatas(List<ItemHandAdd> itemHandAdds) {
        datas.clear();
        datas.addAll(itemHandAdds);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemHandAddDatasFailed(String message) {
        ToastUtils.showMessage(this,message);
    }


    @Override
    public void warningComing(String warningMessage) {

        if (mItemDialog != null && mItemDialog.isShowing()) {
            tag = true;
            dialogwarningMessage = warningMessage;
        } else if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = createDialog(warningMessage);
        } else {
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
        if (mItemHandAdd.getState()!=1){
            mDialogRelativelayout = new DialogRelativelayout(this);
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
                                tag = false;
                            }
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            mItemHandAdd.setInfo("手补件完成，等待品管确认");
//                            mAdapter.notifyDataSetChanged();
                            Map<String,String> mMap=new HashMap<>();
                            mMap.put("id",String.valueOf(mItemHandAdd.getId()));
                            Gson mGson=new Gson();
                            String mS=mGson.toJson(mMap);
                            Log.i("HandAddActivity", mS);
                            getPresenter().getItemHandAddConfirm(mS);
                            getPresenter().getItemHandAddDatas();
                            mAdapter.notifyDataSetChanged();

                            if (tag) {
                                createDialog(dialogwarningMessage);
                                tag = false;
                            }
                        }
                    }).create();
            mItemDialog.show();
        }

    }

}
