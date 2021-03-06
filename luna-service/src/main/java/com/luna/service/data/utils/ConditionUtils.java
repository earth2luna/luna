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

	public static final String ASC = "ASC";

	public static final String DESC = "DESC";

	public static final int DEFAULT_PAGE_SIZE = 14;

	public static final String DEFAULT_PROP_KEY = "prop";

	public static void evalPageMap(Map<String, Object> map, Integer pageNow, Integer pageSize) {
		map.put("startIndex", (pageNow - 1) * pageSize);
		map.put("endIndex", pageSize);
	}

	public static void evalSortOrderMap(Map<String, Object> map, String sortProperty, String sortOrder) {
		map.put("sortProperty", sortProperty);
		map.put("sortOrder", sortOrder);
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

	public static void evalPops(Map<String, Object> map, Object object) {
		map.put(DEFAULT_PROP_KEY, object);
	}
	
	public static Map<String, Object> evalUpdateMap(Long id, Object object) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put(DEFAULT_PROP_KEY, object);
		map.put("id", id);
		return map;
	}

	public static void evalPopStatus(Map<String, Object> map, Integer status) {
		Map<String, Object> props = getProps(map);
		evalStatusEqMap(props, status);
	}

	public static void evalPopProperty(Map<String, Object> map, String key, Object value) {
		Map<String, Object> props = getProps(map);
		props.put(key, value);
	}
}
