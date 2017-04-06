package com.delta.smt.ui.storage_manger.storage_select;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StoreEntity;
import com.delta.smt.ui.mantissa_warehouse.ready.MantissaWarehouseReadyActivity;
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

public class StorageSelectActivity extends BaseActivity<StorageSelectPresenter> implements StorageSelectContract.View, CommonBaseAdapter.OnItemClickListener<StoreEntity> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_storage_select)
    AppCompatButton btnSelectStorageSelect;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;

    private List<StoreEntity> mDataList = new ArrayList<>();
    private CommonBaseAdapter<StoreEntity> adapter;
    private Map<Integer, CheckBox> checkBoxMap = new HashMap<>();
    private int index = 0;
    private String storeName;
    private Bundle bundle;

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
        bundle = new Bundle();
        storeName = SpUtil.getStringSF(this, Constant.STORAGE_NAME);
        getPresenter().getStorageSelect();

    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("仓库选择");

        adapter = new CommonBaseAdapter<StoreEntity>(getBaseContext(), mDataList) {
            @Override
            protected void convert(CommonViewHolder holder, StoreEntity item, int position) {
                holder.setText(R.id.chcekbox, item.getName());
                CheckBox box = (CheckBox) holder.getView(R.id.chcekbox);
                box.setTag(position);
                if (item.getName().equals(storeName)) {
                    box.setChecked(true);
                    index = position;
                }
                checkBoxMap.put(position, box);
            }

            @Override
            protected int getItemViewLayoutId(int position, StoreEntity item) {
                return R.layout.item_storageselect;
            }
        };
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSucess(List<StoreEntity> storageSelect) {

        mDataList.clear();
        for (int i = 0; i < storageSelect.size(); i++) {
            if ("FeederBuffer".equals(storageSelect.get(i).getName())) {
                storageSelect.remove(i);
            }
        }
        mDataList.addAll(storageSelect);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void showLoadingView() {
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {

        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {

        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getStorageSelect();
            }
        });
    }

    @Override
    protected void handError(String contents) {
        super.handError(contents);
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getStorageSelect();
            }
        });

    }

    @Override
    public void showEmptyView() {

        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getStorageSelect();
            }
        });
    }

    @OnClick({R.id.btn_storage_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_storage_select:
                if (TextUtils.isEmpty(storeName)) {
                    SnackbarUtil.showMassage(statusLayout, "请选择仓库");
                } else {

                    bundle.putString(Constant.WARE_HOUSE_NAME, storeName);
                    if ("Mantissa".equals(storeName)) {
                        IntentUtils.showIntent(this, MantissaWarehouseReadyActivity.class, bundle);
                    } else {
                        IntentUtils.showIntent(this, StorageWarningActivity.class, bundle);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, StoreEntity item, int position) {
        CheckBox checkBox = checkBoxMap.get(position);
        index = position;
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
            item.setCheck(false);
            SpUtil.SetStringSF(this, Constant.STORAGE_NAME, "");
            for (CheckBox box : checkBoxMap.values()) {
                box.setFocusable(true);
            }
            storeName = null;

        } else {
            for (CheckBox box : checkBoxMap.values()) {
                box.setChecked(false);
            }
            checkBox.setChecked(true);
            SpUtil.SetStringSF(this, Constant.STORAGE_NAME, item.getName());
            item.setCheck(true);
            storeName = item.getName();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
