package com.delta.smt.ui.store;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.ui.store.di.DaggerWarningListComponent;
import com.delta.smt.ui.store.di.WarningListModule;
import com.delta.smt.ui.store.mvp.WarningListContract;
import com.delta.smt.ui.store.mvp.WarningListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListActivity extends BaseActiviy<WarningListPresenter> implements WarningListContract.View{
    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.ed_work)
    TextView edWork;
    @BindView(R.id.ed_pcb)
    TextView edPcb;
    @BindView(R.id.ed_machine)
    TextView edMachine;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;


    List<ListWarning> mList=new ArrayList<>();
    private CommonBaseAdapter<ListWarning> mAdapter;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerWarningListComponent.builder().appComponent(appComponent).warningListModule(new WarningListModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
    getPresenter().fatchListWarning();
    }

    @Override
    protected void initView() {
        headerTitle.setText(this.getResources().getString(R.string.storetitle));

        List<ListWarning>list=new ArrayList<>();
        list.add(new ListWarning("","","","","",""));

        CommonBaseAdapter<ListWarning> AdapterTitle = new CommonBaseAdapter<ListWarning>(this,list) {
            @Override
            protected void convert(CommonViewHolder holder, ListWarning item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.waring_editext));
            }

            @Override
            protected int getItemViewLayoutId(int position, ListWarning item) {
                return R.layout.item_warning;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyTitle.setAdapter(AdapterTitle);

        mAdapter= new CommonBaseAdapter<ListWarning>(this,mList) {
            @Override
            protected void convert(CommonViewHolder holder, ListWarning item, int position) {
                holder.setText(R.id.pcb_number,item.getPcb());
                holder.setText(R.id.pcb_price,item.getJia());
                holder.setText(R.id.pcb_thenumber,item.getDangqaian());
                holder.setText(R.id.pcb_demand,item.getXuqiu());
                holder.setText(R.id.pcb_code,item.getPcbCode());
                holder.setText(R.id.pcb_time,item.getDc());

            }

            @Override
            protected int getItemViewLayoutId(int position, ListWarning item) {
                return R.layout.item_warning;
            }
        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyContetn.setAdapter(mAdapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning;
    }


    @Override
    public void onSucess(List<ListWarning> wareHouses) {
        mList.clear();
        mList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }
}
