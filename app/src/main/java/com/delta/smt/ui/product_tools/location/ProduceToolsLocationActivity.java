package com.delta.smt.ui.product_tools.location;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ItemLocationVerfyList;
import com.delta.smt.entity.JsonLocationVerfyList;
import com.delta.smt.entity.JsonLocationVerfyRoot;
import com.delta.smt.entity.JsonProductToolsLocationRoot;
import com.delta.smt.ui.product_tools.location.di.DaggerProductToolsLocationComponent;
import com.delta.smt.ui.product_tools.location.di.ProductToolsLocationModule;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationContract;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsLocationActivity extends BaseActivity<ProduceToolsLocationPresenter> implements ProduceToolsLocationContract.View {

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.ProductToolsBarCode)
    EditText mProductToolsBarCodeEditText;

    @BindView(R.id.ShiftBarcode)
    EditText mShiftBarcodeCodeEditText;
    @BindView(R.id.layout_root)
    LinearLayout mLayoutRoot;
    @BindView(R.id.et_location_name)
    EditText mTvLocationName;
    @BindView(R.id.recy_content)
    RecyclerView mRecyContent;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;


    private int flag1 = 1;
    private String ID = "admin";

    private String tools;
    private String shelfBarcode;
    private String mString;

    List<ItemLocationVerfyList> data = new ArrayList<>();
    CommonBaseAdapter<ItemLocationVerfyList> adapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProductToolsLocationComponent.builder().appComponent(appComponent).productToolsLocationModule(new ProductToolsLocationModule(this)).build().Inject(this);

    }

    private View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {

        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setText("重置");
        toolbarTitle.setText("治具入架位");

        adapter = new CommonBaseAdapter<ItemLocationVerfyList>(getContext(), data) {
            @Override
            protected void convert(CommonViewHolder holder, ItemLocationVerfyList item, int position) {
                if (position == 0) {
                    holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
                } else {
                    holder.setText(R.id.tv_tools_code, item.getBarcode());
                    holder.setText(R.id.tv_location_code, item.getShelfBarcode());
                    holder.setText(R.id.tv_location_name, item.getShelfName());
                    holder.setText(R.id.tv_tools_type, item.getJigTypeName());
                    holder.setText(R.id.tv_status, item.getLoanStatus());
                }
                if ("已入架位".equals(item.getLoanStatus())) {
                    holder.itemView.setBackgroundColor(Color.GREEN);
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ItemLocationVerfyList item) {
                return R.layout.item_location_success;
            }
        };
        mRecyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContent.setAdapter(adapter);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_tools_location;
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

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);
        if (flag1 != 0) {
            try {

                String param = GsonTools.createGsonString(new String[]{"jigCode", "user"}, new Object[]{barcode, ID});
                getPresenter().getLocation(param);
                tools = barcode;

            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtil.showMassage(getRootView(this), "治具二维码格式不对，请重新扫描！");
                VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
                VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
            }
        } else {

            try {
                if (shelfBarcode != null) {
                    if (shelfBarcode.equals(barcode)) {

                        String param = GsonTools.createGsonString(new String[]{"jigCode", "user", "shelfCode"}, new Object[]{tools, ID, barcode});
                        getPresenter().getSubmitResult(param);
                        shelfBarcode = barcode;

                    } else {
                        SnackbarUtil.showMassage(getRootView(this), "架位二维码不一致，请重新扫描！");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtil.showMassage(getRootView(this), "架位二维码格式不对，请重新扫描！");
                VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
                VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
            }

        }
    }

    @Override
    public int getLocation(JsonProductToolsLocationRoot param) {
        if (param.getCode() == 0) {
            data.clear();
            adapter.notifyDataSetChanged();
            mTvLocationName.setText(param.getRows().getShelfName());
            mProductToolsBarCodeEditText.setText(tools);
            mShiftBarcodeCodeEditText.setText(param.getRows().getShelfCode());
            shelfBarcode = param.getRows().getShelfCode();
            SnackbarUtil.showMassage(getRootView(this), "扫描成功，请再扫描其对应的架位二维码!");
            VibratorAndVoiceUtils.correctVibrator(ProduceToolsLocationActivity.this);
            VibratorAndVoiceUtils.correctVoice(ProduceToolsLocationActivity.this);
            flag1=0;
        } else {
            data.clear();
            adapter.notifyDataSetChanged();
            SnackbarUtil.showMassage(getRootView(this), "该治具无法完成入架位操作,"+param.getMessage());
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
        }
        return param.getCode();
    }

    @Override
    public int getSubmitResult(JsonLocationVerfyRoot jsonLocationVerfyRoot) {
        if (jsonLocationVerfyRoot.getCode() == 0) {
            data.clear();
            data.add(0, new ItemLocationVerfyList("治具二维码", "架位二维码", "架位名称", "治具类型", "状态"));
            for (JsonLocationVerfyList mJ : jsonLocationVerfyRoot.getRows()) {
                String status = "";
                switch (mJ.getLoanStatus()) {
                    case 1:
                        status = "未确认";
                        break;
                    case 2:
                        status = "已确认";
                        break;
                    case 3:
                        status = "已归还";
                        break;
                    case 4:
                        status = "已入架位";
                        break;
                }

                ItemLocationVerfyList mItemLocationVerfyList = new ItemLocationVerfyList(mJ.getJigcode(), mJ.getShelfCode(), mJ.getShelfName(), mJ.getJigTypeName(), status);
                data.add(mItemLocationVerfyList);
            }
            adapter.notifyDataSetChanged();
            VibratorAndVoiceUtils.correctVibrator(ProduceToolsLocationActivity.this);
            VibratorAndVoiceUtils.correctVoice(ProduceToolsLocationActivity.this);
            mTvLocationName.setText("");
            mShiftBarcodeCodeEditText.setText("");
            mProductToolsBarCodeEditText.setText("");
            flag1 = 1;
            SnackbarUtil.showMassage(getRootView(this), "治具入架位已完成，可以进行下一次操作。");

        } else {
            data.clear();
            adapter.notifyDataSetChanged();
            mShiftBarcodeCodeEditText.setText("");
            mProductToolsBarCodeEditText.setText("");
            mTvLocationName.setText("");
            SnackbarUtil.showMassage(getRootView(this), "该治具无法入架位,"+jsonLocationVerfyRoot.getMessage());
            flag1 = 1;
        }

        return 0;
    }


    @Override
    public void Fail() {
        SnackbarUtil.showMassage(getRootView(this), "请求的数据不存在!");
        VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
        VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
    }


    @OnClick(R.id.tv_setting)
    public void onClick() {

        mShiftBarcodeCodeEditText.setText("");
        mProductToolsBarCodeEditText.setText("");
        mTvLocationName.setText("");
        SnackbarUtil.showMassage(getRootView(this), "状态已重置，请重新扫治具二维码。");
        flag1 = 1;
    }
}
