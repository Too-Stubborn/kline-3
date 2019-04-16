package com.huobi.klinelib.entity;

import java.io.Serializable;

/*************************************************************************
 * Description   :
 *
 * @PackageName  : com.huobi.klinelib.utils
 * @FileName     : IKDJ.java
 * @Author       : chao
 * @Date         : 2019/1/8
 * @Email        : icechliu@gmail.com
 * @version      : V1
 *************************************************************************/
public interface IKDJ extends Serializable {

    /**
     * K值
     */
    float getK();

    /**
     * D值
     */
    float getD();

    /**
     * J值
     */
    float getJ();

}