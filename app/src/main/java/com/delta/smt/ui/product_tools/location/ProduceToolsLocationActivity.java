package com.delta.smt.ui.product_tools.location;

import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsLocationActivity extends BaseActiviy {

    @BindView(R.id.toolbar_title)
    TextView mTitleTextView;

    @BindView(R.id.navigation)
    TextView mBackTextView;

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleTextView.setText("治具入架位");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_tools_location;
    }
}
