package com.delta.smt.ui.storage_manger.ready;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.libs.adapter.ItemTimeViewHolder;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.StorageReady;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.storage_manger.details.StorageDetailsActivity;
import com.delta.smt.ui.storage_manger.ready.di.DaggerStorageReadyComponent;
import com.delta.smt.ui.storage_manger.ready.di.StorageReadyModule;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyContract;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;
import com.delta.smt.utils.ViewUtils;
import com.delta.smt.widget.WarningDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.delta.smt.R.id.recyclerView;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageReadyFragment extends BaseFragment<StorageReadyPresenter>
        implements StorageReadyContract.View, ItemOnclick<StorageReady>,WarningManger.OnWarning {
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @Inject
    WarningManger warningManger;

    private List<StorageReady> dataList = new ArrayList();
    private ItemCountViewAdapter<StorageReady> adapter;
    private String wareHouseName;
    private boolean isSending = false;
    private String mS;
    private WarningDialog warningDialog;

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
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else {
                    holder.setText(R.id.tv_add_count, "状态：正在发料");
                    holder.itemView.setBackgroundColor(Color.YELLOW);
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
    public void onDestroy() {
        warningManger.sendMessage(new SendMessage(Constant.WAREH_ALARM_FLAG +"_" +wareHouseName,1));
        super.onDestroy();
    }

    @Override
    protected void initData() {

        wareHouseName = getArguments().getString(Constant.WARE_HOUSE_NAME);
        Map<String, String> map = new HashMap<>();
        map.put("part", wareHouseName);
        Gson gson = new Gson();
        mS = gson.toJson(map);
        Log.i("aaa", "argument== " + mS);
        //接收那种预警
        warningManger.addWarning(Constant.WAREH_ALARM_FLAG +"_" +wareHouseName, getmActivity().getClass());
        //需要定制的信息
        warningManger.sendMessage(new SendMessage(Constant.WAREH_ALARM_FLAG +"_" +wareHouseName,0));
        //是否接收预警 可以控制预警时机
        warningManger.setReceive(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
//        if (warningDialog == null) {
//            warningDialog = createDialog("sdfsdf");
//        }
//        if (!warningDialog.isShowing()) {
//            warningDialog.show();
//        }
//        updateMessage(warningDialog,"sdfsfsd");
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
    public WarningDialog createDialog(String message) {

        final WarningDialog warningDialog = new WarningDialog(getmActivity());
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);

                    getPresenter().getStorageReady(mS);
                warningDialog.dismiss();
            }
        });
        warningDialog.show();

        return warningDialog;
    }

    /**
     * type == 9  代表你要发送的是哪个
     *
     * @param message
     */
    private void updateMessage(WarningDialog warningDialog,String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("仓库房备料预警");
        String content = "";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Object message1 = jsonObject.get("message");
                content = content + message1 + "\n";
            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getStorageReadyFailed(String message) {


    }

    @Override
    public void onItemClick(View item, StorageReady storageReady, int position) {
//        if (storageReady.getStatus() == 1 && isSending == true) {
//            SnackbarUtil.showMassage(mRecyclerView, Constant.FAILURE_START_ISSUE_STRING
//            );
//            return;
//        } else {
            Intent intent = new Intent(getActivity(), StorageDetailsActivity.class);
            intent.putExtra(Constant.WORK_ORDER, dataList.get(position).getWork_order());
            intent.putExtra(Constant.SIDE, storageReady.getSide());
            intent.putExtra(Constant.WARE_HOUSE_NAME, wareHouseName);
            startActivity(intent);
        //}

    }

    @Override
    public void warningComing(String warningMessage) {
        if (warningDialog == null) {
            warningDialog = createDialog(warningMessage);
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage(warningDialog,warningMessage);
    }
}
