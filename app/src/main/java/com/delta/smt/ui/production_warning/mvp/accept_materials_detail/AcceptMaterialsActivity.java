package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.ui.production_warning.item.ItemAcceptMaterialDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public class AcceptMaterialsActivity extends BaseActivity<AcceptMaterialsPresenter>
        implements AcceptMaterialsContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.tv_line_type)
    TextView mTvLineType;
    @BindView(R.id.tv_material_station_num)
    TextView mTvMaterialStationNum;
    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_content)
    RecyclerView mRecyContent;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;

    private CommonBaseAdapter<ItemAcceptMaterialDetail> adapter;
    private CommonBaseAdapter<ItemAcceptMaterialDetail> adapter1;
    private List<ItemAcceptMaterialDetail> dataList=new ArrayList();
    private List<ItemAcceptMaterialDetail> dataList1=new ArrayList();

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        //初始化titile
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("贴片机接料");

        dataList.add(new ItemAcceptMaterialDetail());
        adapter = new CommonBaseAdapter<ItemAcceptMaterialDetail>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ItemAcceptMaterialDetail item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemAcceptMaterialDetail item) {
                return R.layout.accept_material_detail_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


        dataList1.add(new ItemAcceptMaterialDetail("fdsf","df","0","dsf","rtr"));
        adapter1=new CommonBaseAdapter<ItemAcceptMaterialDetail>(getContext(),dataList1) {
            @Override
            protected void convert(CommonViewHolder holder, ItemAcceptMaterialDetail item, int position) {
                holder.setText(R.id.tv_material_id,item.getMaterial_id());
                holder.setText(R.id.tv_material_station,item.getMaterial_station());
                holder.setText(R.id.tv_remain_num,item.getRemain());
                holder.setText(R.id.tv_unit,item.getUnit());
                holder.setText(R.id.tv_location,item.getLocation());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemAcceptMaterialDetail item) {
                return R.layout.accept_material_detail_item;
            }
        };
        mRecyContent.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mRecyContent.setAdapter(adapter1);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_warning_details;
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
