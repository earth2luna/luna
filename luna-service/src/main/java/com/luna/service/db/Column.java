/**
 * copyright@laulyl
 */
package com.luna.service.db;

import com.luna.utils.enm.JdbcTypeEnum;

/**
 * @author lyl 2016-3-5
 * @description
 */
public class Column {
	private String code;
	private String name;
	private String dataType;
	private String length;
	private String comment;
	private boolean isAllowNull;
	private String defaultValue;
	private JdbcTypeEnum jdbcTypeEnum;
	private boolean isPrimaryKey;
	private boolean isAutoIncrement;
	private Integer index;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isAllowNull() {
		return isAllowNull;
	}

	public void setAllowNull(boolean isAllowNull) {
		this.isAllowNull = isAllowNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public JdbcTypeEnum getJdbcTypeEnum() {
		return jdbcTypeEnum;
	}

	public void setJdbcTypeEnum(JdbcTypeEnum jdbcTypeEnum) {
		this.jdbcTypeEnum = jdbcTypeEnum;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public Column(String code, String name, String dataType, String length, String comment, boolean isAllowNull,
			String defaultValue, JdbcTypeEnum jdbcTypeEnum, boolean isPrimaryKey) {
		super();
		this.code = code;
		this.name = name;
		this.dataType = dataType;
		this.length = length;
		this.comment = comment;
		this.isAllowNull = isAllowNull;
		this.defaultValue = defaultValue;
		this.jdbcTypeEnum = jdbcTypeEnum;
		this.isPrimaryKey = isPrimaryKey;
	}

	public Column() {
	}

}
