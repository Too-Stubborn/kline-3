package com.huobi.klinelib.base;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Looper;

import com.huobi.klinelib.entity.IDepth;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.base
 * @FileName     : BaseDepthAdapter.java
 * @Author       : chao
 * @Date         : 2019/1/9
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public abstract class BaseDepthAdapter implements IAdapter {

    Handler handler = new Handler(Looper.getMainLooper());
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    protected float[] tempLeftDatas;
    protected float[] tempRightDatas;
    private float maxValue = Float.MIN_VALUE;
    private float minValue = Float.MAX_VALUE;
    private float maxIndex = Float.MIN_VALUE;
    private float minIndex = Float.MAX_VALUE;

    private float leftMax;
    private float rightMax;

    protected List<IDepth> iDepthsLeft = new ArrayList<>();
    protected List<IDepth> iDepthsRight = new ArrayList<>();

    Runnable notifyDataChangeRunable = new Runnable() {
        @Override
        public void run() {
            mDataSetObservable.notifyChanged();
        }
    };
    Runnable notifyDataWillChangeRunnable = new Runnable() {
        @Override
        public void run() {
            mDataSetObservable.notifyInvalidated();
        }
    };

    @Override
    public void notifyDataSetChanged() {
        if (getCount() > 0) {
            handler.post(notifyDataChangeRunable);
        }
    }

    @Override
    public void notifyDataWillChanged() {
        handler.post(notifyDataWillChangeRunnable);
    }

    /**
     * 转换数据
     */
    protected float[] parseData(List<IDepth> iDepths, boolean isLeft) {
        if (null == iDepths || iDepths.size() == 0) {
            return new float[0];
        }
        float maxValue = Float.MIN_VALUE;
        float[] tempDatas = new float[iDepths.size() * 2];
        for (int i = 0; i < iDepths.size(); i++) {
            IDepth iDepth = iDepths.get(i);
            tempDatas[i * 2] = iDepth.getPrice();
            float vol = iDepth.getVol();
            float price = iDepth.getPrice();
            tempDatas[i * 2 + 1] = vol;
            if (minValue > vol) {
                minValue = vol;
            }
            if (maxValue < vol) {
                maxValue = vol;
            }
            if (maxIndex < price) {
                maxIndex = price;
            }
            if (minIndex > price) {
                minIndex = price;
            }
        }
        if (this.maxValue < maxValue) {
            this.maxValue = maxValue;
        }
        if (isLeft) {
            leftMax = maxValue;
        } else {
            rightMax = maxValue;
        }
        return tempDatas;
    }

    public float[] getTempLeftDatas() {
        return tempLeftDatas;
    }

    public float[] getTempRightDatas() {
        return tempRightDatas;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxIndex() {
        return maxIndex;
    }

    public float getMinIndex() {
        return minIndex;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public float getLeftMax() {
        return leftMax;
    }

    public float getRightMax() {
        return rightMax;
    }


}
