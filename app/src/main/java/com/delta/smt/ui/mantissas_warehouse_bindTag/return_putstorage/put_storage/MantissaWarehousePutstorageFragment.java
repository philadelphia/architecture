package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.AddMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.LabelBarcode;
import com.delta.buletoothio.barcode.parse.entity.LastMaterialLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehousePutstorageBindTagCarResult;
import com.delta.smt.entity.MantissaWarehousePutstorageBindTagResult;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;
import com.delta.smt.entity.PutBarCode;
import com.delta.smt.entity.UpLocation;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.di.DaggerMantissaWarehousePutstorageComponent;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.di.MantissaWarehousePutstorageModule;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.mvp.MantissaWarehousePutstorageContract;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.mvp.MantissaWarehousePutstoragePresenter;

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
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.car)
    TextView mCar;
    @BindView(R.id.bt_submit)
    Button mBtSubmit;

    private List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> dataList = new ArrayList();
    private List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> adapter;
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> adapter2;
    private List<MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList> beginStoragedataList = new ArrayList();
    private List<MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList> beginStoragedataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList> beginStorageadapter;
    private CommonBaseAdapter<MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList> beginStorageadapter2;
    private CommonBaseAdapter<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> StartStorageAdapter;
    private BaseActivity baseActiviy;
    private int flag = 0;
    private String materialNumber;
    private String lableBarCode;
    private String serialNum;
    private String count;
    private AlertDialog dialog;
    private boolean bl_shelf_no;

    private int position = 0;
    private boolean f = false;
    private String firstMaterialNumber;
    private int onclickBegingButton = 0;

    private int scan_position = -1;
    private int currentStep = -1;
    public static final int begin = 0;
    public static final int end = 1;


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
        return R.layout.fragment_mantissa_putstorage_bindtag;
    }


    @Override
    public void getSucessUpdate(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
        if (mantissaWarehousePutstorages.size() == 0) {
            Toast.makeText(getActivity(), "暂无数据！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getFailedUpdate(String message) {

    }


    @Override
    public void getSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        scan_position = -1;
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getFailed(String message) {

    }

    @Override
    public void getYesNextSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
        dialog.dismiss();
        if (mantissaWarehousePutstorages.size() == 0) {
            mBtNext.setEnabled(false);
            mBtOk.setEnabled(false);
        } else {
            firstMaterialNumber = dataList2.get(0).getMaterial_no();
        }

    }

    @Override
    public void getYesNextFailed(String message) {

    }

    @Override
    public void getYesokSucess() {
        dataList2.clear();
        adapter2.notifyDataSetChanged();
        dialog.dismiss();

        mBtNext.setEnabled(false);
        mBtOk.setEnabled(false);

    }

    @Override
    public void getYesokFailed(String message) {

    }


    @Override
    public void getBeginSucess(MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag mantissaWarehousePutstorageBindTags) {
        beginStoragedataList2.clear();
        currentStep = begin;
        List<MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList> storageBind = mantissaWarehousePutstorageBindTags.getStorageBind();
        beginStoragedataList2.addAll(storageBind);
        mNumber.setText("入库单号: " + mantissaWarehousePutstorageBindTags.getStorageNum());
        if(mantissaWarehousePutstorageBindTags.getCarName() == null){
            flag = 1;
        }else {
            mCar.setText("入库车: " + mantissaWarehousePutstorageBindTags.getCarName());
            flag = 2;
        }
        beginStorageadapter2.notifyDataSetChanged();

    }

    @Override
    public void getBeginFailed(String message) {
        dataList2.clear();
        currentStep = end;
        SnackbarUtil.showMassage(mRecyContetn, message);
    }

    @Override
    public void bindMaterialCarSucess(MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag mantissaWarehousePutstorages) {
        mantissaWarehousePutstorages.getCarName();
        mCar.setText("入库车: " + mantissaWarehousePutstorages.getCarName());
        flag = 2;
        Toast.makeText(baseActiviy, "已绑定入库车", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bindMaterialCarFailed(String message) {
        Toast.makeText(baseActiviy, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getBingingLableSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehousePutstorages);
        adapter2.notifyDataSetChanged();
        scan_position = -1;
        mDeduct.setEnabled(true);
        flag = 1;
    }

    @Override
    public void getBingingLableFailed(String message) {
        SnackbarUtil.showMassage(mRecyContetn, message);
        flag = 2;
    }

    @Override
    public void getonclickBeginButtonSucess(List<MantissaWarehousePutstorageResult.MantissaWarehousePutstorage> mantissaWarehousePutstorages) {
        onclickBegingButton = 1;
        currentStep = end;
        Toast.makeText(baseActiviy, "可以开始绑定！", Toast.LENGTH_SHORT).show();
        // mBegin.setBackgroundColor(getResources().getColor(R.color.c_cfcfcf));
    }

    @Override
    public void getonclickBeginButtonFailed(String message) {
        currentStep = begin;
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
        if (mantissaWarehousePutstorages.size() == 0) {
            mBtNext.setEnabled(false);
            mBtOk.setEnabled(false);
        } else {
            firstMaterialNumber = dataList2.get(0).getMaterial_no();
        }
    }

    @Override
    public void getUpLocationFailed(String message) {
        SnackbarUtil.showMassage(mRecyContetn, message);
    }


    @OnClick({R.id.clean, R.id.deduct, R.id.bt_ok, R.id.bt_next, R.id.bt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clean:
                getPresenter().getUpdate();
                break;
            case R.id.deduct:

                getPresenter().getBeginPut("1");
                beginStorage();
                mClean.setVisibility(View.GONE);
                mDeduct.setVisibility(View.GONE);
                mNumber.setVisibility(View.VISIBLE);
                mCar.setVisibility(View.VISIBLE);

                break;

            case R.id.bt_next:

                alertNextdialog();

                break;
            case R.id.bt_ok:

                alertOKdialog();

                break;
            case R.id.bt_submit:


                break;

        }
    }


    //点击下一个按钮
    public void alertNextdialog() {
        dialog = new AlertDialog.Builder(getActivity()).setTitle("提示")//设置对话框标题

                .setMessage(firstMaterialNumber + "料没有退完，确定要退下一个么？")//设置显示的内容

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        getPresenter().getYesNext();
                    }

                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                }).show();//在按键响应事件中显示此对话框

    }

    //点击完成按钮
    public void alertOKdialog() {
        dialog = new AlertDialog.Builder(getActivity()).setTitle("提示")//设置对话框标题

                .setMessage("退料未完成，是否确定结束?")//设置显示的内容

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        getPresenter().getYesok();

                    }

                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮


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
                    AddMaterialCar Car = (AddMaterialCar) barCodeParseIpml.getEntity(barcode, BarCodeType.ADD_MATERIAL_CAR);
                    String MaterialCar = Car.getSource();
                    MantissaWarehousePutstorageBindTagCarResult carname = new MantissaWarehousePutstorageBindTagCarResult(MaterialCar);
                    getPresenter().bindMaterialCar(GsonTools.createGsonListString(carname));
                }catch (EntityNotFountException e){
                    SnackbarUtil.showMassage(mRecyContetn, "扫描有误，请扫描入库车！");
                }
                break;
            case 2:
                try {


                    LabelBarcode lableBar = (LabelBarcode) barCodeParseIpml.getEntity(barcode, BarCodeType.LABLE_BARCODE);
                    lableBarCode = lableBar.getSource();

                    //WarehousePutstorageBean bindBean = new WarehousePutstorageBean(lastLocation, lableBarCode);

                   // getPresenter().getBindingLabel(GsonTools.createGsonListString(bindBean));
                    Toast.makeText(baseActiviy, "已扫描标签", Toast.LENGTH_SHORT).show();
                } catch (EntityNotFountException e) {
                    //SnackbarUtil.showMassage(mRecyContetn, "扫描有误，请扫描标签！");

                    bl_shelf_no = false;
                    LastMaterialLocation lastMaterialCar = (LastMaterialLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_LOCATION);
                   // lastLocation = lastMaterialCar.getSource();
                    for (int i = 0; i < dataList2.size(); i++) {
                    }
                    if (bl_shelf_no == true) {
                        flag = 2;
                        Toast.makeText(baseActiviy, "已扫描架位", Toast.LENGTH_SHORT).show();

                    } else {
                        SnackbarUtil.showMassage(mRecyContetn, "暂无此架位！");
                    }

                }
                break;

            case 3:

                try {

                    MaterialBlockBarCode lableBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    count = lableBar.getCount();
                    materialNumber = lableBar.getDeltaMaterialNumber();
                    serialNum = lableBar.getStreamNumber();


                    if (materialNumber.equals(firstMaterialNumber)) {
                        setItemHighLightBase(materialNumber);
                        UpLocation bindBean = new UpLocation(materialNumber, serialNum);
//                        Gson gson = new Gson();
//                        String s = gson.toJson(bindBean);
                        getPresenter().getUpLocation(GsonTools.createGsonListString(bindBean));
                        Toast.makeText(getActivity(), "已扫描料盘", Toast.LENGTH_SHORT).show();

                    } else {
                        SnackbarUtil.showMassage(mRecyContetn, "退料顺序有误，请扫描首个料盘");
                    }

                } catch (EntityNotFountException e) {
                    SnackbarUtil.showMassage(mRecyContetn, "扫描有误，请扫描料盘！");
                }

                break;

        }

    }


    @Override
    protected void onSaveState(Bundle outState) {


        super.onSaveState(outState);
        outState.putInt("onclickBegingButton", onclickBegingButton);
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {

        super.onRestoreState(savedInstanceState);
        onclickBegingButton = savedInstanceState.getInt("onclickBegingButton");
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

    public void beginStorage() {
        beginStoragedataList.clear();
        beginStoragedataList.add(new MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList("", "", "", ""));
        beginStorageadapter = new CommonBaseAdapter<MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList>(getContext(), beginStoragedataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList item) {
                return R.layout.mantissa_beginputstorage_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyTitle.setAdapter(beginStorageadapter);


        beginStoragedataList2.clear();
        beginStorageadapter2 = new CommonBaseAdapter<MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList>(getContext(), beginStoragedataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_number, item.getMaterialNo());
                holder.setText(R.id.tv_count, item.getMaterialTotal());
                holder.setText(R.id.tv_tag, item.getMoveLabel());
                holder.setText(R.id.tv_shelf_no, item.getShelf());

            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehousePutstorageBindTagResult.MantissaWarehousePutstorageBindTag.storageBindList item) {
                return R.layout.mantissa_beginputstorage_item;
            }
        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyContetn.setAdapter(beginStorageadapter2);

    }

    @Override
    public void showLoadingView() {
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        mBtSubmit.setVisibility(View.GONE);
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getMantissaWarehousePutstorage();
            }
        });

    }

    @Override
    public void showEmptyView() {
        mBtSubmit.setVisibility(View.GONE);
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getMantissaWarehousePutstorage();
            }
        });

    }

}
