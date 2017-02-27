package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import com.delta.buletoothio.barcode.parse.entity.LastMaterialLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.smt.Constant;
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
    @BindView(R.id.bt_ok)
    Button mBtOk;
    @BindView(R.id.bt_next)
    Button mBtNext;
    private List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> dataList = new ArrayList();
    private List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> adapter;
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> adapter2;
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> StartStorageAdapter;
    private BaseActivity baseActiviy;


    private int flag = 1;

    private String materialNumber;
    private String lableBarCode;
    private String serialNum;
    private String count;
    private String lastLocation;
    private AlertDialog dialog;

    private int position = 0;
    private boolean f = false;
    private String firstMaterialNumber;

    private int scan_position = -1;

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
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_shelf_no, item.getShelf_no());
                holder.setText(R.id.tv_to_shelf_no, item.getTo_shelf_no());
                holder.setText(R.id.tv_tag, item.getLabel_name());
                holder.setText(R.id.tv_count, item.getNum());

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
    public void getFailedUpdate(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message) {

    }


    @Override
    public void getSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        scan_position = -1;
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
        firstMaterialNumber =  dataList2.get(0).getMaterial_no();
    }

    @Override
    public void getFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message) {

    }

    @Override
    public void getYesNextSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
        dialog.dismiss();
    }

    @Override
    public void getYesNextFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message) {

    }

    @Override
    public void getYesokSucess() {
        dataList2.clear();
        adapter2.notifyDataSetChanged();
        dialog.dismiss();
    }

    @Override
    public void getYesokFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message) {

    }


    @Override
    public void getBeginSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getBeginFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message) {
        dataList2.clear();
        SnackbarUtil.showMassage(mRecyContetn, message.toString());
    }


    @Override
    public void getBingingLableSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
        scan_position = -1;
    }

    @Override
    public void getBingingLableFailed(String message) {
        SnackbarUtil.showMassage(mRecyContetn, message);
    }


    @Override
    public void getUpLocationSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        position = 0;
        f = false;
        flag = 3;
        scan_position = -1;
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();


    }

    @Override
    public void getUpLocationFailed(MantissaWarehousePutstorageResult.MantissaWarehousePutstorage message) {
        SnackbarUtil.showMassage(mRecyContetn, message.toString());
    }


    @OnClick({R.id.clean, R.id.deduct,R.id.bt_ok,R.id.bt_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clean:
                getPresenter().getUpdate();
                break;
            case R.id.deduct:
                getPresenter().getMantissaWarehousePutstorage();
                mClean.setVisibility(View.GONE);
                mDeduct.setEnabled(false);
                mBtOk.setVisibility(View.VISIBLE);
                mBtNext.setVisibility(View.VISIBLE);
                beginStorage();
                flag = 3;
                break;

            case R.id.bt_next:

                alertNextdialog();

                break;
            case R.id.bt_ok:

                alertOKdialog();

                break;



        }
    }


    //点击下一个按钮
    public void alertNextdialog(){
       dialog= new AlertDialog.Builder(getActivity()).setTitle("提示")//设置对话框标题

                .setMessage(firstMaterialNumber+"料没有退完，确定要退下一个么？")//设置显示的内容

                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件


                    }

                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮



            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        }).show();//在按键响应事件中显示此对话框

    }

    //点击完成按钮
    public void alertOKdialog(){
       dialog= new AlertDialog.Builder(getActivity()).setTitle("提示")//设置对话框标题

                .setMessage("退料未完成，是否确定结束?")//设置显示的内容

                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                            getPresenter().getYesok();

                    }

                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮



            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        }).show();//在按键响应事件中显示此对话框

    }

    @Override
    protected boolean UseEventBus() {
        return true;
    }

    @Subscribe
    public void scanSucceses(PutBarCode putBarCode) throws EntityNotFountException {

        String barcode = putBarCode.getBarCode();
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();

        switch (flag) {
            case 1:
                try {
//                    MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
//                    materialNumber = materiaBar.getDeltaMaterialNumber();
//                    serialNum = materiaBar.getStreamNumber();
                    //尾数仓架位
                    LastMaterialLocation lastMaterialCar = (LastMaterialLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_LOCATION);
                    lastLocation = lastMaterialCar.getSource();
                    flag = 2;
                    Toast.makeText(baseActiviy, "已扫描架位", Toast.LENGTH_SHORT).show();

                    setItemHighLightBasedOnMID(lastLocation);

                } catch (EntityNotFountException e) {
                    SnackbarUtil.showMassage(mRecyContetn, Constant.SCAN_FAILED);
                }
                break;
            case 2:
                try {

                    LabelBarcode lableBar = (LabelBarcode) barCodeParseIpml.getEntity(barcode, BarCodeType.LABLE_BARCODE);
                    lableBarCode = lableBar.getSource();

                    WarehousePutstorageBean bindBean = new WarehousePutstorageBean(lastLocation, lableBarCode);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);

                    getPresenter().getBindingLabel(s);
                    flag = 1;
                    Toast.makeText(baseActiviy, "已扫描标签", Toast.LENGTH_SHORT).show();
                } catch (EntityNotFountException e) {
                    SnackbarUtil.showMassage(mRecyContetn, Constant.SCAN_FAILED);
                }
                break;

            case 3:

                MaterialBlockBarCode lableBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                count = lableBar.getCount();
                materialNumber = lableBar.getDeltaMaterialNumber();
                serialNum = lableBar.getStreamNumber();



                if(materialNumber.equals(firstMaterialNumber)){
                    setItemHighLightBase(materialNumber);
                    UpLocation bindBean = new UpLocation(materialNumber, serialNum);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);
                    getPresenter().getUpLocation(s);
                    Toast.makeText(getActivity(), "已扫描料盘", Toast.LENGTH_SHORT).show();

//                for (int i = 0; i < dataList2.size(); i++) {
//                    if (materialNumber.equals(dataList2.get(i).getMaterial_num()) && serialNum.equals(dataList2.get(i).getSerial_num())) {
//                        position = i;
//                        f = true;
//                        break;
//                    } else {
//                        Toast.makeText(getActivity(), "尾数仓暂无此料盘", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                if (f = true) {
//                    flag = 4;
//                    f = false;
//                }

                }else{
                    SnackbarUtil.showMassage(mRecyContetn, "退料顺序有误，请扫描首个料盘");
                }
                break;

            case 4:

                FrameLocation frameLocation = (FrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.FRAME_LOCATION);
                String mainStore = frameLocation.getSource();

