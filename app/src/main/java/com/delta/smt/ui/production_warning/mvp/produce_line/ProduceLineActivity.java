package com.delta.smt.ui.production_warning.mvp.produce_line;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.di.produce_line.DaggerProduceLineCompnent;
import com.delta.smt.ui.production_warning.di.produce_line.ProduceLineModule;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.mvp.produce_warning.ProduceWarningActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceLineActivity extends BaseActivity<ProduceLinePresenter>
        implements ProduceLineContract.View,CommonBaseAdapter.OnItemClickListener<ItemProduceLine>{

    @BindView(R.id.ryv_production_line)
    RecyclerView ryvProductionLine;

    private CommonBaseAdapter<ItemProduceLine> mAdapter;
    private List<ItemProduceLine> datas=new ArrayList<>();

    String submitline="add";

    @Override
    protected void initData() {
        getPresenter().getProductionLineDatas();

    }

    @Override
    protected void initView() {
        //设置Recyleview的adapter
        mAdapter=new CommonBaseAdapter<ItemProduceLine>(this,datas) {
            @Override
            protected void convert(CommonViewHolder holder, ItemProduceLine item, int position) {
                holder.setText(R.id.cb_production_line,"SMT_"+item.getLinename());
                CheckBox checkBox = holder.getView(R.id.cb_production_line);
                checkBox.setChecked(item.isChecked());
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemProduceLine item) {
                return R.layout.item_produce_line;
            }
        };
        ryvProductionLine.setLayoutManager(new GridLayoutManager(this,3));
        ryvProductionLine.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }



    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProduceLineCompnent.builder().appComponent(appComponent).
                produceLineModule(new ProduceLineModule(this)).build().inject(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_line;
    }



    @OnClick({R.id.btn_confirm, R.id.btn_all_select, R.id.btn_all_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:

                StringBuffer mStringBuffer = new StringBuffer();

                for (int mI = 0; mI < datas.size(); mI++) {
                    if (datas.get(mI).isChecked()){
                        mStringBuffer.append(datas.get(mI).getLinename()+",");
                    }

                }
                Log.i("aaa", String.valueOf(mStringBuffer));
/*                for (ItemProduceLine data:datas){
                   mStringBuffer.append(data.getLinename()+"'");
                }*/
//                getPresenter().sumbitLine(submitline);
                Intent mIntent=new Intent(this,ProduceWarningActivity.class);
                mIntent.putExtra("condition", (CharSequence) mStringBuffer);
                startActivity(mIntent);
                break;
            case R.id.btn_all_select:

                if (datas.size() != 0) {
                    for (ItemProduceLine data : datas) {
                        data.setChecked(true);
                    }
                }
                Log.e(TAG, "onClick: "+datas.toString());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_all_cancel:
                if (datas.size() != 0) {
                    for (ItemProduceLine data : datas) {
                        data.setChecked(false);
                    }
                }
                Log.e(TAG, "onClick: "+datas.toString());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void getDataLineDatas(List<ItemProduceLine> itemProduceLines) {
        datas.clear();
        datas.addAll(itemProduceLines);
        //对adapter刷新改变
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getFailed() {

    }

    @Override
    public void onItemClick(View view, ItemProduceLine item, int position) {

        CheckBox cb = (CheckBox) view.findViewById(R.id.cb_production_line);
        item.setChecked(!item.isChecked());
        cb.setChecked(item.isChecked());
//        datas.get(position).setChecked(!datas.get(position).isChecked());
//        cb.setChecked(datas.get(position).isChecked());
    }

}
