package com.delta.smt.ui.storage_manger.ready;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.Result;
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

import static com.delta.smt.R.id.cargon_affirm;
import static com.delta.smt.R.id.recyclerView;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageReadyFragment extends BaseFragment<StorageReadyPresenter> implements StorageReadyContract.View,CommonBaseAdapter.OnItemClickListener<StorageReady> {
    @BindView(recyclerView)
    RecyclerView mRecyclerView;

    private List<StorageReady> dataList = new ArrayList();
    private CommonBaseAdapter<StorageReady> adapter;

    @Override
    protected void initView() {

        adapter = new CommonBaseAdapter<StorageReady>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageReady item, int position) {
                holder.setText(R.id.tv_title, "产线: " + item.getLine());
                holder.setText(R.id.tv_line, "工单号: " + item.getWork_order());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFace());
                holder.setText(R.id.tv_add_count, "状态: " + item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageReady item) {
                return R.layout.fragment_storage_ready;
            }
        };



        adapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerStorageReadyComponent.builder().appComponent(appComponent).storageReadyModule(new StorageReadyModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

//        String wareHosueName = getArguments().getString(Constant.WARE_HOUSE_NAME);
        Map<String, String > map = new HashMap<>();
        map.put("part",Constant.WARE_HOUSE_NAME);
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
        dataList.addAll(storageReadies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getStorageReadyFailed(String message) {

    }



    @Override
    public void onItemClick(View view, StorageReady item, int position) {

        Intent intent = new Intent(getActivity(), StorageDetailsActivity.class);
        intent.putExtra("work_order",item.getWork_order());
        startActivity(intent);

    }


}
