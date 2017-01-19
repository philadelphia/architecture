package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.ui.production_warning.di.produce_breakdown_fragment.DaggerProduceBreakdownFragmentCompnent;
import com.delta.smt.ui.production_warning.di.produce_breakdown_fragment.ProduceBreakdownFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceBreakdownFragment extends BaseFragment<ProduceBreakdownFragmentPresenter> implements ProduceBreakdownFragmentContract.View{


    @BindView(R.id.ryv_produce_breakdown)
    RecyclerView mRyvProduceBreakdown;
    private CommonBaseAdapter<ItemBreakDown> mAdapter;
    private List<ItemBreakDown> datas=new ArrayList<>();


    @Override
    protected void initData() {


        Log.i("aaa", "argument== " + ProduceWarningActivity.initLine());

        if (ProduceWarningActivity.initLine()!= null) {
            getPresenter().getItemBreakdownDatas(ProduceWarningActivity.initLine());
        }
    }

    @Override
    protected void initView() {
        mAdapter=new CommonBaseAdapter<ItemBreakDown>(getContext(),datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemBreakDown item, int position) {
                holder.setText(R.id.tv_title,item.getTitle());
                holder.setText(R.id.tv_produce_line,"产线："+item.getProduce_line());
                holder.setText(R.id.tv_word_code,"制程："+item.getMake_process());
                holder.setText(R.id.tv_material_station,"料站："+item.getMaterial_station());
                holder.setText(R.id.tv_breakdown_info,"故障信息："+item.getBreakdown_info());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemBreakDown item) {
                return R.layout.item_produce_breakdown;
            }
        };

        mRyvProduceBreakdown.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceBreakdown.setAdapter(mAdapter);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceBreakdownFragmentCompnent.builder().appComponent(appComponent)
                .produceBreakdownFragmentModule(new ProduceBreakdownFragmentModule(this))
                .build().inject(this);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_produce_breakdown;
    }



    @Override
    public void getItemBreakdownDatas(List<ItemBreakDown> itemBreakDown) {
        datas.clear();
        datas.addAll(itemBreakDown);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemBreakdownDatasFailed(String message) {
        ToastUtils.showMessage(getContext(),message);
    }


    //Activity预警广播触发事件
    @Override
    protected boolean UseEventBus() {
        return true;
    }

    //Activity预警广播触发事件处理
    @Subscribe
    public void event(ProduceWarningMessage produceWarningMessage){
        if (ProduceWarningActivity.initLine() != null) {
            getPresenter().getItemBreakdownDatas(ProduceWarningActivity.initLine());
        }
        Log.e(TAG, "event2: ");
    }
}
