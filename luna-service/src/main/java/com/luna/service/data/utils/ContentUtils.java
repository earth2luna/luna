/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.po.ResourcesContent;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月20日 下午10:33:37
 * @description
 */
public class ContentUtils {

	public static Page<ResourcesContent> selectResourcesContents(IResourcesContentMapper resourcesContentMapper, Long rsId,
			Integer pageNow) {
		Map<String, Object> map = ConditionUtils.getHashMap();
		int pageSize = ConditionUtils.DEFAULT_PAGE_SIZE;
		int defaultPageNow = LangUtils.defaultValue(!(null == pageNow || pageNow <= 0), pageNow, 1);
		map.put("resourcesId", rsId);
		ConditionUtils.evalPageMap(map, defaultPageNow, pageSize);
		ConditionUtils.evalSortOrderMap(map, "sortCode", ConditionUtils.ASC);
		return new Page<ResourcesContent>(resourcesContentMapper.selectList(map),
				resourcesContentMapper.selectCount(map), pageSize, defaultPageNow);
	}

	public static Page<ResourcesCasecade> selectResourcesCasecade(IResourcesContentMapper resourcesContentMapper,
			Long rsId, Integer pageNow) {
		Map<String, Object> map = ConditionUtils.getHashMap();
		int pageSize = ConditionUtils.DEFAULT_PAGE_SIZE;
		int defaultPageNow = LangUtils.defaultValue(!(null == pageNow || pageNow <= 0), pageNow, 1);
		map.put("resourcesId", rsId);
		ConditionUtils.evalPageMap(map, defaultPageNow, pageSize);
		return new Page<ResourcesCasecade>(resourcesContentMapper.selectResourcesCasecade(map),
				resourcesContentMapper.selectCount(map), pageSize, defaultPageNow);
	}

	public static List<ResourcesContent> selectParentContents(IResourcesContentMapper resourcesContentMapper,
			Long rsId) {
		Map<String, Object> map = ConditionUtils.getHashMap();
		map.put("pIdIsNull", true);
		map.put("resourcesId", rsId);
		return resourcesContentMapper.selectList(map);
	}
	
	public static void insert(IResourcesContentMapper mapper,List<ResourcesContent> resourcesContents,long resourcesId){
		if(CollectionUtils.isNotEmpty(resourcesContents)){
			Iterator<ResourcesContent> iterator=resourcesContents.iterator();
			while(iterator.hasNext()){
				ResourcesContent content=iterator.next();
				content.setResourcesId(resourcesId);
				mapper.insert(content);
			}
		}
	}
}
