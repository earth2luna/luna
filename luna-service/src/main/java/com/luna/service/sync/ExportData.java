/**
 * copyright@laulyl
 */
package com.luna.service.sync;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.luna.service.db.Column;
import com.luna.service.db.DataBaseHelpers;
import com.luna.service.db.Table;
import com.luna.service.db.TableMetaDataHandler;
import com.luna.utils.DateUtils;
import com.luna.utils.FilePropertyUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.enm.CharsetEnum;

/**
 * @author laulyl
 * @date 2017年7月6日 下午10:22:09
 * @desction
 */
public class ExportData extends AbstractListWhileDoNormal<Map<String, Object>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExportData.class);

	protected JdbcTemplate jdbcTemplate;
	protected String filePath;
	private String tableName;
	private Integer pageSize;
	private Long ltId;
	private Long gtId;

	private Table table;
	private String insertTableFormat;
	private File exportFile;

	public ExportData(JdbcTemplate jdbcTemplate, String tableName, Integer pageSize, Long ltId, Long gtId,
			String filePath) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.tableName = tableName;
		this.pageSize = pageSize;
		this.ltId = ltId;
		this.gtId = gtId;
		this.filePath = filePath;
	}

	public ExportData() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.sync.AbstractListWhileDoNormal#getList(long)
	 */
	@Override
	public List<Map<String, Object>> getList(long count) {
		return jdbcTemplate.queryForList(
				"SELECT * FROM " + tableName + " WHERE id BETWEEN ? AND ? ORDER BY id ASC LIMIT ?,?", ltId, gtId,
				(count - 1) * pageSize, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.luna.service.sync.AbstractListWhileDoNormal#doElement(java.lang.Object,
	 * long)
	 */
	@Override
	public void doElement(Map<String, Object> t, long count) {
		StringBuilder builder = new StringBuilder();
		List<Column> columns = table.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			Column column = columns.get(i);
			Object object = t.get(column.getName());
			if (null == object) {
				builder.append("null");
			} else {
				builder.append("'");
				switch (column.getJdbcTypeEnum()) {
				case TIMESTAMP:
				case DATE:
				case TIME:
					builder.append(DateUtils.getDateFormat(object, DateUtils.DATE_PATTERN_1));
					break;
				default:
					builder.append(object.toString().replace("\n", "\\n").replace("'", "\\'"));
				}
				builder.append("'");
			}
			if (i < columns.size() - 1) {
				builder.append(",");
			}
		}
		String insertData = String.format(insertTableFormat, builder);
		LOGGER.info(insertData);
		try {
			FileUtils.write(exportFile, LangUtils.append(insertData, "\r\n"), CharsetEnum.UTF8.getCharsetName(), true);
		} catch (IOException e) {
			LOGGER.error("file write error:", e);
		}

	}

	protected void clearFile(File exportFile) {
		FilePropertyUtils.deleteFile(exportFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.sync.AbstractListWhileDoNormal#before()
	 */
	@Override
	public void before() {
		// 先删除
		exportFile = new File(filePath);
		clearFile(exportFile);

		Connection connection = null;
		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			TableMetaDataHandler dataHandler = new TableMetaDataHandler();
			dataHandler.handle(connection.getMetaData());
			table = dataHandler.getTable(tableName);
			insertTableFormat = getInsertTableFormat(table);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DataBaseHelpers.closeConnection(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.luna.service.sync.AbstractListWhileDoNormal#after(long)
	 */
	@Override
	public void after(long count) {

	}

	private String getInsertTableFormat(Table table) {
		List<Column> columns = table.getColumns();
		StringBuilder builder = new StringBuilder(LangUtils.append("INSERT INTO ", table.getName(), "("));
		for (int i = 0; i < columns.size(); i++) {
			builder.append(columns.get(i).getName());
			if (i < columns.size() - 1) {
				builder.append(",");
			}
		}
		return builder.append(") VALUES(%s);").toString();
	}
	
	public static void main(String[] args) {
		String ss="\n'";
		System.out.println(ss.replace("\n", "\\n").replace("'", "\\'"));
	}

}
