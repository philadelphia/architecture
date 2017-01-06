package com.delta.smt.ui.store;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ArrangeInt;
import com.delta.smt.entity.ItemInfo;
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
    private ItemCountdownViewAdapter mAdapter;
    private List<ItemInfo> mList = new ArrayList<>();

    @Override
    protected void initView() {
        mAdapter = new ItemCountdownViewAdapter<ItemInfo>(getContext(), mList) {

            @Override
            protected int getLayoutId() {
                return R.layout.item_base;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ItemInfo itemInfo, int position) {
                holder.setText(R.id.content_text,itemInfo.getText());
            }
        };
        timeRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemTimeOnclck(new ItemOnclick() {
            @Override
            public void onItemClick(View item, int position) {
                if (mList.size() == 0) {

                } else {
                    ItemInfo itemInfo = mList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("workNumber", itemInfo.getWorkNumber());
                    bundle.putString("machine", itemInfo.getMachine());
                    bundle.putString("materialNumber", itemInfo.getMaterialNumber());
                    Log.i("info ----", itemInfo.getWorkNumber());
                    Log.i("info ----", itemInfo.getMachine());
                    Log.i("info ----", itemInfo.getMaterialNumber());
                    Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                    IntentUtils.showIntent(getActivity(), WarningListActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerArrangeComponent.builder().appComponent(appComponent).arrangeModule(new ArrangeModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().fatchArrange();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.time_recyclerview;
    }


    @Override
    public void onSucess(List<ItemInfo> wareHouses) {
        mList.clear();
        mList.addAll(wareHouses);
        EventBus.getDefault().post(new ArrangeInt(wareHouses.size()));
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void event(StoreEmptyMessage message) {
        getPresenter().fatchArrange();
        Log.e(TAG, "event: ");
    }

    @Override
    protected boolean UseEventBus() {
        return true;
    }

    @Override
    public void onFailed() {

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

}
