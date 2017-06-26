package com.delta.commonlibs.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.delta.commonlibs.R;
import com.delta.commonlibs.widget.CustomPopWindow;

import static com.delta.commonlibs.utils.ViewUtils.findView;

/**
 * @author :  V.Wenju.Tian
 * @description :对话框工具类, 提供常用对话框显示, 使用support.v7包内的AlertDialog样式
 * @date : 2016/12/19 14:05
 */
public class DialogUtils {


    public static Dialog createProgressDialog(Context context) {
        return createProgressDialog(context, true);
    }

    public static Dialog createProgressDialog(Context context, boolean needCancle) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(needCancle);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog showCommonDialog(Context context, String message,
                                          DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", listener)
                .setNegativeButton("取消", null)
                .show();
    }

    public static Dialog showCommonDialogWithDeltaBlue(Context context, String message,
                                          DialogInterface.OnClickListener listener, @ColorRes int color_resouce_id) {
        AlertDialog dialog = (AlertDialog) showCommonDialog(context, message, listener);
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button positiveNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveButton.setBackgroundColor(ContextCompat.getColor(context, color_resouce_id));
        positiveNegative.setBackgroundColor(ContextCompat.getColor(context,color_resouce_id));
        return  dialog;
    }

    public static Dialog showConfirmDialog(Context context, String message,
                                           DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", listener)
                .show();
    }

    public static Dialog showDefineDialog(Context context, View view) {

        return new AlertDialog.Builder(context).setView(view).show();
    }

    public static CustomPopWindow createLoadingDialog(Context mContext, @Nullable String message) {


        CustomPopWindow mCustomPopWindow = CustomPopWindow.builder().with(mContext).setView(R.layout.dialog_loading).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setOutsideTouchable(false).setTouchable(false).build();
        ImageView mPoint = findView(mCustomPopWindow.getContentView(), R.id.point);
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_PARENT, 0.37f, Animation.RELATIVE_TO_PARENT, 0.37f);
        ra.setDuration(2000);
        TextView tv_loading = ViewUtils.findView(mCustomPopWindow.getContentView(), R.id.tv_loading);
        if (message != null) {

            tv_loading.setText(message);
        }
        ra.setRepeatCount(Animation.INFINITE);
        ra.setRepeatMode(Animation.RESTART);
        mPoint.startAnimation(ra);
        return mCustomPopWindow;
    }

    public static void loadingViewDismiss(CustomPopWindow mCustomPopWindow) {

        if (mCustomPopWindow != null) {
            mCustomPopWindow.dissmiss();
            ImageView mPoint = findView(mCustomPopWindow.getContentView(), R.id.point);
            mPoint.clearAnimation();
        }
    }


}
