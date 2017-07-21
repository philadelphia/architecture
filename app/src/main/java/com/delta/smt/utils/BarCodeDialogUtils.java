package com.delta.smt.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.delta.commonlibs.utils.DialogUtils;
import com.delta.demacia.barcode.Barcode;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/7/20 15:20
 */


public class BarCodeDialogUtils {

    public static Dialog showCommonDialog(Context context, String message,
                                          DialogInterface.OnClickListener listener, final Barcode mBarcode) {
        Dialog mDialog = DialogUtils.showCommonDialog(context, message, listener);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (mBarcode.isEventFromBarCode(event)){
                    return true;
                }
                return false;
            }
        });
        return mDialog;
    }
}
