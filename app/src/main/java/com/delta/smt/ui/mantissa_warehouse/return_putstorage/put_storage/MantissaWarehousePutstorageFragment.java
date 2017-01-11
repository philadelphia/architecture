package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehousePutstorage;
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

public class MantissaWarehousePutstorageFragment extends BaseFragment<MantissaWarehousePutstoragePresenter> implements MantissaWarehousePutstorageContract.View, BaseActiviy.OnBarCodeSuccess {

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
    private List<MantissaWarehousePutstorage> dataList = new ArrayList();
    private List<MantissaWarehousePutstorage> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehousePutstorage> adapter;
    private CommonBaseAdapter<MantissaWarehousePutstorage> adapter2;
    private View mInflate;
    private BaseActiviy baseActiviy;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: "+context.getClass().getName());
        if (context instanceof BaseActiviy) {
            this.baseActiviy = ((BaseActiviy) context);
            baseActiviy.addOnBarCodeSuccess(this);

        }
    }

    @Override
    protected void initView() {

        dataList.add(new MantissaWarehousePutstorage("", "", "", "", ""));
        adapter = new CommonBaseAdapter<MantissaWarehousePutstorage>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorage item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorage item) {
                return R.layout.mantissa_putstorage_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<MantissaWarehousePutstorage>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorage item, int position) {
                holder.setText(R.id.tv_number, item.getNumber());
                holder.setText(R.id.tv_serialNumber, item.getSerialNumber());
                holder.setText(R.id.tv_location, item.getLocation());
                holder.setText(R.id.tv_tag, item.getTag());
                holder.setText(R.id.tv_type, item.getType());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorage item) {
                return R.layout.mantissa_putstorage_item;
            }

        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerMantissaWarehousePutstorageComponent.builder().appComponent(appComponent).mantissaWarehousePutstorageModule(new MantissaWarehousePutstorageModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        getPresenter().getMantissaWarehousePutstorage();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mantissa_putstorage;
    }


    @Override
    public void getSucess(List<MantissaWarehousePutstorage> mantissaWarehousePutstorages) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void getBeginSucess(List<MantissaWarehousePutstorage> mantissaWarehousePutstorages) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
    }


    @Override
    public void getFailed() {

    }

    @Override
    public void getBeginFailed() {

    }


    @OnClick({R.id.clean, R.id.deduct, R.id.bound})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clean:
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
