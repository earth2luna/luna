/**
 * COPYRIGHT@LAULYL
 */
package com.luna.dao.mapper;

import java.util.List;
import java.util.Map;

import com.luna.dao.po.ResourcesContent;
import com.luna.dao.vo.ResourcesCasecade;

/**
 * @author laulyl
 * @date 2017年2月25日 下午9:20:15
 * @description
 */
public interface IResourcesContentMapper extends IMapper<ResourcesContent> {

	public List<ResourcesCasecade> selectResourcesCasecade(Map<String, Object> map);
}
