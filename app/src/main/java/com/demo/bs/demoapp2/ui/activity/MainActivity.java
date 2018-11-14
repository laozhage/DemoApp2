package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String,Object>> list;
    private String[] type = { "签到考勤","留言","修改密码","查看作业","查看课件"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setToolbar("主页");
        for (int i=0;i<type.length;i++){
            HashMap <String,Object> hashMap =new HashMap<>();
            hashMap.put("type",type[i]);
            list.add(hashMap);
        }
        simpleAdapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Bundle b =new Bundle();
                        b.putString("id", GlobalValue.getUserInfo().getId());
                        go(KQListActivity.class,b);
                        break;
                    case 1:
                        go(MsgListActivity.class);
                        break;
                    case 2:
                        go(ChangePwdActivity.class);
                        break;
                    case 3:
                        go(ZYActivity.class);
                        break;
                    case 4:
                        go(KJListActivity.class);
                        break;
                }
            }
        });
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        list =new ArrayList<>();
        simpleAdapter =new SimpleAdapter(getApplicationContext(),list,R.layout.listitem_main,new String[]{"type"},new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);

    }
}
