package com.delta.smt.ui.storage_manger.ready;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.storage_manger.details.StorageDetailsActivity;
import com.delta.smt.ui.storage_manger.ready.di.DaggerStorageReadyComponent;
import com.delta.smt.ui.storage_manger.ready.di.StorageReadyModule;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyContract;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.delta.smt.R.id.recyclerView;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageReadyFragment extends BaseFragment<StorageReadyPresenter> implements StorageReadyContract.View,CommonBaseAdapter.OnItemClickListener<StorageReady>,WarningManger.OnWarning {
    @BindView(recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    WarningManger warningManger;

    private AlertDialog alertDialog;

    private List<StorageReady> dataList = new ArrayList();
    private CommonBaseAdapter<StorageReady> adapter;

    @Override
    protected void initView() {

        adapter = new CommonBaseAdapter<StorageReady>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageReady item, int position) {
                holder.setText(R.id.tv_title, "线别: " + item.getLine());
                holder.setText(R.id.tv_line, "工单号: " + item.getNumber());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFace());
                holder.setText(R.id.tv_add_count, "状态: " + item.getType());
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


        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.STORAGEREAD, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);

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
    public void getStorageReadyFailed() {

    }


    @Override
    public void onItemClick(View view, StorageReady item, int position) {

        Intent intent = new Intent(getActivity(), StorageDetailsActivity.class);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        warningManger.registWReceiver(this.getActivity());
        super.onResume();
    }

    @Override
    public void onStop() {
        warningManger.unregistWReceriver(getActivity());
        super.onStop();
    }

    @Override
    public void warningComming(String warningMessage) {
        if (alertDialog != null) {
            alertDialog.show();
        } else {
            alertDialog = createDialog(warningMessage);
        }
    }


    public AlertDialog createDialog(String message) {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(getActivity());
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("测试标题");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datas);
        dialogRelativelayout.setStrSecondTitle("预警异常2");
        ArrayList<String> datass = new ArrayList<>();
        datass.add("dsfdsf");
        datass.add("sdfsdf1");
        datass.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datass);
        //5.构建Dialog，setView的时候把这个View set进去。
        return new AlertDialog.Builder(getActivity()).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
