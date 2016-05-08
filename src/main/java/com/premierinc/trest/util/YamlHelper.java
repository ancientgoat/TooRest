package com.premierinc.trest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

/**
 *
 */
public class YamlHelper {

	private YamlHelper() {
	}

	/**
	 *
	 */
	private static <C> C yamlToBean(final File inFile, final Class inClazz) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final C yamlFile = (C) mapper.readValue(inFile, inClazz);
			return yamlFile;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
			// System.out.println ExceptionHelper.toString(e)
		}
	}
}
