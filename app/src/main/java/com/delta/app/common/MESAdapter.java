package com.delta.app.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.delta.app.R;

import java.util.List;

/**
 * Created by Tao.ZT.Zhang on 2017/7/26.
 */

public class MESAdapter extends RecyclerView.Adapter {
    private List<UpLoadEntity.FeedingListBean> mFeedingList;
    private List<UpLoadEntity.MaterialListBean> mMaterialList;
    private Context context;
    private LayoutInflater inflater;
    private static final int HEAD = 0;
    private static final int ITEM_FEEDER = 1;
    private static final int ITEM_MATERIAL = 2;

    public MESAdapter(Context context, List<UpLoadEntity.FeedingListBean> feedingList, List<UpLoadEntity.MaterialListBean> materialList) {
        this.context = context;
        this.mFeedingList = feedingList;
        this.mMaterialList = materialList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD;
        else if (position > 0 && position < mFeedingList.size() + 1)
            return ITEM_FEEDER;
        else if (position == mFeedingList.size() + 1)
            return HEAD;
        else
            return ITEM_MATERIAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case HEAD:
                return new HeadViewHolder(inflater.inflate(R.layout.head_layout,
                        parent, false), context);
            //上料
            case ITEM_FEEDER:
                return new FeederViewHolder(inflater.inflate( R.layout.item_feeder_list,parent, false), context);

            //发料
            case ITEM_MATERIAL:
                return new MaterialViewHolder(inflater.inflate( R.layout.item_feeder_upload,parent, false), context);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder){
            if (position == 0){
                ((HeadViewHolder) holder).setText(R.id.tv_head , context.getString(R.string.feedingList) + mFeedingList.size());
            }else
                ((HeadViewHolder) holder).setText(R.id.tv_head , context.getString(R.string.materialList) + mMaterialList.size());
        } else if (holder instanceof FeederViewHolder){
            final UpLoadEntity.FeedingListBean item = mFeedingList.get(position - 1);
            ((FeederViewHolder)holder).setText(R.id.tv_material_id, "料号：" + item.getMaterial_no());
            ((FeederViewHolder)holder).setText(R.id.tv_slot, "FeederId：" + item.getFeeder_id());
            ((FeederViewHolder)holder).setText(R.id.tv_amount, "流水号：" + String.valueOf(item.getSerial_no()));
            ((FeederViewHolder)holder).setText(R.id.tv_issue, "架位：" + String.valueOf(item.getSlot()));
            final CheckBox mCheckBox = ((FeederViewHolder)holder).getView(R.id.cb_debit);
            mCheckBox.setChecked(item.isChecked());
            ((FeederViewHolder)holder).getView(R.id.al).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheckBox.setChecked(!item.isChecked());
                    item.setChecked(!item.isChecked());
                }
            });

        }else if (holder instanceof MaterialViewHolder){
            final UpLoadEntity.MaterialListBean item = mMaterialList.get(position - mFeedingList.size() - 2 );
            ((MaterialViewHolder)holder).setText(R.id.tv_material_id, "料号：" + item.getMaterial_no());
            ((MaterialViewHolder)holder).setText(R.id.tv_slot, "流水号：" + String.valueOf(item.getSerial_no()));
            ((MaterialViewHolder)holder).setText(R.id.tv_issue, "架位：" + String.valueOf(item.getSlot()));
            final CheckBox mCheckBox = ((MaterialViewHolder)holder).getView(R.id.cb_debit);
            mCheckBox.setVisibility(View.INVISIBLE);

        }



    }

    @Override
    public int getItemCount() {
        return mFeedingList == null && mMaterialList == null ? 0 : mFeedingList.size() + mMaterialList.size() + 2;
    }




    static class  FeederViewHolder extends  CommonViewHolder {
        public FeederViewHolder(View itemView, Context context) {
            super(itemView, context);
        }
    }

    static class MaterialViewHolder extends CommonViewHolder {
        public MaterialViewHolder(View itemView, Context context) {
            super(itemView, context);
        }
    }

    static class HeadViewHolder extends CommonViewHolder {
        public HeadViewHolder(View itemView, Context context) {
            super(itemView, context);
        }
    }
}
