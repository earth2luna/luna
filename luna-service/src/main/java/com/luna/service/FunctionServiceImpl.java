/**
 * copyright@laulyl
 */
package com.luna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesContentMapper;
import com.luna.dao.mapper.IResourcesMapper;
import com.luna.service.data.utils.ContentUtils;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.sync.ExportData;
import com.luna.utils.LangUtils;

/**
 * @author laulyl
 * @date 2017年7月2日 下午5:46:19
 * @desction
 */
@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IResourcesMapper resourcesMapper;
	@Autowired
	IResourcesContentMapper resourcesContentMapper;

	private Integer pageSize = 100;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.FunctionService#exportResourceData()
	 */
	@Override
	public void exportResourceData(Long ltId, Long gtId, String filePath) {
		String tableName = "l_resources";
		int totalCount = ResourcesUtils.selectBetweenResourceCount(resourcesMapper, ltId, gtId);
		export(tableName, totalCount, ltId, gtId, filePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.FunctionService#exportResourceContentData(java.lang.Long,
	 * java.lang.Long, java.lang.String)
	 */
	@Override
	public void exportResourceContentData(Long ltId, Long gtId, String filePath) {
		String tableName = "l_resources_content";
		int totalCount = ContentUtils.selectBetweenContentCount(resourcesContentMapper, ltId, gtId);
		export(tableName, totalCount, ltId, gtId, filePath);
	}

	private void export(String tableName, int totalCount, Long ltId, Long gtId, String filePath) {
		int divisor = totalCount / pageSize;
		int mod = totalCount % pageSize;
		if (LangUtils.booleanValueOfNumber(mod)) {
			divisor += 1;
		}
		new ExportData(jdbcTemplate, tableName, pageSize, ltId, gtId, filePath).synchronizedAll(divisor);
	}
}
