/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import com.luna.dao.po.ResourcesContent;
import com.luna.dao.vo.ResourcesCasecade;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年3月20日 下午9:32:40
 * @description
 */
public interface ResourcesContentService {

	Page<ResourcesContent> selectResourcesContents(Long rsId, Integer pageNow);
	
	Page<ResourcesCasecade> selectResourcesCasecade(Long rsId, Integer pageNow);

}
