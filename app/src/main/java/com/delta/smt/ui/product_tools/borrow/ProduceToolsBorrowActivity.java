package com.delta.smt.ui.product_tools.borrow;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.ui.product_tools.borrow.di.ProduceToolsBorrowComponent;
import com.delta.smt.ui.product_tools.borrow.di.ProduceToolsBorrowModule;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowContract;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBorrowActivity extends BaseActiviy<ProduceToolsBorrowPresenter> implements ProduceToolsBorrowContract.View{

    @BindView(R.id.toolbar_title)
    TextView mTitleTextView;

    @BindView(R.id.navigation)
    TextView mBackTextView;

    @BindView(R.id.ProductBorrowRecyclerView)
    RecyclerView ProductBorrowRecyclerView;

    @Override
    protected void componentInject(AppComponent appComponent) {

        //ProduceToolsBorrowComponent.builder().appComponent(appComponent).ProduceToolsBorrowModule(new ProduceToolsBorrowModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleTextView.setText("治具借出");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_tools_borrow;
    }

    @Override
    public void getFormData(List<ProductWorkItem> ProductWorkItem) {

    }
}
