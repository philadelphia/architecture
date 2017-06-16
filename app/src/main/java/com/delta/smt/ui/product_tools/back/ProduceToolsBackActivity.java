package com.delta.smt.ui.product_tools.back;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.JsonProductBackList;
import com.delta.smt.entity.JsonProductBackRoot;
import com.delta.smt.entity.ProductToolsBack;
import com.delta.smt.ui.product_tools.back.di.DaggerProduceToolsBackComponent;
import com.delta.smt.ui.product_tools.back.di.ProduceToolsBackModule;
import com.delta.smt.ui.product_tools.back.mvp.ProduceToolsBackContract;
import com.delta.smt.ui.product_tools.back.mvp.ProduceToolsBackPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */
public class ProduceToolsBackActivity extends BaseActivity<ProduceToolsBackPresenter> implements ProduceToolsBackContract.View, CommonBaseAdapter.OnItemClickListener<ProductToolsBack> {

    @BindView(R.id.ProductBorrowRecyclerView)
    RecyclerView mProductBorrowRecyclerView;

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.ProductInfoBarCode)
    EditText editText;

    @OnClick(R.id.ProductBack)
    public void confirm() {
        finish();
    }

    List<ProductToolsBack> data = new ArrayList<>();
    CommonBaseAdapter<ProductToolsBack> adapter;
    String ID = "admin";
    String barCode;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProduceToolsBackComponent.builder().appComponent(appComponent).produceToolsBackModule(new ProduceToolsBackModule(this)).build().Inject(this);

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
        toolbarTitle.setText("治具归还");


//        data.add(0, new ProductToolsBack("序号", "治具二维码", "工单号", "治具类型", "状态"));

        adapter = new CommonBaseAdapter<ProductToolsBack>(getContext(), data) {
            @Override
            protected void convert(CommonViewHolder holder, ProductToolsBack item, int position) {

                if (position == 0) {

                    holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));

                } else {

                    holder.setText(R.id.TurnNumber, String.valueOf(position));
                    holder.setText(R.id.ProductToolsBarCode, item.getProductToolsBarCode());
                    holder.setText(R.id.WorkNumber, item.getWorkNumber());
                    holder.setText(R.id.ProductToolsType, item.getProductToolsType());
                    holder.setText(R.id.Status, item.getStatus());

                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ProductToolsBack item) {
                return R.layout.item_product_back;
            }
        };

        adapter.setOnItemClickListener(this);
        mProductBorrowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mProductBorrowRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_back;
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
    public void onItemClick(View view, ProductToolsBack item, int position) {

    }

    @Override
    public void getData(JsonProductBackRoot list) {

        List<ProductToolsBack> p = new ArrayList<>();
        List<JsonProductBackList> j = list.getRows();

        int i = 0;
        if (j != null) {
            for (JsonProductBackList jp : j) {
                ProductToolsBack pt = new ProductToolsBack(i + "", jp.getJigcode(), "1001", jp.getJigTypeName(), jp.getStatName());
                p.add(pt);
            }
        }

        if (list.getCode() == 0) {
            editText.setText(barCode);
            data.clear();
            data.add(0, new ProductToolsBack("序号", "治具二维码", "工单号", "治具类型", "状态"));
            data.addAll(p);
            adapter.notifyDataSetChanged();
            SnackbarUtil.showMassage(this.getWindow().getCurrentFocus(), "成功归还");
            VibratorAndVoiceUtils.correctVibrator(ProduceToolsBackActivity.this);
            VibratorAndVoiceUtils.correctVoice(ProduceToolsBackActivity.this);
        } else {
            SnackbarUtil.showMassage(this.getWindow().getCurrentFocus(), list.getMessage());
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsBackActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsBackActivity.this);
        }

    }

    @Override
    public void getFail() {

        SnackbarUtil.showMassage(this.getWindow().getCurrentFocus(), "请求的数据不存在");
        VibratorAndVoiceUtils.wrongVibrator(ProduceToolsBackActivity.this);
        VibratorAndVoiceUtils.wrongVoice(ProduceToolsBackActivity.this);

    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);

        JSONObject jsonObject = new JSONObject();
        try {

            this.barCode = barcode;
            jsonObject.put("jigCode", barcode);
            jsonObject.put("user", ID);
            String s = jsonObject.toString();
            getPresenter().getData(s);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
