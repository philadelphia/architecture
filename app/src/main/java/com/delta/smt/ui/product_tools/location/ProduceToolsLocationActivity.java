package com.delta.smt.ui.product_tools.location;

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.ProductToolsBarcode;
import com.delta.buletoothio.barcode.parse.entity.ProductToolsRoom;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.JsonProductToolsLocation;
import com.delta.smt.ui.product_tools.location.di.DaggerProductToolsLocationComponent;
import com.delta.smt.ui.product_tools.location.di.ProductToolsLocationModule;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationContract;
import com.delta.smt.ui.product_tools.location.mvp.ProduceToolsLocationPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        if (flag1 != 0) {
            try {
                ProductToolsBarcode p= (ProductToolsBarcode) barCodeParseIpml.getEntity(barcode,BarCodeType.PRODUCT_TOOLS);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("barcode", p.getSource());
                jsonObject.put("userID", ID);
                String s = "[\'" + jsonObject.toString() + "\']";
                getPresenter().getLocation(s);
                tools = barcode;

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "治具二维码格式不对，请重新扫描！", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "架位二维码格式不对，请重新扫描！", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public int getLocation(JsonProductToolsLocation param) {
        if (param.getCode()==0) {
            mProductToolsBarCodeEditText.setText(tools);
            mShiftBarcodeCodeEditText.setText(param.getMessage());
        }else {
            Toast.makeText(this, "该治具无法完成入架位操作!", Toast.LENGTH_SHORT).show();
        }
        return flag1 = param.getCode();
    }

    @Override
    public int getSubmitResoult(int param) {
        if (param == 0) {
            mShiftBarcodeCodeEditText.setText(shift);
            Toast.makeText(this, "治具入架位完成！", Toast.LENGTH_SHORT).show();
            finish();
        }
        return 0;
    }

    @Override
    public void Fail() {
        Toast.makeText(this, "请求的数据不存在!", Toast.LENGTH_SHORT).show();
    }
}
