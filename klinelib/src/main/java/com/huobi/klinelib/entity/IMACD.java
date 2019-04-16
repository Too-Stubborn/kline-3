package com.huobi.klinelib.entity;

import java.io.Serializable;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : IMACD.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public interface IMACD extends Serializable {


    /**
     * DEA值
     */
    float getDea();

    /**
     * DIF值
     */
    float getDif();

    /**
     * MACD值
     */
    float getMacd();

}
