package com.delta.smt.ui.checkstock.mvp;

 import android.util.Log;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.api.ApiService;
 import com.delta.smt.app.App;
 import com.delta.smt.entity.CheckStock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Verifier;
 import org.junit.runner.RunWith;
 import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dagger.Module;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Lin.Hou on 2017/8/16.
 */

public class CheckStockPresenterTest {

    private  final String TAG="CheckStockPresenterTest";
    @Mock
    CheckStockContract.View view;
    @Mock
    CheckStockContract.Model model;
    @Mock
    ApiService apiserver;

    CheckStockPresenter checkStockPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        checkStockPresenter=  new CheckStockPresenter(model,view);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void fetchCheckStock() throws Exception {
//        model.getCheckStock("1234");
        Map<String,String> map=new HashMap<>();
        map.put("labelCode","12345");
        String json = GsonTools.createGsonListString(map);
        //验证网络请求是否执行
        model.getCheckStock(json);
        verify(model).getCheckStock(json);
        //使用mock数据，验证网络强求是否符合自己设想
        CheckStock checkstock1=new CheckStock();
        checkstock1.setCode("1");
        checkstock1.setMessage("success");
        List<CheckStock.RowsBean> list=new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add(new CheckStock.RowsBean());
        }
        checkstock1.setRows(list);
        when(model.getCheckStock(json)).thenReturn(Observable.just(checkstock1));
        model.getCheckStock(json).subscribe(new Action1<CheckStock>() {
            @Override
            public void call(CheckStock checkStock) {
                assertEquals(checkStock.getCode(),"1");
                assertEquals(checkStock.getMessage(),"success");
                assertThat(checkStock.getRows().size(),is(5));//判断有几条数据，is()参数入的是几条
            }
        });



    }

    @Test
    public void fetchCheckStockSuccessNumber() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("labelCode","T-000001");
        String json = GsonTools.createGsonListString(map);

        //模拟真实的网络请求
        model.getCheckStock(json).subscribe(new Action1<CheckStock>() {
            @Override
            public void call(CheckStock checkStock) {
                assertEquals(checkStock.getCode(), "0");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    @Test
    public void fetchError() throws Exception {

    }

    @Test
    public void fetchException() throws Exception {

    }

    @Test
    public void fetchSubmit() throws Exception {

    }

    @Test
    public void fetchCheckStockSuccess() throws Exception {

    }

    @Test
    public void fetchJudgeSuceess() throws Exception {

    }

}