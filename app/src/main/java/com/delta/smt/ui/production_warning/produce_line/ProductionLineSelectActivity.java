package com.delta.smt.ui.production_warning.produce_line;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.production_warining_item.ItemProduceLine;
import com.delta.smt.ui.fault_processing.processing.FaultProcessingActivity;
import com.delta.smt.ui.hand_add.HandAddActivity;
import com.delta.smt.ui.production_scan.work_order.WorkOrderActivity;
import com.delta.smt.ui.production_warning.produce_line.di.DaggerProduceLineCompnent;
import com.delta.smt.ui.production_warning.produce_line.di.ProduceLineModule;
import com.delta.smt.ui.production_warning.produce_line.mvp.ProduceLineContract;
import com.delta.smt.ui.production_warning.produce_line.mvp.ProduceLinePresenter;
import com.delta.smt.ui.production_warning.produce_warning.ProduceWarningActivity;
import com.delta.smt.ui.quality_manage.QualityManageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *@description :产线选择页面
 *
 *@author : Fuxiang.Zhang
 *@date : 2017/9/18 16:03
*/

public class ProductionLineSelectActivity extends BaseActivity<ProduceLinePresenter>
        implements ProduceLineContract.View, CommonBaseAdapter.OnItemClickListener<ItemProduceLine> {

    @BindView(R.id.ryv_production_line)
    RecyclerView ryvProductionLine;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.layout_statusLayout1)
    StatusLayout mLayoutStatusLayout1;


    private CommonBaseAdapter<ItemProduceLine> mAdapter;
    private List<ItemProduceLine> datas = new ArrayList<>();

    private int type;
    private String lineNames;

    @Override
    protected void initData() {
        //初始请求的产线
        getPresenter().getProductionLineDatas();
        Intent intent = getIntent();
        type = intent.getExtras().getInt(Constant.SELECT_TYPE, -1);
        switch (type) {
            case 0:
                lineNames = SpUtil.getStringSF(this, Constant.PRODUCE_WARNING_LINE_NAME);
                break;
            case 1:
                lineNames = SpUtil.getStringSF(this, Constant.FAULT_PROCESSING_LINE_NAME);
                break;
            case 2:
                lineNames = SpUtil.getStringSF(this, Constant.HAND_ADD_LINE_NAME);
                break;
            case 3:
                lineNames = SpUtil.getStringSF(this, Constant.QUALITY_MANAGE);
                break;
            case 4:
                lineNames = SpUtil.getStringSF(this, Constant.PRODUCE_WARNING_LINE_NAME);
                break;
        }
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
                holder.setText(R.id.cb_production_line, item.getLinename());
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
                        mStringBuffer.append(datas.get(mI).getLinename()).append(",");
                    }
                }
                if (TextUtils.isEmpty(mStringBuffer.toString())) {
                    ToastUtils.showMessage(this, "请选择产线！");
                    //Constant.CONDITION = null;
                    return;
                }
//                mStringBuffer.substring(0,mStringBuffer.length()-2);
                Log.i("aaa", String.valueOf(mStringBuffer));
//                Constant.CONDITION = mStringBuffer.toString();
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.PRODUCTION_LINE,  mStringBuffer.toString());
                switch (type) {
                    //生产中预警页面
                    case 0:
                        SpUtil.SetStringSF(this, Constant.PRODUCE_WARNING_LINE_NAME, mStringBuffer.toString());
                        IntentUtils.showIntent(this, ProduceWarningActivity.class);
                        break;
                    case 1:
                        SpUtil.SetStringSF(this, Constant.FAULT_PROCESSING_LINE_NAME, mStringBuffer.toString());
                        IntentUtils.showIntent(this, FaultProcessingActivity.class);
                        break;
                    //手补件页面
                    case 2:
                        SpUtil.SetStringSF(this, Constant.HAND_ADD_LINE_NAME, mStringBuffer.toString());
                        IntentUtils.showIntent(this, HandAddActivity.class);
                        break;
                    case 3:
                        SpUtil.SetStringSF(this, Constant.QUALITY_MANAGE, mStringBuffer.toString());
                        IntentUtils.showIntent(this, QualityManageActivity.class);
                        break;
                    case 4:
                        SpUtil.SetStringSF(this, Constant.PRODUCE_WARNING_LINE_NAME, mStringBuffer.toString());
                        IntentUtils.showIntent(this, WorkOrderActivity.class);
                        break;
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
        if (lineNames != null) {
            String[] split = lineNames.split(",");

            for (ItemProduceLine itemProduceLine : itemProduceLines) {

                if (useLoop(split, itemProduceLine.getLinename())) {
                    itemProduceLine.setChecked(true);
                }
            }
        }
        datas.clear();
        datas.addAll(itemProduceLines);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();

    }

    public boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    @Override
    public void getFailed(String message) {
        if ("Error".equals(message)) {
            Snackbar.make(getCurrentFocus(), this.getString(R.string.server_error_message), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
        }
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

    /**
     * @description :根据不同的数据状态显示不同的view
     */
    @Override
    public void showLoadingView() {
        mLayoutStatusLayout1.showLoadingView();
    }

    @Override
    public void showContentView() {
        mLayoutStatusLayout1.showContentView();
    }

    @Override
    public void showErrorView() {
        mLayoutStatusLayout1.showErrorView();
        mLayoutStatusLayout1.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getProductionLineDatas();
            }
        });
    }

    @Override
    public void showEmptyView() {
        mLayoutStatusLayout1.showEmptyView();
        mLayoutStatusLayout1.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getProductionLineDatas();
            }
        });
    }


}
