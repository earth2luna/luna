/**
 * COPYRIGHT@LAULYL
 */
package com.luna.dao.mapper;

import java.util.List;
import java.util.Map;

import com.luna.dao.po.Resources;
import com.luna.dao.vo.KeyNameVo;
import com.luna.dao.vo.ResourcesCasecade;

/**
 * @author laulyl
 * @date 2017年2月25日 下午9:20:15
 * @description
 */
public interface IResourcesMapper extends IMapper<Resources> {

	public List<ResourcesCasecade> selectResourcesCasecade(Map<String, Object> map);
	
	public List<KeyNameVo> selectWebsiteKeyName();
}
