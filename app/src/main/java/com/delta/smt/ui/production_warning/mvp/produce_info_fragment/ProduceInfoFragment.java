package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.Constant;
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
import com.delta.smt.ui.production_warning.item.ItemInfo;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

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

        Log.i("aaa", "argument== " + ((ProduceWarningActivity) getmActivity()).initLine());

        if (((ProduceWarningActivity) getmActivity()).initLine() != null) {
            getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }


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
        ToastUtils.showMessage(getContext(),message);
    }

    @Override
    public void getItemInfoConfirmSucess() {
        getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
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

                        //预警广播开始接受
                        EventBus.getDefault().post(new BroadcastBegin());

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Map<String, String > map = new HashMap<>();
                        map.put("id", String.valueOf(item.getId()));
                        Gson gson = new Gson();
                        String id = gson.toJson(map);
                        Log.i("ProduceInfoFragment",id);
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
    public void event(ProduceWarningMessage produceWarningMessage){
        if (((ProduceWarningActivity) getmActivity()).initLine() != null) {
            getPresenter().getItemInfoDatas(((ProduceWarningActivity) getmActivity()).initLine());
        }
;
        Log.e(TAG, "event3: ");
    }
}
