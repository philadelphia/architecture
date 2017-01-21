package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.LabelBarcode;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;
import com.delta.smt.entity.PutBarCode;
import com.delta.smt.entity.UpLocation;
import com.delta.smt.entity.WarehousePutstorageBean;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.di.DaggerMantissaWarehousePutstorageComponent;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.di.MantissaWarehousePutstorageModule;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp.MantissaWarehousePutstorageContract;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp.MantissaWarehousePutstoragePresenter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehousePutstorageFragment extends
        BaseFragment<MantissaWarehousePutstoragePresenter> implements MantissaWarehousePutstorageContract.View, BaseActivity.OnBarCodeSuccess {

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
    

    private int flag = 1;

    private String materialNumber;
    private String lableBarCode;
    private String serialNum;
    private String count;

    int position = 0;
    boolean f= false;



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
                if("1".equals(item.getStatus())){
                    holder.setText(R.id.tv_type, "开始退库");
                }else if("0".equals(item.getStatus())){
                    holder.setText(R.id.tv_type,  "等待退入");
                }else {
                    holder.setText(R.id.tv_type,  "完成");
                }

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
        getPresenter().getMantissaWarehousePutstorage();
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
        dataList2.clear();
        Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getBingingLableSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
    }
    @Override
    public void getBingingLableFailed(String message) {
        Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getUpLocationSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();

        int position = 0;
        boolean f= false;
        flag = 3;
    }
    @Override
    public void getUpLocationFailed(String message) {
        Toast.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT).show();
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
                flag = 3;
                break;
            case R.id.bound:
                break;
        }
    }


    @Override
    protected boolean UseEventBus() {
        return true;
    }

    @Subscribe
    public void scanSucceses(PutBarCode putBarCode) throws EntityNotFountException {

        String barcode =putBarCode.getBarCode();
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();

        switch (flag) {
            case 1:
                try {
                    MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    materialNumber = materiaBar.getDeltaMaterialNumber();
                    serialNum = materiaBar.getStreamNumber();
                    serialNum = materiaBar.getStreamNumber();
                    flag = 2;
                    Toast.makeText(baseActiviy, "已扫描料盘", Toast.LENGTH_SHORT).show();
                } catch (EntityNotFountException e) {

                }
                break;
            case 2:
                try {

                    LabelBarcode lableBar = (LabelBarcode) barCodeParseIpml.getEntity(barcode, BarCodeType.LABLE_BARCODE);
                    lableBarCode = lableBar.getSource();

                    WarehousePutstorageBean bindBean = new WarehousePutstorageBean(materialNumber, serialNum, lableBarCode);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);

                    getPresenter().getBindingLabel(s);
                    flag = 1;
                    Toast.makeText(baseActiviy, "已扫描标签", Toast.LENGTH_SHORT).show();
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                }
                break;

            case 3:


                MaterialBlockBarCode lableBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                count = lableBar.getCount();
                materialNumber = lableBar.getDeltaMaterialNumber();
                serialNum = lableBar.getStreamNumber();

                for (int i = 0; i < dataList2.size(); i++) {
                    if(materialNumber.equals(dataList2.get(i).getMaterial_num()) && serialNum.equals( dataList2.get(i).getSerial_num())){
                        position = i;
                        f = true;
                        break;
                    }else {
                        Toast.makeText(getActivity(), "尾数仓暂无此料盘", Toast.LENGTH_SHORT).show();
                    }
                }

                if(f = true){
                    flag = 4;
                    f = false;
                }
                break;

            case 4:

                FrameLocation frameLocation = (FrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.FRAME_LOCATION);
                String mainStore = frameLocation.getSource();

                if(dataList2.get(position).getShelves().equals(mainStore)){

                    UpLocation bindBean = new UpLocation(materialNumber, serialNum, count);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);

                    getPresenter().getUpLocation(s);

                }else {
                    Toast.makeText(getActivity(), "尾数仓暂无此架位", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }

    @Override
    public void onScanSuccess(String barcode) {
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
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: " + context.getClass().getName());
        if (context instanceof BaseActivity) {
            this.baseActiviy = ((BaseActivity) context);
            baseActiviy.addOnBarCodeSuccess(this);

        }
    }

}
