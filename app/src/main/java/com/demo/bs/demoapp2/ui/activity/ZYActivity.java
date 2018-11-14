package com.demo.bs.demoapp2.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.TM;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ZYActivity extends BaseActivity implements View.OnClickListener {
    private int p = 0;
    private TextView tv_tm;
    private RadioButton rbt_a1;
    private RadioButton rbt_a2;
    private RadioButton rbt_a3;
    private RadioButton rbt_a4;
    private Button btn_sumbit;

    private List<TM> infos;
    private Button btn_next;
    private int fen = 0;
    private TextView tv_zsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zy);

        infos = new ArrayList<>();
        initView();
        setToolbar("作业");
        getData();
    }

    private void initView() {
        tv_tm = (TextView) findViewById(R.id.tv_tm);
        rbt_a1 = (RadioButton) findViewById(R.id.rbt_a1);
        rbt_a2 = (RadioButton) findViewById(R.id.rbt_a2);
        rbt_a3 = (RadioButton) findViewById(R.id.rbt_a3);
        rbt_a4 = (RadioButton) findViewById(R.id.rbt_a4);
        btn_sumbit = (Button) findViewById(R.id.btn_sumbit);

        btn_sumbit.setOnClickListener(this);

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        tv_zsd = (TextView) findViewById(R.id.tv_zsd);

    }

    private void init(TM tm) {
        rbt_a1.setText(tm.getA1());
        rbt_a2.setText(tm.getA2());
        rbt_a3.setText(tm.getA3());
        rbt_a4.setText(tm.getA4());
        tv_tm.setText(tm.getWt());
        tv_zsd.setText("知识点: "+tm.getZsd()+"\n难度: "+tm.getNd());
    }

    private void showT(String s) {
        showToast("选择正确,答案为" + s);
        p++;
        fen = fen + 10;
        if (p >= infos.size()) {
            // updateFEN();
            p--;
        } else {

            init(infos.get(p));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sumbit:
                if (p == infos.size()) {
                    return;
                }
                int i = Integer.parseInt(infos.get(p).getDa());
                if (i == 1 && rbt_a1.isChecked()) {
                    showT(rbt_a1.getText().toString());

                } else if (i == 2 && rbt_a2.isChecked()) {
                    showT(rbt_a2.getText().toString());

                } else if (i == 3 && rbt_a3.isChecked()) {
                    showT(rbt_a3.getText().toString());
                } else if (i == 4 && rbt_a4.isChecked()) {
                    showT(rbt_a4.getText().toString());

                } else {
                    showToast("选择错误");
                }
                break;
            case R.id.btn_next:
                p++;
                if (p >= infos.size()) {
                    p--;
                    showToast("已经没有了");
                    updateFEN();
                } else {
                    init(infos.get(p));
                }
                break;
        }
    }

    private void updateFEN() {
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "UpdateF") {
            @Override
            protected void onCallback(String json) {

            }


        };
        httpUtil.addParams("f", String.valueOf(fen));
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();
    }

    private void getData() {

        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetZYList") {
            @Override
            protected void onCallback(String json) {

                Gson gson = new Gson();
                infos = gson.fromJson(json, new TypeToken<List<TM>>() {
                }.getType());
                init(infos.get(0));
            }


        };
        httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
        httpUtil.sendGetRequest();
    }
}
