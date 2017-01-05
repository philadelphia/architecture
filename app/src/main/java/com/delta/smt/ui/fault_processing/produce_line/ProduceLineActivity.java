package com.delta.smt.ui.fault_processing.produce_line;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.processing.FalutProcessingActivity;
import com.delta.smt.ui.production_warning.di.produce_line.DaggerProduceLineCompnent;
import com.delta.smt.ui.production_warning.di.produce_line.ProduceLineModule;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.mvp.produce_line.ProduceLineContract;
import com.delta.smt.ui.production_warning.mvp.produce_line.ProduceLinePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */

public class ProduceLineActivity extends BaseActiviy<ProduceLinePresenter> implements ProduceLineContract.View, CommonBaseAdapter.OnItemClickListener<ItemProduceLine> {

    @BindView(R.id.ryv_production_line)
    RecyclerView ryvProductionLine;

    private CommonBaseAdapter<ItemProduceLine> mAdapter;
    private List<ItemProduceLine> datas = new ArrayList<>();

    String submitline = "dfsdf";

    @Override
    protected void initData() {
        getPresenter().getProductionLineDatas();

    }

    @Override
    protected void initView() {
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


    @OnClick({R.id.btn_confirm, R.id.btn_all_select, R.id.btn_all_cancel})
    public void onClick(View view) {
        Log.e(TAG, "onClick: "+datas.toString());
        switch (view.getId()) {
            case R.id.btn_confirm:
                getPresenter().sumbitLine(submitline);
                IntentUtils.showIntent(this, FalutProcessingActivity.class);
                break;
            case R.id.btn_all_select:

                if (datas.size() != 0) {
                    for (ItemProduceLine data : datas) {
                        data.setChecked(true);
                    }
                }
                Log.e(TAG, "onClick: "+datas.toString());
                mAdapter.notifyDataSetChanged();

                break;
            case R.id.btn_all_cancel:
                if (datas.size() != 0) {
                    for (ItemProduceLine data : datas) {
                        data.setChecked(false);
                    }
                }
                Log.e(TAG, "onClick: "+datas.toString());
                mAdapter.notifyDataSetChanged();
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
    public void getFailed() {

    }


    @Override
    public void onItemClick(View view, ItemProduceLine item, int position) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.cb_production_line);
        item.setChecked(!item.isChecked());
        cb.setChecked(item.isChecked());
    }
}
