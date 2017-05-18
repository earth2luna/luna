/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.main;

import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年5月17日 下午11:25:39
 * @description
 */
public class Main1 {
	public static void main(String[] args) {
		String value="　";
		System.out.println(LangUtils.isBlank(value));
		System.out.println(value.equals(" "));
	}

}
