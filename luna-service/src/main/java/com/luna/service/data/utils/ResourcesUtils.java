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

import com.luna.dao.mapper.IMapper;
import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.dao.po.ResourcesContentMark;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.common.impl.InputResourcesCasecadeOutputId;
import com.luna.service.componet.ResourceSolr;
import com.luna.service.dto.ResourcesVo;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.service.node.ResourcesCasecadeNode;
import com.luna.utils.AssertUtils;
import com.luna.utils.FilePropertyUtils;
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

	public static List<ResourcesCasecade> selectResourcesCasecades(IResourcesMapper resourcesMapper, Integer pageNow,
			String sts, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ConditionUtils.evalIdEqMap(map, id);
		ConditionUtils.evalPageMap(map, pageNow, 1);
		ConditionUtils.evalStatusInMap(map, sts);
		ConditionUtils.evalSortOrderMap(map, "id", "asc");
		return resourcesMapper.selectResourcesCasecade(map);
	}

	public static List<Resources> selectPreviousResourcesCasecades(IResourcesMapper resourcesMapper, Long currentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ltId", currentId);
		ConditionUtils.evalStatusInMap(map, String.valueOf(StatusEnum.ONLINE.getCode()));
		ConditionUtils.evalPageMap(map, 1, 1);
		ConditionUtils.evalSortOrderMap(map, "id", "desc");
		return resourcesMapper.selectList(map);
	}

	public static List<Resources> selectNextResourcesCasecades(IResourcesMapper resourcesMapper, Long currentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gtId", currentId);
		ConditionUtils.evalStatusInMap(map, String.valueOf(StatusEnum.ONLINE.getCode()));
		ConditionUtils.evalPageMap(map, 1, 1);
		ConditionUtils.evalSortOrderMap(map, "id", "asc");
		return resourcesMapper.selectList(map);
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
		int pageSize = ConditionUtils.DEFAULT_PAGE_SIZE;
		int defaultPageNow = LangUtils.defaultValue(!(null == pageNow || pageNow <= 0), pageNow, 1);
		ConditionUtils.evalStatusInMap(map, sts);
		ConditionUtils.evalPageMap(map, defaultPageNow, pageSize);
		ConditionUtils.evalSortOrderMap(map, "createTime", ConditionUtils.DESC);
		return new Page<Resources>(resourcesMapper.selectList(map), resourcesMapper.selectCount(map), pageSize,
				defaultPageNow);
	}

	public static Page<Resources> selectResources(IResourcesMapper resourcesMapper, String sts, Long categoryId,
			int pageSize, Integer pageNow) {
		Map<String, Object> map = new HashMap<String, Object>();
		int defaultPageNow = LangUtils.defaultValue(!(null == pageNow || pageNow <= 0), pageNow, 1);
		ConditionUtils.evalStatusInMap(map, sts);
		ConditionUtils.evalPageMap(map, defaultPageNow, pageSize);
		ConditionUtils.evalSortOrderMap(map, "createTime", ConditionUtils.DESC);
		map.put("categoryId", categoryId);
		return new Page<Resources>(resourcesMapper.selectList(map), resourcesMapper.selectCount(map), pageSize,
				defaultPageNow);
	}

	public static Page<ResourcesVo> transferResourcesVo(Page<Resources> pageInput) {
		Page<ResourcesVo> pageOuput = new Page<ResourcesVo>();
		pageOuput.setCount(pageInput.getCount());
		pageOuput.setPageNow(pageInput.getPageNow());
		pageOuput.setPageSize(pageInput.getPageSize());
		List<ResourcesVo> list = new ArrayList<ResourcesVo>();
		pageOuput.setList(list);
		if (CollectionUtils.isNotEmpty(pageInput.getList())) {
			for (Resources resource : pageInput.getList()) {
				ResourcesVo vo = new ResourcesVo();
				vo.setTitle(resource.getTitle());
				vo.setLink(ResourcesUtils.getWebResourcesPath(Configure.getResourceRelativePath(), resource));
				vo.setCreateTime(resource.getCreateTime());
				list.add(vo);
			}
		}
		return pageOuput;
	}

	public static void markStatus(IMapper<Resources> resourcesMapper, Long id, StatusEnum statusEnum) {
		AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(id), "无效的key值");
		Map<String, Object> map = ConditionUtils.getHashMap();
		ConditionUtils.evalIdEqMap(map, id);
		Resources resources = resourcesMapper.selectById(id);
		resources.setStatus(LangUtils.intValueOfNumber(statusEnum.getCode()));
		ConditionUtils.evalPops(map, resources);
		resourcesMapper.updateById(map);
	}

	public static void init(IMapper<Resources> resourcesMapper, Long id) {
		markStatus(resourcesMapper, id, StatusEnum.INIT);
	}

	public static void online(IMapper<Resources> resourcesMapper, Long id) {
		markStatus(resourcesMapper, id, StatusEnum.ONLINE);
	}

	public static void resourcesDeletion(IMapper<Resources> resourcesMapper, Long id) {
		markStatus(resourcesMapper, id, StatusEnum.RESOURCES_DELETION);
	}

	public static void tableMarkDeletion(IMapper<Resources> resourcesMapper, Long id) {
		markStatus(resourcesMapper, id, StatusEnum.TABLE_MARK_DELETION);
	}

	public static void logicalDeletion(IMapper<Resources> resourcesMapper, Long id) {
		markStatus(resourcesMapper, id, StatusEnum.LOGICAL_DELETION);
	}

	public static long insertResources(IMapper<Resources> resourcesMapper, Resources resources) {
		return resourcesMapper.insert(resources);
	}

	public static File getResourcesFile(String resourcesPath, ResourcesCasecadeNode node) {
		String absoluteGeneratePath = FilePropertyUtils.appendPath(FilePropertyUtils.getWebAppFile().getAbsolutePath(),
				resourcesPath, getResourcesFileName(node));
		return new File(absoluteGeneratePath);
	}

	public static File getResourcesFile(String resourcesPath, Resources resource) {
		String absoluteGeneratePath = FilePropertyUtils.appendPath(FilePropertyUtils.getWebAppFile().getAbsolutePath(),
				resourcesPath, getResourcesFileName(resource));
		return new File(absoluteGeneratePath);
	}

	public static String getWebResourcesPath(String resourceGeneratePathPrefix, ResourceSolr resourceSolr) {
		return FilePropertyUtils.appendPath(Configure.getThisWebDomain(), resourceGeneratePathPrefix,
				getResourcesFileName(resourceSolr));
	}

	public static String getWebResourcesPath(String resourceGeneratePathPrefix, Resources resource) {
		return FilePropertyUtils.appendPath(Configure.getThisWebDomain(), resourceGeneratePathPrefix,
				getResourcesFileName(resource));
	}

	public static String getWebResourcesPath(String resourceGeneratePathPrefix, ResourcesCasecadeNode casecadeNode) {
		return FilePropertyUtils.appendPath(Configure.getThisWebDomain(), resourceGeneratePathPrefix,
				getResourcesFileName(casecadeNode));
	}

	public static String getResourcesFileName(Resources resource) {
		return LangUtils.append(resource.getCategoryId(), "-", resource.getWebsiteCode(), "-", resource.getId(),
				".html");
	}

	public static String getResourcesFileName(ResourcesCasecadeNode node) {
		return LangUtils.append(node.getResourcesCategroyId(), "-", node.getWebsiteCode(), "-", node.getResourcesId(),
				".html");
	}

	public static String getResourcesFileName(ResourceSolr resourceSolr) {
		return LangUtils.append(resourceSolr.getCategoryId(), "-", resourceSolr.getWebsiteCode(), "-",
				resourceSolr.getId(), ".html");
	}

	public static void deleteResourcesFile(String resourcesGeneratePath, Resources resource) {
		File file = getResourcesFile(resourcesGeneratePath, resource);
		if (file.exists()) {
			file.delete();
		}
	}

}
