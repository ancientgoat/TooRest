package com.premierinc.trest.service;

import com.google.common.collect.Lists;
import java.util.List;
import org.saul.gradle.datadefinition.helper.SkWherePacket;

/**
 *
 */
public class TrServicePacket {

	private String yamlDataName;
	private Integer maxRows = 10;
	private List<SkWherePacket> paramList = Lists.newArrayList();

	private TrServicePacket() {
	}

	public String getYamlDataName() {
		return yamlDataName;
	}

	public Integer getMaxRows() {
		return maxRows;
	}

	public void setYamlDataName(final String inYamlDataName) {
		yamlDataName = inYamlDataName;
	}

	public void setMaxRows(final Integer inMaxRows) {
		maxRows = inMaxRows;
	}

	public List<SkWherePacket> getParamList() {
		return paramList;
	}

	/**
	 *
	 */
	public static class Builder {
		TrServicePacket packet = new TrServicePacket();

		public Builder setParamList(final List<SkWherePacket> inParamList) {
			this.packet.paramList = inParamList;
			return this;
		}

		public Builder addParam(String inKey, Object inValue) {
			this.packet.paramList.add(SkWherePacket.parseRestParameters(inKey, inValue));
			return this;
		}

		public Builder setYamlString(final String inYamlString) {
			this.packet.yamlDataName = inYamlString;
			return this;
		}

		public Builder setMaxRows(final Integer inMaxRows) {
			this.packet.maxRows = inMaxRows;
			return this;
		}

		public TrServicePacket build() {
			return this.packet;
		}
	}
}
