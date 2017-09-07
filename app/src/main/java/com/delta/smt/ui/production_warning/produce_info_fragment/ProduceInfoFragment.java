package com.delta.smt.ui.production_warning.produce_info_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BroadcastBegin;
import com.delta.smt.entity.BroadcastCancel;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.entity.production_warining_item.ItemInfo;
import com.delta.smt.ui.production_warning.produce_info_fragment.di.DaggerProduceInfoFragmentCompent;
import com.delta.smt.ui.production_warning.produce_info_fragment.di.ProduceInfoFragmentModule;
import com.delta.smt.ui.production_warning.produce_info_fragment.mvp.ProduceInfoFragmentContract;
import com.delta.smt.ui.production_warning.produce_info_fragment.mvp.ProduceInfoFragmentPresenter;
import com.delta.smt.ui.production_warning.produce_warning.ProduceWarningActivity;
import com.delta.smt.widget.DialogLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceInfoFragment extends BaseFragment<ProduceInfoFragmentPresenter>
        implements ProduceInfoFragmentContract.View, CommonBaseAdapter.OnItemClickListener<ItemInfo>, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.ryv_produce_info)
    RecyclerView mRyvProduceInfo;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout mSrfRefresh;
    @BindView(R.id.statusLayout)
    StatusLayout mStatusLayout;
    private CommonBaseAdapter<ItemInfo> mAdapter;
    private List<ItemInfo> datas = new ArrayList<>();


    DialogLayout mDialogLayout;


    @Override
    protected void initData() {

        Log.i("aaa", "argument== " + ((ProduceWarningActivity) getmActivity()).initLine());

        if (((ProduceWarningActivity) getmActivity()).initLine() != null) {
            getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }


    }

    @Override
    protected void initView() {
        mAdapter = new CommonBaseAdapter<ItemInfo>(getContext(), datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemInfo item, int position) {
                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_produce_line, "产线：" + item.getProduceline());
                holder.setText(R.id.tv_info, "消息：" + item.getInfo());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemInfo item) {
                return R.layout.item_produce_info;
            }
        };
        mRyvProduceInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        mRyvProduceInfo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mSrfRefresh.setOnRefreshListener(this);

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
        if ("Error".equals(message)) {
            Snackbar.make(getActivity().getCurrentFocus(), this.getString(R.string.server_error_message), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getActivity().getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void getItemInfoConfirmSucess() {
        getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
    }


    @Override
    public void onItemClick(View view, final ItemInfo item, int position) {
        EventBus.getDefault().post(new BroadcastCancel());
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        mDialogLayout = new DialogLayout(getContext());
        mDialogLayout.setStrSecondTitle("请求确认");
        final ArrayList<String> datas = new ArrayList<>();

        mDialogLayout.setStrContent(datas);
        dialog.setCancelable(false).setView(mDialogLayout)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        //预警广播开始接受
                        EventBus.getDefault().post(new BroadcastBegin());

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String id = String.valueOf(item.getId());
                        Log.i("ProduceInfoFragment", id);
                        getPresenter().getItemInfoConfirm(id);

                        item.setInfo("操作完成");

                        mAdapter.notifyDataSetChanged();


                        //预警广播开始接受
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
    public void event(ProduceWarningMessage produceWarningMessage) {
        if (((ProduceWarningActivity) getmActivity()).initLine() != null) {
            getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }
        Log.e(TAG, "event3: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
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
        mStatusLayout.showEmptyView();
        mStatusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
            }
        });
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
                mSrfRefresh.setRefreshing(false);
            }
        });
    }
}
