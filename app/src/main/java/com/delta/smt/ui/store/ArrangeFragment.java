package com.delta.smt.ui.store;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ArrangeInt;
import com.delta.smt.entity.ItemInfo;
import com.delta.smt.entity.SentRefreshRequest;
import com.delta.smt.entity.StoreEmptyMessage;
import com.delta.smt.ui.store.di.ArrangeModule;
import com.delta.smt.ui.store.di.DaggerArrangeComponent;
import com.delta.smt.ui.store.mvp.ArrangeContract;
import com.delta.smt.ui.store.mvp.ArrangePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class ArrangeFragment extends BaseFragment<ArrangePresenter> implements ArrangeContract.View {

    @BindView(R.id.time_recycler)
    FamiliarRecyclerView timeRecycler;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private ItemCountViewAdapter mAdapter;
    private List<ItemInfo> mList = new ArrayList<>();
    private int mPosition;


    @Override
    protected void initView() {

        mAdapter = new ItemCountViewAdapter<ItemInfo>(getContext(), mList) {

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
        timeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        timeRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemTimeOnclick(new com.delta.libs.adapter.ItemOnclick() {

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
                            getPresenter().closeLights(mList.get(position).getAlarminfoId(),"1");
                            dialog.dismiss();

                        }
                    }).create();
                    mAffirmDialog.show();

                }
            }
        });
        getPresenter().fatchArrange();
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerArrangeComponent.builder().appComponent(appComponent).arrangeModule(new ArrangeModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
//        getPresenter().fatchArrange();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.time_recyclerview;
    }


    @Override
    public void onSucess(List<ItemInfo> wareHouses) {
        mList.clear();
        mList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(new ArrangeInt(wareHouses.size()));
    }

    @Override
    public void onColenSucess(String s) {

        ItemInfo itemInfo = mList.get(mPosition);
        Bundle bundle = new Bundle();
        bundle.putString("workNumber", itemInfo.getWorkNumber());
        bundle.putString("machine", itemInfo.getMaterialNumber());
        bundle.putString("materialNumber", itemInfo.getMachine());
        bundle.putInt("amout", Integer.valueOf(itemInfo.getAmount()));
        bundle.putString("alarminfoid", itemInfo.getAlarminfoId());
        bundle.putBoolean("alarminfo", itemInfo.isAlarminfo());
        bundle.putString("mainBoard", itemInfo.getMainBoard());
        bundle.putString("subBoard", itemInfo.getSubBoard());
        IntentUtils.showIntent(getActivity(), WarningListActivity.class, bundle);
    }

    @Subscribe
    public void event(StoreEmptyMessage message) {
        getPresenter().fatchArrange();
        Log.e(TAG, "event: ");
    }
    @Subscribe
    public void event(SentRefreshRequest message) {
        getPresenter().fatchArrange();
        Log.e(TAG, "event: ");
    }

    @Override
    protected boolean UseEventBus() {
        return true;
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
                getPresenter().fatchArrange();
            }
        });

    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
    }

}
