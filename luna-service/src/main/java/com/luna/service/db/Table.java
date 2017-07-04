/**
 * copyright@lyl
 */
package com.luna.service.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyl 2016-3-5
 * @description
 */
public class Table {
	private String localKey;
	private String code;
	private String name;
	private String comment;
	private List<Column> columns;
	private Column primaryKey;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public void setColumn(int index,Column column) {
		if (null == columns)
			columns = new ArrayList<Column>();
		columns.add(index,column);
	}

	public Column getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Column primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Table(String code, String name, String comment) {
		super();
		this.code = code;
		this.name = name;
		this.comment = comment;
	}

	public Table() {

	}

	public String getLocalKey() {
		return localKey;
	}

	public void setLocalKey(String localKey) {
		this.localKey = localKey;
	}

	@Override
	public String toString() {
		return "Table [localKey=" + localKey + ", code=" + code + ", name="
				+ name + ", comment=" + comment + ", columns=" + columns
				+ ", primaryKey=" + primaryKey + "]";
	}

	
	

}
