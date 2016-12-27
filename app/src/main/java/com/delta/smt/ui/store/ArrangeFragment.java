package com.delta.smt.ui.store;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.ItemTimeAdapter;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ItemInfo;
import com.delta.smt.ui.store.mvp.ArrangePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class ArrangeFragment extends BaseFragment<ArrangePresenter> {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private ItemTimeAdapter mAdapter;
    private List<ItemInfo>mList;
    @Override
    protected void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter=new ItemTimeAdapter(getActivity(),mList);
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        mList=new ArrayList<>();
        for (int i=0;i<10;i++){
            ItemInfo item=new ItemInfo();
            //TODO  控件有问题
            item.setText("产线:H"+i+"\n"+"工单号:24561215"+i+"\n"+"PCB料号：457485645"+i+"\n"+"机种：H123-"+i+"需求量："+50+"\n"+"状态:"+"备料");
            item.setCountdown(900000);
            item.setEndTime(900000);
            mList.add(item);
        }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.main_list;
    }


}
