package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemTimeViewHolder;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.ui.production_warning.di.produce_breakdown_fragment.DaggerProduceBreakdownFragmentCompnent;
import com.delta.smt.ui.production_warning.di.produce_breakdown_fragment.ProduceBreakdownFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceBreakdownFragment extends BaseFragment<ProduceBreakdownFragmentPresenter> implements ProduceBreakdownFragmentContract.View, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.ryv_produce_breakdown)
    RecyclerView mRyvProduceBreakdown;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout mSrfRefresh;
    private ItemCountViewAdapter<ItemBreakDown> mAdapter;
    private List<ItemBreakDown> datas = new ArrayList<>();


    @Override
    protected void initData() {


        Log.i("aaa", "argument== " + ((ProduceWarningActivity) getmActivity()).initLine());

        if (((ProduceWarningActivity) getmActivity()).initLine() != null) {
            getPresenter().getItemBreakdownDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }
    }

    @Override
    protected void initView() {
        mAdapter = new ItemCountViewAdapter<ItemBreakDown>(getContext(), datas) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_produce_breakdown;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ItemBreakDown itemBreakDown, int position) {
                holder.setText(R.id.tv_title, itemBreakDown.getTitle());
                holder.setText(R.id.tv_produce_line, "产线：" + itemBreakDown.getProduce_line());
                holder.setText(R.id.tv_word_code, "制程：" + itemBreakDown.getMake_process());
                holder.setText(R.id.tv_material_station, "料站：" + itemBreakDown.getMaterial_station());
                holder.setText(R.id.tv_breakdown_info, "故障信息：" + itemBreakDown.getBreakdown_info());
            }


        };

        mRyvProduceBreakdown.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceBreakdown.setAdapter(mAdapter);
        mSrfRefresh.setOnRefreshListener(this);
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

        for (int i = 0; i < itemBreakDown.size(); i++) {
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            try {
                Date parse = format.parse(itemBreakDown.get(i).getCreateTime());
                itemBreakDown.get(i).setCreat_time(parse.getTime());
                itemBreakDown.get(i).setEntityId(i);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        datas.addAll(itemBreakDown);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemBreakdownDatasFailed(String message) {
        if ("Error".equals(message)) {
            Snackbar.make(getActivity().getCurrentFocus(), this.getString(R.string.server_error_message), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getActivity().getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
        }
    }


    //Activity预警广播触发事件
    @Override
    protected boolean UseEventBus() {
        return true;
    }

    //Activity预警广播触发事件处理
    @Subscribe
    public void event(ProduceWarningMessage produceWarningMessage) {
        if (((ProduceWarningActivity) getmActivity()).initLine() != null) {
            getPresenter().getItemBreakdownDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }
        Log.e(TAG, "event2: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.startRefreshTime();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.cancelRefreshTime();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getPresenter().getItemBreakdownDatas(((ProduceWarningActivity) getmActivity()).initLine());
                mSrfRefresh.setRefreshing(false);
            }
        });
    }
}
