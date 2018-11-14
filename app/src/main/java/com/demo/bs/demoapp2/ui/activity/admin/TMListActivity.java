package com.demo.bs.demoapp2.ui.activity.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.TM;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TMListActivity extends BaseActivity {

    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private List<TM> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        setToolbar("题库");
        initView();
        getData();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TMListActivity.this,AddTMActivity.class);
                startActivityForResult(intent,1);
            }
        });
        final String[] strings ={"修改","删除","加入到作业"};
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(TMListActivity.this).setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                Bundle b =new Bundle();
                                b.putParcelable("info",infos.get(position));

                                Intent intent =new Intent(TMListActivity.this,ChangeTMActivity.class);
                                intent.putExtras(b);
                                startActivityForResult(intent,1);
                                break;
                            case 1:
                                del(infos.get(position).getId());
                                break;
                            case 2:
                                addZY(infos.get(position).getId());
                                break;
                        }
                    }
                }).show();
            }
        });
    }

    private void addZY(String id) {
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "AddZY") {
            @Override
            protected void onCallback(String json) {
                showToast("加入成功");
                getData();
            }
        };
        httpUtil.addParams("id",id);
        httpUtil.sendGetRequest();
    }


    private void del(String id) {
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "DelTM") {
            @Override
            protected void onCallback(String json) {
                showToast("删除成功");
                getData();
            }
        };
        httpUtil.addParams("id",id);
        httpUtil.sendGetRequest();
    }

    private void getData() {
        list.clear();
        infos.clear();
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetTMList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos= gson.fromJson(json, new TypeToken<List<TM>>() {
                    }.getType());
                    for (int i = 0; i < infos.size(); i++) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("type", "题目:  " + infos.get(i).getWt()
                                +"\n科目:  "+infos.get(i).getKm()

                                +"\n难度:  "+infos.get(i).getNd()
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
    }
}
