/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.Resources;
import com.luna.service.data.utils.ResourcesUtils;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.ResourcesService#selectResources(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Page<Resources> selectResources(String sts, Integer pageNow) {
		return ResourcesUtils.selectResources(resourcesMapper, sts, pageNow);
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
			if (LangUtils.equals(2, op)) {
				// 上线
			} else if (LangUtils.equals(2, op)) {
				// 资源删除
			} else if (LangUtils.equals(4, op)) {
				// 标记删除
			} else if (LangUtils.equals(5, op)) {
				// 逻辑删除
			} else if (LangUtils.equals(10, op)) {
				// 全部上线
			} else {
				// 无效的权限操作
				throw new AppException(0, "无效的权限操作");
			}
			invokeVo = new InvokeVo("操作成功", null, 1);
			new InvokeVo();
		} catch (AppException e) {
			invokeVo = e.getInvokeVo();
		} catch (Exception e) {
			invokeVo = new InvokeVo("未知异常", null, 0);
			LOGGER.error("[operation error]", e);
		}
		return invokeVo;
	}

}
