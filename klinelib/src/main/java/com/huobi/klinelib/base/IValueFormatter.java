package com.huobi.klinelib.base;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : IValueFormatter.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public interface IValueFormatter {
    /**
     * 格式化value
     *
     * @param value 传入的value值
     * @return 返回字符串
     */
    String format(float value);
}
