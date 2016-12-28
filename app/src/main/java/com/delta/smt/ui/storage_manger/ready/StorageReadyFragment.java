package com.delta.smt.ui.storage_manger.ready;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.ui.storage_manger.details.StorageDetailsActivity;
import com.delta.smt.ui.storage_manger.ready.di.DaggerStorageReadyComponent;
import com.delta.smt.ui.storage_manger.ready.di.StorageReadyModule;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyContract;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
                holder.setText(R.id.info_text, "线别: " + item.getLine());
                holder.setText(R.id.info_text_2, "工单号: " + item.getNumber());
                holder.setText(R.id.info_text_3, "面别: " + item.getFace());
                holder.setText(R.id.info_text_4, "状态: " + item.getType());
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

        getPresenter().getStorageReady();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_checkin;
    }


    @Override
    public void getStorageReadySucess(List<StorageReady> storageReadies) {
        dataList.clear();
        dataList.addAll(storageReadies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getStorageReadyFailed() {

    }


    @Override
    public void onItemClick(View view, StorageReady item, int position) {

        Intent intent = new Intent(getActivity(), StorageDetailsActivity.class);

        startActivity(intent);

    }
}
