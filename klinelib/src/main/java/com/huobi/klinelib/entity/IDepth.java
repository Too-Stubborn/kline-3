package com.huobi.klinelib.entity;

import java.io.Serializable;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.entity
 * @FileName     : IDepth.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public interface IDepth extends Serializable {

    float getPrice();

    float getVol();
}
