package com.delta.smt.ui.storage_manger.storage_select;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StorageSelect;
import com.delta.smt.ui.storage_manger.StorageWarningActivity;
import com.delta.smt.ui.storage_manger.storage_select.di.DaggerStorageSelectComponent;
import com.delta.smt.ui.storage_manger.storage_select.di.StorageSelectModule;
import com.delta.smt.ui.storage_manger.storage_select.mvp.StorageSelectContract;
import com.delta.smt.ui.storage_manger.storage_select.mvp.StorageSelectPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class StorageSelectActivity extends BaseActiviy<StorageSelectPresenter> implements StorageSelectContract.View, CommonBaseAdapter.OnItemClickListener<StorageSelect> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_storage_select)
    AppCompatButton btnSelectStorageSelect;
    @BindView(R.id.header_back)
    RelativeLayout headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;

    private List<StorageSelect> mDataList = new ArrayList<>();
    private CommonBaseAdapter<StorageSelect> adapter;
    private Map<Integer,CheckBox> checkBoxMap = new HashMap<>();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_storage_select;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerStorageSelectComponent.builder().appComponent(appComponent).storageSelectModule(new StorageSelectModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getStorageSelect();
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        headerTitle.setText("仓库选择");
        adapter = new CommonBaseAdapter<StorageSelect>(getBaseContext(), mDataList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageSelect item, int position) {
                holder.setText(R.id.chcekbox, item.getName());
                CheckBox box = (CheckBox) holder.getView(R.id.chcekbox);
                box.setTag(position);
                checkBoxMap.put(position,box);
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageSelect item) {
                return R.layout.item_storageselect;
            }
        };

        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSucess(List<StorageSelect> StorageSelect) {
        mDataList.clear();
        mDataList.addAll(StorageSelect);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFailed() {

    }

    @OnClick({R.id.header_back, R.id.header_setting, R.id.btn_storage_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                onBackPressed();
                break;
            case R.id.header_setting:
                break;
            case R.id.btn_storage_select:
                IntentUtils.showIntent(this, StorageWarningActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(View view, StorageSelect item, int position) {
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
