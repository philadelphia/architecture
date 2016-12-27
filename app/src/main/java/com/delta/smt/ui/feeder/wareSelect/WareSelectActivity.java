package com.delta.smt.ui.feeder.wareSelect;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.WareHouse;
import com.delta.smt.ui.feeder.feederwarning.FeederWarningActivity;
import com.delta.smt.ui.feeder.wareSelect.di.DaggerWareSelectComponent;
import com.delta.smt.ui.feeder.wareSelect.di.WareSelectModule;
import com.delta.smt.ui.feeder.wareSelect.mvp.WareSelectContract;
import com.delta.smt.ui.feeder.wareSelect.mvp.WareSelectPresenter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WareSelectActivity extends BaseActiviy<WareSelectPresenter> implements WareSelectContract.View, CommonBaseAdapter.OnItemClickListener<WareHouse> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_selectWareHouse)
    AppCompatButton btnSelectWareHouse;
    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;

    private List<WareHouse> mDataList = new ArrayList<>();
    private CommonBaseAdapter<WareHouse> adapter;
    private static final String TAG = "FeederCacheRegionActivi";
    private Map<Integer,CheckBox> checkBoxMap = new HashMap<>();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_feeder_cache_region;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerWareSelectComponent.builder().appComponent(appComponent).wareSelectModule(new WareSelectModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().fetchWareHouse();
    }

    @Override
    protected void initView() {
        headerTitle.setText("仓库选择");
        adapter = new CommonBaseAdapter<WareHouse>(getBaseContext(), mDataList) {
            @Override
            protected void convert(CommonViewHolder holder, WareHouse item, int position) {
                holder.setText(R.id.chcekbox, item.getName());
                CheckBox box = (CheckBox) holder.getView(R.id.chcekbox);
                box.setTag(position);
                checkBoxMap.put(position,box);
            }

            @Override
            protected int getItemViewLayoutId(int position, WareHouse item) {
                return R.layout.item_warehouse;
            }
        };

        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSucess(List<WareHouse> wareHouses) {
        mDataList.clear();
        mDataList.addAll(wareHouses);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFailed() {

    }

    @OnClick({R.id.header_back, R.id.header_setting, R.id.btn_selectWareHouse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                onBackPressed();
                break;
            case R.id.header_setting:
                break;
            case R.id.btn_selectWareHouse:
                IntentUtils.showIntent(this, FeederWarningActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(View view, WareHouse item, int position) {
        CheckBox checkBox = checkBoxMap.get(position);
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
            for (CheckBox box : checkBoxMap.values()) {
                box.setFocusable(true);
            }
        } else {
            for (CheckBox box : checkBoxMap.values()) {
                box.setChecked(false);
            }
            checkBox.setChecked(true);
        }

    }


}
