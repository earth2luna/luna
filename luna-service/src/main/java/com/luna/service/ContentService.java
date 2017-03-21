/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.List;

import com.luna.dao.po.ResourcesContent;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.service.dto.ContentForm;
import com.luna.utils.classes.InvokeVo;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月20日 下午9:32:40
 * @description
 */
public interface ContentService {

	Page<ResourcesContent> selectResourcesContents(Long rsId, Integer pageNow);
	
	Page<ResourcesCasecade> selectResourcesCasecade(Long rsId, Integer pageNow);
	
	List<ResourcesContent> selectParentContents(Long rsId);

	ResourcesContent selectById(Long cId);

	InvokeVo modify(ContentForm contentForm);
}
