/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.vo.ResourcesCasecade;

/**
 * @author laulyl
 * @date 2017年2月28日 下午4:16:12
 * @description
 */
public class ResourcesUtils {

	public static List<ResourcesCasecade> selectResourcesCasecades(IResourcesMapper resourcesMapper, int startIndex,
			int endIndex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startIndex", 0);
		map.put("endIndex", 1);
		List<ResourcesCasecade> casecades = resourcesMapper.selectResourcesCasecade(map);
		return casecades;
	}

	public static List<ResourcesCasecade> selectResourcesCasecades(IResourcesMapper resourcesMapper, int startIndex) {
		return selectResourcesCasecades(resourcesMapper, startIndex, 1);
	}

}
