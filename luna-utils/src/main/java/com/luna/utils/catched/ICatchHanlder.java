/**
 * copyright@www.jd.com
 */
package com.luna.utils.catched;

import java.util.List;

/**
 * @author lyl
 * @date 2016-5-9
 * @time 下午4:46:29
 * @desc 抓取处理器
 */
public interface ICatchHanlder<T> {

    /**
     * 处理任务
     * @param list 入参集合
     */
    public void handle(List<T> list);
}
