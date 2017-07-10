/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service;

import java.util.List;

import com.luna.dao.vo.KeyNameVo;
import com.luna.service.catcher.CatchRuler;
import com.luna.service.catcher.CatcherModel;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年4月6日 下午6:26:28
 * @description
 */
public interface CatcherService {

	public void modify(CatcherModel catcherModel, CatchRuler catchRuler);

	public Page<CatcherModel> selectCatcherRules(Integer pageNow);
	
	public CatcherModel select(Long id);
	
	public void delete(Long id);
	
	public void catching(Long id);
	
	public List<KeyNameVo> selectWebsiteKeyName();
}
