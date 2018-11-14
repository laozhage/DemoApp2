package com.demo.bs.demoapp2.ui.activity.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.ui.activity.XTMListActivity;
import com.demo.bs.demoapp2.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminMainActivity extends BaseActivity {


    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String,Object>> list;
    private String[] type = { "设置签到码","查看学生信息","查看留言","发布作业","学生成绩分析","上传课件","查看作业"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
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
                        go(SetKQActivity.class);
                        break;
                    case 1:
                        go(XSListActivity.class);
                        break;
                    case 2:
                        go(MsgListActivity.class);
                        break;
                    case 3:
                        go(TMListActivity.class);
                        break;
                    case 4:
                        go(AllGradeActivity.class);
                        break;
                    case 5:
                        go(UploadActivity.class);
                        break;
                    case 6:
                        go(XTMListActivity.class);
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
