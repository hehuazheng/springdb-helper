package com.hhz.spring.db.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

public class ReadWriteDataSource extends AbstractDataSource implements
		InitializingBean {
	private Logger LOGGER = LoggerFactory.getLogger(ReadWriteDataSource.class);

	private DataSource writeDataSource;
	private Map<String, DataSource> readDataSourceMap;

	@Override
	public Connection getConnection() throws SQLException {
		ConnectionType connectionType = ConnectionTypeHolder.get();
		try {
			if (connectionType == null
					|| connectionType.getType().equals(
							ConnectionType.READ_WRITE)) {
				return writeDataSource.getConnection();
			}
			if (connectionType.getType().equals(ConnectionType.READ)) {
				String key = connectionType.getKey();
				if (key == null || key.length() == 0) { // null key, return a
														// random read
														// connection
					key = randomReadKey();
				}
				if (readDataSourceMap.get(key) == null) {
					LOGGER.warn(
							"can't find read connection using {}, random one",
							key);
					key = randomReadKey();
				}
				return readDataSourceMap.get(key).getConnection();
			}
		} catch (Exception e) {
			LOGGER.error("getConnectionError", e);
			if (e instanceof SQLException) {
				throw (SQLException) e;
			} else {
				throw new SQLException(e);
			}
		}

		throw new IllegalArgumentException("invalid connection type: "
				+ connectionType.getType() + ", key: "
				+ connectionType.getKey());
	}

	private String randomReadKey() {
		String[] keys = readDataSourceMap.keySet().toArray(new String[0]);
		int size = readDataSourceMap.size();
		int rand = new Random().nextInt(size);
		return keys[rand];
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(writeDataSource);
		Assert.notNull(readDataSourceMap);
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	public void setReadDataSourceMap(Map<String, DataSource> readDataSourceMap) {
		this.readDataSourceMap = readDataSourceMap;
	}
}
