package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.HttpUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private EditText account;
    private EditText password;
    private Button btn_register;
    private RadioButton rbt_admin;
    private RadioButton rbt_use;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setToolbar("注册");
        rbt_use.setChecked(true);
    }


    private void initView() {
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        rbt_admin = (RadioButton) findViewById(R.id.rbt_admin);

        rbt_use = (RadioButton) findViewById(R.id.rbt_use);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
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

        // TODO validate success, do something
        String loginapi = "Register";
        if (rbt_use.isChecked()){
            loginapi = "Register";
        }
        if (rbt_admin.isChecked()){
            loginapi = "ARegister";
        }
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), loginapi) {
            @Override
            protected void onCallback(String json) {
                if (json.equals("true")) {
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                    finish();
                } else if (json.equals("isexist")) {
                    Toast.makeText(getApplicationContext(), "账号已经存在", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "连接错误", Toast.LENGTH_SHORT).show();
                }
            }
        };
        httpUtil.addParams("username", accountString);
        httpUtil.addParams("password", passwordString);
        httpUtil.sendGetRequest();

    }
}

