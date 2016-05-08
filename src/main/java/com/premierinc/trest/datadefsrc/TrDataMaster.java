package com.premierinc.trest.datadefsrc;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class TrDataMaster {

	private Map<String, TrDataDef> dataDefNameMap = Maps.newHashMap();
	private Map<String, TrDataSource> dataSourceNameMap = Maps.newHashMap();

	/**
	 *
	 */
	public TrDataMaster(@NotNull Set<TrDataDef> inDataDefs, @NotNull Set<TrDataSource> inDataSources) {
		buildMaps(inDataDefs, inDataSources);
	}

	/**
	 *
	 */
	public TrDataDef getDataDef(@NotNull String inName) {
		TrDataDef dataDef = this.dataDefNameMap.get(inName);
		if (null == dataDef) {
			throw new IllegalArgumentException(String.format("Can NOT find DataDef with name '%s'.", inName));
		}
		return dataDef;
	}

	/**
	 *
	 */
	public TrDataSource getDataSource(@NotNull String inName) {
		TrDataSource dataSource = this.dataSourceNameMap.get(inName);
		if (null == dataSource) {
			throw new IllegalArgumentException(String.format("Can NOT find DataSource with name '%s'.", inName));
		}
		return dataSource;
	}

	/**
	 *
	 */
	private void buildMaps(Set<TrDataDef> inDataDefs, Set<TrDataSource> inDataSources) {
		// Data Defs
		inDataDefs.forEach(dd -> {
			String name = dd.getName();
			if (null != name && 0 < name.length()) {
				dataDefNameMap.put(name, dd);
			} else {
				throw new IllegalArgumentException(
						String.format("Input DataDef must have a name :\n%s", dd.dumpToString()));
			}

		});
		// Data Sources
		inDataSources.forEach(ds -> {
			String name = ds.getName();
			if (null != name && 0 < name.length()) {
				dataSourceNameMap.put(name, ds);
			} else {
				throw new IllegalArgumentException(
						String.format("Input DataSource must have a name :\n%s", ds.dumpToString()));
			}
		});
	}
}