//                if (dataList2.get(position).getShelves().equals(mainStore)) {
//
//                UpLocation bindBean = new UpLocation(materialNumber, serialNum, count);
//                Gson gson = new Gson();
//                String s = gson.toJson(bindBean);
//
//                getPresenter().getUpLocation(s);
//
//            } else {
//                Toast.makeText(getActivity(), "尾数仓暂无此架位", Toast.LENGTH_SHORT).show();
//            }

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

    public void setItemHighLightBasedOnMID(String Shelf_no) {
        for (int i = 0; i < dataList2.size(); i++) {
            if (dataList2.get(i).getShelf_no().equals(Shelf_no)) {
                scan_position = i;
                break;
            }
        }
        adapter2.notifyDataSetChanged();
    }
    public void setItemHighLightBase(String materialNumber) {
        for (int i = 0; i < dataList2.size(); i++) {
            if (dataList2.get(i).getMaterial_no().equals(materialNumber)) {
                scan_position = i;
                break;
            }
        }
        adapter2.notifyDataSetChanged();
    }

    public void beginStorage(){
        dataList.clear();
        dataList.add(new MantissaWarehousePutstorageResult.MantissaWarehousePutstorage("", "", "", "", ""));
        adapter = new CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item) {
                return R.layout.mantissa_beginputstorage_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyTitle.setAdapter(adapter);


        dataList2.clear();
        adapter2 = new CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_to_shelf_no, item.getTo_shelf_no());
                holder.setText(R.id.tv_tag, item.getLabel_name());
                holder.setText(R.id.tv_count, item.getNum());

            }
            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorageResult.MantissaWarehousePutstorage item) {
                return R.layout.mantissa_beginputstorage_item;
            }
        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

    }


}
