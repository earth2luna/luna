/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.mapper.IResourcesContentMarkMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.service.data.utils.ConditionUtils;
import com.luna.service.data.utils.Configure;
import com.luna.service.data.utils.ContentUtils;
import com.luna.service.data.utils.Render;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.dto.RenderParameter;
import com.luna.service.dto.ResourcesForm;
import com.luna.service.dto.ResourcesVo;
import com.luna.service.enumer.resource.CreatorEnum;
import com.luna.service.enumer.resource.StatusEnum;
import com.luna.utils.AssertUtils;
import com.luna.utils.DateUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.AppException;
import com.luna.utils.classes.InvokeVo;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月16日 下午10:35:43
 * @description
 */
@Service
public class ResourcesServiceImpl implements ResourcesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesServiceImpl.class);

	@Autowired
	private IResourcesMapper resourcesMapper;
	@Autowired
	private IResourcesContentMapper resourcesContentMapper;
	@Autowired
	private IResourcesContentMarkMapper contentMarkMapper;
	@Autowired
	private FreeMarkerConfigurationFactoryBean factoryBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesService#selectResources(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Page<ResourcesVo> selectResources(String sts, Integer pageNow) {
		Page<Resources> page = ResourcesUtils.selectResources(resourcesMapper, sts, pageNow);
		return ResourcesUtils.transferResourcesVo(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesService#operation(java.lang.Long,
	 * java.lang.Integer)
	 */
	@Override
	public InvokeVo operation(Long rsId, Integer op) {
		InvokeVo invokeVo = null;
		try {

			Object data = null;
			Resources resources = resourcesMapper.selectById(rsId);
			String resourcesRelativePath = Configure.getResourceRelativePath();
			if (LangUtils.equals(2, op)) {
				// 上线
				AssertUtils.isTrue(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
				new Render(new RenderParameter(resourcesMapper, contentMarkMapper, factoryBean.getObject(), null, rsId))
						.renderSingle();
			} else if (LangUtils.equals(3, op)) {
				// 资源删除
				AssertUtils.isTrue(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
				ResourcesUtils.resourcesDeletion(resourcesMapper, rsId);
				ResourcesUtils.deleteResourcesFile(resourcesRelativePath, resources);
			} else if (LangUtils.equals(4, op)) {
				// 表标记删除
				AssertUtils.isTrue(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
				ResourcesUtils.tableMarkDeletion(resourcesMapper, rsId);
			} else if (LangUtils.equals(5, op)) {
				// 逻辑删除
				AssertUtils.isTrue(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
				ResourcesUtils.logicalDeletion(resourcesMapper, rsId);
				ResourcesUtils.deleteResourcesFile(resourcesRelativePath, resources);
			} else if (LangUtils.equals(6, op)) {
				// 获取访问路径
				AssertUtils.isTrue(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
				data = ResourcesUtils.getWebResourcesPath(resourcesRelativePath, resources);
			} else if (LangUtils.equals(7, op)) {
				// 数据删除
				AssertUtils.isTrue(LangUtils.booleanValueOfNumber(rsId), "无效的资源key值");
				resourcesMapper.deleteById(rsId);
				ContentUtils.deleteContentByResourceId(resourcesContentMapper, rsId);
				ResourcesUtils.deleteResourcesFile(resourcesRelativePath, resources);
			} else if (LangUtils.equals(9, op)) {
				// 待上线状态的全部上线全
				new Render(new RenderParameter(resourcesMapper, contentMarkMapper, factoryBean.getObject(),
						String.valueOf(StatusEnum.INIT.getCode()), null)).render();
			} else if (LangUtils.equals(10, op)) {
				// 全部上线
				new Render(new RenderParameter(resourcesMapper, contentMarkMapper, factoryBean.getObject(), null, null))
						.render();
			} else {
				// 无效的权限操作
				throw new AppException("无效的权限操作");
			}
			invokeVo = new InvokeVo("操作成功", data, 1);
		} catch (AppException e) {
			invokeVo = e.getInvokeVo();
		} catch (Exception e) {
			invokeVo = new InvokeVo("未知异常", null, 0);
			LOGGER.error("[operation error]", e);
		}
		return invokeVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesService#selecttById(java.lang.Long)
	 */
	@Override
	public Resources selectById(Long id) {
		if (!LangUtils.booleanValueOfNumber(id))
			return null;
		return resourcesMapper.selectById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesService#modify(com.luna.service.dto.
	 * ResourcesForm)
	 */
	@Override
	public InvokeVo modify(ResourcesForm resourcesForm) {
		InvokeVo invokeVo = null;
		try {
			AssertUtils.notNullOfApp(resourcesForm, "无效的参数");
			AssertUtils.notNullOfApp(CreatorEnum.get(resourcesForm.getCreatorId()), "无效的创建人");
			AssertUtils.notNullOfApp(resourcesForm.getSourceWebsiteLink(), "无效的来源网站链接");
			AssertUtils.notNullOfApp(resourcesForm.getSourceWebsiteName(), "无效的来源网站名称");
			AssertUtils.notNullOfApp(resourcesForm.getSourceAuthor(), "无效的来源作者");
			AssertUtils.notNullOfApp(resourcesForm.getTtl(), "无效的标题");
			AssertUtils.notNullOfApp(resourcesForm.getWebsiteCode(), "无效的网站编码");
			Date sourceDate = DateUtils.parse(resourcesForm.getSourceDate(), DateUtils.DATE_PATTERN_2);
			if (LangUtils.booleanValueOfNumber(resourcesForm.getRsId())) {
				Map<String, Object> map = ConditionUtils.getHashMap();
				ConditionUtils.evalIdEqMap(map, resourcesForm.getRsId());
				ConditionUtils.evalPopStatus(map, LangUtils.intValueOfNumber(StatusEnum.INIT.getCode()));
				ConditionUtils.evalPopProperty(map, "createTime", new Date());
				ConditionUtils.evalPopProperty(map, "categoryId", resourcesForm.getCategoryId());
				ConditionUtils.evalPopProperty(map, "creatorId", resourcesForm.getCreatorId());
				ConditionUtils.evalPopProperty(map, "sourceSiteLink", resourcesForm.getSourceWebsiteLink());
				ConditionUtils.evalPopProperty(map, "sourceSiteName", resourcesForm.getSourceWebsiteName());
				ConditionUtils.evalPopProperty(map, "sourceAuthor", resourcesForm.getSourceAuthor());
				ConditionUtils.evalPopProperty(map, "sourceDate", sourceDate);
				ConditionUtils.evalPopProperty(map, "title", resourcesForm.getTtl());
				ConditionUtils.evalPopProperty(map, "websiteCode", resourcesForm.getWebsiteCode());
				resourcesMapper.update(map);
			} else {
				Resources r = new Resources();
				r.setCreateTime(new Date());
				r.setStatus(LangUtils.intValueOfNumber(StatusEnum.INIT.getCode()));
				r.setCategoryId(resourcesForm.getCategoryId());
				r.setCreatorId(resourcesForm.getCreatorId());
				r.setSourceSiteLink(resourcesForm.getSourceWebsiteLink());
				r.setSourceSiteName(resourcesForm.getSourceWebsiteName());
				r.setSourceAuthor(resourcesForm.getSourceAuthor());
				r.setSourceDate(sourceDate);
				r.setTitle(resourcesForm.getTtl());
				r.setWebsiteCode(resourcesForm.getWebsiteCode());
				resourcesMapper.insert(r);
			}
			invokeVo = new InvokeVo("操作成功", null, 1);
		} catch (AppException e) {
			invokeVo = e.getInvokeVo();
		} catch (Exception e) {
			invokeVo = new InvokeVo("未知异常", null, 0);
			LOGGER.error("[operation error]", e);
		}
		return invokeVo;
	}

}
