package com.delta.smt.utils;

import android.text.TextUtils;
import android.util.Log;

import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;

import java.util.regex.Pattern;


/**
 * Created by Lin.Hou on 2017-01-03.
 */

public class BarCodeUtils {
    private static final String BACKUPMATERIIALCAR= "^SMT-A[0-9]{2}$";
    private static final String FBOX= "^Fbox-3[0-9]{3}$";
    private static final String FEEDER= "^KT[0-9]{1}[A-Z]{2}.{1}[0-9]{6}$";
    private static final String FEEDERCAR= "^FeederCar-A[0-9]{2}$";
    private static final String FRAMELOCATION= "^[0-9A-Z]{6}-[0-9A-Z]{1}$";
    private static final String MATERIALBLOCKBARCODE= "^[0-9]{10}.{2}[0-9]{3}.{2}[0-9a-z]{3}.{2}[0-9]{6}.{2}[0-9]{4}.*$";
    private static final String MATERIALSTATION= "^[0-9]{3}$";
    private static final String RBOX= "^RBox-[0-9]{3}$";



    public static    BarCodeType barCodeType(String s)throws NullPointerException {
        if (!TextUtils.isEmpty(s)){
        if ( Pattern.compile(BACKUPMATERIIALCAR).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(BACKUPMATERIIALCAR).matcher(s).matches());
            return BarCodeType.BACKUP_MATERIAL_CAR;
        }
        if (Pattern.compile(FBOX).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FBOX).matcher(s).matches());
            return BarCodeType.FBOX;
        }
        if (Pattern.compile(FEEDER).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FEEDER).matcher(s).matches());
                return BarCodeType.FEEDER;
        }
        if (Pattern.compile(FEEDERCAR).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FEEDERCAR).matcher(s).matches());
                return BarCodeType.FEEDER_CAR;
        }
        if (Pattern.compile(FRAMELOCATION).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FRAMELOCATION).matcher(s).matches());
            return BarCodeType.FRAME_LOCATION;
        }
        if (Pattern.compile(MATERIALBLOCKBARCODE).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(MATERIALBLOCKBARCODE).matcher(s).matches());
            return BarCodeType.MATERIAL_BLOCK_BARCODE;
        }
        if (Pattern.compile(MATERIALSTATION).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(MATERIALSTATION).matcher(s).matches());
            return BarCodeType.MATERIAL_STATION;
        }
        if (Pattern.compile(RBOX).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(RBOX).matcher(s).matches());
            return BarCodeType.RBOX;
        }

         }
        return  null;

    }
}
