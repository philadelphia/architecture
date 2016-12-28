package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

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
import com.delta.smt.ui.production_warning.di.produce_info_fragment.DaggerProduceInfoFragmentCompent;
import com.delta.smt.ui.production_warning.di.produce_info_fragment.ProduceInfoFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.item.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceInfoFragment extends BaseFragment<ProduceInfoFragmentPresenter>
    implements ProduceInfoFragmentContract.View, CommonBaseAdapter.OnItemClickListener<ItemInfo> {


    @BindView(R.id.ryv_produce_info)
    RecyclerView mRyvProduceInfo;
    private CommonBaseAdapter<ItemInfo> mAdapter;
    private List<ItemInfo> datas=new ArrayList<>();

    @Override
    protected void initData() {
        getPresenter().getItemInfoDatas();
    }

    @Override
    protected void initView() {
        mAdapter=new CommonBaseAdapter<ItemInfo>(getContext(),datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemInfo item, int position) {
                holder.setText(R.id.tv_title,item.getTitle());
                holder.setText(R.id.tv_produce_line,item.getProduceline());
                holder.setText(R.id.tv_info,item.getInfo());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemInfo item) {
                return R.layout.item_produce_info;
            }
        };
        mRyvProduceInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceInfo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceInfoFragmentCompent.builder().appComponent(appComponent)
                .produceInfoFragmentModule(new ProduceInfoFragmentModule(this))
                .build().inject(this);
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

    @Override
    public void getItemInfoDatas(List<ItemInfo> itemInfos) {
        datas.clear();
        datas.addAll(itemInfos);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemInfoDatasFailed() {

    }


    @Override
    public void onItemClick(View view, ItemInfo item, int position) {
        item.setInfo("dfsafa");
        mAdapter.notifyDataSetChanged();
    }
}
