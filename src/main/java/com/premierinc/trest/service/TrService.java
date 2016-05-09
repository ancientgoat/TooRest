package com.premierinc.trest.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.premierinc.trest.reset.TrResetCache;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.gradle.property.SaulDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TrService {

	//	@Autowired
	//	private DataSource dataSource;

	/**
	 *
	 */
	public List<Map<String, Object>> getSomeData(String inYamlDataName) {
		List<Map<String, Object>> list = readByJdbc(inYamlDataName);
		return list;
	}


	/**
	 *
	 */
	public List<Map<String, Object>> saveSomeData(final String inDataDefinitionName, final Map<String, Object> inMap) {
		return null;
	}

	/**
	 *
	 */
	private List<Map<String, Object>> readByJdbc(String inYamlDataName) {

		List<Map<String, Object>> returnList = Lists.newArrayList();
		SaulDataDefinition dataDefinition = TrResetCache.getDataDefinition(inYamlDataName);
		SaulDataSource saulDataSource = dataDefinition.getDataSource();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = saulDataSource.getDataSource()
					.getConnection();
			String sql = dataDefinition.getSql();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();

			int columnCount = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = Maps.newHashMap();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object value = resultSet.getObject(i);
					map.put(columnName, value);
				}
				returnList.add(map);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				if (null != resultSet)
					resultSet.close();

				if (null != preparedStatement)
					preparedStatement.close();

				if (null != connection)
					connection.close();
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}

		return returnList;
	}
}
