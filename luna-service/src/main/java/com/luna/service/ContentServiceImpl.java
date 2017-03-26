/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.po.ResourcesContent;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.data.utils.ConditionUtils;
import com.luna.service.data.utils.ContentUtils;
import com.luna.service.dto.ContentForm;
import com.luna.utils.AssertUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.RegexUtils;
import com.luna.utils.classes.AppException;
import com.luna.utils.classes.InvokeVo;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月20日 下午9:39:00
 * @description
 */
@Service
public class ContentServiceImpl implements ContentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

	@Autowired
	private IResourcesContentMapper resourcesContentMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.ResourcesContentService#selectResourcesContents(java.
	 * lang.Long, java.lang.Integer)
	 */
	@Override
	public Page<ResourcesContent> selectResourcesContents(Long rsId, Integer pageNow) {
		AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
		return ContentUtils.selectResourcesContents(resourcesContentMapper, rsId, pageNow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.ResourcesContentService#selectResourcesCasecade(java.
	 * lang.Long, java.lang.Integer)
	 */
	@Override
	public Page<ResourcesCasecade> selectResourcesCasecade(Long rsId, Integer pageNow) {
		AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
		return ContentUtils.selectResourcesCasecade(resourcesContentMapper, rsId, pageNow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.ResourcesContentService#selectParentContents(java.lang.
	 * Long)
	 */
	@Override
	public List<ResourcesContent> selectParentContents(Long rsId) {
		AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
		return ContentUtils.selectParentContents(resourcesContentMapper, rsId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesContentService#selectById(java.lang.Long)
	 */
	@Override
	public ResourcesContent selectById(Long cId) {
		if (null == cId)
			return null;
		return resourcesContentMapper.selectById(cId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.ResourcesContentService#modify(com.luna.service.dto.
	 * ContentForm)
	 */
	@Override
	public InvokeVo modify(ContentForm contentForm) {
		InvokeVo invokeVo = null;
		try {
			if (LangUtils.booleanValueOfNumber(contentForm.getKey())) {
				ResourcesContent content = transfer2ResourcesContent(
						resourcesContentMapper.selectById(contentForm.getKey()), contentForm);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(ConditionUtils.DEFAULT_PROP_KEY, content);
				map.put("id", contentForm.getKey());
				resourcesContentMapper.updateById(map);
			} else {
				ResourcesContent content = transfer2ResourcesContent(new ResourcesContent(), contentForm);
				resourcesContentMapper.insert(content);
			}
			invokeVo = new InvokeVo("执行成功", null, 1);
		} catch (AppException e) {
			invokeVo = e.getInvokeVo();
		} catch (Exception e) {
			invokeVo = new InvokeVo("未知异常", null, 0);
			LOGGER.error("[modify error]", e);
		}
		return invokeVo;
	}

	private ResourcesContent transfer2ResourcesContent(ResourcesContent content, ContentForm contentForm) {
		AssertUtils.notNullOfApp(contentForm, "无效的内容参数");
		AssertUtils.isTrueOfApp(RegexUtils.matches(contentForm.getSt(), "\\d+", true), "无效的排序规则");
		AssertUtils.notNullOfApp(contentForm.getHc(), "无效的处理方式");
		AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(contentForm.getRsId()), "无效的资源");
		AssertUtils.isTrueOfApp(null == contentForm.getpId() || LangUtils.booleanValueOfNumber(contentForm.getpId()),
				"无效的父内容参数");
		content.setContent(contentForm.getCont());
		content.setHandlerCode(contentForm.getHc());
		content.setPath(contentForm.getPth());
		content.setPId(contentForm.getpId());
		content.setResourcesId(contentForm.getRsId());
		content.setSortCode(NumberUtils.toInt(contentForm.getSt()));
		content.setTitle(contentForm.getTt());
		return content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ContentService#delete(java.lang.Long)
	 */
	@Override
	public InvokeVo delete(Long cId) {
		InvokeVo invokeVo = null;
		try {
			AssertUtils.isTrueOfApp(LangUtils.booleanValueOfNumber(cId), "无效的key值");
			resourcesContentMapper.deleteById(cId);
			invokeVo = new InvokeVo("执行成功", null, 1);
		} catch (AppException e) {
			invokeVo = e.getInvokeVo();
		} catch (Exception e) {
			invokeVo = new InvokeVo("未知异常", null, 0);
			LOGGER.error("[modify error]", e);
		}
		return invokeVo;
	}
}
