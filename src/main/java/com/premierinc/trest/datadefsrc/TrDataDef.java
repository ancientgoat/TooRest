package com.premierinc.trest.datadefsrc;

/**
 *
 */
public class TrDataDef {
	private String source;
	private String name;
	private String shortName;
	private String dataSourceName;
	private String sql;

	public String getSource() {
		return source;
	}

	public void setSource(final String inSource) {
		source = inSource;
	}

	public String getName() {
		return name;
	}

	public void setName(final String inName) {
		name = inName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(final String inShortName) {
		shortName = inShortName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(final String inDataSourceName) {
		dataSourceName = inDataSourceName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(final String inSql) {
		sql = inSql;
	}

	public String dumpToString() {
		final StringBuilder sb = new StringBuilder()//
				.append(String.format("source         : %s\n", source))
				.append(String.format("name           : %s\n", name))
				.append(String.format("shortName      : %s\n", shortName))
				.append(String.format("dataSourceName : %s\n", dataSourceName))
				.append(String.format("sql            : %s\n", sql));

		return sb.toString();
	}
}
