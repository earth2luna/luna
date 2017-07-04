/**
 * copyright@laulyl
 */
package com.luna.service.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.luna.utils.infce.IHandler;


/**
 * @author lyl
 * 2016-3-23	
 * @description 
 */
public class ResultSetMetaDataHandler implements IHandler {

	/* (non-Javadoc)
	 * @see org.laulyl.utils.infce.Handler#handle(java.lang.Object)
	 */
	@Override
	public void handle(Object object) throws SQLException {
		PreparedStatement preparedStatement=(PreparedStatement) object;
		preparedStatement.getMetaData();

	}

}
