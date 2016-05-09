package com.premierinc.trest.controller;

import com.premierinc.trest.exception.TrNotFoundException;
import com.premierinc.trest.service.TrService;
import com.premierinc.trest.util.ExceptionHelper;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getSomethingOrOther(@PathVariable("name") String inDataDefinitionName) {

		List<Map<String, Object>> map = null;

		try {
			map = trService.getSomeData(inDataDefinitionName);
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
