package com.huobi.klinelib.adapter;

import android.os.Handler;
import android.os.Looper;

import com.huobi.klinelib.base.BaseKLineChartAdapter;
import com.huobi.klinelib.entity.KLineEntity;
import com.huobi.klinelib.utils.DataHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据适配器
 * Created by tifezh on 2016/6/18.
 */
public class KLineChartAdapter extends BaseKLineChartAdapter {

    private List<KLineEntity> datas = new ArrayList<>();

    public KLineChartAdapter() {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public String getDate(int position) {
        return datas.get(position).Date;
    }

    /**
     * 向头部添加数据
     */
    public void addHeaderData(List<KLineEntity> data) {
        if (data != null && !data.isEmpty()) {
            notifyDataWillChanged();
            datas.clear();
            datas.addAll(data);
        }
    }

    /**
     * 向尾部添加数据
     */
    public void resetData(List<KLineEntity> data) {
        notifyDataWillChanged();
        if (data != null && !data.isEmpty()) {
            datas.addAll(0, data);
        }
    }

    public void addLast(KLineEntity entity) {

        if (entity != null) {
            datas.add(datas.size(), entity);
            DataHelper.calculate(datas);
        }
    }


    /**
     * 获取当前K线最后一个数据
     *
     * @return 最后一根线的bean
     */
    public KLineEntity getLastData() {
        if (null != datas) {
            return datas.get(datas.size());
        }
        return null;
    }

    /**
     * 改变某个点的值
     *
     * @param position 索引值
     */
    public void changeItem(int position, KLineEntity data) {
        notifyDataWillChanged();
        datas.set(position, data);
        notifyDataSetChanged();
    }

    /**
     * 数据清除
     */
    public void clearData() {
        notifyDataWillChanged();
        datas.clear();
        notifyDataSetChanged();
    }
}
