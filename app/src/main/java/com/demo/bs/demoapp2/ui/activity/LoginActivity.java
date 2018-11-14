package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.activity.admin.AdminMainActivity;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private EditText account;
    private EditText password;
    private Button btn_register;
    private Button btn_login;
    private RadioButton rbt_admin;
    private RadioButton rbt_use;
    private RadioButton rbt_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setToolbar("登陆");

    }

    private void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        rbt_admin = (RadioButton) findViewById(R.id.rbt_admin);

        rbt_use = (RadioButton) findViewById(R.id.rbt_use);
        rbt_use.setChecked(true);
        rbt_teacher = (RadioButton) findViewById(R.id.rbt_teacher);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                go(RegisterActivity.class);
                break;
            case R.id.btn_login:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String accountString = account.getText().toString().trim();
        if (TextUtils.isEmpty(accountString)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String api = "Login";
        if (rbt_use.isChecked()) {
            api = "Login";
        }
        if (rbt_admin.isChecked()) {
            api = "AdminLogin";
        }
        if(rbt_teacher.isChecked()){
            api="TechLogin";
        }
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), api) {
            @Override
            protected void onCallback(String json) {
                if (!json.equals("error")) {
                    GlobalValue.getUserInfo().setId(json);
                    Log.d(GlobalValue.TAG, "用户id: " + GlobalValue.getUserInfo().getId());
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    if(rbt_use.isChecked()){
                        go(MainActivity.class);
                    }
                    if (rbt_admin.isChecked()){
                        go(AdminMainActivity.class);
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpUtil.addParams("username", accountString);
        httpUtil.addParams("password", passwordString);
        httpUtil.sendGetRequest();

    }
}

