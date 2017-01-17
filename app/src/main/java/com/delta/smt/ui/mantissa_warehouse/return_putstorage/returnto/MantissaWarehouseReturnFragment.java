package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseReturnResult;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.di.DaggerMantissaWarehouseReturnComponent;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.di.MantissaWarehouseReturnModule;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnContract;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnFragment extends BaseFragment<MantissaWarehouseReturnPresenter> implements MantissaWarehouseReturnContract.View, BaseActivity.OnBarCodeSuccess {
    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;
    private List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> dataList = new ArrayList();
    private List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn> adapter;
    private CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn> adapter2;
    private BaseActivity baseActiviy;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: " + context.getClass().getName());
        if (context instanceof BaseActivity) {
            this.baseActiviy = ((BaseActivity) context);
            baseActiviy.addOnBarCodeSuccess(this);
        }
    }

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseReturnComponent.builder().appComponent(appComponent).mantissaWarehouseReturnModule(new MantissaWarehouseReturnModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        getPresenter().getMantissaWarehouseReturn();

    }

    @Override
    protected void initView() {

        dataList.add(new MantissaWarehouseReturnResult.MantissaWarehouseReturn("", "", "", "",""));
        adapter = new CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReturnResult.MantissaWarehouseReturn item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReturnResult.MantissaWarehouseReturn item) {
                return R.layout.mantissa_return_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReturnResult.MantissaWarehouseReturn item, int position) {
                holder.setText(R.id.tv_workOrder, item.getWork_order());
                holder.setText(R.id.tv_number, item.getMaterial_num());
                holder.setText(R.id.tv_serialNumber, item.getSerial_num());
                holder.setText(R.id.tv_location, item.getShelves());
                holder.setText(R.id.tv_type, item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReturnResult.MantissaWarehouseReturn item) {
                return R.layout.mantissa_return_item;
            }

        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mantissa_return;
    }

    @Override
    public void getSucess(List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> mantissaWarehouseReturnes) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehouseReturnes);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void getFailed(String message) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged: " + hidden);
        if (baseActiviy != null) {
            if (hidden) {
                baseActiviy.removeOnBarCodeSuccess(this);
            } else {
                baseActiviy.addOnBarCodeSuccess(this);
            }
        }
    }

    @Override
    public void onPause() {

        super.onPause();

    }


    @Override
    public void onScanSuccess(String barcode) {

        Log.e(TAG, "onScanSucess: " + barcode);
    }
}
