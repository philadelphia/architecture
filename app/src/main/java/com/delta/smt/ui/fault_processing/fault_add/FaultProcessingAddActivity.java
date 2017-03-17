package com.delta.smt.ui.fault_processing.fault_add;

import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.fault_add.di.DaggerFaultProcessingAddComponent;
import com.delta.smt.ui.fault_processing.fault_add.di.FaultProcessingAddModule;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddContract;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/9 19:40
 */


public class FaultProcessingAddActivity extends BaseActivity<FaultProcessingAddPresenter> implements FaultProcessingAddContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_code)
    TextView etCode;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_step)
    TextView tvStep;
    @BindView(R.id.et_step)
    EditText etStep;
    @BindView(R.id.bt_save)
    Button btSave;
    private String faultCode;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingAddComponent.builder().appComponent(appComponent).faultProcessingAddModule(new FaultProcessingAddModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        faultCode = getIntent().getExtras().getString(Constant.FAULT_CODE);
        etCode.setText(faultCode);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("新增处理方案");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fault_add;
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


    @OnClick(R.id.bt_save)
    public void onClick() {

        String faultName = etTitle.getText().toString();
        String content = etStep.getText().toString();


        if (TextUtils.isEmpty(faultName)) {
            ToastUtils.showMessage(this, "方案标题不能为空");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showMessage(this, "故障排除步骤不能为空");
            return;
        }
        if (SingleClick.isSingle(5000)) {
            Map map = new HashMap();
            map.put("name", faultName);
            map.put("faultCode", faultCode);
            map.put("faultDetails", content);
            getPresenter().addSolution(GsonTools.createGsonString(map));
        }


    }

    @Override
    public void addSucess(String message) {
        ToastUtils.showMessage(this, message);
        finish();
    }

    @Override
    public void addFailed(String message) {
        ToastUtils.showMessage(this, message);
    }
}
