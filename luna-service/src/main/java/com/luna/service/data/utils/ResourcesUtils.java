/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.dao.po.ResourcesContentMark;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年2月28日 下午4:16:12
 * @description
 */
public class ResourcesUtils {

	public static List<ResourcesCasecade> selectResourcesCasecades(IResourcesMapper resourcesMapper, int startIndex,
			int endIndex) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startIndex", startIndex);
		map.put("endIndex", 1);
		List<ResourcesCasecade> casecades = resourcesMapper.selectResourcesCasecade(map);
		return casecades;
	}

	public static List<ResourcesCasecade> selectResourcesCasecades(IResourcesMapper resourcesMapper, int startIndex) {
		return selectResourcesCasecades(resourcesMapper, startIndex, 1);
	}

	public static List<ResourcesContentMark> selectResourcesContentMarks(IResourcesContentMarkMapper contentMarkMapper,
			List<Long> resourcesContentIds) {
		if (CollectionUtils.isEmpty(resourcesContentIds))
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourcesContentIds", resourcesContentIds);
		return contentMarkMapper.selectList(map);
	}

	public static List<ResourcesContentMark> selectResourcesContentMarksAsCasecades(
			IResourcesContentMarkMapper contentMarkMapper, List<ResourcesCasecade> casecades) {
		List<Long> list = LangUtils.joins2ArrayList(casecades, new InputResourcesCasecadeOutputId());
		return selectResourcesContentMarks(contentMarkMapper, list);
	}

	public static Map<Long, List<ResourcesContentMark>> selectResourcesContentMarkMapAsCasecades(
			IResourcesContentMarkMapper contentMarkMapper, List<ResourcesCasecade> casecades) {
		List<ResourcesContentMark> contentMarks = selectResourcesContentMarksAsCasecades(contentMarkMapper, casecades);
		Map<Long, List<ResourcesContentMark>> contentMarkMap = new HashMap<Long, List<ResourcesContentMark>>();
		if (CollectionUtils.isNotEmpty(contentMarks)) {
			Iterator<ResourcesContentMark> iterator = contentMarks.iterator();
			while (iterator.hasNext()) {
				ResourcesContentMark contentMark = iterator.next();
				Long key = contentMark.getResourcesContentId();
				List<ResourcesContentMark> value = contentMarkMap.get(key);
				if (null == value) {
					value = new ArrayList<ResourcesContentMark>();
					contentMarkMap.put(key, value);
				}
				value.add(contentMark);
			}
		}
		return contentMarkMap;
	}

	public static Page<Resources> selectResources(IResourcesMapper resourcesMapper, String sts, Integer pageNow) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageSize = 1;
		int defaultPageNow = LangUtils.defaultValue(!(null == pageNow || pageNow <= 0), pageNow, 1);
		ServiceUtils.evalStatusInMap(map, sts);
		ServiceUtils.evalPageMap(map, defaultPageNow, pageSize);
		return new Page<Resources>(resourcesMapper.selectList(map), resourcesMapper.selectCount(map), pageSize,
				defaultPageNow);
	}

}
