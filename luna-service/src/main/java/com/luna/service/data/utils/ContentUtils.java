/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.data.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.po.ResourcesContent;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.common.impl.IInputContentOutputAttachement;
import com.luna.service.dto.CatcherContent;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月20日 下午10:33:37
 * @description
 */
public class ContentUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentUtils.class);

	public static int selectBetweenContentCountById(IResourcesContentMapper resourcesContentMapper, Long ltId,
			Long gtId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ltId", ltId);
		map.put("gtId", gtId);
		return resourcesContentMapper.selectCount(map);
	}

	public static int selectBetweenContentCountByResourceId(IResourcesContentMapper resourcesContentMapper,
			Long ltResourcesId, Long gtResourcesId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ltResourcesId", ltResourcesId);
		map.put("gtResourcesId", gtResourcesId);
		return resourcesContentMapper.selectCount(map);
	}

	public static ResourcesContent selectBetweenContentByResourceId(IResourcesContentMapper resourcesContentMapper,
			Long ltResourcesId, Long gtResourcesId, String idOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ltResourcesId", ltResourcesId);
		map.put("gtResourcesId", gtResourcesId);
		ConditionUtils.evalSortOrderMap(map, "id", idOrder);
		ConditionUtils.evalPageMap(map, 1, 1);
		List<ResourcesContent> contents = resourcesContentMapper.selectList(map);
		ResourcesContent content = null;
		if (CollectionUtils.isNotEmpty(contents)) {
			content = contents.get(0);
		}
		return content;
	}

	public static Page<ResourcesContent> selectResourcesContents(IResourcesContentMapper resourcesContentMapper,
			Long rsId, Integer pageNow) {
		Map<String, Object> map = ConditionUtils.getHashMap();
		int pageSize = ConditionUtils.DEFAULT_PAGE_SIZE;
		int defaultPageNow = LangUtils.defaultValue(!(null == pageNow || pageNow <= 0), pageNow, 1);
		map.put("resourcesId", rsId);
		ConditionUtils.evalPageMap(map, defaultPageNow, pageSize);
		ConditionUtils.evalSortOrderMap(map, "id ASC,sortCode", ConditionUtils.ASC);
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

	public static void insertCatchers(IResourcesContentMapper mapper, List<CatcherContent> catcherContent,
			long resourcesId) {
		if (CollectionUtils.isNotEmpty(catcherContent)) {
			Iterator<CatcherContent> iterator = catcherContent.iterator();
			int sortCode = 0;
			while (iterator.hasNext()) {
				CatcherContent content = iterator.next();
				if (null == content.getParentLevelId()) {
					++sortCode;
					content.setSortCode(sortCode);
					content.setResourcesId(resourcesId);
					mapper.insert(content);
					List<ResourcesContent> contents = getContentsByLevleId(catcherContent, content.getLevelId());
					insert(mapper, contents, resourcesId, content.getId());
				}
			}
		}
	}

	public static void deleteContentByResourceId(IResourcesContentMapper resourcesContentMapper, Long rsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourcesId", rsId);
		List<ResourcesContent> contents = resourcesContentMapper.selectList(map);
		if (CollectionUtils.isNotEmpty(contents)) {
			resourcesContentMapper.delete(map);
			List<String> attahements = LangUtils.joins2ArrayList(contents, new IInputContentOutputAttachement());
			if (CollectionUtils.isNotEmpty(attahements)) {
				Iterator<String> iterator = attahements.iterator();
				while (iterator.hasNext()) {
					String absoluteGeneratePath = FilePropertyUtils.appendPath(
							FilePropertyUtils.getWebAppFile().getAbsolutePath(), Configure.getAttachementPath(),
							iterator.next());
					File file = new File(absoluteGeneratePath);
					if (file.exists()) {
						file.delete();
					}
				}
			}
		}
	}

	public static List<ResourcesContent> getContentsByLevleId(List<CatcherContent> catcherContent, Long levelId) {
		List<ResourcesContent> contents = new ArrayList<ResourcesContent>();
		if (CollectionUtils.isNotEmpty(catcherContent)) {
			Iterator<CatcherContent> iterator = catcherContent.iterator();
			while (iterator.hasNext()) {
				CatcherContent content = iterator.next();
				if (LangUtils.equals(levelId, content.getParentLevelId())) {
					contents.add(content);
				}
			}
		}
		return contents;
	}

	public static void insert(IResourcesContentMapper mapper, List<ResourcesContent> resourcesContents,
			long resourcesId, Long parentId) {
		if (CollectionUtils.isNotEmpty(resourcesContents)) {
			Iterator<ResourcesContent> iterator = resourcesContents.iterator();
			int sortCode = 0;
			while (iterator.hasNext()) {
				++sortCode;
				ResourcesContent content = iterator.next();
				content.setSortCode(sortCode);
				content.setResourcesId(resourcesId);
				content.setPId(parentId);
				try {
					mapper.insert(content);
				} catch (Exception e) {
					LOGGER.error(content.toString(), e);
				}
			}
		}
	}
}
