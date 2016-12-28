package com.delta.smt.ui.feeder.warning;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.Constant;
import com.delta.smt.ui.feeder.handle.feederCheckIn.FeederCheckInActivity;
import com.delta.smt.ui.feeder.warning.checkin.di.CheckInModule;
import com.delta.smt.ui.feeder.warning.checkin.di.DaggerCheckInComponent;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInContract;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CheckinFragment extends BaseFragment<CheckInPresenter> implements CheckInContract.View ,CommonBaseAdapter.OnItemClickListener<FeederSupplyWarningItem> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final String TAG = "CheckinFragment";
    private List<FeederSupplyWarningItem> dataList = new ArrayList();
    private CommonBaseAdapter<FeederSupplyWarningItem> adapter;
    @Override
    protected void initView() {
        adapter = new CommonBaseAdapter<FeederSupplyWarningItem>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyWarningItem item, int position) {
                holder.setText(R.id.tv_title, "线别: " + String.valueOf(item.getLineNumber()));
                holder.setText(R.id.tv_line, "工单号: " + item.getWorkItemID());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFaceID());
                holder.setText(R.id.tv_add_count, "状态: " + item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyWarningItem item) {
                return R.layout.feeder_supply_list_item;
            }
        };

        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerCheckInComponent.builder().appComponent(appComponent).checkInModule(new CheckInModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: ");
        getPresenter().getAllCheckedInFeeders();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_checkin;
    }

    @Override
    public void onSuccess(List<FeederSupplyWarningItem> datas) {
        dataList.clear();
        dataList.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onItemClick(View view, FeederSupplyWarningItem item, int position) {
        Log.i(TAG, "onItemClick: ");
        Log.i(TAG, "onItemClick: " + view.getClass().getSimpleName() + position);
        String workItemID = item.getWorkItemID();
//        Bundle bundle = new Bundle();
        IntentUtils.showIntent(getmActivity(), FeederCheckInActivity.class,new String[]{Constant.WORK_ITEM_ID},new String[]{workItemID});
    }
}
