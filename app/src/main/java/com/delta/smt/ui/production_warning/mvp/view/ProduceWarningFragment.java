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
import com.delta.smt.ui.production_warning.di.produce_warning_fragment.DaggerProduceWarningFragmentCompnent;
import com.delta.smt.ui.production_warning.di.produce_warning_fragment.ProduceWarningFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningFragment extends BaseFragment<ProduceWarningFragmentPresenter> implements ProduceWarningFragmentContract.View{


    @BindView(R.id.ryv_produce_warning)
    RecyclerView mRyvProduceWarning;

    private CommonBaseAdapter<ItemWarningInfo> mAdapter;
    private  List<ItemWarningInfo> datas=new ArrayList<>();

    @Override
    protected void initView() {
        mAdapter=new CommonBaseAdapter<ItemWarningInfo>(getContext(),datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemWarningInfo item, int position) {
                holder.setText(R.id.tv_title,item.getTitle());
                holder.setText(R.id.tv_produce_line,item.getProductionline());
                holder.setText(R.id.tv_make_process,item.getMakeprocess());
                holder.setText(R.id.tv_warning_info,item.getWarninginfo());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemWarningInfo item) {
                return R.layout.item_produce_warning;
            }
        };
        mRyvProduceWarning.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceWarning.setAdapter(mAdapter);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceWarningFragmentCompnent.builder().appComponent(appComponent).
                produceWarningFragmentModule(new ProduceWarningFragmentModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getItemWarningDatas();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_produce_warning;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void getItemWarningDatas(List<ItemWarningInfo> itemWarningInfo) {
        datas.clear();
        datas.addAll(itemWarningInfo);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemWarningDatasFailed() {

    }
}
