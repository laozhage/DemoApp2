package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.Msg;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.HttpUtil;

public class ChangeMsgActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_name;
    private Button btn_submit;
    private EditText edt_code;
    private Msg msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgadd_info);
        msg =getIntent().getParcelableExtra("info");
        initView();
        setToolbar("修改留言");
    }

    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        edt_code = (EditText) findViewById(R.id.edt_code);
        setResult(1);
        edt_code.setText(msg.getNr());
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

        String code = edt_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "留言不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "UpdateMsg") {
            @Override
            protected void onCallback(String json) {

                    if (json.equals("true")) {
                        showToast("修改成功");
                        finish();
                    }else {
                        showToast("修改失败");
                    }

            }
        };

        httpUtil.addParams("msg", code);
        httpUtil.addParams("id", msg.getId());
        httpUtil.sendGetRequest();

    }


}
