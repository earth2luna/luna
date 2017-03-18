/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.HashMap;
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
public class ConditionUtils {

	public static final int DEFAULT_PAGE_SIZE = 20;

	public static final String DEFAULT_PROP_KEY = "prop";

	public static void evalPageMap(Map<String, Object> map, Integer pageNow, Integer pageSize) {
		map.put("startIndex", (pageNow - 1) * pageSize);
		map.put("endIndex", pageSize);
	}

	public static void evalStatusInMap(Map<String, Object> map, String sts) {
		List<Long> statusIn = LangUtils.split2ArrayListLong(sts, Constants.COMMA_STRING);
		map.put("statusIn", CollectionUtils.isEmpty(statusIn) ? null : statusIn);
	}

	public static void evalIdEqMap(Map<String, Object> map, Long id) {
		map.put("id", id);
	}

	public static void evalStatusEqMap(Map<String, Object> map, Integer status) {
		map.put("status", status);
	}

	public static Map<String, Object> getHashMap() {
		return new HashMap<String, Object>();
	}

	public static Map<String, Object> getProps(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		Map<String, Object> props = (Map<String, Object>) map.get(DEFAULT_PROP_KEY);
		if (null == props) {
			props = getHashMap();
			map.put(DEFAULT_PROP_KEY, props);
		}
		return props;
	}

	public static void evalPopStatus(Map<String, Object> map, Integer status) {
		Map<String, Object> props = getProps(map);
		evalStatusEqMap(props, status);
	}
}
