package com.demo.bs.demoapp2.ui.activity.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.List;



public class ShowQDActivity extends BaseActivity implements android.view.View.OnClickListener {

    private Button btn_stusubmit;
    private ListView showqd;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> qdlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showqd);
        initView();
        setToolbar("查看签到信息");
    }

    private void initView() {
        btn_stusubmit = (Button) findViewById(R.id.btn_stusubmit);
        btn_stusubmit.setOnClickListener(this);
        setResult(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                showqd();
                break;
        }
    }

    public void showqd() {

    }
}