package com.delta.smt.ui.fault_processing.fault_add;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.fault_processing.fault_add.di.DaggerFaultProcessingAddComponent;
import com.delta.smt.ui.fault_processing.fault_add.di.FaultProcessingAddModule;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddContract;
import com.delta.smt.ui.fault_processing.fault_add.mvp.FaultProcessingAddPresenter;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/9 19:40
 */


public class FaultProcessingAddActivity extends BaseActivity<FaultProcessingAddPresenter> implements FaultProcessingAddContract.View {
    private String content;
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

    private static final String TAG = "FaultProcessingAddActiv";

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
    ImageButton actionBlockquote;
    @BindView(R.id.action_insert_image)
    ImageButton actionInsertImage;
    @BindView(R.id.action_insert_link)
    ImageButton actionInsertLink;

    private String fileName = null;
    private String faultCode = "主故障-111";
    private File file;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingAddComponent.builder().appComponent(appComponent).faultProcessingAddModule(new FaultProcessingAddModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
//        faultCode = getIntent().getExtras().getString(Constant.FAULT_CODE);
//
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
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
                editText.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //show softKeyBoard
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                horizontalScrollView.setVisibility(View.GONE);

                saveHtmlToFile(richEditor.getHtml());
                printFiles(this);
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
        StringBuilder url = new StringBuilder("[\"{");

        String argu = url.append("\"solution_name\":")
                .append("\"").append(fileName).append("\"")
                .append(",")
                .append("\"solution_detail\"").append(":").append("\"")
                .append("").append("\"")
                .append(",")
                .append("\"exception_code\"").append(":").append("\"")
                .append(faultCode).append("\"}\"")
                .append(",{")
                .append("\"$type\"").append(":").append("\"java.io.File\"")
                .append(",")
                .append("\"$value\"")
                .append(":[")
                .append("\"$FILE:")
                .append(fileName)
                .append("\"")
                .append("]}]").toString();
        Log.i(TAG, "upLoadFile: argu===  " + argu);


//        / 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

// 添加描述
        String descriptionString = "hello, 这是文件描述";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);


        getPresenter().upLoadFile(requestFile, body, argu);
    }


    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("fileName", "line_fault");
        String param = new Gson().toJson(map);
        getPresenter().getTemplateContent(param);


    }

    private void printFiles(Context context) {
        File[] files = context.getExternalCacheDir().listFiles();
        Log.i(TAG, "files size is ==:" + files.length);
        List<File> fileList = Arrays.asList(files);
        for (File file : fileList) {
            Log.i(TAG, "fileName: " + file.getName() + "File Size===" + file.length());
        }


    }

    private boolean saveHtmlToFile(String html) {
        Log.i(TAG, "saveHtmlToFile: " + html);
        fileName = editText.getText().toString();
        if (TextUtils.isEmpty(editText.getText())) {
            ToastUtils.showMessage(this, "请输入方案名称", Toast.LENGTH_SHORT);
            return false;
        }
        // 5 写入html文件
//        file = new File(this.getFilesDir(), fileName);
        file = new File(this.getExternalCacheDir(), fileName);
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
            return false;
        }


    }


    @Override
    public void onSuccess(String message) {
        Log.i(TAG, "onSuccess: " + message);
        content = message;
//        mWebView.loadDataWithBaseURL(null, message, "text/html","UTF-8", null);
//        richEditor.loadDataWithBaseURL(null, message, "text/html", "UTF-8", null);
        richEditor.setHtml(message);
        richEditor.setInputEnabled(false);
        Log.i(TAG, "getHtml: " + richEditor.getHtml());
//
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
                floatingActionButton.setVisibility(View.GONE);
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


}
