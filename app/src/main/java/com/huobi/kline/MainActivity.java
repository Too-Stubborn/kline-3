package com.huobi.kline;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.huobi.klinelib.adapter.DepthDataAdapter;
import com.huobi.klinelib.adapter.KLineChartAdapter;
import com.huobi.klinelib.draw.Status;
import com.huobi.klinelib.entity.KLineEntity;
import com.huobi.klinelib.formatter.DateFormatter;
import com.huobi.klinelib.utils.DataHelper;
import com.huobi.klinelib.utils.ViewUtil;
import com.huobi.klinelib.view.KLineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<KLineEntity> datas;

    private KLineChartAdapter adapter;

    private DepthDataAdapter dataAdapter;

    private ArrayList<TextView> subTexts = new ArrayList<>();

    private int mainIndex;

    private int subIndex = -1;
    private KLineChartView chartView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DepthFullView depthView = findViewById(R.id.full_depth_view);
        chartView = findViewById(R.id.kLineChartView);
        findViewById(R.id.text_view_ma).setOnClickListener(this);
        findViewById(R.id.text_view_boll).setOnClickListener(this);
        findViewById(R.id.text_view_macd).setOnClickListener(this);
        findViewById(R.id.text_view_kdj).setOnClickListener(this);
        findViewById(R.id.text_view_rsi).setOnClickListener(this);
        findViewById(R.id.text_view_wr).setOnClickListener(this);
        findViewById(R.id.radio_button_time_line).setOnClickListener(this);
        findViewById(R.id.radio_button_k_line).setOnClickListener(this);
        findViewById(R.id.text_view_hide_sub).setOnClickListener(this);
        findViewById(R.id.text_view_hide_master).setOnClickListener(this);

//        DepthDataAdapter dataAdapter = new DepthDataAdapter();
//        depthView.setDataAdapter(dataAdapter);


