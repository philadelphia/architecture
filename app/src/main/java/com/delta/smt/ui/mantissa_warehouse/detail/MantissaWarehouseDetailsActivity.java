package com.delta.smt.ui.mantissa_warehouse.detail;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BindBean;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.ui.mantissa_warehouse.detail.di.DaggerMantissaWarehouseDetailsComponent;
import com.delta.smt.ui.mantissa_warehouse.detail.di.MantissaWarehouseDetailsModule;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsContract;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public class MantissaWarehouseDetailsActivity extends BaseActivity<MantissaWarehouseDetailsPresenter> implements MantissaWarehouseDetailsContract.View {

    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.textView2)
    TextView mTextView2;
    private List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> dataList = new ArrayList();
    private List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> adapter;
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> adapter2;
    private MantissaWarehouseReady.MantissaWarehouse mMantissaWarehouse ;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseDetailsComponent.builder().appComponent(appComponent).mantissaWarehouseDetailsModule(new MantissaWarehouseDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        Intent intent = this.getIntent();
        mMantissaWarehouse=(MantissaWarehouseReady.MantissaWarehouse)intent.getSerializableExtra("item");

        String bb = mMantissaWarehouse.getWork_order();
        BindBean bindBean = new BindBean(bb);
        Gson gson = new Gson();
        String s = gson.toJson(bindBean);


        getPresenter().getMantissaWarehouseDetails(s);

    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("尾数仓备料");

        dataList.add(new MantissaWarehouseDetailsResult.MantissaWarehouseDetails("", "", "", "", "",""));
        adapter = new CommonBaseAdapter<MantissaWarehouseDetailsResult.MantissaWarehouseDetails>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseDetailsResult.MantissaWarehouseDetails item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseDetailsResult.MantissaWarehouseDetails item) {
                return R.layout.details_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<MantissaWarehouseDetailsResult.MantissaWarehouseDetails>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseDetailsResult.MantissaWarehouseDetails item, int position) {
                holder.setText(R.id.tv_number, item.getMaterial_num());
                holder.setText(R.id.tv_location, item.getShelves());
                holder.setText(R.id.tv_needNumber, item.getRe_quantity());
                holder.setText(R.id.tv_shipments, item.getSe_quantity());
                holder.setText(R.id.tv_type, item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseDetailsResult.MantissaWarehouseDetails item) {
                return R.layout.details_item;
            }

        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mantissa_details;
    }

    @Override
    public void getSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehouseDetailses);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void getFailed(String message) {

    }


    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);

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
