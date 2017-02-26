/**
 * copyright@laulyl
 */
package com.luna.utils.enm;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.luna.utils.AssertUtils;

/**
 * @author lyl
 * 2016-4-17	
 * @description 
 */
public enum JdbcTypeEnum {

	 // ARRAY(Types.ARRAY),
	  BIT(Types.BIT,java.lang.Integer.class),
	  TINYINT(Types.TINYINT,java.lang.Byte.class),
	  SMALLINT(Types.SMALLINT,java.lang.Short.class),
	  INTEGER(Types.INTEGER,java.lang.Integer.class),
	  BIGINT(Types.BIGINT,java.lang.Long.class),
	  FLOAT(Types.FLOAT,java.lang.Float.class),
	  DOUBLE(Types.DOUBLE,java.lang.Double.class),
	  REAL(Types.REAL,java.math.BigDecimal.class),
	  NUMERIC(Types.NUMERIC,java.math.BigDecimal.class),
	  DECIMAL(Types.DECIMAL,java.math.BigDecimal.class),
	  CHAR(Types.CHAR,java.lang.String.class),
	  VARCHAR(Types.VARCHAR,java.lang.String.class),
	  LONGVARCHAR(Types.LONGVARCHAR,java.lang.String.class),
	  DATE(Types.DATE,java.util.Date.class),
	  TIME(Types.TIME,java.util.Date.class),
	  TIMESTAMP(Types.TIMESTAMP,java.util.Date.class),
	 // BINARY(Types.BINARY),
	 // VARBINARY(Types.VARBINARY),
	 // LONGVARBINARY(Types.LONGVARBINARY),
	 // NULL(Types.NULL),
	 // OTHER(Types.OTHER),
	 // BLOB(Types.BLOB),
	  CLOB(Types.CLOB,java.lang.String.class),
	  BOOLEAN(Types.BOOLEAN,java.lang.Boolean.class),
	  //CURSOR(-10), // Oracle
	 // UNDEFINED(Integer.MIN_VALUE + 1000),
	  NVARCHAR(-9,java.lang.String.class), // JDK6
	  NCHAR(-15,java.lang.String.class), // JDK6
	  NCLOB(2011,java.lang.String.class), // JDK6
	 // STRUCT(Types.STRUCT)
	  ;

	public final int dataTypeCode;
	public final Class<?> javaClass;

	private JdbcTypeEnum(int dataTypeCode,Class<?> javaClass) {
		this.dataTypeCode = dataTypeCode;
		this.javaClass=javaClass;
	}
	
	private static Map<Integer,JdbcTypeEnum> codeLookup = new HashMap<Integer,JdbcTypeEnum>();

	  static {
	    for (JdbcTypeEnum type : JdbcTypeEnum.values()) {
	      codeLookup.put(type.dataTypeCode, type);
	    }
	  }
	  
	 public static JdbcTypeEnum forCode(int code)  {
		 JdbcTypeEnum typeEnum= codeLookup.get(code);
		 AssertUtils.isTrue(null!=typeEnum);
		 return typeEnum;
	  }

	public Class<?> getJavaClass() {
		return javaClass;
	}
	 
	 


}
