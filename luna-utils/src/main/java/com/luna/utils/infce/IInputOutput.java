/**
 * copyright@jd.com
 */
package com.luna.utils.infce;

/**
 * @author lyl
 * @time 2016-12-1下午2:19:01
 * @desc 输入，输出接口
 */
public interface IInputOutput<I, O> {

	public O get(I i);

}
