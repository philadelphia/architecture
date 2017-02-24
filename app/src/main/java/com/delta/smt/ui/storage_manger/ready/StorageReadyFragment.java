package com.delta.smt.ui.storage_manger.ready;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.libs.adapter.ItemTimeViewHolder;
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
import com.delta.smt.utils.ViewUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.delta.smt.R.id.recyclerView;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageReadyFragment extends BaseFragment<StorageReadyPresenter>
        implements StorageReadyContract.View, ItemOnclick<StorageReady> {
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;

    private List<StorageReady> dataList = new ArrayList();
    private ItemCountViewAdapter<StorageReady> adapter;
    private String wareHouseName;
    private boolean isSending = false;
    private String mS;

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
            protected void convert(ItemTimeViewHolder holder, StorageReady item, int position) {
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
    public void onResume() {
        super.onResume();
        getPresenter().getStorageReady(mS);
        isSending = false;
    }

    @Override
    protected void initData() {

        wareHouseName = getArguments().getString(Constant.WARE_HOUSE_NAME);
        Map<String, String> map = new HashMap<>();
        map.put("part", wareHouseName);
        Gson gson = new Gson();
        mS = gson.toJson(map);
        Log.i("aaa", "argument== " + mS);
        statusLayout.showLoadingView();


    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_ready_rec;
    }


    @Override
    public void getStorageReadySucess(List<StorageReady> storageReadies) {
        ViewUtils.showContentView(statusLayout, storageReadies);
        dataList.clear();
        for (int i = 0; i < storageReadies.size(); i++) {
            storageReadies.get(i).setEnd_time(storageReadies.get(i).getRemain_time() * 1000 + System.currentTimeMillis());
            storageReadies.get(i).setEntityId(i);
            if (storageReadies.get(i).getStatus() == 0) {
                Collections.swap(storageReadies, 0, i);
                isSending = true;
            }
        }
        dataList.addAll(storageReadies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getStorageReadyFailed(String message) {


    }

    @Override
    public void onItemClick(View item, StorageReady storageReady, int position) {
        if (storageReady.getStatus() == 1 && isSending == true) {
            SnackbarUtil.showMassage(mRecyclerView, Constant.FAILURE_START_ISSUE_STRING
            );
            return;
        } else {
            Intent intent = new Intent(getActivity(), StorageDetailsActivity.class);
            intent.putExtra(Constant.WORK_ORDER, dataList.get(position).getWork_order());
            intent.putExtra(Constant.SIDE, storageReady.getSide());
            intent.putExtra(Constant.WARE_HOUSE_NAME, wareHouseName);
            startActivity(intent);
        }

    }
}
