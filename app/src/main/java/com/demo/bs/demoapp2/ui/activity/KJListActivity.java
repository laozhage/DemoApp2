package com.demo.bs.demoapp2.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.FileInfo;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.EncodeUtils;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KJListActivity extends BaseActivity {

    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> list;
    private List<FileInfo> infos;
    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/mbj/";          //SD卡的路径
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        setToolbar("课件列表");
        initView();
        getData();
        findViewById(R.id.btn_add).setVisibility(View.GONE);
        final String[] strings ={"下载"};
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(KJListActivity.this).setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "DownLoadFile") {
                                    @Override
                                    protected void onCallback(String json) {
                                        EncodeUtils.GenerateImage(json,FILE_SAVEPATH,infos.get(position).getPath());
                                        showToast("已下载到:"+FILE_SAVEPATH);
                                    }
                                };
                                httpUtil.addParams("fileName", infos.get(position).getPath());
                                httpUtil.sendGetRequest();
                                break;

                        }
                    }
                }).show();
            }
        });
    }




    private void getData() {
        list.clear();
        infos.clear();
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetKJList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos= gson.fromJson(json, new TypeToken<List<FileInfo>>() {
                    }.getType());
                    for (int i = 0; i < infos.size(); i++) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("type", "课件标题:  " + infos.get(i).getBt()
                                +"\n文件名:  "+infos.get(i).getPath()
                                +"\n发布时间:  "+infos.get(i).getTime()

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
