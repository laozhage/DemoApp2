package com.demo.bs.demoapp2.ui.activity.admin;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.demo.bs.demoapp2.R;
import com.demo.bs.demoapp2.bean.UserInfo;
import com.demo.bs.demoapp2.ui.base.BaseActivity;
import com.demo.bs.demoapp2.utils.GlobalValue;
import com.demo.bs.demoapp2.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;




public class AllGradeActivity extends BaseActivity {
    private TextView total;
    public String[] xlabel = new String[]{"0~59","60~69","70~79","80~89","90~100"};
    private TextView averagescore;
    private TextView failed;
    private List<UserInfo> infos;
    private ColumnChartView chart;
    private List<Float> list = new ArrayList<>();
    private List<Integer> integers=new ArrayList<>();
    ColumnChartData columnData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        setToolbar("成绩统计");
        initView();

    }

    private void initView() {

        total = (TextView) findViewById(R.id.total);
        averagescore = (TextView) findViewById(R.id.averagescore);
        failed = (TextView) findViewById(R.id.failed);
        infos = new ArrayList<>();
        getData();
        chart = (ColumnChartView) findViewById(R.id.columnchart);

    }

    private void getData() {

        infos.clear();
        HttpUtil httpUtil = new HttpUtil(getApplicationContext(), "GetXSList") {
            @Override
            protected void onCallback(String json) {
                try {
                    Gson gson = new Gson();
                    infos = gson.fromJson(json, new TypeToken<List<UserInfo>>() {
                    }.getType());

                    total.setText((infos.size()) + "");
                    int bjg = 0;
                    int f_60=0;
                    int f_70=0;
                    int f_80=0;
                    int f_90=0;
                    int zf = 0;
                    for (int i = 0; i < infos.size(); i++) {
                        if (Integer.parseInt(infos.get(i).getJs_sx()) < 60) {
                            bjg++;
                        }
                        if (Integer.parseInt(infos.get(i).getJs_sx())>=60&&Integer.parseInt(infos.get(i).getJs_sx())<70){
                f_60++;
            }
            if (Integer.parseInt(infos.get(i).getJs_sx())>=70&&Integer.parseInt(infos.get(i).getJs_sx())<80){
                f_70++;
            }
            if (Integer.parseInt(infos.get(i).getJs_sx())>=80&&Integer.parseInt(infos.get(i).getJs_sx())<90){
                f_80++;
            }
            if (Integer.parseInt(infos.get(i).getJs_sx())>=90&&Integer.parseInt(infos.get(i).getJs_sx())<=100){
                f_90++;
            }

            //  list.add(Float.parseFloat(infos.get(i).getJs_sx()));
            zf = zf + Integer.parseInt(infos.get(i).getJs_sx());

        }
        integers.add(bjg);
        integers.add(f_60);
        integers.add(f_70);
        integers.add(f_80);
        integers.add(f_90);
        failed.setText(bjg + "");
        averagescore.setText(div(Double.valueOf(zf), Double.valueOf(infos.size()), 2) + "");

        initCv();
    } catch (Exception e) {

    }
}
        };
                httpUtil.addParams("id", GlobalValue.getUserInfo().getId());
                httpUtil.sendGetRequest();
                }

private void initCv() {
        int numSubcolumns = 1;
        int numColumns = xlabel.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

        values = new ArrayList<SubcolumnValue>();

        values.add(new SubcolumnValue(integers.get(i),
                        ChartUtils.pickColor()));

            // 点击柱状图就展示数据量
            axisValues.add(new AxisValue(i).setLabel(xlabel[i]));

            columns.add(new Column(values).setHasLabels(true));
        }

        columnData = new ColumnChartData(columns);
        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true)
                .setTextColor(Color.BLACK));
        columnData.setAxisYLeft(new Axis().setHasLines(true)
                .setTextColor(Color.BLACK).setMaxLabelChars(2));
        chart.setColumnChartData(columnData);
        chart.setValueSelectionEnabled(true);

        chart.setZoomType(ZoomType.HORIZONTAL);
    }

    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }

}
