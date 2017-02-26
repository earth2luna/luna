/**
 * copyright@www.jd.com
 */
package com.luna.utils.catched;

import java.io.Serializable;

/**
 * @author lyl
 * @date 2016-5-9
 * @time 下午4:47:20
 * @desc
 */
public interface IGetProperty<T> {

    /**
     * 获取对象中其中一个属性值
     * @param t 对象
     * @return 属性值
     */
    public Serializable get(T t);
}
