/**
 * copyright@laulyl
 */
package com.luna.service;

import java.util.ArrayList;
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
import com.luna.dao.po.Resources;
import com.luna.dao.vo.KeyNameVo;
import com.luna.service.catcher.CatchRuler;
import com.luna.service.catcher.CatcherModel;
import com.luna.service.catcher.CatcherProcessor;
import com.luna.service.data.utils.CatcherRuleUtils;
import com.luna.service.data.utils.ConditionUtils;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.enumer.content.HandlerMethodEnum;
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
		t.setContent(JSON.toJSONString(catcherModel, SerializerFeature.DisableCircularReferenceDetect));
		if (LangUtils.booleanValueOfNumber(catcherModel.getId())) {
			catcherRuleMapper.update(ConditionUtils.evalUpdateMap(catcherModel.getId(), t));
		} else {
			catcherRuleMapper.insert(t);
		}
	}

	private void merge(CatcherModel catcherModel, CatchRuler catchRuler) {
		merge(catcherModel.getResourceTitleCatchRulers(), catchRuler, HandlerMethodEnum.P.getCode());
		merge(catcherModel.getResourceAuthorCatchRulers(), catchRuler, HandlerMethodEnum.P.getCode());
		merge(catcherModel.getResourceDateCatchRulers(), catchRuler, HandlerMethodEnum.P.getCode());
		merge(catcherModel.getIteratorRuler().getContentCatchRulers(), catchRuler, HandlerMethodEnum.P.getCode());
		merge(catcherModel.getIteratorRuler().getContentPathCatchRulers(), catchRuler,
				HandlerMethodEnum.IMAGE.getCode());
		merge(catcherModel.getIteratorRuler().getOneLevelContentTitleCatchRulers(), catchRuler,
				HandlerMethodEnum.P.getCode());
		merge(catcherModel.getIteratorRuler().getTwoLevelContentTitleCatchRulers(), catchRuler,
				HandlerMethodEnum.P.getCode());
	}

	private void merge(List<CatchRuler> catchRulers, CatchRuler catchRuler, Integer defaultHandlerCode) {
		if (CollectionUtils.isNotEmpty(catchRulers)) {
			catchRulers.forEach(element -> merge(element, catchRuler, defaultHandlerCode));
		}
	}

	private void merge(CatchRuler catchRuler1, CatchRuler catchRuler2, Integer defaultHandlerCode) {
		catchRuler1.setBreakValues(catchRuler2.getBreakValues());
		catchRuler1.setEqualsFilters(catchRuler2.getEqualsFilters());
		catchRuler1.setIndexOfFilters(catchRuler2.getIndexOfFilters());
		catchRuler1.setReplaceModels(catchRuler2.getReplaceModels());
		if (!LangUtils.booleanValueOfNumber(catchRuler1.getHandlerCode())) {
			catchRuler1.setHandlerCode(defaultHandlerCode);
		}
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
		Validate.isTrue(LangUtils.booleanValueOfNumber(id), "无效的key 值");
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
		Validate.notNull(catcherModel, "无效的key值");
		Resources resourcecs = ResourcesUtils.selecResourcesBySiteLink(resourcesMapper,
				catcherModel.getCatcherWebUrl());
		Validate.isTrue(null == resourcecs, "url 已存在");
		Spider.create(new CatcherProcessor(resourcesMapper, contentMapper, catcherModel))
				// 开始抓
				.addUrl(catcherModel.getCatcherWebUrl())
				// 开启5个线程抓取
				.thread(1)
				// 启动爬虫
				.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CatcherService#selectWebsiteKeyName()
	 */
	@Override
	public List<KeyNameVo> selectWebsiteKeyName() {
		List<KeyNameVo> keyNameVos = resourcesMapper.selectWebsiteKeyName();
		Long code = 0L;
		if (CollectionUtils.isNotEmpty(keyNameVos)) {
			KeyNameVo keyNameVo = keyNameVos.stream().findFirst().get();
			code = keyNameVo.getId();
		} else {
			keyNameVos = new ArrayList<KeyNameVo>();
		}
		KeyNameVo keyNameVo = new KeyNameVo();
		keyNameVo.setId(++code);
		keyNameVo.setDescription("MAX");
		keyNameVos.add(0, keyNameVo);
		return keyNameVos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.CatcherService#copy(java.lang.Long)
	 */
	@Override
	public void copy(Long key) {
		Validate.isTrue(LangUtils.booleanValueOfNumber(key), "无效的key 值");
		CatcherRule catcherRule = catcherRuleMapper.selectById(key);
		Validate.notNull(catcherRule, "无效的key值");
		catcherRule.setId(null);
		catcherRuleMapper.insert(catcherRule);
	}

}
