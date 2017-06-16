package com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.LastMaterialLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BacKBarCode;
import com.delta.smt.entity.MantissaWarehouseReturnBean;
import com.delta.smt.entity.MantissaWarehouseReturnResult;
import com.delta.smt.entity.ManualDebitBean;
import com.delta.smt.entity.WarehousePutinStorageBean;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.MantissaWarehouseReturnAndPutStorageActivity;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.di.DaggerMantissaWarehouseReturnComponent;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.di.MantissaWarehouseReturnModule;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnContract;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.mvp.MantissaWarehouseReturnPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.smt.utils.ViewUtils;
import com.delta.smt.widget.CustomPopWindow;
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

public class MantissaWarehouseReturnFragment extends BaseFragment<MantissaWarehouseReturnPresenter>
        implements MantissaWarehouseReturnContract.View, View.OnClickListener {
    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.checkBox)
    CheckBox mCheckBox;
    private List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> dataList = new ArrayList();
    private List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn> adapter;
    private CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn> adapter2;
    private BaseActivity baseActiviy;

    private int flag = 1;

    private String materialNumber;
    private String lastCar;
    private String serialNum;

    private String automaticDebit;
    private String manualDebit;
    private boolean ischeck = false;

    private CustomPopWindow mCustomPopWindow;
    private CommonBaseAdapter<ManualDebitBean.ManualDebit> undoList_adapter;

    private MantissaWarehouseReturnAndPutStorageActivity mantissaWarehouseReturnAndPutStorageActivity;

    private int scan_position = -1;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseReturnComponent.builder().appComponent(appComponent).mantissaWarehouseReturnModule(new MantissaWarehouseReturnModule(this)).build().inject(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MantissaWarehouseReturnAndPutStorageActivity){
             mantissaWarehouseReturnAndPutStorageActivity = (MantissaWarehouseReturnAndPutStorageActivity) context;
        }
    }

    @Override
    protected void initData() {

        getPresenter().getMantissaWarehouseReturn();

        ischeck = SpUtil.getBooleanSF(getContext(), "autochecked");
    }

    @Override
    protected void initView() {

        dataList.add(new MantissaWarehouseReturnResult.MantissaWarehouseReturn("", "", ""));
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
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }

                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_location, item.getShelf_no());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReturnResult.MantissaWarehouseReturn item) {
                return R.layout.mantissa_return_item;
            }

        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

        mCheckBox.setChecked(ischeck);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.SetBooleanSF(getContext(), "autochecked", isChecked);
            }
        });
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMaterialLocationSucess() {
        flag = 2;
        //扫描成功震动并发声
        VibratorAndVoiceUtils.correctVibrator(getmActivity());
        VibratorAndVoiceUtils.correctVoice(getmActivity());
        setItemHighLightBasedOnMID(materialNumber);
        mRecyContetn.scrollToPosition(scan_position);
        Toast.makeText(getActivity(), "已扫描料盘", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMaterialLocationFailed(String message) {
        SnackbarUtil.showMassage(mRecyContetn, message);
        //扫描失败震动并发声
        VibratorAndVoiceUtils.wrongVibrator(getActivity());
        VibratorAndVoiceUtils.wrongVoice(getActivity());
    }

    @Override
    protected boolean UseEventBus() {
        return true;
    }

    @Override
    public void getputinstrageSucess(List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> mantissaWarehouseReturns) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehouseReturns);
        adapter2.notifyDataSetChanged();
        flag = 1;
        scan_position = -1;
        //扫描成功震动并发声
        VibratorAndVoiceUtils.correctVibrator(getmActivity());
        VibratorAndVoiceUtils.correctVoice(getmActivity());
    }

    @Override
    public void getputinstrageFailed(String message) {
        SnackbarUtil.showMassage(mRecyContetn, message);
        //扫描失败震动并发声
        VibratorAndVoiceUtils.wrongVibrator(getActivity());
        VibratorAndVoiceUtils.wrongVoice(getActivity());
    }

    @Override
    public void getManualmaticDebitSucess(List<ManualDebitBean.ManualDebit> manualDebits) {
        if (manualDebits.size() == 0) {

            ToastUtils.showMessage(getContext(), "暂无扣账列表！");

        } else {

            mCustomPopWindow = CustomPopWindow.builder().with(getContext()).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    .setAnimationStyle(R.style.popupAnimalStyle)
                    .setView(R.layout.dialog_bottom_sheet)
                    .enableBlur(true).setStartHeightBlur(100).build().showAsDropDown(mantissaWarehouseReturnAndPutStorageActivity.getToolbar());

            View mContentView = mCustomPopWindow.getContentView();
            RecyclerView rv_debit = ViewUtils.findView(mContentView, R.id.rv_sheet);
            undoList_adapter = new CommonBaseAdapter<ManualDebitBean.ManualDebit>(getContext(), manualDebits) {
                @Override
                protected void convert(CommonViewHolder holder, final ManualDebitBean.ManualDebit item, int position) {
                    holder.setText(R.id.tv_material_id, "料号：" + item.getMaterial_no());
                    holder.setText(R.id.tv_serial_num, "流水号：" + item.getSerial_no());
                    final CheckBox mCheckBox = holder.getView(R.id.cb_debit);
                    mCheckBox.setChecked(item.isChecked());
                    holder.getView(R.id.al).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCheckBox.setChecked(!item.isChecked());
                            item.setChecked(!item.isChecked());
                        }
                    });
                }

                @Override
                protected int getItemViewLayoutId(int position, ManualDebitBean.ManualDebit item) {
                    return R.layout.item_mantissawarehousedebit_list;
                }

            };
            rv_debit.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            rv_debit.setLayoutManager(linearLayoutManager);
            rv_debit.setAdapter(undoList_adapter);


        }
    }

    @Override
    public void getManualmaticDebitFailed(String message) {

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

        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getMantissaWarehouseReturn();
            }
        });

    }

    @Override
    public void showEmptyView() {

        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getMantissaWarehouseReturn();
            }
        });

    }


    @Subscribe
    public void scanSucceses(BacKBarCode bacKBarCode) {

        String barcode = bacKBarCode.getBarCode();

        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();

        switch (flag) {
            case 1:
                try {
                    MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    materialNumber = materiaBar.getDeltaMaterialNumber();
                    serialNum = materiaBar.getStreamNumber();


                    MantissaWarehouseReturnBean bindBean = new MantissaWarehouseReturnBean(materialNumber, serialNum);
                    String s = GsonTools.createGsonListString(bindBean);

                    getPresenter().getMaterialLocation(s);
                } catch (EntityNotFountException e) {
                    SnackbarUtil.showMassage(mRecyContetn, "扫描有误，请扫描料盘！");
                }
                break;
            case 2:
                try {
                    LastMaterialLocation lastMaterialCar = (LastMaterialLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_LOCATION);
                    lastCar = lastMaterialCar.getSource();
                    automaticDebit = "1";
                    manualDebit = "0";

                    if (ischeck) {
                        WarehousePutinStorageBean bindBean = new WarehousePutinStorageBean(materialNumber, serialNum, lastCar, automaticDebit);
                        String s = GsonTools.createGsonListString(bindBean);
                        getPresenter().getputinstrage(s);
                    } else {
                        WarehousePutinStorageBean bindBean = new WarehousePutinStorageBean(materialNumber, serialNum, lastCar, manualDebit);
                        String s = GsonTools.createGsonListString(bindBean);
                        getPresenter().getputinstrage(s);
                    }

                    Toast.makeText(getActivity(), "已扫描架位", Toast.LENGTH_SHORT).show();
                } catch (EntityNotFountException e) {
                    // SnackbarUtil.showMassage(mRecyContetn, "扫描有误，请扫描架位！");

                    try {
                        MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                        materialNumber = materiaBar.getDeltaMaterialNumber();
                        serialNum = materiaBar.getStreamNumber();

                        MantissaWarehouseReturnBean bindBean = new MantissaWarehouseReturnBean(materialNumber, serialNum);
                        Gson gson = new Gson();
                        String s = gson.toJson(bindBean);

                        getPresenter().getMaterialLocation(s);
                    } catch (EntityNotFountException ee) {
                        SnackbarUtil.showMassage(mRecyContetn, "此处不能识别此码！");
                    }


                }
                break;

        }


    }


    public void setItemHighLightBasedOnMID(String materialID) {
        for (int i = 0; i < dataList2.size(); i++) {
            if (dataList2.get(i).getMaterial_no().equals(materialID)) {
                scan_position = i;
                break;
            }
        }
        adapter2.notifyDataSetChanged();
    }

    @OnClick({R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                getPresenter().getManualmaticDebit();
                break;

        }
    }

}
