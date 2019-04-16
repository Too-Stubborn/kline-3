package com.huobi.klinelib.entity;

import java.io.Serializable;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : IVolume.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/

public interface IVolume  extends Serializable {

    /**
     * 开盘价
     */
    float getOpenPrice();

    /**
     * 收盘价
     */
    float getClosePrice();

    /**
     * 成交量
     */
    float getVolume();

    /**
     * 五(月，日，时，分，5分等)均量
     */
    float getMA5Volume();

    /**
     * 十(月，日，时，分，5分等)均量
     */
    float getMA10Volume();
}
