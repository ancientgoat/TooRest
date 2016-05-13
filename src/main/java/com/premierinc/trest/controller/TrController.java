package com.premierinc.trest.controller;

import com.google.common.collect.Lists;
import com.premierinc.trest.exception.TrNotFoundException;
import com.premierinc.trest.service.TrService;
import com.premierinc.trest.service.TrServicePacket;
import com.premierinc.trest.util.ExceptionHelper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.saul.gradle.datadefinition.helper.SkWherePacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 *
 */
@RestController
@RequestMapping("/toorest")
public class TrController {

	private TrService trService;

	@Autowired
	public TrController(final TrService inService) {
		this.trService = inService;
	}

	/**
	 *
	 */
	@RequestMapping(value = "/many/{name}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getSomethingOrOther(WebRequest webRequest,
			@PathVariable("name") String inDataDefinitionName) {

		TrServicePacket packet = new TrServicePacket.Builder()//
				.setYamlString(inDataDefinitionName)
				.setParamList(parseParameters(webRequest))
				.build();

		List<Map<String, Object>> map = null;
		try {
			map = trService.getSomeData(packet);
			return new ResponseEntity(map, OK);
		} catch (TrNotFoundException e) {
			System.out.println(ExceptionHelper.toString(e));
			return new ResponseEntity(e.toString(), NOT_FOUND);
		} catch (Exception e) {
			System.out.println(ExceptionHelper.toString(e));
			return new ResponseEntity(e.toString(), INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 */
	@RequestMapping(value = "/one/{name}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getOneSomethingOrOther(WebRequest webRequest,
			@PathVariable("name") String inDataDefinitionName) {

		TrServicePacket packet = new TrServicePacket.Builder()//
				.setYamlString(inDataDefinitionName)
				.setParamList(parseParameters(webRequest))
				.setMaxRows(1)
				.build();
		Map<String, Object> map = null;
		try {
			map = trService.getOneTuple(packet);
			return new ResponseEntity(map, OK);
		} catch (TrNotFoundException e) {
			System.out.println(ExceptionHelper.toString(e));
			return new ResponseEntity(e.toString(), NOT_FOUND);
		} catch (Exception e) {
			System.out.println(ExceptionHelper.toString(e));
			return new ResponseEntity(e.toString(), INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity saveSomethingOrOther(@PathVariable("name") String inDataDefinitionName,
			@RequestBody Map<String, Object> inMap) {

		List<Map<String, Object>> map = null;

		try {
			map = trService.saveSomeData(inDataDefinitionName, inMap);
			return new ResponseEntity(map, OK);
		} catch (TrNotFoundException e) {
			System.out.println(ExceptionHelper.toString(e));
			return new ResponseEntity(e.toString(), NOT_FOUND);
		} catch (Exception e) {
			System.out.println(ExceptionHelper.toString(e));
			return new ResponseEntity(e.toString(), INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 */
	private List<SkWherePacket> parseParameters(WebRequest webRequest) {
		Map<String, String[]> params = webRequest.getParameterMap();
		List<SkWherePacket> packetList = Lists.newArrayList();

		for (Map.Entry<String, String[]> eSet : params.entrySet()) {
			boolean found = false;
			String key = eSet.getKey();

			String[] values = eSet.getValue();
			Object newValue = null;
			if (0 < values.length) {
				String value = values[0];
				newValue = value;
				if (!value.startsWith("'")) {
					try {
						newValue = new BigDecimal(value);
					} catch (Exception e) {
						newValue = value;
					}
				}
			}
			SkWherePacket packet = SkWherePacket.parseRestParameters(key, newValue);
			packetList.add(packet);
		}
		return packetList;
	}

	//	@RequestMapping(value = "/dumbdto/{name}", method = RequestMethod.GET, produces = "application/json")
	//	public String getDumoDto(@PathVariable("name") String inName) {
	//
	//		try {
	//			DumbDto dumbDto = this.childEntityService.gimmeDatDumbDto(inName);
	//			return dumbDto.toString();
	//		} catch (Exception e) {
	//			System.out.println(ExceptionHelper.toString(e));
	//			throw new IllegalArgumentException(e);
	//		}
	//	}
	//
	//	@RequestMapping(value = "/dumbdtos", method = RequestMethod.GET, produces = "application/json")
	//	public String getDemDumoDtos() {
	//		try {
	//			return JsonHelper.beanToJsonString(this.childEntityService.gimmeDemDumbDtos());
	//		} catch (Exception e) {
	//			System.out.println(ExceptionHelper.toString(e));
	//			throw new IllegalArgumentException(e);
	//		}
	//	}
}
