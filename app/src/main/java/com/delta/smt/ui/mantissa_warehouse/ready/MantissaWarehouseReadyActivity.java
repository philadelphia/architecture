package com.delta.smt.ui.mantissa_warehouse.ready;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.smt.Constant;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.mantissa_warehouse.detail.MantissaWarehouseDetailsActivity;
import com.delta.smt.ui.mantissa_warehouse.ready.di.DaggerMantissaWarehouseReadyComponent;
import com.delta.smt.ui.mantissa_warehouse.ready.di.MantissaWarehouseReadyModule;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyContract;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;

import static com.delta.smt.R.id.recyclerView;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */
@Module
public class MantissaWarehouseReadyActivity extends BaseActiviy<MantissaWarehouseReadyPresenter> implements MantissaWarehouseReadyContract.View, CommonBaseAdapter.OnItemClickListener<MantissaWarehouseReady> , WarningManger.OnWarning{

    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.header_back)
    RelativeLayout mHeaderBack;
    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_setting)
    TextView mHeaderSetting;

    @Inject
    WarningManger warningManger;


    private List<MantissaWarehouseReady> dataList = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseReady> adapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseReadyComponent.builder().appComponent(appComponent).mantissaWarehouseReadyModule(new MantissaWarehouseReadyModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        getPresenter().getMantissaWarehouseReadies();

        //接收那种预警，没有的话自己定义常量
        WarningManger.getInstance().addWarning(Constant.STORAGEREAD, getClass());
        //是否接收预警 可以控制预警时机
        WarningManger.getInstance().setRecieve(true);
        //关键 初始化预警接口
        WarningManger.getInstance().setOnWarning(this);

    }

    @Override
    protected void initView() {
        mHeaderTitle.setText("尾数仓备料");
        adapter = new CommonBaseAdapter<MantissaWarehouseReady>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReady item, int position) {
                holder.setText(R.id.tv_title, "线别: " + item.getLine());
                holder.setText(R.id.tv_line, "工单号: " + item.getNumber());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFace());
                holder.setText(R.id.tv_add_count, "状态: " + item.getType());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReady item) {
                return R.layout.fragment_storage_ready;
            }

        };

        adapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mantissa;
    }

    @Override
    public void onItemClick(View view, MantissaWarehouseReady item, int position) {
        Intent intent = new Intent(this, MantissaWarehouseDetailsActivity.class);

        startActivity(intent);
    }

    @Override
    public void getSucess(List<MantissaWarehouseReady> mantissaWarehouseReadies) {
        dataList.clear();
        dataList.addAll(mantissaWarehouseReadies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getFailed() {

    }

    @OnClick({R.id.header_back, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.header_setting:
                break;
        }
    }

    @Override
    public void warningComming(String warningMessage) {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("测试标题");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datas);
        //5.构建Dialog，setView的时候把这个View set进去。
        new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    protected void onResume() {
        WarningManger.getInstance().registWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        WarningManger.getInstance().unregistWReceriver(this);
        super.onStop();
    }
}
