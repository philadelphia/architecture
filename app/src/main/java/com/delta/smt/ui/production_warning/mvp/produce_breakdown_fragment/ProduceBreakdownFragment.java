package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.ui.production_warning.di.produce_breakdown_fragment.DaggerProduceBreakdownFragmentCompnent;
import com.delta.smt.ui.production_warning.di.produce_breakdown_fragment.ProduceBreakdownFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceBreakdownFragment extends BaseFragment<ProduceBreakdownFragmentPresenter> implements ProduceBreakdownFragmentContract.View{


    @BindView(R.id.ryv_produce_breakdown)
    RecyclerView mRyvProduceBreakdown;
    private ItemCountdownViewAdapter<ItemBreakDown> mAdapter;
    private List<ItemBreakDown> datas=new ArrayList<>();


    @Override
    protected void initData() {


        Log.i("aaa", "argument== " + ((ProduceWarningActivity) getmActivity()).initLine());

        if (((ProduceWarningActivity) getmActivity()).initLine()!= null) {
            getPresenter().getItemBreakdownDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }
    }

    @Override
    protected void initView() {
        mAdapter=new ItemCountdownViewAdapter<ItemBreakDown>(getContext(),datas) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_produce_breakdown;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ItemBreakDown itemBreakDown, int position) {
                holder.setText(R.id.tv_title,itemBreakDown.getTitle());
                holder.setText(R.id.tv_produce_line,"产线："+itemBreakDown.getProduce_line());
                holder.setText(R.id.tv_word_code,"制程："+itemBreakDown.getMake_process());
                holder.setText(R.id.tv_material_station,"料站："+itemBreakDown.getMaterial_station());
                holder.setText(R.id.tv_breakdown_info,"故障信息："+itemBreakDown.getBreakdown_info());
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
        if (((ProduceWarningActivity) getmActivity()).initLine() != null) {
            getPresenter().getItemBreakdownDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }
        Log.e(TAG, "event2: ");
    }
}
