package com.delta.smt.ui.storage_manger.details;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.ui.storage_manger.details.di.DaggerStorageDetailsComponent;
import com.delta.smt.ui.storage_manger.details.di.StorageDetailsModule;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsContract;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetailsActivity extends BaseActiviy<StorageDetailsPresenter> implements StorageDetailsContract.View{

    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;
    @BindView(R.id.header_back)
    TextView mHeaderBack;
    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_setting)
    TextView mHeaderSetting;
    private List<StorageDetails> dataList = new ArrayList();
    private List<StorageDetails> dataList2 = new ArrayList();
    private CommonBaseAdapter<StorageDetails> adapter;
    private CommonBaseAdapter<StorageDetails> adapter2;
    private View mInflate;
    private BaseActiviy baseActiviy;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerStorageDetailsComponent.builder().appComponent(appComponent).storageDetailsModule(new StorageDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        getPresenter().getStorageDetails();

    }

    @Override
    protected void initView() {
        mHeaderTitle.setText("仓库A备料");
        dataList.add(new StorageDetails("", "", "", "", ""));
        adapter = new CommonBaseAdapter<StorageDetails>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageDetails item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageDetails item) {
                return R.layout.details_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


               adapter2 = new CommonBaseAdapter<StorageDetails>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, StorageDetails item, int position) {
                holder.setText(R.id.tv_number, item.getNumber());
                holder.setText(R.id.tv_location, item.getLocation());
                holder.setText(R.id.tv_needNumber, item.getNeedNumber());
                holder.setText(R.id.tv_shipments, item.getShipments());
                holder.setText(R.id.tv_type, item.getType());
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageDetails item) {
                return R.layout.details_item;
            }

        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_details;
    }

    @Override
    public void getSucess(List<StorageDetails> storageDetailses) {
        dataList2.clear();
        dataList2.addAll(storageDetailses);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getFailed() {

    }



    @OnClick({R.id.header_back, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.header_setting:
                break;
        }
    }


    @Override
    public void onScanSuccess(String barcode) {

        super.onScanSuccess(barcode);
        //Toast.makeText(this,barcode,Toast.LENGTH_SHORT).show();


    }
}
