/**
 * copyright@laulyl
 */
package com.luna.service;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.luna.dao.mapper.IResourcesMapper;
import com.luna.service.data.utils.ResourcesUtils;
import com.luna.service.db.DataBaseHelpers;
import com.luna.service.db.Table;
import com.luna.service.db.TableMetaDataHandler;
import com.luna.service.sync.ExportResourceFile;
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

	private Integer pageSize = 100;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.FunctionService#exportResourceData()
	 */
	@Override
	public void exportResourceData(Long ltId, Long gtId,String filePath) {
		Connection connection = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			TableMetaDataHandler dataHandler = new TableMetaDataHandler();
			dataHandler.handle(connection.getMetaData());
			String tableName = "l_resources";
			Table table = dataHandler.getTable(tableName);
			int count = ResourcesUtils.selectBetweenResourceCount(resourcesMapper, ltId, gtId);
			int divisor = count / pageSize;
			int mod = count % pageSize;
			if (LangUtils.booleanValueOfNumber(mod)) {
				divisor += 1;
			}
			new ExportResourceFile(jdbcTemplate, table, pageSize, ltId, gtId,filePath)
					.synchronizedAll(divisor);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DataBaseHelpers.closeConnection(connection);
		}
	}

	
}
