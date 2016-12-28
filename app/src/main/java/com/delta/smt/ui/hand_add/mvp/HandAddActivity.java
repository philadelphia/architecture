package com.delta.smt.ui.hand_add.mvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.hand_add.di.DaggerHandAddCompent;
import com.delta.smt.ui.hand_add.di.HandAddModule;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class HandAddActivity extends BaseActiviy<HandAddPresenter>
        implements HandAddContract.View,CommonBaseAdapter.OnItemClickListener<ItemHandAdd> {

    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.ryv_hand_add)
    RecyclerView mRyvHandAdd;

    private CommonBaseAdapter<ItemHandAdd> mAdapter;
    private List<ItemHandAdd> datas=new ArrayList<>();

    DialogRelativelayout mDialogRelativelayout;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerHandAddCompent.builder().appComponent(appComponent)
                .handAddModule(new HandAddModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getItemHandAddDatas();
    }

    @Override
    protected void initView() {
        mHeaderTitle.setText("线外人员");
        mAdapter=new CommonBaseAdapter<ItemHandAdd>(this,datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemHandAdd item, int position) {
                holder.setText(R.id.tv_title,item.getTitle());
                holder.setText(R.id.tv_line,item.getProduce_line());
                holder.setText(R.id.tv_add_count,item.getAdd_count());
                holder.setText(R.id.tv_material_station,item.getMaterial_station());
                holder.setText(R.id.tv_warning_info,item.getInfo());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemHandAdd item) {
                return R.layout.item_hand_add;
            }
        };
        mRyvHandAdd.setLayoutManager(new LinearLayoutManager(this));
        mRyvHandAdd.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_hand_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.header_back, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.header_setting:
                break;
        }
    }

    @Override
    public void getItemHandAddDatas(List<ItemHandAdd> itemHandAdds) {
        datas.clear();
        datas.addAll(itemHandAdds);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemHandAddDatasFailed() {

    }


    @Override
    public void onItemClick(View view, final ItemHandAdd item, int position) {

        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        mDialogRelativelayout=new DialogRelativelayout(this);
        mDialogRelativelayout.setStrSecondTitle("请求确认");
        final ArrayList<String> datas = new ArrayList<>();
        datas.add("手补件完成？");
        mDialogRelativelayout.setStrContent(datas);
        dialog.setView(mDialogRelativelayout)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item.setInfo("预警信息：手补件完成，等待品管确认");
                        mAdapter.notifyDataSetChanged();
                    }
                }).show();

    }
}
