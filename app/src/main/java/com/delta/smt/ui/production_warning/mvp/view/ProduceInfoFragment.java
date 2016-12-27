package com.delta.smt.ui.production_warning.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.item.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceInfoFragment extends BaseFragment<ProduceInfoFragmentPresenter> {


    @BindView(R.id.ryv_produce_info)
    RecyclerView mRyvProduceInfo;
    private CommonBaseAdapter<ItemInfo> mAdapter;
    private List<ItemInfo> datas=new ArrayList<>();

    @Override
    protected void initData() {
        datas.add(new ItemInfo("锡膏配送中","产线：H13","消息：锡膏即将配送到产线，请确认"));
        datas.add(new ItemInfo("替换钢网配送中","产线：H13","消息：替换钢网配送产线，请确认"));
    }

    @Override
    protected void initView() {
        mAdapter=new CommonBaseAdapter<ItemInfo>(getContext(),datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemInfo item, int position) {

            }

            @Override
            protected int getItemViewLayoutId(int position, ItemInfo item) {
                return R.layout.item_produce_info;
            }
        };
        mRyvProduceInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceInfo.setAdapter(mAdapter);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }



    @Override
    protected int getContentViewId() {
        return R.layout.fragment_produce_info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
