package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseReturn;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.di.DaggerMantissaWarehouseReturnComponent;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.di.MantissaWarehouseReturnModule;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnContract;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnFragment extends BaseFragment<MantissaWarehouseReturnPresenter> implements MantissaWarehouseReturnContract.View, BaseActiviy.OnDispathchKeyEvent {
    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;
    private List<MantissaWarehouseReturn> dataList = new ArrayList();
    private List<MantissaWarehouseReturn> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseReturn> adapter;
    private CommonBaseAdapter<MantissaWarehouseReturn> adapter2;
    private View mInflate;


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

        dataList.add(new MantissaWarehouseReturn("", "", "", "", ""));
        adapter = new CommonBaseAdapter<MantissaWarehouseReturn>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReturn item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReturn item) {
                return R.layout.mantissa_return_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<MantissaWarehouseReturn>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReturn item, int position) {
                holder.setText(R.id.tv_workOrder, item.getWorkOrder());
                holder.setText(R.id.tv_number, item.getNumber());
                holder.setText(R.id.tv_serialNumber, item.getSerialNumber());
                holder.setText(R.id.tv_location, item.getLocation());
                holder.setText(R.id.tv_type, item.getType());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReturn item) {
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
    public void getSucess(List<MantissaWarehouseReturn> mantissaWarehouseReturnes) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehouseReturnes);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void getFailed() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }
}
