package com.demo.bs.demoapp2.ui.activity.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.MKInfo;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddTMActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_wt;
    private EditText edt_a1;
    private EditText edt_a2;
    private EditText edt_a3;
    private EditText edt_a4;
    private EditText edt_da;
    private Button btn_submit;
    private Spinner sp;
    private List<MKInfo> infos;
    private List<String> strings;
    ArrayAdapter adapter;
    private EditText edt_nd;
    private EditText edt_zsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtm);
        initView();
        infos = new ArrayList<>();
        strings = new ArrayList<>();
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        sp.setAdapter(adapter);
        setToolbar("添加题目");
        getData();
    }


    private void initView() {
        edt_wt = (EditText) findViewById(R.id.edt_wt);
        edt_a1 = (EditText) findViewById(R.id.edt_a1);
        edt_a2 = (EditText) findViewById(R.id.edt_a2);
        edt_a3 = (EditText) findViewById(R.id.edt_a3);
        edt_a4 = (EditText) findViewById(R.id.edt_a4);
        edt_da = (EditText) findViewById(R.id.edt_da);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);
        sp = (Spinner) findViewById(R.id.sp);

        edt_nd = (EditText) findViewById(R.id.edt_nd);

        edt_zsd = (EditText) findViewById(R.id.edt_zsd);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void getData() {

        infos.clear();
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetKMList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos = gson.fromJson(json, new TypeToken<List<MKInfo>>() {
                    }.getType());
                    for (int i = 0; i < infos.size(); i++) {
                        strings.add(infos.get(i).getKm());
                    }
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {

                }
            }
        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();
    }

    private void submit() {
        // validate
        String wt = edt_wt.getText().toString().trim();
        if (TextUtils.isEmpty(wt)) {
            Toast.makeText(this, "问题", Toast.LENGTH_SHORT).show();
            return;
        }

        String a1 = edt_a1.getText().toString().trim();
        if (TextUtils.isEmpty(a1)) {
            Toast.makeText(this, "答案1", Toast.LENGTH_SHORT).show();
            return;
        }

        String a2 = edt_a2.getText().toString().trim();
        if (TextUtils.isEmpty(a2)) {
            Toast.makeText(this, "答案2", Toast.LENGTH_SHORT).show();
            return;
        }

        String a3 = edt_a3.getText().toString().trim();
        if (TextUtils.isEmpty(a3)) {
            Toast.makeText(this, "答案3", Toast.LENGTH_SHORT).show();
            return;
        }

        String a4 = edt_a4.getText().toString().trim();
        if (TextUtils.isEmpty(a4)) {
            Toast.makeText(this, "答案4", Toast.LENGTH_SHORT).show();
            return;
        }

        String da = edt_da.getText().toString().trim();
        if (TextUtils.isEmpty(da)) {
            Toast.makeText(this, "正确答案为(输入第几个)", Toast.LENGTH_SHORT).show();
            return;
        }
        String nd = edt_nd.getText().toString().trim();
        if (TextUtils.isEmpty(nd)) {
            Toast.makeText(this, "难度", Toast.LENGTH_SHORT).show();
            return;
        }

        String zsd = edt_zsd.getText().toString().trim();
        if (TextUtils.isEmpty(zsd)) {
            Toast.makeText(this, "知识点", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something

        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "AddTM") {
            @Override
            protected void onCallback(String json) {

                if (json.equals("true")) {
                    showToast("留言成功");
                    finish();
                } else {
                    showToast("留言失败");
                }

            }
        };

        httpUtil.addParams("a1", a1);
        httpUtil.addParams("a2", a2);
        httpUtil.addParams("a3", a3);
        httpUtil.addParams("a4", a4);
        httpUtil.addParams("wt", wt);
        httpUtil.addParams("da", da);
        httpUtil.addParams("nd", nd);
        httpUtil.addParams("zsd", zsd);
        httpUtil.addParams("km", (String) sp.getSelectedItem());
        httpUtil.sendGetRequest();


    }

}
