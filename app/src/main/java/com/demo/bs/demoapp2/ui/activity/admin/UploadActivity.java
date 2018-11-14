package com.demo.bs.demoapp2.ui.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.EncodeUtils;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;

import java.io.File;

public class UploadActivity extends BaseActivity implements View.OnClickListener {

    public final static String SDCARD_MNT = "/mnt/sdcard";
    public final static String SDCARD = "/sdcard";
    private EditText bt;
    private Button btn_xzwj;
    private Button btn_sc;
    private String base64Str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);
        initView();
        setToolbar("上传文件");
    }

    private void initView() {
        bt = (EditText) findViewById(R.id.bt);
        btn_xzwj = (Button) findViewById(R.id.btn_xzwj);
        btn_sc = (Button) findViewById(R.id.btn_sc);

        btn_xzwj.setOnClickListener(this);
        btn_sc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xzwj:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_sc:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String btString = bt.getText().toString().trim();
        if (TextUtils.isEmpty(btString)) {
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String path = btn_xzwj.getText().toString().trim();
        if (TextUtils.isEmpty(path) || path.equals("选择文件")) {
            Toast.makeText(this, "请选择文件", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "UpLoadFile") {
            @Override
            protected void onCallback(String json) {
                if (json.equals("error")) {
                    showToast("上传失败");
                } else {
                    showToast("上传成功");
                }
            }
        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.addParams("bt", btString);
        httpUtil.addParams("fileName", path.substring(path.lastIndexOf("/") + 1));//取其地址
        httpUtil.addParams("base64", base64Str);
        httpUtil.sendPostRequest();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {   //是否选择，没选择就不会继续
            Uri uri = data.getData();               //得到uri，后面就是将uri转化成file的过程。
            String path = getAbsolutePathFromNoStandardUri(uri);
           // Log.d("cjy", path);
            base64Str = EncodeUtils.GetFileStr(path);
            btn_xzwj.setText(path);
        }
    }

    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String filePath = null;

        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);//解码
        String pre1 = "file://" + SDCARD + File.separator;
        String pre2 = "file://" + SDCARD_MNT + File.separator;
        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath()    //获取SD卡根目录
                    + File.separator + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre2.length());
        }
        return filePath;
    }

}


