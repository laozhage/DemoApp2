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
import com.demo.bs.demoapp2.bean.Msg;
import com.demo.bs.demoapp2.ui.activity.AddMsgActivity;
import com.demo.bs.demoapp2.ui.activity.ChangeMsgActivity;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MsgListActivity extends BaseActivity {

    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private List<Msg> infos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        setToolbar("留言信息");
        initView();
        getData();
      //  findViewById(R.id.btn_add).setVisibility(View.GONE);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MsgListActivity.this,AddMsgActivity.class);
                startActivityForResult(intent,1);
            }
        });
        final String[] strings ={"回复该留言","删除"};
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MsgListActivity.this).setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:
                                Bundle b =new Bundle();
                                b.putParcelable("info",infos.get(position));

                                Intent intent =new Intent(MsgListActivity.this,ChangeMsgActivity.class);
                                intent.putExtras(b);
                                startActivityForResult(intent,1);
                                break;
                            case 1:
                                del(infos.get(position).getId());
                                break;

                        }
                    }
                }).show();
            }
        });

    }
    private void del(String id) {
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "DelMsg") {
            @Override
            protected void onCallback(String json) {
                showToast("删除成功");
                getData();
            }
        };
        httpUtil.addParams("id",id);
        httpUtil.sendGetRequest();
    }
    private void initView() {

        lv = (ListView) findViewById(R.id.lv);
        list =new ArrayList<>();
        infos =new ArrayList<>();
        simpleAdapter =new SimpleAdapter(getApplicationContext(),list, R.layout.listitem_textitem,new String[]{"type"},new int[]{R.id.tv_content});
        lv.setAdapter(simpleAdapter);
    }

    private void getData() {
        list.clear();
        infos.clear();
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetMsgList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos= gson.fromJson(json, new TypeToken<List<Msg>>() {
                    }.getType());
                    for (int i = 0; i < infos.size(); i++) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("type", "留言内容:  " + infos.get(i).getNr()
                                +"\n来自:  "+infos.get(i).getFromid()
                                +"\n发表时间:  "+infos.get(i).getTime()

                        );
                        list.add(map);
                    }
                    simpleAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }
        };
        httpUtil.addParams("id", "all");
        httpUtil.sendGetRequest();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
    }
}
