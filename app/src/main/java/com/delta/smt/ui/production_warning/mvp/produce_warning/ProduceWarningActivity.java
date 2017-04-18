package com.delta.smt.ui.production_warning.mvp.produce_warning;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.widget.autolayout.AutoTabLayout;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.widget.DialogLayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BroadcastBegin;
import com.delta.smt.entity.BroadcastCancel;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.production_warning.di.produce_warning.DaggerTitleNumberCompent;
import com.delta.smt.ui.production_warning.di.produce_warning.TitleNumberModule;
import com.delta.smt.ui.production_warning.item.TitleNumber;
import com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment.ProduceBreakdownFragment;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragment;
import com.delta.smt.ui.production_warning.mvp.produce_warning_fragment.ProduceWarningFragment;
import com.delta.smt.utils.ViewUtils;
import com.delta.smt.widget.WarningDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningActivity extends BaseActivity<ProduceWarningPresenter> implements
        TabLayout.OnTabSelectedListener, ProduceWarningContract.View, WarningManger.OnWarning {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.tl_title)
    AutoTabLayout mTlTitle;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.statusLayout)
    StatusLayout mStatusLayout;
    private ProduceWarningFragment mProduceWarningFragment;
    private ProduceBreakdownFragment mProduceBreakdownFragment;
    private ProduceInfoFragment mProduceInfoFragment;
    private FragmentTransaction mFragmentTransaction;
    private SupportFragment currentFragment;
    private String[] titles;
    private String lines;
    private String[] line;

    @Inject
    WarningManger warningManger;
    private WarningDialog alertDialog;
    private boolean item_run_tag = false;
    private String lastWarningMessage;
    public Map<String, String> titleDatas = new HashMap<>();
    private int warning_number, breakdown_number, info_number;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerTitleNumberCompent.builder().appComponent(appComponent).titleNumberModule(new TitleNumberModule(this)).build().inject(this);
    }

    @Override
    public boolean UseEventBus() {
        return true;
    }

    @Override
    protected void initData() {

        Log.i("aaa", "选择进入：" + Constant.CONDITION);
        if (initLine() != null) {
            getPresenter().getTitileNumber(initLine());
        }
        if (warning_number == 0 && breakdown_number == 0 && info_number == 0) {
            titles = new String[]{"预警", "故障", "消息"};
        } else {
            titles = new String[]{"预警(" + warning_number + ")",
                    "故障(" + breakdown_number + ")",
                    "消息(" + info_number + ")"};
        }

//        Constant.CONDITION = null;
/*        if (getIntent().getExtras().getString(Constant.PRODUCTION_LINE)!=null&&!getIntent().getExtras().getString(Constant.PRODUCTION_LINE).equals("")) {
            Constant.CONDITION = getIntent().getExtras().getString(Constant.PRODUCTION_LINE);
        }*/

        lines=Constant.CONDITION;
        line=lines.split(",");


        //注册广播初始化
        warningManger.addWarning(String.valueOf(Constant.PRODUCTION_LINE_ALARM_FLAG), getClass());
        warningManger.addWarning(String.valueOf(Constant.OPERATOR_FAULT_ALARM_FLAG), getClass());
        for (int mI = 0; mI < line.length; mI++) {
            Log.e("eee", "initData: "+ line[mI]);
            //需要定制的信息
            warningManger.sendMessage(new SendMessage(Constant.PRODUCTION_LINE_ALARM_FLAG+"_"+line[mI], 0));
            warningManger.sendMessage(new SendMessage(Constant.OPERATOR_FAULT_ALARM_FLAG+"_"+line[mI], 0));
        }

        warningManger.setReceive(true);
        warningManger.setOnWarning(this);
    }


    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("生产中预警");

        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                mTlTitle.addTab(mTlTitle.newTab());
            }
            ViewUtils.setTabTitle(mTlTitle, titles);
            mTlTitle.addOnTabSelectedListener(this);
        }

        mProduceBreakdownFragment = new ProduceBreakdownFragment();
        mProduceInfoFragment = new ProduceInfoFragment();
        mProduceWarningFragment = new ProduceWarningFragment();
        loadMultipleRootFragment(R.id.fl_container, 0, mProduceWarningFragment, mProduceBreakdownFragment, mProduceInfoFragment);
        currentFragment = mProduceWarningFragment;

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_warning;
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: ");
        warningManger.registerWReceiver(this);
        getPresenter().getTitileNumber(initLine());
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        warningManger.unregisterWReceiver(this);
        for (int mI = 0; mI < line.length; mI++) {
            //需要定制的信息
//            warningManger.sendMessage(new SendMessage(Constant.PRODUCTION_LINE_ALARM_FLAG+"-"+line[mI], 0));
            warningManger.sendMessage(new SendMessage(Constant.PRODUCTION_LINE_ALARM_FLAG+"_"+line[mI],1));
            warningManger.sendMessage(new SendMessage(Constant.OPERATOR_FAULT_ALARM_FLAG+"_"+line[mI],1));
        }
        super.onDestroy();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                showHideFragment(mProduceWarningFragment, currentFragment);
                currentFragment = mProduceWarningFragment;
                break;
            case 1:
                showHideFragment(mProduceBreakdownFragment, currentFragment);
                currentFragment = mProduceBreakdownFragment;
                break;
            case 2:
                showHideFragment(mProduceInfoFragment, currentFragment);
                currentFragment = mProduceInfoFragment;
                break;
            default:
                break;

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }


    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void getTitleDatas(TitleNumber titleNumber) {

        warning_number = titleNumber.getWarning_number();
        breakdown_number = titleNumber.getBreakdown_number();
        info_number = titleNumber.getInfo_number();

        if (warning_number == 0 && breakdown_number == 0 && info_number == 0) {
            titles = new String[]{"预警", "故障", "消息"};
        } else {
            titles = new String[]{"预警(" + warning_number + ")",
                    "故障(" + breakdown_number + ")",
                    "消息(" + info_number + ")"};
        }
        ViewUtils.setTabTitle(mTlTitle, titles);
    }

    @Override
    public void getTitleDatasFailed(String message) {
//        ToastUtils.showMessage(this, message);
        if ("Error".equals(message)) {
            Snackbar.make(getCurrentFocus(),this.getString(R.string.server_error_message),Snackbar.LENGTH_LONG).show();
        }else {
            Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
        }
        titles = new String[]{"预警", "故障", "消息"};
    }
    private WarningDialog warningDialog;

    //收到预警广播触发的方法
    @Override
    public void warningComing(String message) {
        //showDialog(warningMessage);
        Log.e(TAG, "warningComing: " + message);

        if (warningDialog == null) {
            warningDialog = createDialog(message);
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage(message);
    }

    private void updateMessage(String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        List<String> types = new ArrayList<>();
//        List<String> content=new ArrayList<>();
        String content = "";
        datas.clear();

        try {
            JSONArray jsonArray= new JSONArray(message);
            for (int mI = 0; mI < jsonArray.length(); mI++) {
                JSONObject jsonObject = jsonArray.getJSONObject(mI);
                String type = jsonObject.getString("type");
                String[] split = type.split("_");
                type = split[0];

                if(!types.contains(type)){
                    types.add(type);
                    WaringDialogEntity warningEntity = new WaringDialogEntity();
                    datas.add(warningEntity);
                }

            }
            for (int i1 = 0; i1 < types.size(); i1++) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String type = jsonObject.getString("type");
                    String[] split = type.split("_");
                    if (types.get(i1).equals(split[0])) {

                        if (datas.get(i1).getContent()!=null) {
                            content = datas.get(i1).getContent();
                        }
                        if(Constant.PRODUCTION_LINE_ALARM_FLAG.equals(split[0])){
                            datas.get(i1).setTitle("接料预警：");
                        }else if(Constant.OPERATOR_FAULT_ALARM_FLAG.equals(split[0])){
                            datas.get(i1).setTitle("操作员故障预警：");
                        }
                        Object message1 = jsonObject.get("message");
                        datas.get(i1).setContent(content+message1 + "\n");
                    }
                }
            }
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }



