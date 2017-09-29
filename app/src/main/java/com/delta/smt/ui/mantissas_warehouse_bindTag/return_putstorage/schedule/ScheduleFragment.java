package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.libs.adapter.ItemTimeViewHolder;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ScheduleResult;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.MantissaWarehouseReturnDetailsActivity;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.di.DaggerScheduleComponent;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.di.ScheduleModule;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.mvp.ScheduleContract;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.mvp.SchedulePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.delta.smt.R.id.statusLayout;

/**
 * Created by Zhenyu.Liu on 2017/9/26.
 */

public class ScheduleFragment extends BaseFragment<SchedulePresenter>
        implements ScheduleContract.View, ItemOnclick<ScheduleResult.Schedule>{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(statusLayout)
    StatusLayout mStatusLayout;

    private List<ScheduleResult.Schedule> dataList = new ArrayList();
    private ItemCountViewAdapter<ScheduleResult.Schedule> adapter;


    @Override
    protected void initData() {
        getPresenter().getScheduleList();
    }

    @Override
    protected void initView() {

        adapter = new ItemCountViewAdapter<ScheduleResult.Schedule>(getContext(), dataList) {

            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.fragment_schedule;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ScheduleResult.Schedule item, int position) {
                holder.setText(R.id.tv_title, "工单号: " + item.getWork_order());
                holder.setText(R.id.tv_line, "线别: " + item.getLine_name());
                holder.setText(R.id.tv_material_station, "面别: " + item.getSide());
            }
        };
        adapter.setOnItemTimeOnclick(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

    }



    @Override
    public void getScheduleSuccess(List<ScheduleResult.Schedule> scheduleResults) {
        dataList.clear();

        for (int i = 0; i < scheduleResults.size(); i++) {
            scheduleResults.get(i).setEnd_time(Math.round(scheduleResults.get(i).getRemain_time() * 1000) + System.currentTimeMillis());
            scheduleResults.get(i).setEntityId(i);
        }
        
        dataList.addAll(scheduleResults);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getScheduleFailed(String message) {

    }

    @Override
    public void showLoadingView() {
        mStatusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        mStatusLayout.showContentView();
    }

    @Override
    public void showEmptyView() {
        mStatusLayout.showErrorView();
        mStatusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   getPresenter().getScheduleList();
            }
        });
    }


    @Override
    public void showErrorView() {
        mStatusLayout.showErrorView();
        mStatusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  getPresenter().getScheduleList();
            }
        });
    }


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerScheduleComponent.builder().appComponent(appComponent).scheduleModule(new ScheduleModule(this)).build().inject(this);
    }



    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_ready_rec;
    }


    @Override
    public void onItemClick(View item, ScheduleResult.Schedule schedule, int position) {
        Intent intent = new Intent(getActivity(), MantissaWarehouseReturnDetailsActivity.class);
        intent.putExtra(Constant.WORK_ORDER, dataList.get(position).getWork_order());
        intent.putExtra(Constant.SIDE, dataList.get(position).getSide());
        startActivity(intent);
    }
}
