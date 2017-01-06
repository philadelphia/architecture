package com.delta.smt.ui.store;

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
import com.delta.smt.entity.ItemInfo;
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
    private ItemCountdownViewAdapter mAdapter;

    private List<ItemInfo> mList = new ArrayList<>();

    @Override
    protected void initView() {
        mAdapter = new ItemCountdownViewAdapter<ItemInfo>(getActivity(), mList) {
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
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                IntentUtils.showIntent(getActivity(), WarningListActivity.class);
            }
        });
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerWarningComponent.builder().appComponent(appComponent).warningModule(new WarningModule(this)).build().inject(this);
    }

    @Subscribe
    public void event(StoreEmptyMessage message) {
        Log.e(TAG, "event: ");
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    protected boolean UseEventBus() {
        return true;
    }

    @Override
    protected void initData() {
        getPresenter().fatchWarning();
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
    public void onSucess(List<ItemInfo> wareHouses) {
        Log.i("info", "" + wareHouses.size());
        mList.clear();
        mList.addAll(wareHouses);
        EventBus.getDefault().post(new WarningInt(wareHouses.size()));
        mAdapter.notifyDataSetChanged();
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
