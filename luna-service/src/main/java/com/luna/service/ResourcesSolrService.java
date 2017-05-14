/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.List;

import com.luna.service.componet.SuggetVo;
import com.luna.service.dto.ResourceSolrVo;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年4月23日 下午6:24:23
 * @description
 */
public interface ResourcesSolrService {

	public void synchronizedAll();

	public void delete(String deletedIds);

	public List<SuggetVo> sugget(String query);

	public Page<ResourceSolrVo> query(String query, Integer pageNo,Integer pageSize);
	
	public Page<ResourceSolrVo> SimpleQuery(String query, Integer pageNow, Integer pageSize);
}
