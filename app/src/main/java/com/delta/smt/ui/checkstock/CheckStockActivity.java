package com.delta.smt.ui.checkstock;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.ui.checkstock.mvp.CheckStockPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.delta.smt.R.id.recy_contetn;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockActivity extends BaseActiviy<CheckStockPresenter> {
    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.cargoned)
    EditText cargoned;
    @BindView(R.id.cargon_affirm)
    Button cargonAffirm;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(recy_contetn)
    RecyclerView recyContetn;
    private List<CheckStock> dataList= new ArrayList<>();
    private CommonBaseAdapter<CheckStock> mAdapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {

        for (int i=0;i<10;i++){
            CheckStock checkStock=new CheckStock();
            checkStock.setPcb("034335230"+i);
            checkStock.setLiu("2016876500"+i);
            checkStock.setNumber("200");
            if (i==6||i==3||i==3){
                checkStock.setPcb("未开始");
            }else if (i==0){
                checkStock.setPcb("开始盘点");
            }
            checkStock.setPcb("盘点完成");
            dataList.add(checkStock);


        }

    }

    @Override
    protected void initView() {
        List<CheckStock>list=new ArrayList<>();
        list.add(new CheckStock("","","","",""));
        CommonBaseAdapter<CheckStock> mAdapterTitle = new CommonBaseAdapter<CheckStock>(getContext(), list) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStock item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.waring_editext));
            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStock item) {
                return R.layout.item_check;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyTitle.setAdapter(mAdapterTitle);

        mAdapter= new CommonBaseAdapter<CheckStock>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStock item, int position) {
                holder.setText(R.id.statistics,item.getPcb());
                holder.setText(R.id.statistics_id,item.getLiu());
                holder.setText(R.id.statistics_pcbnumber,item.getNumber());
                holder.setText(R.id.statistics_number,item.getCheck());
                holder.setText(R.id.statistics_storenumber,item.getZhuangtai());
            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStock item) {
                return R.layout.item_check;
            }
        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyContetn.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {

            }
        });

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_check;
    }


}
