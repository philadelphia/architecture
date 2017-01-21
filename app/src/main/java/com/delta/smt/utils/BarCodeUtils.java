package com.delta.smt.utils;

import android.text.TextUtils;
import android.util.Log;

import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.BackupMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.FBox;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.FeederCar;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.MaterialStation;
import com.delta.buletoothio.barcode.parse.entity.RBox;

import java.util.regex.Pattern;


/**
 * Created by Lin.Hou on 2017-01-03.
 */

public class BarCodeUtils {

/*    private static final String BACKUPMATERIIALCAR= "^SMT-A[0-9]{2}$";
    private static final String FBOX= "^Fbox-[0-9]{3}$";
    private static final String FEEDER= "^KT[0-9A-Z]+";
    private static final String FEEDERCAR= "^FeederCar-A[0-9]{2}$";
    private static final String FRAMELOCATION= "^[0-9A-Z]{6}-[0-9A-Z]{1}$";
    private static final String MATERIALBLOCKBARCODE= "^[0-9A-Z]{10,18}\\{.*";
    private static final String MATERIALSTATION= "^[0-9]{3}$";
    private static final String RBOX= "^Rbox-[0-9]{3}$";*/

    public static BarCodeType barCodeType(String s){
        if (!TextUtils.isEmpty(s)){

            if ( Pattern.compile(BackupMaterialCar.myResEx).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(BackupMaterialCar.myResEx).matcher(s).matches());
                return BarCodeType.BACKUP_MATERIAL_CAR;
            }
            if (Pattern.compile(MaterialBlockBarCode.myResEx).matcher(s).matches()){
                Log.i("barcodeUtils",""+Pattern.compile(MaterialBlockBarCode.myResEx).matcher(s).matches());
                return BarCodeType.MATERIAL_BLOCK_BARCODE;
            }
            if (Pattern.compile(FBox.myResEx).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FBox.myResEx).matcher(s).matches());
                return BarCodeType.FBOX;
            }
            if (Pattern.compile(Feeder.myResEx).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(Feeder.myResEx).matcher(s).matches());
                return BarCodeType.FEEDER;
            }
            if (Pattern.compile(FeederCar.myResEx).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FeederCar.myResEx).matcher(s).matches());
                return BarCodeType.FEEDER_CAR;
            }
            if (Pattern.compile(FrameLocation.myResEx).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(FrameLocation.myResEx).matcher(s).matches());
                return BarCodeType.FRAME_LOCATION;
            }
            if (Pattern.compile(MaterialStation.myResEx).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(MaterialStation.myResEx).matcher(s).matches());
                return BarCodeType.MATERIAL_STATION;
            }
            if (Pattern.compile(RBox.myResEx).matcher(s).matches()){
            Log.i("barcodeUtils",""+Pattern.compile(RBox.myResEx).matcher(s).matches());
                return BarCodeType.RBOX;
            }

        }
        return  null;
    }
}
