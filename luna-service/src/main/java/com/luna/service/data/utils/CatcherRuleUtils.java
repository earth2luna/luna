/**
 * copyright@laulyl
 */
package com.luna.service.data.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.luna.dao.mapper.ICatcherRuleMapper;
import com.luna.dao.po.CatcherRule;
import com.luna.service.catcher.CatcherModel;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

/**
 * @author laulyl
 * @date 2017年7月10日 上午8:14:02
 * @desction
 */
public class CatcherRuleUtils {

	public static Page<CatcherModel> selectCatcherRules(ICatcherRuleMapper catcherRuleMapper, int pageSize,
			Integer pageNow) {
		Map<String, Object> map = new HashMap<String, Object>();
		int defaultPageNow = LangUtils.defaultValue(!(null == pageNow || pageNow <= 0), pageNow, 1);
		ConditionUtils.evalPageMap(map, defaultPageNow, pageSize);
		ConditionUtils.evalSortOrderMap(map, "id", ConditionUtils.DESC);
		List<CatcherModel> catcherModels = transfer(catcherRuleMapper.selectList(map));
		return new Page<CatcherModel>(catcherModels, catcherRuleMapper.selectCount(map), pageSize, defaultPageNow);
	}

	public static List<CatcherModel> transfer(List<CatcherRule> catcherRules) {
		List<CatcherModel> ret = null;
		if (CollectionUtils.isNotEmpty(catcherRules)) {
			ret = catcherRules.stream().map(catcherRule -> transfer(catcherRule)).collect(Collectors.toList());
		}
		return ret;
	}

	public static CatcherModel transfer(CatcherRule catcherRule) {
		CatcherModel catcherModel = JSON.parseObject(catcherRule.getContent(), CatcherModel.class);
		catcherModel.setId(catcherRule.getId());
		return catcherModel;
	}
}
