package com.delta.smt;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.GridItemDecoration;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.service.WarningService;
import com.delta.smt.ui.checkstock.CheckStockActivity;
import com.delta.smt.ui.hand_add.mvp.HandAddActivity;
import com.delta.smt.ui.main.mvp.MainPresenter;
import com.delta.smt.ui.mantissa_warehouse.ready.MantissaWarehouseReadyActivity;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.MantissaWarehouseReturnAndPutStorage;
import com.delta.smt.ui.production_warning.mvp.produce_line.ProduceLineActivity;
import com.delta.smt.ui.storage_manger.StorageWarningActivity;
import com.delta.smt.ui.store.StoreIssueActivity;
import com.delta.smt.ui.storeroom.StoreRoomActivity;
import com.delta.smt.ui.warningSample.WarningSampleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActiviy<MainPresenter> implements CommonBaseAdapter.OnItemClickListener<String> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<String> fuctionString;

    @Override
    protected void componentInject(AppComponent appComponent) {


    }

    @Override
    protected void initView() {

        CommonBaseAdapter<String> adapter = new CommonBaseAdapter<String>(this, fuctionString) {
            @Override
            protected void convert(CommonViewHolder holder, String item, int position) {
                holder.setImageResource(R.id.iv_function,R.drawable.title);
                Log.e(TAG, "convert: " + item);
                holder.setText(R.id.tv_function, item);
            }

            @Override
            protected int getItemViewLayoutId(int position, String item) {
                return R.layout.item_function;
            }
        };
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new GridItemDecoration(this));
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {
        Intent intent = new Intent(this, WarningService.class);
        startService(intent);
        fuctionString = new ArrayList<>();
        fuctionString.add("Feeder缓冲区");
        fuctionString.add("仓库房");
        fuctionString.add("PCB库房2");
        fuctionString.add("PCB库房3");
        fuctionString.add("PCB库房4");
        fuctionString.add("sample");
        fuctionString.add("warningSample");
        fuctionString.add("手补件通知");
        fuctionString.add("尾数仓备料");
        fuctionString.add("尾数仓退料及入库");
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void onItemClick(View view, String item, int position) {
        Log.e(TAG, "onItemClick: " + item + position);
        ToastUtils.showMessage(this, item);

        switch (item) {
            case "Feeder缓冲区":
                IntentUtils.showIntent(this, com.delta.smt.ui.feeder.wareSelect.WareSelectActivity.class);
                break;
            case "仓库房":
                IntentUtils.showIntent(this, StorageWarningActivity.class);
                break;
            case "PCB库房2":
                IntentUtils.showIntent(this, StoreIssueActivity.class);
                break;
            case "PCB库房3":
                IntentUtils.showIntent(this, StoreRoomActivity.class);
                break;
            case "PCB库房4":
                IntentUtils.showIntent(this, CheckStockActivity.class);
                break;
            case "sample":
                IntentUtils.showIntent(this, ProduceLineActivity.class);
                break;
            case "手补件通知":
                IntentUtils.showIntent(this, HandAddActivity.class);
                break;
            case "尾数仓备料":
                IntentUtils.showIntent(this, MantissaWarehouseReadyActivity.class);
                break;
            case "尾数仓退料及入库":
                IntentUtils.showIntent(this, MantissaWarehouseReturnAndPutStorage.class);
                break;
            case "warningSample":
                IntentUtils.showIntent(this, WarningSampleActivity.class);
            default:
                break;
        }
    }

}
