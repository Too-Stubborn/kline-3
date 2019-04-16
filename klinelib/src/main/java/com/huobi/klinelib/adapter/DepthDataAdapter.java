package com.huobi.klinelib.adapter;

import android.os.Handler;
import android.os.Looper;

import com.huobi.klinelib.base.BaseDepthAdapter;
import com.huobi.klinelib.entity.IDepth;

import java.util.List;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.adapter
 * @FileName     : DepthDataAdapter.java
 * @Author       : chao
 * @Date         : 2019/1/9
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public class DepthDataAdapter extends BaseDepthAdapter {


    public void setNewData(List<IDepth> leftDatas, List<IDepth> rightDatas) {
        notifyDataWillChanged();
        iDepthsLeft.clear();
        iDepthsLeft.addAll(leftDatas);
        iDepthsRight.clear();
        iDepthsRight.addAll(rightDatas);
        tempLeftDatas = parseData(leftDatas, true);
        tempRightDatas = parseData(rightDatas, false);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return iDepthsLeft.size() + iDepthsRight.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public String getDate(int position) {
        return null;
    }

}