/*        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                //可能有多种预警的情况
                Object message1 = jsonObject.get("message");
                content = content + message1 + "\n";
            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
    private List<WaringDialogEntity> getWarningEntities(JSONArray jsonArray) throws JSONException {
        List<String> types = new ArrayList<>();
        List<WaringDialogEntity> waringDialogEntities = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String type = jsonObject.getString("type");
            String[] split = type.split("_");
            type = split[0];
            types.add(type);
            WaringDialogEntity waringDialogEntity = new WaringDialogEntity();
            if (titleDatas.containsKey(type)) {
                if (split.length==1){
                    waringDialogEntity.setTitle(titleDatas.get(type));
                }else {
                    waringDialogEntity.setTitle(split[1] + titleDatas.get(type));
                }
                waringDialogEntity.setContent("");
            }
            waringDialogEntities.add(waringDialogEntity);
        }
        for (int i1 = 0; i1 < types.size(); i1++) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String type = jsonObject.getString("type");
                String[] split = type.split("_");
                if (types.get(i1).equals(split[0])) {
                    String content = waringDialogEntities.get(i1).getContent();
                    Object message1 = jsonObject.get("message");
                    waringDialogEntities.get(i1).setContent(content + message1 + "\n");
                }
            }
        }

        return waringDialogEntities;
    }

    public WarningDialog createDialog(String message) {
        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
//                getPresenter().getAllOverReceiveItems();
                getPresenter().getTitileNumber(initLine());
                warningDialog.dismiss();
            }
        });
        warningDialog.show();

        return warningDialog;
    }

    /*private AlertDialog createDialog(final String warningMessage) {
        DialogLayout dialogLayout = new DialogLayout(this);
        //3.传入的是黑色字体的二级标题
        dialogLayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogLayout.setStrContent(datas);
        return new AlertDialog.Builder(this).setCancelable(false).setView(dialogLayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EventBus.getDefault().post(new ProduceWarningMessage(warningMessage));
                dialog.dismiss();
            }
        }).show();
    }*/

    //Fragment点击item触发事件处理
    @Subscribe
    public void event(BroadcastCancel broadcastCancel) {
        item_run_tag = true;
        Log.e(TAG, "event4: ");
    }

    //Fragment中item处理完触发事件处理
    @Subscribe
    public void event(BroadcastBegin broadcastbegin) {
        item_run_tag = false;
        if (lastWarningMessage != null) {
            alertDialog = createDialog(lastWarningMessage);
            lastWarningMessage = null;
        }
        if (initLine() != null) {
            getPresenter().getTitileNumber(initLine());
        }

        Log.e(TAG, "event5: ");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String initLine() {
        Map<String, String> map = new HashMap<>();
        map.put("lines", Constant.CONDITION);
        String line = GsonTools.createGsonString(map);
        return line;
    }

    /**
     * @description :根据不同的数据状态显示不同的view
     */
    @Override
    public void showLoadingView() {
        mStatusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        mStatusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        mStatusLayout.showErrorView();
        mStatusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getTitileNumber(initLine());
            }
        });
    }

    @Override
    public void showEmptyView() {
        mStatusLayout.showEmptyView();
        mStatusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getTitileNumber(initLine());
            }
        });
    }


}
