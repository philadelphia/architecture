package com.delta.smt.ui.production_warning.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.print.PrintHelper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.delta.smt.R;
import com.delta.smt.StartActivity;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.item.ItemProductionLine;
import com.delta.smt.ui.production_warning.mvp.presenter.ProductionLinePresenter;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProductionLineActivity extends BaseActiviy<ProductionLinePresenter> {

    @BindView(R.id.ryv_production_line)
    RecyclerView ryvProductionLine;

    private CommonBaseAdapter<ItemProductionLine> mAdapter;
    private List<ItemProductionLine> datas=new ArrayList<>();

    int count=15;
    @Override
    protected void initView() {
        mAdapter=new CommonBaseAdapter<ItemProductionLine>(this,datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemProductionLine item, int position) {
                holder.setText(R.id.cb_production_line,item.getLinename());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemProductionLine item) {
                return R.layout.item_production_line;
            }
        };
        ryvProductionLine.setLayoutManager(new GridLayoutManager(this,2));
        ryvProductionLine.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {
        for (int i=1;i<=count;i++){
            datas.add(new ItemProductionLine("SMT_H"+i));
        }

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_production_line;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_confirm, R.id.btn_all_select, R.id.btn_all_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                startActivity(new Intent(this,ProductionWarningActivity.class));
                break;
            case R.id.btn_all_select:

                break;
            case R.id.btn_all_cancel:

                break;
        }
    }
}
