package com.delta.smt.utils;

import android.text.TextUtils;
import android.util.Log;

import com.delta.buletoothio.barcode.parse.BarCodeType;

import java.util.IdentityHashMap;
import java.util.IllegalFormatCodePointException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.delta.buletoothio.barcode.parse.BarCodeType.FRAME_LOCATION;


/**
 * Created by Lin.Hou on 2017-01-03.
 */

public class BarCodeUtils {
    private static final String BACKUPMATERIIALCAR= "^SMT-A[0-9]{2}$";
    private static final String FBOX= "^Fbox-[0-9]{3}$";
    private static final String FEEDER= "^KT[0-9A-Z]+";
    private static final String FEEDERCAR= "^FeederCar-A[0-9]{2}$";
    private static final String FRAMELOCATION= "^[0-9A-Z]{6}-[0-9A-Z]{1}$";
    private static final String MATERIALBLOCKBARCODE= "^[0-9]{10}.\\{[0-9]{10}$";
    private static final String MATERIALSTATION= "^[0-9]{3}$";
    private static final String RBOX= "^Rbox-[0-9]{3}$";



    public static BarCodeType barCodeType(String s){
        if (!TextUtils.isEmpty(s)){
            if ( Pattern.compile(BACKUPMATERIIALCAR).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(BACKUPMATERIIALCAR).matcher(s).matches());
                return BarCodeType.BACKUP_MATERIAL_CAR;
        }
            else if (Pattern.compile(FBOX).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FBOX).matcher(s).matches());
                return BarCodeType.FBOX;
        }
            else if (Pattern.compile(FEEDER).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FEEDER).matcher(s).matches());
                return BarCodeType.FEEDER;
        }
            else if (Pattern.compile(FEEDERCAR).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FEEDERCAR).matcher(s).matches());
                return BarCodeType.FEEDER_CAR;
        }
            else if (Pattern.compile(FRAMELOCATION).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FRAMELOCATION).matcher(s).matches());
                return BarCodeType.FRAME_LOCATION;
        }

            else if (Pattern.compile(MATERIALSTATION).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(MATERIALSTATION).matcher(s).matches());
                return BarCodeType.MATERIAL_STATION;
        }
            else if (Pattern.compile(RBOX).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(RBOX).matcher(s).matches());
                return BarCodeType.RBOX;
        }
            else if (Pattern.compile(MATERIALBLOCKBARCODE).matcher(s).matches()){
                Log.i("barcodeUtils",""+Pattern.compile(MATERIALBLOCKBARCODE).matcher(s).matches());
                return BarCodeType.MATERIAL_BLOCK_BARCODE;
            }
        }
        return  null;
    }
}
