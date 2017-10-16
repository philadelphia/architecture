package com.delta.smt.ui.fault_processing.processing;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.dropdownfiltermenu.DropDownMenu;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.libs.adapter.ItemTimeViewHolder;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FaultFilter;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.FaultMessage.RowsBean;
import com.delta.smt.entity.FaultMessage.RowsBean.FiltersBean;
import com.delta.smt.entity.FaultParameter;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.SolutionMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.fault_processing.fault_add.FaultProcessingAddActivity;
import com.delta.smt.ui.fault_processing.fault_solution.FaultSolutionDetailActivity;
import com.delta.smt.ui.fault_processing.processing.di.DaggerFaultProcessingComponent;
import com.delta.smt.ui.fault_processing.processing.di.FaultProcessingModule;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingContract;
import com.delta.smt.ui.fault_processing.processing.mvp.FaultProcessingPresenter;
import com.delta.commonlibs.utils.ViewUtils;
import com.delta.smt.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.commonlibs.utils.ViewUtils.findView;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:02
 */

public class FaultProcessingActivity extends BaseActivity<FaultProcessingPresenter> implements FalutProcessingContract.View, WarningManger.OnWarning, ItemOnclick<RowsBean.FailuresBean>, View.OnClickListener {

    RecyclerView rvFaultProcessing;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    StatusLayout statusLayout;
    @Inject
    WarningManger warningManger;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private List<RowsBean.FailuresBean> failuresBeen = new ArrayList<>();
    List<SolutionMessage.RowsBean> solutionDataList = new ArrayList<>();
    // private CommonBaseAdapter<RowsBean> mMyAdapter;
    CommonBaseAdapter<SolutionMessage.RowsBean> dialog_adapter;
    private String lines;
    private LinearLayoutManager manager;
    private ItemCountViewAdapter<RowsBean.FailuresBean> mMyAdapter;
    private FaultParameter faultParameter;
    private String parameter;
    private BottomSheetDialog bottomSheetDialog;
    private TextView tv_sheet_title;
    private RowsBean.FailuresBean item = new RowsBean.FailuresBean();
    private List<View> popupViews = new ArrayList<>();
    private List<FiltersBean> faultFilterDatas = new ArrayList<>();
    private String[] lineNames;
    private WarningDialog warningDialog;
    private RecyclerView filterRecyclerView;
    private CommonBaseAdapter<FiltersBean> faultFilterAdapter;
    private int checkPosition;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingComponent.builder().appComponent(appComponent).faultProcessingModule(new FaultProcessingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        lines = SpUtil.getStringSF(this, Constant.FAULT_PROCESSING_LINE_NAME);
        faultParameter = new FaultParameter();
        faultParameter.setLines(lines);
        faultParameter.setProcesses("");
        parameter = GsonTools.createGsonListString(faultParameter);
        warningManger.addWarning(Constant.ENGINEER_FAULT_ALARM_FLAG, this.getClass());
        warningManger.setReceive(true);
        warningManger.setOnWarning(this);

        if (lines != null) {

            lineNames = lines.split(",");

            for (String lineName : lineNames) {

                warningManger.sendMessage(new SendMessage(Constant.ENGINEER_FAULT_ALARM_FLAG + "_" + lineName, 0));
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        warningManger.registerWReceiver(this);
        getPresenter().getFaultProcessingMessages(parameter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        warningManger.unregisterWReceiver(this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("故障处理预警");
        //toolbar.setOnMenuItemClickListener(this);
        filterRecyclerView = new RecyclerView(this);
        filterRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        popupViews.add(filterRecyclerView);
        View contentView = LayoutInflater.from(this).inflate(R.layout.recycleview_content, null);
        rvFaultProcessing = ViewUtils.findView(contentView, R.id.rv_faultProcessing);
        statusLayout = ViewUtils.findView(contentView, R.id.statusLayouts);
        dropDownMenu.setDropDownMenu(Collections.singletonList("故障类型：全部"), popupViews, contentView);
        mMyAdapter = new ItemCountViewAdapter<RowsBean.FailuresBean>(this, failuresBeen) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_processing;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, RowsBean.FailuresBean faultMessage, int position) {
                holder.setText(R.id.tv_line, "产线：" + faultMessage.getLine());
                holder.setText(R.id.tv_name, faultMessage.getProcess() + "-" + faultMessage.getLine());
                holder.setText(R.id.tv_processing, "制程：" + faultMessage.getProcess());
                holder.setText(R.id.tv_faultMessage, "故障信息：" + faultMessage.getChild_exception_name());
                holder.setText(R.id.tv_code, "故障代码：" + faultMessage.getException_code());
            }
        };

        manager = new LinearLayoutManager(this);
        rvFaultProcessing.setLayoutManager(manager);
        rvFaultProcessing.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemTimeOnclick(this);
        checkPosition = -1;
        faultFilterAdapter = new CommonBaseAdapter<FiltersBean>(this, faultFilterDatas) {

            @Override
            protected void convert(CommonViewHolder holder, FiltersBean item, int position) {
                holder.setText(R.id.text, item.getMain_exception_name());
                if (checkPosition == position) {
                    holder.setTextColor(R.id.text, FaultProcessingActivity.this.getResources().getColor(R.color.c_428bca));
                    holder.setBackgroundColor(R.id.text, FaultProcessingActivity.this.getResources().getColor(R.color.gray));
                } else {
                    holder.setTextColor(R.id.text, FaultProcessingActivity.this.getResources().getColor(R.color.c_111111));
                    holder.setBackgroundColor(R.id.text, FaultProcessingActivity.this.getResources().getColor(R.color.white));
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, FiltersBean item) {
                return R.layout.item_default_drop_down;
            }

//            public void setCheckPositon(int position){
//                this.checkPosition =position;
//                notifyDataSetChanged();
//            }

        };
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterRecyclerView.setAdapter(faultFilterAdapter);
        faultFilterAdapter.setOnItemClickListener(new CommonBaseAdapter.OnItemClickListener<RowsBean.FiltersBean>() {
            @Override
            public void onItemClick(View view, RowsBean.FiltersBean item, int position) {
                dropDownMenu.setTabText(position == 0 ? "故障类型：全部" : "" + item.getMain_exception_name());
                String tabText = dropDownMenu.getTabText();
                if ("故障类型：全部".equals(tabText)) {
                    tabText = "";
                }
                checkPosition = position;
                faultFilterAdapter.notifyDataSetChanged();
                faultParameter.setProcesses(tabText);
                parameter = GsonTools.createGsonListString(faultParameter);
                getPresenter().getFaultProcessingMessages(parameter);
                dropDownMenu.closeMenu();
            }
        });
        createBottomSheetDialog();

    }

    @Override
    public void onBackPressedSupport() {

        //退出activity前关闭菜单
        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_fault_processing, menu);
        return true;
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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fault_processing;
    }


    @Override
    public void getFaultMessageSuccess(FaultMessage faultMessage) {

        //getPresenter().getFaultFilterMessage();
        failuresBeen.clear();
        List<RowsBean.FailuresBean> rows = faultMessage.getRows().getFailures();
        for (int i = 0; i < rows.size(); i++) {
            rows.get(i).setCreat_time(System.currentTimeMillis() - Math.round(rows.get(i).getDuration_time()) * 1000);
            rows.get(i).setEntityId(i);
        }
        failuresBeen.addAll(rows);
        mMyAdapter.notifyDataSetChanged();
        faultFilterDatas.clear();
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setMain_exception_name("不限");
        faultFilterDatas.add(filtersBean);
        faultFilterDatas.addAll(faultMessage.getRows().getFilters());
        faultFilterAdapter.notifyDataSetChanged();
    }

    @Override
    public void getFaultMessageFailed(String message) {

        SnackbarUtil.show(statusLayout, getString(R.string.server_error_message));
    }

    @Override
    public void getSolutionMessageSuccess(List<SolutionMessage.RowsBean> rowsBeen) {


        solutionDataList.clear();
        solutionDataList.addAll(rowsBeen);
        dialog_adapter.notifyDataSetChanged();

    }

    @Override
    public void showLoadingView() {
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {

        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {

        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getFaultProcessingMessages(parameter);
            }
        });
    }

    @Override
    public void showEmptyView() {

        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getFaultProcessingMessages(parameter);
            }
        });
    }

    @Override
    public void getFaultFilterMessageFailed() {

    }

    @Override
    public void getFaultFilterMessageSuccess(FaultFilter faultFilter) {

//        faultFilterDatas.clear();
//        faultFilterDatas.addAll(faultFilter.);
//        faultFilterAdapter.notifyDataSetChanged();

    }

    @OnClick({R.id.tv_setting})
    public void onClick() {
    }

    @Override
    public void warningComing(String warningMessage) {

        if (warningDialog == null) {
            warningDialog = createDialog(warningMessage);
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage(warningDialog, warningMessage);
    }


    /**
     * type == 9  代表你要发送的是哪个
     *
     * @param message
     */
    private void updateMessage(WarningDialog warningDialog, String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("工程师故障预警");
        String content = "";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Object message1 = jsonObject.get("message");
                content = content + message1 + "\n";
            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public WarningDialog createDialog(String message) {

        final WarningDialog warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
                getPresenter().getFaultProcessingMessages(parameter);
            }
        });
        warningDialog.show();

        return warningDialog;
    }

    @Override
    protected void handError(String contents) {
        super.handError(contents);
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getFaultProcessingMessages(parameter);
            }
        });
    }

    private void createBottomSheetDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialogview_fault_processing, null);
        tv_sheet_title = findView(view, R.id.tv_title);
        RecyclerView rv_ll = findView(view, R.id.rv_processing_dialog);
        TextView tv_add = findView(view, R.id.tv_add);
        tv_add.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_add.getPaint().setAntiAlias(true);//抗锯齿
        tv_add.setOnClickListener(this);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(Constant.FAULT_CODE, item.getException_code());
                IntentUtils.showIntent(FaultProcessingActivity.this, FaultProcessingAddActivity.class, bundle);
                bottomSheetDialog.dismiss();
            }
        });
        dialog_adapter = new CommonBaseAdapter<SolutionMessage.RowsBean>(this, solutionDataList) {
            @Override
            protected void convert(CommonViewHolder holder, SolutionMessage.RowsBean item, int position) {

                TextView textView = holder.getView(R.id.tv_content);
                textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                textView.getPaint().setAntiAlias(true);//抗锯齿
                textView.setText(Html.fromHtml(item.getName()));
            }

            @Override
            protected int getItemViewLayoutId(int position, SolutionMessage.RowsBean item) {
                return R.layout.item_fault_processing_dialog;
            }
        };

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        rv_ll.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        rv_ll.setLayoutManager(linearLayoutManager);
        rv_ll.setAdapter(dialog_adapter);
        dialog_adapter.setOnItemClickListener(new CommonBaseAdapter.OnItemClickListener<SolutionMessage.RowsBean>() {
            @Override
            public void onItemClick(View view, SolutionMessage.RowsBean rowsBean, int position) {
                Bundle bundle = new Bundle();
                //bundle.putString(Constant.FAULT_ID, String.valueOf(item.get));
                bundle.putString(Constant.FAULT_CODE, item.getException_code());
                bundle.putString(Constant.FAULT_PROCESSING_LINE_NAME, String.valueOf(item.getLine()));
                bundle.putString(Constant.FAULT_SOLUTION_NAME, rowsBean.getName());
                IntentUtils.showIntent(FaultProcessingActivity.this, FaultSolutionDetailActivity.class, bundle);
                bottomSheetDialog.dismiss();

            }
        });
        //从bottomSheetDialog拿到behavior
        final BottomSheetBehavior<View> mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet));
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheetDialog.dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onItemClick(final View ItemView, final RowsBean.FailuresBean item, int position) {

        getPresenter().getSolution(item.getException_code());
        this.item = item;
        solutionDataList.clear();
        tv_sheet_title.setText(item.getException_name() + " " + item.getException_code());
        bottomSheetDialog.show();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        for (String lineName : lineNames) {

            warningManger.sendMessage(new SendMessage(Constant.ENGINEER_FAULT_ALARM_FLAG + "_" + lineName, 1));
        }
        super.onDestroy();
    }


}
