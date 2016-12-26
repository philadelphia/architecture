package com.delta.smt.ui.production_warning.mvp.produce_line;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.di.produce_line.DaggerProduceLineCompnent;
import com.delta.smt.ui.production_warning.di.produce_line.ProduceLineModule;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceLineActivity extends BaseActiviy<ProduceLinePresenter> implements ProduceLineContract.View{

    @BindView(R.id.ryv_production_line)
    RecyclerView ryvProductionLine;

    private CommonBaseAdapter<ItemProduceLine> mAdapter;
    private List<ItemProduceLine> datas=new ArrayList<>();

    String submitline="dfsdf";
    @Override
    protected void initView() {
        //设置Recyleview的adapter
        mAdapter=new CommonBaseAdapter<ItemProduceLine>(this,datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemProduceLine item, int position) {
                holder.setText(R.id.cb_production_line,item.getLinename());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemProduceLine item) {
                return R.layout.item_production_line;
            }
        };
        ryvProductionLine.setLayoutManager(new GridLayoutManager(this,3));
        ryvProductionLine.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {
        getPresenter().getProductionLineDatas();

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProduceLineCompnent.builder().appComponent(appComponent).produceLineModule(new ProduceLineModule(this)).build().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_line;
    }



    @OnClick({R.id.btn_confirm, R.id.btn_all_select, R.id.btn_all_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:

                getPresenter().sumbitLine(submitline);

                startActivity(new Intent(this,ProduceWarningActivity.class));
                break;
            case R.id.btn_all_select:

                break;
            case R.id.btn_all_cancel:

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
}
