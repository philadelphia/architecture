package com.delta.smt.ui.production_warning.accept_materials_detail.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.production_warining_item.ItemAcceptMaterialDetail;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */
@ActivityScope
public class AcceptMaterialsPresenter extends BasePresenter<AcceptMaterialsContract.Model,AcceptMaterialsContract.View>{

    @Inject
    public AcceptMaterialsPresenter(AcceptMaterialsContract.Model model, AcceptMaterialsContract.View mView) {
        super(model, mView);
    }

    //请求item数据
    public void getItemDatas(String line){

        Map<String,String> mMap=new HashMap<>();
        mMap.put("line",line);
        line=GsonTools.createGsonListString(mMap);
        Log.e("aaa", "getItemDatas: "+line);
        getModel().getAcceptMaterialsItemDatas(line)
                .subscribe(new Action1<ItemAcceptMaterialDetail>() {
            @Override
            public void call(ItemAcceptMaterialDetail itemAcceptMaterialDetail) {
                if (itemAcceptMaterialDetail.getCode().equals("0")) {
                    getView().getAcceptMaterialsItemDatas(itemAcceptMaterialDetail);
                }else{
                    getView().getItemDatasFailed(itemAcceptMaterialDetail.getMsg());

                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //提交新旧料盘数据
    public void commitSerialNumber(String line,String material_number,String oldSerialNumber, String newSerialNumber,String newBarcode){
        Log.e("aaa", "commitSerialNumber:old: "+oldSerialNumber );
        Log.e("aaa", "commitSerialNumber:new: "+newSerialNumber );

        Map<String, String> map = new HashMap<>();
        map.put("line", line);
        map.put("material_no", material_number);
        map.put("serial_no", newSerialNumber);
        map.put("old_material_no", material_number);
        map.put("old_serial_no", oldSerialNumber);
        map.put("barcode", newBarcode);

        String argu = GsonTools.createGsonListString(map);

        Log.e(TAG, "commitSerialNumber: "+argu );
        getModel().commitSerialNumber(argu).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                switch (result.getCode()){
                    case 0:
                        getView().commitSerialNumberSuccess();
                        break;
                    case -1:
//                        服务器错误。
                        break;
                    case -2:
                        getView().onNewMaterialNotExists(result.getMessage());
                        break;
                    case -3:
                        getView().onOldMaterialNotExists(result.getMessage());
                        break;
                    case -4:
//                        上传MES失败。暂时不实现。
                        break;
                    default:
                        getView().getItemDatasFailed(result.getMessage());
                        Log.e("aaa", "请求错误 "+result.getMessage());

                        break;
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemDatasFailed("Error");
                    Log.e("aaa", "请求异常 "+throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //提交料盘，feeder，料站成功数据
    public void commitarcoderDate(String partNumber, String slot
    ,String feeder,String line,String serialNumber,String barcode){
        Log.e("eee", "partNumber: "+partNumber );
        Log.e("eee", "slot: "+slot );
        Log.e("eee", "feeder: "+feeder );
        Log.e("eee", "line: "+line );
        Log.e("eee", "serialNumber: "+serialNumber );
        Log.e("eee", "barcode: "+barcode );

        Map<String, String> map = new HashMap<>();
        map.put("partNumber", partNumber);
        map.put("slot", slot);
        map.put("feeder", feeder);
        map.put("line", line);
        map.put("serialNumber", serialNumber);
        map.put("barcode", barcode);
        String argu =GsonTools.createGsonListString(map);


        getModel().commitSerialNumber(argu).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()) {
                    getView().commitSerialNumberSuccess();
                }else{
                    getView().getItemDatasFailed(result.getMessage());
                    Log.e("aaa", "请求错误 "+result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemDatasFailed("Error");
                    Log.e("aaa", "请求异常 "+throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //返回关灯请求
    public void requestCloseLight(String line){
        // XXX wenming.huang
        String[] temp = line.split("线别：");
        Log.d("onKeyDown",temp[1]);
        line = temp[1];

        Log.e(TAG, "requestCloseLight: "+line );
        Map<String,String> map=new HashMap<>();
        map.put("line",line);
        line= GsonTools.createGsonListString(map);

        getModel().requestCloseLight(line).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()) {
                    getView().showMessage("已关灯");
                }else{
                    getView().getItemDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
