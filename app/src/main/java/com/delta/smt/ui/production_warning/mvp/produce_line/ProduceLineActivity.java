package com.delta.smt.ui.production_warning.mvp.produce_line;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.processing.FalutProcessingActivity;
import com.delta.smt.ui.hand_add.mvp.HandAddActivity;
import com.delta.smt.ui.production_warning.di.produce_line.DaggerProduceLineCompnent;
import com.delta.smt.ui.production_warning.di.produce_line.ProduceLineModule;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.mvp.accept_materials_detail.AcceptMaterialsActivity;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceLineActivity extends BaseActivity<ProduceLinePresenter>
        implements ProduceLineContract.View, CommonBaseAdapter.OnItemClickListener<ItemProduceLine> {

    @BindView(R.id.ryv_production_line)
    RecyclerView ryvProductionLine;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;


    private CommonBaseAdapter<ItemProduceLine> mAdapter;
    private List<ItemProduceLine> datas = new ArrayList<>();

    private int type;

    @Override
    protected void initData() {
        //初始请求的产线
        getPresenter().getProductionLineDatas();
        Intent intent = getIntent();
        type = intent.getExtras().getInt(Constant.SELECTTYPE, -1);
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("产线选择");



        //设置Recyleview的adapter
        mAdapter = new CommonBaseAdapter<ItemProduceLine>(this, datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemProduceLine item, int position) {
                holder.setText(R.id.cb_production_line, "SMT_" + item.getLinename());
                CheckBox checkBox = holder.getView(R.id.cb_production_line);
                checkBox.setChecked(item.isChecked());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemProduceLine item) {
                return R.layout.item_produce_line;
            }
        };
        ryvProductionLine.setLayoutManager(new GridLayoutManager(this, 3));
        ryvProductionLine.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProduceLineCompnent.builder().appComponent(appComponent).
                produceLineModule(new ProduceLineModule(this)).build().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_line;
    }


    @OnClick({R.id.btn_confirm, R.id.btn_all_select, R.id.btn_all_cancel, R.id.btn_un_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                Log.e(TAG, "onClick: " + type);
                StringBuffer mStringBuffer = new StringBuffer();
                for (int mI = 0; mI < datas.size(); mI++) {
                    if (datas.get(mI).isChecked()) {
                        mStringBuffer.append(datas.get(mI).getLinename() + ",");
                    }
                }
                if (TextUtils.isEmpty(mStringBuffer.toString())) {
                    ToastUtils.showMessage(this, "请选择产线！");
                    Constant.CONDITION = null;
                    return;
                }

                Log.i("aaa", String.valueOf(mStringBuffer));
                Constant.CONDITION = mStringBuffer.toString();
//                Constant.initLine();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.PRODUCTIONLINE, mStringBuffer.toString());
                if (type == 0) {
                    IntentUtils.showIntent(this, ProduceWarningActivity.class, bundle);
//                    IntentUtils.showIntent(this, AcceptMaterialsActivity.class);

                }

                if (type == 1) {
                    IntentUtils.showIntent(this, FalutProcessingActivity.class, bundle);

                }

                if (type == 2) {
                    IntentUtils.showIntent(this, HandAddActivity.class, bundle);

                }
                break;

            case R.id.btn_all_select:

                if (datas.size() != 0) {
                    for (ItemProduceLine data : datas) {
                        data.setChecked(true);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                Log.e(TAG, "onClick: " + datas.toString());

                break;

            case R.id.btn_all_cancel:
                if (datas.size() != 0) {
                    for (ItemProduceLine data : datas) {
                        data.setChecked(false);
                    }
                }
                Log.e(TAG, "onClick: " + datas.toString());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_un_select:

                if (datas.size() != 0) {
                    for (ItemProduceLine data : datas) {
                        data.setChecked(!data.isChecked());
                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void getDataLineDatas(List<ItemProduceLine> itemProduceLines) {
        datas.clear();
        datas.addAll(itemProduceLines);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void getFailed(String message) {
        Snackbar.make(getCurrentFocus(),message,Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onItemClick(View view, ItemProduceLine item, int position) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.cb_production_line);
        item.setChecked(!item.isChecked());
        cb.setChecked(item.isChecked());
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
