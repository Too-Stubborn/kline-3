package com.huobi.kline;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.huobi.klinelib.adapter.DepthDataAdapter;
import com.huobi.klinelib.adapter.DepthRecycleViewAdapter;
import com.huobi.klinelib.entity.DepthEntity;
import com.huobi.klinelib.entity.IDepth;
import com.huobi.klinelib.view.DepthView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*************************************************************************
 * Description   : 封装一个完整的深度图
 *
 * @PackageName  : com.huobi.kline
 * @FileName     : DepthFullView.java
 * @Author       : chao
 * @Date         : 2019/1/11
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public class DepthFullView extends RelativeLayout {

    private DepthRecycleViewAdapter<IDepth> depthAdapter;

    public DepthFullView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public DepthFullView(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DepthFullView(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.kline_depth_part, this);
        DepthView depthView = inflate.findViewById(R.id.depth_view);
        DepthDataAdapter dataAdapter = new DepthDataAdapter();
        depthView.setDataAdapter(dataAdapter);

        RecyclerView recyclerView = inflate.findViewById(R.id.recycley_view_horizental_depth);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        depthAdapter = new DepthRecycleViewAdapter<IDepth>(context);
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.setAdapter(depthAdapter);


        //mock data
        new Thread(() -> {
            int temp = 0;
            while (temp < 10) {
                SystemClock.sleep(3000);
                List<IDepth> left = new ArrayList<>();
                List<IDepth> right = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    DepthEntity e = new DepthEntity();
                    e.setPrice(i);
                    e.setVol(100 - i + new Random().nextFloat() * 10 + temp);
                    left.add(e);
                }
                for (int i = 0; i < 100; i++) {
                    DepthEntity e = new DepthEntity();
                    e.setPrice(100 + i + 5);
                    e.setVol(i +new Random().nextFloat() * 10 + 1 + temp);
                    right.add(e);
                }
                dataAdapter.setNewData(left, right);
                inflate.postDelayed(() -> {
                    dataAdapter.notifyDataSetChanged();
                    depthAdapter.setData(left, right, dataAdapter.getLeftMax(), dataAdapter.getRightMax());
                }, 1);
                temp++;
            }
        }).start();
    }


}
