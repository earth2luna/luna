/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import com.luna.dao.po.Resources;
import com.luna.service.dto.ResourcesForm;
import com.luna.service.dto.ResourcesVo;
import com.luna.utils.classes.InvokeVo;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月16日 下午10:26:59
 * @description
 */
public interface ResourcesService {
	
	Page<ResourcesVo> selectResources(String sts, Integer pageNow);

	InvokeVo operation(Long rsId, Integer op);

	Resources selectById(Long id);
	
	InvokeVo modify(ResourcesForm resourcesForm);
}
