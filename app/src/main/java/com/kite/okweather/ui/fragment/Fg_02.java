package com.kite.okweather.ui.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.kite.okweather.R;
import com.kite.okweather.beans.Db_Bean_City_List;
import com.kite.okweather.beans.Db_Bean_My_City_List;
import com.kite.okweather.beans.Weather_Bean_7Day;
import com.kite.okweather.beans.Weather_Bean_Hours;
import com.kite.okweather.ui.activity.Main;
import com.kite.okweather.utils.BaseActivity;
import com.kite.okweather.utils.BaseFragment;
import com.kite.okweather.utils.Utils;
import com.rainy.weahter_bg_plug.WeatherBg;
import com.rainy.weahter_bg_plug.utils.WeatherUtil;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.CountDownButton;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.dialog.strategy.IDialogStrategy;
import com.xuexiang.xui.widget.guidview.GuideCaseView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Fg_02 extends BaseFragment {

    WeatherBg bg;
    LineChart line;

    @Override
    protected int initLayout() {
        return R.layout.fg_02;
    }

    @Override
    protected void initView(View view) {
//        line = view.findViewById(R.id.lineChart_01);
//        bg = view.findViewById(R.id.bg);
//        bg.changeWeather("heavyRainy");
    }

    @Override
    protected void initData() {
//        Weather_Bean_7Day day7 = Fg_01.day7;
//        List<Float> list_y = new ArrayList<>();
//        List<Float> list_x = new ArrayList<>();
//
//        for (int i = 0; i < day7.getDaily().size(); i++) {
//            list_y.add(Float.valueOf(day7.getDaily().get(i).getTempMax()));
//            list_x.add(Float.valueOf(i));
//        }
//        testChart(list_x, list_y);
    }

    @Override
    protected void initSp() {

    }

    @Override
    protected void title() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_test:
//
//
//                break;
//    }

    }


    LineData initChart(List<String> x, List<String> y) {
//        Weather_Bean_Hours hours = Fg_01.hours;
//        List<String> string_x = new ArrayList<>();
//        List<String> string_y = new ArrayList<>();
//
//        for (int i = 0; i < hours.getHourly().size(); i++) {
//            string_x.add(hours.getHourly().get(i).getFxTime());
//            string_y.add(hours.getHourly().get(i).getTemp());
//        }
//        line.setData(initChart(string_x, string_y));
//        line.invalidate();
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < y.size(); i++) {
            Entry entry = new Entry((i + 1), Float.parseFloat(y.get(i)));
            entries.add(entry);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // 添加数据
        dataSet.setColor(Color.rgb(0, 0, 0));
        dataSet.setValueTextColor(Color.rgb(0, 0, 0)); // 自定义数据样式

        LineData lineData = new LineData(dataSet);

        lineData.notifyDataChanged();

        return lineData;

    }


    List<Entry> list = new ArrayList<>();

    /**
     * 测试折线图
     *
     * @param list_x
     * @param list_y
     */
    void testChart(List<Float> list_x, List<Float> list_y) {
        List<Entry> list = new ArrayList<>();
//        添加数据
        for (int i = 0; i < list_y.size(); i++) {
            list.add(new Entry(list_x.get(i), list_y.get(i)));     //其中两个数字对应的分别是   X轴   Y轴
        }
        LineDataSet lineDataSet = new LineDataSet(list, "语文");   //list是你这条线的数据  "语文" 是你对这条线的描述
        LineData lineData = new LineData(lineDataSet);
        line.setData(lineData);
        //折线图背景
        line.setBackgroundColor(0x30000000);   //背景颜色
        line.getXAxis().setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
        line.getAxisLeft().setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）

        //对于右下角一串字母的操作
        line.getDescription().setEnabled(false);                  //是否显示右下角描述
        line.getDescription().setText("这是修改那串英文的方法");    //修改右下角字母的显示
        line.getDescription().setTextSize(20);                    //字体大小
        line.getDescription().setTextColor(Color.RED);             //字体颜色

        //图例
        Legend legend = line.getLegend();
        legend.setEnabled(true);    //是否显示图例

        //X轴
        XAxis xAxis = line.getXAxis();
        xAxis.setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
        xAxis.setAxisLineColor(Color.RED);   //X轴颜色
        xAxis.setAxisLineWidth(2);           //X轴粗细
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {   //X轴自定义坐标
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v == 1) {
                    return "第一个";
                }
                if (v == 2) {
                    return "第二个";
                }
                if (v == 3) {
                    return "第三个";
                }
                return "";//注意这里需要改成 ""
            }
        });
        xAxis.setAxisMaximum(5);   //X轴最大数值
        xAxis.setAxisMinimum(0);   //X轴最小数值
        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        xAxis.setLabelCount(5, false);


        //Y轴
        YAxis AxisLeft = line.getAxisLeft();
        AxisLeft.setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）
        AxisLeft.setAxisLineColor(Color.BLUE);  //Y轴颜色
        AxisLeft.setAxisLineWidth(2);           //Y轴粗细
        AxisLeft.setValueFormatter(new IndexAxisValueFormatter() {  //Y轴自定义坐标
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {

                for (int a = 0; a < 16; a++) {     //用个for循环方便
                    if (a == v) {
                        return "第" + a + "个";
                    }
                }
                return "";
            }
        });
        AxisLeft.setAxisMaximum(15);   //Y轴最大数值
        AxisLeft.setAxisMinimum(0);   //Y轴最小数值
        //Y轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        AxisLeft.setLabelCount(15, false);

        //是否隐藏右边的Y轴（不设置的话有两条Y轴 同理可以隐藏左边的Y轴）
        line.getAxisRight().setEnabled(false);

        //折线
        //设置折线的式样   这个是圆滑的曲线（有好几种自己试试）     默认是直线
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(Color.GREEN);  //折线的颜色
        lineDataSet.setLineWidth(2);        //折线的粗细
        //是否画折线点上的空心圆  false表示直接画成实心圆
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleHoleRadius(3);  //空心圆的圆心半径
        //圆点的颜色     可以实现超过某个值定义成某个颜色的功能   这里先不讲 后面单独写一篇
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setCircleRadius(3);      //圆点的半径
        //定义折线上的数据显示    可以实现加单位    以及显示整数（默认是显示小数）
        lineDataSet.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                if (entry.getY() == v) {
                    return v + "℃";
                }
                return "";
            }
        });

        //数据更新
        line.notifyDataSetChanged();
        line.invalidate();

        //动画（如果使用了动画可以则省去更新数据的那一步）
        line.animateY(3000); //折线在Y轴的动画  参数是动画执行时间 毫秒为单位
//        line.animateX(2000); //X轴动画
//        line.animateXY(2000,2000);//XY两轴混合动画
    }


}
