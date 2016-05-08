package com.premierinc.trest.reset;

import com.google.common.collect.Maps;
import com.premierinc.trest.exception.TrNotFoundException;
import java.io.File;
import java.util.Map;
import java.util.Set;
import org.saul.dto.SaulReadJarredDefinitions;
import org.saul.gradle.datadefinition.helper.SaulSetup;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.gradle.property.DataGenProperties;
import org.saul.gradle.property.SaulDataSource;
import org.springframework.core.io.ClassPathResource;

/**
 *
 */
public class TrResetCache {

	private static Map<String, SaulDataDefinition> dataDefinitionMap = Maps.newHashMap();
	private static Map<String, SaulDataSource> dataSourceMap = Maps.newHashMap();

	/**
	 *
	 */
	public static void readAllDefinitions() {
		dataDefinitionMap.clear();
		Set<SaulDataDefinition> saulDataDefinitions = SaulReadJarredDefinitions.readAllDefinitions();
		saulDataDefinitions.forEach(dd -> {
			dataDefinitionMap.put(dd.getIdentity()
					.getName(), dd);
		});

		System.out.println("____________________________________");
		System.out.println(dataDefinitionMap.size());
		System.out.println(dataDefinitionMap.size());
		System.out.println(dataDefinitionMap.size());
		System.out.println(dataDefinitionMap.size());
		System.out.println(dataDefinitionMap.size());
		System.out.println(dataDefinitionMap.size());
		System.out.println(dataDefinitionMap.size());
		System.out.println("____________________________________");
		dataDefinitionMap.values()
				.forEach(dd -> {
					System.out.println(dd.dumpToString());
				});

		try {
			ClassPathResource resource = new ClassPathResource(DataGenProperties.DS_SHORT);
			System.out.println("========= resource : " + resource);
			File file = resource.getFile();
			Map<String, SaulDataSource> stringSaulDataSourceMap = SaulSetup.readAllSourcesIntoMap(file);

			for (SaulDataDefinition dataDef : dataDefinitionMap.values()) {
				String dataSourceName = dataDef.getDataSourceName();
				SaulDataSource saulDataSource = stringSaulDataSourceMap.get(dataSourceName);
				if (null == saulDataSource) {
					throw new IllegalArgumentException(
							String.format("Can NOT find Data source with name '%s' from " + "Data Definition '%s'.",
									dataSourceName, dataDef.getIdentity()
											.getName()));
				}
				dataDef.setDataSource(saulDataSource);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	public static Map<String, SaulDataDefinition> getDataDefinitionMap() {
		return dataDefinitionMap;
	}

	/**
	 *
	 */
	public static SaulDataDefinition getDataDefinition(String inName) {
		SaulDataDefinition dataDefinition = dataDefinitionMap.get(inName);
		if (null == dataDefinition) {
			throw new TrNotFoundException(String.format("No such Data Definition '%s'.", inName));
		}
		return dataDefinition;
	}
}
