package com.demo.bs.demoapp2.ui.activity;

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

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {

    private EditText new_password;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initView();
        setToolbar("修改密码");
    }

    private void initView() {
        new_password = (EditText) findViewById(R.id.new_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String password = new_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(),"ChangePwd") {
            @Override
            protected void onCallback(String json) {
                if (!json.equals("error")){

                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    go(MainActivity.class);

                }else {
                    Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        };

        httpUtil.addParams("id",GlobalValue.getUserInfo().getId());
        httpUtil.addParams("password",password);
        httpUtil.sendGetRequest();

    }
}
