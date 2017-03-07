package com.delta.smt.ui.product_tools.location;

import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.JsonProductToolsLocation;
import com.delta.smt.ui.product_tools.location.di.DaggerProductToolsLocationComponent;
import com.delta.smt.ui.product_tools.location.di.ProductToolsLocationModule;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationContract;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import org.json.JSONObject;

import butterknife.BindView;

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

    private int flag1 = 1;
    private int ID = 1001;

    private String tools;
    private String shift;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProductToolsLocationComponent.builder().appComponent(appComponent).productToolsLocationModule(new ProductToolsLocationModule(this)).build().Inject(this);

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
        toolbarTitle.setText("治具入架位");

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
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("barcode", barcode);
                jsonObject.put("userID", ID);
                String s = "[\'" + jsonObject.toString() + "\']";
                getPresenter().getLocation(s);
                tools = barcode;

            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtil.showMassage(this.getWindow().getCurrentFocus(),"治具二维码格式不对，请重新扫描！");
                VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
                VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
            }
        } else {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("jigBarcode", tools);
                jsonObject.put("shelfBarcode", barcode);
                jsonObject.put("userID", ID);
                String s = "[\'" + jsonObject.toString() + "\']";
                getPresenter().getSubmitResoult(s);
                shift = barcode;
            } catch (Exception e) {
                e.printStackTrace();
                SnackbarUtil.showMassage(this.getWindow().getCurrentFocus(),"架位二维码格式不对，请重新扫描！");
                VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
                VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
            }

        }
    }

    @Override
    public int getLocation(JsonProductToolsLocation param) {
        if (param.getCode()==0) {
            mProductToolsBarCodeEditText.setText(tools);
            mShiftBarcodeCodeEditText.setText(param.getMessage());
            VibratorAndVoiceUtils.correctVibrator(ProduceToolsLocationActivity.this);
            VibratorAndVoiceUtils.correctVoice(ProduceToolsLocationActivity.this);
        }else {
            SnackbarUtil.showMassage(this.getWindow().getCurrentFocus(),"该治具无法完成入架位操作!");
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
        }
        return flag1 = param.getCode();
    }

    @Override
    public int getSubmitResoult(int param) {
        if (param == 0) {

            mShiftBarcodeCodeEditText.setText(shift);
            VibratorAndVoiceUtils.correctVibrator(ProduceToolsLocationActivity.this);
            VibratorAndVoiceUtils.correctVoice(ProduceToolsLocationActivity.this);
            mShiftBarcodeCodeEditText.setText("");
            mProductToolsBarCodeEditText.setText("");
            flag1=1;
            Snackbar.make(getCurrentFocus(),"治具入架位已完成，可以进行下一次操作。",Snackbar.LENGTH_LONG).show();

        }else{
            mShiftBarcodeCodeEditText.setText("");
            mProductToolsBarCodeEditText.setText("");
            Snackbar.make(getCurrentFocus(),"该治具无法入架位。",Snackbar.LENGTH_LONG).show();
            flag1=1;
        }
        return 0;
    }

    @Override
    public void Fail() {
        SnackbarUtil.showMassage(this.getWindow().getCurrentFocus(),"请求的数据不存在!");
        VibratorAndVoiceUtils.wrongVibrator(ProduceToolsLocationActivity.this);
        VibratorAndVoiceUtils.wrongVoice(ProduceToolsLocationActivity.this);
    }
}
