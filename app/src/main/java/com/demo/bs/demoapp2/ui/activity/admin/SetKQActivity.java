package com.demo.bs.demoapp2.ui.activity.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;

import java.util.Random;

public class SetKQActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_name;
    private Button btn_submit;
    private EditText edt_code;
    private Button btn_stusubmit;
    private Button btn_over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_kqadd_info);
        initView();
        setToolbar("设置签到");
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        btn_stusubmit=(Button) findViewById(R.id.btn_stusubmit);
        btn_stusubmit.setOnClickListener(this);
        btn_over=(Button) findViewById(R.id.btn_over);
        btn_over.setOnClickListener(this);
        edt_code = (EditText) findViewById(R.id.edt_code);
        setResult(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
            case R.id.btn_stusubmit:
                go(XSListActivity.class);
                break;
            case R.id.btn_over:
                over();
                break;
        }
    }
    /*
    private void  stusubmit() {
        showToast("当前没有学生签到");
        return;
    }*/
    public static String getRandomString() { //length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    private void  over() {
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "课程名称", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = getRandomString();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "签到码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "SetQD") {
            @Override
            protected void onCallback(String json) {

                if (json.equals("true")) {
                    showToast("结束成功");
                    finish();
                }else {
                    showToast("结束失败");
                }

            }
        };

        httpUtil.addParams("code", code);
        httpUtil.addParams("km", name);
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();

    }
    private void submit() {
        // validate
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "课程名称", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = edt_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "签到码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "SetQD") {
            @Override
            protected void onCallback(String json) {

                    if (json.equals("true")) {
                        showToast("设置成功");
             //           finish();
                    }else {
                        showToast("设置失败");
                    }

            }
        };

        httpUtil.addParams("code", code);
        httpUtil.addParams("km", name);
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();

    }


}
