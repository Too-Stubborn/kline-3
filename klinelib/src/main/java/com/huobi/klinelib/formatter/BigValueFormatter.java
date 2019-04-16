package com.huobi.klinelib.formatter;

import com.huobi.klinelib.base.IValueFormatter;

import java.util.Locale;

/*************************************************************************
 * Description   : 对较大数据进行格式化
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : BigValueFormatter.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/

public class BigValueFormatter implements IValueFormatter {

    //必须是排好序的
    private int[] values={10000,1000000,100000000};
    private String[] units={"万","百万","亿"};

    @Override
    public String format(float value) {
        String unit="";
        int i=values.length-1;
        while (i>=0)
        {
            if(value>values[i]) {
                value /= values[i];
                unit = units[i];
                break;
            }
            i--;
        }
        return String.format(Locale.getDefault(),"%.2f", value)+unit;
    }
}
