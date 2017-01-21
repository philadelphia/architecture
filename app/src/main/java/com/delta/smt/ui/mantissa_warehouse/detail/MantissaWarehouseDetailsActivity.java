package com.delta.smt.ui.mantissa_warehouse.detail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.LastMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaBingingCarBean;
import com.delta.smt.entity.MantissaCarResult;
import com.delta.smt.entity.MantissaCarBean;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MantissaWarehouseputBean;
import com.delta.smt.entity.WarehouseDetailBean;
import com.delta.smt.ui.mantissa_warehouse.detail.di.DaggerMantissaWarehouseDetailsComponent;
import com.delta.smt.ui.mantissa_warehouse.detail.di.MantissaWarehouseDetailsModule;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsContract;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
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
    @BindView(R.id.car)
    TextView mCar;
    private List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> dataList = new ArrayList();
    private List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> adapter;
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> adapter2;
    private MantissaWarehouseReady.MantissaWarehouse mMantissaWarehouse;
    private String workorder;
    private String name;

    private String lastCar;

    private int flag = 1;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseDetailsComponent.builder().appComponent(appComponent).mantissaWarehouseDetailsModule(new MantissaWarehouseDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        Intent intent = this.getIntent();
        mMantissaWarehouse = (MantissaWarehouseReady.MantissaWarehouse) intent.getSerializableExtra("item");

        workorder = mMantissaWarehouse.getWork_order();
        WarehouseDetailBean bindBean = new WarehouseDetailBean(workorder);
        Gson gson = new Gson();
        String s = gson.toJson(bindBean);
        getPresenter().getMantissaWarehouseDetails(s);
        mCar.setText("");
        //备料车
        MantissaCarBean car = new MantissaCarBean(workorder, "Mantiss");
        String carbean = gson.toJson(car);
        getPresenter().getFindCar(carbean);

    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("尾数仓备料");

        dataList.add(new MantissaWarehouseDetailsResult.MantissaWarehouseDetails("", "", "", "", "", ""));
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
                if (item.getMaterial_num().equals(name)) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                if ("1".equals(item.getStatus())) {
                    holder.setText(R.id.tv_type, "发料中");
                } else if ("2".equals(item.getStatus())) {
                    holder.setText(R.id.tv_type, "完成");
                } else if ("0".equals(item.getStatus())) {
                    holder.setText(R.id.tv_type, "未开始");
                }
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
    public void getBingingCarSucess(List<MantissaCarResult.MantissaCar> car) {
        mCar.setText("");
        mCar.setText(lastCar);
        flag = 2;
    }


    @Override
    public void getBingingCarFailed(String message) {
        flag = 1;
    }

    @Override
    public void getMantissaWarehouseputSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehouseDetailses);

        int position = 0;
        for (int i = 0; i < dataList2.size(); i++) {
            if (dataList2.get(i).getMaterial_num().equals(name)) {
                position = i;
                mRecyContetn.scrollToPosition(i);
            }
        }

        Collections.swap(dataList2,0,position);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void getMantissaWarehouseputFailed(String message) {

    }

    @Override
    public void getMantissaWareOverSucess(List<MantissaWarehouseDetailsResult.MantissaWarehouseDetails> mantissaWarehouseDetailses) {
        Toast.makeText(this, "扣账成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMantissaWareOverFailed(String message) {

    }

    @Override
    public void getFindCarSucess(List<MantissaCarResult.MantissaCar> car) {
        String rows = car.get(0).getMsg();
        mCar.setText(rows);
        flag = 2;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void getFindCarFailed(String message) {

        flag = 1;
    }


    @Override
    public void onScanSuccess(String barcode) {
        Log.e(TAG, "onScanSuccess: ");
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (flag) {
            case 1:
                try {
                    LastMaterialCar LastMaterialCar = (LastMaterialCar) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_CAR);
                    lastCar = LastMaterialCar.getSource();
                    Toast.makeText(this, lastCar, Toast.LENGTH_SHORT).show();
                    MantissaBingingCarBean bindBean = new MantissaBingingCarBean(workorder, "Mantiss", lastCar);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);
                    getPresenter().getbingingCar(s);
                } catch (EntityNotFountException e) {
                    ToastUtils.showMessage(this, "请扫描对应料车");
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    String serial_num = materiaBar.getStreamNumber();
                    String material_num = materiaBar.getDeltaMaterialNumber();
                    String unit = materiaBar.getUnit();
                    String vendor = materiaBar.getVendor();
                    String dc = materiaBar.getDC();
                    String lc = materiaBar.getLC();
                    String trasaction_code = materiaBar.getBusinessCode();
                    String po = materiaBar.getPO();
                    String quantity = materiaBar.getCount();
                    MantissaWarehouseputBean bindBean = new MantissaWarehouseputBean(serial_num, material_num, unit, vendor, dc, lc, trasaction_code, po, quantity);
                    name = material_num;
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);
                    getPresenter().getMantissaWarehouseput(s);

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this, "请扫描对应料盘");
                }

                break;

        }
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
