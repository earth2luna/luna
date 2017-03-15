/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.luna.utils.LangUtils;
import com.luna.utils.constant.Constants;

/**
 * @author laulyl
 * @date 2017年3月15日 下午11:23:46
 * @description
 */
public class ServiceUtils {

	public static void evalPageMap(Map<String, Object> map, Integer pageNow, Integer pageSize) {
		map.put("startIndex", (pageNow - 1) * pageSize);
		map.put("endIndex", pageSize);
	}

	public static void evalStatusInMap(Map<String, Object> map, String sts) {
		List<Long> statusIn = LangUtils.split2ArrayListLong(sts, Constants.COMMA_STRING);
		map.put("statusIn", CollectionUtils.isEmpty(statusIn) ? null : statusIn);
	}
}