//        new Thread(() -> {
//            int temp = 0;
//            while (temp < 10) {
//                SystemClock.sleep(3000);
//                List<IDepth> left = new ArrayList<>();
//                List<IDepth> right = new ArrayList<>();
//                for (int i = 0; i < 100; i++) {
//                    DepthEntity e = new DepthEntity();
//                    e.setPrice(i);
//                    e.setVol(100 - i + temp);
//                    left.add(e);
//                }
//                for (int i = 0; i < 100; i++) {
//                    DepthEntity e = new DepthEntity();
//                    e.setPrice(100 + i + 5);
//                    e.setVol(i + 1 + temp);
//                    right.add(e);
//                }
//                dataAdapter.setNewData(left, right);
//                runOnUiThread(() -> {
//                    dataAdapter.notifyDataSetChanged();
//                });
//                temp++;
//            }
//        }).start();
        initKline();
        initData();
        initListener();
    }

    private void initKline() {
        adapter = new KLineChartAdapter();
//        subTexts.add(findViewById(R.id.macdText));
//        subTexts.add(findViewById(R.id.kdjText));
//        subTexts.add(findViewById(R.id.rsiText));
//        subTexts.add(findViewById(R.id.wrText));

        chartView = findViewById(R.id.kLineChartView);
        chartView.setAdapter(adapter);
        chartView.setDateTimeFormatter(new DateFormatter());
        chartView.setGridColumns(5);
        chartView.setGridRows(5);
    }

    private void initData() {

        chartView.justShowLoading();
        new Thread(() -> {
            datas = DataRequest.getALL(MainActivity.this);
//            datas = datas.subList(0, 20);
//                    subList(0, 500);
            DataHelper.calculate(datas);

            runOnUiThread(() -> {
                adapter.resetData(datas);
                adapter.notifyDataSetChanged();
                chartView.startAnimation();
                chartView.refreshEnd();
                chartView.setOverScrollRange(getWindowManager().getDefaultDisplay().getWidth() / 5);

            });
        }).start();

        new Thread(() -> {
            int i = 0;
            while (i < 10) {
                SystemClock.sleep(5000);

                KLineEntity item = (KLineEntity) adapter.getItem(adapter.getCount() - 1);
                KLineEntity kLineEntity = new KLineEntity();
                kLineEntity.Close = item.Close - 5;
                kLineEntity.High = item.High + 20;
                kLineEntity.Open = item.Open;
                kLineEntity.Low = item.Low - 20;
                kLineEntity.Volume = item.Volume + 1;
                kLineEntity.Date = item.Date;
                Random random = new Random(SystemClock.currentThreadTimeMillis());
//                SystemClock.sleep(3000);
//                KLineEntity item = (KLineEntity) adapter.getItem(adapter.getCount() - 1);
//
//                if (1 % 2 == 0) {
                item.Close = item.Close - 10;
                adapter.changeItem(adapter.getCount() - 1, kLineEntity);
//                } else {
//                    kLineEntity.Open = item.Close;
//                    float tempFloat = random.nextFloat();
//                    float tempFloat1 = random.nextFloat();
//                    if (tempFloat > tempFloat1) {
//                        kLineEntity.High = tempFloat * 100 + 100;
//                        kLineEntity.Low = tempFloat1 * 100 + 100;
//                    } else {
//                        kLineEntity.High = tempFloat1 * 100 + 100;
//                        kLineEntity.Low = tempFloat;
//                    }
//                    kLineEntity.Close = random.nextFloat() * 100 + 100;
//                    kLineEntity.Volume = random.nextFloat() * 100;
//                    kLineEntity.Date = "2016/10/27";
//                    adapter.addLast(kLineEntity);
//                    adapter.notifyDataSetChanged();
//                    i++;
//                }


            }
        }).start();

    }


    private void initListener() {
//        maText.setOnClickListener(() -> {
//            if (mainIndex != 0) {
//                chartView.hideSelectData();
//                mainIndex = 0;
//                maText.textColor = Color.parseColor("#eeb350");
//                bollText.textColor = Color.WHITE;
//                chartView.changeMainDrawType(Status.MA);
//            }
//        });
//        bollText.setOnClickListener(() -> {
//            if (mainIndex != 1) {
//                chartView.hideSelectData();
//                mainIndex = 1;
//                bollText.textColor = Color.parseColor("#eeb350");
//                maText.textColor = Color.WHITE;
//                chartView.changeMainDrawType(Status.BOLL);
//            }
//        });
//        mainHide.setOnClickListener(() -> {
//            if (mainIndex != -1) {
//                chartView.hideSelectData();
//                mainIndex = -1;
//                bollText.textColor = Color.WHITE;
//                maText.textColor = Color.WHITE;
//                chartView.changeMainDrawType(Status.NONE);
//            }
//        });
//        for (int index = 0; index < subTexts.size(); index++) {
//            int finalIndex = index;
//            subTexts.get(index).setOnClickListener(() -> {
//                if (subIndex != finalIndex) {
//                    chartView.hideSelectData();
//                    if (subIndex != -1) {
//                        subTexts.get(subIndex).setTextColor(Color.WHITE);
//                    }
//                    subIndex = finalIndex;
//                    subTexts.get(finalIndex).setTextColor(Color.parseColor("#eeb350"));
//                    chartView.setChildDraw(subIndex);
//                }
//            });
//        }
//        subHide.setOnClickListener(() -> {
//            if (subIndex != -1) {
//                chartView.hideSelectData();
//                subTexts[subIndex].textColor = Color.WHITE;
//                subIndex = -1;
//                chartView.hideChildDraw();
//            }
//        });
//        fenText.setOnClickListener(() -> {
//            chartView.hideSelectData();
//            fenText.textColor = Color.parseColor("#eeb350");
//            kText.textColor = Color.WHITE;
//            chartView.setMainDrawLine(true);
//        });
//        kText.setOnClickListener(() -> {
//            chartView.hideSelectData();
//            kText.textColor = Color.parseColor("#eeb350");
//            fenText.textColor = Color.WHITE;
//            chartView.setMainDrawLine(false);
//        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.text_view_hide_master:
                chartView.hideSelectData();
                chartView.changeMainDrawType(Status.NONE);
                break;
            case R.id.text_view_hide_sub:
                chartView.hideSelectData();
                chartView.hideChildDraw();
                break;
            case R.id.text_view_ma:
                chartView.hideSelectData();
                chartView.changeMainDrawType(Status.MA);
                break;
            case R.id.text_view_boll:
                chartView.hideSelectData();
                chartView.changeMainDrawType(Status.BOLL);
                break;
            case R.id.text_view_macd:
                chartView.hideSelectData();
//                chartView.changeMainDrawType(Status.);
                chartView.setChildDraw(0);
                break;
            case R.id.text_view_kdj:
                chartView.hideSelectData();
//                chartView.changeMainDrawType(Status.);
                chartView.setChildDraw(1);
                break;
            case R.id.text_view_rsi:
                chartView.hideSelectData();
//                chartView.changeMainDrawType(Status.);
                chartView.setChildDraw(2);
                break;
            case R.id.text_view_wr:
                chartView.hideSelectData();
//                chartView.changeMainDrawType(Status.);
                chartView.setChildDraw(3);
                break;
            case R.id.radio_button_time_line:
                chartView.hideSelectData();
//                chartView.changeMainDrawType(Status.);
                chartView.setMainDrawLine(true);
                break;
            case R.id.radio_button_k_line:
                chartView.hideSelectData();
//                chartView.changeMainDrawType(Status.);
                chartView.setMainDrawLine(false);
                break;
        }
    }
}
