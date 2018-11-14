package com.demo.bs.demoapp2.ui.activity.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.UserInfo;
import com.demo.bs.demoapp2.ui.activity.KQListActivity;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XSListActivity extends BaseActivity {


    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private List<UserInfo> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        setToolbar("学生列表");
        initView();
        getData();
        final String[] strings ={"评分","查看签到情况"};
        findViewById(R.id.btn_add).setVisibility(View.GONE);//设置控件隐藏
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(XSListActivity.this).setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle b =new Bundle();
                        b.putString("id",infos.get(position).getId());
                        switch (which){
                            case 0:
                                final EditText e =new EditText(XSListActivity.this);
                                new AlertDialog.Builder(XSListActivity.this).setTitle("请输入评分结果").setView(e).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (e.getText().toString().equals("")){
                                            showToast("内容不能为空");
                                            return;
                                        }
                                        updateG(e.getText().toString(),infos.get(position).getId());
                                    }
                                }).setNegativeButton("取消",null).show();
                                break;
                            case 1:
                                go(KQListActivity.class,b);
                                break;

                            case 2:
                                break;
                        }
                    }
                }).show();
            }
        });
    }

    private void updateG(String c,String id) {

        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "UpdateG") {
            @Override
            protected void onCallback(String json) {
                showToast("评分成功");
                getData();
            }
        };
        httpUtil.addParams("id", id);
        httpUtil.addParams("c", c);
        httpUtil.sendGetRequest();
    }


     private void getData() {
                        list.clear();
                        infos.clear();
                        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetXSList") {
                            @Override
                            protected void onCallback(String json) {
                                try {
                                    Gson gson = new Gson();
                                    infos= gson.fromJson(json, new TypeToken<List<UserInfo>>() {
                                    }.getType());
                                    for (int i = 0; i < infos.size(); i++) {
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("type", "姓名:  " + infos.get(i).getName()
                                                +"\n学号:  "+infos.get(i).getId()
                                                +"\n评分:  "+infos.get(i).getGrade()+"分"
                                                +"\n作业:  "+infos.get(i).getJs_sx()+"分"
                                        );
                                        list.add(map);
                                    }
                                    simpleAdapter.notifyDataSetChanged();
                                } catch (Exception e) {

                }
            }
        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        list =new ArrayList<>();
        infos =new ArrayList<>();
        simpleAdapter =new SimpleAdapter(getApplicationContext(),list, R.layout.listitem_textitem,new String[]{"type"},new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);
    }


}
