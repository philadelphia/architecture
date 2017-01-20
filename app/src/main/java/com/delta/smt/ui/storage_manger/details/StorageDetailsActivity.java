package com.delta.smt.ui.storage_manger.details;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.BackupMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.IssureToWarehBody;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.ui.storage_manger.details.di.DaggerStorageDetailsComponent;
import com.delta.smt.ui.storage_manger.details.di.StorageDetailsModule;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsContract;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetailsActivity extends BaseActivity<StorageDetailsPresenter> implements StorageDetailsContract.View {

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
    private List<StorageDetails> dataList = new ArrayList();
    private List<StorageDetails> dataList2 = new ArrayList();
    private CommonBaseAdapter<StorageDetails> adapter;
    private CommonBaseAdapter<StorageDetails> adapter2;
    private View mInflate;
    private BaseActivity baseActiviy;
    private boolean isHaveMaterialCar = true;
    private boolean isScanMateriCar;
    private BarCodeParseIpml barCodeImp;
    private String work_order;
    private String part;
    private MaterialBlockBarCode materialblockbarcode;
    private String currentDeltaMaterialNumber = "";

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerStorageDetailsComponent.builder().appComponent(appComponent).storageDetailsModule(new StorageDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {
        barCodeImp = new BarCodeParseIpml();
        work_order = getIntent().getStringExtra("work_order");
        part = Constant.WARE_HOUSE_NAME;
        Map<String, String> mMap = new HashMap<>();
        mMap.put("part", Constant.WARE_HOUSE_NAME);
        mMap.put("work_order", work_order);
        Gson mGson = new Gson();
        String mS = mGson.toJson(mMap);
        Log.i("aaa", mS);
        getPresenter().getStorageDetails(mS);
        getPresenter().queryMaterailCar(mS);

    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("仓库备料");

        dataList.add(new StorageDetails("", "", "", "", 1));
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
                holder.setText(R.id.tv_number, item.getMaterial_num());
                holder.setText(R.id.tv_location, item.getShelves());
                holder.setText(R.id.tv_needNumber, item.getRe_quantity());
                holder.setText(R.id.tv_shipments, item.getSe_quantity());
                if (item.getMaterial_num().equals(currentDeltaMaterialNumber)) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                switch (item.getStatus()) {
                    case 0:
                        holder.setText(R.id.tv_type, "未发料");
                        break;
                    case 1:
                        holder.setText(R.id.tv_type, "正在发料");
                        break;
                    case 2:
                        holder.setText(R.id.tv_type, "完成发料");
                        break;
                    default:
                        break;
                }
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
        Collections.sort(dataList2, new Comparator<StorageDetails>() {
            @Override
            public int compare(StorageDetails o1, StorageDetails o2) {

                return o2.getStatus() - o1.getStatus();
            }
        });
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bindMaterialCarSucess(List<BindPrepCarIDByWorkOrderResult.DataBean> data) {

        dataList2.clear();
        for (BindPrepCarIDByWorkOrderResult.DataBean dataBean : data) {
            StorageDetails stroageDetail = new StorageDetails();
            stroageDetail.setMaterial_num(dataBean.getMaterial_num());
            stroageDetail.setRe_quantity(dataBean.getAmount());
            stroageDetail.setSe_quantity(dataBean.getDemand());
            stroageDetail.setShelves(dataBean.getShelves());
            stroageDetail.setStatus(dataBean.getStatus());
            dataList2.add(stroageDetail);
        }
        Collections.sort(dataList2, new Comparator<StorageDetails>() {
            @Override
            public int compare(StorageDetails o1, StorageDetails o2) {

                return o2.getStatus() - o1.getStatus();
            }
        });
        adapter2.notifyDataSetChanged();
    }

    private boolean isOver;

    @Override
    public void issureToWarehSuccess(List<StorageDetails> rows) {

        dataList2.clear();
        dataList2.addAll(rows);
        adapter2.notifyDataSetChanged();
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).equals(currentDeltaMaterialNumber)) {
                mRecyContetn.scrollToPosition(i);
            }
            if (rows.get(i).getStatus() != 2) {
                isOver = false;
            }

        }
//        if (isOver) {
//            getPresenter().issureToWarehFinish();
//            isOver = false;
//        }
    }

    @Override
    public void issureToWarehFinishSuccess(String msg) {

        ToastUtils.showMessage(this, "扣帐结果" + msg);
    }

    @Override
    public void queryMaterailCar(String rows) {
        mTextView2.setText(rows);
        isHaveMaterialCar = true;
        Log.e(TAG, "queryMaterailCar: " + rows);

    }

    @Override
    public void queryMaterailCarFailed(String msg) {
        ToastUtils.showMessage(this, msg);
        isHaveMaterialCar = false;
        Log.e(TAG, "queryMaterailCarFailed: " + msg);
    }


    int state = 1;

    @Override
    public void onScanSuccess(String barcode) {

        switch (state) {
            case 1:
                Log.e(TAG, "onScanSuccess: " + state);
                BackupMaterialCar car = null;
                if (!isHaveMaterialCar) {
                    //// TODO: 2017/1/19 扫描料车
                    try {
                        car = ((BackupMaterialCar) barCodeImp.getEntity(barcode, BarCodeType.BACKUP_MATERIAL_CAR));
                        mTextView2.setText(car.getSource());
                        state = 2;

                        Map<String, String> maps = new HashMap<>();
                        maps.put("work_order", work_order);
                        maps.put("part", part);
                        maps.put("pre_car", car.getSource());

                        getPresenter().bindBoundPrepCar(GsonTools.createGsonString(maps));

                    } catch (EntityNotFountException e) {
                        e.printStackTrace();
                        state = 1;
                        ToastUtils.showMessage(this, "扫描料车格式不对");
                        break;
                    }

                } else {
                    state = 2;
                }
            case 2:
                Log.e(TAG, "onScanSuccess: " + state);
                //扫描料盘
                try {
                    materialblockbarcode = (MaterialBlockBarCode) barCodeImp.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    IssureToWarehBody issureToWarehBody = new IssureToWarehBody();
                    issureToWarehBody.setMaterial_num(materialblockbarcode.getDeltaMaterialNumber());
                    issureToWarehBody.setUnit(materialblockbarcode.getUnit());
                    issureToWarehBody.setDc(materialblockbarcode.getDC());
                    issureToWarehBody.setLc(materialblockbarcode.getDC());
                    issureToWarehBody.setVendor(materialblockbarcode.getDC());
                    issureToWarehBody.setVendor(materialblockbarcode.getVendor());
                    issureToWarehBody.setTrasaction_code(materialblockbarcode.getBusinessCode());
                    issureToWarehBody.setPo(materialblockbarcode.getPO());
                    issureToWarehBody.setQuantity(materialblockbarcode.getCount());
                    currentDeltaMaterialNumber = materialblockbarcode.getDeltaMaterialNumber();
                    getPresenter().issureToWareh(GsonTools.createGsonString(issureToWarehBody));

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this, "扫描料盘格式不对");
                }

                state = 2;
                break;
            default:
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


    @OnClick(R.id.button2)
    public void onClick() {
        if(isOver){
            getPresenter().issureToWarehFinish();
        }

    }
}
