package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.UserInfo;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_name;
    private EditText edt_xh;
    private EditText edt_sex;
    private EditText edt_bj;
    private EditText edt_tel;
    private Button btn_submit;
    private UserInfo userInfo;
    private EditText edt_sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        setToolbar("个人信息");
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_xh = (EditText) findViewById(R.id.edt_xh);
        edt_sex = (EditText) findViewById(R.id.edt_sex);
        edt_bj = (EditText) findViewById(R.id.edt_bj);
        edt_tel = (EditText) findViewById(R.id.edt_tel);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);
        getdate();
        edt_sr = (EditText) findViewById(R.id.edt_sr);

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
        String name = edt_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String xh = edt_xh.getText().toString().trim();
        if (TextUtils.isEmpty(xh)) {
            Toast.makeText(this, "身份证不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String sex = edt_sex.getText().toString().trim();
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String bj = edt_bj.getText().toString().trim();
        if (TextUtils.isEmpty(bj)) {
            Toast.makeText(this, "年龄不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String tel = edt_tel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            Toast.makeText(this, "电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        String sr = edt_sr.getText().toString().trim();
//        if (TextUtils.isEmpty(sr)) {
//            Toast.makeText(this, "出生日期不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
        // TODO validate success, do something
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "UserInfoSetting") {
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
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.addParams("tel", tel);
        httpUtil.addParams("grade", bj);
        httpUtil.addParams("name", name);
        //httpUtil.addParams("sr", sr);
        httpUtil.addParams("sex",sex);
        httpUtil.addParams("xh", xh);

        httpUtil.sendGetRequest();

    }

    private void getdate() {
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetUserInfo") {
            @Override
            protected void onCallback(String json) {
                if (json.equals("error")) {
                    userInfo = new UserInfo();
                    initdate();
                } else {
                    Gson gson = new Gson();
                    userInfo = gson.fromJson(json, UserInfo.class);
                    initdate();
                }
            }
        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();

    }

    private void initdate() {
        edt_bj.setText(userInfo.getGrade());
        edt_name.setText(userInfo.getName());
        edt_sex.setText(userInfo.getSex());
        edt_tel.setText(userInfo.getTel());
        edt_xh.setText(userInfo.getStudent_number());
        edt_sr.setText(userInfo.getBirthday());
    }


}
