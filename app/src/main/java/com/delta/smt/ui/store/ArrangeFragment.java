package com.delta.smt.ui.store;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.ItemTimeAdapter;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ItemInfo;
import com.delta.smt.ui.store.di.ArrangeModule;
import com.delta.smt.ui.store.di.DaggerArrangeComponent;
import com.delta.smt.ui.store.mvp.ArrangeContract;
import com.delta.smt.ui.store.mvp.ArrangePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class ArrangeFragment extends BaseFragment<ArrangePresenter> implements ArrangeContract.View{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private ItemTimeAdapter mAdapter;
    private List<ItemInfo>mList=new ArrayList<>();
    @Override
    protected void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter=new ItemTimeAdapter(getActivity(),mList);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemTimeOnclck(new ItemOnclick() {
            @Override
            public void onItemClick(View item, int position) {
                Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();
                IntentUtils.showIntent(getActivity(),WarningListActivity.class);
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
        return R.layout.main_list;
    }


    @Override
    public void onSucess(List<ItemInfo> wareHouses) {
        mList.clear();
        mList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }
}
