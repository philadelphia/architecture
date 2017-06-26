package com.delta.smt.ui.store;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ItemInfo;
import com.delta.smt.entity.SentRefreshRequest;
import com.delta.smt.entity.StoreEmptyMessage;
import com.delta.smt.entity.WarningInt;
import com.delta.smt.ui.store.di.DaggerWarningComponent;
import com.delta.smt.ui.store.di.WarningModule;
import com.delta.smt.ui.store.mvp.WarningContract;
import com.delta.smt.ui.store.mvp.WarningPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class WarringFragment extends BaseFragment<WarningPresenter> implements WarningContract.View {


    @BindView(R.id.time_recycler)
    FamiliarRecyclerView timeRecycler;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private ItemCountViewAdapter mAdapter;


    private List<ItemInfo> mList = new ArrayList<>();
    private int mPosition;

    @Override
    protected void initView() {

        mAdapter = new ItemCountViewAdapter<ItemInfo>(getActivity(), mList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_base;
            }

            @Override
            protected void convert(com.delta.libs.adapter.ItemTimeViewHolder holder, ItemInfo itemInfo, int position) {
                holder.setText(R.id.content_text,itemInfo.getText());
            }

        };
        timeRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemTimeOnclick(new ItemOnclick() {

            @Override
            public void onItemClick(View item, Object o, final int position) {
                if (mList.size() == 0) {

                } else {
                    AlertDialog mAffirmDialog=new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("是否确认备料?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPosition=position;
                            getPresenter().closeLights(mList.get(position).getAlarminfoId(),0);
                            dialog.dismiss();

                        }
                    }).create();
                    mAffirmDialog.show();


                }
            }
        });
      getPresenter().fatchWarning();
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerWarningComponent.builder().appComponent(appComponent).warningModule(new WarningModule(this)).build().inject(this);
    }

    @Subscribe
    public void event(StoreEmptyMessage message)
    {getPresenter().fatchWarning();
        Log.e(TAG, "event: ");
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @Subscribe
    public void event(SentRefreshRequest message) {
        getPresenter().fatchWarning();
        Log.e(TAG, "event: ");
    }

    @Override
    protected boolean UseEventBus() {
        return true;
    }

    @Override
    protected void initData() {

//        mList=new ArrayList<>();
//        for (int i=0;i<10;i++){
//            ItemInfo item=new ItemInfo();
//            //TODO  控件有问题
//            item.setText("产线:H"+i+"\n"+"工单号:24561215"+i+"\n"+"PCB料号：457485645"+i+"\n"+"机种：H123-"+i+"需求量："+50+"\n"+"状态:"+"备料");
//            item.setCountdown(900000);
//            item.setEndTime(900000);
//            mList.add(item);
//        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.time_recyclerview;
    }

    @Override
    public void onSucess(List<com.delta.smt.entity.ItemInfo> wareHouses) {
//        Log.i("info", "" + wareHouses.size());
         mList.clear();
         mList.addAll(wareHouses);
        EventBus.getDefault().post(new WarningInt(wareHouses.size()));
        mAdapter.notifyDataSetChanged();
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getAlarminfoId()==1&&"2016980001".equals(mList.get(i).getWorkNumber())&&"50".equals(mList.get(i).getAmount())) {
                ItemInfo productWorkItem = mList.get(i);
                mList.remove(i);
                mList.add(1, productWorkItem);
            }
        }
    }

    @Override
    public void onColenSucess(String s) {

        ItemInfo itemInfo = mList.get(mPosition);
        Bundle bundle = new Bundle();
        bundle.putString("workNumber", itemInfo.getWorkNumber());
        bundle.putString("machine", itemInfo.getMaterialNumber());
        bundle.putString("materialNumber", itemInfo.getMachine());
        bundle.putInt("amout", Integer.valueOf(itemInfo.getAmount()));
        bundle.putInt("alarminfoid", itemInfo.getAlarminfoId());
        bundle.putBoolean("alarminfo", itemInfo.isAlarminfo());
        bundle.putString("mainBoard", itemInfo.getMainBoard());
        bundle.putString("subBoard", itemInfo.getSubBoard());
        IntentUtils.showIntent(getActivity(), WarningListActivity.class, bundle);
    }


    @Override
    public void onFailed(String s) {
        ToastUtils.showMessage(getActivity(),s);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (null != mAdapter) {
            mAdapter.startRefreshTime();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mAdapter) {
            mAdapter.cancelRefreshTime();
        }
    }
    @Override
    public void showLoadingView() {
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().fatchWarning();
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
    }

}
