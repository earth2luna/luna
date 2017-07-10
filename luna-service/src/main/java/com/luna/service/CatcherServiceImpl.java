/**
 * copyright@laulyl
 */
package com.luna.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.luna.dao.mapper.ICatcherRuleMapper;
import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.dao.po.CatcherRule;
import com.luna.service.catcher.CatchRuler;
import com.luna.service.catcher.CatcherModel;
import com.luna.service.catcher.CatcherProcessor;
import com.luna.service.data.utils.CatcherRuleUtils;
import com.luna.service.data.utils.ConditionUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.classes.Page;

import us.codecraft.webmagic.Spider;

/**
 * @author laulyl
 * @date 2017年7月10日 上午12:22:24
 * @desction
 */
@Service
public class CatcherServiceImpl implements CatcherService {

	@Autowired
	private ICatcherRuleMapper catcherRuleMapper;
	@Autowired
	private IResourcesMapper resourcesMapper;
	@Autowired
	private IResourcesContentMapper contentMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.CatcherService#modify(com.luna.service.catcher.CatcherModel)
	 */
	@Override
	public void modify(CatcherModel catcherModel, CatchRuler catchRuler) {
		merge(catcherModel, catchRuler);
		CatcherRule t = new CatcherRule();
		t.setId(catcherModel.getId());
		t.setContent(JSON.toJSONString(catcherModel, SerializerFeature.DisableCircularReferenceDetect));
		if (LangUtils.booleanValueOfNumber(t.getId())) {
			catcherRuleMapper.update(ConditionUtils.evalPops(t));
		} else {
			catcherRuleMapper.insert(t);
		}
	}

	private void merge(CatcherModel catcherModel, CatchRuler catchRuler) {
		merge(catcherModel.getResourceTitleCatchRulers(), catchRuler);
		merge(catcherModel.getResourceAuthorCatchRulers(), catchRuler);
		merge(catcherModel.getResourceDateCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getContentCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getContentPathCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getOneLevelContentTitleCatchRulers(), catchRuler);
		merge(catcherModel.getIteratorRuler().getTwoLevelContentTitleCatchRulers(), catchRuler);
	}

	private void merge(List<CatchRuler> catchRulers, CatchRuler catchRuler) {
		if (CollectionUtils.isNotEmpty(catchRulers)) {
			catchRulers.forEach(element -> merge(element, catchRuler));
		}
	}

	private void merge(CatchRuler catchRuler1, CatchRuler catchRuler2) {
		catchRuler1.setBreakValues(catchRuler2.getBreakValues());
		catchRuler1.setEqualsFilters(catchRuler2.getEqualsFilters());
		catchRuler1.setIndexOfFilters(catchRuler2.getIndexOfFilters());
		catchRuler1.setReplaceModels(catchRuler2.getReplaceModels());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CatcherService#selectCatcherRules(java.lang.Integer)
	 */
	@Override
	public Page<CatcherModel> selectCatcherRules(Integer pageNow) {
		return CatcherRuleUtils.selectCatcherRules(catcherRuleMapper, 10, pageNow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CatcherService#select(java.lang.Long)
	 */
	@Override
	public CatcherModel select(Long id) {
		CatcherModel ret = null;
		if (LangUtils.booleanValueOfNumber(id))
			ret = CatcherRuleUtils.transfer(catcherRuleMapper.selectById(id));
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CatcherService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		catcherRuleMapper.deleteById(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CatcherService#catching(java.lang.Long)
	 */
	@Override
	public void catching(Long id) {
		CatcherModel catcherModel = select(id);
		Validate.notNull(catcherModel);
		Spider.create(new CatcherProcessor(resourcesMapper, contentMapper, catcherModel))
				// 开始抓
				.addUrl(catcherModel.getCatcherWebUrl())
				// 开启5个线程抓取
				.thread(1)
				// 启动爬虫
				.run();
	}

}
