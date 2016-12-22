package com.delta.smt.common;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.delta.smt.R;

import java.util.ArrayList;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2016
 * @creator : YangS
 * @create-time : 2016/12/22 16:31
 * @description :自定义的Dialog的View
 */

public class DialogRelativelayout extends LinearLayout {

    private Context context;

    public DialogRelativelayout(Context context) {
        super(context);
        this.context = context;
        this.setOrientation(VERTICAL);
        this.setPadding(dip2px(context,16),dip2px(context,8),10,0);
    }

    public DialogRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
    }

    public DialogRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
    }



    /**
     * 传入的是一级标题，红色的
     * @param strTitle
     */
    public void setStrTitle(String strTitle) {

        TextView tvTitle = new TextView(context);
        tvTitle.setTextColor(Color.RED);
        tvTitle.setText(strTitle);
        tvTitle.setTextSize(px2sp(context,16));

        LayoutParams param=new LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(0,0,0,0);
        tvTitle.setLayoutParams(param);

        this.addView(tvTitle);

    }

    /**
     * 传入的是二级标题，黑色
     * @param strSecondTitle
     */
    public void setStrSecondTitle(String strSecondTitle) {

        TextView tvSecondTitle = new TextView(context);
        tvSecondTitle.setText(strSecondTitle);
        tvSecondTitle.setTextSize(px2sp(context,14));
        tvSecondTitle.setTextColor(Color.parseColor("#333333"));

        LayoutParams param=new LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(dip2px(context,12),dip2px(context,6),0,dip2px(context,6));
        tvSecondTitle.setLayoutParams(param);

        this.addView(tvSecondTitle);
    }

    /**
     * 传入的是diglog的listview的ArrayList。
     * @param arrayListContent
     */

    public void setStrContent(final ArrayList<String> arrayListContent) {

        ListView listView = new ListView(context);
        listView.setDivider(null);
        listView.setAdapter(new ArrayAdapter<String>(context, R.layout.warn_dialog_item,arrayListContent));
        this.addView(listView);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
