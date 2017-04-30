/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.main;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * @author laulyl
 * @date 2017年4月29日 下午7:29:40
 * @description
 */
public class Main {

	public static void main(String[] args) {
		try {
			System.out.println(PinyinHelper.convertToPinyinString("CSS in JS 花重庆锦官城", "",PinyinFormat.WITHOUT_TONE));
			System.out.println(PinyinHelper.getShortPinyin("CSS in JS 花重庆锦官城"));
		} catch (PinyinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
