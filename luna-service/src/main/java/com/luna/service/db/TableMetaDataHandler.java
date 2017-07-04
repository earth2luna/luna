/**
 * copyright@laulyl
 */
package com.luna.service.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.luna.utils.CharacterUtils;
import com.luna.utils.LangUtils;
import com.luna.utils.enm.JdbcTypeEnum;
import com.luna.utils.infce.IHandler;

/**
 * @author lyl 2016-3-23
 * @description
 */
public class TableMetaDataHandler implements IHandler {

	private static final Log LOG = LogFactory.getLog(TableMetaDataHandler.class);

	private static final String JOIN_SIGIN = ".";

	private List<String> tableNames = new ArrayList<String>();

	private List<Table> tables = new ArrayList<Table>();

	private Map<String, Table> tableMap = new HashMap<String, Table>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.laulyl.utils.infce.Handler#handle(java.lang.Object)
	 */
	@Override
	public void handle(Object object) throws Exception {
		DatabaseMetaData databaseMetaData = (DatabaseMetaData) object;
		LOG.info(LangUtils.append("database name:", databaseMetaData.getDatabaseProductName()));
		LOG.info(LangUtils.append("driver version:", databaseMetaData.getDriverVersion()));

		tableResultSetHandle(databaseMetaData);
	}

	private void tableResultSetHandle(DatabaseMetaData databaseMetaData) throws SQLException {
		ResultSet resultSet = databaseMetaData.getTables(null, null, null, null);
		while (resultSet.next()) {
			String tableName = resultSet.getString("TABLE_NAME");
			if (StringUtils.isBlank(tableName))
				continue;
			String tableCat = resultSet.getString("TABLE_CAT");
			String tableSchem = resultSet.getString("TABLE_SCHEM");
			String tableRemarks = resultSet.getString("REMARKS");
			tableNames
					.add(LangUtils
							.decode(StringUtils.isBlank(tableCat) && StringUtils.isBlank(tableSchem),
									LangUtils.append(tableName),

									StringUtils.isBlank(tableCat), LangUtils.append(tableSchem, JOIN_SIGIN, tableName),

									StringUtils.isBlank(tableSchem), LangUtils.append(tableCat, JOIN_SIGIN, tableName),

									LangUtils.append(tableCat, JOIN_SIGIN, tableSchem, JOIN_SIGIN, tableName))
							.toString());
			LOG.info("-------------------TABLE SPLITOR LINE-------------------");
			LOG.info(LangUtils.pf("tableCatlog:%s ,tableSchem:%s ,tableRemarks:%s ,tableName:%s",
					CharacterUtils.ifNulDefaultValue(tableCat, "null"),
					CharacterUtils.ifNulDefaultValue(tableSchem, "null"),
					CharacterUtils.ifNulDefaultValue(tableRemarks, "null"), tableNames.get(tableNames.size() - 1)));
			Table table = new Table(tableName, tableName, tableRemarks);
			table.setLocalKey(CharacterUtils.getUUID());
			columnResultSetHandle(databaseMetaData, tableCat, tableSchem, tableName, table);
			tables.add(table);
			tableMap.put(tableName, table);
		}
	}

	private void columnResultSetHandle(DatabaseMetaData databaseMetaData, String tableCat, String tableSchem,
			String tableName, Table table) throws SQLException {
		ResultSet columnResultSet = databaseMetaData.getColumns(tableCat, tableSchem, tableName, null);
		ResultSet primarykeyResultSet = databaseMetaData.getPrimaryKeys(tableCat, tableSchem, tableName);
		primarykeyResultSet.next();
		String primaryKey = primarykeyResultSet.getString("COLUMN_NAME");
		LOG.info(LangUtils.pf("primaryKey:%s", CharacterUtils.ifNulDefaultValue(primaryKey, "null")));
		int index = 0;
		while (columnResultSet.next()) {
			String columnName = columnResultSet.getString("COLUMN_NAME");
			String columnDataType = columnResultSet.getString("DATA_TYPE");
			String columnTypeName = columnResultSet.getString("TYPE_NAME");
			String columnSize = columnResultSet.getString("COLUMN_SIZE");
			String columnRemarks = columnResultSet.getString("REMARKS");
			String autoIncrement = columnResultSet.getString("IS_AUTOINCREMENT");
			LOG.info(LangUtils.pf(
					"columnName:%s ,autoIncrement:%s ,columnDataType:%s ,columnTypeName %s ,columnSize:%s ,columnRemarks:%s",

					CharacterUtils.ifNulDefaultValue(columnName, "null"),

					CharacterUtils.ifNulDefaultValue(autoIncrement, "null"),

					CharacterUtils.ifNulDefaultValue(columnDataType, "null"),

					CharacterUtils.ifNulDefaultValue(columnTypeName, "null"),
					CharacterUtils.ifNulDefaultValue(columnSize, "null"),

					CharacterUtils.ifNulDefaultValue(columnRemarks, "null")));
			Column column = new Column(columnName, columnName, columnDataType, columnSize, columnRemarks, true, null,
					JdbcTypeEnum.forCode(Integer.valueOf(columnDataType)), false);
			column.setAutoIncrement(StringUtils.equals("YES", autoIncrement));
			table.setColumn(index, column);
			column.setIndex(index);
			if (StringUtils.equals(primaryKey, columnName)) {
				column.setPrimaryKey(true);
				table.setPrimaryKey(column);
			}
			++index;
		}
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public List<Table> getTables() {
		return tables;
	}

	public Table getTable(String tableName) {
		return tableMap.get(tableName);
	}

}
