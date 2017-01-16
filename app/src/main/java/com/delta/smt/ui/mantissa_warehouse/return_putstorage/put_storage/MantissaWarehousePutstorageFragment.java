package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.di.DaggerMantissaWarehousePutstorageComponent;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.di.MantissaWarehousePutstorageModule;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp.MantissaWarehousePutstorageContract;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp.MantissaWarehousePutstoragePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehousePutstorageFragment extends BaseFragment<MantissaWarehousePutstoragePresenter> implements MantissaWarehousePutstorageContract.View, BaseActivity.OnBarCodeSuccess {

    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;
    @BindView(R.id.clean)
    Button mClean;
    @BindView(R.id.deduct)
    Button mDeduct;
    @BindView(R.id.bound)
    Button mBound;
    private List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> dataList = new ArrayList();
    private List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> adapter;
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> adapter2;
    private BaseActivity baseActiviy;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: "+context.getClass().getName());
        if (context instanceof BaseActivity) {
            this.baseActiviy = ((BaseActivity) context);
            baseActiviy.addOnBarCodeSuccess(this);

        }
    }

    @Override
    protected void initView() {

        dataList.add(new MantissaWarehousePutstorageResult.MantissaWarehousePutstorage("", "", "", "", ""));
        adapter = new CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item) {
                return R.layout.mantissa_putstorage_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item, int position) {

                holder.setText(R.id.tv_number, item.getMaterial_num());
                holder.setText(R.id.tv_serialNumber, item.getSerial_num());
                holder.setText(R.id.tv_location, item.getShelves());
                holder.setText(R.id.tv_tag, item.getLabel());
                holder.setText(R.id.tv_type, item.getStatus());

            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item) {
                return R.layout.mantissa_putstorage_item;
            }
        };

        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerMantissaWarehousePutstorageComponent.builder().appComponent(appComponent).mantissaWarehousePutstorageModule(new MantissaWarehousePutstorageModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

     //   getPresenter().getMantissaWarehousePutstorage();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mantissa_putstorage;
    }


    @Override
    public void getSucessUpdate(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getFailedUpdate(String message) {

    }

    @Override
    public void getSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getFailed(String message) {

    }

    @Override
    public void getBeginSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getBeginFailed(String message) {

    }


    @OnClick({R.id.clean, R.id.deduct, R.id.bound})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clean:
                getPresenter().getUpdate();
                break;
            case R.id.deduct:
                getPresenter().getBeginPut();
                mBound.setEnabled(false);
                mClean.setEnabled(false);
                mDeduct.setEnabled(false);
                break;
            case R.id.bound:
                break;
        }
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
    public void onScanSuccess(String barcode) {
        Log.e(TAG, "onScanSucess: " + barcode);
    }
}
