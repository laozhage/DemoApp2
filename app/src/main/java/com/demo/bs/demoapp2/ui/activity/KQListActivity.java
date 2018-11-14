package com.demo.bs.demoapp2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.QD;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KQListActivity extends BaseActivity {

    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private List<QD> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        setToolbar("签到信息");
        initView();
        getData();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(KQListActivity.this,AddKQActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }



    private void getData() {
        list.clear();
        infos.clear();
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetQDList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos= gson.fromJson(json, new TypeToken<List<QD>>() {
                    }.getType());
                    for (int i = 0; i < infos.size(); i++) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("type", "课程名称:  " + infos.get(i).getKcname()
                                +"\n签到时间:  "+infos.get(i).getTime()

                        );
                        list.add(map);
                    }
                    simpleAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }
        };
        httpUtil.addParams("id", getIntent().getStringExtra("id"));
        httpUtil.sendGetRequest();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        list =new ArrayList<>();
        infos =new ArrayList<>();
        simpleAdapter =new SimpleAdapter(getApplicationContext(),list, R.layout.listitem_textitem,new String[]{"type"},new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
    }
}
