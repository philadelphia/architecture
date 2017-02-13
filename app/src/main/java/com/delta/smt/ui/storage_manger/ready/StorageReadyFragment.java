package com.delta.smt.ui.storage_manger.ready;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.ui.storage_manger.details.StorageDetailsActivity;
import com.delta.smt.ui.storage_manger.ready.di.DaggerStorageReadyComponent;
import com.delta.smt.ui.storage_manger.ready.di.StorageReadyModule;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyContract;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.delta.smt.R.id.recyclerView;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageReadyFragment extends BaseFragment<StorageReadyPresenter>
        implements StorageReadyContract.View, com.delta.libs.adapter.ItemOnclick {
    @BindView(recyclerView)
    RecyclerView mRecyclerView;

    private List<StorageReady> dataList = new ArrayList();
    private ItemCountViewAdapter<StorageReady> adapter;
    private String wareHosueName;

    @Override
    protected void initView() {

        adapter = new ItemCountViewAdapter<StorageReady>(getContext(), dataList) {

            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.fragment_storage_ready;
            }

            @Override
            protected void convert(com.delta.libs.adapter.ItemTimeViewHolder holder, StorageReady item, int position) {
                holder.setText(R.id.tv_title, "产线: " + item.getLine_name());
                holder.setText(R.id.tv_line, "工单号: " + item.getWork_order());
                holder.setText(R.id.tv_material_station, "面别: " + item.getSide());
                if (item.getStatus() == 1) {
                    holder.setText(R.id.tv_add_count, "状态：未开始发料");
                } else {
                    holder.setText(R.id.tv_add_count, "状态：正在发料");
                }
            }
        };
        adapter.setOnItemTimeOnclick(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerStorageReadyComponent.builder().appComponent(appComponent).storageReadyModule(new StorageReadyModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        wareHosueName = getArguments().getString(Constant.WARE_HOUSE_NAME);
        Map<String, String> map = new HashMap<>();
        map.put("part", wareHosueName);
        Gson gson = new Gson();
        String mS = gson.toJson(map);
        Log.i("aaa", "argument== " + mS);
        getPresenter().getStorageReady(mS);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_ready_rec;
    }


    @Override
    public void getStorageReadySucess(List<StorageReady> storageReadies) {
        dataList.clear();
        for (StorageReady storageReady : storageReadies) {
            storageReady.setEnd_time(storageReady.getRemain_time() + System.currentTimeMillis());
        }
        dataList.addAll(storageReadies);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void getStorageReadyFailed(String message) {

        ToastUtils.showMessage(getActivity(), message);
    }

    @Override
    public void onItemClick(View item, Object o, int position) {
        Intent intent = new Intent(getActivity(), StorageDetailsActivity.class);
        intent.putExtra("work_order", dataList.get(position).getWork_order());
        intent.putExtra(Constant.WARE_HOUSE_NAME,wareHosueName);
        startActivity(intent);
    }
}
