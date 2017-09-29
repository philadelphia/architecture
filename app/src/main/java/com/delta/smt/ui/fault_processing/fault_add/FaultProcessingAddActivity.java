package com.delta.smt.ui.fault_processing.fault_add;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.utils.DialogUtils;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.utils.ViewUtils;
import com.delta.commonlibs.widget.CustomPopWindow;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.fault_add.di.DaggerFaultProcessingAddComponent;
import com.delta.smt.ui.fault_processing.fault_add.di.FaultProcessingAddModule;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddContract;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddPresenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author :  V.Wenju.Tian
 * @description :
 * @date : 2017/1/9 19:40
 */


public class FaultProcessingAddActivity extends BaseActivity<FaultProcessingAddPresenter> implements FaultProcessingAddContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.editor)
    RichEditor richEditor;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.action_bold)
    ImageButton actionBold;
    @BindView(R.id.action_italic)
    ImageButton actionItalic;
    @BindView(R.id.action_underline)
    ImageButton actionUnderline;
    @BindView(R.id.action_heading1)
    ImageButton actionHeading1;
    @BindView(R.id.action_heading2)
    ImageButton actionHeading2;
    @BindView(R.id.action_heading3)
    ImageButton actionHeading3;
    @BindView(R.id.action_outdent)
    ImageButton actionOutdent;
    @BindView(R.id.action_align_left)
    ImageButton actionAlignLeft;
    @BindView(R.id.action_align_center)
    ImageButton actionAlignCenter;
    @BindView(R.id.action_align_right)
    ImageButton actionAlignRight;
    @BindView(R.id.action_insert_bullets)
    ImageButton actionInsertBullets;
    @BindView(R.id.action_insert_numbers)
    ImageButton actionInsertNumbers;
    @BindView(R.id.action_blockquote)
    ImageButton actionBlockQuote;
    @BindView(R.id.action_insert_image)
    ImageButton actionInsertImage;
    @BindView(R.id.action_insert_link)
    ImageButton actionInsertLink;
    @BindView(R.id.sl)
    StatusLayout mSl;
    private String fileName = null;
    private String faultCode = "";
    private File file;
    private CustomPopWindow mLoadingDialog;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingAddComponent.builder().appComponent(appComponent).faultProcessingAddModule(new FaultProcessingAddModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        faultCode = getIntent().getExtras().getString(Constant.FAULT_CODE);

    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        toolbarTitle.setText("新增处理方案");


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fault_add;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fault_processing_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_complete:
                editText.setGravity(Gravity.CENTER_VERTICAL);
                editText.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //show softKeyBoard
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                horizontalScrollView.setVisibility(View.GONE);
                fileName = editText.getText().toString();
                if (TextUtils.isEmpty(editText.getText())) {
                    ToastUtils.showMessage(this, "请输入方案名称", Toast.LENGTH_SHORT);
                    return false;
                }
                saveHtmlToFile(richEditor.getHtml());
                upLoadFile(file);
                break;

            case R.id.action_redo:
                richEditor.redo();
                break;
            case R.id.action_undo:
                richEditor.undo();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void upLoadFile(File file) {

        String argument = "[\"{" + "\\\"solution_name\\\":" +
                "\\\"" + file.getName() + "\\\"" +
                "," +
                "\\\"solution_detail\\\"" + ":" + "\\\"" +
                "" + "\\\"" +
                "," +
                "\\\"exception_code\\\"" + ":" + "\\\"" +
                faultCode + "\\\"}\"" +
                ",{" +
                "\"$type\"" + ":" + "\"java.io.File\"" +
                "," +
                "\"$value\"" +
                ":[" +
                "\"$FILE:" +
                file.getName() +
                "\"" +
                "]}]";
        Log.i(TAG, "argu: " + argument);


        // 创建 RequestBody，用于封装 请求RequestBody

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        try {
            long size = requestFile.contentLength();
            Log.i(TAG, "upLoadFile size: " + size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        // 添加描述
//        String descriptionString = "hello, 这是文件描述";
//        @SuppressWarnings("UnusedAssignment")
//        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        getPresenter().upLoadFile(requestFile, body, argument);
//
    }


    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("fileName", "line_fault");
        map.put("exception_code",faultCode);
        getPresenter().getTemplateContent(GsonTools.createGsonListString(map));


    }

    private boolean saveHtmlToFile(String html) {
        Log.i(TAG, "saveHtmlToFile: " + html);

        // 5 写入html文件
        // file = new File(this.getFilesDir(), fileName);
        file = new File(this.getExternalCacheDir(), fileName + ".html");
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            bw.write(html);
            bw.flush();
            Log.i(TAG, "saveHtmlToFile: " + "将信息点转换html文件完成");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null && bw != null) {
                    fos.close();
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;

    }


    @Override
    public void onSuccess(String message) {
        Log.i(TAG, "onGetWarningListSuccess: " + message);
        richEditor.setHtml(message);
        richEditor.setInputEnabled(false);
        floatingActionButton.setVisibility(View.GONE);
        beginEdit();
    }


    @OnClick({R.id.action_bold, R.id.action_italic, R.id.action_underline, R.id.action_heading1, R.id.action_heading2, R.id.action_heading3, R.id.action_outdent, R.id.action_align_left, R.id.action_align_center, R.id.action_align_right, R.id.action_insert_bullets, R.id.action_insert_numbers, R.id.action_blockquote, R.id.action_insert_image, R.id.action_insert_link, R.id.floatingActionButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_bold:
                richEditor.setBold();
                break;
            case R.id.action_italic:
                richEditor.setItalic();
                break;
            case R.id.action_underline:
                richEditor.setUnderline();
                break;
            case R.id.action_heading1:
                richEditor.setHeading(1);
                break;
            case R.id.action_heading2:
                richEditor.setHeading(2);
                break;
            case R.id.action_heading3:
                richEditor.setHeading(3);
                break;
            case R.id.action_outdent:
                richEditor.setOutdent();
                break;
            case R.id.action_align_left:
                richEditor.setAlignLeft();
                break;
            case R.id.action_align_center:
                richEditor.setAlignCenter();
                break;
            case R.id.action_align_right:
                richEditor.setAlignRight();
                break;
            case R.id.action_insert_bullets:
                richEditor.setBullets();
                break;
            case R.id.action_insert_numbers:
                richEditor.setNumbers();
                break;
            case R.id.action_blockquote:
                richEditor.setBlockquote();
                break;
            case R.id.action_insert_image:
                richEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
                break;
            case R.id.action_insert_link:
                richEditor.insertLink(null, null);
                break;
            case R.id.floatingActionButton:
                beginEdit();
                break;
        }
    }

    private void beginEdit() {
        editText.setVisibility(View.VISIBLE);
        editText.requestFocus();
        horizontalScrollView.setVisibility(View.VISIBLE);
        richEditor.setInputEnabled(true);
//        richEditor.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //show softKeyBoard
//        imm.showSoftInput(richEditor, InputMethodManager.SHOW_IMPLICIT);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    public void onFailed(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void upLoadFileSuccess() {

    }

    @Override
    public void showLoadingView() {

        mSl.showLoadingView();
        TextView tv_loading = ViewUtils.findView(this, R.id.tv_loading);
        tv_loading.setText("正在加载模板！");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "richEditor: " + richEditor.isInEditMode());
        Log.i(TAG, "editText: " + editText.isFocused());
        if (richEditor.isInEditMode() || editText.isFocused()) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("警告")
                            .setMessage("你即将要退出当前页面?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onBackPressed();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    alertDialog.show();
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showContentView() {
        mSl.showContentView();
    }
    @Override
    public void showErrorView() {

        mSl.showErrorView();
        mSl.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("fileName", "line_fault");
                getPresenter().getTemplateContent(GsonTools.createGsonListString(map));
            }
        });
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {

            mLoadingDialog = DialogUtils.createLoadingDialog(this, null);
        }
        mLoadingDialog.showAtLocation(ViewUtils.getRootViewWithActivity(this), Gravity.CENTER, 0, 0);
    }

    @Override
    public void showLoadingDialogSuccess() {

        if (mLoadingDialog != null) {
            DialogUtils.loadingViewDismiss(mLoadingDialog);
        }
        this.finish();
        ToastUtils.showMessage(this, "上传成功！");

    }

    @Override
    public void showLoadingDialogFailed() {

        if (mLoadingDialog != null) {
            DialogUtils.loadingViewDismiss(mLoadingDialog);
        }
        ToastUtils.showMessage(this, "上传失败！");
    }
}
