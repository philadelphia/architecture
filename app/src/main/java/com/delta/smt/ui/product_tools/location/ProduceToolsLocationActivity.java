package com.delta.smt.ui.product_tools.location;

import android.view.MenuItem;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsLocationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("治具入架位");

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_tools_location;
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
