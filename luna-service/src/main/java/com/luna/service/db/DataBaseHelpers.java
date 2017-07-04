/**
 * copyright@laulyl
 */
package com.luna.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.luna.utils.infce.IHandler;


/**
 * @author lyl 2016-3-14
 * @description
 */
public class DataBaseHelpers {

	public static void queryDataBaseMetaData(Connection connection,
			IHandler handler) throws Exception {
		handle(handler, connection.getMetaData());
	}

	public static void query(Connection connection, String sql, IHandler handler)
			throws Exception {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		query(preparedStatement, handler);
	}

	public static void query(PreparedStatement preparedStatement,
			IHandler handler) throws Exception {
		preparedStatement.execute();
		handle(handler, preparedStatement);
	}

	public static int update(PreparedStatement preparedStatement,
			IHandler handler) throws Exception {
		int count = preparedStatement.executeUpdate();
		handle(handler, preparedStatement);
		return count;
	}

	private static void handle(IHandler handler, Object paramter)
			throws Exception {
		if (null != handler) {
			handler.handle(paramter);
		}
	}

	public static void closeConnection(Connection connection) {
		try {
			if (!(null == connection || connection.isClosed()))
				connection.close();
		} catch (SQLException e) {
		}
	}

	public static void closeStatement(Statement statement) {
		try {
			if (!(null == statement || statement.isClosed())) {
				statement.close();
			}
		} catch (SQLException e) {
		}
	}

}
