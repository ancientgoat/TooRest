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
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefHelper;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.gradle.property.SaulDataSource;
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
	public List<Map<String, Object>> getSomeData(TrServicePacket inPacket) {
		List<Map<String, Object>> list = readByJdbc(inPacket);
		return list;
	}

	/**
	 *
	 */
	public Map<String, Object> getOneTuple(TrServicePacket inPacket) {
		List<Map<String, Object>> list = readByJdbc(inPacket);
		Map<String, Object> map = null;
		if (0 < list.size()) {
			map = list.get(0);
		}
		return map;
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
	private List<Map<String, Object>> readByJdbc(TrServicePacket inPacket) {

		String yamlDataName = inPacket.getYamlDataName();
		List<Map<String, Object>> returnList = Lists.newArrayList();
		SaulDataDefinition dataDefinition = TrResetCache.getDataDefinition(yamlDataName);
		SaulDataSource saulDataSource = dataDefinition.getDataSource();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = saulDataSource.getDataSource()
					.getConnection();
			SaulDataDefHelper helper = new SaulDataDefHelper(dataDefinition);


			// ///////////////////////////////////////////////////////////////////

			String sql = helper.getSelectSql(inPacket.getParamList());





			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setMaxRows(inPacket.getMaxRows());
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
