package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BroadcastBegin;
import com.delta.smt.entity.BroadcastCancel;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.ui.production_warning.di.produce_info_fragment.DaggerProduceInfoFragmentCompent;
import com.delta.smt.ui.production_warning.di.produce_info_fragment.ProduceInfoFragmentModule;
import com.delta.smt.ui.production_warning.item.ItemBreakDown;
import com.delta.smt.ui.production_warning.item.ItemInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.internal.schedulers.NewThreadScheduler;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceInfoFragment extends BaseFragment<ProduceInfoFragmentPresenter>
    implements ProduceInfoFragmentContract.View, CommonBaseAdapter.OnItemClickListener<ItemInfo> {




    @BindView(R.id.ryv_produce_info)
    RecyclerView mRyvProduceInfo;
    private CommonBaseAdapter<ItemInfo> mAdapter;
    private List<ItemInfo> datas=new ArrayList<>();


    DialogRelativelayout mDialogRelativelayout;


    @Override
    protected void initData() {
        getPresenter().getItemInfoDatas();

    }

    @Override
    protected void initView() {
        mAdapter=new CommonBaseAdapter<ItemInfo>(getContext(),datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemInfo item, int position) {
                holder.setText(R.id.tv_title,item.getTitle());
                holder.setText(R.id.tv_produce_line,"产线："+item.getProduceline());
                holder.setText(R.id.tv_info,"消息："+item.getInfo());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemInfo item) {
                return R.layout.item_produce_info;
            }
        };
        mRyvProduceInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceInfo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceInfoFragmentCompent.builder().appComponent(appComponent)
                .produceInfoFragmentModule(new ProduceInfoFragmentModule(this))
                .build().inject(this);
    }



    @Override
    protected int getContentViewId() {
        return R.layout.fragment_produce_info;
    }



    @Override
    public void getItemInfoDatas(List<ItemInfo> itemInfos) {
        datas.clear();
        datas.addAll(itemInfos);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getItemInfoDatasFailed(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onItemClick(View view, final ItemInfo item, int position) {
        EventBus.getDefault().post(new BroadcastCancel());
        AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
        mDialogRelativelayout=new DialogRelativelayout(getContext());
        mDialogRelativelayout.setStrSecondTitle("请求确认");
        final ArrayList<String> datas = new ArrayList<>();

        mDialogRelativelayout.setStrContent(datas);
        dialog.setCancelable(false).setView(mDialogRelativelayout)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        EventBus.getDefault().post(new BroadcastBegin());

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item.setInfo("dfsafa");
                        mAdapter.notifyDataSetChanged();
                        EventBus.getDefault().post(new BroadcastBegin());
                    }
                }).show();

    }

    //Activity预警广播触发事件
    @Override
    protected boolean UseEventBus() {
        return true;
    }

    //Activity预警广播触发事件处理
    @Subscribe
    public void event(ProduceWarningMessage produceWarningMessage){
        getPresenter().getItemInfoDatas();
        Log.e(TAG, "event3: ");
    }
}
